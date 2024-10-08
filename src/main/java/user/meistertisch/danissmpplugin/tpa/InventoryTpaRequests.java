package user.meistertisch.danissmpplugin.tpa;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import user.meistertisch.danissmpplugin.Main;
import user.meistertisch.danissmpplugin.files.FilePlayer;

import java.util.List;
import java.util.ResourceBundle;

public class InventoryTpaRequests {
    Player player;
    Inventory inv;
    ResourceBundle bundle;
    ItemStack accept;
    ItemStack decline;
    boolean toTarget;
    List<Player> requests;

    public InventoryTpaRequests(Player player, boolean toTarget) {
        this.player = player;
        this.toTarget = toTarget;
        bundle = ResourceBundle.getBundle("language_" + FilePlayer.getConfig().getString(player.getName() + ".lang"));
        inv = Bukkit.createInventory(player, 9*3, Component.text(bundle.getString("tpa.inv.title")));

        if(toTarget) {
            requests = Main.getManagerTPA().getRequestedTo(player);
        } else {
            requests = Main.getManagerTPA().getRequestedHere(player);
        }

        if(requests.isEmpty())
            return;

        setModeItemStack(bundle);

        for(int i = 0; i < 9; i++){
            if(requests.size() <= i)
                break;
            Player target = requests.get(i);

            //TODO: Make player heads to players
            ItemStack item = new ItemStack(Material.PLAYER_HEAD);
            ItemMeta meta = item.getItemMeta();
            meta.displayName(Component.text(bundle.getString(target.getName())));
            item.setItemMeta(meta);
            inv.addItem(item);

            inv.setItem(i+9, accept);
            inv.setItem(i+18, decline);
        }
    }

    private void setModeItemStack(ResourceBundle bundle){
        decline = new ItemStack(Material.RED_STAINED_GLASS_PANE);
        ItemMeta meta = decline.getItemMeta();
        meta.displayName(Component.text(bundle.getString("tpa.decline")));
        meta.lore(List.of(Component.text(bundle.getString("tpa.decline.desc"))));
        decline.setItemMeta(meta);

        accept = new ItemStack(Material.LIME_STAINED_GLASS_PANE);
        meta = accept.getItemMeta();
        meta.displayName(Component.text(bundle.getString("tpa.accept")));
        meta.lore(List.of(Component.text(bundle.getString("tpa.accept.desc"))));
        accept.setItemMeta(meta);
    }
}
