package user.meistertisch.danissmpplugin.level.types.trading;

import io.papermc.paper.event.player.PlayerTradeEvent;
import net.kyori.adventure.text.Component;
import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityBreedEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import user.meistertisch.danissmpplugin.Main;
import user.meistertisch.danissmpplugin.files.FileLevels;
import user.meistertisch.danissmpplugin.files.FilePlayer;
import user.meistertisch.danissmpplugin.level.BossBarLevel;
import user.meistertisch.danissmpplugin.level.LevelingLimiter;
import user.meistertisch.danissmpplugin.level.MessageLevelUp;
import user.meistertisch.danissmpplugin.level.types.LevelType;

import java.util.ResourceBundle;

public class EventLevelingTrading implements Listener {
    @EventHandler
    public void tradeEvent(PlayerTradeEvent event) {
        if(!Main.getPlugin().getConfig().getBoolean("levelingSystem.use", true)){
            return;
        }

        Player player = event.getPlayer();
        double xp = 0.25;
        if(event.getVillager().getType() == EntityType.WANDERING_TRADER) xp = 0.5;

        ResourceBundle bundle = ResourceBundle.getBundle("language_" + FilePlayer.getConfig().getString(player.getName() + ".lang"));
        showXP(player, bundle.getString("level.trading"), xp);
    }

    @EventHandler
    public void villagerKillEvent(EntityDeathEvent event) {
        if(!Main.getPlugin().getConfig().getBoolean("levelingSystem.use", true)){
            return;
        }

        if(event.getEntity().getType() == EntityType.VILLAGER && event.getEntity().getKiller() instanceof Player player){
            double xp = -0.5;
            ResourceBundle bundle = ResourceBundle.getBundle("language_" + FilePlayer.getConfig().getString(player.getName() + ".lang"));
            showXP(player, bundle.getString("level.trading.killing"), xp);
        }
    }

    @EventHandler
    public void babyEvent(EntityBreedEvent event) {
        if(!Main.getPlugin().getConfig().getBoolean("levelingSystem.use", true)){
            return;
        }

        if(event.getBreeder() instanceof Player player){
            double xp = 0.3;
            ResourceBundle bundle = ResourceBundle.getBundle("language_" + FilePlayer.getConfig().getString(player.getName() + ".lang"));
            showXP(player, bundle.getString("level.trading.breeding"), xp);
        }
    }

    private void showXP(Player player, String text, double xp){
        LevelingLimiter.playerLeveled(player);
        if(LevelingLimiter.isPlayerLimited(player)) return;

        FileConfiguration config = FileLevels.getConfig();
        player.sendActionBar(Component.text(text + " | " + (xp * 100) + " XP"));

        int levelBefore = (int) config.getDouble(player.getName() + ".level.trading");
        double levelAfter = config.getDouble(player.getName() + ".level.trading") + xp;

        if(levelAfter < 0) levelAfter = 0;

        config.set(player.getName() + ".level.trading", levelAfter);
        FileLevels.saveConfig();

        if (levelAfter - levelBefore >= 1) {
            player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1, 1);
        } else {
            if (FileLevels.getConfig().getBoolean(player.getName() + ".level.xpSound"))
                player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1, 10);
        }

        if ((int) levelAfter % 10 == 0 && levelAfter - levelBefore >= 1) {
            new MessageLevelUp(player, LevelType.TRADING, (int) levelAfter);
            FileLevels.getConfig().set(player.getName() + ".rewardsLeft.trading", FileLevels.getConfig().getInt(player.getName() + ".rewardsLeft.trading") + 1);
            FileLevels.saveConfig();
        }

        new BossBarLevel(LevelType.TRADING, player);
    }
}
