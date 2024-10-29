package user.meistertisch.danissmpplugin.admin.dimensions;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPortalEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import user.meistertisch.danissmpplugin.Main;

public class ListenerDimension implements Listener {
    @EventHandler
    public void noTeleporting(PlayerPortalEvent event) {
        if (event.getCause() == PlayerTeleportEvent.TeleportCause.END_PORTAL && event.getFrom().getWorld().getName().equals("world")) {
            if (!Main.getPlugin().getConfig().getBoolean("dimension.end")) {
                event.setCancelled(true);
                Player player = event.getPlayer();
                player.setVelocity(player.getEyeLocation().getDirection().multiply(-1.25));
            }
        } else if (event.getCause() == PlayerTeleportEvent.TeleportCause.NETHER_PORTAL && event.getFrom().getWorld().getName().equals("world")) {
            if (!Main.getPlugin().getConfig().getBoolean("dimension.nether")) {
                event.setCancelled(true);
                Player player = event.getPlayer();
                player.setVelocity(player.getEyeLocation().getDirection().multiply(-1.25));
            }
        }
    }
}
