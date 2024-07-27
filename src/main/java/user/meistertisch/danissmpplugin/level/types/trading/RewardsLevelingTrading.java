package user.meistertisch.danissmpplugin.level.types.trading;

import org.bukkit.Material;
import org.bukkit.entity.Villager;
import user.meistertisch.danissmpplugin.level.invs.drumroll.WinRarityTypes;
import user.meistertisch.danissmpplugin.level.types.adventure.RewardsLevelingAdventure;

import java.util.ArrayList;
import java.util.List;

public enum RewardsLevelingTrading {
    BLAST_FURNANCE("level.trading.rewards.blast_furnance.name", "level.trading.rewards.blast_furnance.desc", Material.BLAST_FURNACE, 2, WinRarityTypes.COMMON),
    SMOKER("level.trading.rewards.smoker.name", "level.trading.rewards.smoker.desc", Material.SMOKER, 2, WinRarityTypes.COMMON),
    CARTOGRAPHY_TABLE("level.trading.rewards.cartography_table.name", "level.trading.rewards.cartography_table.desc", Material.CARTOGRAPHY_TABLE, 2, WinRarityTypes.COMMON),
    BREWING_STAND("level.trading.rewards.brewing_stand.name", "level.trading.rewards.brewing_stand.desc", Material.BREWING_STAND, 2, WinRarityTypes.COMMON),
    COMPOSTER("level.trading.rewards.composter.name", "level.trading.rewards.composter.desc", Material.COMPOSTER, 2, WinRarityTypes.COMMON),
    BARREL("level.trading.rewards.barrel.name", "level.trading.rewards.barrel.desc", Material.BARREL, 2, WinRarityTypes.COMMON),
    FLETCHING_TABLE("level.trading.rewards.fletching_table.name", "level.trading.rewards.fletching_table.desc", Material.FLETCHING_TABLE, 2, WinRarityTypes.COMMON),
    CAULDRON("level.trading.rewards.cauldron.name", "level.trading.rewards.cauldron.desc", Material.CAULDRON, 2, WinRarityTypes.COMMON),
    LECTERN("level.trading.rewards.lectern.name", "level.trading.rewards.lectern.desc", Material.LECTERN, 2, WinRarityTypes.COMMON),
    STONECUTTER("level.trading.rewards.stonecutter.name", "level.trading.rewards.stonecutter.desc", Material.STONECUTTER, 2, WinRarityTypes.COMMON),
    LOOM("level.trading.rewards.loom.name", "level.trading.rewards.loom.desc", Material.LOOM, 2, WinRarityTypes.COMMON),
    SMITHING_TABLE("level.trading.rewards.smithing_table.name", "level.trading.rewards.smithing_table.desc", Material.SMITHING_TABLE, 2, WinRarityTypes.COMMON),
    GRINDSTONE("level.trading.rewards.grindstone.name", "level.trading.rewards.grindstone.desc", Material.GRINDSTONE, 2, WinRarityTypes.COMMON),
    SOME_MONEY("level.trading.rewards.some_money.name", "level.trading.rewards.some_money.desc", Material.EMERALD, 8, WinRarityTypes.COMMON),
    MORE_MONEY("level.trading.rewards.more_money.name", "level.trading.rewards.more_money.desc", Material.EMERALD, 16, WinRarityTypes.UNCOMMON),
    EVEN_MORE_MONEY("level.trading.rewards.even_more_money.name", "level.trading.rewards.even_more_money.desc", Material.EMERALD, 32, WinRarityTypes.RARE),
    TONS_OF_MONEY("level.trading.rewards.tons_of_money.name", "level.trading.rewards.tons_of_money.desc", Material.EMERALD, 64, WinRarityTypes.EPIC),
    A_LOT_OF_MONEY("level.trading.rewards.a_lot_of_money.name", "level.trading.rewards.a_lot_of_money.desc", Material.EMERALD_BLOCK, 9, WinRarityTypes.LEGENDARY),
    VILLAGER("level.trading.rewards.villager.name", "level.trading.rewards.villager.desc", Material.VILLAGER_SPAWN_EGG, 1, WinRarityTypes.LEGENDARY);

    private final String name;
    private final String description;
    private final Material material;
    private final int amount;
    private final WinRarityTypes rarity;

    RewardsLevelingTrading(String name, String description, Material material, int amount, WinRarityTypes rarity) {
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
