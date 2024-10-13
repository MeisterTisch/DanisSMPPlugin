package user.meistertisch.danissmpplugin.durability;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import user.meistertisch.danissmpplugin.files.FilePlayer;

import java.util.List;
import java.util.ResourceBundle;

public class CommandDurability implements TabExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(sender instanceof Player player){
            ResourceBundle bundle = ResourceBundle.getBundle("language_" + FilePlayer.getConfig().getString(player.getName() + ".lang"));
            if(args.length == 0){
                boolean isOn = FilePlayer.getConfig().getBoolean(player.getName() + ".durabilityPing");
                FilePlayer.getConfig().set(player.getName() + ".durabilityPing", !isOn);
                FilePlayer.saveConfig();

                if(isOn){
                    player.sendMessage(Component.text(bundle.getString("commands.durabilityPing.off")).color(NamedTextColor.RED));
                } else {
                    player.sendMessage(Component.text(bundle.getString("commands.durabilityPing.on")).color(NamedTextColor.GREEN));
                }

                return true;
            }
            if(args[0].equalsIgnoreCase("on")){
                player.sendMessage(Component.text(bundle.getString("commands.durabilityPing.on")).color(NamedTextColor.GREEN));
                return true;
            }
            if(args[0].equalsIgnoreCase("off")){
                player.sendMessage(Component.text(bundle.getString("commands.durabilityPing.off")).color(NamedTextColor.RED));
                return true;
            }

            //TODO: TRESHOLD SELBER!

            player.sendMessage(Component.text(bundle.getString("commands.invalidArg")).color(NamedTextColor.RED));
        }
        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        return List.of();
    }
}
