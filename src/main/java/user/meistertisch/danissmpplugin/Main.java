package user.meistertisch.danissmpplugin;

import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import user.meistertisch.danissmpplugin.files.FileLevels;
import user.meistertisch.danissmpplugin.files.FilePlayer;
import user.meistertisch.danissmpplugin.level.CommandLeveling;
import user.meistertisch.danissmpplugin.level.types.EventLevelingFarming;
import user.meistertisch.danissmpplugin.level.types.EventLevelingMining;
import user.meistertisch.danissmpplugin.level.types.EventLevelingCombat;

public final class  Main extends JavaPlugin {
    private static Main plugin;
    PluginManager pluginManager;

    @Override
    public void onEnable() {
        //FIRST THINGS FIRST
        plugin = this;
        pluginManager = Bukkit.getPluginManager();

        //Commands
        getCommand("level").setExecutor(new CommandLeveling());

        //Listeners
        pluginManager.registerEvents(new EventLevelingMining(), this);
        pluginManager.registerEvents(new EventLevelingFarming(), this);
        pluginManager.registerEvents(new EventLevelingCombat(), this);

        //Files
        this.saveDefaultConfig();
        this.saveResource("language_de.properties", true);
        this.saveResource("language_en.properties", true);
        FilePlayer.setup();
        FileLevels.setup();
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
