package user.meistertisch.danissmpplugin.level.types.adventure;

import org.bukkit.Material;
import user.meistertisch.danissmpplugin.level.invs.drumroll.WinRarityTypes;

import java.util.ArrayList;
import java.util.List;

public enum RewardsLevelingAdventure {
    STEAK("level.adventure.rewards.steak.name", "level.adventure.rewards.steak.desc", Material.COOKED_BEEF, 16, WinRarityTypes.COMMON),
    PORKCHOP("level.adventure.rewards.porkchop.name", "level.adventure.rewards.porkchop.desc", Material.COOKED_PORKCHOP, 16, WinRarityTypes.COMMON),
    MUTTON("level.adventure.rewards.mutton.name", "level.adventure.rewards.mutton.desc", Material.COOKED_MUTTON, 16, WinRarityTypes.COMMON),
    CHICKEN("level.adventure.rewards.chicken.name", "level.adventure.rewards.chicken.desc", Material.COOKED_CHICKEN, 16, WinRarityTypes.COMMON),
    BREAD("level.adventure.rewards.bread.name", "level.adventure.rewards.bread.desc", Material.BREAD, 32, WinRarityTypes.COMMON),
    MANY_STEAK("level.adventure.rewards.many_steak.name", "level.adventure.rewards.many_steak.desc", Material.COOKED_BEEF, 32, WinRarityTypes.UNCOMMON),
    MANY_PORKCHOP("level.adventure.rewards.many_porkchop.name", "level.adventure.rewards.many_porkchop.desc", Material.COOKED_PORKCHOP, 32, WinRarityTypes.UNCOMMON),
    MANY_MUTTON("level.adventure.rewards.many_mutton.name", "level.adventure.rewards.many_mutton.desc", Material.COOKED_MUTTON, 32, WinRarityTypes.UNCOMMON),
    MANY_CHICKEN("level.adventure.rewards.many_chicken.name", "level.adventure.rewards.many_chicken.desc", Material.COOKED_CHICKEN, 32, WinRarityTypes.UNCOMMON),
    MANY_BREAD("level.adventure.rewards.many_bread.name", "level.adventure.rewards.many_bread.desc", Material.BREAD, 64, WinRarityTypes.UNCOMMON),
    GOLDEN_CARROTS("level.adventure.rewards.golden_carrots.name", "level.adventure.rewards.golden_carrots.desc", Material.GOLDEN_CARROT, 16, WinRarityTypes.RARE),
    MANY_GOLDEN_CARROTS("level.adventure.rewards.many_golden_carrots.name", "level.adventure.rewards.many_golden_carrots.desc", Material.GOLDEN_CARROT, 32, WinRarityTypes.EPIC),
    TOTEM("level.adventure.rewards.totem.name", "level.adventure.rewards.totem.desc", Material.TOTEM_OF_UNDYING, 1, WinRarityTypes.LEGENDARY),
    ENCH_GAPPLE("level.adventure.rewards.ench_gapple.name", "level.adventure.rewards.ench_gapple.desc", Material.ENCHANTED_GOLDEN_APPLE, 4, WinRarityTypes.LEGENDARY);

    String name;
    String description;
    Material material;
    int amount;
    WinRarityTypes rarity;

    RewardsLevelingAdventure(String name, String description, Material material, int amount, WinRarityTypes rarity) {
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

    public static List<RewardsLevelingAdventure> getRewards(WinRarityTypes rarity){
        List<RewardsLevelingAdventure> list = new ArrayList<>();

        for(RewardsLevelingAdventure reward : RewardsLevelingAdventure.values()){
            if(reward.getRarity().equals(rarity)){
                list.add(reward);
            }
        }

        return list;
    }

    public static RewardsLevelingAdventure getNextItem(){
        WinRarityTypes rarity = WinRarityTypes.getRandomRarity();
        List<RewardsLevelingAdventure> rewards = getRewards(rarity);

        return rewards.get((int) (Math.random() * rewards.size()));
    }
}
