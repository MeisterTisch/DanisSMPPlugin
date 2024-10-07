package user.meistertisch.danissmpplugin.essentials;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextReplacementConfig;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import user.meistertisch.danissmpplugin.Main;
import user.meistertisch.danissmpplugin.files.FilePlayer;

import java.util.List;
import java.util.ResourceBundle;

public class CommandTeamspeak implements TabExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        ResourceBundle bundle = ResourceBundle.getBundle("language_en");
        if(sender instanceof Player player){
            bundle = ResourceBundle.getBundle("language_" + FilePlayer.getConfig().getString(player.getName() + ".lang"));
        }
        if(!Main.getPlugin().getConfig().getBoolean("teamspeak.use")) {
            sender.sendMessage(Component.text(bundle.getString("commands.teamspeak.noUse")).color(NamedTextColor.RED));
            return true;
        }

        if(args.length != 0) {
            sender.sendMessage(Component.text(bundle.getString("commands.invalidArg")).color(NamedTextColor.RED));
            return true;
        }

        Component ip = Component.text(Main.getPlugin().getConfig().getString("teamspeak.ip")).color(TextColor.color(0x2580c3))
                .clickEvent(ClickEvent.copyToClipboard(Main.getPlugin().getConfig().getString("teamspeak.ip")));
        Component password = Component.text(bundle.getString("commands.teamspeak.noPassword")).color(NamedTextColor.GOLD);
        if(Main.getPlugin().getConfig().getBoolean("teamspeak.isPasswordProtected"))
            password = Component.text(Main.getPlugin().getConfig().getString("teamspeak.password")).color(TextColor.color(0x2580c3))
                    .clickEvent(ClickEvent.copyToClipboard(Main.getPlugin().getConfig().getString("teamspeak.password")));

        sender.sendMessage(
                Component.text(bundle.getString("commands.teamspeak")).color(NamedTextColor.GREEN)
                        .replaceText(TextReplacementConfig.builder().match("%ip%").replacement(ip).build()));
        sender.sendMessage(Component.text(bundle.getString("commands.teamspeak.password")).color(NamedTextColor.GREEN)
                .replaceText(TextReplacementConfig.builder().match("%password%").replacement(password).build()));
        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        return List.of();
    }
}
