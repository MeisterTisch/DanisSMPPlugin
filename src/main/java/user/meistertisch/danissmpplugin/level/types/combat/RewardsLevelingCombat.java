package user.meistertisch.danissmpplugin.level.types.combat;

import org.bukkit.Material;
import user.meistertisch.danissmpplugin.level.invs.drumroll.WinRarityTypes;

import java.util.ArrayList;
import java.util.List;

public enum RewardsLevelingCombat {
    STICK("level.combat.rewards.stick.name", "level.combat.rewards.stick.desc", Material.STICK, 8, WinRarityTypes.COMMON),
    WOODEN_SWORD("level.combat.rewards.wooden_sword.name", "level.combat.rewards.wooden_sword.desc", Material.WOODEN_SWORD, 1, WinRarityTypes.UNCOMMON),
    BOW("level.combat.rewards.bow.name", "level.combat.rewards.bow.desc", Material.BOW, 1, WinRarityTypes.UNCOMMON),
    ARROW("level.combat.rewards.arrow.name", "level.combat.rewards.arrow.desc", Material.ARROW, 16, WinRarityTypes.UNCOMMON),
    CROSSBOW("level.combat.rewards.crossbow.name", "level.combat.rewards.crossbow.desc", Material.CROSSBOW, 1, WinRarityTypes.UNCOMMON),
    SPECTRAL_ARROW("level.combat.rewards.spectral_arrow.name", "level.combat.rewards.spectral_arrow.desc", Material.SPECTRAL_ARROW, 8, WinRarityTypes.RARE),
    LEATHER_HELMET("level.combat.rewards.leather_helmet.name", "level.combat.rewards.leather_helmet.desc", Material.LEATHER_HELMET, 1, WinRarityTypes.RARE),
    LEATHER_CHESTPLATE("level.combat.rewards.leather_chestplate.name", "level.combat.rewards.leather_chestplate.desc", Material.LEATHER_CHESTPLATE, 1, WinRarityTypes.RARE),
    LEATHER_LEGGINGS("level.combat.rewards.leather_leggings.name", "level.combat.rewards.leather_leggings.desc", Material.LEATHER_LEGGINGS, 1, WinRarityTypes.RARE),
    LEATHER_BOOTS("level.combat.rewards.leather_boots.name", "level.combat.rewards.leather_boots.desc", Material.LEATHER_BOOTS, 1, WinRarityTypes.RARE),
    STONE_SWORD("level.combat.rewards.stone_sword.name", "level.combat.rewards.stone_sword.desc", Material.STONE_SWORD, 1, WinRarityTypes.RARE),
    SHIELD("level.combat.rewards.shield.name", "level.combat.rewards.shield.desc", Material.SHIELD, 1, WinRarityTypes.RARE),
    IRON_SWORD("level.combat.rewards.iron_sword.name", "level.combat.rewards.iron_sword.desc", Material.IRON_SWORD, 1, WinRarityTypes.EPIC),
    IRON_HELMET("level.combat.rewards.iron_helmet.name", "level.combat.rewards.iron_helmet.desc", Material.IRON_HELMET, 1, WinRarityTypes.EPIC),
    IRON_CHESTPLATE("level.combat.rewards.iron_chestplate.name", "level.combat.rewards.iron_chestplate.desc", Material.IRON_CHESTPLATE, 1, WinRarityTypes.EPIC),
    IRON_LEGGINGS("level.combat.rewards.iron_leggings.name", "level.combat.rewards.iron_leggings.desc", Material.IRON_LEGGINGS, 1, WinRarityTypes.EPIC),
    IRON_BOOTS("level.combat.rewards.iron_boots.name", "level.combat.rewards.iron_boots.desc", Material.IRON_BOOTS, 1, WinRarityTypes.EPIC),
    DIAMOND_HELMET("level.combat.rewards.diamond_helmet.name", "level.combat.rewards.diamond_helmet.desc", Material.DIAMOND_HELMET, 1, WinRarityTypes.LEGENDARY),
    DIAMOND_CHESTPLATE("level.combat.rewards.diamond_chestplate.name", "level.combat.rewards.diamond_chestplate.desc", Material.DIAMOND_CHESTPLATE, 1, WinRarityTypes.LEGENDARY),
    DIAMOND_LEGGINGS("level.combat.rewards.diamond_leggings.name", "level.combat.rewards.diamond_leggings.desc", Material.DIAMOND_LEGGINGS, 1, WinRarityTypes.LEGENDARY),
    DIAMOND_BOOTS("level.combat.rewards.diamond_boots.name", "level.combat.rewards.diamond_boots.desc", Material.DIAMOND_BOOTS, 1, WinRarityTypes.LEGENDARY),
    DIAMOND_SWORD("level.combat.rewards.diamond_sword.name", "level.combat.rewards.diamond_sword.desc", Material.DIAMOND_SWORD, 1, WinRarityTypes.LEGENDARY);

    private final String name;
    private final String description;
    private final Material material;
    private final int amount;
    private final WinRarityTypes rarity;

    RewardsLevelingCombat(String name, String description, Material material, int amount, WinRarityTypes rarity) {
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

    public static List<RewardsLevelingCombat> getRewards(WinRarityTypes rarity){
        List<RewardsLevelingCombat> list = new ArrayList<>();

        for(RewardsLevelingCombat reward : RewardsLevelingCombat.values()){
            if(reward.getRarity().equals(rarity)){
                list.add(reward);
            }
        }

        return list;
    }

    public static RewardsLevelingCombat getNextItem(){
        WinRarityTypes rarity = WinRarityTypes.getRandomRarity();
        List<RewardsLevelingCombat> rewards = getRewards(rarity);

        return rewards.get((int) (Math.random() * rewards.size()));
    }
}
