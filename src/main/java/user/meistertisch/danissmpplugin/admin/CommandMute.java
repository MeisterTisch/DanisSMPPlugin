package user.meistertisch.danissmpplugin.admin;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextReplacementConfig;
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

public class CommandMute implements TabExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        ResourceBundle bundle = ResourceBundle.getBundle("language_en");

        if(sender instanceof Player player){
            bundle = ResourceBundle.getBundle("language_" + FilePlayer.getConfig().getString(player.getName() + ".lang"));
            if (!FileAdmins.isAdmin(player)) {
                player.sendMessage(Component.text(bundle.getString("commands.noAdmin")).color(NamedTextColor.RED));
                return true;
            }
        }

        if(args.length != 1){
            sender.sendMessage(Component.text(bundle.getString("commands.invalidArg")).color(NamedTextColor.RED));
            return true;
        }

        Player target = sender.getServer().getPlayer(args[0]);
        if(target == null){
            sender.sendMessage(Component.text(bundle.getString("commands.noPlayer")).color(NamedTextColor.RED));
            return true;
        }

        boolean isMuted = FilePlayer.getConfig().getBoolean(target.getName() + ".muted");

        if(label.equalsIgnoreCase("unmute") && !isMuted){
            sender.sendMessage(Component.text(bundle.getString("commands.mute.notMuted")).color(NamedTextColor.RED)
                    .replaceText(TextReplacementConfig.builder().match("%target%").replacement(
                            Component.text(target.getName()).color(NamedTextColor.GOLD)
                    ).build()));
            return true;
        }

        if(label.equalsIgnoreCase("mute") && isMuted){
            sender.sendMessage(Component.text(bundle.getString("commands.mute.alreadyMuted")).color(NamedTextColor.RED)
                    .replaceText(TextReplacementConfig.builder().match("%target%").replacement(
                    Component.text(target.getName()).color(NamedTextColor.GOLD)
            ).build()));
            return true;
        }

        FilePlayer.getConfig().set(target.getName() + ".muted", !isMuted);
        FilePlayer.saveConfig();

        sender.sendMessage(
                Component.text(
                        (label.equalsIgnoreCase("mute") ? bundle.getString("commands.mute.muted") : bundle.getString("commands.mute.unmuted"))
                ).color(label.equalsIgnoreCase("mute") ? NamedTextColor.RED : NamedTextColor.GREEN)
                        .replaceText(TextReplacementConfig.builder().match("%target%").replacement(
                                Component.text(target.getName()).color(NamedTextColor.GOLD)
                        ).build())
        );
        ResourceBundle targetBundle = ResourceBundle.getBundle("language_" + FilePlayer.getConfig().getString(target.getName() + ".lang"));
        target.sendMessage(
                Component.text(
                        (label.equalsIgnoreCase("mute") ? targetBundle.getString("commands.mute.muted.target") : targetBundle.getString("commands.mute.unmuted.target"))
                ).color(label.equalsIgnoreCase("mute") ? NamedTextColor.RED : NamedTextColor.GREEN)
        );

        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(args.length == 1)
            return null;
        return List.of();
    }
}
