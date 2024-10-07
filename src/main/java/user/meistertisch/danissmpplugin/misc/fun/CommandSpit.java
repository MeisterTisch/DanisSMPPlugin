package user.meistertisch.danissmpplugin.misc.fun;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.LlamaSpit;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import user.meistertisch.danissmpplugin.Main;
import user.meistertisch.danissmpplugin.files.FilePlayer;

import java.util.List;
import java.util.ResourceBundle;

public class CommandSpit implements TabExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(sender instanceof Player player){
            if(!Main.getPlugin().getConfig().getBoolean("spit.use")) {
                player.sendMessage(Component.text(
                        ResourceBundle.getBundle(
                                "language_" + FilePlayer.getConfig().getString(player.getName() + ".lang"))
                                .getString("functionTypes.spit.disabled")).color(NamedTextColor.RED));
                return true;
            }

            player.getWorld().spawn(player.getEyeLocation(), LlamaSpit.class,
                    llamaSpit -> llamaSpit.setVelocity(player.getEyeLocation().getDirection().multiply(0.75)));
            player.getWorld().playSound(player.getLocation(), Sound.ENTITY_LLAMA_SPIT, SoundCategory.PLAYERS, 1, 1);
        } else {
            sender.sendMessage(Component.text(ResourceBundle.getBundle("language_en").getString("commands.noPlayer")).color(NamedTextColor.RED));
        }
        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        return List.of();
    }
}
