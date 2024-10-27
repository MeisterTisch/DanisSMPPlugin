package user.meistertisch.danissmpplugin.spawn;

import org.bukkit.util.BoundingBox;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;

public class SchedulerPlayerPositions {
    private static ScheduledExecutorService service;
    private static ScheduledFuture scheduledFuture;

    private static BoundingBox bb = ManagerSpawn.getBoundingBox();

//    public static void setup(){
//        service = Executors.newSingleThreadScheduledExecutor();
//        scheduledFuture = service.scheduleAtFixedRate(() -> {
//            for(Player player : Bukkit.getOnlinePlayers()){
////                if(bb.overlaps(player.getBoundingBox()) && !bb.overlaps(playerBB.get(player))){
//
//                }
////                playerBB.put(player, player.getBoundingBox());
//            }
//        }, 0,1000, java.util.concurrent.TimeUnit.MILLISECONDS);
//    }

    public static void stop(){
        scheduledFuture.cancel(true);
        service.shutdown();
    }
}
