package user.meistertisch.danissmpplugin.level.types;

import net.kyori.adventure.bossbar.BossBar;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Material;

import java.util.List;

public enum LevelType {
    MINING(NamedTextColor.GRAY, BossBar.Color.WHITE),
    FARMING(NamedTextColor.GOLD, BossBar.Color.YELLOW);

    private final NamedTextColor color;
    private final BossBar.Color barColor;

    LevelType(NamedTextColor color, BossBar.Color barColor) {
        this.color = color;
        this.barColor = barColor;
    }

    public NamedTextColor getColor() {
        return color;
    }

    public BossBar.Color getBarColor() {
        return barColor;
    }

    public List<Material> getValidBlocks(){
        if(this == MINING) {
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
        } else {
            return null;
        }


    }
}
