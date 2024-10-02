package user.meistertisch.danissmpplugin.essentials.chat;

import io.papermc.paper.event.player.AsyncChatEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;


public class ListenerChatFormater implements Listener {
    @EventHandler
    public void onChatMessage(AsyncChatEvent event){
        event.renderer(new MainChatRenderer());
    }
}
