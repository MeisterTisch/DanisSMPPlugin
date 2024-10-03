package user.meistertisch.danissmpplugin.misc.thunderstormSummoner;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import user.meistertisch.danissmpplugin.Main;
import user.meistertisch.danissmpplugin.files.FileAdmins;
import user.meistertisch.danissmpplugin.files.FilePlayer;

import java.util.List;
import java.util.ResourceBundle;

public class CommandThunderstormSummoner implements TabExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        ResourceBundle bundle = ResourceBundle.getBundle("language_en");
        if(sender instanceof Player player){
            bundle = ResourceBundle.getBundle("language_" + FilePlayer.getConfig().getString(player.getName() + ".lang"));

            if(!FileAdmins.isAdmin(player)){
                player.sendMessage(Component.text(bundle.getString("commands.noAdmin")).color(NamedTextColor.RED));
                return true;
            }
        }
        FileConfiguration config = Main.getPlugin().getConfig();

        if(args.length == 0){
            boolean isOn = config.getBoolean("thunderSpawn.use");
            config.set("thunderSpawn.use", !isOn);
            if(!isOn){
                sender.sendMessage(Component.text(bundle.getString("commands.thunderstormSummoner.turnedOn")).color(NamedTextColor.GREEN));
            } else {
                sender.sendMessage(Component.text(bundle.getString("commands.thunderstormSummoner.turnedOff")).color(NamedTextColor.RED));
            }
        } else if(args.length == 1){
            if(args[0].equalsIgnoreCase("on") || args[0].equalsIgnoreCase("off")){
                boolean isOn = args[0].equalsIgnoreCase("on");

                if(config.getBoolean("thunderSpawn.use") == isOn){
                    if(isOn){
                        sender.sendMessage(Component.text(bundle.getString("commands.thunderstormSummoner.alreadyTurnedOn")).color(NamedTextColor.RED));
                    } else {
                        sender.sendMessage(Component.text(bundle.getString("commands.thunderstormSummoner.alreadyTurnedOff")).color(NamedTextColor.RED));
                    }
                    return true;
                }

                config.set("thunderSpawn.use", isOn);
                if(isOn){
                    sender.sendMessage(Component.text(bundle.getString("commands.thunderstormSummoner.turnedOn")).color(NamedTextColor.GREEN));
                } else {
                    sender.sendMessage(Component.text(bundle.getString("commands.thunderstormSummoner.turnedOff")).color(NamedTextColor.RED));
                }

            } else {
                sender.sendMessage(Component.text(bundle.getString("commands.invalidArg")).color(NamedTextColor.RED));
                return true;
            }
        } else {
            sender.sendMessage(Component.text(bundle.getString("commands.invalidArg")).color(NamedTextColor.RED));
            return true;
        }

        sender.sendMessage(Component.text(bundle.getString("functionTypes.changedRestart")).color(NamedTextColor.GOLD));
        Main.getPlugin().saveConfig();
        Main.getPlugin().reloadConfig();
        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        return List.of();
    }
}
