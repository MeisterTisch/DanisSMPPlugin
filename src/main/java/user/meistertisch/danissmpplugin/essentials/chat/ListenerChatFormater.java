package user.meistertisch.danissmpplugin.essentials.chat;

import io.papermc.paper.event.player.AsyncChatEvent;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import user.meistertisch.danissmpplugin.Main;
import user.meistertisch.danissmpplugin.files.FileAdmins;
import user.meistertisch.danissmpplugin.files.FilePlayer;

import java.util.ResourceBundle;


public class ListenerChatFormater implements Listener {
    @EventHandler
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

        event.renderer(new MainChatRenderer());
        SchedulerChatCooldown.addPlayer(event.getPlayer(), Main.getPlugin().getConfig().getInt("chat.cooldown"));
    }
}
