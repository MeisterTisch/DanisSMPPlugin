package user.meistertisch.danissmpplugin.level.types;

import net.kyori.adventure.bossbar.BossBar;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.Inventory;

import java.util.List;

public enum LevelType {
    MINING(NamedTextColor.GRAY, BossBar.Color.WHITE, Material.DIAMOND_PICKAXE),
    MONSTERS(NamedTextColor.RED, BossBar.Color.RED, Material.DIAMOND_SWORD),
    FARMING(NamedTextColor.GOLD, BossBar.Color.YELLOW, Material.DIAMOND_HOE);

    private final NamedTextColor color;
    private final BossBar.Color barColor;
    private final Material invStartItem;

    LevelType(NamedTextColor color, BossBar.Color barColor, Material invStartItem) {
        this.color = color;
        this.barColor = barColor;
        this.invStartItem = invStartItem;
    }

    public NamedTextColor getColor() {
        return color;
    }

    public BossBar.Color getBarColor() {
        return barColor;
    }

    public Material getInvStartItem() {
        return invStartItem;
    }

    public List<Material> getValidBlocks(){
        switch (this){
            case MINING -> {
                return List.of(
                        Material.TUFF,
                        Material.GRANITE,
                        Material.DEEPSLATE,
                        Material.STONE,
                        Material.DIORITE,
                        Material.ANDESITE,
                        Material.COAL_ORE,
                        Material.IRON_ORE,
                        Material.GOLD_ORE,
                        Material.DIAMOND_ORE,
                        Material.EMERALD_ORE,
                        Material.LAPIS_ORE,
                        Material.REDSTONE_ORE,
                        Material.COPPER_ORE,
                        Material.DEEPSLATE_COAL_ORE,
                        Material.DEEPSLATE_IRON_ORE,
                        Material.DEEPSLATE_GOLD_ORE,
                        Material.DEEPSLATE_DIAMOND_ORE,
                        Material.DEEPSLATE_EMERALD_ORE,
                        Material.DEEPSLATE_LAPIS_ORE,
                        Material.DEEPSLATE_REDSTONE_ORE,
                        Material.DEEPSLATE_COPPER_ORE
                );
            }
            case FARMING -> {
                return List.of(
                        Material.CARROTS,
                        Material.POTATOES,
                        Material.WHEAT,
                        Material.PUMPKIN,
                        Material.MELON,
                        Material.BEETROOTS,
                        Material.TORCHFLOWER,
                        Material.SWEET_BERRY_BUSH
                );
            }
            default -> {
                return List.of();
            }
        }
    }

    public List<EntityType> getValidMobs(){
        switch (this){
            case MONSTERS -> {
                return List.of(
                        EntityType.ZOMBIE,
                        EntityType.SKELETON,
                        EntityType.CREEPER,
                        EntityType.SPIDER,
                        EntityType.CAVE_SPIDER,
                        EntityType.ENDERMAN,
                        EntityType.PHANTOM,
                        EntityType.WITCH,
                        EntityType.ELDER_GUARDIAN,
                        EntityType.BLAZE,
                        EntityType.GHAST,
                        EntityType.HOGLIN,
                        EntityType.PIGLIN,
                        EntityType.PIGLIN_BRUTE,
                        EntityType.WITHER,
                        EntityType.WITHER_SKELETON,
                        EntityType.ZOMBIFIED_PIGLIN,
                        EntityType.ZOMBIE_VILLAGER,
                        EntityType.ENDER_DRAGON,
                        EntityType.ENDERMITE,
                        EntityType.DROWNED,
                        EntityType.BOGGED,
                        EntityType.EVOKER,
                        EntityType.BREEZE,
                        EntityType.GUARDIAN,
                        EntityType.HUSK,
                        EntityType.MAGMA_CUBE,
                        EntityType.PILLAGER,
                        EntityType.RAVAGER,
                        EntityType.SHULKER,
                        EntityType.SILVERFISH,
                        EntityType.STRAY,
                        EntityType.VEX,
                        EntityType.VINDICATOR,
                        EntityType.WARDEN,
                        EntityType.SLIME,
                        EntityType.ZOGLIN,
                        EntityType.ILLUSIONER
                );
            }
            default -> {
                return List.of();
            }
        }
    }

    public Inventory getRewardInventory(){

        switch (this){
            case MINING -> {

            }
        }
        return null;
    }
}
