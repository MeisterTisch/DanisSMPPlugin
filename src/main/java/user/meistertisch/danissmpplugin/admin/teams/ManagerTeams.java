package user.meistertisch.danissmpplugin.admin.teams;

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
        for (String player : FilePlayer.getConfig().getKeys(false)) {
            if (FilePlayer.getConfig().getString(player + ".team", "").equals(teamName)) {
                removeMember(player);
            }
        }

        FileConfiguration config = FileTeams.getConfig();
        config.set(teamName, null);
        FileTeams.saveConfig();
        updateTeamShowcase();
    }

    public static void setTeamColor(String teamName, Color color) {
        FileConfiguration config = FileTeams.getConfig();
        config.set(teamName + ".color", color);
        FileTeams.saveConfig();
        updateTeamShowcase();
    }

    public static void setTeamDecoration(String teamName, TextDecoration decoration) {
        FileConfiguration config = FileTeams.getConfig();
        config.set(teamName + ".decoration", decoration.toString().toLowerCase(Locale.ROOT));
        FileTeams.saveConfig();
        updateTeamShowcase();
    }

    public static void removeTeamDecoration(String teamName) {
        FileConfiguration config = FileTeams.getConfig();
        config.set(teamName + ".decoration", null);
        FileTeams.saveConfig();
        updateTeamShowcase();
    }

    public static void setTeamName(String teamName, String newTeamName) {
        FileConfiguration config = FileTeams.getConfig();
        config.set(newTeamName, config.getConfigurationSection(teamName));
        config.set(teamName, null);
        FileTeams.saveConfig();
        updateTeamShowcase();
    }

    public static void addMember(String teamName, String player) {
        FileConfiguration config = FilePlayer.getConfig();
        config.set(player + ".isTeam", true);
        config.set(player + ".team", teamName);

        Player playerObj = Bukkit.getPlayer(player);
        if (playerObj != null) {
            playerObj.displayName(FileTeams.getTeamNamePrefixComponent(playerObj));
            playerObj.playerListName(FileTeams.getTeamNamePrefixComponent(playerObj));
        }

        FilePlayer.saveConfig();
    }

    public static void removeMember(String player) {
        FileConfiguration config = FilePlayer.getConfig();
        config.set(player + ".isTeam", false);
        config.set(player + ".team", null);

        Player playerObj = Bukkit.getPlayer(player);
        if (playerObj != null) {
            playerObj.displayName(FileTeams.getTeamNamePrefixComponent(playerObj));
            playerObj.playerListName(FileTeams.getTeamNamePrefixComponent(playerObj));
        }

        FilePlayer.saveConfig();
    }

    public static void setTeamPrefixAndSuffix(String teamName, String prefix, String suffix) {
        FileConfiguration config = FileTeams.getConfig();
        config.set(teamName + ".prefix", prefix);
        config.set(teamName + ".suffix", suffix);
        config.set(teamName + ".usesPrefixAndSuffix", true);
        FileTeams.saveConfig();
        updateTeamShowcase();
    }

    public static void removeTeamPrefixAndSuffix(String teamName) {
        FileConfiguration config = FileTeams.getConfig();
        config.set(teamName + ".prefix", null);
        config.set(teamName + ".suffix", null);
        config.set(teamName + ".usesPrefixAndSuffix", false);
        FileTeams.saveConfig();
        updateTeamShowcase();
    }

    public static void updateTeamShowcase() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            player.displayName(FileTeams.getTeamNamePrefixComponent(player));
            player.playerListName(FileTeams.getTeamNamePrefixComponent(player));
        }
    }
}
