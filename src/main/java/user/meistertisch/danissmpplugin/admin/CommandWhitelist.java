package user.meistertisch.danissmpplugin.admin;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextReplacementConfig;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import user.meistertisch.danissmpplugin.Main;
import user.meistertisch.danissmpplugin.files.FileAdmins;
import user.meistertisch.danissmpplugin.files.FilePlayer;
import user.meistertisch.danissmpplugin.files.FileWhitelist;

import java.util.List;
import java.util.ResourceBundle;

public class CommandWhitelist implements TabExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        ResourceBundle bundle = ResourceBundle.getBundle("language_en");

        if(sender instanceof Player player) {
            bundle = ResourceBundle.getBundle("language_" + FilePlayer.getConfig().getString(player.getName() + ".lang"));

            if(!FileAdmins.isAdmin(player)) {
                player.sendMessage(Component.text(bundle.getString("commands.noAdmin")).color(NamedTextColor.RED));
                return true;
            }
        }

        if(args.length == 0 || args.length > 2) {
            sender.sendMessage(Component.text(bundle.getString("commands.invalidArg")).color(NamedTextColor.RED));
            return true;
        }

        switch (args[0].toLowerCase()) {
            case "add" -> {
                if(args.length != 1) {
                    sender.sendMessage(Component.text(bundle.getString("commands.invalidArg")).color(NamedTextColor.RED));
                    return true;
                }
                for(String p : FileWhitelist.getList()){
                    Main.getPlugin().getServer().getOfflinePlayer(p).setWhitelisted(true);
                }
                Component count = Component.text(FileWhitelist.getList().size(), NamedTextColor.GOLD);
                Component message = Component.text(bundle.getString("commands.whitelist.added"), NamedTextColor.GREEN)
                        .replaceText(TextReplacementConfig.builder().match("%count%").replacement(count).build());
                sender.sendMessage(message);
            }
            case "remove" -> {
                if(args.length != 1) {
                    sender.sendMessage(Component.text(bundle.getString("commands.invalidArg")).color(NamedTextColor.RED));
                    return true;
                }
                for(String p : FileWhitelist.getList()){
                    Main.getPlugin().getServer().getOfflinePlayer(p).setWhitelisted(false);
                }
                Component count = Component.text(FileWhitelist.getList().size(), NamedTextColor.GOLD);
                Component message = Component.text(bundle.getString("commands.whitelist.removed"), NamedTextColor.RED)
                        .replaceText(TextReplacementConfig.builder().match("%count%").replacement(count).build());
                sender.sendMessage(message);
            }
            case "addplayer" -> {
                if(args.length != 2) {
                    sender.sendMessage(Component.text(bundle.getString("commands.invalidArg")).color(NamedTextColor.RED));
                    return true;
                }
                FileWhitelist.addPlayer(args[1]);
                Component player = Component.text(args[1], NamedTextColor.GOLD);
                Component message = Component.text(bundle.getString("commands.whitelist.playerAdded"), NamedTextColor.GREEN)
                        .replaceText(TextReplacementConfig.builder().match("%player%").replacement(player).build());
                sender.sendMessage(message);

                Component button = Component.text(bundle.getString("commands.whitelist.yes"), NamedTextColor.GREEN)
                        .clickEvent(ClickEvent.runCommand("/minecraft:whitelist add " + args[1]));
                message = Component.text(bundle.getString("commands.whitelist.playerAdded.afterMessage"), NamedTextColor.GOLD)
                        .append(Component.text(" ")).append(button);
                sender.sendMessage(message);
            }
            case "removeplayer" -> {
                if(args.length != 2) {
                    sender.sendMessage(Component.text(bundle.getString("commands.invalidArg")).color(NamedTextColor.RED));
                    return true;
                }
                FileWhitelist.removePlayer(args[1]);
                Component player = Component.text(args[1], NamedTextColor.GOLD);
                Component message = Component.text(bundle.getString("commands.whitelist.playerRemoved"), NamedTextColor.RED)
                        .replaceText(TextReplacementConfig.builder().match("%player%").replacement(player).build());
                sender.sendMessage(message);

                Component button = Component.text(bundle.getString("commands.whitelist.yes"), NamedTextColor.GREEN)
                        .clickEvent(ClickEvent.runCommand("/minecraft:whitelist remove " + args[1]));
                message = Component.text(bundle.getString("commands.whitelist.playerRemoved.afterMessage"), NamedTextColor.GOLD)
                        .append(Component.text(" ")).append(button);
                sender.sendMessage(message);
            }
            default -> sender.sendMessage(Component.text(bundle.getString("commands.invalidArg")).color(NamedTextColor.RED));
        }
        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if(args.length == 1)
            return List.of("add", "remove", "addplayer", "removeplayer");
        if(args.length == 2 && args[0].equalsIgnoreCase("removeplayer"))
            return FileWhitelist.getList();
        return List.of();
    }
}
