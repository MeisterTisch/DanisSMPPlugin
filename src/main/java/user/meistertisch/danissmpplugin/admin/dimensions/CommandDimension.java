package user.meistertisch.danissmpplugin.admin.dimensions;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextReplacementConfig;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
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

public class CommandDimension implements TabExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        ResourceBundle bundle = ResourceBundle.getBundle("language_en");
        List<String> list = FileAdmins.getConfig().getStringList("admins");

        if(sender instanceof Player player) {
            bundle = ResourceBundle.getBundle("language_" + FilePlayer.getConfig().getString(player.getName() + ".lang"));
            if(!FileAdmins.isAdmin(player)) {
                player.sendMessage(Component.text(bundle.getString("commands.noAdmin")).color(NamedTextColor.RED));
                return true;
            }
        }

        if(args.length != 2){
            sender.sendMessage(Component.text(bundle.getString("commands.invalidArg")).color(NamedTextColor.RED));
            return true;
        }

        String dimension = args[0].toLowerCase();
        String action = args[1].toLowerCase();

        if(!List.of("end", "nether").contains(dimension) || !List.of("allow", "disallow").contains(action)){
            sender.sendMessage(Component.text(bundle.getString("commands.invalidArg")).color(NamedTextColor.RED));
            return true;
        }

        boolean isAllowed = Main.getPlugin().getConfig().getBoolean("dimension." + dimension);

        if(isAllowed == action.equals("allow") || !isAllowed == action.equals("disallow")){
            sender.sendMessage(Component.text(bundle.getString("commands.dimension.already" + (isAllowed ? "Allowed" : "Disallowed"))).color(NamedTextColor.RED)
                    .replaceText(TextReplacementConfig.builder().match("%dimension%").replacement(
                            Component.text(bundle.getString("commands.dimension." + dimension)).color(NamedTextColor.GOLD)).build()));
            return true;
        }

        Main.getPlugin().getConfig().set("dimension." + dimension, action.equals("allow"));
        Main.getPlugin().saveConfig();

        for(Player player : Bukkit.getOnlinePlayers()){
            ResourceBundle bundleP = ResourceBundle.getBundle("language_" + FilePlayer.getConfig().getString(player.getName() + ".lang"));
            player.sendMessage(Component.text(bundleP.getString("commands.dimension." + action + "ed")).color(TextColor.color(Main.getSecondaryColor()))
                    .replaceText(TextReplacementConfig.builder().match("%dimension%").replacement(
                            Component.text(bundleP.getString("commands.dimension." + dimension)).color(TextColor.color(Main.getPrimaryColor()))).build()));
        }

        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player player && FileAdmins.isAdmin(player)) {
            if(args.length == 1){
                return List.of("end", "nether");
            }
            if(args.length == 2){
                return List.of("allow", "disallow");
            }
        }
        return List.of();
    }
}
