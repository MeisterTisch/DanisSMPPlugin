package user.meistertisch.danissmpplugin.misc;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextReplacementConfig;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import user.meistertisch.danissmpplugin.Main;
import user.meistertisch.danissmpplugin.files.FileTeams;

import java.util.List;
import java.util.ResourceBundle;

public class CommandShareItem implements TabExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        ResourceBundle bundle = ResourceBundle.getBundle("language_en");
        if(sender instanceof Player player){
            if(args.length != 0){
                player.sendMessage(Component.text(bundle.getString("commands.invalidArg")).color(NamedTextColor.RED));
                return true;
            }

            ItemStack item = player.getInventory().getItemInMainHand();

            if(item.getType() == Material.AIR){
                player.sendMessage(Component.text(bundle.getString("commands.shareItem.noItem")).color(NamedTextColor.RED));
                return true;
            }

            Component itemName = item.displayName().decorate(TextDecoration.BOLD);

            Component chatMessage = Component.text("%player%: %message%");

            chatMessage = chatMessage
                    .replaceText(TextReplacementConfig.builder().match("%player%").replacement(FileTeams.getTeamNamePrefixComponent(player)).build())
                    .replaceText(TextReplacementConfig.builder().match("%message%").replacement(itemName).build());

            Bukkit.broadcast(chatMessage);
            return true;
        }

        sender.sendMessage(Component.text(bundle.getString("commands.onlyPlayer")).color(NamedTextColor.RED));
        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        return List.of();
    }
}
