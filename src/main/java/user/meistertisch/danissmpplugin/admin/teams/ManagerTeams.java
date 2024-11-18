package user.meistertisch.danissmpplugin.admin.teams;


import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import user.meistertisch.danissmpplugin.files.FilePlayer;
import user.meistertisch.danissmpplugin.files.FileTeams;

import javax.annotation.Nullable;
import java.util.Locale;

public class ManagerTeams {
    public static void createTeam(String teamName, Color color, @Nullable TextDecoration decoration) {
        FileConfiguration config = FileTeams.getConfig();
        config.set(teamName + ".color", color);
        if (decoration != null) {
            config.set(teamName + ".decoration", decoration.toString().toLowerCase(Locale.ROOT));
        }
        config.set(teamName + ".usesPrefixAndSuffix", false);
        FileTeams.saveConfig();
    }

    public static void deleteTeam(String teamName) {
        FileConfiguration config = FileTeams.getConfig();
        config.set(teamName, null);
        FileTeams.saveConfig();

        for (Player player : Bukkit.getOnlinePlayers()) {
            if (FilePlayer.getConfig().getString(player.getName() + ".team").equals(teamName)) {
                removeMember(player);
            }
        }
    }

    public static void setTeamColor(String teamName, Color color) {
        FileConfiguration config = FileTeams.getConfig();
        config.set(teamName + ".color", color);
        FileTeams.saveConfig();
    }

    public static void setTeamDecoration(String teamName, TextDecoration decoration) {
        FileConfiguration config = FileTeams.getConfig();
        config.set(teamName + ".decoration", decoration.toString().toLowerCase(Locale.ROOT));
        FileTeams.saveConfig();
    }

    public static void removeTeamDecoration(String teamName) {
        FileConfiguration config = FileTeams.getConfig();
        config.set(teamName + ".decoration", null);
        FileTeams.saveConfig();
    }

    public static void setTeamName(String teamName, String newTeamName) {
        FileConfiguration config = FileTeams.getConfig();
        config.set(newTeamName, config.getConfigurationSection(teamName));
        config.set(teamName, null);
        FileTeams.saveConfig();
    }

    public static void addMember(String teamName, Player player) {
        FileConfiguration config = FilePlayer.getConfig();
        config.set(player.getName() + ".isTeam", true);
        config.set(player.getName() + ".team", teamName);
        player.displayName(FileTeams.getTeamNamePrefixComponent(player));
        player.playerListName(FileTeams.getTeamNamePrefixComponent(player));
        FileTeams.saveConfig();
    }

    public static void removeMember(Player player) {
        FileConfiguration config = FilePlayer.getConfig();
        config.set(player.getName() + ".isTeam", false);
        config.set(player.getName() + ".team", null);
        player.displayName(Component.text(player.getName()));
        player.playerListName(Component.text(player.getName()));
        FileTeams.saveConfig();
    }

    public static void setTeamPrefixAndSuffix(String teamName, String prefix, String suffix) {
        FileConfiguration config = FileTeams.getConfig();
        config.set(teamName + ".prefix", prefix);
        config.set(teamName + ".suffix", suffix);
        config.set(teamName + ".usesPrefixAndSuffix", true);
        FileTeams.saveConfig();
    }

    public static void removeTeamPrefixAndSuffix(String teamName) {
        FileConfiguration config = FileTeams.getConfig();
        config.set(teamName + ".prefix", null);
        config.set(teamName + ".suffix", null);
        config.set(teamName + ".usesPrefixAndSuffix", false);
        FileTeams.saveConfig();
    }


}
