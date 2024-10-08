package user.meistertisch.danissmpplugin;

import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import user.meistertisch.danissmpplugin.admin.CommandAdmin;
import user.meistertisch.danissmpplugin.admin.CommandTest;
import user.meistertisch.danissmpplugin.admin.functions.CommandFunctions;
import user.meistertisch.danissmpplugin.admin.functions.ListenerInvClickFunctions;
import user.meistertisch.danissmpplugin.essentials.CommandDiscord;
import user.meistertisch.danissmpplugin.essentials.CommandTeamspeak;
import user.meistertisch.danissmpplugin.essentials.chat.ListenerChatFormater;
import user.meistertisch.danissmpplugin.essentials.ListenerJoinAndLeave;
import user.meistertisch.danissmpplugin.essentials.lang.CommandLanguage;
import user.meistertisch.danissmpplugin.files.FileAdmins;
import user.meistertisch.danissmpplugin.files.FileLevels;
import user.meistertisch.danissmpplugin.files.FilePlayer;
import user.meistertisch.danissmpplugin.files.FileTeams;
import user.meistertisch.danissmpplugin.level.CommandLeveling;
import user.meistertisch.danissmpplugin.level.ListenerJoinRewardsReminder;
import user.meistertisch.danissmpplugin.level.invs.drumroll.ListenerInvInteractDrumroll;
import user.meistertisch.danissmpplugin.level.invs.start.ListenerInvClickStartRewarding;
import user.meistertisch.danissmpplugin.level.types.adventure.EventLevelingAdventure;
import user.meistertisch.danissmpplugin.level.types.building.EventLevelingBuilding;
import user.meistertisch.danissmpplugin.level.types.combat.EventLevelingCombat;
import user.meistertisch.danissmpplugin.level.types.farming.EventLevelingFarming;
import user.meistertisch.danissmpplugin.level.types.magic.EventLevelingMagic;
import user.meistertisch.danissmpplugin.level.types.mining.EventLevelingMining;
import user.meistertisch.danissmpplugin.level.types.trading.EventLevelingTrading;
import user.meistertisch.danissmpplugin.misc.fun.CommandSpit;
import user.meistertisch.danissmpplugin.misc.thunderstormSummoner.CommandThunderstormSummoner;
import user.meistertisch.danissmpplugin.misc.thunderstormSummoner.ListenerTridentThrow;
import user.meistertisch.danissmpplugin.tpa.ManagerTPA;

public final class  Main extends JavaPlugin {
    private static Main plugin;
    PluginManager pluginManager;
    private static ManagerTPA managerTPA;

    @Override
    public void onEnable() {
        //FIRST THINGS FIRST
        plugin = this;
        pluginManager = Bukkit.getPluginManager();

        //Commands
        getCommand("admin").setExecutor(new CommandAdmin());
        getCommand("functions").setExecutor(new CommandFunctions());
        getCommand("test").setExecutor(new CommandTest());
        getCommand("level").setExecutor(new CommandLeveling());
        getCommand("thunder").setExecutor(new CommandThunderstormSummoner());
        getCommand("language").setExecutor(new CommandLanguage());
        getCommand("spit").setExecutor(new CommandSpit());
        getCommand("discord").setExecutor(new CommandDiscord());
        getCommand("teamspeak").setExecutor(new CommandTeamspeak());

        //Listeners
            //Functions
        pluginManager.registerEvents(new ListenerInvClickFunctions(), this);

            //Essentials
        pluginManager.registerEvents(new ListenerJoinAndLeave(), this);
        pluginManager.registerEvents(new ListenerChatFormater(), this);

            //Leveling
        if(getConfig().getBoolean("levelingSystem.use")) {
            pluginManager.registerEvents(new EventLevelingMining(), this);
            pluginManager.registerEvents(new EventLevelingFarming(), this);
            pluginManager.registerEvents(new EventLevelingCombat(), this);
            pluginManager.registerEvents(new EventLevelingAdventure(), this);
            pluginManager.registerEvents(new EventLevelingTrading(), this);
            pluginManager.registerEvents(new EventLevelingMagic(), this);
            pluginManager.registerEvents(new EventLevelingBuilding(), this);
            pluginManager.registerEvents(new ListenerInvClickStartRewarding(), this);
            //Leveling: Rewards
            if(getConfig().getBoolean("levelingSystem.rewards")){
                pluginManager.registerEvents(new ListenerInvInteractDrumroll(), this);
                pluginManager.registerEvents(new ListenerJoinRewardsReminder(), this);
            }
        }

            //ThunderstormSummoner
        if(getConfig().getBoolean("thunderSpawn.use"))
            pluginManager.registerEvents(new ListenerTridentThrow(), this);


        //Schedulers

        //Files
        this.saveDefaultConfig();
        this.saveResource("language_de.properties", true);
        this.saveResource("language_en.properties", true);
        FilePlayer.setup();
        FileLevels.setup();
        FileAdmins.setup();
        FileTeams.setup();

        //Managers
        managerTPA = new ManagerTPA();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic

        this.saveConfig();
        FilePlayer.saveConfig();
        FileLevels.saveConfig();
        FileAdmins.saveConfig();
        FileTeams.saveConfig();
    }

    //Some static Getters
    public static Main getPlugin() {
        return plugin;
    }

    public static ManagerTPA getManagerTPA() {
        return managerTPA;
    }
}
