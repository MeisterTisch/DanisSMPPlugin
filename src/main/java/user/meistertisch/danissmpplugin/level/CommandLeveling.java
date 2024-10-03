package user.meistertisch.danissmpplugin.level;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextReplacementConfig;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import user.meistertisch.danissmpplugin.Main;
import user.meistertisch.danissmpplugin.files.FileAdmins;
import user.meistertisch.danissmpplugin.files.FileLevels;
import user.meistertisch.danissmpplugin.files.FilePlayer;
import user.meistertisch.danissmpplugin.level.invs.drumroll.InventoryDrumroll;
import user.meistertisch.danissmpplugin.level.invs.start.InventoryLevelsStart;
import user.meistertisch.danissmpplugin.level.types.LevelType;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class CommandLeveling implements TabExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if(commandSender instanceof Player player){
            ResourceBundle bundle = ResourceBundle.getBundle("language_" + FilePlayer.getConfig().getString(player.getName() + ".lang"));

            if(!Main.getPlugin().getConfig().getBoolean("levelingSystem.use")){
                player.sendMessage(
                        Component.text(bundle.getString("level.disabled"))
                                .color(NamedTextColor.RED)
                );
                return true;
            }

            switch (strings.length){
                case 0 -> {
                    new InventoryLevelsStart(player);
                }
                case 1 -> {
                    if(strings[0].equalsIgnoreCase("rewards")){
                        Component text = Component.text(bundle.getString("commands.level.rewardsLeft")).color(NamedTextColor.DARK_GREEN);
                        boolean allZero = true;

                        for(LevelType type : LevelType.values()){
                            if(!(FileLevels.getConfig().getInt(player.getName() + ".rewardsLeft." + type.name().toLowerCase()) == 0)){
                                allZero = false;
                                Component minus = Component.text("\n - ").color(NamedTextColor.DARK_GRAY);
                                Component reminderText = Component.text(bundle.getString("level." + type.toString().toLowerCase()) +
                                                ": " + FileLevels.getConfig().getInt(player.getName() + ".rewardsLeft." + type.name().toLowerCase()))
                                        .color(type.getColor());
                                minus = minus.append(reminderText);
                                text = text.append(minus);
                            }
                        }

                        if(allZero){
                            text = Component.text(bundle.getString("commands.level.noRewardsLeft"))
                                    .color(NamedTextColor.RED);
                        }

                        player.sendMessage(text);
                    }
                    else if(strings[0].equalsIgnoreCase("levels")){
                        Component text = Component.text(bundle.getString("commands.level.checkAll.other")).color(NamedTextColor.DARK_GREEN)
                                .replaceText(TextReplacementConfig.builder().match("%target%")
                                        .replacement(
                                                Component.text(player.getName())
                                                        .color(NamedTextColor.GOLD)
                                                        .decoration(TextDecoration.ITALIC, true)
                                        ).build());

                        for (LevelType type : LevelType.values()) {
                            Component minus = Component.text("\n - ").color(NamedTextColor.DARK_GRAY);
                            Component level = Component.text(type.name() + ": " +
                                    FileLevels.getConfig().getInt(player.getName() + ".level." + type.name().toLowerCase())).color(type.getColor());
                            minus = minus.append(level);
                            text = text.append(minus);
                        }

                        player.sendMessage(text);
                        return true;
                    }
                    else {
                        LevelType levelType;

                        try {
                            levelType = LevelType.valueOf(strings[0].toUpperCase());
                        } catch (IllegalArgumentException e) {
                            player.sendMessage(
                                    Component.text(bundle.getString("commands.invalidArg"))
                                            .color(NamedTextColor.RED)
                            );
                            return true;
                        }

                        Component levelTypeName = Component.text(levelType.name()).color(levelType.getColor()).decoration(TextDecoration.BOLD, true);
                        Component rewardsLeft = Component.text(FileLevels.getConfig().getInt(player.getName() + ".rewardsLeft." + levelType.name().toLowerCase()))
                                .color(levelType.getColor());
                        Component lvl = Component.text(FileLevels.getConfig().getInt(player.getName() + ".level." + levelType.name().toLowerCase()))
                                .color(levelType.getColor());

                        Component text = Component.text(bundle.getString("commands.level.check")).color(NamedTextColor.DARK_GREEN)
                                .replaceText(TextReplacementConfig.builder().match("%rewards%").replacement(rewardsLeft).build())
                                .replaceText(TextReplacementConfig.builder().match("%type%").replacement(levelTypeName).build())
                                .replaceText(TextReplacementConfig.builder().match("%level%").replacement(lvl).build());

                        player.sendMessage(text);
                    }
                }
                case 2 -> {
                    if(!Main.getPlugin().getConfig().getBoolean("levelingSystem.rewards")){
                        player.sendMessage(
                                Component.text(bundle.getString("level.rewarding.disabled"))
                                        .color(NamedTextColor.RED)
                        );
                        return true;
                    }

                    if(!strings[1].equalsIgnoreCase("open")){
                        player.sendMessage(
                                Component.text(bundle.getString("commands.invalidArg"))
                                        .color(NamedTextColor.RED)
                        );
                        return true;
                    }

                    LevelType levelType;

                    try {
                        levelType = LevelType.valueOf(strings[0].toUpperCase());
                    } catch (IllegalArgumentException e) {
                        player.sendMessage(
                                Component.text(bundle.getString("commands.invalidArg"))
                                        .color(NamedTextColor.RED)
                        );
                        return true;
                    }

                    if(FileLevels.getConfig().getInt(player.getName() + ".rewardsLeft." + levelType.name().toLowerCase()) <= 0){
                        player.sendMessage(
                                Component.text(bundle.getString("commands.level.noRewardsLeft"))
                                        .color(NamedTextColor.RED)
                        );
                        return true;
                    }

                    if(player.getInventory().firstEmpty() == -1){
                        player.sendMessage(
                                Component.text(bundle.getString("level.inv.start.noSpace")).color(NamedTextColor.RED)
                        );
                        return true;
                    }

                    new InventoryDrumroll(player, levelType);
                }
                case 3, 4 -> {
                    int caseNumber = strings.length;
                    if(!FileAdmins.isAdmin(player)){
                        player.sendMessage(Component.text(bundle.getString("commands.noAdmin")).color(NamedTextColor.RED));
                        return true;
                    }

                    if(!strings[0].equalsIgnoreCase("get")){
                        player.sendMessage(Component.text(bundle.getString("commands.invalidArg")).color(NamedTextColor.RED));
                        return true;
                    }

                    String category = strings[1].toLowerCase();
                    if(!List.of("rewards", "levels").contains(category)){
                        player.sendMessage(Component.text(bundle.getString("commands.invalidArg")).color(NamedTextColor.RED));
                        return true;
                    }

                    Player target = Bukkit.getPlayer(strings[2]);
                    if(!Bukkit.getOnlinePlayers().contains(target) || target == null){
                        player.sendMessage(Component.text(bundle.getString("commands.invalidTarget")).color(NamedTextColor.RED));
                        return true;
                    }

                    LevelType levelType = null;
                    if(caseNumber == 4){
                        try {
                            levelType = LevelType.valueOf(strings[3].toUpperCase());
                        } catch (IllegalArgumentException e) {
                            player.sendMessage(
                                    Component.text(bundle.getString("commands.invalidArg"))
                                            .color(NamedTextColor.RED)
                            );
                            return true;
                        }
                    }

                    if(category.equalsIgnoreCase("levels")){
                        Component text;
                        if(caseNumber == 3){
                            text = Component.text(bundle.getString("commands.level.checkAll.other")).color(NamedTextColor.DARK_GREEN)
                                    .replaceText(TextReplacementConfig.builder().match("%target%")
                                            .replacement(
                                                    Component.text(target.getName())
                                                            .color(NamedTextColor.GOLD)
                                                            .decoration(TextDecoration.ITALIC, true)
                                            ).build());

                            for (LevelType type : LevelType.values()) {
                                Component minus = Component.text("\n - ").color(NamedTextColor.DARK_GRAY);
                                Component level = Component.text(bundle.getString("level." + type.name().toLowerCase()) + ": " +
                                        FileLevels.getConfig().getInt(target.getName() + ".level." + type.name().toLowerCase())).color(type.getColor());
                                minus = minus.append(level);
                                text = text.append(minus);
                            }

                        } else {
                            text = Component.text(bundle.getString("commands.level.check.other")).color(NamedTextColor.DARK_GREEN)
                                    .replaceText(TextReplacementConfig.builder().match("%target%")
                                            .replacement(
                                                    Component.text(target.getName())
                                                            .color(NamedTextColor.GOLD)
                                                            .decoration(TextDecoration.ITALIC, true)
                                            ).build()
                                    ).replaceText(TextReplacementConfig.builder().match("%type%")
                                            .replacement(
                                                    Component.text(bundle.getString("level." + levelType.toString().toLowerCase()))
                                                            .color(levelType.getColor())
                                                            .decoration(TextDecoration.BOLD, true)
                                            ).build())
                                    .replaceText(TextReplacementConfig.builder().match("%level%")
                                            .replacement(
                                                    Component.text(FileLevels.getConfig().getInt(target.getName() + ".level." + levelType.name().toLowerCase()))
                                                            .color(levelType.getColor())
                                            ).build());
                        }

                        player.sendMessage(text);
                        return true;
                    }

                    if(category.equalsIgnoreCase("rewards")){
                        Component text;

                        if(caseNumber == 3){
                            text = Component.text(bundle.getString("commands.level.rewardsLeft.target")).color(NamedTextColor.DARK_GREEN)
                                    .replaceText(TextReplacementConfig.builder().match("%target%")
                                            .replacement(
                                                    Component.text(target.getName())
                                                            .color(NamedTextColor.GOLD)
                                                            .decoration(TextDecoration.ITALIC, true)
                                            ).build());

                            boolean allZero = true;

                            for (LevelType type : LevelType.values()) {
                                int left = FileLevels.getConfig().getInt(target.getName() + ".rewardsLeft." + type.name().toLowerCase());
                                if (left > 0) {
                                    allZero = false;
                                    Component minus = Component.text("\n - ").color(NamedTextColor.DARK_GRAY);
                                    Component reminderText = Component.text(bundle.getString("level." + type.toString().toLowerCase()) +
                                                    ": " + left)
                                            .color(type.getColor());
                                    minus = minus.append(reminderText);
                                    text = text.append(minus);
                                }

                                if (allZero) {
                                    text = Component.text(bundle.getString("commands.level.noRewardsLeft.target"))
                                            .replaceText(TextReplacementConfig.builder().match("%target%")
                                                    .replacement(
                                                            Component.text(target.getName())
                                                                    .color(NamedTextColor.GOLD)
                                                                    .decoration(TextDecoration.ITALIC, true)
                                                    ).build())
                                            .color(NamedTextColor.RED);
                                }
                            }
                        } else {
                            String bundleKey = "commands.level.rewardsLeft.targetSolo";
                            NamedTextColor color = NamedTextColor.DARK_GREEN;
                            if(FileLevels.getConfig().getInt(target.getName() + ".rewardsLeft." + levelType.name().toLowerCase()) <= 0){
                                bundleKey = "commands.level.noRewardsLeft.targetSolo";
                                color = NamedTextColor.RED;
                            }

                            text = Component.text(bundle.getString(bundleKey)).color(color)
                                    .replaceText(TextReplacementConfig.builder().match("%target%")
                                            .replacement(
                                                    Component.text(target.getName())
                                                            .color(NamedTextColor.GOLD)
                                                            .decoration(TextDecoration.ITALIC, true)
                                            ).build()
                                    ).replaceText(TextReplacementConfig.builder().match("%type%")
                                            .replacement(
                                                    Component.text(bundle.getString("level." + levelType.toString().toLowerCase()))
                                                            .color(levelType.getColor())
                                                            .decoration(TextDecoration.BOLD, true)
                                            ).build()
                                    ).replaceText(TextReplacementConfig.builder().match("%rewardsLeft%")
                                            .replacement(
                                                    Component.text(FileLevels.getConfig().getInt(target.getName() + ".rewardsLeft." + levelType.name().toLowerCase()))
                                                            .color(levelType.getColor())
                                            ).build());
                        }

                        player.sendMessage(text);
                        return true;
                    }

                }
                case 5 -> {
                    if(!FileAdmins.isAdmin(player)){
                        player.sendMessage(Component.text(bundle.getString("commands.noAdmin")).color(NamedTextColor.RED));
                        return true;
                    }

                    String action = strings[0].toLowerCase();
                    if(!List.of("add", "set", "remove").contains(action)){
                        player.sendMessage(Component.text(bundle.getString("commands.invalidArg")).color(NamedTextColor.RED));
                        return true;
                    }

                    String category = strings[1].toLowerCase();
                    if(!List.of("rewards", "levels").contains(category)){
                        player.sendMessage(Component.text(bundle.getString("commands.invalidArg")).color(NamedTextColor.RED));
                        return true;
                    }

                    Player target = Bukkit.getPlayer(strings[2]);
                    if(!Bukkit.getOnlinePlayers().contains(target) || target == null){
                        player.sendMessage(Component.text(bundle.getString("commands.invalidTarget")).color(NamedTextColor.RED));
                        return true;
                    }

                    LevelType levelType = null;
                    try {
                        levelType = LevelType.valueOf(strings[3].toUpperCase());
                    } catch (IllegalArgumentException e) {
                        player.sendMessage(
                                Component.text(bundle.getString("commands.invalidArg"))
                                        .color(NamedTextColor.RED)
                        );
                        return true;
                    }

                    int amount = 0;
                    try {
                        amount = Integer.parseInt(strings[4]);
                    } catch (NumberFormatException e) {
                        player.sendMessage(
                                Component.text(bundle.getString("commands.invalidArg"))
                                        .color(NamedTextColor.RED)
                        );
                        return true;
                    }

                    if(amount == 0 && !action.equalsIgnoreCase("set")){
                        player.sendMessage(
                                Component.text(bundle.getString("commands.nothingChanged"))
                                        .color(NamedTextColor.RED)
                        );
                        return true;
                    }

                    if(action.equalsIgnoreCase("add"))
                        action = "added";
                    else if(action.equalsIgnoreCase("remove"))
                        action = "removed";

                    String bundleCategory = category;

                    if(category.equalsIgnoreCase("levels"))
                        bundleCategory = "Level";
                    else if (category.equalsIgnoreCase("rewards"))
                        bundleCategory = "Rewards";

                    String fileCategory = category;
                    if(category.equalsIgnoreCase("rewards"))
                        fileCategory = "rewardsLeft";
                    else if (category.equalsIgnoreCase("levels"))
                        fileCategory = "level";

                    switch (action){
                        case "added" -> {
                            FileLevels.getConfig().set(target.getName() + "." + fileCategory + "." + levelType.name().toLowerCase(),
                                    FileLevels.getConfig().getInt(target.getName() + "." + fileCategory + "." + levelType.name().toLowerCase()) + amount);
                        }
                        case "removed" -> {
                            FileLevels.getConfig().set(target.getName() + "." + fileCategory + "." + levelType.name().toLowerCase(),
                                    FileLevels.getConfig().getInt(target.getName() + "." + fileCategory + "." + levelType.name().toLowerCase()) - amount);
                        }
                        case "set" -> {
                            FileLevels.getConfig().set(target.getName() + "." + fileCategory + "." + levelType.name().toLowerCase(), amount);
                        }
                    }
                    FileLevels.saveConfig();

                    Component text = Component.text(bundle.getString("commands.level." + action + bundleCategory)).color(NamedTextColor.DARK_GREEN)
                            .replaceText(TextReplacementConfig.builder().match("%target%")
                                    .replacement(
                                            Component.text(target.getName())
                                                    .color(NamedTextColor.GOLD)
                                                    .decoration(TextDecoration.ITALIC, true)
                                    ).build()
                            ).replaceText(TextReplacementConfig.builder().match("%type%")
                                    .replacement(
                                            Component.text(bundle.getString("level." + levelType.toString().toLowerCase()))
                                                    .color(levelType.getColor())
                                                    .decoration(TextDecoration.BOLD, true)
                                    ).build()
                            ).replaceText(TextReplacementConfig.builder().match("%value%")
                                    .replacement(
                                            Component.text(String.valueOf(amount))
                                                    .color(levelType.getColor())
                                    ).build())
                            .replaceText(TextReplacementConfig.builder().match("%" + category + "%")
                                    .replacement(
                                            Component.text(String.valueOf(FileLevels.getConfig().getInt(target.getName() + "." + fileCategory + "." + levelType.name().toLowerCase())))
                                                    .color(levelType.getColor())
                                    ).build());

                    player.sendMessage(text);
                }
            }
            return true;
        } else {
            //TODO: Make Console tauglich digga
        }
        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        switch (strings.length){
            case 1 -> {
                ArrayList<String> list = new ArrayList<>(List.of("rewards", "levels", "farming", "mining", "combat", "adventure", "magic", "trading", "building"));
                if(commandSender instanceof Player player && FileAdmins.isAdmin(player)){
                    list.addAll(List.of("add", "set", "remove", "get"));
                }
                return list;
            }
            case 2 -> {
                if(List.of("add", "set", "remove", "get").contains(strings[0].toLowerCase()) && commandSender instanceof Player player && FileAdmins.isAdmin(player)){
                    return List.of("rewards", "levels" );
                }
                else if(List.of("farming", "mining", "combat", "adventure", "magic", "trading", "building").contains(strings[0].toLowerCase())){
                    return List.of("open");
                } else return new ArrayList<>();
            }
            case 3 -> {
                if(List.of("add", "set", "remove", "get").contains(strings[0].toLowerCase()) && List.of("rewards", "levels").contains(strings[1].toLowerCase())
                        && commandSender instanceof Player player && FileAdmins.isAdmin(player)){
                    return null;
                }
            }
            case 4 -> {
                if(List.of("add", "set", "remove", "get").contains(strings[0].toLowerCase()) && List.of("rewards", "levels").contains(strings[1].toLowerCase())
                        && commandSender instanceof Player player && FileAdmins.isAdmin(player)){
                    return List.of("farming", "mining", "combat", "adventure", "magic", "trading", "building");
                }
            }
            case 5 -> {
                if(List.of("add", "set", "remove", "get").contains(strings[0].toLowerCase()) && List.of("rewards", "levels").contains(strings[1].toLowerCase())
                        && List.of("farming", "mining", "combat", "adventure", "magic", "trading", "building").contains(strings[3].toLowerCase())
                        && commandSender instanceof Player player && FileAdmins.isAdmin(player)){
                    return List.of("0", "1", "2", "5", "10");
                }
            }
        }
        return List.of();
    }
}
