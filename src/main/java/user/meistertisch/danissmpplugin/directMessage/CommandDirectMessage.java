package user.meistertisch.danissmpplugin.directMessage;

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

import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

public class CommandDirectMessage implements TabExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        ResourceBundle bundle = ResourceBundle.getBundle("language_en");
        if(!(sender instanceof Player player)){
            bundle = ResourceBundle.getBundle("language_en");
            sender.sendMessage(Component.text(bundle.getString("commands.onlyPlayer")).color(NamedTextColor.RED));
            return true;
        }

        switch (label.toLowerCase(Locale.ROOT)){
            case "message", "msg" -> {
                if(args.length < 2){
                    sender.sendMessage(Component.text(bundle.getString("commands.invalidArg")).color(NamedTextColor.RED));
                    return true;
                }

                Player target = Bukkit.getPlayer(args[0]);

                if(target == null){
                    sender.sendMessage(Component.text(bundle.getString("commands.playerNotFound")).color(NamedTextColor.RED)
                            .replaceText(TextReplacementConfig.builder().match("%target%").replacement(Component.text(args[0]).color(NamedTextColor.GOLD)).build()));
                    return true;
                }

                if(player == target){
                    sender.sendMessage(Component.text(bundle.getString("commands.directMessage.yourself")).color(NamedTextColor.RED));
                    return true;
                }

                String[] message = new String[args.length - 1];
                System.arraycopy(args, 1, message, 0, args.length - 1);

                ManagerDirectMessage.sendMessage(player, target, ManagerDirectMessage.convertToString(message));
            }
            case "reply", "r" -> {
                if(args.length < 1){
                    sender.sendMessage(Component.text(bundle.getString("commands.invalidArg")).color(NamedTextColor.RED));
                    return true;
                }

                if(ManagerDirectMessage.getReply(player) == null){
                    sender.sendMessage(Component.text(bundle.getString("commands.directMessage.noReply")).color(NamedTextColor.RED));
                    return true;
                }

                ManagerDirectMessage.sendMessage(player, ManagerDirectMessage.getReply(player), ManagerDirectMessage.convertToString(args));
            }
        }
        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(List.of("message", "msg").contains(label.toLowerCase(Locale.ROOT)) && args.length == 1)
            return null;

        return List.of();
    }
}
