package user.meistertisch.danissmpplugin.level.types.magic;

import net.kyori.adventure.text.Component;
import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.enchantment.EnchantItemEvent;
import user.meistertisch.danissmpplugin.Main;
import user.meistertisch.danissmpplugin.files.FileLevels;
import user.meistertisch.danissmpplugin.files.FilePlayer;
import user.meistertisch.danissmpplugin.level.BossBarLevel;
import user.meistertisch.danissmpplugin.level.LevelingLimiter;
import user.meistertisch.danissmpplugin.level.MessageLevelUp;
import user.meistertisch.danissmpplugin.level.types.LevelType;

import java.util.ResourceBundle;

public class EventLevelingMagic implements Listener {
    @EventHandler
    public void enchantingTableEvent(EnchantItemEvent event){
        if(!Main.getPlugin().getConfig().getBoolean("levelingSystem.use", true)){
            return;
        }

        double xp = (double) event.getExpLevelCost() / 30;

        ResourceBundle bundle = ResourceBundle.getBundle("language_" + FilePlayer.getConfig().getString(event.getEnchanter().getName() + ".lang"));
        showXP(event.getEnchanter(), bundle.getString("level.magic.enchanting"), xp);
    }

    private void showXP(Player player, String text, double xp){
        LevelingLimiter.playerLeveled(player);
        if(LevelingLimiter.isPlayerLimited(player)) return;

        FileConfiguration config = FileLevels.getConfig();
        player.sendActionBar(Component.text(text + " | " + (xp * 100) + " XP"));

        int levelBefore = (int) config.getDouble(player.getName() + ".level.magic");
        double levelAfter = config.getDouble(player.getName() + ".level.magic") + xp;

        config.set(player.getName() + ".level.magic", levelAfter);
        FileLevels.saveConfig();

        if (levelAfter - levelBefore >= 1) {
            player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1, 1);
        } else {
            if (FileLevels.getConfig().getBoolean(player.getName() + ".level.xpSound"))
                player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1, 10);
        }

        if ((int) levelAfter % 10 == 0 && levelAfter - levelBefore >= 1) {
            new MessageLevelUp(player, LevelType.MAGIC, (int) levelAfter);
            FileLevels.getConfig().set(player.getName() + ".rewardsLeft.magic", FileLevels.getConfig().getInt(player.getName() + ".rewardsLeft.magic") + 1);
            FileLevels.saveConfig();
        }

        new BossBarLevel(LevelType.MAGIC, player);
    }
}
