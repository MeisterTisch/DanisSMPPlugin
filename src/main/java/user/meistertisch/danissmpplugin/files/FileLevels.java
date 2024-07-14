package user.meistertisch.danissmpplugin.files;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import user.meistertisch.danissmpplugin.Main;

import java.io.File;
import java.io.IOException;

public class FileLevels {
    private static File file;
    private static FileConfiguration config;

    public static void setup() {
        file = new File(
                Main.getPlugin().getDataFolder(), "levels.yml"); //levels
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
}
