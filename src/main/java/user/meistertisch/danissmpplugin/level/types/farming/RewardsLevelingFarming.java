package user.meistertisch.danissmpplugin.level.types.farming;

import org.bukkit.Material;
import user.meistertisch.danissmpplugin.level.invs.drumroll.WinRarityTypes;

import java.util.ArrayList;
import java.util.List;

public enum RewardsLevelingFarming {
    WHEAT_SEEDS("level.farming.rewards.wheat_seeds.name", "level.farming.rewards.wheat_seeds.desc", Material.WHEAT_SEEDS, 16, WinRarityTypes.COMMON),
    BEETROOT_SEEDS("level.farming.rewards.beetroot_seeds.name", "level.farming.rewards.beetroot_seeds.desc", Material.BEETROOT_SEEDS, 16, WinRarityTypes.COMMON),
    CARROT("level.farming.rewards.carrot.name", "level.farming.rewards.carrot.desc", Material.CARROT, 8, WinRarityTypes.COMMON),
    POTATO("level.farming.rewards.potato.name", "level.farming.rewards.potato.desc", Material.POTATO, 8, WinRarityTypes.COMMON),
    BEETROOT("level.farming.rewards.beetroot.name", "level.farming.rewards.beetroot.desc", Material.BEETROOT, 9, WinRarityTypes.UNCOMMON),
    WHEAT("level.farming.rewards.wheat.name", "level.farming.rewards.wheat.desc", Material.WHEAT, 24, WinRarityTypes.UNCOMMON),
    KELP("level.farming.rewards.kelp.name", "level.farming.rewards.kelp.desc", Material.KELP, 16, WinRarityTypes.UNCOMMON),
    SUGAR_CANE("level.farming.rewards.sugar_cane.name", "level.farming.rewards.sugar_cane.desc", Material.SUGAR_CANE, 16, WinRarityTypes.UNCOMMON),
    BAMBOO("level.farming.rewards.bamboo.name", "level.farming.rewards.bamboo.desc", Material.BAMBOO, 16, WinRarityTypes.UNCOMMON),
    PUMPKIN_SEEDS("level.farming.rewards.pumpkin_seeds.name", "level.farming.rewards.pumpkin_seeds.desc", Material.PUMPKIN_SEEDS, 16, WinRarityTypes.UNCOMMON),
    MELON_SEEDS("level.farming.rewards.melon_seeds.name", "level.farming.rewards.melon_seeds.desc", Material.MELON_SEEDS, 16, WinRarityTypes.UNCOMMON),
    CACTUS("level.farming.rewards.cactus.name", "level.farming.rewards.cactus.desc", Material.CACTUS, 16, WinRarityTypes.RARE),
    PUMPKIN("level.farming.rewards.pumpkin.name", "level.farming.rewards.pumpkin.desc", Material.PUMPKIN, 4, WinRarityTypes.RARE),
    MELON("level.farming.rewards.melon.name", "level.farming.rewards.melon.desc", Material.MELON, 4, WinRarityTypes.RARE),
    SWEET_BERRIES("level.farming.rewards.sweet_berries.name", "level.farming.rewards.sweet_berries.desc", Material.SWEET_BERRIES, 16, WinRarityTypes.RARE),
    COCOA_BEANS("level.farming.rewards.cocoa_beans.name", "level.farming.rewards.cocoa_beans.desc", Material.COCOA_BEANS, 8, WinRarityTypes.EPIC),
    NETHER_WART("level.farming.rewards.nether_wart.name", "level.farming.rewards.nether_wart.desc", Material.NETHER_WART, 8, WinRarityTypes.EPIC),
    CHORUS_FRUIT("level.farming.rewards.chorus_fruit.name", "level.farming.rewards.chorus_fruit.desc", Material.CHORUS_FRUIT, 8, WinRarityTypes.LEGENDARY);

    private final String name;
    private final String description;
    private final Material material;
    private final int amount;
    private final WinRarityTypes rarity;

    RewardsLevelingFarming(String name, String description, Material material, int amount, WinRarityTypes rarity) {
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

    public static List<RewardsLevelingFarming> getRewards(WinRarityTypes rarity){
        List<RewardsLevelingFarming> list = new ArrayList<>();

        for(RewardsLevelingFarming reward : RewardsLevelingFarming.values()){
            if(reward.getRarity().equals(rarity)){
                list.add(reward);
            }
        }

        return list;
    }

    public static RewardsLevelingFarming getNextItem(){
        WinRarityTypes rarity = WinRarityTypes.getRandomRarity();
        List<RewardsLevelingFarming> rewards = getRewards(rarity);

        return rewards.get((int) (Math.random() * rewards.size()));
    }
}
