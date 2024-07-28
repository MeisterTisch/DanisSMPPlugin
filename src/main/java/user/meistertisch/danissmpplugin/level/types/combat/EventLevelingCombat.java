package user.meistertisch.danissmpplugin.level.types.combat;

import net.kyori.adventure.text.Component;
import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import user.meistertisch.danissmpplugin.files.FileLevels;
import user.meistertisch.danissmpplugin.level.BossBarLevel;
import user.meistertisch.danissmpplugin.level.MessageLevelUp;
import user.meistertisch.danissmpplugin.level.types.LevelType;

public class EventLevelingCombat implements Listener {
    @EventHandler
    public void monsterKilled(EntityDeathEvent event){
        if(LevelType.COMBAT.getValidMobs().contains(event.getEntityType()) && event.getEntity().getKiller() instanceof Player player){
            double xp;

            switch (event.getEntityType()){
                case ZOMBIFIED_PIGLIN, ZOMBIE_VILLAGER, ENDERMITE, SILVERFISH -> xp = 0.05;
                case ZOMBIE, SKELETON, CREEPER, SPIDER, WITCH, PIGLIN, DROWNED, GUARDIAN, MAGMA_CUBE, PILLAGER, SHULKER, VEX, SLIME -> xp = 0.1;
                case CAVE_SPIDER, PHANTOM, BLAZE, GHAST, WITHER_SKELETON, BOGGED, HUSK, STRAY, ZOGLIN -> xp = 0.15;
                case ENDERMAN, HOGLIN, EVOKER, BREEZE, VINDICATOR, ILLUSIONER -> xp = 0.25;
                case PIGLIN_BRUTE, RAVAGER -> xp = 0.4;
                case ELDER_GUARDIAN -> xp = 0.6;
                case WITHER -> xp = 0.75;
                case ENDER_DRAGON -> xp = 1;
                case WARDEN -> xp = 2.5;
                default -> xp = 0;
            }

            showXP(player, event.getEntityType(), xp);
        }
    }



    // Showing the player the XP they got
    private void showXP(Player player, EntityType monster, double xp){
        FileConfiguration config = FileLevels.getConfig();
        player.sendActionBar(Component.text(monster + " | " + (xp * 100) + " XP"));

        int levelBefore = (int) config.getDouble(player.getName() + ".level.combat");
        double levelAfter = config.getDouble(player.getName() + ".level.combat") + xp;

        config.set(player.getName() + ".level.combat", levelAfter);
        FileLevels.saveConfig();

        if (levelAfter - levelBefore >= 1) {
            player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1, 1);
        } else {
            if (FileLevels.getConfig().getBoolean(player.getName() + ".level.xpSound"))
                player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1, 10);
        }

        if ((int) levelAfter % 10 == 0 && levelAfter - levelBefore >= 1) {
            new MessageLevelUp(player, LevelType.COMBAT, (int) levelAfter);
            FileLevels.getConfig().set(player.getName() + ".rewardsLeft.combat", FileLevels.getConfig().getInt(player.getName() + ".rewardsLeft.combat") + 1);
            FileLevels.saveConfig();
        }

        new BossBarLevel(LevelType.COMBAT, player);
    }
}
