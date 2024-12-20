package user.meistertisch.danissmpplugin.admin;

import net.kyori.adventure.text.Component;
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

            if(FileAdmins.isAdmin(player)) {
                player.sendMessage(Component.text(bundle.getString("commands.noAdmin")).color(NamedTextColor.RED));
                return true;
            }
        }

        if(args.length != 1) {
            sender.sendMessage(Component.text(bundle.getString("commands.invalidArg")).color(NamedTextColor.RED));
            return true;
        }

        switch (args[0].toLowerCase()) {
            case "add" -> {
                for(String p : FileWhitelist.getList()){
                    Main.getPlugin().getServer().getOfflinePlayer(p).setWhitelisted(true);
                }
            }
            case "remove" -> {
                for(String p : FileWhitelist.getList()){
                    Main.getPlugin().getServer().getOfflinePlayer(p).setWhitelisted(false);
                }
            }
            default -> sender.sendMessage(Component.text(bundle.getString("commands.invalidArg")).color(NamedTextColor.RED));
        }
        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if(args.length == 1)
            return List.of("add", "remove");
        return List.of();
    }
}
