package user.meistertisch.danissmpplugin.essentials;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import user.meistertisch.danissmpplugin.Main;
import user.meistertisch.danissmpplugin.files.FilePlayer;

public class ManagerFileSetterAtFirstTimeJoin {
    public static void set(Player player){
        String name = player.getName();
        FileConfiguration config = FilePlayer.getConfig();

        config.set(name + ".lang", "en");
        config.set(name + ".level.xpSounds", true);
        config.set(name + ".isTeam", false);
        config.set(name + ".tpa", true);
        config.set(name + ".durabilityPing", true);
        config.set(name + ".sccsSearchesLeft", Main.getPlugin().getConfig().getInt("slimeChunkCheck.checks"));

        FilePlayer.saveConfig();
    }
}
