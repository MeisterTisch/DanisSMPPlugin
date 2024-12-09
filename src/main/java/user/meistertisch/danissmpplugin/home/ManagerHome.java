package user.meistertisch.danissmpplugin.home;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import user.meistertisch.danissmpplugin.admin.teams.ManagerTeams;
import user.meistertisch.danissmpplugin.files.FileHomes;
import user.meistertisch.danissmpplugin.files.FilePlayer;

import java.util.ArrayList;
import java.util.List;

public class ManagerHome {
    public static void addHome(Player player, String name, Location location) {
        FileConfiguration config = FileHomes.getConfig();
        config.set(player.getName() + "." + name + ".world", location.getWorld().getName());
        config.set(player.getName() + "." + name + ".x", location.getX());
        config.set(player.getName() + "." + name + ".y", location.getY());
        config.set(player.getName() + "." + name + ".z", location.getZ());
        config.set(player.getName() + "." + name + ".yaw", location.getYaw());
        config.set(player.getName() + "." + name + ".pitch", location.getPitch());
        config.set(player.getName() + "." + name + ".shared", false);
        FileHomes.saveConfig();
    }

    public static void removeHome(Player player, String name) {
        FileConfiguration config = FileHomes.getConfig();
        config.set(player.getName() + "." + name, null);
        FileHomes.saveConfig();
    }

    public static void renameHome(Player player, String oldName, String newName) {
        FileConfiguration config = FileHomes.getConfig();
        config.set(player.getName() + "." + newName, config.get(player.getName() + "." + oldName));
        config.set(player.getName() + "." + oldName, null);
        FileHomes.saveConfig();
    }

    public static void teleportHome(Player player, String name) {
        FileConfiguration config = FileHomes.getConfig();
        if (config.get(player.getName() + "." + name) != null) {
            Location location = new Location(
                    player.getServer().getWorld(config.getString(player.getName() + "." + name + ".world", "world")),
                    config.getDouble(player.getName() + "." + name + ".x"),
                    config.getDouble(player.getName() + "." + name + ".y"),
                    config.getDouble(player.getName() + "." + name + ".z"),
                    (float) config.getDouble(player.getName() + "." + name + ".yaw"),
                    (float) config.getDouble(player.getName() + "." + name + ".pitch")
            );
            player.teleport(location);
        }
    }

    public static boolean homeExists(Player player, String name) {
        FileConfiguration config = FileHomes.getConfig();
        return config.get(player.getName() + "." + name) != null;
    }

    public static List<String> getHomes(Player player) {
        FileConfiguration config = FileHomes.getConfig();
        List<String> homes = new ArrayList<>();
        if (config.getConfigurationSection(player.getName()) != null) {
            homes.addAll(config.getConfigurationSection(player.getName()).getKeys(false));
        }
        return homes;
    }

    public static int getHomeCount(Player player) {
        return getHomes(player).size();
    }

    public static void shareHome(Player player, String home){
        FileConfiguration config = FileHomes.getConfig();
        if(!homeExists(player, home)){
            return;
        }

        if(FilePlayer.getConfig().getBoolean(player + ".isTeam")){
            return;
        }

        for(String string : getHomes(player)){
            if(config.getBoolean(player.getName() + "." + string + ".shared")){
                return;
            }
        }

        config.set(player.getName() + "." + home + ".shared", true);

        String team = FilePlayer.getConfig().getString(player.getName() + ".team", "");
        for(Player p : Bukkit.getOnlinePlayers()){
            if(FilePlayer.getConfig().getString(p.getName() + ".team", "").equals(team)){
                //TODO
            }
        }

        FileHomes.saveConfig();
    }
}
