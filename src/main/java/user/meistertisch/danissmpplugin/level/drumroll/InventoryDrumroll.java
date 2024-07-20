package user.meistertisch.danissmpplugin.level.drumroll;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;
import user.meistertisch.danissmpplugin.Main;
import user.meistertisch.danissmpplugin.files.FilePlayer;
import user.meistertisch.danissmpplugin.level.types.LevelType;
import user.meistertisch.danissmpplugin.level.types.adventure.RewardsLevelingAdventure;

import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;

public class InventoryDrumroll {
    Player player;
    Inventory inv;
    ResourceBundle bundle;
    ScheduledExecutorService service;
    ScheduledFuture scheduledFuture;
    int count = 0;


    public InventoryDrumroll(Player player, LevelType levelType) {
        service = Executors.newSingleThreadScheduledExecutor();
        this.player = player;
        bundle = ResourceBundle.getBundle("language_" + FilePlayer.getConfig().getString(player.getName() + ".lang"));
        inv = Bukkit.createInventory(player, 9*3, Component.text(bundle.getString("level.inv.drumroll.title")));
        ItemStack greenGlass = new ItemStack(Material.LIME_STAINED_GLASS_PANE);
        inv.setItem(4, greenGlass);
        inv.setItem(22, greenGlass);


        switch (levelType){
            case ADVENTURE -> {
                RewardsLevelingAdventure[] rewards = new RewardsLevelingAdventure[9];
//                player.openInventory(inv);
                service.scheduleAtFixedRate(() -> {
                    count++;

                    for (int i = 0; i < rewards.length; i++) {
                        rewards[i + 1] = rewards[i];
                    }
                    rewards[0] = RewardsLevelingAdventure.getNextItem();

                    for (int i = 0; i < rewards.length; i++) {
                        ItemStack item = new ItemStack(rewards[i].getMaterial(), rewards[i].getAmount());
                        ItemMeta meta = item.getItemMeta();
                        meta.displayName(Component.text(bundle.getString(rewards[i].getName())));
                        meta.lore(List.of(Component.text(bundle.getString(rewards[i].getDescription()))));
                        item.setItemMeta(meta);
                        System.out.println(item);
                        inv.setItem(17 - i, item);
                    }
                    Bukkit.getScheduler().runTask(Main.getPlugin(), () -> player.openInventory(inv));

                    if(count > 10)
                        scheduledFuture.cancel(true);
                }, 0,2, java.util.concurrent.TimeUnit.SECONDS);
            }
        }


    }
}
