package user.meistertisch.danissmpplugin.sccs;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextReplacementConfig;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.checkerframework.checker.units.qual.C;
import user.meistertisch.danissmpplugin.Main;
import user.meistertisch.danissmpplugin.files.FilePlayer;
import user.meistertisch.danissmpplugin.files.FileSlimeChunks;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ManagerSCCS {
    public static boolean isSlimeChunk(int x, int z, World world) {
        if(world == null) {
            return false;
        }
        return world.getChunkAt(x, z).isSlimeChunk();
    }

    public static List<Chunk> getSlimeChunks(String player) {
        World world = Bukkit.getWorld("world");
        List<Chunk> chunks = new ArrayList<>();

        if(world == null) {
            return chunks;
        }

        FileConfiguration config = FileSlimeChunks.getConfig();
        int foundChunks = config.getInt(player + ".foundChunks");

        for(int i = 1; i <= foundChunks; i++) {
            int x = config.getInt(player + ".chunk" + i + ".x");
            int z = config.getInt(player + ".chunk" + i + ".z");
            chunks.add(world.getChunkAt(x, z));
        }

        return chunks;
    }

    public static Component getSlimeChunksComponent(String player, List<Chunk> chunks){
        ResourceBundle bundle = ResourceBundle.getBundle("language_" + FilePlayer.getConfig().getString(player + ".lang"));
        Component component = Component.text("\n" + bundle.getString("sccs.noChunksFound")).color(NamedTextColor.RED);

        if(!Main.getPlugin().getConfig().getBoolean("slimeChunkCheck.save")){
            component = Component.text(bundle.getString("sccs.savingDisabled")).color(NamedTextColor.RED);
        }

        if(chunks == null)
            chunks = getSlimeChunks(player);

        int i = 1;
        if(!chunks.isEmpty()){
            component = Component.text("\n");
            for(Chunk chunk : chunks){
                Component minus = Component.text("-").color(NamedTextColor.GRAY);
                Component chunkComp = Component.text("%minus% Slimechunk %number%: (%xCords% | %zCords%)").color(TextColor.color(Main.getSecondaryColor()))
                        .replaceText(TextReplacementConfig.builder().match("%minus%").replacement(minus).build());

                Component x = Component.text(chunk.getX()).color(TextColor.color(Main.getPrimaryColor()));
                Component z = Component.text(chunk.getZ()).color(TextColor.color(Main.getPrimaryColor()));
                Component number = Component.text(chunks.indexOf(chunk) + 1).color(TextColor.color(Main.getPrimaryColor()));

                chunkComp = chunkComp
                        .replaceText(TextReplacementConfig.builder().match("%xCords%").replacement(x).build())
                        .replaceText(TextReplacementConfig.builder().match("%zCords%").replacement(z).build())
                        .replaceText(TextReplacementConfig.builder().match("%number%").replacement(number).build());

                component = component.append(chunkComp);

                if(i != chunks.size()){
                    component = component.append(Component.text("\n"));
                }
                i++;
            }
        }
        
        return component;
    }

    public static void saveSlimeChunk(Player player, Chunk chunk) {
        if(isAlreadySaved(chunk, player)) return;

        FileConfiguration config = FileSlimeChunks.getConfig();
        int foundChunks = config.getInt(player.getName() + ".foundChunks");
        config.set(player.getName() + ".foundChunks", foundChunks + 1);
        config.set(player.getName() + ".chunk" + (foundChunks + 1) + ".x", chunk.getX());
        config.set(player.getName() + ".chunk" + (foundChunks + 1) + ".z", chunk.getZ());
        FileSlimeChunks.saveConfig();
    }

    public static void reset(){
        FileConfiguration config = FileSlimeChunks.getConfig();

        config.getKeys(true).forEach(key -> {
            config.set(key, null);
        });

        FileSlimeChunks.saveConfig();
    }

    public static boolean isAlreadySaved(Chunk chunk, Player player){
        return getSlimeChunks(player.getName()).contains(chunk);
    }
}
