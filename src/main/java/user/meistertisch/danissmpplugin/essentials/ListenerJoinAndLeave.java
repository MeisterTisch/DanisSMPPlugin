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
import user.meistertisch.danissmpplugin.files.FileAdmins;
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
        boolean firstJoin = !player.hasPlayedBefore();

        //Player joined first time, setting files
        if (firstJoin) {
            ManagerFileSetterAtFirstTimeJoin.set(player);

            player.sendMessage(Component.text("Your language has been set to English! To change the language, please execute this command:").color(NamedTextColor.RED));
            player.sendMessage(Component.text("/language <language>").color(NamedTextColor.GOLD));
        }

        ResourceBundle bundle = ResourceBundle.getBundle("language_" + FilePlayer.getConfig().getString(player.getName() + ".lang"));

        //Join Message
        Component playerText = Component.text(player.getName()).color(color1).decorate(TextDecoration.BOLD);

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

        //Title and Effect
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

        //Setting List Header and Footer
        player.sendPlayerListHeaderAndFooter(
                Component.text(Main.getPlugin().getConfig().getString("list.header", "")).color(color1),
                Component.text(Main.getPlugin().getConfig().getString("list.footer", "")).color(color2)
        );

        //Setting Team
        player.displayName(FileTeams.getTeamName(player));
        player.playerListName(FileTeams.getTeamName(player));

        //Was hidden before player left and joined again?
        FilePlayer.getConfig().set(player.getName() + ".hidden", false);
        FilePlayer.saveConfig();
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
