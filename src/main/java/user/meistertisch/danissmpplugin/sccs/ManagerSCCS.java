package user.meistertisch.danissmpplugin.sccs;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import user.meistertisch.danissmpplugin.files.FileSlimeChunks;

import java.util.ArrayList;
import java.util.List;

public class ManagerSCCS {
    public static boolean isSlimeChunk(int x, int z) {
        World world = Bukkit.getWorld("world");
        if(world == null) {
            return false;
        }
        return world.getChunkAt(x, z).isSlimeChunk();
    }

    public static List<Chunk> getSlimeChunks(Player player) {
        World world = Bukkit.getWorld("world");
        if(world == null) {
            return null;
        }

        List<Chunk> chunks = new ArrayList<>();
        FileConfiguration config = FileSlimeChunks.getConfig();
        int foundChunks = config.getInt(player.getName() + ".foundChunks");

        for(int i = 1; i <= foundChunks; i++) {
            int x = config.getInt(player.getName() + ".chunk" + i + ".x");
            int z = config.getInt(player.getName() + ".chunk" + i + ".z");
            chunks.add(world.getChunkAt(x, z));
        }

        return chunks;
    }
}
