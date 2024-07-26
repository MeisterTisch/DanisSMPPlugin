package user.meistertisch.danissmpplugin.level;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import user.meistertisch.danissmpplugin.files.FileLevels;
import user.meistertisch.danissmpplugin.files.FilePlayer;
import user.meistertisch.danissmpplugin.level.types.LevelType;

import java.util.ResourceBundle;

public class ListenerJoinRewardsReminder implements Listener {
    @EventHandler
    public void playerJoin(PlayerJoinEvent event){
        Player player = event.getPlayer();
        ResourceBundle bundle = ResourceBundle.getBundle("language_" + FilePlayer.getConfig().getString(event.getPlayer().getName() + ".lang"));
        Component text = Component.text(bundle.getString("level.rewarding.reminder")).color(NamedTextColor.GOLD);
        boolean allZero = true;

        for(LevelType type : LevelType.values()){
            if(!(FileLevels.getConfig().getInt(player.getName() + ".rewardsLeft." + type.name().toLowerCase()) == 0)){
                allZero = false;
                Component minus = Component.text("\n - ").color(NamedTextColor.DARK_GRAY);
                Component reminderText = Component.text(bundle.getString("level." + type.toString().toLowerCase()) +
                        ": " + FileLevels.getConfig().getInt(player.getName() + ".rewardsLeft." + type.name().toLowerCase()))
                        .color(type.getColor());
                minus = minus.append(reminderText);
                text = text.append(minus);
            }
        }

        if(allZero) return;

        player.sendMessage(text);
    }
}
