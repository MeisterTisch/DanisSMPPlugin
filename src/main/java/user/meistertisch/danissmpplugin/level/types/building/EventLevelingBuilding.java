package user.meistertisch.danissmpplugin.level.types.building;

import net.kyori.adventure.text.Component;
import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.CreativeCategory;
import user.meistertisch.danissmpplugin.Main;
import user.meistertisch.danissmpplugin.files.FileLevels;
import user.meistertisch.danissmpplugin.files.FilePlayer;
import user.meistertisch.danissmpplugin.level.BossBarLevel;
import user.meistertisch.danissmpplugin.level.LevelingLimiter;
import user.meistertisch.danissmpplugin.level.MessageLevelUp;
import user.meistertisch.danissmpplugin.level.types.LevelType;

import java.util.ResourceBundle;

public class EventLevelingBuilding implements Listener {
    @EventHandler
    public void blockPlacedEvent(BlockPlaceEvent event) {
        if(!Main.getPlugin().getConfig().getBoolean("levelingSystem.use", true)){
            return;
        }

        if(event.getBlock().getType().getCreativeCategory() == CreativeCategory.BUILDING_BLOCKS){
            double xp = 0.01;

            ResourceBundle bundle = ResourceBundle.getBundle("language_" + FilePlayer.getConfig().getString(event.getPlayer().getName() + ".lang"));
            showXP(event.getPlayer(), bundle.getString("level.building.build"), xp);
        }
    }

    private void showXP(Player player, String text, double xp){
        LevelingLimiter.playerLeveled(player);
        if(LevelingLimiter.isPlayerLimited(player)) return;

        FileConfiguration config = FileLevels.getConfig();
        player.sendActionBar(Component.text(text + " | " + (xp * 100) + " XP"));

        int levelBefore = (int) config.getDouble(player.getName() + ".level.building");
        double levelAfter = config.getDouble(player.getName() + ".level.building") + xp;

        config.set(player.getName() + ".level.building", levelAfter);
        FileLevels.saveConfig();

        if (levelAfter - levelBefore >= 1) {
            player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1, 1);
        } else {
            if (FileLevels.getConfig().getBoolean(player.getName() + ".level.xpSound"))
                player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1, 10);
        }

        if ((int) levelAfter % 10 == 0 && levelAfter - levelBefore >= 1) {
            new MessageLevelUp(player, LevelType.BUILDING, (int) levelAfter);
            FileLevels.getConfig().set(player.getName() + ".rewardsLeft.building", FileLevels.getConfig().getInt(player.getName() + ".rewardsLeft.building") + 1);
            FileLevels.saveConfig();
        }

        new BossBarLevel(LevelType.BUILDING, player);
    }
}
