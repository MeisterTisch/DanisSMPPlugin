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

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class CommandAdmin implements TabExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        ResourceBundle bundle = ResourceBundle.getBundle("language_en");
        List<String> list = FileAdmins.getConfig().getStringList("admins");

        if(sender instanceof Player player) {
            bundle = ResourceBundle.getBundle("language_" + FilePlayer.getConfig().getString(player.getName() + ".lang"));

            if(!list.contains(player.getUniqueId().toString())) {
                player.sendMessage(Component.text(bundle.getString("commands.noAdmin")).color(NamedTextColor.RED));
                return true;
            }
        }

        if(args.length == 0 || args.length > 2){
            sender.sendMessage(Component.text(bundle.getString("commands.invalidTarget")).color(NamedTextColor.RED));
            return true;
        }

        Player target = Bukkit.getPlayer(args[0]);

        if(target == null){
            sender.sendMessage(Component.text(bundle.getString("commands.invalidTarget")).color(NamedTextColor.RED));
            return true;
        }

        if(list.contains(target.getUniqueId().toString())){
            list.remove(target.getUniqueId().toString());
            FileAdmins.getConfig().set("admins", list);
            FileAdmins.saveConfig();
            sender.sendMessage(Component.text(bundle.getString("commands.admin.removed")).color(NamedTextColor.GOLD)
                    .replaceText(TextReplacementConfig.builder().match("%target%").replacement(target.getName()).build()));
            if(target.isOnline()){
                ResourceBundle targetBundle = ResourceBundle.getBundle("language_" + FilePlayer.getConfig().getString(target.getName() + ".lang"));
                target.sendMessage(Component.text(targetBundle.getString("commands.admin.targetRemoved")).color(NamedTextColor.RED));
            }
            return true;
        }

        list.add(String.valueOf(target.getUniqueId().toString()));
        FileAdmins.getConfig().set("admins", list);
        FileAdmins.saveConfig();
        sender.sendMessage(Component.text(bundle.getString("commands.admin.added")).color(NamedTextColor.GREEN)
                .replaceText(TextReplacementConfig.builder().match("%target%").replacement(target.getName()).build()));
        if(target.isOnline()){
            ResourceBundle targetBundle = ResourceBundle.getBundle("language_" + FilePlayer.getConfig().getString(target.getName() + ".lang"));
            target.sendMessage(Component.text(targetBundle.getString("commands.admin.targetAdded")).color(NamedTextColor.GREEN));
        }
        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(args.length == 1){
            return null;
        }
        else return new ArrayList<>();
    }
}
