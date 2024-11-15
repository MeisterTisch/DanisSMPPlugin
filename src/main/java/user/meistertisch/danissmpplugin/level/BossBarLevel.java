package user.meistertisch.danissmpplugin.level;

import net.kyori.adventure.bossbar.BossBar;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;
import user.meistertisch.danissmpplugin.files.FileLevels;
import user.meistertisch.danissmpplugin.files.FilePlayer;
import user.meistertisch.danissmpplugin.level.types.LevelType;

import java.util.ResourceBundle;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;

public class BossBarLevel {
    //Make Constructor
    BossBar bossBar;
    private static ScheduledExecutorService service;

    public BossBarLevel(LevelType levelType, Player player){
        if(!FilePlayer.getConfig().getBoolean(player.getName() + ".level.xpBar")) return;

        ResourceBundle bundle = ResourceBundle.getBundle("language_" + FilePlayer.getConfig().getString(player.getName() + ".lang"));
        player.activeBossBars().forEach(bossBar -> bossBar.removeViewer(player));
        service = Executors.newSingleThreadScheduledExecutor();

        int level = (int) FileLevels.getConfig().getDouble(player.getName() + ".level." + levelType.toString().toLowerCase());
        float progress = (float) FileLevels.getConfig().getDouble(player.getName() + ".level." + levelType.toString().toLowerCase()) - level;

        String levelName = bundle.getString("level");
        String typeName = bundle.getString("level." + levelType.toString().toLowerCase());
        Component name = Component.text(typeName + " | " + levelName + ": " + level).color(levelType.getColor());
        this.bossBar = BossBar.bossBar(name, progress, levelType.getBarColor(), BossBar.Overlay.PROGRESS);

        bossBar.addViewer(player);

        ScheduledFuture scheduledFuture = service.schedule(() -> {
            bossBar.removeViewer(player);
        }, 5, java.util.concurrent.TimeUnit.SECONDS);
    }
}
