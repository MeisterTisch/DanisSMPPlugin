package user.meistertisch.danissmpplugin.essentials;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextReplacementConfig;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.title.Title;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import user.meistertisch.danissmpplugin.Main;
import user.meistertisch.danissmpplugin.files.FilePlayer;
import user.meistertisch.danissmpplugin.files.FileTeams;

import java.time.Duration;
import java.util.Locale;
import java.util.ResourceBundle;

public class ListenerJoinAndLeave implements Listener {
    TextColor color1 = TextColor.color(Main.getPlugin().getConfig().getColor("colors.accent1", Color.fromRGB(0x7704a8)).asRGB());
    TextColor color2 = TextColor.color(Main.getPlugin().getConfig().getColor("colors.accent2", Color.fromRGB(0xF01BC)).asRGB());

    @EventHandler
    public void playerJoinEvent(PlayerJoinEvent event) {
        event.joinMessage(Component.text(""));
        Player player = event.getPlayer();
        if (FilePlayer.getConfig().getString(player.getName()) == null) {
            FilePlayer.getConfig().set(player.getName() + ".lang", "en");
            FilePlayer.saveConfig();
            player.sendMessage(Component.text("Your language has been set to English! To change the language, please execute this command:").color(NamedTextColor.RED));
            player.sendMessage(Component.text("/language <language>").color(NamedTextColor.GOLD));
        }
        ResourceBundle bundle = ResourceBundle.getBundle("language_" + FilePlayer.getConfig().getString(player.getName() + ".lang"));

        Component playerText = Component.text(player.getName()).color(color1).decorate(TextDecoration.BOLD);
        boolean firstJoin = !player.hasPlayedBefore();

        for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
            String lang = FilePlayer.getConfig().getString(onlinePlayer.getName() + ".lang");
            ResourceBundle tempBundle = ResourceBundle.getBundle("language_" + lang);

            if (firstJoin) {
                onlinePlayer.sendMessage(Component.text(tempBundle.getString("join.joinFirstTime")).color(color2).replaceText(
                        TextReplacementConfig.builder().match("%player%").replacement(playerText).build()
                ));
            } else {
                onlinePlayer.sendMessage(Component.text(tempBundle.getString("join.joinMessage")).color(color2).replaceText(
                        TextReplacementConfig.builder().match("%player%").replacement(playerText).build()
                ));
            }
        }

        Title title = Title.title(
                Component.text(bundle.getString("join.title")).color(color1),
                Component.text(""),
                Title.Times.times(Duration.ofSeconds(1), Duration.ofSeconds(2), Duration.ofSeconds(1))
        );
        if(firstJoin) {
          title = Title.title(
                  Component.text(bundle.getString("join.firstTime.title")).color(color1),
                  Component.text(Main.getPlugin().getConfig().getString("name") + "!").color(color2),
                  Title.Times.times(Duration.ofSeconds(1), Duration.ofSeconds(2), Duration.ofSeconds(1))
          );
        }
        player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 40, 255, false, false, false));
        player.showTitle(title);

        player.sendPlayerListHeaderAndFooter(
                Component.text(Main.getPlugin().getConfig().getString("list.header")).color(color1),
                Component.text(Main.getPlugin().getConfig().getString("list.footer")).color(color2)
        );

        if(FilePlayer.getConfig().getBoolean(player.getName() + ".isTeam")) {
            String teamName = FilePlayer.getConfig().getString(player.getName() + ".team");
            Color teamColor = FileTeams.getConfig().getColor(teamName + ".color");

            TextDecoration teamDecoration;
            Component teamNameComp;
            if(FileTeams.getConfig().getString(teamName + ".decoration") != null) {
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

            Component name = Component.text("%team% %player%");
            name = name
                    .replaceText(TextReplacementConfig.builder().match("%team%").replacement(teamNameComp).build())
                    .replaceText(TextReplacementConfig.builder().match("%player%").replacement(player.getName()).build());

            player.playerListName(name);
        }
    }

    @EventHandler
    public void playerLeaveEvent(PlayerQuitEvent event) {
        event.quitMessage(Component.text(""));
        Player player = event.getPlayer();

        Component playerText = Component.text(player.getName()).color(color1).decorate(TextDecoration.BOLD);

        for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
            String lang = FilePlayer.getConfig().getString(onlinePlayer.getName() + ".lang");
            if (lang == null) {
                lang = "de";
            }
            ResourceBundle bundle = ResourceBundle.getBundle("language_" + lang);
            onlinePlayer.sendMessage(Component.text(bundle.getString("leaveMessage")).color(color2).replaceText(
                    TextReplacementConfig.builder().match("%player%").replacement(playerText).build()
            ));
        }
    }
}
