package user.meistertisch.danissmpplugin.admin.misc;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import user.meistertisch.danissmpplugin.Main;
import user.meistertisch.danissmpplugin.admin.spawn.ManagerSpawn;
import user.meistertisch.danissmpplugin.admin.spawn.ParticlesSpawnBorder;

public class CommandTest implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        ManagerSpawn.addBypass((Player) sender, false);
        return true;
    }
}
