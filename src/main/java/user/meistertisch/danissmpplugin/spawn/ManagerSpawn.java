package user.meistertisch.danissmpplugin.spawn;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.util.BoundingBox;
import user.meistertisch.danissmpplugin.files.FileSpawn;

import java.util.ArrayList;
import java.util.List;

public class ManagerSpawn {
    private static FileConfiguration config = FileSpawn.getConfig();
    private static int spawns;
    private static World world;
    private static List<Location> spawnLocations = new ArrayList<>();
    private static Location border1;
    private static Location border2;
    private static List<Player> bypassBuilding = new ArrayList<>();
    private static List<Player> bypassCombat = new ArrayList<>();

    private static void initialize() {
        spawns = config.getInt("spawns");
        world = Bukkit.getWorld(config.getString("world", "world"));
        border1 = new Location(world, config.getDouble("border1.x"), config.getDouble("border1.y"), config.getDouble("border1.z"));
        border2 = new Location(world, config.getDouble("border2.x"), config.getDouble("border2.y"), config.getDouble("border2.z"));

        for(int i = 1; i <= spawns; i++) {
            spawnLocations.add(new Location(world, config.getDouble(i + ".x"), config.getDouble(i + ".y"),
                    config.getDouble(i + ".z"), (float) config.getDouble(i + ".yaw"),
                    (float) config.getDouble(i + ".pitch")));
        }
    }

    public static Location getBorder1() {
        initialize();
        return border1;
    }

    public static Location getBorder2() {
        initialize();
        return border2;
    }

    public static void addBypass(Player player, boolean isBuilding) {
        if(isBuilding) {
            bypassBuilding.add(player);
        } else {
            bypassCombat.add(player);
        }
    }

    public static void removeBypass(Player player, boolean isBuilding) {
        if(isBuilding) {
            bypassBuilding.remove(player);
        } else {
            bypassCombat.remove(player);
        }
    }

    public static void removeBypass(Player player) {
        bypassBuilding.remove(player);
        bypassCombat.remove(player);
    }

    public static boolean isBypass(Player player, boolean isBuilding) {
        if(isBuilding) {
            return bypassBuilding.contains(player);
        } else {
            return bypassCombat.contains(player);
        }
    }

    public static BoundingBox getBoundingBox() {
        initialize();
        return new BoundingBox(
                ManagerSpawn.getBorder1().x(),
                ManagerSpawn.getBorder1().y(),
                ManagerSpawn.getBorder1().z(),
                ManagerSpawn.getBorder2().x(),
                ManagerSpawn.getBorder2().y(),
                ManagerSpawn.getBorder2().z()
        );
    }

    public static List<Location> getSpawnLocations() {
        initialize();
        return spawnLocations;
    }

    public static Location getRandomSpawnLocation() {
        initialize();
        return spawnLocations.get((int) (Math.random() * spawnLocations.size()));
    }
}
