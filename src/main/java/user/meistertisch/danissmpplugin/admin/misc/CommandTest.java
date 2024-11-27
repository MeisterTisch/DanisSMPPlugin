package user.meistertisch.danissmpplugin.admin.misc;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.jetbrains.annotations.NotNull;
import user.meistertisch.danissmpplugin.afk.ManagerAFK;

public class CommandTest implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(sender instanceof Player player) {
//            player.openInventory(Bukkit.createInventory(null, 9*6));
            System.out.println(ManagerAFK.afkPlayers);
        }
        return true;
    }
}
