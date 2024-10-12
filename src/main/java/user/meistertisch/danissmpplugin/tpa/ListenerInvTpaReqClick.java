package user.meistertisch.danissmpplugin.tpa;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import user.meistertisch.danissmpplugin.Main;
import user.meistertisch.danissmpplugin.files.FileLevels;
import user.meistertisch.danissmpplugin.files.FilePlayer;
import user.meistertisch.danissmpplugin.level.invs.drumroll.InventoryDrumroll;
import user.meistertisch.danissmpplugin.level.types.LevelType;

import java.util.ResourceBundle;

public class ListenerInvTpaReqClick implements Listener {
    @EventHandler
    public void invClick(InventoryClickEvent event){
        if(!(event.getWhoClicked() instanceof Player player))
            return;

        ResourceBundle bundle = ResourceBundle.getBundle("language_" + FilePlayer.getConfig().getString(player.getName() + ".lang"));
        String title = bundle.getString("tpa.inv.title");

        if(!player.getOpenInventory().title().equals(Component.text(title)))
            return;

        event.setCancelled(true);

        ItemStack item = event.getCurrentItem();
        if(item == null || !item.hasItemMeta() || !item.getItemMeta().hasItemName())
            return;

        Material material = item.getType();
        if(material != Material.RED_STAINED_GLASS_PANE && material != Material.LIME_STAINED_GLASS_PANE){
            return;
        }


    }
}
