package user.meistertisch.danissmpplugin.admin;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextReplacementConfig;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
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

public class CommandFakeAdmin implements TabExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        ResourceBundle bundle = ResourceBundle.getBundle("language_en");
        if(sender instanceof Player player && !FileAdmins.isAdmin(player)){
            bundle = ResourceBundle.getBundle("language_" + FilePlayer.getConfig().getString(player.getName() + ".lang", "en"));
            player.sendMessage(Component.text(bundle.getString("commands.noAdmin"), NamedTextColor.RED));
            return true;
        }

        if(args.length != 1){
            sender.sendMessage(Component.text(bundle.getString("commands.invalidArg"), NamedTextColor.RED));
            return true;
        }

        Player target = Bukkit.getPlayer(args[0]);
        if(target == null){
            sender.sendMessage(Component.text(bundle.getString("commands.playerNotFound"), NamedTextColor.RED).replaceText(TextReplacementConfig.builder().match("%target%").replacement(args[0]).build()));
            return true;
        }

        ResourceBundle tBundle = ResourceBundle.getBundle("language_" + FilePlayer.getConfig().getString(target.getName() + ".lang", "en"));
        target.sendMessage(Component.text(tBundle.getString("commands.fakeAdmin.target"), NamedTextColor.GREEN));
        sender.sendMessage(Component.text(bundle.getString("commands.fakeAdmin"), NamedTextColor.GREEN).replaceText(TextReplacementConfig.builder().match("%target%").replacement(Component.text(target.getName(), NamedTextColor.GOLD)).build()));
        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        return List.of();
    }
}
