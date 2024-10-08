package user.meistertisch.danissmpplugin.tpa;

import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ManagerTPA {
    private HashMap<Player, Player> requestsTo;
    private HashMap<Player, Player> requestsHere;

    public ManagerTPA() {
        requestsTo = new HashMap<>();
        requestsHere = new HashMap<>();
    }

    public void addRequestTo(Player from, Player to) {
        requestsTo.put(from, to);
    }

    public void addRequestHere(Player from, Player here) {
        requestsHere.put(from, here);
    }

    public void tpTo(Player from) {
        if(requestsTo.containsKey(from)) {
            from.teleport(requestsTo.get(from));
            requestsTo.remove(from);
        }
    }

    public void tpHere(Player from) {
        if(requestsHere.containsKey(from)) {
            requestsHere.get(from).teleport(from);
            requestsHere.remove(from);
        }
    }

    public List<Player> getRequestedTo(Player player){
        List<Player> list = new ArrayList<>();
        for(Player pTo : requestsTo.keySet()){
            if(requestsTo.get(pTo).equals(player)){
                list.add(pTo);
            }
        }
        return list;
    }

    public List<Player> getRequestedHere(Player player){
        List<Player> list = new ArrayList<>();
        for(Player pHere : requestsHere.keySet()){
            if(requestsHere.get(pHere).equals(player)){
                list.add(pHere);
            }
        }
        return list;
    }
}
