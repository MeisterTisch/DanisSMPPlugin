package user.meistertisch.danissmpplugin.essentials.chat;

import io.papermc.paper.event.player.AsyncChatEvent;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextReplacementConfig;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.eclipse.sisu.Priority;
import user.meistertisch.danissmpplugin.Main;
import user.meistertisch.danissmpplugin.files.FileAdmins;
import user.meistertisch.danissmpplugin.files.FilePlayer;

import java.util.ResourceBundle;


public class ListenerChatFormater implements Listener {
    public void onChatMessage(AsyncChatEvent event){
        if(Main.getPlugin().getConfig().getBoolean("chat.disabled") && !FileAdmins.isAdmin(event.getPlayer())){
            ResourceBundle bundle = ResourceBundle.getBundle("language_" + FilePlayer.getConfig().getString(event.getPlayer().getName() + ".lang"));
            event.getPlayer().sendMessage(Component.text(bundle.getString("commands.chat")).color(NamedTextColor.RED));
            event.setCancelled(true);
            return;
        }

        if(SchedulerChatCooldown.isOnCooldown(event.getPlayer())){
            ResourceBundle bundle = ResourceBundle.getBundle("language_" + FilePlayer.getConfig().getString(event.getPlayer().getName() + ".lang"));
            event.getPlayer().sendMessage(Component.text(bundle.getString("commands.chat.onCooldown")).color(NamedTextColor.RED));
            event.setCancelled(true);
            return;
        }

        if(FilePlayer.getConfig().getBoolean(event.getPlayer().getName() + ".muted")){
            ResourceBundle bundle = ResourceBundle.getBundle("language_" + FilePlayer.getConfig().getString(event.getPlayer().getName() + ".lang"));
            event.getPlayer().sendMessage(Component.text(bundle.getString("commands.mute.chatMuted")).color(NamedTextColor.RED));
            event.setCancelled(true);

            for(Player all : Bukkit.getOnlinePlayers()){
                if(FileAdmins.isAdmin(all)){
                    all.sendMessage(
                            Component.text(bundle.getString("commands.mute.chatMuted.toAdmin")).color(NamedTextColor.RED)
                                    .replaceText(TextReplacementConfig.builder().match("%target%").replacement(
                                            Component.text(event.getPlayer().getName()).color(NamedTextColor.GOLD)
                                    ).build())
                            .append(event.message().color(NamedTextColor.GOLD))
                    );
                }
            }
            return;
        }

        event.renderer(new MainChatRenderer());
        SchedulerChatCooldown.addPlayer(event.getPlayer(), Main.getPlugin().getConfig().getInt("chat.cooldown"));
    }
}
