package user.meistertisch.danissmpplugin.misc;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import user.meistertisch.danissmpplugin.spawn.ManagerSpawn;

public class ListenerPlayerDeathSpawnTp implements Listener {
    @EventHandler
    public void playerDead(PlayerDeathEvent event){
        if(event.getPlayer().getRespawnLocation() == null || ManagerSpawn.getSpawnLocations().contains(event.getPlayer().getRespawnLocation())){
            event.getPlayer().setRespawnLocation(ManagerSpawn.getRandomSpawn(), true);
        }
    }
}
