package user.meistertisch.danissmpplugin.admin.functions;

import org.bukkit.Material;

public enum FunctionTypes {
    LEVEL_SYSTEM("functionTypes.level_system", "functionTypes.level_system.desc", "levelingSystem.use", Material.EXPERIENCE_BOTTLE),
    LEVEL_SYSTEM_REWARDS("functionTypes.level_system.rewards", "functionTypes.level_system.rewards.desc", "levelingSystem.rewards", Material.EMERALD);

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
