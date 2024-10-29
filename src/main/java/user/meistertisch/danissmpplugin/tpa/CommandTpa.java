package user.meistertisch.danissmpplugin.tpa;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import user.meistertisch.danissmpplugin.Main;
import user.meistertisch.danissmpplugin.combatTimer.ManagerCombatTimer;
import user.meistertisch.danissmpplugin.files.FilePlayer;

import java.util.List;
import java.util.ResourceBundle;

public class CommandTpa implements TabExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if(commandSender instanceof Player player){
            ResourceBundle bundle = ResourceBundle.getBundle("language_" + FilePlayer.getConfig().getString(player.getName() + ".lang"));

            if(strings.length == 0){
                player.sendMessage(Component.text(bundle.getString("commands.invalidArg")).color(NamedTextColor.RED));
                return true;
            }
            if(strings.length == 1) {
                if (strings[0].equalsIgnoreCase("accept")) {
                    if(ManagerCombatTimer.isInCombat(player)){
                        player.sendMessage(Component.text(bundle.getString("combatTimer.added")).color(NamedTextColor.RED));
                        return true;
                    }

                    Main.getManagerTPA().accept(player);
                    return true;
                } else if (strings[0].equalsIgnoreCase("decline")) {
                    Main.getManagerTPA().decline(player);
                    return true;
                } else if(List.of("allow", "disallow").contains(strings[0].toLowerCase())){
                    FilePlayer.getConfig().set(player.getName() + ".tpa", strings[0].equalsIgnoreCase("allow"));
                    FilePlayer.saveConfig();

                    if(strings[0].equalsIgnoreCase("allow")){
                        player.sendMessage(Component.text(bundle.getString("commands.tpa.allowed")).color(NamedTextColor.GREEN));
                    } else {
                        player.sendMessage(Component.text(bundle.getString("commands.tpa.disallowed")).color(NamedTextColor.RED));
                    }
                    return true;
                } else if (strings[0].equalsIgnoreCase("cancel")) {
                    Main.getManagerTPA().cancelRequest(player);
                    return true;
                } else {
                    player.sendMessage(Component.text(bundle.getString("commands.invalidArg")).color(NamedTextColor.RED));
                    return true;
                }
            }
            if(strings.length == 2) {
                if (List.of("here", "to").contains(strings[0].toLowerCase())) {
                    if(ManagerCombatTimer.isInCombat(player)){
                        player.sendMessage(Component.text(bundle.getString("combatTimer.added")).color(NamedTextColor.RED));
                        return true;
                    }

                    Player target = Bukkit.getPlayer(strings[1]);
                    if (target == null || FilePlayer.getConfig().getBoolean(target.getName() + ".hidden")) {
                        player.sendMessage(Component.text(bundle.getString("commands.invalidTarget")).color(NamedTextColor.RED));
                        return true;
                    }
                    Main.getManagerTPA().addRequest(player, target, strings[0].equalsIgnoreCase("here"));
                    return true;
                }
            }
        } else {
            commandSender.sendMessage(ResourceBundle.getBundle("language_en").getString("commands.onlyPlayer"));
        }
        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if(strings.length == 1){
            return List.of("accept", "decline", "here", "to", "allow", "disallow", "cancel");
        }
        return null;
    }
}
