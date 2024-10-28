package user.meistertisch.danissmpplugin.admin;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import user.meistertisch.danissmpplugin.Main;
import user.meistertisch.danissmpplugin.files.FileAdmins;
import user.meistertisch.danissmpplugin.files.FilePlayer;

import java.util.List;
import java.util.ResourceBundle;

public class CommandChat implements TabExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        ResourceBundle bundle = ResourceBundle.getBundle("language_en");

        if(sender instanceof Player player) {
            bundle = ResourceBundle.getBundle("language_" + FilePlayer.getConfig().getString(player.getName() + ".lang"));
            List<String> list = FileAdmins.getConfig().getStringList("admins");
            if (!list.contains(player.getUniqueId().toString())) {
                player.sendMessage(Component.text(bundle.getString("commands.noAdmin")).color(NamedTextColor.RED));
                return true;
            }
        }

        if (args.length != 1 || !List.of("on", "off").contains(args[0].toLowerCase())){
            sender.sendMessage(Component.text(bundle.getString("commands.invalidArg")).color(NamedTextColor.RED));
            return true;
        }

        boolean isTurningOff = args[0].equalsIgnoreCase("off");
        boolean isOff = Main.getPlugin().getConfig().getBoolean("chat.disabled");

        if (isTurningOff && isOff) {
            sender.sendMessage(Component.text(bundle.getString("commands.chat.alreadyOff")).color(NamedTextColor.RED));
            return true;
        }
        if (!isTurningOff && !isOff) {
            sender.sendMessage(Component.text(bundle.getString("commands.chat.alreadyOn")).color(NamedTextColor.RED));
            return true;
        }

        Main.getPlugin().getConfig().set("chat.disabled", isTurningOff);
        Main.getPlugin().saveConfig();
        Main.getPlugin().reloadConfig();

        for(Player player : Bukkit.getOnlinePlayers()){
            ResourceBundle bundleP = ResourceBundle.getBundle("language_" + FilePlayer.getConfig().getString(player.getName() + ".lang"));
            if(isTurningOff){
                player.sendMessage(Component.text(bundleP.getString("commands.chat.turnedOff")).color(NamedTextColor.RED));
            } else {
                player.sendMessage(Component.text(bundleP.getString("commands.chat.turnedOn")).color(NamedTextColor.GREEN));
            }
        }

        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        return List.of();
    }
}
