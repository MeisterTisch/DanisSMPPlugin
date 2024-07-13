package user.meistertisch.danissmpplugin.level.types;

import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.block.data.Ageable;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import user.meistertisch.danissmpplugin.files.FilePlayer;
import user.meistertisch.danissmpplugin.level.BossBarLevel;
import user.meistertisch.danissmpplugin.level.MessageLevelUp;


public class EventLevelingFarming implements Listener {

    // FOR DESTROYING FARMING BLOCKS
    @EventHandler
    public void blockDestroyed(BlockBreakEvent event){
        //TODO: Add CoreProtect API to check if block was placed by player

        if(LevelType.FARMING.getValidBlocks().contains(event.getBlock().getType())) {
            Player player = event.getPlayer();
            double xp;

            switch (event.getBlock().getType()) {
                case CARROTS, POTATOES, WHEAT, BEETROOTS -> xp = 0.05;
                case SWEET_BERRY_BUSH -> xp = 0.1;
                case MELON, PUMPKIN -> {
                    xp = 0.25;

                    if (player.getActiveItemHand() == EquipmentSlot.HAND) {
                        ItemStack item = player.getInventory().getItemInMainHand();
                        if (item.hasItemMeta() && item.getItemMeta().hasEnchant(Enchantment.SILK_TOUCH)) {
                            xp = 0.4;
                            break;
                        }
                    }
                    if (player.getActiveItemHand() == EquipmentSlot.OFF_HAND) {
                        ItemStack item = player.getInventory().getItemInOffHand();
                        if (item.hasItemMeta() && item.getItemMeta().hasEnchant(Enchantment.SILK_TOUCH)) {
                            xp = 0.4;
                        }
                    }
                }
                case TORCHFLOWER -> xp = 0.5;
                default -> {
                    return;
                }
            }


            //TODO: Multiplier for enchantments

            switch (event.getBlock().getType()){
                case MELON, PUMPKIN, TORCHFLOWER -> {
                    showXP(player, event.getBlock().getType(), xp);
                }
                default -> {
                    //Check for plant not being a fucking minor, so you won't get xp for being a fucking pedophile, fucking loser
                    if(event.getBlock().getBlockData() instanceof Ageable ageable && ageable.getAge() == ageable.getMaximumAge()){
                        showXP(player, event.getBlock().getType(), xp);
                    }
                }
            }
        }
    }

    // FOR PLACING SEEDS
    @EventHandler
    public void placingSeeds(BlockPlaceEvent event){
        Player player = event.getPlayer();
        double xp;

        switch (event.getBlock().getType()) {
            case CARROTS, POTATOES, WHEAT, BEETROOTS -> xp = 0.05;
            case SWEET_BERRY_BUSH -> xp = 0.1;
            case MELON_STEM, PUMPKIN_STEM -> xp = 0.25;
            case TORCHFLOWER_CROP -> xp = 0.5;
            default -> {
                return;
            }
        }
        xp = xp / 2;

        //TODO: Multiplier for enchantments
        showXP(player, event.getBlock().getType(), xp);
    }


    // Showing the player the XP they got
    private void showXP(Player player, Material block, double xp){
        FileConfiguration config = FilePlayer.getConfig();
        player.sendActionBar(Component.text(block + " | " + (xp * 100) + " XP"));

        int levelBefore = (int) config.getDouble(player.getName() + ".level.farming");
        double levelAfter = config.getDouble(player.getName() + ".level.farming") + xp;

        config.set(player.getName() + ".level.farming", levelAfter);
        FilePlayer.saveConfig();

        if (levelAfter - levelBefore >= 1) {
            player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1, 1);
        } else {
            if (FilePlayer.getConfig().getBoolean(player.getName() + ".level.xpSound"))
                player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1, 10);
        }

        if ((int) levelAfter % 10 == 0 && levelAfter - levelBefore >= 1) {
            new MessageLevelUp(player, LevelType.FARMING, (int) levelAfter);
        }

        new BossBarLevel(LevelType.FARMING, player);
    }
}
