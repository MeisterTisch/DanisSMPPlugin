package user.meistertisch.danissmpplugin.level.invs.start;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import user.meistertisch.danissmpplugin.files.FileLevels;
import user.meistertisch.danissmpplugin.files.FilePlayer;
import user.meistertisch.danissmpplugin.level.invs.drumroll.InventoryDrumroll;
import user.meistertisch.danissmpplugin.level.types.LevelType;

import java.util.ResourceBundle;

public class ListenerInvClickStartRewarding implements Listener {
    @EventHandler
    public void invClick(InventoryClickEvent event){
        if(!(event.getWhoClicked() instanceof Player player))
            return;

        ResourceBundle bundle = ResourceBundle.getBundle("language_" + FilePlayer.getConfig().getString(player.getName() + ".lang"));
        String title = bundle.getString("level.inv.start.title");

        if(!player.getOpenInventory().getOriginalTitle().equals(title))
            return;

        event.setCancelled(true);
        ItemStack item = event.getCurrentItem();

        if(item == null || !item.hasItemMeta() || !item.getItemMeta().hasItemName())
            return;

        Material material = item.getType();
        LevelType type = null;

        for(LevelType lvlType : LevelType.values()){
            if(lvlType.getInvStartItem() == material){
                type = lvlType;
                break;
            }
        }
        if(type == null)
            return;

        player.closeInventory();

        if(FileLevels.getConfig().getInt(player.getName() + ".rewardsLeft." + type.name().toLowerCase()) <= 0){
            player.sendMessage(
                    Component.text(bundle.getString("level.inv.start.noRewardsLeft")).color(NamedTextColor.RED)
            );
            return;
        }

        if(player.getInventory().firstEmpty() == -1){
            player.sendMessage(
                    Component.text(bundle.getString("level.inv.start.noSpace")).color(NamedTextColor.RED)
            );
            return;
        }

        new InventoryDrumroll(player, type);
    }
}
