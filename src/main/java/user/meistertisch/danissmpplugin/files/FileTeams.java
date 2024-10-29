package user.meistertisch.danissmpplugin.files;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextReplacementConfig;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import user.meistertisch.danissmpplugin.Main;

import java.io.File;
import java.io.IOException;
import java.util.Locale;

public class FileTeams {
    private static File file;
    private static FileConfiguration config;

    public static void setup() {
        file = new File(
                Main.getPlugin().getDataFolder(), "teams.yml"); //teams
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        config = YamlConfiguration.loadConfiguration(file);
    }

    public static FileConfiguration getConfig() {
        return config;
    }

    public static void saveConfig() {
        try {
            config.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        reload();
    }

    public static void reload() {
        config = YamlConfiguration.loadConfiguration(file);
    }

    public static Component getTeamName(Player player){
        if(FilePlayer.getConfig().getBoolean(player.getName() + ".isTeam")) {
            String teamName = FilePlayer.getConfig().getString(player.getName() + ".team");

            if(teamName == null) {
                return player.name();
            }

            Color teamColor = config.getColor(teamName + ".color");

            TextDecoration teamDecoration;
            Component teamNameComp;
            Component prefix;
            Component suffix;
            Component name = Component.text("%prefix% %player% %suffix%");

            if(FileTeams.getConfig().getBoolean(teamName + ".usesPrefixAndSuffix")){
                if (FileTeams.getConfig().getString(teamName + ".decoration") != null) {
                    teamDecoration = TextDecoration.valueOf(FileTeams.getConfig().getString(teamName + ".decoration").toUpperCase(Locale.ROOT));
                    prefix = Component.text("[").color(TextColor.color(teamColor.asRGB()))
                            .append(Component.text(FileTeams.getConfig().getString(teamName + ".prefix", "null")).color(TextColor.color(teamColor.asRGB())).decorate(teamDecoration))
                            .append(Component.text("]").color(TextColor.color(teamColor.asRGB())));
                    suffix = Component.text("[").color(TextColor.color(teamColor.asRGB()))
                            .append(Component.text(FileTeams.getConfig().getString(teamName + ".suffix", "null")).color(TextColor.color(teamColor.asRGB())).decorate(teamDecoration))
                            .append(Component.text("]").color(TextColor.color(teamColor.asRGB())));
                } else {
                    prefix = Component.text("[").color(TextColor.color(teamColor.asRGB()))
                            .append(Component.text(FileTeams.getConfig().getString(teamName + ".prefix", "null")).color(TextColor.color(teamColor.asRGB())))
                            .append(Component.text("]").color(TextColor.color(teamColor.asRGB())));
                    suffix = Component.text("[").color(TextColor.color(teamColor.asRGB()))
                            .append(Component.text(FileTeams.getConfig().getString(teamName + ".suffix", "null")).color(TextColor.color(teamColor.asRGB())))
                            .append(Component.text("]").color(TextColor.color(teamColor.asRGB())));
                }
                prefix = prefix.decorate(TextDecoration.BOLD);
                suffix = suffix.decorate(TextDecoration.BOLD);

                name = name
                        .replaceText(TextReplacementConfig.builder().match("%prefix%").replacement(prefix).build())
                        .replaceText(TextReplacementConfig.builder().match("%player%").replacement(player.getName()).build())
                        .replaceText(TextReplacementConfig.builder().match("%suffix%").replacement(suffix).build());
            } else {
                if (FileTeams.getConfig().getString(teamName + ".decoration") != null) {
                    teamDecoration = TextDecoration.valueOf(FileTeams.getConfig().getString(teamName + ".decoration").toUpperCase(Locale.ROOT));
                    teamNameComp = Component.text("[").color(TextColor.color(teamColor.asRGB()))
                            .append(Component.text(teamName).color(TextColor.color(teamColor.asRGB())).decorate(teamDecoration))
                            .append(Component.text("]").color(TextColor.color(teamColor.asRGB())));
                } else {
                    teamNameComp = Component.text("[").color(TextColor.color(teamColor.asRGB()))
                            .append(Component.text(teamName).color(TextColor.color(teamColor.asRGB())))
                            .append(Component.text("]").color(TextColor.color(teamColor.asRGB())));
                }
                teamNameComp = teamNameComp.decorate(TextDecoration.BOLD);

                name = name
                        .replaceText(TextReplacementConfig.builder().match("%prefix%").replacement(teamNameComp).build())
                        .replaceText(TextReplacementConfig.builder().match("%player%").replacement(player.getName()).build())
                        .replaceText(TextReplacementConfig.builder().match(" %suffix%").replacement(Component.text("")).build());
            }

            return name;
        }
        return player.name();
    }
}
