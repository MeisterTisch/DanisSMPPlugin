package user.meistertisch.danissmpplugin.admin;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import user.meistertisch.danissmpplugin.Main;
import user.meistertisch.danissmpplugin.files.FileTeams;
import user.meistertisch.danissmpplugin.tpa.InventoryTpaRequests;

public class CommandTest implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        Main.getManagerTPA().addRequest((Player) sender, Bukkit.getPlayer("MasterTable"), true);
        return true;
    }
}
