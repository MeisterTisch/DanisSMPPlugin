package user.meistertisch.danissmpplugin.combatTimer;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import user.meistertisch.danissmpplugin.admin.spawn.ManagerSpawn;

public class ListenerCombat implements Listener {
    @EventHandler
    public void playerAttacked(EntityDamageByEntityEvent event){
        if(event.getEntity() instanceof Player player){
            if(ManagerSpawn.getBoundingBox().contains(player.getBoundingBox()) && !ManagerCombatTimer.isInCombat(player)){
                return;
            }

            if(event.getDamager() instanceof Player damager){
                ManagerCombatTimer.addPlayer(player, 10);
                ManagerCombatTimer.addPlayer(damager, 10);
            } else {
                ManagerCombatTimer.addPlayer(player, 5);
            }
        }
    }
}
