package user.meistertisch.danissmpplugin.admin.functions;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import user.meistertisch.danissmpplugin.Main;
import user.meistertisch.danissmpplugin.files.FilePlayer;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.UUID;

public class InventoryFunctions {
    Player player;
    Inventory inv;
    ResourceBundle bundle;
    int page;
    ItemStack disabled;
    ItemStack enabled;
    ItemStack nextSite;
    ItemStack previousSite;
    FileConfiguration config = Main.getPlugin().getConfig();

    public InventoryFunctions(Player player, int page) {
        this.player = player;
        this.page = page;
        bundle = ResourceBundle.getBundle("language_" + FilePlayer.getConfig().getString(player.getName() + ".lang"));
        inv = Bukkit.createInventory(player, 9*3, Component.text(bundle.getString("functionTypes.inv.title")));
        setModeItemStack(bundle);
        setArrowItemStack(bundle);

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

        if(page > 1)
            inv.setItem(18, previousSite);

        if(FunctionTypes.values().length > 9*page)
            inv.setItem(26, nextSite);

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

    private void setArrowItemStack(ResourceBundle bundle){
        nextSite = new ItemStack(Material.ARROW);
        ItemMeta nextSiteMeta = nextSite.getItemMeta();
        nextSiteMeta.displayName(Component.text(bundle.getString("functionTypes.nextSite")));
        nextSite.setItemMeta(nextSiteMeta);


        previousSite = new ItemStack(Material.ARROW);
        ItemMeta previousSiteMeta = nextSite.getItemMeta();
        previousSiteMeta.displayName(Component.text(bundle.getString("functionTypes.previousSite")));
        previousSite.setItemMeta(previousSiteMeta);
    }
}
