package user.meistertisch.danissmpplugin.misc.thunderstormSummoner;

import com.destroystokyo.paper.event.player.PlayerLaunchProjectileEvent;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.LightningStrike;
import org.bukkit.entity.Player;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;


public class ListenerTridentThrow implements Listener {
    @EventHandler
    public void playerThrewTrident(PlayerLaunchProjectileEvent event){
        Player player = event.getPlayer();

        if(event.getProjectile().getType() != EntityType.TRIDENT)
            return;
        if(!event.getItemStack().hasItemMeta())
            return;
        ItemStack item = event.getItemStack();
        ItemMeta meta = item.getItemMeta();

        if(!meta.hasEnchant(Enchantment.CHANNELING))
            return;
        Damageable damageMeta = (Damageable) item.getItemMeta();

        if(damageMeta.getDamage() >= 250 * 0.6)
            return;

        if(player.getLocation().getBlockY() < 320 && player.getPitch() > -85)
            return;

        if(player.getWorld().isThundering())
            return;

        player.getWorld().setThundering(true);
        damageMeta.setDamage((int) (250 * 0.6 + 1));
        item.setItemMeta(damageMeta);
    }
}
