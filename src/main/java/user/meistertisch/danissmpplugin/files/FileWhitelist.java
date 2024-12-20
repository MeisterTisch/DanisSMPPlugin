package user.meistertisch.danissmpplugin.files;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import user.meistertisch.danissmpplugin.Main;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class FileWhitelist {
    private static File file;
    private static FileConfiguration config;

    public static void setup() {
        file = new File(
                Main.getPlugin().getDataFolder(), "whitelist.yml");
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        config = YamlConfiguration.loadConfiguration(file);
    }

    public static FileConfiguration getConfig() {
        return config;
    }

    public static void saveConfig() {
        try {
            config.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        reload();
    }

    public static void reload() {
        config = YamlConfiguration.loadConfiguration(file);
    }

    public static boolean isAdmin(Player player){
        return getConfig().getList("whitelist").contains(String.valueOf(player.getUniqueId()));
    }

    public static List<String> getList(){
        return getConfig().getStringList("whitelist");
    }

    public static void removePlayer(@NotNull String arg) {
        List<String> list = getList();
        list.remove(arg);
        getConfig().set("whitelist", list);
        saveConfig();
        reload();
    }

    public static void addPlayer(@NotNull String arg) {
        List<String> list = getList();
        list.add(arg);
        getConfig().set("whitelist", list);
        saveConfig();
        reload();
    }
}
