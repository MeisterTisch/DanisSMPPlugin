package user.meistertisch.danissmpplugin.misc;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextReplacementConfig;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class CommandSeed implements TabExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        long seed = (long) (Math.random() * 10000000);
        seed = seed * 1000000;

        if (Math.random() > 0.5) {
            seed *= -1;
        }

        Component seedMsg = Component.text(seed, NamedTextColor.GREEN);
        Component message = Component.text("Seed: [%seed%]").replaceText(TextReplacementConfig.builder().match("%seed%").replacement(seedMsg).build());

        sender.sendMessage(message);
        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        return List.of();
    }
}
