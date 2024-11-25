package user.meistertisch.danissmpplugin.essentials;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import user.meistertisch.danissmpplugin.Main;
import user.meistertisch.danissmpplugin.files.FilePlayer;

public class ManagerFileSetterAtJoin {
    public static void set(Player player){
        String name = player.getName();
        FileConfiguration config = FilePlayer.getConfig();

        if(!config.contains(name + ".lang")){
            config.set(name + ".lang", "en");
        }

        if(!config.contains(name + ".level.xpSounds")){
            config.set(name + ".level.xpSounds", true);
        }

        if(!config.contains(name + ".level.xpBar")){
            config.set(name + ".level.xpBar", true);
        }

        if(!config.contains(name + ".isTeam")){
            config.set(name + ".isTeam", false);
        }

        if(!config.contains(name + ".tpa")){
            config.set(name + ".tpa", true);
        }

        if(!config.contains(name + ".durabilityPing")){
            config.set(name + ".durabilityPing", true);
        }

        if (!config.contains(name + ".muted")) {
            config.set(name + ".muted", false);
        }

        if(!config.contains(name + ".sccsSearchesLeft")){
            config.set(name + ".sccsSearchesLeft", Main.getPlugin().getConfig().getInt("slimeChunkCheck.checks"));
        }

        FilePlayer.saveConfig();
    }
}
