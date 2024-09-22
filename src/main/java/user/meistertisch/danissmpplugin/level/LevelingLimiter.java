package user.meistertisch.danissmpplugin.level;

import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import java.util.HashMap;

public class LevelingLimiter implements Listener {
    private static HashMap<Player, Integer> limitBuildUp = new HashMap<Player, Integer>();

    public static void playerLeveled(Player player){
        if(!limitBuildUp.containsKey(player))
            limitBuildUp.put(player, 0);

        limitBuildUp.put(player, limitBuildUp.get(player) + 1);
        System.out.println("Player " + player.getName() + " has now " + limitBuildUp.get(player) + " levels");

        if(limitBuildUp.get(player) >= 15){
            limitBuildUp.put(player, 0);
        }
    }

    public static boolean isPlayerLimited(Player player){
        return limitBuildUp.containsKey(player) && limitBuildUp.get(player) >= 10;
    }
}
