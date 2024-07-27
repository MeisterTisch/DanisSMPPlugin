package user.meistertisch.danissmpplugin.level.invs.drumroll;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextReplacementConfig;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitTask;
import user.meistertisch.danissmpplugin.Main;
import user.meistertisch.danissmpplugin.files.FilePlayer;
import user.meistertisch.danissmpplugin.level.types.LevelType;
import user.meistertisch.danissmpplugin.level.types.adventure.RewardsLevelingAdventure;
import user.meistertisch.danissmpplugin.level.types.building.RewardsLevelingBuilding;
import user.meistertisch.danissmpplugin.level.types.combat.RewardsLevelingCombat;
import user.meistertisch.danissmpplugin.level.types.farming.RewardsLevelingFarming;
import user.meistertisch.danissmpplugin.level.types.mining.RewardsLevelingMining;
import user.meistertisch.danissmpplugin.level.types.trading.RewardsLevelingTrading;

import java.util.List;
import java.util.ResourceBundle;

public class InventoryDrumroll {
    Player player;
    Inventory inv;
    ResourceBundle bundle;
    int count = 0;
    BukkitTask task;

    public InventoryDrumroll(Player player, LevelType levelType) {
        this.player = player;
        bundle = ResourceBundle.getBundle("language_" + FilePlayer.getConfig().getString(player.getName() + ".lang"));
        inv = Bukkit.createInventory(player, 9*3, Component.text(bundle.getString("level.inv.drumroll.title")));
        ItemStack greenGlass = new ItemStack(Material.LIME_STAINED_GLASS_PANE);
        inv.setItem(4, greenGlass);
        inv.setItem(22, greenGlass);


        switch (levelType){
            case ADVENTURE -> {
                RewardsLevelingAdventure[] rewards = new RewardsLevelingAdventure[9];
                player.openInventory(inv);

                task = Bukkit.getScheduler().runTaskTimer(Main.getPlugin(), () -> {
                    count++;
                    int period = 1;

                    if(count >= 60 && count <= 100){
                        period = 2;
                    }
                    if(count >= 100 && count <= 140){
                        period = 5;
                    }
                    if(count >= 140 && count <= 180){
                        period = 10;
                    }
                    if(count >= 180 && count <= 200){
                        period = 20;
                    }
                    if(count >= 200){
                        period = 9999;
                    }
                    if(count >= 220){
                        task.cancel();
                        givePlayerReward(rewards[4]);
                        return;
                    }
//                    player.openInventory(inv);

                    if(count % period == 0) {
                        RewardsLevelingAdventure lastReward = RewardsLevelingAdventure.getNextItem();
                        for (int i = 0; i < 9; i++) {
                            RewardsLevelingAdventure thisReward = rewards[i];
                            rewards[i] = lastReward;
                            lastReward = thisReward;
                        }

                        for (int i = 0; i < 9; i++) {
                            RewardsLevelingAdventure reward = rewards[i];
                            if(reward == null) continue;

                            ItemStack item = new ItemStack(reward.getMaterial(), reward.getAmount());
                            ItemMeta meta = item.getItemMeta();
                            meta.displayName(Component.text(bundle.getString(reward.getName())).color(reward.getRarity().getColor()));
                            meta.lore(List.of(Component.text(bundle.getString(reward.getDescription()))));
                            item.setItemMeta(meta);

                            inv.setItem(17 - i, item);
                        }
                    }
                }, 0, 1);

            }
            case TRADING -> {
                RewardsLevelingTrading[] rewards = new RewardsLevelingTrading[9];
                player.openInventory(inv);

                task = Bukkit.getScheduler().runTaskTimer(Main.getPlugin(), () -> {
                    count++;
                    int period = 1;

                    if(count >= 60 && count <= 100){
                        period = 2;
                    }
                    if(count >= 100 && count <= 140){
                        period = 5;
                    }
                    if(count >= 140 && count <= 180){
                        period = 10;
                    }
                    if(count >= 180 && count <= 200){
                        period = 20;
                    }
                    if(count >= 200){
                        period = 9999;
                    }
                    if(count >= 220){
                        task.cancel();
                        givePlayerReward(rewards[4]);
                        return;
                    }
//                    player.openInventory(inv);

                    if(count % period == 0) {
                        RewardsLevelingTrading lastReward = RewardsLevelingTrading.getNextItem();
                        for (int i = 0; i < 9; i++) {
                            RewardsLevelingTrading thisReward = rewards[i];
                            rewards[i] = lastReward;
                            lastReward = thisReward;
                        }

                        for (int i = 0; i < 9; i++) {
                            RewardsLevelingTrading reward = rewards[i];
                            if(reward == null) continue;

                            ItemStack item = new ItemStack(reward.getMaterial(), reward.getAmount());
                            ItemMeta meta = item.getItemMeta();
                            meta.displayName(Component.text(bundle.getString(reward.getName())).color(reward.getRarity().getColor()));
                            meta.lore(List.of(Component.text(bundle.getString(reward.getDescription()))));
                            item.setItemMeta(meta);

                            inv.setItem(17 - i, item);
                        }
                    }
                }, 0, 1);

            }
            case MINING -> {
                RewardsLevelingMining[] rewards = new RewardsLevelingMining[9];
                player.openInventory(inv);

                task = Bukkit.getScheduler().runTaskTimer(Main.getPlugin(), () -> {
                    count++;
                    int period = 1;

                    if(count >= 60 && count <= 100){
                        period = 2;
                    }
                    if(count >= 100 && count <= 140){
                        period = 5;
                    }
                    if(count >= 140 && count <= 180){
                        period = 10;
                    }
                    if(count >= 180 && count <= 200){
                        period = 20;
                    }
                    if(count >= 200){
                        period = 9999;
                    }
                    if(count >= 220){
                        task.cancel();
                        givePlayerReward(rewards[4]);
                        return;
                    }
//                    player.openInventory(inv);

                    if(count % period == 0) {
                        RewardsLevelingMining lastReward = RewardsLevelingMining.getNextItem();
                        for (int i = 0; i < 9; i++) {
                            RewardsLevelingMining thisReward = rewards[i];
                            rewards[i] = lastReward;
                            lastReward = thisReward;
                        }

                        for (int i = 0; i < 9; i++) {
                            RewardsLevelingMining reward = rewards[i];
                            if(reward == null) continue;

                            ItemStack item = new ItemStack(reward.getMaterial(), reward.getAmount());
                            ItemMeta meta = item.getItemMeta();
                            meta.displayName(Component.text(bundle.getString(reward.getName())).color(reward.getRarity().getColor()));
                            meta.lore(List.of(Component.text(bundle.getString(reward.getDescription()))));
                            item.setItemMeta(meta);

                            inv.setItem(17 - i, item);
                        }
                    }
                }, 0, 1);

            }
            case COMBAT -> {
                RewardsLevelingCombat[] rewards = new RewardsLevelingCombat[9];
                player.openInventory(inv);

                task = Bukkit.getScheduler().runTaskTimer(Main.getPlugin(), () -> {
                    count++;
                    int period = 1;

                    if(count >= 60 && count <= 100){
                        period = 2;
                    }
                    if(count >= 100 && count <= 140){
                        period = 5;
                    }
                    if(count >= 140 && count <= 180){
                        period = 10;
                    }
                    if(count >= 180 && count <= 200){
                        period = 20;
                    }
                    if(count >= 200){
                        period = 9999;
                    }
                    if(count >= 220){
                        task.cancel();
                        givePlayerReward(rewards[4]);
                        return;
                    }
//                    player.openInventory(inv);

                    if(count % period == 0) {
                        RewardsLevelingCombat lastReward = RewardsLevelingCombat.getNextItem();
                        for (int i = 0; i < 9; i++) {
                            RewardsLevelingCombat thisReward = rewards[i];
                            rewards[i] = lastReward;
                            lastReward = thisReward;
                        }

                        for (int i = 0; i < 9; i++) {
                            RewardsLevelingCombat reward = rewards[i];
                            if(reward == null) continue;

                            ItemStack item = new ItemStack(reward.getMaterial(), reward.getAmount());
                            ItemMeta meta = item.getItemMeta();
                            meta.displayName(Component.text(bundle.getString(reward.getName())).color(reward.getRarity().getColor()));
                            meta.lore(List.of(Component.text(bundle.getString(reward.getDescription()))));
                            item.setItemMeta(meta);

                            inv.setItem(17 - i, item);
                        }
                    }
                }, 0, 1);

            }
            case BUILDING -> {
                RewardsLevelingBuilding[] rewards = new RewardsLevelingBuilding[9];
                player.openInventory(inv);

                task = Bukkit.getScheduler().runTaskTimer(Main.getPlugin(), () -> {
                    count++;
                    int period = 1;

                    if(count >= 60 && count <= 100){
                        period = 2;
                    }
                    if(count >= 100 && count <= 140){
                        period = 5;
                    }
                    if(count >= 140 && count <= 180){
                        period = 10;
                    }
                    if(count >= 180 && count <= 200){
                        period = 20;
                    }
                    if(count >= 200){
                        period = 9999;
                    }
                    if(count >= 220){
                        task.cancel();
                        givePlayerReward(rewards[4]);
                        return;
                    }
//                    player.openInventory(inv);

                    if(count % period == 0) {
                        RewardsLevelingBuilding lastReward = RewardsLevelingBuilding.getNextItem();
                        for (int i = 0; i < 9; i++) {
                            RewardsLevelingBuilding thisReward = rewards[i];
                            rewards[i] = lastReward;
                            lastReward = thisReward;
                        }

                        for (int i = 0; i < 9; i++) {
                            RewardsLevelingBuilding reward = rewards[i];
                            if(reward == null) continue;

                            ItemStack item = new ItemStack(reward.getMaterial(), reward.getAmount());
                            ItemMeta meta = item.getItemMeta();
                            meta.displayName(Component.text(bundle.getString(reward.getName())).color(reward.getRarity().getColor()));
                            meta.lore(List.of(Component.text(bundle.getString(reward.getDescription()))));
                            item.setItemMeta(meta);

                            inv.setItem(17 - i, item);
                        }
                    }
                }, 0, 1);

            }
            case FARMING -> {
                RewardsLevelingFarming[] rewards = new RewardsLevelingFarming[9];
                player.openInventory(inv);

                task = Bukkit.getScheduler().runTaskTimer(Main.getPlugin(), () -> {
                    count++;
                    int period = 1;

                    if(count >= 60 && count <= 100){
                        period = 2;
                    }
                    if(count >= 100 && count <= 140){
                        period = 5;
                    }
                    if(count >= 140 && count <= 180){
                        period = 10;
                    }
                    if(count >= 180 && count <= 200){
                        period = 20;
                    }
                    if(count >= 200){
                        period = 9999;
                    }
                    if(count >= 220){
                        task.cancel();
                        givePlayerReward(rewards[4]);
                        return;
                    }
//                    player.openInventory(inv);

                    if(count % period == 0) {
                        RewardsLevelingFarming lastReward = RewardsLevelingFarming.getNextItem();
                        for (int i = 0; i < 9; i++) {
                            RewardsLevelingFarming thisReward = rewards[i];
                            rewards[i] = lastReward;
                            lastReward = thisReward;
                        }

                        for (int i = 0; i < 9; i++) {
                            RewardsLevelingFarming reward = rewards[i];
                            if(reward == null) continue;

                            ItemStack item = new ItemStack(reward.getMaterial(), reward.getAmount());
                            ItemMeta meta = item.getItemMeta();
                            meta.displayName(Component.text(bundle.getString(reward.getName())).color(reward.getRarity().getColor()));
                            meta.lore(List.of(Component.text(bundle.getString(reward.getDescription()))));
                            item.setItemMeta(meta);

                            inv.setItem(17 - i, item);
                        }
                    }
                }, 0, 1);

            }
        }


    }

    private void givePlayerReward(Object obj){
        ItemStack item = inv.getItem(13);
        if(item == null) return;
        player.getInventory().addItem(item);

        Component rarity;
        Component itemName;

        if(obj instanceof RewardsLevelingAdventure reward){
            rarity = Component.text(bundle.getString(reward.getRarity().getName()))
                    .color(reward.getRarity().getColor())
                    .decoration(TextDecoration.BOLD, true);
            itemName = item.displayName()
                    .color(reward.getRarity().getColor())
                    .decoration(TextDecoration.BOLD, true);
        } else if(obj instanceof RewardsLevelingTrading reward){
            rarity = Component.text(bundle.getString(reward.getRarity().getName()))
                    .color(reward.getRarity().getColor())
                    .decoration(TextDecoration.BOLD, true);
            itemName = item.displayName()
                    .color(reward.getRarity().getColor())
                    .decoration(TextDecoration.BOLD, true);
        } else if(obj instanceof RewardsLevelingMining reward){
            rarity = Component.text(bundle.getString(reward.getRarity().getName()))
                    .color(reward.getRarity().getColor())
                    .decoration(TextDecoration.BOLD, true);
            itemName = item.displayName()
                    .color(reward.getRarity().getColor())
                    .decoration(TextDecoration.BOLD, true);
        }  else if(obj instanceof RewardsLevelingCombat reward){
            rarity = Component.text(bundle.getString(reward.getRarity().getName()))
                    .color(reward.getRarity().getColor())
                    .decoration(TextDecoration.BOLD, true);
            itemName = item.displayName()
                    .color(reward.getRarity().getColor())
                    .decoration(TextDecoration.BOLD, true);
        } else if(obj instanceof RewardsLevelingBuilding reward){
            rarity = Component.text(bundle.getString(reward.getRarity().getName()))
                    .color(reward.getRarity().getColor())
                    .decoration(TextDecoration.BOLD, true);
            itemName = item.displayName()
                    .color(reward.getRarity().getColor())
                    .decoration(TextDecoration.BOLD, true);
        }  else if(obj instanceof RewardsLevelingFarming reward){
            rarity = Component.text(bundle.getString(reward.getRarity().getName()))
                    .color(reward.getRarity().getColor())
                    .decoration(TextDecoration.BOLD, true);
            itemName = item.displayName()
                    .color(reward.getRarity().getColor())
                    .decoration(TextDecoration.BOLD, true);
        }
        else {
            return;
        }

        player.closeInventory();

        Component text = Component.text(bundle.getString("level.rewarding"))
                .color(TextColor.color(NamedTextColor.DARK_GREEN))
                .replaceText(TextReplacementConfig.builder().match("%rarity%").replacement(rarity).build())
                .replaceText(TextReplacementConfig.builder().match("%item%").replacement(itemName).build());

        player.sendMessage(text);
    }
}
