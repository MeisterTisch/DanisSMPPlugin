package user.meistertisch.danissmpplugin.admin.teams;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextReplacementConfig;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import user.meistertisch.danissmpplugin.Main;
import user.meistertisch.danissmpplugin.files.FileAdmins;
import user.meistertisch.danissmpplugin.files.FilePlayer;
import user.meistertisch.danissmpplugin.files.FileTeams;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

public class CommandTeams implements TabExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        ResourceBundle bundle = ResourceBundle.getBundle("language_en");
        if (sender instanceof Player player) {
            bundle = ResourceBundle.getBundle("language_" + FilePlayer.getConfig().getString(player.getName() + ".lang"));
            if (!FileAdmins.isAdmin(player)) {
                player.sendMessage(Component.text(bundle.getString("commands.noAdmin"), NamedTextColor.RED));
                return true;
            }
        }
        if (args.length == 0) {
            sender.sendMessage(bundle.getString("commands.invalidArg"));
            return true;
        }

        List<String> decorationList = List.of("italic", "underlined", "strikethrough", "remove");

        switch (args[0].toLowerCase()) {
            case "create" -> {
                if (args.length < 2 || args.length > 6) {
                    sender.sendMessage(Component.text(bundle.getString("commands.invalidArg")).color(NamedTextColor.RED));
                    return true;
                }
                if (FileTeams.getConfig().getKeys(false).contains(args[1])) {
                    sender.sendMessage(Component.text(bundle.getString("commands.teams.alreadyExists")).color(NamedTextColor.RED));
                    return true;
                }
                if (args.length == 3 || args.length == 4) {
                    String colorString = args[2];
                    int color;

                    if (Main.getPlugin().getConfig().getBoolean("teams.onlyMcColors")) {
                        if (!NamedTextColor.NAMES.keys().stream().toList().contains(colorString)) {
                            sender.sendMessage(Component.text(bundle.getString("commands.invalidArg")).color(NamedTextColor.RED));
                            return true;
                        }
                        color = NamedTextColor.NAMES.value(colorString).value();
                    } else {
                        if (NamedTextColor.NAMES.keys().stream().toList().contains(colorString)) {
                            color = NamedTextColor.NAMES.value(colorString).value();
                        } else {
                            try {
                                color = Integer.parseInt(colorString, 16);
                            } catch (NumberFormatException e) {
                                sender.sendMessage(Component.text(bundle.getString("commands.invalidArg")).color(NamedTextColor.RED));
                                return true;
                            }
                        }
                    }

                    if (!Main.getPlugin().getConfig().getBoolean("teams.sameColorMultipleTeams")) {
                        for (String team : FileTeams.getConfig().getKeys(false)) {
                            if (FileTeams.getConfig().getColor(team + ".color", Color.fromRGB(0x000000)).asRGB() == color) {
                                Component teamName = Component.text(team).color(TextColor.color(color)).decorate(TextDecoration.BOLD);
                                if (FileTeams.getConfig().getString(team + ".decoration") != null) {
                                    teamName = teamName
                                            .decorate(TextDecoration.valueOf(FileTeams.getConfig().getString(team + ".decoration", "bold").toUpperCase(Locale.ROOT)));
                                }

                                sender.sendMessage(
                                        Component.text(bundle.getString("commands.teams.create.colorTaken")).color(NamedTextColor.RED)
                                                .replaceText(TextReplacementConfig.builder().match("%team%").replacement(teamName).build())
                                );
                                return true;
                            }
                        }
                    }

                    if (args.length == 4) {
                        if (!decorationList.contains(args[3].toLowerCase(Locale.ROOT)) || args[3].equalsIgnoreCase("remove")) {
                            sender.sendMessage(Component.text(bundle.getString("commands.invalidArg")).color(NamedTextColor.RED));
                            return true;
                        }
                        TextDecoration decoration = TextDecoration.valueOf(args[3].toUpperCase(Locale.ROOT));
                        ManagerTeams.createTeam(args[1], Color.fromRGB(color), decoration);

                        Component team = Component.text(args[1]).color(TextColor.color(color)).decorate(TextDecoration.BOLD).decorate(decoration);
                        Component text = Component.text(bundle.getString("commands.teams.create")).color(NamedTextColor.GREEN)
                                .replaceText(TextReplacementConfig.builder().match("%team%").replacement(team).build());
                        sender.sendMessage(text);
                    } else {
                        ManagerTeams.createTeam(args[1], Color.fromRGB(color), null);

                        Component team = Component.text(args[1]).color(TextColor.color(color)).decorate(TextDecoration.BOLD);
                        Component text = Component.text(bundle.getString("commands.teams.create")).color(NamedTextColor.GREEN)
                                .replaceText(TextReplacementConfig.builder().match("%team%").replacement(team).build());
                        sender.sendMessage(text);
                    }

                    return true;
                }
                if (args.length == 5 || args.length == 6) {
                    String colorString = args[4];
                    int color;

                    if (Main.getPlugin().getConfig().getBoolean("teams.onlyMcColors")) {
                        if (!NamedTextColor.NAMES.keys().stream().toList().contains(colorString)) {
                            sender.sendMessage(Component.text(bundle.getString("commands.invalidArg")).color(NamedTextColor.RED));
                            return true;
                        }
                        color = NamedTextColor.NAMES.value(colorString).value();
                    } else {
                        if (NamedTextColor.NAMES.keys().stream().toList().contains(colorString)) {
                            color = NamedTextColor.NAMES.value(colorString).value();
                        } else {
                            try {
                                color = Integer.parseInt(colorString, 16);
                            } catch (NumberFormatException e) {
                                sender.sendMessage(Component.text(bundle.getString("commands.invalidArg")).color(NamedTextColor.RED));
                                return true;
                            }
                        }
                    }

                    if (!Main.getPlugin().getConfig().getBoolean("teams.sameColorMultipleTeams")) {
                        for (String team : FileTeams.getConfig().getKeys(false)) {
                            if (FileTeams.getConfig().getColor(team + ".color", Color.fromRGB(0x000000)).asRGB() == color) {
                                Component teamName = Component.text(team).color(TextColor.color(color)).decorate(TextDecoration.BOLD);
                                if (FileTeams.getConfig().getString(team + ".decoration") != null) {
                                    teamName = teamName
                                            .decorate(TextDecoration.valueOf(FileTeams.getConfig().getString(team + ".decoration", "bold").toUpperCase(Locale.ROOT)));
                                }

                                sender.sendMessage(
                                        Component.text(bundle.getString("commands.teams.create.colorTaken")).color(NamedTextColor.RED)
                                                .replaceText(TextReplacementConfig.builder().match("%team%").replacement(teamName).build())
                                );
                                return true;
                            }
                        }
                    }

                    if (args.length == 6) {
                        if (!decorationList.contains(args[5].toLowerCase(Locale.ROOT)) || args[5].equalsIgnoreCase("remove")) {
                            sender.sendMessage(Component.text(bundle.getString("commands.invalidArg")).color(NamedTextColor.RED));
                            return true;
                        }
                        TextDecoration decoration = TextDecoration.valueOf(args[5].toUpperCase(Locale.ROOT));
                        ManagerTeams.createTeam(args[1], Color.fromRGB(color), decoration);
                        ManagerTeams.setTeamPrefixAndSuffix(args[1], args[2], args[3]);

                        Component team = Component.text(args[1]).color(TextColor.color(color)).decorate(TextDecoration.BOLD).decorate(decoration);
                        Component text = Component.text(bundle.getString("commands.teams.create")).color(NamedTextColor.GREEN)
                                .replaceText(TextReplacementConfig.builder().match("%team%").replacement(team).build());
                        sender.sendMessage(text);
                    } else {
                        ManagerTeams.createTeam(args[1], Color.fromRGB(color), null);
                        ManagerTeams.setTeamPrefixAndSuffix(args[1], args[2], args[3]);

                        Component team = Component.text(args[1]).color(TextColor.color(color)).decorate(TextDecoration.BOLD);
                        Component text = Component.text(bundle.getString("commands.teams.create")).color(NamedTextColor.GREEN)
                                .replaceText(TextReplacementConfig.builder().match("%team%").replacement(team).build());
                        sender.sendMessage(text);
                    }

                    return true;
                }
            }
            case "delete" -> {
                if (args.length != 2) {
                    sender.sendMessage(Component.text(bundle.getString("commands.invalidArg")).color(NamedTextColor.RED));
                    return true;
                }
                if (!FileTeams.getConfig().getKeys(false).contains(args[1])) {
                    sender.sendMessage(Component.text(bundle.getString("commands.teams.notExists")).color(NamedTextColor.RED));
                    return true;
                }
                Component team = Component.text(args[1]).color(TextColor.color(FileTeams.getConfig().getColor(args[1] + ".color", Color.fromRGB(0x000000)).asRGB())).decorate(TextDecoration.BOLD);
                if (FileTeams.getConfig().getString(args[1] + ".decoration") != null) {
                    team = team.decorate(TextDecoration.valueOf(FileTeams.getConfig().getString(args[1] + ".decoration", "bold").toUpperCase(Locale.ROOT)));
                }

                Component text = Component.text(bundle.getString("commands.teams.delete")).color(NamedTextColor.GREEN)
                        .replaceText(TextReplacementConfig.builder().match("%team%").replacement(team).build());
                sender.sendMessage(text);

                for(Player target : Bukkit.getOnlinePlayers()){
                    if(FilePlayer.getConfig().getString(target.getName() + ".team", "").equals(args[1])){
                        ResourceBundle bundleTarget = ResourceBundle.getBundle("language_" + FilePlayer.getConfig().getString(target.getName() + ".lang", "en"));
                        Component targetText = Component.text(bundleTarget.getString("commands.teams.delete.target")).color(NamedTextColor.RED);
                        target.sendMessage(targetText);
                    }
                }

                ManagerTeams.deleteTeam(args[1]);
                return true;
            }
            case "rename" -> {
                if (args.length != 3) {
                    sender.sendMessage(Component.text(bundle.getString("commands.invalidArg")).color(NamedTextColor.RED));
                    return true;
                }
                if (!FileTeams.getConfig().getKeys(false).contains(args[1])) {
                    sender.sendMessage(Component.text(bundle.getString("commands.teams.notExists")).color(NamedTextColor.RED));
                    return true;
                }
                if (FileTeams.getConfig().getKeys(false).contains(args[2])) {
                    sender.sendMessage(Component.text(bundle.getString("commands.teams.alreadyExists")).color(NamedTextColor.RED));
                    return true;
                }
                Component team = Component.text(args[1]).color(TextColor.color(FileTeams.getConfig().getColor(args[1] + ".color", Color.fromRGB(0x000000)).asRGB())).decorate(TextDecoration.BOLD);
                if (FileTeams.getConfig().getString(args[1] + ".decoration") != null) {
                    team = team.decorate(TextDecoration.valueOf(FileTeams.getConfig().getString(args[1] + ".decoration", "bold").toUpperCase(Locale.ROOT)));
                }

                ManagerTeams.setTeamName(args[1], args[2]);

                Component newTeam = Component.text(args[2]).color(TextColor.color(FileTeams.getConfig().getColor(args[2] + ".color", Color.fromRGB(0x000000)).asRGB())).decorate(TextDecoration.BOLD);
                if (FileTeams.getConfig().getString(args[2] + ".decoration") != null) {
                    newTeam = newTeam.decorate(TextDecoration.valueOf(FileTeams.getConfig().getString(args[2] + ".decoration", "bold").toUpperCase(Locale.ROOT)));
                }
                Component text = Component.text(bundle.getString("commands.teams.rename")).color(NamedTextColor.GREEN)
                        .replaceText(TextReplacementConfig.builder().match("%team%").replacement(team).build())
                        .replaceText(TextReplacementConfig.builder().match("%newTeam%").replacement(newTeam).build());
                sender.sendMessage(text);

                for(Player target : Bukkit.getOnlinePlayers()){
                    if(FilePlayer.getConfig().getString(target.getName() + ".team", "").equals(args[1])){
                        ResourceBundle bundleTarget = ResourceBundle.getBundle("language_" + FilePlayer.getConfig().getString(target.getName() + ".lang", "en"));
                        Component targetText = Component.text(bundleTarget.getString("commands.teams.rename.target")).color(NamedTextColor.GREEN)
                                .replaceText(TextReplacementConfig.builder().match("%team%").replacement(newTeam).build());
                        target.sendMessage(targetText);
                    }
                }

                return true;
            }
            case "color" -> {
                if (args.length != 3) {
                    sender.sendMessage(Component.text(bundle.getString("commands.invalidArg")).color(NamedTextColor.RED));
                    return true;
                }
                if (!FileTeams.getConfig().getKeys(false).contains(args[1])) {
                    sender.sendMessage(Component.text(bundle.getString("commands.teams.notExists")).color(NamedTextColor.RED));
                    return true;
                }
                String colorString = args[2];
                int color;

                if (Main.getPlugin().getConfig().getBoolean("teams.onlyMcColors")) {
                    if (!NamedTextColor.NAMES.keys().stream().toList().contains(colorString)) {
                        sender.sendMessage(Component.text(bundle.getString("commands.invalidArg")).color(NamedTextColor.RED));
                        return true;
                    }
                    color = NamedTextColor.NAMES.value(colorString).value();
                } else {
                    if (NamedTextColor.NAMES.keys().stream().toList().contains(colorString)) {
                        color = NamedTextColor.NAMES.value(colorString).value();
                    } else {
                        try {
                            color = Integer.parseInt(colorString, 16);
                        } catch (NumberFormatException e) {
                            sender.sendMessage(Component.text(bundle.getString("commands.invalidArg")).color(NamedTextColor.RED));
                            return true;
                        }
                    }
                }

                boolean isTaken = false;
                for (String teams : FileTeams.getConfig().getKeys(false)) {
                    if (FileTeams.getConfig().getColor(teams + ".color", Color.fromRGB(0x000000)).asRGB() == color) {
                        isTaken = true;
                        break;
                    }
                }
                if (!Main.getPlugin().getConfig().getBoolean("teams.sameColorMultipleTeams") && isTaken) {
                    sender.sendMessage(Component.text(bundle.getString("commands.teams.create.colorTaken")).color(NamedTextColor.RED));
                    return true;
                }

                ManagerTeams.setTeamColor(args[1], Color.fromRGB(color));

                Component team = Component.text(args[1]).color(TextColor.color(color)).decorate(TextDecoration.BOLD);
                if (FileTeams.getConfig().getString(args[1] + ".decoration") != null) {
                    team = team.decorate(TextDecoration.valueOf(FileTeams.getConfig().getString(args[1] + ".decoration", "bold").toUpperCase(Locale.ROOT)));
                }

                Component text = Component.text(bundle.getString("commands.teams.color")).color(NamedTextColor.GREEN)
                        .replaceText(TextReplacementConfig.builder().match("%team%").replacement(team).build());
                sender.sendMessage(text);

                for(Player target : Bukkit.getOnlinePlayers()){
                    if(FilePlayer.getConfig().getString(target.getName() + ".team", "").equals(args[1])){
                        ResourceBundle bundleTarget = ResourceBundle.getBundle("language_" + FilePlayer.getConfig().getString(target.getName() + ".lang", "en"));
                        Component targetText = Component.text(bundleTarget.getString("commands.teams.color.target")).color(NamedTextColor.GREEN);
                        target.sendMessage(targetText);
                    }
                }

                return true;
            }
            case "decoration" -> {
                if (args.length != 3) {
                    sender.sendMessage(Component.text(bundle.getString("commands.invalidArg")).color(NamedTextColor.RED));
                    return true;
                }
                if (!FileTeams.getConfig().getKeys(false).contains(args[1])) {
                    sender.sendMessage(Component.text(bundle.getString("commands.teams.notExists")).color(NamedTextColor.RED));
                    return true;
                }
                if (!decorationList.contains(args[2].toLowerCase(Locale.ROOT))) {
                    sender.sendMessage(Component.text(bundle.getString("commands.invalidArg")).color(NamedTextColor.RED));
                    return true;
                }

                if (args[2].equalsIgnoreCase("remove")) {
                    ManagerTeams.removeTeamDecoration(args[1]);
                    Component team = Component.text(args[1]).color(TextColor.color(FileTeams.getConfig().getColor(args[1] + ".color", Color.fromRGB(0x000000)).asRGB())).decorate(TextDecoration.BOLD);
                    Component text = Component.text(bundle.getString("commands.teams.decoration.remove")).color(NamedTextColor.GREEN)
                            .replaceText(TextReplacementConfig.builder().match("%team%").replacement(team).build());
                    sender.sendMessage(text);
                    return true;
                }

                TextDecoration decoration = TextDecoration.valueOf(args[2].toUpperCase(Locale.ROOT));
                ManagerTeams.setTeamDecoration(args[1], decoration);
                Component team = Component.text(args[1]).color(TextColor.color(FileTeams.getConfig().getColor(args[1] + ".color", Color.fromRGB(0x000000)).asRGB())).decorate(TextDecoration.BOLD).decorate(decoration);
                Component text = Component.text(bundle.getString("commands.teams.decoration")).color(NamedTextColor.GREEN)
                        .replaceText(TextReplacementConfig.builder().match("%team%").replacement(team).build());
                sender.sendMessage(text);

                for (Player target : Bukkit.getOnlinePlayers()){
                    if(FilePlayer.getConfig().getString(target.getName() + ".team", "").equals(args[1])){
                        ResourceBundle bundleTarget = ResourceBundle.getBundle("language_" + FilePlayer.getConfig().getString(target.getName() + ".lang", "en"));
                        Component targetText = Component.text(bundleTarget.getString("commands.teams.decoration.target")).color(NamedTextColor.GREEN);
                        target.sendMessage(targetText);
                    }
                }

                return true;
            }
            case "setprefixsuffix" -> {
                if (args.length != 4) {
                    sender.sendMessage(Component.text(bundle.getString("commands.invalidArg")).color(NamedTextColor.RED));
                    return true;
                }
                if (!FileTeams.getConfig().getKeys(false).contains(args[1])) {
                    sender.sendMessage(Component.text(bundle.getString("commands.teams.notExists")).color(NamedTextColor.RED));
                    return true;
                }

                ManagerTeams.setTeamPrefixAndSuffix(args[1], args[2], args[3]);

                Component prefix = Component.text(args[2]).color(TextColor.color(FileTeams.getConfig().getColor(args[1] + ".color", Color.fromRGB(0x000000)).asRGB())).decorate(TextDecoration.BOLD);
                Component suffix = Component.text(args[3]).color(TextColor.color(FileTeams.getConfig().getColor(args[1] + ".color", Color.fromRGB(0x000000)).asRGB())).decorate(TextDecoration.BOLD);
                Component team = Component.text(args[1]).color(TextColor.color(FileTeams.getConfig().getColor(args[1] + ".color", Color.fromRGB(0x000000)).asRGB())).decorate(TextDecoration.BOLD);

                if (FileTeams.getConfig().getString(args[1] + ".decoration") != null) {
                    team = team.decorate(TextDecoration.valueOf(FileTeams.getConfig().getString(args[1] + ".decoration", "bold").toUpperCase(Locale.ROOT)));
                    prefix = prefix.decorate(TextDecoration.valueOf(FileTeams.getConfig().getString(args[1] + ".decoration", "bold").toUpperCase(Locale.ROOT)));
                    suffix = suffix.decorate(TextDecoration.valueOf(FileTeams.getConfig().getString(args[1] + ".decoration", "bold").toUpperCase(Locale.ROOT)));
                }

                Component text = Component.text(bundle.getString("commands.teams.setPrefixSuffix")).color(NamedTextColor.GREEN)
                        .replaceText(TextReplacementConfig.builder().match("%team%").replacement(team).build())
                        .replaceText(TextReplacementConfig.builder().match("%prefix%").replacement(prefix).build())
                        .replaceText(TextReplacementConfig.builder().match("%suffix%").replacement(suffix).build());

                sender.sendMessage(text);

                for (Player target : Bukkit.getOnlinePlayers()){
                    if(FilePlayer.getConfig().getString(target.getName() + ".team", "").equals(args[1])){
                        ResourceBundle bundleTarget = ResourceBundle.getBundle("language_" + FilePlayer.getConfig().getString(target.getName() + ".lang", "en"));
                        Component targetText = Component.text(bundleTarget.getString("commands.teams.setPrefixSuffix.target")).color(NamedTextColor.GREEN)
                                .replaceText(TextReplacementConfig.builder().match("%prefix%").replacement(prefix).build())
                                .replaceText(TextReplacementConfig.builder().match("%suffix%").replacement(suffix).build());
                        target.sendMessage(targetText);
                    }
                }

                return true;
            }
            case "removeprefixsuffix" -> {
                if (args.length != 2) {
                    sender.sendMessage(Component.text(bundle.getString("commands.invalidArg")).color(NamedTextColor.RED));
                    return true;
                }
                if (!FileTeams.getConfig().getKeys(false).contains(args[1])) {
                    sender.sendMessage(Component.text(bundle.getString("commands.teams.notExists")).color(NamedTextColor.RED));
                    return true;
                }
                ManagerTeams.removeTeamPrefixAndSuffix(args[1]);

                Component team = Component.text(args[1]).color(TextColor.color(FileTeams.getConfig().getColor(args[1] + ".color", Color.fromRGB(0x000000)).asRGB())).decorate(TextDecoration.BOLD);
                if(FileTeams.getConfig().getString(args[1] + ".decoration") != null){
                    team = team.decorate(TextDecoration.valueOf(FileTeams.getConfig().getString(args[1] + ".decoration", "bold").toUpperCase(Locale.ROOT)));
                }

                Component text = Component.text(bundle.getString("commands.teams.removePrefixSuffix")).color(NamedTextColor.GREEN)
                        .replaceText(TextReplacementConfig.builder().match("%team%").replacement(team).build());
                sender.sendMessage(text);

                for(Player target : Bukkit.getOnlinePlayers()){
                    if(FilePlayer.getConfig().getString(target.getName() + ".team", "").equals(args[1])){
                        ResourceBundle bundleTarget = ResourceBundle.getBundle("language_" + FilePlayer.getConfig().getString(target.getName() + ".lang", "en"));
                        Component targetText = Component.text(bundleTarget.getString("commands.teams.removePrefixSuffix.target")).color(NamedTextColor.RED);
                        target.sendMessage(targetText);
                    }
                }

                return true;
            }
            case "add" -> {
                if (args.length != 3) {
                    sender.sendMessage(Component.text(bundle.getString("commands.invalidArg")).color(NamedTextColor.RED));
                    return true;
                }
                if (!FileTeams.getConfig().getKeys(false).contains(args[1])) {
                    sender.sendMessage(Component.text(bundle.getString("commands.teams.notExists")).color(NamedTextColor.RED));
                    return true;
                }

                String target = args[2];
                ManagerTeams.addMember(args[1], target);

                Component team = Component.text(args[1]).color(TextColor.color(FileTeams.getConfig().getColor(args[1] + ".color", Color.fromRGB(0x000000)).asRGB())).decorate(TextDecoration.BOLD);
                if(FileTeams.getConfig().getString(args[1] + ".decoration") != null){
                    team = team.decorate(TextDecoration.valueOf(FileTeams.getConfig().getString(args[1] + ".decoration", "bold").toUpperCase(Locale.ROOT)));
                }

                Component targetComp = Component.text(target).color(NamedTextColor.GOLD);

                Component text = Component.text(bundle.getString("commands.teams.add")).color(NamedTextColor.GREEN)
                        .replaceText(TextReplacementConfig.builder().match("%team%").replacement(team).build())
                        .replaceText(TextReplacementConfig.builder().match("%target%").replacement(targetComp).build());

                sender.sendMessage(text);

                for(Player p : Bukkit.getOnlinePlayers()){
                    if(p == sender){
                        continue;
                    }

                    ResourceBundle pBundle = ResourceBundle.getBundle("language_" + FilePlayer.getConfig().getString(p.getName() + ".lang", "en"));

                    if(p.getName().equals(target)){
                        Component pText = Component.text(pBundle.getString("commands.teams.add.target")).color(NamedTextColor.GREEN)
                                .replaceText(TextReplacementConfig.builder().match("%team%").replacement(team).build());
                        p.sendMessage(pText);
                        continue;
                    }

                    if(FilePlayer.getConfig().getString(p.getName() + ".team", "").equals(args[1])){
                        Component pText = Component.text(pBundle.getString("commands.teams.add.teamMessage")).color(NamedTextColor.GREEN)
                                .replaceText(TextReplacementConfig.builder().match("%target%").replacement(targetComp).build());
                        p.sendMessage(pText);
                    }
                }
            }
            case "remove" -> {
                if (args.length != 2) {
                    sender.sendMessage(Component.text(bundle.getString("commands.invalidArg")).color(NamedTextColor.RED));
                    return true;
                }

                String target = args[1];

                if(!FilePlayer.getConfig().getBoolean(target + ".isTeam")){
                    sender.sendMessage(Component.text(bundle.getString("commands.teams.remove.notInTeam")).color(NamedTextColor.RED)
                            .replaceText(TextReplacementConfig.builder().match("%target%").replacement(Component.text(target).color(NamedTextColor.GOLD)).build()));
                    return true;
                }

                String teamName = FilePlayer.getConfig().getString(target + ".team", "null");

                Component team = Component.text(teamName).color(TextColor.color(FileTeams.getConfig().getColor(teamName + ".color", Color.fromRGB(0x000000)).asRGB())).decorate(TextDecoration.BOLD);
                if(FileTeams.getConfig().getString(teamName + ".decoration") != null){
                    team = team.decorate(TextDecoration.valueOf(FileTeams.getConfig().getString(teamName + ".decoration", "bold").toUpperCase(Locale.ROOT)));
                }

                Component targetComp = Component.text(target).color(NamedTextColor.GOLD);

                Component text = Component.text(bundle.getString("commands.teams.remove")).color(NamedTextColor.RED)
                        .replaceText(TextReplacementConfig.builder().match("%team%").replacement(team).build())
                        .replaceText(TextReplacementConfig.builder().match("%target%").replacement(targetComp).build());

                sender.sendMessage(text);

                for(Player p : Bukkit.getOnlinePlayers()){
                    if(p == sender){
                        continue;
                    }

                    ResourceBundle pBundle = ResourceBundle.getBundle("language_" + FilePlayer.getConfig().getString(p.getName() + ".lang", "en"));

                    if(p.getName().equals(target)){
                        Component pText = Component.text(pBundle.getString("commands.teams.remove.target")).color(NamedTextColor.RED)
                                .replaceText(TextReplacementConfig.builder().match("%team%").replacement(team).build());
                        p.sendMessage(pText);
                        continue;
                    }

                    if(FilePlayer.getConfig().getString(p.getName() + ".team", "").equals(args[1])){
                        Component pText = Component.text(pBundle.getString("commands.teams.remove.teamMessage")).color(NamedTextColor.RED)
                                .replaceText(TextReplacementConfig.builder().match("%target%").replacement(targetComp).build());
                        p.sendMessage(pText);
                    }
                }

                ManagerTeams.removeMember(target);
            }
            case "list" -> {
                if (args.length != 1 && args.length != 2) {
                    sender.sendMessage(Component.text(bundle.getString("commands.invalidArg")).color(NamedTextColor.RED));
                    return true;
                }

                if(args.length == 1){
                    Component text = Component.text(bundle.getString("commands.teams.list")).color(NamedTextColor.GREEN);
                    sender.sendMessage(text);

                    boolean isEmpty = true;
                    for (String team : FileTeams.getConfig().getKeys(false)) {
                        isEmpty = false;
                        Component minus = Component.text("- ").color(NamedTextColor.GRAY);
                        Component teamName = Component.text(team).color(TextColor.color(FileTeams.getConfig().getColor(team + ".color", Color.fromRGB(0x000000)).asRGB())).decorate(TextDecoration.BOLD);
                        if (FileTeams.getConfig().getString(team + ".decoration") != null) {
                            teamName = teamName.decorate(TextDecoration.valueOf(FileTeams.getConfig().getString(team + ".decoration", "bold").toUpperCase(Locale.ROOT)));
                        }

                        sender.sendMessage(minus.append(teamName));
                    }
                    if(isEmpty)
                        sender.sendMessage(Component.text(bundle.getString("commands.teams.list.noTeams")).color(NamedTextColor.RED));

                    return true;
                } else {
                    String team = args[1];

                    if(!FileTeams.getConfig().getKeys(false).contains(team)){
                        sender.sendMessage(Component.text(bundle.getString("commands.teams.notExists")).color(NamedTextColor.RED));
                        return true;
                    }

                    Component teamName = Component.text(team).color(TextColor.color(FileTeams.getConfig().getColor(team + ".color", Color.fromRGB(0x000000)).asRGB())).decorate(TextDecoration.BOLD);
                    if (FileTeams.getConfig().getString(team + ".decoration") != null) {
                        teamName = teamName.decorate(TextDecoration.valueOf(FileTeams.getConfig().getString(team + ".decoration", "bold").toUpperCase(Locale.ROOT)));
                    }

                    Component text = Component.text(bundle.getString("commands.teams.list.team")).color(NamedTextColor.GREEN)
                            .replaceText(TextReplacementConfig.builder().match("%team%").replacement(teamName).build());
                    sender.sendMessage(text);


                    boolean isEmpty = true;
                    for (String player : FilePlayer.getConfig().getKeys(false)) {
                        if(!FilePlayer.getConfig().getString(player + ".team", "").equals(team)){
                            continue;
                        }
                        Component minus = Component.text("- ").color(NamedTextColor.GRAY);
                        sender.sendMessage(minus.append(Component.text(player)));
                        isEmpty = false;
                    }
                    if(isEmpty)
                        sender.sendMessage(Component.text(bundle.getString("commands.teams.list.emptyTeam")).color(NamedTextColor.RED)
                                .replaceText(TextReplacementConfig.builder().match("%team%").replacement(teamName).build()));
                    return true;
                }
            }
        }

        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        List<String> decorationList = List.of("italic", "underlined", "strikethrough");
        List<String> decorationListAndRemove = List.of("italic", "underlined", "strikethrough", "remove");

        if(sender instanceof Player player){
            if(!FileAdmins.isAdmin(player)){
                return List.of();
            }
        }

        if (args.length == 1) {
            return List.of("create", "delete", "color", "rename", "decoration", "add", "remove", "setPrefixSuffix", "removePrefixSuffix", "list");
        }
        if (args.length == 2) {
            if (args[0].equalsIgnoreCase("create"))
                return List.of();
            if(args[0].equalsIgnoreCase("remove")){
                List<String> list = new ArrayList<>();
                for(Player player : Bukkit.getOnlinePlayers()){
                    if(FilePlayer.getConfig().getBoolean(player.getName() + ".isTeam")){
                        list.add(player.getName());
                    }
                }
                return list;
            }
            if(args[0].equalsIgnoreCase("list")){
                return FileTeams.getConfig().getKeys(false).stream().toList();
            }
            return FileTeams.getConfig().getKeys(false).stream().toList();
        }
        if (args.length == 3) {
            switch (args[0].toLowerCase(Locale.ROOT)) {
                case "create", "color" -> {
                    return NamedTextColor.NAMES.keys().stream().toList();
                }
                case "decoration" -> {
                    return decorationListAndRemove;
                }
                case "add" -> {
                    return null;
                }
                default -> {
                    return List.of();
                }
            }
        }
        if (args.length == 4) {
            switch (args[0].toLowerCase(Locale.ROOT)) {
                case "create" -> {
                    if (NamedTextColor.NAMES.keys().stream().toList().contains(args[2])) {
                        return decorationList;
                    }

                    try {
                        Integer.parseInt(args[2], 16);
                    } catch (NumberFormatException e) {
                        return List.of();
                    }
                    return decorationList;
                }
            }
        }
        if (args.length == 5) {
            switch (args[0].toLowerCase(Locale.ROOT)) {
                case "create" -> {
                    if (!NamedTextColor.NAMES.keys().stream().toList().contains(args[2].toLowerCase(Locale.ROOT))) {
                        return NamedTextColor.NAMES.keys().stream().toList();
                    }
                }
            }
        }
        if (args.length == 6) {
            switch (args[0].toLowerCase(Locale.ROOT)) {
                case "create" -> {
                    if (!NamedTextColor.NAMES.keys().stream().toList().contains(args[2].toLowerCase(Locale.ROOT))) {
                        return decorationList;
                    }
                }
            }
        }
        return List.of();
    }
}
