package user.meistertisch.danissmpplugin.combatTimer;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.entity.Player;
import user.meistertisch.danissmpplugin.files.FilePlayer;

import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class ManagerCombatTimer {
    private static ScheduledExecutorService service;
    private static ScheduledFuture scheduledFuture;
    private static HashMap<Player, Integer> combatTimer = new HashMap<>();

    public static void setup(){
        service = Executors.newSingleThreadScheduledExecutor();
        scheduledFuture = service.scheduleAtFixedRate(() -> {
            for(Player player : combatTimer.keySet()){
                if(combatTimer.get(player) > 0){
                    combatTimer.put(player, combatTimer.get(player) - 1);
                } else {
                    combatTimer.remove(player);
                    ResourceBundle bundle = ResourceBundle.getBundle("language_" + FilePlayer.getConfig().getString(player.getName() + ".lang"));
                    player.sendActionBar(Component.text(bundle.getString("combatTimer.removed")).color(NamedTextColor.GREEN));
                }
            }
        },0,1, TimeUnit.SECONDS);
    }

    public static boolean isInCombat(Player player){
        return combatTimer.containsKey(player);
    }

    public static void shutdown(){
        scheduledFuture.cancel(true);
        service.shutdown();
    }

    public static void addPlayer(Player player, int timeInSeconds){
        if(!combatTimer.containsKey(player)){
            ResourceBundle bundle = ResourceBundle.getBundle("language_" + FilePlayer.getConfig().getString(player.getName() + ".lang"));
            player.sendActionBar(Component.text(bundle.getString("combatTimer.added")).color(NamedTextColor.GOLD));
        }
        combatTimer.put(player, timeInSeconds);
    }
}


