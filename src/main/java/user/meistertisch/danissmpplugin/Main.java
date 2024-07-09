package user.meistertisch.danissmpplugin;

import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import user.meistertisch.danissmpplugin.files.FilePlayer;
import user.meistertisch.danissmpplugin.level.types.EventLevelingMining;

public final class  Main extends JavaPlugin {
    private static Main plugin;
    PluginManager pluginManager;

    @Override
    public void onEnable() {
        //FIRST THINGS FIRST
        plugin = this;
        pluginManager = Bukkit.getPluginManager();

        //Commands

        //Listeners
        pluginManager.registerEvents(new EventLevelingMining(), this);

        //Files
        this.saveDefaultConfig();
        this.saveResource("language_de.properties", true);
        this.saveResource("language_en.properties", true);
        FilePlayer.setup();

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    //Some static Getters
    public static Main getPlugin() {
        return plugin;
    }
}
