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
import user.meistertisch.danissmpplugin.files.FileAdmins;
import user.meistertisch.danissmpplugin.files.FilePlayer;

import java.util.List;
import java.util.ResourceBundle;

public class CommandClearChat implements TabExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(sender instanceof Player player && !FileAdmins.isAdmin(player)){
            ResourceBundle bundle = ResourceBundle.getBundle("language_" + FilePlayer.getConfig().getString(player.getName() + ".lang", "en"));
            player.sendMessage(Component.text(bundle.getString("commands.noAdmin")).color(NamedTextColor.RED));
            return true;
        }

        for(Player player : Bukkit.getOnlinePlayers()){
            if(FileAdmins.isAdmin(player)){
                continue;
            }

            for (int i = 0; i < 100; i++) {
                player.sendMessage(Component.text("\n"));
            }
        }

        for (Player player : Bukkit.getOnlinePlayers()) {
            ResourceBundle bundle = ResourceBundle.getBundle("language_" + FilePlayer.getConfig().getString(player.getName() + ".lang", "en"));
            player.sendMessage(Component.text(bundle.getString("commands.chatCleared")).color(NamedTextColor.RED));
        }
        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        return List.of();
    }
}
