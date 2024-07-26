package user.meistertisch.danissmpplugin.level.invs.start;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import user.meistertisch.danissmpplugin.files.FileLevels;
import user.meistertisch.danissmpplugin.files.FilePlayer;
import user.meistertisch.danissmpplugin.level.types.LevelType;

import java.util.List;
import java.util.ResourceBundle;

public class InventoryLevelsStart {
    public InventoryLevelsStart(Player player) {
        ResourceBundle bundle = ResourceBundle.getBundle("language_" + FilePlayer.getConfig().getString(player.getName() + ".lang"));
        String title = bundle.getString("level.inv.start.title");

        Inventory start = Bukkit.createInventory(null, 9, Component.text(title));

        for(LevelType type : LevelType.values()){
            ItemStack item = new ItemStack(type.getInvStartItem());
            ItemMeta meta = item.getItemMeta();
            meta.itemName(Component.text(bundle.getString("level." + type.name().toLowerCase())).color(type.getColor()));

            meta.itemName(Component.text(
                    bundle.getString("level." + type.name().toLowerCase()) + " | " + bundle.getString("level") + ": " + (int) FileLevels.getConfig().getDouble(player.getName() + ".level." + type.name().toLowerCase())
                    )
                    .color(type.getColor()));

            List<? extends Component> lore = List.of(
                    Component.text(bundle.getString("level.inv.start.rewardsLeft")
                            + (int) FileLevels.getConfig().getDouble(player.getName() + ".rewardsLeft." + type.name().toLowerCase())
                    )
            );

            meta.lore(lore);

            item.setItemMeta(meta);
            start.addItem(item);
        }

        player.openInventory(start);
    }
}
