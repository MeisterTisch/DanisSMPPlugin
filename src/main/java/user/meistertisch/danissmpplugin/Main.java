package user.meistertisch.danissmpplugin;

import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import user.meistertisch.danissmpplugin.admin.*;
import user.meistertisch.danissmpplugin.admin.dimensions.CommandDimension;
import user.meistertisch.danissmpplugin.admin.dimensions.ListenerDimension;
import user.meistertisch.danissmpplugin.admin.misc.CommandTest;
import user.meistertisch.danissmpplugin.admin.freeze.CommandFreeze;
import user.meistertisch.danissmpplugin.admin.freeze.ListenerMoveFreeze;
import user.meistertisch.danissmpplugin.admin.functions.CommandFunctions;
import user.meistertisch.danissmpplugin.admin.functions.ListenerInvClickFunctions;
import user.meistertisch.danissmpplugin.admin.teams.CommandTeams;
import user.meistertisch.danissmpplugin.afk.CommandAFK;
import user.meistertisch.danissmpplugin.afk.ListenerMoveAFK;
import user.meistertisch.danissmpplugin.afk.ManagerAFK;
import user.meistertisch.danissmpplugin.directMessage.CommandDirectMessage;
import user.meistertisch.danissmpplugin.directMessage.ManagerDirectMessage;
import user.meistertisch.danissmpplugin.essentials.chat.SchedulerChatCooldown;
import user.meistertisch.danissmpplugin.files.*;
import user.meistertisch.danissmpplugin.home.CommandHome;
import user.meistertisch.danissmpplugin.misc.*;
import user.meistertisch.danissmpplugin.sccs.CommandSCCS;
import user.meistertisch.danissmpplugin.spawn.CommandSpawn;
import user.meistertisch.danissmpplugin.spawn.SchedulerPlayerPositions;
import user.meistertisch.danissmpplugin.combatTimer.ListenerCombat;
import user.meistertisch.danissmpplugin.combatTimer.ManagerCombatTimer;
import user.meistertisch.danissmpplugin.spawn.ListenerSpawnProt;
import user.meistertisch.danissmpplugin.durability.CommandDurability;
import user.meistertisch.danissmpplugin.durability.ListenerDurabilityPing;
import user.meistertisch.danissmpplugin.essentials.CommandDiscord;
import user.meistertisch.danissmpplugin.essentials.CommandTeamspeak;
import user.meistertisch.danissmpplugin.essentials.chat.ListenerChatFormater;
import user.meistertisch.danissmpplugin.essentials.ListenerJoinAndLeave;
import user.meistertisch.danissmpplugin.essentials.lang.CommandLanguage;
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
import user.meistertisch.danissmpplugin.mobHealthbar.ListenerMobAttack;
import user.meistertisch.danissmpplugin.tpa.CommandTpa;
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

        //Files
        this.saveDefaultConfig();
        FilePlayer.setup();
        FileLevels.setup();
        FileAdmins.setup();
        FileTeams.setup();
        FileSpawn.setup();
        FileSlimeChunks.setup();
        FileHomes.setup();
        FileWhitelist.setup();

        //Managers
        managerTPA = new ManagerTPA();
        ManagerCombatTimer.setup();
        ManagerDirectMessage.setup();
        ManagerAFK.setup();

        //Commands
        getCommand("admin").setExecutor(new CommandAdmin());
        getCommand("afk").setExecutor(new CommandAFK());
        getCommand("announce").setExecutor(new CommandAnnounce());
        getCommand("chat").setExecutor(new CommandChat());
        getCommand("clearChat").setExecutor(new CommandClearChat());
        getCommand("dimension").setExecutor(new CommandDimension());
        getCommand("discord").setExecutor(new CommandDiscord());
        getCommand("donate").setExecutor(new CommandDonate());
        getCommand("durability").setExecutor(new CommandDurability());
        getCommand("hide").setExecutor(new CommandHide());
        getCommand("home").setExecutor(new CommandHome());
        getCommand("fakeadmin").setExecutor(new CommandFakeAdmin());
        getCommand("freeze").setExecutor(new CommandFreeze());
        getCommand("functions").setExecutor(new CommandFunctions());
        getCommand("inventory").setExecutor(new CommandInvLooker());
        getCommand("language").setExecutor(new CommandLanguage());
        getCommand("level").setExecutor(new CommandLeveling());
        getCommand("message").setExecutor(new CommandDirectMessage());
        getCommand("mute").setExecutor(new CommandMute());
        getCommand("plugins").setExecutor(new CommandPlugins());
        getCommand("scc").setExecutor(new CommandSCCS());
        getCommand("seed").setExecutor(new CommandSeed());
        getCommand("shareItem").setExecutor(new CommandShareItem());
        getCommand("sign").setExecutor(new CommandSign());
        getCommand("spawn").setExecutor(new CommandSpawn());
        getCommand("spit").setExecutor(new CommandSpit());
        getCommand("teams").setExecutor(new CommandTeams());
        getCommand("teamspeak").setExecutor(new CommandTeamspeak());
        getCommand("test").setExecutor(new CommandTest());
        getCommand("thunder").setExecutor(new CommandThunderstormSummoner());
        getCommand("tpa").setExecutor(new CommandTpa());
        getCommand("whitelist").setExecutor(new CommandWhitelist());


        //Listeners
            //Functions
        pluginManager.registerEvents(new ListenerInvClickFunctions(), this);

            //AFK
        pluginManager.registerEvents(new ListenerMoveAFK(), this);

            //Essentials
        pluginManager.registerEvents(new ListenerJoinAndLeave(), this);
        pluginManager.registerEvents(new ListenerChatFormater(), this);

            //Leveling
        pluginManager.registerEvents(new EventLevelingMining(), this);
        pluginManager.registerEvents(new EventLevelingFarming(), this);
        pluginManager.registerEvents(new EventLevelingCombat(), this);
        pluginManager.registerEvents(new EventLevelingAdventure(), this);
        pluginManager.registerEvents(new EventLevelingTrading(), this);
        pluginManager.registerEvents(new EventLevelingMagic(), this);
        pluginManager.registerEvents(new EventLevelingBuilding(), this);
        pluginManager.registerEvents(new ListenerInvClickStartRewarding(), this);
            //Leveling: Rewards
        pluginManager.registerEvents(new ListenerInvInteractDrumroll(), this);
        pluginManager.registerEvents(new ListenerJoinRewardsReminder(), this);

            //ThunderstormSummoner
        pluginManager.registerEvents(new ListenerTridentThrow(), this);

            //Durability Ping
        pluginManager.registerEvents(new ListenerDurabilityPing(), this);

            //MobHealthbar
        pluginManager.registerEvents(new ListenerMobAttack(), this);

            //Freeze
        pluginManager.registerEvents(new ListenerMoveFreeze(), this);

            //Spawn
        pluginManager.registerEvents(new ListenerSpawnProt(), this);

            //CombatTimer
        pluginManager.registerEvents(new ListenerCombat(), this);

            //Dimension
        pluginManager.registerEvents(new ListenerDimension(), this);

            //Player Head Drop
        pluginManager.registerEvents(new ListenerPlayerDeathHeadDrop(), this);

            //Respawn to Spawn, if no RespawnLocation is set
        pluginManager.registerEvents(new ListenerPlayerDeathSpawnTp(), this);



        //Schedulers
//        SchedulerPlayerPositions.setup();
        SchedulerChatCooldown.setup();

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic

        //Files
        this.saveConfig();
        FilePlayer.saveConfig();
        FileLevels.saveConfig();
        FileAdmins.saveConfig();
        FileTeams.saveConfig();
        FileSlimeChunks.saveConfig();
        FileWhitelist.saveConfig();
        FileHomes.saveConfig();
        FileSpawn.saveConfig();

        //Schedulers
        SchedulerPlayerPositions.shutdown();
        ManagerCombatTimer.shutdown();
        SchedulerChatCooldown.shutdown();
        ManagerDirectMessage.shutdown();
        ManagerAFK.shutdown();
    }

    //Some static Getters
    public static Main getPlugin() {
        return plugin;
    }

    public static ManagerTPA getManagerTPA() {
        return managerTPA;
    }

    public static int getPrimaryColor(){
        return plugin.getConfig().getInt("colors.accent1");
    }

    public static int getSecondaryColor(){
        return plugin.getConfig().getInt("colors.accent2");
    }
}
