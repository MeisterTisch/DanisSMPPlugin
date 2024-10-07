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

public class CommandDiscord implements TabExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        ResourceBundle bundle = ResourceBundle.getBundle("language_en");
        if(sender instanceof Player player){
            bundle = ResourceBundle.getBundle("language_" + FilePlayer.getConfig().getString(player.getName() + ".lang"));
        }
        if(!Main.getPlugin().getConfig().getBoolean("discord.use")) {
            sender.sendMessage(Component.text(bundle.getString("commands.discord.noUse")).color(NamedTextColor.RED));
            return true;
        }

        if(args.length != 0) {
            sender.sendMessage(Component.text(bundle.getString("commands.invalidArg")).color(NamedTextColor.RED));
            return true;
        }
        Component link = Component.text(Main.getPlugin().getConfig().getString("discord.link")).color(TextColor.color(0x7289da))
                .clickEvent(ClickEvent.openUrl(Main.getPlugin().getConfig().getString("discord.link")));
        sender.sendMessage(
                Component.text(bundle.getString("commands.discord")).color(NamedTextColor.GREEN)
                        .replaceText(TextReplacementConfig.builder().match("%link%").replacement(link).build()));
        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        return List.of();
    }
}
