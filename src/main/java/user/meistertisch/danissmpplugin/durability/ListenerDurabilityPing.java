package user.meistertisch.danissmpplugin.durability;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemDamageEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import user.meistertisch.danissmpplugin.Main;
import user.meistertisch.danissmpplugin.files.FilePlayer;

import java.util.ResourceBundle;

public class ListenerDurabilityPing implements Listener {
    @EventHandler
    public void itemUse(PlayerItemDamageEvent event){
        if(!Main.getPlugin().getConfig().getBoolean("durabilityPing.use")){
            return;
        }

        Player player = event.getPlayer();
        ItemStack item = event.getItem();

        if(!FilePlayer.getConfig().getBoolean(player.getName() + ".durabilityPing")){
            return;
        }

        ResourceBundle bundle = ResourceBundle.getBundle("language_" + FilePlayer.getConfig().getString(player.getName() + ".lang"));
        Damageable damageMeta = (Damageable) item.getItemMeta();
        int maxDurability = item.getType().getMaxDurability();

        if(!damageMeta.hasDamage()){
            return;
        }

        int currentDurability = maxDurability - damageMeta.getDamage();

        if(currentDurability < maxDurability * Main.getPlugin().getConfig().getDouble("durabilityPing.threshold")){
            player.sendActionBar(Component.text(bundle.getString("commands.durability")).color(NamedTextColor.GOLD));
        }
    }
}
