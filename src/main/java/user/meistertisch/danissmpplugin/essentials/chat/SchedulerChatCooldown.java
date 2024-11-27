package user.meistertisch.danissmpplugin.essentials.chat;

import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class SchedulerChatCooldown {
    private static ScheduledExecutorService service;
    private static ScheduledFuture scheduledFuture;
    private static HashMap<Player, Integer> cooldown = new HashMap<>();

    public static void setup(){
        service = Executors.newSingleThreadScheduledExecutor();
        scheduledFuture = service.scheduleAtFixedRate(() -> {
            for(Player player : cooldown.keySet()){
                if(cooldown.get(player) > 0){
                    cooldown.put(player, cooldown.get(player) - 1);
                } else {
                    cooldown.remove(player);
                }
            }
        },0,1, TimeUnit.SECONDS);
    }

    public static void addPlayer(Player player, int cooldown) {
        SchedulerChatCooldown.cooldown.put(player, cooldown);
    }

    public static boolean isOnCooldown(Player player){
        return SchedulerChatCooldown.cooldown.containsKey(player);
    }

    public static void shutdown(){
        scheduledFuture.cancel(true);
        service.shutdown();
    }
}
