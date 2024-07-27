package user.meistertisch.danissmpplugin.level.types.magic;

import org.bukkit.Material;
import user.meistertisch.danissmpplugin.level.invs.drumroll.WinRarityTypes;

import java.util.ArrayList;
import java.util.List;

public enum RewardsLevelingMagic {
    LAPIS("level.magic.rewards.lapis.name", "level.magic.rewards.lapis.desc", Material.LAPIS_LAZULI, 32, WinRarityTypes.COMMON),
    MANY_LAPIS("level.magic.rewards.many_lapis.name", "level.magic.rewards.many_lapis.desc", Material.LAPIS_LAZULI, 64, WinRarityTypes.UNCOMMON),
    BOOKS("level.magic.rewards.books.name", "level.magic.rewards.books.desc", Material.BOOK, 16, WinRarityTypes.UNCOMMON),
    ENDER_PEARL("level.magic.rewards.ender_pearl.name", "level.magic.rewards.ender_pearl.desc", Material.ENDER_PEARL, 16, WinRarityTypes.RARE),
    MANY_BOOKS("level.magic.rewards.many_books.name", "level.magic.rewards.many_books.desc", Material.BOOK, 32, WinRarityTypes.RARE),
    ENCHANTING_TABLE("level.magic.rewards.enchanting_table.name", "level.magic.rewards.enchanting_table.desc", Material.ENCHANTING_TABLE, 1, WinRarityTypes.RARE),
    BOTTLE_O_ENCHANTING("level.magic.rewards.bottle_o_enchanting.name", "level.magic.rewards.bottle_o_enchanting.desc", Material.EXPERIENCE_BOTTLE, 16, WinRarityTypes.EPIC),
    MANY_BOTTLES_O_ENCHANTING("level.magic.rewards.many_bottle_o_enchanting.name", "level.magic.rewards.many_bottle_o_enchanting.desc", Material.EXPERIENCE_BOTTLE, 32, WinRarityTypes.LEGENDARY);

    private final String name;
    private final String description;
    private final Material material;
    private final int amount;
    private final WinRarityTypes rarity;

    RewardsLevelingMagic(String name, String description, Material material, int amount, WinRarityTypes rarity) {
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

    public static List<RewardsLevelingMagic> getRewards(WinRarityTypes rarity){
        List<RewardsLevelingMagic> list = new ArrayList<>();

        for(RewardsLevelingMagic reward : RewardsLevelingMagic.values()){
            if(reward.getRarity().equals(rarity)){
                list.add(reward);
            }
        }

        return list;
    }

    public static RewardsLevelingMagic getNextItem(){
        WinRarityTypes rarity = WinRarityTypes.getRandomRarity();
        List<RewardsLevelingMagic> rewards = getRewards(rarity);

        return rewards.get((int) (Math.random() * rewards.size()));
    }
}
