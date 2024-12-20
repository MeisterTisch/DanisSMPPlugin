package user.meistertisch.danissmpplugin.spawn;

import com.destroystokyo.paper.event.block.BlockDestroyEvent;
import com.destroystokyo.paper.event.entity.PhantomPreSpawnEvent;
import org.bukkit.block.Block;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.*;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.util.BoundingBox;
import user.meistertisch.danissmpplugin.combatTimer.ManagerCombatTimer;
import user.meistertisch.danissmpplugin.level.types.building.EventLevelingBuilding;

import java.util.List;

public class ListenerSpawnProt implements Listener {
    BoundingBox bb = ManagerSpawn.getBoundingBox();

    @EventHandler
    public void blockDestroy(BlockDestroyEvent event){
        if(bb.contains(event.getBlock().getBoundingBox())){
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void blockDestroy(BlockBreakEvent event){
        if(ManagerSpawn.isBypass(event.getPlayer(), true))
            return;

        if(bb.contains(event.getBlock().getBoundingBox())){
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void explosion(EntityExplodeEvent event){
        boolean isBlockInSpawn = false;
        for(Block block : event.blockList()){
            if(bb.contains(block.getBoundingBox())){
                isBlockInSpawn = true;
                break;
            }
        }

        if(bb.contains(event.getEntity().getBoundingBox()) || isBlockInSpawn){
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void interact(PlayerInteractEvent event){
        if(ManagerSpawn.isBypass(event.getPlayer(), true))
            return;

        if(bb.contains(event.getPlayer().getBoundingBox())){
            event.setCancelled(true);
        }
        if(event.getClickedBlock() != null && bb.contains(event.getClickedBlock().getBoundingBox())){
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void blockPlace(BlockPlaceEvent event){
        if(ManagerSpawn.isBypass(event.getPlayer(), true))
            return;

        if(bb.contains(event.getBlockPlaced().getBoundingBox())){
            event.setCancelled(true);
            EventLevelingBuilding.placedBlocksAtSpawn.add(event.getPlayer());
        }
    }

    @EventHandler
    public void fallDamage(EntityDamageEvent event){
        if(event.getEntity() instanceof Player player){
            if(ManagerSpawn.isBypass(player, false))
                return;
            if(ManagerCombatTimer.isInCombat(player))
                return;
        }

        if(bb.contains(event.getEntity().getBoundingBox())){
            if(event.getCause() == EntityDamageEvent.DamageCause.FALL){
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void entityDamage(EntityDamageByEntityEvent event){
        if(event.getDamager() instanceof Player player){
            if(ManagerSpawn.isBypass(player, false))
                return;
            if(ManagerCombatTimer.isInCombat(player))
                return;
        }

        if(bb.contains(event.getEntity().getBoundingBox())){
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void entityDamage(EntityDamageByBlockEvent event){
        if(event.getDamager() instanceof Player player){
            if(ManagerSpawn.isBypass(player, false))
                return;
            if(ManagerCombatTimer.isInCombat(player))
                return;
        }

        if(bb.contains(event.getEntity().getBoundingBox())){
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void mobSpawn(CreatureSpawnEvent event){
        if(bb.contains(event.getEntity().getBoundingBox())){

            //Command /spit :)
            if(event.getEntityType() == EntityType.LLAMA_SPIT)
                return;
            if(event.getEntityType() == EntityType.ITEM)
                return;
            if(event.getEntityType() == EntityType.EXPERIENCE_ORB)
                return;
            if(event.getEntityType() == EntityType.FIREWORK_ROCKET)
                return;
            if(event.getEntityType() == EntityType.ARROW)
                return;
            if(event.getEntityType() == EntityType.SPECTRAL_ARROW)
                return;
            if(event.getEntityType() == EntityType.TRIDENT)
                return;
            if(event.getEntityType() == EntityType.SNOWBALL)
                return;
            if(event.getEntityType() == EntityType.EGG)
                return;
            if(event.getEntityType() == EntityType.ENDER_PEARL)
                return;
            if(event.getEntityType() == EntityType.EYE_OF_ENDER)
                return;


            event.setCancelled(true);
        }
    }

    @EventHandler
    public void hunger(FoodLevelChangeEvent event){
        if(bb.contains(event.getEntity().getBoundingBox())){
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void phantomSpawning(PhantomPreSpawnEvent event){
        if(bb.contains(event.getSpawningEntity().getBoundingBox())){
            event.setCancelled(true);
        }
    }
}
