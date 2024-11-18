package user.meistertisch.danissmpplugin.admin.teams;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextReplacementConfig;
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
import user.meistertisch.danissmpplugin.files.FileAdmins;
import user.meistertisch.danissmpplugin.files.FilePlayer;
import user.meistertisch.danissmpplugin.files.FileTeams;

import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

public class CommandTeams implements TabExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        ResourceBundle bundle = ResourceBundle.getBundle("language_en");
        if(sender instanceof Player player) {
            bundle = ResourceBundle.getBundle("language_" + FilePlayer.getConfig().getString(player.getName() + ".lang"));
            if(!FileAdmins.isAdmin(player)) {
                player.sendMessage(bundle.getString("commands.noAdmin"));
                return true;
            }
        }
        if(args.length == 0) {
            sender.sendMessage(bundle.getString("commands.invalidArg"));
            return true;
        }

        List<String> decorationList = List.of("italic", "underlined", "strikethrough", "remove");

        switch (args[0].toLowerCase()){
            case "create" -> {
                if(args.length < 2 || args.length > 6){
                    sender.sendMessage(bundle.getString("commands.invalidArg"));
                    return true;
                }
                if(FileTeams.getConfig().getKeys(false).contains(args[1])){
                    sender.sendMessage(Component.text(bundle.getString("commands.teams.alreadyExists")).color(NamedTextColor.RED));
                    return true;
                }
                if(args.length == 3 || args.length == 4){
                    String colorString = args[2];
                    int color;

                    if(Main.getPlugin().getConfig().getBoolean("teams.onlyMcColors")){
                        if(!NamedTextColor.NAMES.keys().stream().toList().contains(colorString)){
                            sender.sendMessage(Component.text(bundle.getString("commands.invalidArg")).color(NamedTextColor.RED));
                            return true;
                        }
                        color = NamedTextColor.NAMES.value(colorString).value();
                    } else {
                        if(NamedTextColor.NAMES.keys().stream().toList().contains(colorString)){
                            color = NamedTextColor.NAMES.value(colorString).value();
                        } else {
                            try {
                                color = Integer.parseInt(colorString, 16);
                            } catch (NumberFormatException e){
                                sender.sendMessage(Component.text(bundle.getString("commands.invalidArg")).color(NamedTextColor.RED));
                                return true;
                            }
                        }
                    }

                    if(!Main.getPlugin().getConfig().getBoolean("teams.sameColorMultipleTeams")){
                        for(String team : FileTeams.getConfig().getKeys(false)){
                            if(FileTeams.getConfig().getColor(team + ".color", Color.fromRGB(0x000000)).asRGB() == color){
                                Component teamName = Component.text(team).color(TextColor.color(color)).decorate(TextDecoration.BOLD);
                                if(FileTeams.getConfig().getString(team + ".decoration") != null){
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

                    if(args.length == 4){
                        if(!decorationList.contains(args[3].toLowerCase(Locale.ROOT)) || args[3].equalsIgnoreCase("remove")){
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
                if(args.length == 5 || args.length == 6){
                    String colorString = args[4];
                    int color;

                    if(Main.getPlugin().getConfig().getBoolean("teams.onlyMcColors")){
                        if(!NamedTextColor.NAMES.keys().stream().toList().contains(colorString)){
                            sender.sendMessage(Component.text(bundle.getString("commands.invalidArg")).color(NamedTextColor.RED));
                            return true;
                        }
                        color = NamedTextColor.NAMES.value(colorString).value();
                    } else {
                        if(NamedTextColor.NAMES.keys().stream().toList().contains(colorString)){
                            color = NamedTextColor.NAMES.value(colorString).value();
                        } else {
                            try {
                                color = Integer.parseInt(colorString, 16);
                            } catch (NumberFormatException e){
                                sender.sendMessage(Component.text(bundle.getString("commands.invalidArg")).color(NamedTextColor.RED));
                                return true;
                            }
                        }
                    }

                    if(!Main.getPlugin().getConfig().getBoolean("teams.sameColorMultipleTeams")){
                        for(String team : FileTeams.getConfig().getKeys(false)){
                            if(FileTeams.getConfig().getColor(team + ".color", Color.fromRGB(0x000000)).asRGB() == color){
                                Component teamName = Component.text(team).color(TextColor.color(color)).decorate(TextDecoration.BOLD);
                                if(FileTeams.getConfig().getString(team + ".decoration") != null){
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

                    if(args.length == 6){
                        if(!decorationList.contains(args[5].toLowerCase(Locale.ROOT)) || args[5].equalsIgnoreCase("remove")){
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
                if(args.length != 2){
                    sender.sendMessage(bundle.getString("commands.invalidArg"));
                    return true;
                }
                if(!FileTeams.getConfig().getKeys(false).contains(args[1])){
                    sender.sendMessage(Component.text(bundle.getString("commands.teams.notExists")).color(NamedTextColor.RED));
                    return true;
                }
                Component team = Component.text(args[1]).color(TextColor.color(FileTeams.getConfig().getColor(args[1] + ".color", Color.fromRGB(0x000000)).asRGB())).decorate(TextDecoration.BOLD);
                if(FileTeams.getConfig().getString(args[1] + ".decoration") != null){
                    team = team.decorate(TextDecoration.valueOf(FileTeams.getConfig().getString(args[1] + ".decoration", "bold").toUpperCase(Locale.ROOT)));
                }

                Component text = Component.text(bundle.getString("commands.teams.delete")).color(NamedTextColor.GREEN)
                        .replaceText(TextReplacementConfig.builder().match("%team%").replacement(team).build());
                sender.sendMessage(text);

                ManagerTeams.deleteTeam(args[1]);
                return true;
            }
            case "rename" -> {
                if(args.length != 3){
                    sender.sendMessage(bundle.getString("commands.invalidArg"));
                    return true;
                }
                if(!FileTeams.getConfig().getKeys(false).contains(args[1])){
                    sender.sendMessage(Component.text(bundle.getString("commands.teams.notExists")).color(NamedTextColor.RED));
                    return true;
                }
                if(FileTeams.getConfig().getKeys(false).contains(args[2])){
                    sender.sendMessage(Component.text(bundle.getString("commands.teams.alreadyExists")).color(NamedTextColor.RED));
                    return true;
                }
                Component team = Component.text(args[1]).color(TextColor.color(FileTeams.getConfig().getColor(args[1] + ".color", Color.fromRGB(0x000000)).asRGB())).decorate(TextDecoration.BOLD);
                if(FileTeams.getConfig().getString(args[1] + ".decoration") != null){
                    team = team.decorate(TextDecoration.valueOf(FileTeams.getConfig().getString(args[1] + ".decoration", "bold").toUpperCase(Locale.ROOT)));
                }

                ManagerTeams.setTeamName(args[1], args[2]);

                Component newTeam = Component.text(args[2]).color(TextColor.color(FileTeams.getConfig().getColor(args[2] + ".color", Color.fromRGB(0x000000)).asRGB())).decorate(TextDecoration.BOLD);
                if(FileTeams.getConfig().getString(args[2] + ".decoration") != null){
                    newTeam = newTeam.decorate(TextDecoration.valueOf(FileTeams.getConfig().getString(args[2] + ".decoration", "bold").toUpperCase(Locale.ROOT)));
                }
                Component text = Component.text(bundle.getString("commands.teams.rename")).color(NamedTextColor.GREEN)
                        .replaceText(TextReplacementConfig.builder().match("%team%").replacement(team).build())
                        .replaceText(TextReplacementConfig.builder().match("%newTeam%").replacement(newTeam).build());
                sender.sendMessage(text);

                return true;
            }
            case "color" -> {
                if (args.length != 3) {
                    sender.sendMessage(bundle.getString("commands.invalidArg"));
                    return true;
                }
                if (!FileTeams.getConfig().getKeys(false).contains(args[1])) {
                    sender.sendMessage(Component.text(bundle.getString("commands.teams.notExists")).color(NamedTextColor.RED));
                    return true;
                }
                String colorString = args[2];
                int color;

            }
        }
        


        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        List<String> decorationList = List.of("italic", "underlined", "strikethrough");

        if(args.length == 1){
            return List.of("create", "delete", "color", "rename", "decoration", "add", "remove", "setPrefixSuffix", "removePrefixSuffix", "list");
        }
        if(args.length == 2){
            if(args[0].equalsIgnoreCase("create"))
                return List.of();
            return FileTeams.getConfig().getKeys(false).stream().toList();
        }
        if(args.length == 3){
            switch (args[0].toLowerCase(Locale.ROOT)){
                case "create", "color" -> {
                    return NamedTextColor.NAMES.keys().stream().toList();
                }
                case "decoration" -> {
                    return decorationList;
                }
                case "add", "remove" -> {
                    return null;
                }
                default -> {
                    return List.of();
                }
            }
        }
        if(args.length == 4){
            switch (args[0].toLowerCase(Locale.ROOT)){
                case "create" -> {
                    if(NamedTextColor.NAMES.keys().stream().toList().contains(args[2])){
                        return decorationList;
                    }

                    try {
                        Integer.parseInt(args[2], 16);
                    } catch (NumberFormatException e){
                        return List.of();
                    }
                    return decorationList;
                }
            }
        }
        if(args.length == 5){
            switch (args[0].toLowerCase(Locale.ROOT)){
                case "create" -> {
                    if(!NamedTextColor.NAMES.keys().stream().toList().contains(args[2].toLowerCase(Locale.ROOT))){
                        return NamedTextColor.NAMES.keys().stream().toList();
                    }
                }
            }
        }
        if(args.length == 6){
            switch (args[0].toLowerCase(Locale.ROOT)){
                case "create" -> {
                    if(!NamedTextColor.NAMES.keys().stream().toList().contains(args[2].toLowerCase(Locale.ROOT))){
                        return decorationList;
                    }
                }
            }
        }
        return List.of();
    }
}
