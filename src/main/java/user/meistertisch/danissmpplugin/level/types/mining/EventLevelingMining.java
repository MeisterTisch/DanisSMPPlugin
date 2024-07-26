package user.meistertisch.danissmpplugin.level.types.mining;

import net.kyori.adventure.text.Component;
import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import user.meistertisch.danissmpplugin.files.FileLevels;
import user.meistertisch.danissmpplugin.level.BossBarLevel;
import user.meistertisch.danissmpplugin.level.MessageLevelUp;
import user.meistertisch.danissmpplugin.level.types.LevelType;

public class EventLevelingMining implements Listener {

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        //TODO: Add CoreProtect API to check if block was placed by player
        if(LevelType.MINING.getValidBlocks().contains(event.getBlock().getType())){
            Player player = event.getPlayer();
            FileConfiguration config = FileLevels.getConfig();

            double xp;

            switch (event.getBlock().getType()){
                case STONE, DEEPSLATE -> xp = 0.01;
                case TUFF -> xp = 0.025;
                case GRANITE, DIORITE, ANDESITE -> xp = 0.05;
                case COAL_ORE, DEEPSLATE_COAL_ORE, COPPER_ORE, DEEPSLATE_COPPER_ORE -> xp = 0.1;
                case IRON_ORE, DEEPSLATE_IRON_ORE -> xp = 0.2;
                case REDSTONE_ORE, DEEPSLATE_REDSTONE_ORE, LAPIS_ORE, DEEPSLATE_LAPIS_ORE -> xp = 0.3;
                case GOLD_ORE, DEEPSLATE_GOLD_ORE -> xp = 0.4;
                case DIAMOND_ORE, DEEPSLATE_DIAMOND_ORE, EMERALD_ORE, DEEPSLATE_EMERALD_ORE -> xp = 0.5;
                default -> {
                    return;
                }
            }

            //TODO: Multiplier for enchantments

            player.sendActionBar(Component.text(event.getBlock().getType() + " | " + (xp * 100) + " XP"));

            int levelBefore = (int) config.getDouble(player.getName() + ".level.mining");
            double levelAfter = config.getDouble(player.getName() + ".level.mining") + xp;

            config.set(player.getName() + ".level.mining", levelAfter);
            FileLevels.saveConfig();

            if(levelAfter - levelBefore >= 1){
                player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1, 1);
            } else {
                if(FileLevels.getConfig().getBoolean(player.getName() + ".level.xpSound"))
                    player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1, 10);
            }

            if((int) levelAfter % 10 == 0 && levelAfter - levelBefore >= 1){
                new MessageLevelUp(player, LevelType.MINING, (int) levelAfter);
                FileLevels.getConfig().set(player.getName() + ".rewardsLeft.mining", FileLevels.getConfig().getInt(player.getName() + ".rewardsLeft.mining") + 1);
                FileLevels.saveConfig();
            }

            new BossBarLevel(LevelType.MINING, player);
        }
    }
}
