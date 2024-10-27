package user.meistertisch.danissmpplugin.spawn;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import user.meistertisch.danissmpplugin.Main;
import user.meistertisch.danissmpplugin.files.FileAdmins;
import user.meistertisch.danissmpplugin.files.FilePlayer;
import user.meistertisch.danissmpplugin.files.FileSpawn;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class CommandSpawn implements TabExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(sender instanceof Player player) {
            ResourceBundle bundle = ResourceBundle.getBundle("language_" + FilePlayer.getConfig().getString(player.getName() + ".lang"));
            List<String> admins = FileAdmins.getConfig().getStringList("admins");

            if(args.length == 0) {
                if(FileSpawn.getConfig().getInt("spawns") <= 0){
                    player.sendMessage(Component.text(bundle.getString("commands.spawn.noLeft")).color(NamedTextColor.RED));
                    return true;
                }

                if(Main.getPlugin().getConfig().getBoolean("spawn.commandTp")) {
                    player.teleport(ManagerSpawn.getRandomSpawnLocation());
                    player.sendMessage(Component.text(bundle.getString("commands.spawn.teleported")).color(NamedTextColor.GREEN));
                } else {
                    player.sendMessage(Component.text(bundle.getString("commands.spawn.tpDeactivated")).color(NamedTextColor.RED));
                }
                return true;
            }

            if(!admins.contains(player.getUniqueId().toString())) {
                player.sendMessage(Component.text(bundle.getString("commands.noAdmin")).color(NamedTextColor.RED));
                return true;
            }

            switch (args[0].toLowerCase()) {
                case "bypass" -> {
                    if(args.length == 2){
                        if(args[1].equalsIgnoreCase("building")){
                            ManagerSpawn.addBypass(player, true);
                            player.sendMessage(Component.text(bundle.getString("commands.spawn.bypass.building")).color(NamedTextColor.GREEN));
                            return true;
                        } else if(args[1].equalsIgnoreCase("combat")){
                            ManagerSpawn.addBypass(player, false);
                            player.sendMessage(Component.text(bundle.getString("commands.spawn.bypass.combat")).color(NamedTextColor.GREEN));
                            return true;
                        } else if(args[1].equalsIgnoreCase("off")){
                            ManagerSpawn.removeBypass(player);
                            player.sendMessage(Component.text(bundle.getString("commands.spawn.bypass.off")).color(NamedTextColor.GREEN));
                            return true;
                        }
                    }

                    player.sendMessage(Component.text(bundle.getString("commands.invalidArg")).color(NamedTextColor.RED));
                    return true;
                }
                case "border" -> {
                    if(args.length == 3){
                        if(args[1].equalsIgnoreCase("set")){
                            if(!args[2].equalsIgnoreCase("1") && !args[2].equalsIgnoreCase("2")) {
                                player.sendMessage(Component.text(bundle.getString("commands.invalidArg")).color(NamedTextColor.RED));
                                return true;
                            }

                            FileSpawn.getConfig().set("border" + args[2] + ".x", player.getLocation().getBlockX());
                            FileSpawn.getConfig().set("border" + args[2] + ".y", player.getLocation().getBlockY());
                            FileSpawn.getConfig().set("border" + args[2] + ".z", player.getLocation().getBlockZ());
                            FileSpawn.saveConfig();

                            player.sendMessage(Component.text(bundle.getString("commands.spawn.borderSet")).color(NamedTextColor.GREEN));
                        }
                    }

                    player.sendMessage(Component.text(bundle.getString("commands.invalidArg")).color(NamedTextColor.RED));
                    return true;
                }
                case "add" -> {
                    FileSpawn.getConfig().set("spawns", FileSpawn.getConfig().getInt("spawns") + 1);
                    FileSpawn.getConfig().set(FileSpawn.getConfig().getInt("spawns") + ".x", player.getLocation().getBlockX());
                    FileSpawn.getConfig().set(FileSpawn.getConfig().getInt("spawns") + ".y", player.getLocation().getBlockY());
                    FileSpawn.getConfig().set(FileSpawn.getConfig().getInt("spawns") + ".z", player.getLocation().getBlockZ());
                    FileSpawn.getConfig().set(FileSpawn.getConfig().getInt("spawns") + ".yaw", player.getLocation().getYaw());
                    FileSpawn.getConfig().set(FileSpawn.getConfig().getInt("spawns") + ".pitch", player.getLocation().getPitch());
                    FileSpawn.saveConfig();

                    player.sendMessage(Component.text(bundle.getString("commands.spawn.added")).color(NamedTextColor.GREEN));
                    return true;
                }
                case "remove" -> {
                    if(FileSpawn.getConfig().getInt("spawns") <= 0){
                        player.sendMessage(Component.text(bundle.getString("commands.spawn.noLeft")).color(NamedTextColor.RED));
                        return true;
                    }

                    FileSpawn.getConfig().set(String.valueOf(FileSpawn.getConfig().getInt("spawns")), null);
                    FileSpawn.getConfig().set("spawns", FileSpawn.getConfig().getInt("spawns") - 1);
                    FileSpawn.saveConfig();

                    player.sendMessage(Component.text(bundle.getString("commands.spawn.removed")).color(NamedTextColor.GREEN));
                    return true;
                }
                case "tp" -> {
                    if(args.length == 2){
                        int spawn;
                        try {
                            spawn = Integer.parseInt(args[1]);
                        } catch (Exception e){
                            player.sendMessage(Component.text(bundle.getString("commands.invalidArg")).color(NamedTextColor.RED));
                            return true;
                        }

                        if(spawn > FileSpawn.getConfig().getInt("spawns") || spawn <= 0){
                            player.sendMessage(Component.text(bundle.getString("commands.invalidArg")).color(NamedTextColor.RED));
                            return true;
                        }

                        //Admins only here, so no check needed
                        player.teleport(ManagerSpawn.getSpawnLocations().get(spawn - 1));
                        player.sendMessage(Component.text(bundle.getString("commands.spawn.teleported")).color(NamedTextColor.GREEN));
                    } else {
                        player.sendMessage(Component.text(bundle.getString("commands.invalidArg")).color(NamedTextColor.RED));
                    }
                    return true;
                }
            }
        } else {
            ResourceBundle bundle = ResourceBundle.getBundle("language_en");
            sender.sendMessage(Component.text(bundle.getString("commands.noPlayer")).color(NamedTextColor.RED));
        }
        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(sender instanceof Player player){
            if(!FileAdmins.isAdmin(player))
                return List.of();
        }

        if(args.length == 1) {
            return List.of("bypass", "border", "add", "remove", "tp");
        }
        if(args.length == 2){
            if(args[0].equalsIgnoreCase("bypass")){
                return List.of("building", "combat", "off");
            }
            if(args[0].equalsIgnoreCase("border")){
                return List.of("set");
            }
            if(args[0].equalsIgnoreCase("tp")){
                List<String> list = new ArrayList<>();
                for (int i = 1; i <= FileSpawn.getConfig().getInt("spawns"); i++) {
                    list.add(String.valueOf(i));
                }
                return list;
            }
        }
        if(args.length == 3){
            if(args[0].equalsIgnoreCase("border") && args[1].equalsIgnoreCase("set")){
                return List.of("1", "2");
            }
        }
        return List.of();
    }
}
