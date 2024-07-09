package user.meistertisch.danissmpplugin.level;

import net.kyori.adventure.bossbar.BossBar;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import user.meistertisch.danissmpplugin.FilePlayer;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;

public class BossBarLevel {
    //Make Constructor
    BossBar bossBar;
    private static ScheduledExecutorService service;

    public BossBarLevel(LevelType levelType, Player player){
        player.activeBossBars().forEach(bossBar -> bossBar.removeViewer(player));
        service = Executors.newSingleThreadScheduledExecutor();

        int level = (int) FilePlayer.getConfig().getDouble(player.getName() + ".level." + levelType.toString().toLowerCase());
        float progress = (float) FilePlayer.getConfig().getDouble(player.getName() + ".level." + levelType.toString().toLowerCase()) - level;

        this.bossBar = BossBar.bossBar(
                Component.text("Mining | Level: " + level).color(TextColor.color(255,255,255)),
                progress, BossBar.Color.WHITE, BossBar.Overlay.PROGRESS);

        bossBar.addViewer(player);
//        if(level % 5 == 0){
//            player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1, 1);
//        } else {
//            player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1, 1);
//        }

        ScheduledFuture scheduledFuture = service.schedule(() -> {
            bossBar.removeViewer(player);
        }, 5, java.util.concurrent.TimeUnit.SECONDS);
    }
}
