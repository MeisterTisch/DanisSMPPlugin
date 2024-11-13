package user.meistertisch.danissmpplugin.sccs;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextReplacementConfig;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import user.meistertisch.danissmpplugin.Main;
import user.meistertisch.danissmpplugin.files.FileAdmins;
import user.meistertisch.danissmpplugin.files.FilePlayer;
import user.meistertisch.danissmpplugin.files.FileSlimeChunks;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

public class CommandSCCS implements TabExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        ResourceBundle bundle = ResourceBundle.getBundle("language_en");

        if(commandSender instanceof Player player){
            bundle = ResourceBundle.getBundle("language_" + FilePlayer.getConfig().getString(player.getName() + ".lang"));
            boolean isAdmin = FileAdmins.isAdmin(player);

            if(strings.length == 0){
                Component component = ManagerSCCS.getSlimeChunksComponent(player.getName(), null);
                Component text = Component.text(bundle.getString("sccs.get")).color(TextColor.color(Main.getSecondaryColor())).append(component);
                player.sendMessage(text);
                return true;
            }

            switch (strings[0].toLowerCase(Locale.ROOT)){
                case "list" -> {
                    if(strings.length == 1){
                        Component component = ManagerSCCS.getSlimeChunksComponent(player.getName(), null);
                        Component text = Component.text(bundle.getString("sccs.get")).color(TextColor.color(Main.getSecondaryColor())).append(component);
                        player.sendMessage(text);
                        return true;
                    }
                    if(strings.length == 2){
                        Component component = ManagerSCCS.getSlimeChunksComponent(strings[1], null);
                        Component text = Component.text(bundle.getString("sccs.target.getFoundChunks")).color(TextColor.color(Main.getSecondaryColor()))
                                .replaceText(TextReplacementConfig.builder().match("%target%")
                                        .replacement(Component.text(strings[1]).color(TextColor.color(Main.getPrimaryColor()))).build())
                                .append(component);
                        player.sendMessage(text);
                        return true;
                    }
                    player.sendMessage(Component.text(bundle.getString("commands.invalidArg")).color(NamedTextColor.RED));
                }
                case "check" -> {
                    if(strings.length != 1){
                        player.sendMessage(Component.text(bundle.getString("commands.invalidArg")).color(NamedTextColor.RED));
                        return true;
                    }

                    if(FilePlayer.getConfig().getInt(player.getName() + ".sccsSearchesLeft") == 0){
                        player.sendMessage(Component.text(bundle.getString("sccs.noChecksLeft")).color(NamedTextColor.RED));
                        return true;
                    }

                    List<Chunk> foundChunks = new ArrayList<>();
                    boolean onlyCenter = Main.getPlugin().getConfig().getBoolean("slimeChunkCheck.onlyCenter");
                    boolean found = false;

                    int x = player.getLocation().getChunk().getX();
                    int z = player.getLocation().getChunk().getZ();

                    for(int i = -1; i <= 1; i++){
                        if(onlyCenter) i = 0;

                        for(int j = -1; j <= 1; j++){
                            if(onlyCenter) j = 0;

                            if(ManagerSCCS.isSlimeChunk(x + i, z + j, player.getWorld())){
                                found = true;

                                World world = player.getWorld();
                                foundChunks.add(world.getChunkAt(x + i, z + j));
                                if(Main.getPlugin().getConfig().getBoolean("slimeChunkCheck.save"))
                                    ManagerSCCS.saveSlimeChunk(player, world.getChunkAt(x + i, z + j));
                            }

                            if(onlyCenter){
                                break;
                            }
                        }
                        if(onlyCenter){
                            break;
                        }
                    }

                    if(!(!found && Main.getPlugin().getConfig().getBoolean("slimeChunkCheck.noneFoundNoRemove"))) {
                        FilePlayer.getConfig().set(player.getName() + ".sccsSearchesLeft", FilePlayer.getConfig().getInt(player.getName() + ".sccsSearchesLeft") - 1);
                        FilePlayer.saveConfig();
                    }

                    if(!found){
                        player.sendMessage(Component.text(bundle.getString("sccs.noChunksFound")).color(NamedTextColor.RED));
                        return true;
                    }

                    Component text = Component.text(bundle.getString("sccs.found")).color(NamedTextColor.GREEN)
                            .replaceText(TextReplacementConfig.builder().match("%number%").replacement(Component.text(foundChunks.size()).color(NamedTextColor.GOLD)).build());

                    player.sendMessage(text.append(ManagerSCCS.getSlimeChunksComponent(player.getName(), foundChunks)));
                }
                case "share" -> {
                    if(strings.length != 1){
                        player.sendMessage(Component.text(bundle.getString("commands.invalidArg")).color(NamedTextColor.RED));
                        return true;
                    }

                    if(!FilePlayer.getConfig().getBoolean(player.getName() + ".isTeam")){
                        player.sendMessage(Component.text(bundle.getString("sccs.notInTeam")).color(NamedTextColor.RED));
                        return true;
                    }

                    if(FileSlimeChunks.getConfig().getInt(player.getName() + ".foundChunks") == 0){
                        player.sendMessage(Component.text(bundle.getString("sccs.noChunksFoundYet")).color(NamedTextColor.RED));
                        return true;
                    }

                    List<Player> team = new ArrayList<>();
                    String teamName = FilePlayer.getConfig().getString(player.getName() + ".team");

                    for(Player p : Bukkit.getOnlinePlayers()){
                        if(FilePlayer.getConfig().getBoolean(p.getName() + ".isTeam") && FilePlayer.getConfig().getString(p.getName() + ".team", "").equals(teamName)){
                            team.add(p);
                        }
                    }

                    Component slimes = ManagerSCCS.getSlimeChunksComponent(player.getName(), null);

                    for(Player p : team){
                        if(p == player) {
                            player.sendMessage(Component.text(bundle.getString("sccs.teamShared.sharer")).color(TextColor.color(Main.getSecondaryColor())).append(slimes));
                            continue;
                        }

                        ResourceBundle pBundle = ResourceBundle.getBundle("language_" + FilePlayer.getConfig().getString(p.getName() + ".lang"));
                        Component text = Component.text(pBundle.getString("sccs.teamShared")).color(TextColor.color(Main.getSecondaryColor()))
                                .replaceText(TextReplacementConfig.builder().match("%player%").replacement(Component.text(player.getName()).color(TextColor.color(Main.getPrimaryColor()))).build());
                        p.sendMessage(text.append(slimes));
                    }
                }
                case "reset" -> {
                    if(!isAdmin){
                        player.sendMessage(Component.text(bundle.getString("commands.noAdmin")).color(NamedTextColor.RED));
                        return true;
                    }

                    if(strings.length == 1 || (strings.length == 2 && !strings[1].equalsIgnoreCase("confirm"))){
                        Component cmd = Component.text("/scc reset confirm").color(NamedTextColor.GOLD).clickEvent(
                                ClickEvent.clickEvent(ClickEvent.Action.RUN_COMMAND, "/scc reset confirm")
                        ).decorate(TextDecoration.UNDERLINED);
                        Component msg = Component.text(bundle.getString("sccs.reset.confirm")).color(NamedTextColor.RED).append(cmd);

                        player.sendMessage(msg);
                        return true;
                    }
                    if(strings.length == 2 && strings[1].equalsIgnoreCase("confirm")){
                        ManagerSCCS.reset();

                        for(Player p : Bukkit.getOnlinePlayers()){
                            ResourceBundle pBundle = ResourceBundle.getBundle("language_" + FilePlayer.getConfig().getString(p.getName() + ".lang"));
                            p.sendMessage(Component.text(pBundle.getString("sccs.reset")).color(TextColor.color(Main.getPrimaryColor())));
                        }

                        return true;
                    }
                    player.sendMessage(Component.text(bundle.getString("commands.invalidArg")).color(NamedTextColor.RED));
                }
                case "add", "remove", "get" -> {
                    if(!isAdmin){
                        player.sendMessage(Component.text(bundle.getString("commands.noAdmin")).color(NamedTextColor.RED));
                        return true;
                    }

                    if(strings.length == 2 && strings[0].equalsIgnoreCase("get")){
                        Player target = Bukkit.getPlayer(strings[1]);
                        if(target == null){
                            player.sendMessage(Component.text(bundle.getString("commands.invalidTarget")).color(NamedTextColor.RED));
                            return true;
                        }

                        int targetNumber = FilePlayer.getConfig().getInt(target.getName() + ".sccsSearchesLeft");
                        Component text = Component.text(bundle.getString("sccs.target.getChecks")).color(TextColor.color(Main.getSecondaryColor()))
                                .replaceText(TextReplacementConfig.builder().match("%target%")
                                        .replacement(Component.text(target.getName()).color(TextColor.color(Main.getPrimaryColor()))).build())
                                .replaceText(TextReplacementConfig.builder().match("%number%").replacement(Component.text(targetNumber).color(TextColor.color(Main.getPrimaryColor()))).build());

                        player.sendMessage(text);
                        return true;
                    }

                    if(strings.length == 3 && List.of("add", "remove").contains(strings[0].toLowerCase(Locale.ROOT))){
                        Player target = Bukkit.getPlayer(strings[1]);
                        if(target == null){
                            player.sendMessage(Component.text(bundle.getString("commands.invalidTarget")).color(NamedTextColor.RED));
                            return true;
                        }

                        int targetNumber = FilePlayer.getConfig().getInt(target.getName() + ".sccsSearchesLeft");
                        int number;

                        try {
                            number = Integer.parseInt(strings[2]);
                        } catch (NumberFormatException e) {
                            player.sendMessage(Component.text(bundle.getString("commands.invalidArg")).color(NamedTextColor.RED));
                            return true;
                        }



                        if(number <= 0){
                            player.sendMessage(Component.text(bundle.getString("commands.invalidArg")).color(NamedTextColor.RED));
                            return true;
                        }


                        boolean isAdding = strings[0].equalsIgnoreCase("add");
                        if(!isAdding && targetNumber == 0){
                            player.sendMessage(Component.text(bundle.getString("sccs.target.alreadyZero")).color(NamedTextColor.RED)
                                    .replaceText(TextReplacementConfig.builder().match("%target%").replacement(target.getName()).build()));
                            return true;
                        }

                        if(number > targetNumber && !isAdding)
                            number = targetNumber;

                        Component text;
                        ResourceBundle targetBundle = ResourceBundle.getBundle("language_" + FilePlayer.getConfig().getString(target.getName() + ".lang"));



                        if(isAdding){
                            FilePlayer.getConfig().set(target.getName() + ".sccsSearchesLeft", targetNumber + number);
                            FilePlayer.saveConfig();
                            text = Component.text(bundle.getString("sccs.added")).color(NamedTextColor.GREEN)
                                    .replaceText(TextReplacementConfig.builder()
                                            .match("%number%").replacement(Component.text(number).color(NamedTextColor.GOLD)).build())
                                    .replaceText(TextReplacementConfig.builder()
                                            .match("%target%").replacement(Component.text(target.getName()).color(NamedTextColor.GOLD)).build())
                                    .replaceText(TextReplacementConfig.builder()
                                            .match("%numberNow%").replacement(Component.text(targetNumber + number).color(NamedTextColor.GOLD)).build());
                            target.sendMessage(Component.text(targetBundle.getString("sccs.target.added")).color(NamedTextColor.GREEN)
                                    .replaceText(TextReplacementConfig.builder().match("%number%").replacement(Component.text(number).color(NamedTextColor.GOLD)).build())
                                            .replaceText(TextReplacementConfig.builder()
                                            .match("%numberNow%").replacement(Component.text(targetNumber + number).color(NamedTextColor.GOLD)).build())
                            );
                        } else {
                            FilePlayer.getConfig().set(target.getName() + ".sccsSearchesLeft", targetNumber - number);
                            FilePlayer.saveConfig();
                            text = Component.text(bundle.getString("sccs.removed")).color(NamedTextColor.RED)
                                    .replaceText(TextReplacementConfig.builder()
                                            .match("%number%").replacement(Component.text(number).color(NamedTextColor.GOLD)).build())
                                    .replaceText(TextReplacementConfig.builder()
                                            .match("%target%").replacement(Component.text(target.getName()).color(NamedTextColor.GOLD)).build())
                                    .replaceText(TextReplacementConfig.builder()
                                            .match("%numberNow%").replacement(Component.text(targetNumber - number).color(NamedTextColor.GOLD)).build());
                            target.sendMessage(Component.text(targetBundle.getString("sccs.target.removed")).color(NamedTextColor.GREEN)
                                    .replaceText(TextReplacementConfig.builder().match("%number%").replacement(Component.text(number).color(NamedTextColor.GOLD)).build())
                                    .replaceText(TextReplacementConfig.builder()
                                            .match("%numberNow%").replacement(Component.text(targetNumber - number).color(NamedTextColor.GOLD)).build())
                            );
                        }

                        player.sendMessage(text);
                        return true;
                    }
                }
            }

        } else {
            commandSender.sendMessage(Component.text(bundle.getString("commands.onlyPlayer")).color(NamedTextColor.RED));
            return true;
        }
        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if(!(commandSender instanceof Player player)){
            return List.of();
        }

        List<String> list = new ArrayList<>(List.of("list", "check", "share"));
        if(FileAdmins.isAdmin(player)) {
            list.addAll(List.of("reset", "add", "remove", "get"));
        }

        if(strings.length == 2){
            switch (strings[0].toLowerCase()){
                case "reset" -> {
                    return List.of("confirm");
                }
                case "add", "remove", "get", "list" -> {
                    return null;
                }
            }
        }
        if(strings.length == 3){
            switch (strings[0].toLowerCase()){
                case "add", "remove" -> {
                    return List.of("1", "2", "5", "10");
                }
            }
        }

        return list;
    }
}
