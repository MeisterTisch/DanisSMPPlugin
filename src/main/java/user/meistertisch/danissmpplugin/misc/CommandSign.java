package user.meistertisch.danissmpplugin.misc;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import user.meistertisch.danissmpplugin.Main;
import user.meistertisch.danissmpplugin.files.FilePlayer;

import java.util.List;
import java.util.ResourceBundle;

public class CommandSign implements TabExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        ResourceBundle bundle = ResourceBundle.getBundle("language_en");
        if(sender instanceof Player player){
            bundle = ResourceBundle.getBundle("language_" + FilePlayer.getConfig().getString(player.getName() + ".lang"));
            if(args.length != 0){
                sender.sendMessage(Component.text(bundle.getString("commands.invalidArg")).color(NamedTextColor.RED));
                return true;
            }

            ItemStack item = player.getInventory().getItemInMainHand();
            if(item.getType() == Material.AIR){
                player.sendMessage(Component.text(bundle.getString("commands.sign.noItem")).color(NamedTextColor.RED));
                return true;
            }
            ItemMeta meta = item.getItemMeta();
            List<Component> lore;

            if(meta.hasLore() && meta.lore() != null){
                lore = meta.lore();
                Component name = Component.text(player.getName()).color(TextColor.color(Main.getSecondaryColor()));
                if(lore.contains(name)){
                    player.sendMessage(Component.text(bundle.getString("commands.sign.alreadySigned")).color(NamedTextColor.RED));
                    return true;
                }
                lore.add(name);

            } else {
                lore = List.of(
                                Component.text(bundle.getString("commands.sign.text")).color(TextColor.color(Main.getPrimaryColor())),
                                Component.text(player.getName()).color(TextColor.color(Main.getSecondaryColor()))
                        );
            }

            meta.lore(lore);
            item.setItemMeta(meta);
            player.getInventory().setItemInMainHand(item);
            player.sendMessage(Component.text(bundle.getString("commands.sign")).color(NamedTextColor.GREEN)) ;
        } else {
            sender.sendMessage(Component.text(bundle.getString("commands.noPlayer")).color(NamedTextColor.RED));
            return true;
        }
        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        return List.of();
    }
}
