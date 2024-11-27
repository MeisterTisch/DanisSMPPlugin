package user.meistertisch.danissmpplugin.afk;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class ListenerMoveAFK implements Listener {
    @EventHandler
    public void onMoveAFK(PlayerMoveEvent event){
        if(ManagerAFK.isAFK(event.getPlayer())){
            ManagerAFK.removeAFK(event.getPlayer());
        }
    }
}
