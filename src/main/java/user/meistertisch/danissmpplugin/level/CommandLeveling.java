package user.meistertisch.danissmpplugin.level;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextReplacementConfig;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
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
                case 2 -> {
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
                    FileLevels.getConfig().set(player.getName() + ".rewardsLeft." + type.name().toLowerCase(),
                            FileLevels.getConfig().getInt(player.getName() + ".rewardsLeft." + type.name().toLowerCase())-1);
                    FileLevels.saveConfig();
                }
                case 3 ->{
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
                case 4 -> {

                }
            }
            return true;
        } else {

        }
        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {

        return List.of();
    }
}
