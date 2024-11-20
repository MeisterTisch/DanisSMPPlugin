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
import user.meistertisch.danissmpplugin.files.FileLevels;
import user.meistertisch.danissmpplugin.files.FilePlayer;
import user.meistertisch.danissmpplugin.level.types.LevelType;
import user.meistertisch.danissmpplugin.level.types.adventure.RewardsLevelingAdventure;
import user.meistertisch.danissmpplugin.level.types.building.RewardsLevelingBuilding;
import user.meistertisch.danissmpplugin.level.types.combat.RewardsLevelingCombat;
import user.meistertisch.danissmpplugin.level.types.farming.RewardsLevelingFarming;
import user.meistertisch.danissmpplugin.level.types.magic.RewardsLevelingMagic;
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
        ItemStack common = new ItemStack(Material.LIGHT_GRAY_STAINED_GLASS_PANE);
        ItemStack uncommon = new ItemStack(Material.LIME_STAINED_GLASS_PANE);
        ItemStack rare = new ItemStack(Material.BLUE_STAINED_GLASS_PANE);
        ItemStack epic = new ItemStack(Material.PURPLE_STAINED_GLASS_PANE);
        ItemStack legendary = new ItemStack(Material.ORANGE_STAINED_GLASS_PANE);
        ItemStack black = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
        ItemStack white = new ItemStack(Material.WHITE_STAINED_GLASS_PANE);
        ItemStack gray = new ItemStack(Material.GRAY_STAINED_GLASS_PANE);
        ItemStack light_gray = new ItemStack(Material.LIGHT_GRAY_STAINED_GLASS_PANE);

        ItemStack greenGlass = new ItemStack(Material.LIME_STAINED_GLASS_PANE);
        inv.setItem(4, greenGlass);
        inv.setItem(22, greenGlass);
        inv.setItem(0, black);
        inv.setItem(1, gray);
        inv.setItem(2, light_gray);
        inv.setItem(3, white);
        inv.setItem(5, white);
        inv.setItem(6, light_gray);
        inv.setItem(7, gray);
        inv.setItem(8, black);
        inv.setItem(18, black);
        inv.setItem(19, gray);
        inv.setItem(20, light_gray);
        inv.setItem(21, white);
        inv.setItem(23, white);
        inv.setItem(24, light_gray);
        inv.setItem(25, gray);
        inv.setItem(26, black);

        FileLevels.getConfig().set(player.getName() + ".rewardsLeft." + levelType.name().toLowerCase(),
                FileLevels.getConfig().getInt(player.getName() + ".rewardsLeft." + levelType.name().toLowerCase())-1);
        FileLevels.saveConfig();


        switch (levelType){
            case ADVENTURE -> {
                RewardsLevelingAdventure[] rewards = new RewardsLevelingAdventure[9];

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
                    player.openInventory(inv);

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
                    player.openInventory(inv);

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

                        switch (rewards[4].getRarity()){
                            case COMMON -> {
                                inv.setItem(4, common);
                                inv.setItem(22, common);
                            }
                            case UNCOMMON -> {
                                inv.setItem(4, uncommon);
                                inv.setItem(22, uncommon);
                            }
                            case RARE -> {
                                inv.setItem(4, rare);
                                inv.setItem(22, rare);
                            }
                            case EPIC -> {
                                inv.setItem(4, epic);
                                inv.setItem(22, epic);
                            }
                            case LEGENDARY -> {
                                inv.setItem(4, legendary);
                                inv.setItem(22, legendary);
                            }
                        }
                    }
                }, 0, 1);

            }
            case MINING -> {
                RewardsLevelingMining[] rewards = new RewardsLevelingMining[9];

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
                    player.openInventory(inv);

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
                    player.openInventory(inv);

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
                    player.openInventory(inv);

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
                    player.openInventory(inv);

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
            case MAGIC -> {
                RewardsLevelingMagic[] rewards = new RewardsLevelingMagic[9];

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
                    player.openInventory(inv);

                    if(count % period == 0) {
                        RewardsLevelingMagic lastReward = RewardsLevelingMagic.getNextItem();
                        for (int i = 0; i < 9; i++) {
                            RewardsLevelingMagic thisReward = rewards[i];
                            rewards[i] = lastReward;
                            lastReward = thisReward;
                        }

                        for (int i = 0; i < 9; i++) {
                            RewardsLevelingMagic reward = rewards[i];
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
        WinRarityTypes rarityType;

        switch (obj) {
            case RewardsLevelingAdventure reward -> {
                rarityType = reward.getRarity();
                rarity = Component.text(bundle.getString(reward.getRarity().getName()))
                        .color(reward.getRarity().getColor())
                        .decoration(TextDecoration.BOLD, true);
                itemName = item.displayName()
                        .color(reward.getRarity().getColor())
                        .decoration(TextDecoration.BOLD, true);
            }
            case RewardsLevelingTrading reward -> {
                rarityType = reward.getRarity();
                rarity = Component.text(bundle.getString(reward.getRarity().getName()))
                        .color(reward.getRarity().getColor())
                        .decoration(TextDecoration.BOLD, true);
                itemName = item.displayName()
                        .color(reward.getRarity().getColor())
                        .decoration(TextDecoration.BOLD, true);
            }
            case RewardsLevelingMining reward -> {
                rarityType = reward.getRarity();
                rarity = Component.text(bundle.getString(reward.getRarity().getName()))
                        .color(reward.getRarity().getColor())
                        .decoration(TextDecoration.BOLD, true);
                itemName = item.displayName()
                        .color(reward.getRarity().getColor())
                        .decoration(TextDecoration.BOLD, true);
            }
            case RewardsLevelingCombat reward -> {
                rarityType = reward.getRarity();
                rarity = Component.text(bundle.getString(reward.getRarity().getName()))
                        .color(reward.getRarity().getColor())
                        .decoration(TextDecoration.BOLD, true);
                itemName = item.displayName()
                        .color(reward.getRarity().getColor())
                        .decoration(TextDecoration.BOLD, true);
            }
            case RewardsLevelingBuilding reward -> {
                rarityType = reward.getRarity();
                rarity = Component.text(bundle.getString(reward.getRarity().getName()))
                        .color(reward.getRarity().getColor())
                        .decoration(TextDecoration.BOLD, true);
                itemName = item.displayName()
                        .color(reward.getRarity().getColor())
                        .decoration(TextDecoration.BOLD, true);
            }
            case RewardsLevelingFarming reward -> {
                rarityType = reward.getRarity();
                rarity = Component.text(bundle.getString(reward.getRarity().getName()))
                        .color(reward.getRarity().getColor())
                        .decoration(TextDecoration.BOLD, true);
                itemName = item.displayName()
                        .color(reward.getRarity().getColor())
                        .decoration(TextDecoration.BOLD, true);
            }
            case RewardsLevelingMagic reward -> {
                rarityType = reward.getRarity();
                rarity = Component.text(bundle.getString(reward.getRarity().getName()))
                        .color(reward.getRarity().getColor())
                        .decoration(TextDecoration.BOLD, true);
                itemName = item.displayName()
                        .color(reward.getRarity().getColor())
                        .decoration(TextDecoration.BOLD, true);
            }
            case null, default -> {
                return;
            }
        }

        player.closeInventory();

        Component text = Component.text(bundle.getString("level.rewarding"))
                .color(TextColor.color(NamedTextColor.DARK_GREEN))
                .replaceText(TextReplacementConfig.builder().match("%rarity%").replacement(rarity).build())
                .replaceText(TextReplacementConfig.builder().match("%item%").replacement(itemName).build());

        player.sendMessage(text);

        if(!Main.getPlugin().getConfig().getBoolean("levelingSystem.announce." + rarityType.toString().toLowerCase()))
            return;

        for(Player onPlayer : Bukkit.getOnlinePlayers()){
            ResourceBundle tempBundle = ResourceBundle.getBundle("language_" + FilePlayer.getConfig().getString(onPlayer.getName() + ".lang"));

            Component announceText = Component.text(tempBundle.getString("level.rewarding.announce"))
                    .color(TextColor.color(NamedTextColor.GOLD))
                    .replaceText(TextReplacementConfig.builder().match("%rarity%").replacement(tempBundle.getString(rarityType.getName())).build())
                    .replaceText(TextReplacementConfig.builder().match("%item%").replacement(itemName).build())
                    .replaceText(TextReplacementConfig.builder().match("%player%").replacement(player.getName()).build());

            onPlayer.sendMessage(announceText);
        }
    }
}