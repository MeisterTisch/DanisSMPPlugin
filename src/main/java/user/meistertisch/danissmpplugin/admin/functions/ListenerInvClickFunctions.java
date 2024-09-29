package user.meistertisch.danissmpplugin.admin.functions;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextReplacementConfig;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import user.meistertisch.danissmpplugin.Main;
import user.meistertisch.danissmpplugin.files.FilePlayer;

import java.util.ResourceBundle;

public class ListenerInvClickFunctions implements Listener {

    @EventHandler
    public void invClick(InventoryClickEvent event){
        if(!(event.getWhoClicked() instanceof Player player))
            return;

        ResourceBundle bundle = ResourceBundle.getBundle("language_" + FilePlayer.getConfig().getString(player.getName() + ".lang"));
        String title = bundle.getString("functionTypes.inv.title");

        if(!player.getOpenInventory().title().equals(Component.text(title))){
            return;
        }

        event.setCancelled(true);
        ItemStack item = event.getCurrentItem();
        if(item == null){
            return;
        }

        Material material = item.getType();
        FunctionTypes type = null;
        boolean isEnabled = false;

        if(material == Material.LIME_STAINED_GLASS_PANE || material == Material.RED_STAINED_GLASS_PANE){
            if(event.getInventory().getItem(event.getSlot()-9) == null)
                return;

            isEnabled = material == Material.LIME_STAINED_GLASS_PANE;

            material = event.getInventory().getItem(event.getSlot()-9).getType();
            for(FunctionTypes funcType : FunctionTypes.values()){
                if(funcType.getMaterial() == material){
                    type = funcType;
                    break;
                }
            }
        }

        if(type == null)
            return;

        Main.getPlugin().getConfig().set(type.getConfig(), !isEnabled);
        Main.getPlugin().saveConfig();
        Main.getPlugin().reloadConfig();

        player.closeInventory();

        if(!isEnabled){
            player.sendMessage(
                    Component.text(bundle.getString("functionTypes.enabled.hasBeen")).replaceText(TextReplacementConfig.builder()
                                    .match("%type%").replacement(bundle.getString(type.getName())).build()).color(NamedTextColor.GREEN)
            );

        } else {
            player.sendMessage(
                    Component.text(bundle.getString("functionTypes.disabled.hasBeen")).replaceText(TextReplacementConfig.builder()
                            .match("%type%").replacement(bundle.getString(type.getName())).build()).color(NamedTextColor.RED)
            );
        }

        player.sendMessage(
                Component.text(bundle.getString("functionTypes.changedRestart")).color(NamedTextColor.RED)
        );
    }
}
