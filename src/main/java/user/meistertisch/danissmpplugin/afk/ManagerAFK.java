package user.meistertisch.danissmpplugin.afk;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import user.meistertisch.danissmpplugin.Main;
import user.meistertisch.danissmpplugin.files.FilePlayer;
import user.meistertisch.danissmpplugin.files.FileTeams;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class ManagerAFK {
    private static ScheduledExecutorService service;
    private static ScheduledFuture scheduledFutureGetAfk;
    private static ScheduledFuture scheduledFutureDetectMovement;
    private static HashMap<Player, Location> oldLoc = new HashMap<>();
    private static HashMap<Player, Location> newLoc = new HashMap<>();
    private static HashMap<Player, Integer> afkTime = new HashMap<>();
    public static List<Player> afkPlayers = new ArrayList<>();

    public static void setup(){
        service = Executors.newSingleThreadScheduledExecutor();
        scheduledFutureGetAfk = service.scheduleAtFixedRate(() -> {
            for(Player player : Bukkit.getOnlinePlayers()){
                if(afkPlayers.contains(player)){
                    continue;
                }

                if(!newLoc.containsKey(player)){
                    newLoc.put(player, player.getLocation());
                    continue;
                }

                oldLoc.put(player, newLoc.get(player));
                newLoc.put(player, player.getLocation());

                if(isSameLocation(oldLoc.get(player), newLoc.get(player))){
                    if(afkTime.containsKey(player)){
                        afkTime.put(player, afkTime.get(player) + 1);
                    } else {
                        afkTime.put(player, 1);
                    }
                } else {
                    afkTime.remove(player);
                }
            }

            for(Player player : afkTime.keySet()){
                int timeout = Main.getPlugin().getConfig().getInt("afk.timeout") * 60 / 10;
                if(!afkPlayers.contains(player) && afkTime.get(player) >= timeout){
                    setAFK(player);
                }
            }
        },0,10, TimeUnit.SECONDS);

//        scheduledFutureDetectMovement = service.scheduleAtFixedRate(() -> {
//            for(Player player : afkPlayers){
//                if(!newLoc.containsKey(player)){
//                    newLoc.put(player, player.getLocation());
//                    continue;
//                }
//
//                oldLoc.put(player, newLoc.get(player));
//                newLoc.put(player, player.getLocation());
//
//                System.out.println(oldLoc.get(player));
//                System.out.println(newLoc.get(player));
//
//                if(!isSameLocation(oldLoc.get(player), newLoc.get(player))){
//                    removeAFK(player);
//                }
//            }
//        }, 0, 20, TimeUnit.MILLISECONDS);
    }

    private static boolean isSameLocation(Location loc1, Location loc2){
        boolean isSameWorld = loc1.getWorld().equals(loc2.getWorld());
        boolean isSameX = loc1.getX() == loc2.getX();
        boolean isSameY = loc1.getY() == loc2.getY();
        boolean isSameZ = loc1.getZ() == loc2.getZ();
        boolean isSameYaw = loc1.getYaw() == loc2.getYaw();
        boolean isSamePitch = loc1.getPitch() == loc2.getPitch();

        return isSameWorld && isSameX && isSameY && isSameZ && isSameYaw && isSamePitch;
    }

    public static void setAFK(Player player) {
        afkPlayers.add(player);
        ResourceBundle bundle = ResourceBundle.getBundle("language_" + FilePlayer.getConfig().getString(player.getName() + ".lang"));
        player.sendMessage(Component.text(bundle.getString("afk.true")).color(NamedTextColor.GRAY));
        player.displayName(FileTeams.getTeamNamePrefixComponent(player));
        player.playerListName(FileTeams.getTeamNamePrefixComponent(player));
    }

    public static void removeAFK(Player player) {
        afkPlayers.remove(player);
        ResourceBundle bundle = ResourceBundle.getBundle("language_" + FilePlayer.getConfig().getString(player.getName() + ".lang"));
        player.sendMessage(Component.text(bundle.getString("afk.false")).color(NamedTextColor.GRAY));
        player.displayName(FileTeams.getTeamNamePrefixComponent(player));
        player.playerListName(FileTeams.getTeamNamePrefixComponent(player));
    }

    public static boolean isAFK(Player player) {
        return afkPlayers.contains(player);
    }

    public static void shutdown(){
        scheduledFutureGetAfk.cancel(true);
        scheduledFutureDetectMovement.cancel(true);
        service.shutdown();
    }
}
