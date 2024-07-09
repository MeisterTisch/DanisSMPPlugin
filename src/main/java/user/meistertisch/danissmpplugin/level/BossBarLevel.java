package user.meistertisch.danissmpplugin.level;

import net.kyori.adventure.bossbar.BossBar;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.entity.Player;
import user.meistertisch.danissmpplugin.files.FilePlayer;

import java.util.ResourceBundle;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;

public class BossBarLevel {
    //Make Constructor
    BossBar bossBar;
    private static ScheduledExecutorService service;

    public BossBarLevel(LevelType levelType, Player player){
        ResourceBundle bundle = ResourceBundle.getBundle("language_" + FilePlayer.getConfig().getString(player.getName() + ".lang"));
        player.activeBossBars().forEach(bossBar -> bossBar.removeViewer(player));
        service = Executors.newSingleThreadScheduledExecutor();

        int level = (int) FilePlayer.getConfig().getDouble(player.getName() + ".level." + levelType.toString().toLowerCase());
        float progress = (float) FilePlayer.getConfig().getDouble(player.getName() + ".level." + levelType.toString().toLowerCase()) - level;

        //TODO: Change Text of BossBar
        String levelName = bundle.getString("level");
        String typeName = bundle.getString("level." + levelType.toString().toLowerCase());
        Component name = Component.text(typeName + " | " + levelName + ": " + level).color(levelType.color);
        this.bossBar = BossBar.bossBar(name, progress, levelType.barColor, BossBar.Overlay.PROGRESS);

        bossBar.addViewer(player);

        ScheduledFuture scheduledFuture = service.schedule(() -> {
            bossBar.removeViewer(player);
        }, 5, java.util.concurrent.TimeUnit.SECONDS);
    }
}
