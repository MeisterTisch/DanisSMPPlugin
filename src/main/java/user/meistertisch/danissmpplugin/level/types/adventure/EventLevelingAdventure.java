package user.meistertisch.danissmpplugin.level.types.adventure;

import net.kyori.adventure.text.Component;
import org.bukkit.Sound;
import org.bukkit.Statistic;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerStatisticIncrementEvent;
import user.meistertisch.danissmpplugin.files.FileLevels;
import user.meistertisch.danissmpplugin.level.BossBarLevel;
import user.meistertisch.danissmpplugin.level.LevelingLimiter;
import user.meistertisch.danissmpplugin.level.MessageLevelUp;
import user.meistertisch.danissmpplugin.level.types.LevelType;

import java.util.concurrent.ScheduledExecutorService;

public class EventLevelingAdventure implements Listener {
    private static ScheduledExecutorService service;
    //TODO: Check for if already gave xp for the statistic
    //TODO: Rate Limit

    @EventHandler
    public void checkForStatistics(PlayerStatisticIncrementEvent event){
        Player player = event.getPlayer();
        Statistic stat = event.getStatistic();
        System.out.println("Statistic: " + event.getStatistic());
        int modulo;

        switch (stat) {
            case RAID_TRIGGER, RAID_WIN -> modulo = 5;
            case ARMOR_CLEANED, DEATHS, CLEAN_SHULKER_BOX -> modulo = 10;
            case ANIMALS_BRED, BANNER_CLEANED, FLOWER_POTTED, TARGET_HIT, CAULDRON_USED -> modulo = 15;
            case OPEN_BARREL, CAULDRON_FILLED, DISPENSER_INSPECTED, DROPPER_INSPECTED, LEAVE_GAME, RECORD_PLAYED, PLAYER_KILLS, TRAPPED_CHEST_TRIGGERED -> modulo = 25;
            case TIME_SINCE_DEATH, TIME_SINCE_REST -> modulo = 30;
            case CAKE_SLICES_EATEN-> modulo = 35;
            case INTERACT_WITH_ANVIL, BEACON_INTERACTION, INTERACT_WITH_BLAST_FURNACE, BREWINGSTAND_INTERACTION,
                 INTERACT_WITH_CAMPFIRE, INTERACT_WITH_CARTOGRAPHY_TABLE, CRAFTING_TABLE_INTERACTION, FURNACE_INTERACTION,
                 INTERACT_WITH_GRINDSTONE, INTERACT_WITH_LECTERN, INTERACT_WITH_LOOM, INTERACT_WITH_SMITHING_TABLE,
                 INTERACT_WITH_SMOKER, INTERACT_WITH_STONECUTTER -> modulo = 40;
            case BELL_RING, FISH_CAUGHT, HOPPER_INSPECTED, NOTEBLOCK_PLAYED, NOTEBLOCK_TUNED, TALKED_TO_VILLAGER, TRADED_WITH_VILLAGER -> modulo = 50;
            case PLAY_ONE_MINUTE, TOTAL_WORLD_TIME -> modulo = 60;
            case ENDERCHEST_OPENED, CHEST_OPENED, ITEM_ENCHANTED, SHULKER_BOX_OPENED, SLEEP_IN_BED -> modulo = 100;
            case DAMAGE_BLOCKED_BY_SHIELD, DAMAGE_RESISTED, DAMAGE_DEALT_RESISTED, DAMAGE_ABSORBED, DROP_COUNT -> modulo = 250;
            case DAMAGE_DEALT, DAMAGE_DEALT_ABSORBED, DAMAGE_TAKEN -> modulo = 500;
            case JUMP, MOB_KILLS -> modulo = 1000;
            case CLIMB_ONE_CM, WALK_ON_WATER_ONE_CM, WALK_UNDER_WATER_ONE_CM, PIG_ONE_CM, STRIDER_ONE_CM, SNEAK_TIME -> modulo = 250 * 100;
            case CROUCH_ONE_CM, SWIM_ONE_CM, FALL_ONE_CM, WALK_ONE_CM, HORSE_ONE_CM, MINECART_ONE_CM -> modulo = 500 * 100;
            case FLY_ONE_CM, SPRINT_ONE_CM, BOAT_ONE_CM, AVIATE_ONE_CM -> modulo = 1000 * 100;
            default -> {
                return;
            }
        }
        if(event.getNewValue() != 0 && event.getNewValue() % modulo == 0){
            showXP(player, stat, 1);
        } else {
            System.out.println(player.getStatistic(stat) % modulo);
        }
    }

    private void showXP(Player player, Statistic stat, double xp){
        LevelingLimiter.playerLeveled(player);
        if(LevelingLimiter.isPlayerLimited(player)) return;

        FileConfiguration config = FileLevels.getConfig();
        player.sendActionBar(Component.text(stat + " | " + (xp * 100) + " XP"));

        int levelBefore = (int) config.getDouble(player.getName() + ".level.adventure");
        double levelAfter = config.getDouble(player.getName() + ".level.adventure") + xp;

        config.set(player.getName() + ".level.adventure", levelAfter);
        FileLevels.saveConfig();

        if (levelAfter - levelBefore >= 1) {
            player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1, 1);
        } else {
            if (FileLevels.getConfig().getBoolean(player.getName() + ".level.xpSound"))
                player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1, 10);
        }

        if ((int) levelAfter % 10 == 0 && levelAfter - levelBefore >= 1) {
            new MessageLevelUp(player, LevelType.ADVENTURE, (int) levelAfter);
            FileLevels.getConfig().set(player.getName() + ".rewardsLeft.adventure", FileLevels.getConfig().getInt(player.getName() + ".rewardsLeft.adventure") + 1);
            FileLevels.saveConfig();
        }

        new BossBarLevel(LevelType.ADVENTURE, player);
    }
}

