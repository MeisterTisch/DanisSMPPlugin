package user.meistertisch.danissmpplugin.essentials.lang;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import user.meistertisch.danissmpplugin.files.FilePlayer;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

public class CommandLanguage implements TabExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        ResourceBundle bundle = ResourceBundle.getBundle("language_en");
        if(sender instanceof Player player){
            bundle = ResourceBundle.getBundle("language_" + FilePlayer.getConfig().getString(player.getName() + ".lang"));
        }

        if(args.length != 1) {
            sender.sendMessage(Component.text(bundle.getString("commands.invalidArg")).color(NamedTextColor.RED));
            return true;
        }

        if (Languages.getLanguage(args[0]) == null) {
            sender.sendMessage(Component.text(bundle.getString("commands.invalidArg")).color(NamedTextColor.RED));
            return true;
        }

        if (sender instanceof Player player) {
            if (FilePlayer.getConfig().getString(player.getName() + ".lang").equalsIgnoreCase(Languages.getLanguage(args[0]).getShortage())) {
                player.sendMessage(Component.text(bundle.getString("commands.language.already")).color(NamedTextColor.RED));
                return true;
            }

            FilePlayer.getConfig().set(player.getName() + ".lang", Languages.getLanguage(args[0]).getShortage());
            FilePlayer.saveConfig();
            bundle = ResourceBundle.getBundle("language_" + FilePlayer.getConfig().getString(player.getName() + ".lang"));
            player.sendMessage(Component.text(bundle.getString("commands.language.changed")).color(NamedTextColor.GREEN));
            return true;
        }
        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(args.length == 1){
            List<String> languages = new ArrayList<>();
            for(Languages lang : Languages.values()){
                languages.add(lang.name().toLowerCase(Locale.ROOT));
            }
            return languages;
        }
        return List.of();
    }
}
