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

public class CommandHide implements TabExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        ResourceBundle bundle = ResourceBundle.getBundle("language_en");

        if(sender instanceof Player player){
            bundle = ResourceBundle.getBundle("language_" + FilePlayer.getConfig().getString(player.getName() + ".lang"));
            if(!FileAdmins.isAdmin(player)){
                player.sendMessage(Component.text(bundle.getString("commands.noAdmin")).color(NamedTextColor.RED));
                return true;
            }

            if(args.length != 0){
                player.sendMessage(Component.text(bundle.getString("commands.invalidArg")).color(NamedTextColor.RED));
                return true;
            }

            boolean isHidden = FilePlayer.getConfig().getBoolean(player.getName() + ".hidden");
            FilePlayer.getConfig().set(player.getName() + ".hidden", !isHidden);

            player.sendMessage(
                    Component.text(
                            !isHidden ? bundle.getString("commands.hide.hidden") : bundle.getString("commands.hide.notHidden")
                    ).color(!isHidden ? NamedTextColor.RED : NamedTextColor.GREEN)
            );

            for(Player all : Bukkit.getOnlinePlayers()){
                if(!FileAdmins.isAdmin(all)){
                    if(isHidden){
                        all.showPlayer(Main.getPlugin(), player);
                    } else {
                        all.hidePlayer(Main.getPlugin(), player);
                    }
                }
            }

        } else {
            sender.sendMessage(Component.text(bundle.getString("commands.noPlayer")).color(NamedTextColor.RED));
        }
        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        return List.of();
    }
}
