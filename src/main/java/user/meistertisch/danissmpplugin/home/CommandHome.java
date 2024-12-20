package user.meistertisch.danissmpplugin.home;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextReplacementConfig;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import user.meistertisch.danissmpplugin.Main;
import user.meistertisch.danissmpplugin.admin.teams.ManagerTeams;
import user.meistertisch.danissmpplugin.combatTimer.ManagerCombatTimer;
import user.meistertisch.danissmpplugin.files.FileHomes;
import user.meistertisch.danissmpplugin.files.FilePlayer;
import user.meistertisch.danissmpplugin.files.FileTeams;

import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

public class CommandHome implements TabExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(sender instanceof Player player){
            ResourceBundle bundle = ResourceBundle.getBundle("language_" + FilePlayer.getConfig().getString(player.getName() + ".lang"));

            if(args.length == 0 || args.length > 3){
                player.sendMessage(Component.text(bundle.getString("commands.invalidArg"), NamedTextColor.RED));
                return true;
            }

            switch (args[0].toLowerCase()){
                case "create" -> {
                    if(args.length != 2){
                        player.sendMessage(Component.text(bundle.getString("commands.invalidArg"), NamedTextColor.RED));
                        return true;
                    }

                    if(ManagerHome.getHomeCount(player.getName()) >= Main.getPlugin().getConfig().getInt("homeSystem.maxHomes")){
                        player.sendMessage(Component.text(bundle.getString("commands.home.maxHomes"), NamedTextColor.RED)
                                .replaceText(TextReplacementConfig.builder().match("%maxHomes%").replacement(Component.text(Main.getPlugin().getConfig().getInt("homeSystem.maxHomes"), NamedTextColor.GOLD)).build()));
                        return true;
                    }

                    if(ManagerHome.homeExists(player, args[1])){
                        player.sendMessage(Component.text(bundle.getString("commands.home.exists"), NamedTextColor.RED)
                                .replaceText(TextReplacementConfig.builder().match("%home%").replacement(Component.text(args[1], NamedTextColor.GOLD)).build()));
                        return true;
                    }

                    String team = FilePlayer.getConfig().getString(player.getName() + ".team", "");
                    String owner = ManagerHome.getHomeOwner(args[1]);

                    if(!player.getName().equals(owner) && ManagerTeams.getTeam(team).contains(owner) && FileHomes.getConfig().getBoolean(owner + "." + args[1] + ".shared")){
                        player.sendMessage(Component.text(bundle.getString("commands.home.notYourHome"), NamedTextColor.RED));
                        return true;
                    }

                    ManagerHome.addHome(player, args[1], player.getLocation());
                    player.sendMessage(Component.text(bundle.getString("commands.home.create"), NamedTextColor.GREEN)
                            .replaceText(TextReplacementConfig.builder().match("%home%").replacement(Component.text(args[1], NamedTextColor.GOLD)).build()));
                }
                case "remove", "delete" -> {
                    if(args.length != 2){
                        player.sendMessage(Component.text(bundle.getString("commands.invalidArg"), NamedTextColor.RED));
                        return true;
                    }

                    if(!ManagerHome.homeExists(player, args[1])){
                        player.sendMessage(Component.text(bundle.getString("commands.home.notExists"), NamedTextColor.RED)
                                .replaceText(TextReplacementConfig.builder().match("%home%").replacement(Component.text(args[1], NamedTextColor.GOLD)).build()));
                        return true;
                    }

                    String team = FilePlayer.getConfig().getString(player.getName() + ".team", "");
                    String owner = ManagerHome.getHomeOwner(args[1]);

                    if(!player.getName().equals(owner) && ManagerTeams.getTeam(team).contains(owner) && FileHomes.getConfig().getBoolean(owner + "." + args[1] + ".shared")){
                        player.sendMessage(Component.text(bundle.getString("commands.home.notYourHome"), NamedTextColor.RED));
                        return true;
                    }

                    ManagerHome.removeHome(player, args[1]);
                    player.sendMessage(Component.text(bundle.getString("commands.home.remove"), NamedTextColor.RED)
                            .replaceText(TextReplacementConfig.builder().match("%home%").replacement(Component.text(args[1], NamedTextColor.GOLD)).build()));
                }
                case "rename" -> {
                    if(args.length != 3){
                        player.sendMessage(Component.text(bundle.getString("commands.invalidArg"), NamedTextColor.RED));
                        return true;
                    }

                    if(!ManagerHome.homeExists(player, args[1])){
                        player.sendMessage(Component.text(bundle.getString("commands.home.notExists"), NamedTextColor.RED)
                                .replaceText(TextReplacementConfig.builder().match("%home%").replacement(Component.text(args[1], NamedTextColor.GOLD)).build()));
                        return true;
                    }

                    if(ManagerHome.homeExists(player, args[2])){
                        player.sendMessage(Component.text(bundle.getString("commands.home.exists"), NamedTextColor.RED)
                                .replaceText(TextReplacementConfig.builder().match("%home%").replacement(Component.text(args[2], NamedTextColor.GOLD)).build()));
                        return true;
                    }

                    String team = FilePlayer.getConfig().getString(player.getName() + ".team", "");
                    String owner = ManagerHome.getHomeOwner(args[1]);

                    if(!player.getName().equals(owner) && ManagerTeams.getTeam(team).contains(owner) && FileHomes.getConfig().getBoolean(owner + "." + args[1] + ".shared")){
                        player.sendMessage(Component.text(bundle.getString("commands.home.notYourHome"), NamedTextColor.RED));
                        return true;
                    }

                    ManagerHome.renameHome(player, args[1], args[2]);
                    player.sendMessage(Component.text(bundle.getString("commands.home.rename"), NamedTextColor.GREEN)
                            .replaceText(TextReplacementConfig.builder().match("%oldHome%").replacement(Component.text(args[1], NamedTextColor.GOLD)).build())
                            .replaceText(TextReplacementConfig.builder().match("%newHome%").replacement(Component.text(args[2], NamedTextColor.GOLD)).build()));
                }
                case "teleport", "tp" -> {
                    if(args.length != 2){
                        player.sendMessage(Component.text(bundle.getString("commands.invalidArg"), NamedTextColor.RED));
                        return true;
                    }

                    if(!ManagerHome.homeExists(player, args[1])){
                        player.sendMessage(Component.text(bundle.getString("commands.home.notExists"), NamedTextColor.RED)
                                .replaceText(TextReplacementConfig.builder().match("%home%").replacement(Component.text(args[1], NamedTextColor.GOLD)).build()));
                        return true;
                    }

                    if(ManagerCombatTimer.isInCombat(player)){
                        player.sendMessage(Component.text(bundle.getString("combatTimer")).color(NamedTextColor.RED));
                        return true;
                    }

                    String team = FilePlayer.getConfig().getString(player.getName() + ".team", "");
                    String owner = ManagerHome.getHomeOwner(args[1]);

                    if(!player.getName().equals(owner) && ManagerTeams.getTeam(team).contains(owner) && FileHomes.getConfig().getBoolean(owner + "." + args[1] + ".shared")){
                        player.sendMessage(Component.text(bundle.getString("commands.home.notYourHome"), NamedTextColor.RED));
                        return true;
                    }

                    ManagerHome.teleportHome(player, args[1]);
                    player.sendMessage(Component.text(bundle.getString("commands.home.teleport"), NamedTextColor.GREEN)
                            .replaceText(TextReplacementConfig.builder().match("%home%").replacement(Component.text(args[1], NamedTextColor.GOLD)).build()));
                }
//                case "share" -> {
//                    if(args.length != 2){
//                        player.sendMessage(Component.text(bundle.getString("commands.invalidArg"), NamedTextColor.RED));
//                        return true;
//                    }
//
//                    if(!ManagerHome.homeExists(player, args[1])){
//                        player.sendMessage(Component.text(bundle.getString("commands.home.notExists"), NamedTextColor.RED)
//                                .replaceText(TextReplacementConfig.builder().match("%home%").replacement(Component.text(args[1], NamedTextColor.GOLD)).build()));
//                        return true;
//                    }
//
//                    ManagerHome.shareHome(player, args[1]);
//                    player.sendMessage(Component.text(bundle.getString("commands.home.share"), NamedTextColor.GREEN)
//                            .replaceText(TextReplacementConfig.builder().match("%home%").replacement(Component.text(args[1], NamedTextColor.GOLD)).build()));
//                }
                default -> {
                    player.sendMessage(Component.text(bundle.getString("commands.invalidArg"), NamedTextColor.RED));
                    return true;
                }
            }

        } else {
            ResourceBundle bundle = ResourceBundle.getBundle("language_en");
            sender.sendMessage(Component.text(bundle.getString("commands.onlyPlayer"), NamedTextColor.RED));
            return true;
        }

        return true;
    }

    @Override
        public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(!(sender instanceof Player player)){
            return List.of();
        }
        if(args.length == 1){
            //TODO: Share and Unshare
            return List.of("create", "remove", "delete", "rename", "teleport", "tp");
        }
        if(args.length == 2){
            if(List.of("remove", "delete", "teleport", "tp", "rename").contains(args[0].toLowerCase(Locale.ROOT))){
                List<String> list = ManagerHome.getHomes(player.getName());
                if(args[0].equalsIgnoreCase("teleport") || args[0].equalsIgnoreCase("tp")){
                    if(!FileTeams.getConfig().getBoolean(player.getName() + ".isTeam")){
                        return list;
                    }

                    String team = FilePlayer.getConfig().getString(player.getName() + ".team", "");
                    for(String p : ManagerTeams.getTeam(team)){
                        for(String home : ManagerHome.getHomes(p)) {
                            if (FileHomes.getConfig().getBoolean(p + "." + home + ".shared")) {
                                list.add(home);
                            }
                        }
                    }
                }

                return list;
            }
        }
        return List.of();
    }
}
