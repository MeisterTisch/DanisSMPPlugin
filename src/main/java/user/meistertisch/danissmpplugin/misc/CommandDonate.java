package user.meistertisch.danissmpplugin.misc;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Color;
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

public class CommandDonate implements TabExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        Component minus = Component.text("- ").color(NamedTextColor.GRAY);
        Component kofi = Component.text("KoFi").color(TextColor.color(0xE3D6C6)).decorate(TextDecoration.UNDERLINED)
                .clickEvent(ClickEvent.openUrl("https://ko-fi.com/meistertisch"));
        Component streamelements = Component.text("StreamElements").color(TextColor.color(0x6441A4)).decorate(TextDecoration.UNDERLINED)
                .clickEvent(ClickEvent.openUrl("https://streamelements.com/meistertisch/tip"));

        ResourceBundle bundle = ResourceBundle.getBundle("language_en");
        if(sender instanceof Player player){
            bundle = ResourceBundle.getBundle("language_" + FilePlayer.getConfig().getString(player.getName() + ".lang"));
        }

        sender.sendMessage(Component.text(bundle.getString("commands.donate")).color(TextColor.color(Main.getSecondaryColor()))
                .append(Component.text("\n")).append(minus).append(kofi)
                .append(Component.text("\n")).append(minus).append(streamelements)
                .append(Component.text("\n\n" + bundle.getString("commands.donate.after")).color(TextColor.color(Main.getSecondaryColor()))));
        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        return List.of();
    }
}
