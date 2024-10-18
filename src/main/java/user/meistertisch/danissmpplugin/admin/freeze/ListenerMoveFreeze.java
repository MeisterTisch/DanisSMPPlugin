package user.meistertisch.danissmpplugin.admin.freeze;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerMoveEvent;


public class ListenerMoveFreeze implements Listener {
    @EventHandler
    public void targetMoves(PlayerMoveEvent event){
        if(ManagerFreeze.isFrozen(event.getPlayer())){
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void targetGetsDamage(EntityDamageEvent event){
        if(event.getEntity() instanceof Player player && ManagerFreeze.isFrozen(player)){
            event.setCancelled(true);
        }
    }


    @EventHandler
    public void targetBuilds(BlockPlaceEvent event){
        if(ManagerFreeze.isFrozen(event.getPlayer())){
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void targetBreaks(BlockBreakEvent event){
        if(ManagerFreeze.isFrozen(event.getPlayer())){
            event.setCancelled(true);
        }
    }
}
