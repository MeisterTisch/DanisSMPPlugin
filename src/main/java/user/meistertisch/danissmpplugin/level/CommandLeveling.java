package user.meistertisch.danissmpplugin.level;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextReplacementConfig;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import net.md_5.bungee.api.chat.hover.content.Text;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
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
                } //TODO: Implementieren der restlichen Funktion (rewards, levels) Siehe DrawIO
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

                case 1+99 -> {
                    switch (strings[0].toLowerCase()){
                        case "reward" -> {
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
                        default -> player.sendMessage(
                                Component.text(bundle.getString("commands.invalidArg"))
                                        .color(NamedTextColor.RED)
                        );
                    }
                }
                case 2+99 -> {
                    if(List.of("give", "set", "remove", "get").contains(strings[1].toLowerCase())){
                        if(!FileAdmins.isAdmin(player)){
                            player.sendMessage(Component.text(bundle.getString("commands.noAdmin")).color(NamedTextColor.RED));
                            return true;
                        }
                        player.sendMessage(Component.text(bundle.getString("commands.invalidArg")).color(NamedTextColor.RED));
                        return true;
                    }

                    String input = strings[1].toUpperCase();
                    LevelType type;

                    try {
                        type = LevelType.valueOf(input);
                    } catch (IllegalArgumentException e) {
                        player.sendMessage(
                                Component.text(bundle.getString("commands.invalidArg"))
                                        .color(NamedTextColor.RED)
                        );
                        return true;
                    }

                    if(FileLevels.getConfig().getInt(player.getName() + ".rewardsLeft." + type.name().toLowerCase()) <= 0){
                        player.sendMessage(
                                Component.text(bundle.getString("level.inv.start.noRewardsLeft")).color(NamedTextColor.RED)
                        );
                        return true;
                    }

                    if(player.getInventory().firstEmpty() == -1){
                        player.sendMessage(
                                Component.text(bundle.getString("level.inv.start.noSpace")).color(NamedTextColor.RED)
                        );
                        return true;
                    }

                    new InventoryDrumroll(player, type);
                }
                case 3+99 ->{
                    if(List.of("give", "set", "remove", "get").contains(strings[1].toLowerCase())) {
                        if (!FileAdmins.isAdmin(player)) {
                            player.sendMessage(Component.text(bundle.getString("commands.noAdmin")).color(NamedTextColor.RED));
                            return true;
                        }
                        if (!strings[1].equalsIgnoreCase("get")) {
                            player.sendMessage(Component.text(bundle.getString("commands.invalidArg")).color(NamedTextColor.RED));
                            return true;
                        }
                    }
                    String target = strings[2];

                    Component text = Component.text(bundle.getString("commands.level.rewardsLeft.target")).color(NamedTextColor.DARK_GREEN)
                            .replaceText(TextReplacementConfig.builder().match("%target%")
                                    .replacement(
                                            Component.text(target)
                                                    .color(NamedTextColor.GREEN)
                                                    .decoration(TextDecoration.ITALIC, true)
                                    ).build());

                    boolean allZero = true;

                    for (LevelType type : LevelType.values()) {
                        if (!(FileLevels.getConfig().getInt(target + ".rewardsLeft." + type.name().toLowerCase()) == 0)) {
                            allZero = false;
                            Component minus = Component.text("\n - ").color(NamedTextColor.DARK_GRAY);
                            Component reminderText = Component.text(bundle.getString("level." + type.toString().toLowerCase()) +
                                            ": " + FileLevels.getConfig().getInt(target + ".rewardsLeft." + type.name().toLowerCase()))
                                    .color(type.getColor());
                            minus = minus.append(reminderText);
                            text = text.append(minus);
                        }
                    }

                    if (allZero) {
                        text = Component.text(bundle.getString("commands.level.noRewardsLeft.target"))
                                .color(NamedTextColor.RED).replaceText(TextReplacementConfig.builder().match("%target%")
                                        .replacement(
                                                Component.text(target)
                                                        .color(NamedTextColor.GOLD)
                                                        .decoration(TextDecoration.ITALIC, true)
                                        ).build());;
                    }

                    player.sendMessage(text);
                }
                case 4+99 -> {
                    if(List.of("give", "set", "remove").contains(strings[1].toLowerCase())) {
                        if (!FileAdmins.isAdmin(player)) {
                            player.sendMessage(Component.text(bundle.getString("commands.noAdmin")).color(NamedTextColor.RED));
                            return true;
                        }
                        if (!strings[1].equalsIgnoreCase("get")) {
                            player.sendMessage(Component.text(bundle.getString("commands.invalidArg")).color(NamedTextColor.RED));
                            return true;
                        }
                    }
                    String target = strings[2];
                    String input = strings[3].toUpperCase();
                    LevelType type;

                    try {
                        type = LevelType.valueOf(input);
                    } catch (IllegalArgumentException e) {
                        player.sendMessage(
                                Component.text(bundle.getString("commands.invalidArg"))
                                        .color(NamedTextColor.RED)
                        );
                        return true;
                    }

                    if(FileLevels.getConfig().getInt(target + ".rewardsLeft." + type.name().toLowerCase()) <= 0){
                        Component targetComp = Component.text(target).color(NamedTextColor.GOLD).decoration(TextDecoration.ITALIC, true);
                        Component typeComp = Component.text(bundle.getString("level." + type.toString().toLowerCase()), type.getColor())
                                .decoration(TextDecoration.BOLD, true);
                        Component text = Component.text(bundle.getString("commands.level.noRewardsLeft.targetSolo")).color(NamedTextColor.RED)
                                        .replaceText(TextReplacementConfig.builder().match("%target%").replacement(targetComp).build())
                                        .replaceText(TextReplacementConfig.builder().match("%type%").replacement(typeComp).build());
                        player.sendMessage(text);
                        return true;
                    }

                    Component targetComp = Component.text(target).color(NamedTextColor.GREEN).decoration(TextDecoration.ITALIC, true);
                    Component typeComp = Component.text(bundle.getString("level." + type.toString().toLowerCase()), type.getColor())
                            .decoration(TextDecoration.BOLD, true);
                    Component text = Component.text(bundle.getString("commands.level.rewardsLeft.targetSolo")).color(NamedTextColor.DARK_GREEN)
                            .replaceText(TextReplacementConfig.builder().match("%target%").replacement(targetComp).build())
                            .replaceText(TextReplacementConfig.builder().match("%type%").replacement(typeComp).build())
                            .append(Component.text(" " + FileLevels.getConfig().getInt(target + ".rewardsLeft." + type.name().toLowerCase()))
                                    .color(NamedTextColor.GREEN));
                    player.sendMessage(text);
                }
                case 5+99 -> {
                    
                }
            }
            return true;
        } else {

        }
        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        switch (strings.length){
            case 1 -> {
                return List.of("reward");
            }
            case 2 -> {
                List<String> list = new ArrayList<>(List.of("farming", "mining", "combat", "adventure", "magic", "trading", "building"));
                if(commandSender instanceof Player player && FileAdmins.isAdmin(player)){
                    list.addAll(List.of("give", "set", "remove", "get"));
                }
                return list;
            }
            case 3 -> {
                if(List.of("give", "set", "remove", "get").contains(strings[1].toLowerCase())){
                    return null;
                }
                return List.of();
            }
            case 4 -> {
                if(List.of("give", "set", "remove", "get").contains(strings[1].toLowerCase())){
                    return List.of("farming", "mining", "combat", "adventure", "magic", "trading", "building");
                }
            }
            case 5 -> {
                if(List.of("give", "set", "remove").contains(strings[1].toLowerCase())){
                    return List.of("0", "1", "2", "5", "10");
                }
            }
        }
        return List.of();
    }
}
