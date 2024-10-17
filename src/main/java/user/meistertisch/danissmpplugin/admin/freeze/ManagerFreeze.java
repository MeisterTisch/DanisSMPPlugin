package user.meistertisch.danissmpplugin.admin.freeze;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.entity.Player;
import user.meistertisch.danissmpplugin.files.FilePlayer;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.UUID;

public class ManagerFreeze {
    private static List<UUID> frozen = new ArrayList<>();

    public static void freeze(Player player){
        ResourceBundle bundle = ResourceBundle.getBundle("language_" + FilePlayer.getConfig().getString(player.getName() + ".lang"));
        player.sendMessage(Component.text(bundle.getString("commands.freeze.target")).color(NamedTextColor.RED));
        frozen.add(player.getUniqueId());
    }

    public static void unfreeze(Player player){
        ResourceBundle bundle = ResourceBundle.getBundle("language_" + FilePlayer.getConfig().getString(player.getName() + ".lang"));
        player.sendMessage(Component.text(bundle.getString("commands.unfreeze.target")).color(NamedTextColor.GREEN));
        frozen.remove(player.getUniqueId());
    }

    public static boolean isFrozen(Player player){
        return frozen.contains(player.getUniqueId());
    }
}
