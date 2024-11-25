package user.meistertisch.danissmpplugin.misc;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextReplacementConfig;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import user.meistertisch.danissmpplugin.Main;
import user.meistertisch.danissmpplugin.files.FilePlayer;

import java.util.List;
import java.util.ResourceBundle;


public class ListenerPlayerDeathHeadDrop implements Listener {
    @EventHandler
    public void playerDeath(PlayerDeathEvent event){
        ItemStack head = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta meta = (SkullMeta) head.getItemMeta();
        meta.setOwningPlayer(event.getEntity());

        if(event.getEntity().getKiller() != null){
            ResourceBundle bundle = ResourceBundle.getBundle("language_" + FilePlayer.getConfig().getString(event.getEntity().getName() + ".lang"));

            Component killer = Component.text(event.getEntity().getKiller().getName()).color(TextColor.color(Main.getPrimaryColor()));
            Component player = Component.text(event.getEntity().getName()).color(TextColor.color(Main.getPrimaryColor()));
            Component message = Component.text(bundle.getString("playerHeadDrop")).color(TextColor.color(Main.getSecondaryColor()))
                    .replaceText(TextReplacementConfig.builder().match("%player%").replacement(player).build())
                    .replaceText(TextReplacementConfig.builder().match("%killer%").replacement(killer).build());

            List<Component> list = List.of(message);
            meta.lore(list);
        }

        head.setItemMeta(meta);

        event.getEntity().getWorld().dropItem(event.getPlayer().getLocation().add(0.5, 0, 0.5), head);
    }
}
