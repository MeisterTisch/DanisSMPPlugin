package user.meistertisch.danissmpplugin.admin.functions;

import org.bukkit.Material;

public enum FunctionTypes {
    LEVEL_SYSTEM("functionTypes.level_system", "functionTypes.level_system.desc", "levelingSystem.use", Material.EXPERIENCE_BOTTLE),
    LEVEL_SYSTEM_REWARDS("functionTypes.level_system.rewards", "functionTypes.level_system.rewards.desc", "levelingSystem.rewards", Material.EMERALD),
    HOME_SYSTEM("functionTypes.home_system", "functionTypes.home_system.desc", "homeSystem.use", Material.OAK_DOOR),
    HOME_SYSTEM_TEAM("functionTypes.home_system.team", "functionTypes.home_system.team.desc", "homeSystem.team", Material.BIRCH_DOOR),
    TPA_SYSTEM("functionTypes.tpa_system", "functionTypes.tpa_system.desc", "tpaSystem.use", Material.ENDER_PEARL),
    SLIME_CHUNK_CHECK_SYSTEM("functionTypes.slime_chunk_check_system", "functionTypes.slime_chunk_check_system.desc", "slimeChunkCheckSystem.use", Material.SLIME_BALL),
    THUNDER_SPAWN("functionTypes.thunder_spawn", "functionTypes.thunder_spawn.desc", "thunderSpawn.use", Material.LIGHTNING_ROD),
    DURABILITY_PING("functionTypes.durability_ping", "functionTypes.durability_ping.desc", "durabilityPing.use", Material.DAMAGED_ANVIL),
    MOB_HEALTHBAR("functionTypes.mob_healthbar", "functionTypes.mob_healthbar.desc", "mobHealthbar.use", Material.PIG_SPAWN_EGG),
    HEAD_DROP("functionTypes.head_drop", "functionTypes.head_drop.desc", "headDrop.use", Material.PLAYER_HEAD),
    VILLAGER_DEATH_MESSAGE("functionTypes.villager_death_message", "functionTypes.villager_death_message.desc", "villagerDeathMessage.use", Material.VILLAGER_SPAWN_EGG),
    SPIT("functionTypes.spit", "functionTypes.spit.desc", "spit.use", Material.WATER_BUCKET),;

    private String name;
    private String desc;
    private String config;
    private Material material;

    FunctionTypes(String name, String desc, String config, Material material) {
        this.name = name;
        this.desc = desc;
        this.config = config;
        this.material = material;
    }

    public String getName() {
        return name;
    }

    public String getDesc() {
        return desc;
    }

    public String getConfig() {
        return config;
    }

    public Material getMaterial() {
        return material;
    }
}
