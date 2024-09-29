package user.meistertisch.danissmpplugin.admin.functions;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import user.meistertisch.danissmpplugin.Main;
import user.meistertisch.danissmpplugin.files.FilePlayer;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class InventoryFunctions {
    Player player;
    Inventory inv;
    ResourceBundle bundle;
    int page;
    ItemStack disabled;
    ItemStack enabled;
    FileConfiguration config = Main.getPlugin().getConfig();

    public InventoryFunctions(Player player, int page) {
        this.player = player;
        this.page = page;
        bundle = ResourceBundle.getBundle("language_" + FilePlayer.getConfig().getString(player.getName() + ".lang"));
        inv = Bukkit.createInventory(player, 9*3, Component.text(bundle.getString("functionTypes.inv.title")));
        setModeItemStack(bundle);

        List<FunctionTypes> functionsList = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            if(FunctionTypes.values().length <= i+9*(page-1))
                break;
            functionsList.add(i, FunctionTypes.values()[i+9*(page-1)]);
        }

        for(int i = 0; i < 9; i++){
            if(functionsList.size() <= i)
                break;
            FunctionTypes type = functionsList.get(i);

            ItemStack item = new ItemStack(type.getMaterial());
            ItemMeta meta = item.getItemMeta();
            meta.displayName(Component.text(bundle.getString(type.getName())));
            meta.lore(List.of(Component.text(bundle.getString(type.getDesc()))));
            item.setItemMeta(meta);
            inv.addItem(item);

            if(config.getBoolean(type.getConfig())){
                inv.setItem(i+9, enabled);
            } else {
                inv.setItem(i+9, disabled);
            }
        }

        player.openInventory(inv);
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
