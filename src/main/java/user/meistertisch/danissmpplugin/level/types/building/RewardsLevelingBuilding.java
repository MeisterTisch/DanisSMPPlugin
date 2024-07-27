package user.meistertisch.danissmpplugin.level.types.building;

import org.bukkit.Material;
import user.meistertisch.danissmpplugin.level.invs.drumroll.WinRarityTypes;


import java.util.ArrayList;
import java.util.List;

public enum RewardsLevelingBuilding  {
    STONE("level.building.rewards.stone.name", "level.building.rewards.stone.desc", Material.STONE, 32, WinRarityTypes.COMMON),
    DEEPSLATE("level.building.rewards.deepslate.name", "level.building.rewards.deepslate.desc", Material.DEEPSLATE, 16, WinRarityTypes.COMMON),
    COBBLESTONE("level.building.rewards.cobblestone.name", "level.building.rewards.cobblestone.desc", Material.COBBLESTONE, 32, WinRarityTypes.COMMON),
    SPRUCE_PLANKS("level.building.rewards.spruce_planks.name", "level.building.rewards.spruce_planks.desc", Material.SPRUCE_PLANKS, 32, WinRarityTypes.COMMON),
    BIRCH_PLANKS("level.building.rewards.birch_planks.name", "level.building.rewards.birch_planks.desc", Material.BIRCH_PLANKS, 32, WinRarityTypes.COMMON),
    OAK_PLANKS("level.building.rewards.oak_planks.name", "level.building.rewards.oak_planks.desc", Material.OAK_PLANKS, 32, WinRarityTypes.COMMON),

    STONE_BRICKS("level.building.rewards.stone_bricks.name", "level.building.rewards.stone_bricks.desc", Material.STONE_BRICKS, 32, WinRarityTypes.UNCOMMON),
    POLISHED_DEEPSLATE("level.building.rewards.polished_deepslate.name", "level.building.rewards.polished_deepslate.desc", Material.POLISHED_DEEPSLATE, 16, WinRarityTypes.UNCOMMON),
    MOSSY_COBBLESTONE("level.building.rewards.mossy_cobblestone.name", "level.building.rewards.mossy_cobblestone.desc", Material.MOSSY_COBBLESTONE, 16, WinRarityTypes.UNCOMMON),
    OAK_LOG("level.building.rewards.oak_log.name", "level.building.rewards.oak_log.desc", Material.OAK_LOG, 16, WinRarityTypes.UNCOMMON),
    SPRUCE_LOG("level.building.rewards.spruce_log.name", "level.building.rewards.spruce_log.desc", Material.SPRUCE_LOG, 16, WinRarityTypes.UNCOMMON),
    BIRCH_LOG("level.building.rewards.birch_log.name", "level.building.rewards.birch_log.desc", Material.BIRCH_LOG, 16, WinRarityTypes.UNCOMMON),
    CRIMSON_PLANKS("level.building.rewards.crimson_planks.name", "level.building.rewards.crimson_planks.desc", Material.CRIMSON_PLANKS, 32, WinRarityTypes.UNCOMMON),
    WARPED_PLANKS("level.building.rewards.warped_planks.name", "level.building.rewards.warped_planks.desc", Material.WARPED_PLANKS, 32, WinRarityTypes.UNCOMMON),
    DIORITE("level.building.rewards.diorite.name", "level.building.rewards.diorite.desc", Material.DIORITE, 16, WinRarityTypes.UNCOMMON),
    GRANITE("level.building.rewards.granite.name", "level.building.rewards.granite.desc", Material.GRANITE, 16, WinRarityTypes.UNCOMMON),
    ANDESITE("level.building.rewards.andesite.name", "level.building.rewards.andesite.desc", Material.ANDESITE, 16, WinRarityTypes.UNCOMMON),
    END_STONE("level.building.rewards.end_stone.name", "level.building.rewards.end_stone.desc", Material.END_STONE, 16, WinRarityTypes.UNCOMMON),
    NETHER_BRICKS("level.building.rewards.nether_bricks.name", "level.building.rewards.nether_bricks.desc", Material.NETHER_BRICKS, 32, WinRarityTypes.UNCOMMON),

    BRICKS("level.building.rewards.bricks.name", "level.building.rewards.bricks.desc", Material.BRICKS, 32, WinRarityTypes.RARE),
    END_STONE_BRICKS("level.building.rewards.end_stone_bricks.name", "level.building.rewards.end_stone_bricks.desc", Material.END_STONE_BRICKS, 16, WinRarityTypes.RARE),
    SMOOTH_STONE("level.building.rewards.smooth_stone.name", "level.building.rewards.smooth_stone.desc", Material.SMOOTH_STONE, 32, WinRarityTypes.RARE),
    CRIMSON_STEM("level.building.rewards.crimson_stem.name", "level.building.rewards.crimson_stem.desc", Material.CRIMSON_STEM, 16, WinRarityTypes.RARE),
    WARPED_STEM("level.building.rewards.warped_stem.name", "level.building.rewards.warped_stem.desc", Material.WARPED_STEM, 16, WinRarityTypes.RARE),
    CHERRY_PLANKS("level.building.rewards.cherry_planks.name", "level.building.rewards.cherry_planks.desc", Material.CHERRY_PLANKS, 32, WinRarityTypes.RARE),
    POLISHED_DIORITE("level.building.rewards.polished_diorite.name", "level.building.rewards.polished_diorite.desc", Material.POLISHED_DIORITE, 16, WinRarityTypes.RARE),
    POLISHED_GRANITE("level.building.rewards.polished_granite.name", "level.building.rewards.polished_granite.desc", Material.POLISHED_GRANITE, 16, WinRarityTypes.RARE),
    POLISHED_ANDESITE("level.building.rewards.polished_andesite.name", "level.building.rewards.polished_andesite.desc", Material.POLISHED_ANDESITE, 16, WinRarityTypes.RARE),

    BLOCK_OF_QUARTZ("level.building.rewards.block_of_quartz.name", "level.building.rewards.block_of_quartz.desc", Material.QUARTZ_BLOCK, 32, WinRarityTypes.EPIC),
    SMOOTH_QUARTZ("level.building.rewards.smooth_quartz.name", "level.building.rewards.smooth_quartz.desc", Material.SMOOTH_QUARTZ, 32, WinRarityTypes.EPIC),
    CHERRY_LOG("level.building.rewards.cherry_log.name", "level.building.rewards.cherry_log.desc", Material.CHERRY_LOG, 16, WinRarityTypes.EPIC),

    SEA_LANTERN("level.building.rewards.sea_lantern.name", "level.building.rewards.sea_lantern.desc", Material.SEA_LANTERN, 16, WinRarityTypes.LEGENDARY);

    private final String name;
    private final String description;
    private final Material material;
    private final int amount;
    private final WinRarityTypes rarity;

    RewardsLevelingBuilding(String name, String description, Material material, int amount, WinRarityTypes rarity) {
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

    public static List<RewardsLevelingBuilding> getRewards(WinRarityTypes rarity){
        List<RewardsLevelingBuilding> list = new ArrayList<>();

        for(RewardsLevelingBuilding reward : RewardsLevelingBuilding.values()){
            if(reward.getRarity().equals(rarity)){
                list.add(reward);
            }
        }

        return list;
    }

    public static RewardsLevelingBuilding getNextItem(){
        WinRarityTypes rarity = WinRarityTypes.getRandomRarity();
        List<RewardsLevelingBuilding> rewards = getRewards(rarity);

        return rewards.get((int) (Math.random() * rewards.size()));
    }
}
