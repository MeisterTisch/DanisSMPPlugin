package user.meistertisch.danissmpplugin.admin;

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

import java.util.List;
import java.util.ResourceBundle;

public class CommandHide implements TabExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        ResourceBundle bundle = ResourceBundle.getBundle("language_en");

        if(sender instanceof Player player){
            bundle = ResourceBundle.getBundle("language_" + FilePlayer.getConfig().getString(player.getName() + ".lang"));
            if(!FileAdmins.isAdmin(player)){
                player.sendMessage(Component.text(bundle.getString("commands.noAdmin")).color(NamedTextColor.RED));
                return true;
            }

            if(args.length != 0){
                player.sendMessage(Component.text(bundle.getString("commands.invalidArg")).color(NamedTextColor.RED));
                return true;
            }

            boolean isHidden = FilePlayer.getConfig().getBoolean(player.getName() + ".hidden");
            FilePlayer.getConfig().set(player.getName() + ".hidden", !isHidden);

            player.sendMessage(
                    Component.text(
                            !isHidden ? bundle.getString("commands.hide.hidden") : bundle.getString("commands.hide.notHidden")
                    ).color(!isHidden ? NamedTextColor.RED : NamedTextColor.GREEN)
            );

            for(Player all : Bukkit.getOnlinePlayers()){
                if(all == player) continue;

                TextColor color1 = TextColor.color(Main.getPlugin().getConfig().getColor("colors.accent1", Color.fromRGB(0x7704a8)).asRGB());
                TextColor color2 = TextColor.color(Main.getPlugin().getConfig().getColor("colors.accent2", Color.fromRGB(0xF01BC)).asRGB());
                ResourceBundle tempBundle = ResourceBundle.getBundle("language_" + FilePlayer.getConfig().getString(all.getName() + ".lang"));
                Component playerText = Component.text(player.getName()).color(color1).decorate(TextDecoration.BOLD);

                    if(isHidden){
                        if(!FileAdmins.isAdmin(all)) {
                            all.showPlayer(Main.getPlugin(), player);
                            all.sendMessage(Component.text(tempBundle.getString("join.joinMessage")).color(color2).replaceText(
                                    TextReplacementConfig.builder().match("%player%").replacement(playerText).build()
                            ));
                        } else {
                            playerText = playerText.color(NamedTextColor.GOLD);
                            all.sendMessage(Component.text(tempBundle.getString("commands.hide.notHidden.toOtherAdmins")).color(NamedTextColor.GREEN).replaceText(
                                    TextReplacementConfig.builder().match("%player%").replacement(playerText).build()
                            ));
                        }
                    } else {
                        if(!FileAdmins.isAdmin(all)) {
                            all.hidePlayer(Main.getPlugin(), player);
                            all.sendMessage(Component.text(tempBundle.getString("leaveMessage")).color(color2).replaceText(
                                    TextReplacementConfig.builder().match("%player%").replacement(playerText).build()
                            ));
                        } else {
                            playerText = playerText.color(NamedTextColor.GOLD);
                            all.sendMessage(Component.text(tempBundle.getString("commands.hide.hidden.toOtherAdmins")).color(NamedTextColor.RED).replaceText(
                                    TextReplacementConfig.builder().match("%player%").replacement(playerText).build()
                            ));
                        }
                    }
            }

        } else {
            sender.sendMessage(Component.text(bundle.getString("commands.noPlayer")).color(NamedTextColor.RED));
        }
        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        return List.of();
    }
}
