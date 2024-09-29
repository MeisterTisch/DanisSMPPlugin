package user.meistertisch.danissmpplugin.level.invs.drumroll;

import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import user.meistertisch.danissmpplugin.files.FilePlayer;

import java.util.ResourceBundle;

public class ListenerInvInteractDrumroll implements Listener {
    @EventHandler
    public void inventoryInteract(InventoryClickEvent event){
        if(!(event.getWhoClicked() instanceof Player player)){
            return;
        }

        ResourceBundle bundle = ResourceBundle.getBundle("language_" + FilePlayer.getConfig().getString(player.getName() + ".lang"));

        if(event.getView().title().equals(Component.text(bundle.getString("level.inv.drumroll.title")))){
            event.setCancelled(true);
        }
    }
}
