package user.meistertisch.danissmpplugin;

import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import user.meistertisch.danissmpplugin.admin.CommandAdmin;
import user.meistertisch.danissmpplugin.files.FileAdmins;
import user.meistertisch.danissmpplugin.files.FileLevels;
import user.meistertisch.danissmpplugin.files.FilePlayer;
import user.meistertisch.danissmpplugin.level.CommandLeveling;
import user.meistertisch.danissmpplugin.level.ListenerJoinRewardsReminder;
import user.meistertisch.danissmpplugin.level.invs.drumroll.ListenerInvInteractDrumroll;
import user.meistertisch.danissmpplugin.level.invs.start.ListenerInvClickStartRewarding;
import user.meistertisch.danissmpplugin.level.types.adventure.EventLevelingAdventure;
import user.meistertisch.danissmpplugin.level.types.combat.EventLevelingCombat;
import user.meistertisch.danissmpplugin.level.types.farming.EventLevelingFarming;
import user.meistertisch.danissmpplugin.level.types.mining.EventLevelingMining;

public final class  Main extends JavaPlugin {
    private static Main plugin;
    PluginManager pluginManager;
    EventLevelingAdventure eventLevelingAdventure;

    @Override
    public void onEnable() {
        //FIRST THINGS FIRST
        plugin = this;
        pluginManager = Bukkit.getPluginManager();

        //Commands
        getCommand("level").setExecutor(new CommandLeveling());
        getCommand("admin").setExecutor(new CommandAdmin());

        //Listeners
        pluginManager.registerEvents(new EventLevelingMining(), this);
        pluginManager.registerEvents(new EventLevelingFarming(), this);
        pluginManager.registerEvents(new EventLevelingCombat(), this);
        pluginManager.registerEvents(new EventLevelingAdventure(), this);
        pluginManager.registerEvents(new ListenerInvClickStartRewarding(), this);
        pluginManager.registerEvents(new ListenerInvInteractDrumroll(), this);
        pluginManager.registerEvents(new ListenerJoinRewardsReminder(), this);

        //Files
        this.saveDefaultConfig();
        this.saveResource("language_de.properties", true);
        this.saveResource("language_en.properties", true);
        FilePlayer.setup();
        FileLevels.setup();
        FileAdmins.setup();
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
