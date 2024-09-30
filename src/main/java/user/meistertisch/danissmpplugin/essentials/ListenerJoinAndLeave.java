package user.meistertisch.danissmpplugin.essentials;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextReplacementConfig;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import user.meistertisch.danissmpplugin.files.FilePlayer;

import java.util.ResourceBundle;

public class ListenerJoinAndLeave implements Listener {
    TextColor color1 = TextColor.color(0x7704a8);
    TextColor color2 = TextColor.color(0xF01BC);

    @EventHandler
    public void playerJoinEvent(PlayerJoinEvent event){
        event.joinMessage(Component.text(""));
        Player player = event.getPlayer();
        if(FilePlayer.getConfig().getString(player.getName()) == null) {
            FilePlayer.getConfig().set(player.getName() + ".lang", "de");
            FilePlayer.saveConfig();
        }

        Component playerText = Component.text(player.getName()).color(color1).decorate(TextDecoration.BOLD);
        boolean firstJoin = !player.hasPlayedBefore();

        for(Player onlinePlayer : Bukkit.getOnlinePlayers()){
            String lang = FilePlayer.getConfig().getString(onlinePlayer.getName() + ".lang");
            if(lang == null){
                lang = "de";
            }
            ResourceBundle bundle = ResourceBundle.getBundle("language_" + lang);

            if(firstJoin){
                onlinePlayer.sendMessage(Component.text(bundle.getString("joinFirstTime")).color(color2).replaceText(
                        TextReplacementConfig.builder().match("%player%").replacement(playerText).build()
                ));
            } else {
                onlinePlayer.sendMessage(Component.text(bundle.getString("joinMessage")).color(color2).replaceText(
                        TextReplacementConfig.builder().match("%player%").replacement(playerText).build()
                ));
            }
        }


    }

    @EventHandler
    public void playerLeaveEvent(PlayerQuitEvent event){
        event.quitMessage(Component.text(""));
        Player player = event.getPlayer();

        Component playerText = Component.text(player.getName()).color(color1).decorate(TextDecoration.BOLD);

        for(Player onlinePlayer : Bukkit.getOnlinePlayers()){
            String lang = FilePlayer.getConfig().getString(onlinePlayer.getName() + ".lang");
            if(lang == null){
                lang = "de";
            }
            ResourceBundle bundle = ResourceBundle.getBundle("language_" + lang);
            onlinePlayer.sendMessage(Component.text(bundle.getString("leaveMessage")).color(color2).replaceText(
                    TextReplacementConfig.builder().match("%player%").replacement(playerText).build()
            ));
        }
    }
}
