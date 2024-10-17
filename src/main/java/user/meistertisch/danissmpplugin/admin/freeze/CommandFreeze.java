package user.meistertisch.danissmpplugin.admin.freeze;

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

public class CommandFreeze implements TabExecutor {
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

        if(args.length != 1){
            sender.sendMessage(Component.text(bundle.getString("commands.invalidTarget")).color(NamedTextColor.RED));
            return true;
        }

        Player player = Bukkit.getPlayer(args[0]);
        if(player == null){
            sender.sendMessage(Component.text(bundle.getString("commands.invalidTarget")).color(NamedTextColor.RED));
            return true;
        }

        boolean isUnfreezing = label.equalsIgnoreCase("unfreeze");

        if(!isUnfreezing == ManagerFreeze.isFrozen(player)){
            if(isUnfreezing) {
                sender.sendMessage(Component.text(bundle.getString("commands.unfreeze.already")).color(NamedTextColor.RED)
                        .replaceText(TextReplacementConfig.builder().match("%target%").replacement(Component.text(args[0]).color(NamedTextColor.GOLD)).build()));
                return true;
            }
            sender.sendMessage(Component.text(bundle.getString("commands.freeze.already")).color(NamedTextColor.RED)
                    .replaceText(TextReplacementConfig.builder().match("%target%").replacement(Component.text(args[0]).color(NamedTextColor.GOLD)).build()));
            return true;
        }

        if(isUnfreezing){
            ManagerFreeze.unfreeze(player);
            sender.sendMessage(
                    Component.text(bundle.getString("commands.unfreeze")).color(NamedTextColor.GREEN)
                            .replaceText(TextReplacementConfig.builder().match("%target%").replacement(Component.text(args[0]).color(NamedTextColor.GOLD)).build())
            );
            return true;
        }

        ManagerFreeze.freeze(player);
        sender.sendMessage(
                Component.text(bundle.getString("commands.freeze")).color(NamedTextColor.GREEN)
                        .replaceText(TextReplacementConfig.builder().match("%target%").replacement(Component.text(args[0]).color(NamedTextColor.GOLD)).build())
        );
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
