package user.meistertisch.danissmpplugin.admin.misc;

import net.kyori.adventure.text.Component;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class CommandTest implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(sender instanceof Player player){
            System.out.println("Name: " + player.getName());
            System.out.println("DisplayName: " + player.displayName());
            System.out.println("PlayerListName: " + player.playerListName());
        }
        return true;
    }
}
