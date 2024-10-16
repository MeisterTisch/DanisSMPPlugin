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
import org.bukkit.inventory.meta.ItemMeta;
import user.meistertisch.danissmpplugin.Main;
import user.meistertisch.danissmpplugin.files.FilePlayer;

import java.util.List;
import java.util.ResourceBundle;

public class ListenerInvClickFunctions implements Listener {
    ItemStack disabled;
    ItemStack enabled;

    @EventHandler
    public void invClick(InventoryClickEvent event){
        if(!(event.getWhoClicked() instanceof Player player))
            return;

        ResourceBundle bundle = ResourceBundle.getBundle("language_" + FilePlayer.getConfig().getString(player.getName() + ".lang"));
        String title = bundle.getString("functionTypes.inv.title");
        setModeItemStack(bundle);

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

            if(isEnabled){
                event.getInventory().setItem(event.getSlot(), disabled);
            } else {
                event.getInventory().setItem(event.getSlot(), enabled);
            }
        } else if(material == Material.ARROW){
            if(item.getItemMeta().displayName().equals(Component.text(bundle.getString("functionTypes.nextSite")))){
                player.closeInventory();
                new InventoryFunctions(player, 2);
            } else if(item.getItemMeta().displayName().equals(Component.text(bundle.getString("functionTypes.previousSite")))){
                player.closeInventory();
                new InventoryFunctions(player, 1);
            }
        }


        if(type == null)
            return;

        Main.getPlugin().getConfig().set(type.getConfig(), !isEnabled);
        Main.getPlugin().saveConfig();
        Main.getPlugin().reloadConfig();

//        player.closeInventory();


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

//        player.sendMessage(
//                Component.text(bundle.getString("functionTypes.changedRestart")).color(NamedTextColor.RED)
//        );
    }

    private void setModeItemStack(ResourceBundle bundle){
        disabled = new ItemStack(Material.RED_STAINED_GLASS_PANE);
        ItemMeta meta = disabled.getItemMeta();
        meta.displayName(Component.text(bundle.getString("functionTypes.disabled")));
        meta.lore(List.of(Component.text(bundle.getString("functionTypes.disabled.desc"))));
        disabled.setItemMeta(meta);

        enabled = new ItemStack(Material.LIME_STAINED_GLASS_PANE);
        meta = enabled.getItemMeta();
        meta.displayName(Component.text(bundle.getString("functionTypes.enabled")));
        meta.lore(List.of(Component.text(bundle.getString("functionTypes.enabled.desc"))));
        enabled.setItemMeta(meta);
    }
}
