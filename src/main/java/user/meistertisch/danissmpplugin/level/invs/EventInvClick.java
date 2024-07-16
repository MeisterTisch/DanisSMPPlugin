package user.meistertisch.danissmpplugin.level.invs;

import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import user.meistertisch.danissmpplugin.files.FilePlayer;
import user.meistertisch.danissmpplugin.level.types.LevelType;
import user.meistertisch.danissmpplugin.level.types.adventure.InventoryLevelsAdventure;
import user.meistertisch.danissmpplugin.level.types.building.InventoryLevelsBuilding;
import user.meistertisch.danissmpplugin.level.types.combat.InventoryLevelsCombat;
import user.meistertisch.danissmpplugin.level.types.farming.InventoryLevelsFarming;
import user.meistertisch.danissmpplugin.level.types.magic.InventoryLevelsMagic;
import user.meistertisch.danissmpplugin.level.types.mining.InventoryLevelsMining;
import user.meistertisch.danissmpplugin.level.types.trading.InventoryLevelsTrading;

import java.util.ResourceBundle;

public class EventInvClick implements Listener {
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
        switch (type){
            case COMBAT -> new InventoryLevelsCombat(player);
            case MINING -> new InventoryLevelsMining(player);
            case FARMING -> new InventoryLevelsFarming(player);
            case ADVENTURE -> new InventoryLevelsAdventure(player);
            case MAGIC -> new InventoryLevelsMagic(player);
            case TRADING -> new InventoryLevelsTrading(player);
            case BUILDING -> new InventoryLevelsBuilding(player);
        }
    }
}
