package user.meistertisch.danissmpplugin.admin.misc;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import user.meistertisch.danissmpplugin.files.FileAdmins;
import user.meistertisch.danissmpplugin.files.FilePlayer;

import java.util.List;
import java.util.ResourceBundle;

public class CommandInvLooker implements TabExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(sender instanceof Player player) {
            ResourceBundle bundle = ResourceBundle.getBundle("language_" + FilePlayer.getConfig().getString(player.getName() + ".lang"));
            List<String> list = FileAdmins.getConfig().getStringList("admins");
            if(!list.contains(player.getUniqueId().toString())) {
                player.sendMessage(Component.text(bundle.getString("commands.noAdmin")).color(NamedTextColor.RED));
                return true;
            }

            if(args.length != 1){
                sender.sendMessage(Component.text(bundle.getString("commands.invalidArg")).color(NamedTextColor.RED));
                return true;
            }

            Player target = player.getServer().getPlayer(args[0]);
            if(target == null || target == player){
                sender.sendMessage(Component.text(bundle.getString("commands.invalidTarget")).color(NamedTextColor.RED));
                return true;
            }

            if(label.equalsIgnoreCase("ec") || label.equalsIgnoreCase("enderchest")){
                player.openInventory(target.getEnderChest());
                return true;
            }
            player.openInventory(target.getInventory());
        }

        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(args.length == 1){
            return null;
        }
        return List.of();
    }
}
