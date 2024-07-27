package user.meistertisch.danissmpplugin.level.types.mining;

import org.bukkit.Material;
import user.meistertisch.danissmpplugin.level.invs.drumroll.WinRarityTypes;

import java.util.ArrayList;
import java.util.List;

public enum RewardsLevelingMining {
    COAL("level.mining.rewards.coal.name", "level.mining.rewards.coal.desc", Material.COAL, 8, WinRarityTypes.COMMON),
    IRON("level.mining.rewards.iron.name", "level.mining.rewards.iron.desc", Material.IRON_INGOT, 4, WinRarityTypes.COMMON),
    GOLD("level.mining.rewards.gold.name", "level.mining.rewards.gold.desc", Material.GOLD_INGOT, 2, WinRarityTypes.COMMON),
    TUFF("level.mining.rewards.tuff.name", "level.mining.rewards.tuff.desc", Material.TUFF, 8, WinRarityTypes.COMMON),
    STONE("level.mining.rewards.stone.name", "level.mining.rewards.stone.desc", Material.STONE, 16, WinRarityTypes.COMMON),
    LAPIS("level.mining.rewards.lapis.name", "level.mining.rewards.lapis.desc", Material.LAPIS_LAZULI, 8, WinRarityTypes.UNCOMMON),
    REDSTONE("level.mining.rewards.redstone.name", "level.mining.rewards.redstone.desc", Material.REDSTONE, 8, WinRarityTypes.UNCOMMON),
    COPPER("level.mining.rewards.copper.name", "level.mining.rewards.copper.desc", Material.COPPER_INGOT, 8, WinRarityTypes.UNCOMMON),
    DIAMOND("level.mining.rewards.diamond.name", "level.mining.rewards.diamond.desc", Material.DIAMOND, 1, WinRarityTypes.RARE),
    EMERALD("level.mining.rewards.emerald.name", "level.mining.rewards.emerald.desc", Material.EMERALD, 2, WinRarityTypes.RARE),
    QUARTZ("level.mining.rewards.quartz.name", "level.mining.rewards.quartz.desc", Material.QUARTZ, 4, WinRarityTypes.RARE),
    AMETHYST("level.mining.rewards.amethyst.name", "level.mining.rewards.amethyst.desc", Material.AMETHYST_SHARD, 8, WinRarityTypes.RARE),
    GLOW_LICHEN("level.mining.rewards.glow_lichen.name", "level.mining.rewards.glow_lichen.desc", Material.GLOW_LICHEN, 8, WinRarityTypes.RARE),
    ANCIENT_DEBRIS("level.mining.rewards.ancient_debris.name", "level.mining.rewards.ancient_debris.desc", Material.ANCIENT_DEBRIS, 1, WinRarityTypes.EPIC),
    MORE_DIAMONDS("level.mining.rewards.more_diamonds.name", "level.mining.rewards.more_diamonds.desc", Material.DIAMOND, 4, WinRarityTypes.EPIC),
    MORE_EMERALDS("level.mining.rewards.more_emeralds.name", "level.mining.rewards.more_emeralds.desc", Material.EMERALD, 8, WinRarityTypes.EPIC),
    NETHERITE("level.mining.rewards.netherite.name", "level.mining.rewards.netherite.desc", Material.NETHERITE_INGOT, 1, WinRarityTypes.LEGENDARY);

    private final String name;
    private final String description;
    private final Material material;
    private final int amount;
    private final WinRarityTypes rarity;

    RewardsLevelingMining(String name, String description, Material material, int amount, WinRarityTypes rarity) {
        this.name = name;
        this.description = description;
        this.material = material;
        this.amount = amount;
        this.rarity = rarity;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Material getMaterial() {
        return material;
    }

    public int getAmount() {
        return amount;
    }

    public WinRarityTypes getRarity() {
        return rarity;
    }

    public static List<RewardsLevelingMining> getRewards(WinRarityTypes rarity){
        List<RewardsLevelingMining> list = new ArrayList<>();

        for(RewardsLevelingMining reward : RewardsLevelingMining.values()){
            if(reward.getRarity().equals(rarity)){
                list.add(reward);
            }
        }

        return list;
    }

    public static RewardsLevelingMining getNextItem(){
        WinRarityTypes rarity = WinRarityTypes.getRandomRarity();
        List<RewardsLevelingMining> rewards = getRewards(rarity);

        return rewards.get((int) (Math.random() * rewards.size()));
    }
}
