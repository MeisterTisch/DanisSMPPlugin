package user.meistertisch.danissmpplugin.admin;

import org.bukkit.Color;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import user.meistertisch.danissmpplugin.files.FileTeams;

public class CommandTest implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        FileTeams.getConfig().set("Hobara.color", Color.fromRGB(0x7704a8));
        FileTeams.saveConfig();
        return true;
    }
}
