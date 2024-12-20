package user.meistertisch.danissmpplugin.level.types.farming;

import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.block.data.Ageable;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityBreedEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.BoundingBox;
import user.meistertisch.danissmpplugin.Main;
import user.meistertisch.danissmpplugin.files.FileLevels;
import user.meistertisch.danissmpplugin.level.BossBarLevel;
import user.meistertisch.danissmpplugin.level.LevelingLimiter;
import user.meistertisch.danissmpplugin.level.MessageLevelUp;
import user.meistertisch.danissmpplugin.level.types.LevelType;
import user.meistertisch.danissmpplugin.spawn.ManagerSpawn;

import java.util.ResourceBundle;


public class EventLevelingFarming implements Listener {

    // FOR DESTROYING FARMING BLOCKS
    @EventHandler
    public void blockDestroyed(BlockBreakEvent event){
        if(!Main.getPlugin().getConfig().getBoolean("levelingSystem.use", true)){
            return;
        }

        BoundingBox bb = ManagerSpawn.getBoundingBox();
        if(bb.contains(event.getBlock().getBoundingBox()) || bb.contains(event.getPlayer().getBoundingBox())){
            return;
        }

        //~~Add CoreProtect API to check if block was placed by player~~
        //Don't want, made timeout

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
        if(!Main.getPlugin().getConfig().getBoolean("levelingSystem.use", true)){
            return;
        }

        BoundingBox bb = ManagerSpawn.getBoundingBox();
        if(bb.contains(event.getBlock().getBoundingBox()) || bb.contains(event.getPlayer().getBoundingBox())){
            return;
        }

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

    // FOR MAKING THE ANIMALS GO BAM BAM
    @EventHandler
    public void animalBreeding(EntityBreedEvent event){
        if(event.getBreeder() instanceof Player player){
            double xp = 0.3;

            ResourceBundle bundle = ResourceBundle.getBundle("language_" + FileLevels.getConfig().getString(player.getName() + ".lang"));
            showXP(player, bundle.getString("level.farming.breeding"), xp);
        }
    }

    @EventHandler
    public void berryBushInteract(PlayerInteractEvent event){
        if(!Main.getPlugin().getConfig().getBoolean("levelingSystem.use", true)){
            return;
        }

        BoundingBox bb = ManagerSpawn.getBoundingBox();
        if((event.getClickedBlock() != null && bb.contains(event.getClickedBlock().getBoundingBox())) || bb.contains(event.getPlayer().getBoundingBox())){
            return;
        }

        if(event.getAction() == Action.RIGHT_CLICK_BLOCK && event.getClickedBlock().getType() == Material.SWEET_BERRY_BUSH){
            Player player = event.getPlayer();
            double xp = 0.1;

            if(event.getClickedBlock().getBlockData() instanceof Ageable ageable && ageable.getAge() == ageable.getMaximumAge()){
                showXP(player, event.getClickedBlock().getType(), xp);
            }
        }
    }


    // Showing the player the XP they got
    private void showXP(Player player, Material block, double xp){
        LevelingLimiter.playerLeveled(player);
        if(LevelingLimiter.isPlayerLimited(player)) return;

        FileConfiguration config = FileLevels.getConfig();
        player.sendActionBar(Component.text(block + " | " + (xp * 100) + " XP"));

        int levelBefore = (int) config.getDouble(player.getName() + ".level.farming");
        double levelAfter = config.getDouble(player.getName() + ".level.farming") + xp;

        config.set(player.getName() + ".level.farming", levelAfter);
        FileLevels.saveConfig();

        if (levelAfter - levelBefore >= 1) {
            player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1, 1);
        } else {
            if (FileLevels.getConfig().getBoolean(player.getName() + ".level.xpSound"))
                player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1, 10);
        }

        if ((int) levelAfter % 10 == 0 && levelAfter - levelBefore >= 1) {
            new MessageLevelUp(player, LevelType.FARMING, (int) levelAfter);
            FileLevels.getConfig().set(player.getName() + ".rewardsLeft.farming", FileLevels.getConfig().getInt(player.getName() + ".rewardsLeft.farming") + 1);
            FileLevels.saveConfig();
        }

        new BossBarLevel(LevelType.FARMING, player);
    }

    private void showXP(Player player, String text, double xp){
        LevelingLimiter.playerLeveled(player);
        if(LevelingLimiter.isPlayerLimited(player)) return;

        FileConfiguration config = FileLevels.getConfig();
        player.sendActionBar(Component.text(text + " | " + (xp * 100) + " XP"));

        int levelBefore = (int) config.getDouble(player.getName() + ".level.farming");
        double levelAfter = config.getDouble(player.getName() + ".level.farming") + xp;

        config.set(player.getName() + ".level.farming", levelAfter);
        FileLevels.saveConfig();

        if (levelAfter - levelBefore >= 1) {
            player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1, 1);
        } else {
            if (FileLevels.getConfig().getBoolean(player.getName() + ".level.xpSound"))
                player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1, 10);
        }

        if ((int) levelAfter % 10 == 0 && levelAfter - levelBefore >= 1) {
            new MessageLevelUp(player, LevelType.FARMING, (int) levelAfter);
            FileLevels.getConfig().set(player.getName() + ".rewardsLeft.farming", FileLevels.getConfig().getInt(player.getName() + ".rewardsLeft.farming") + 1);
            FileLevels.saveConfig();
        }

        new BossBarLevel(LevelType.FARMING, player);
    }
}
