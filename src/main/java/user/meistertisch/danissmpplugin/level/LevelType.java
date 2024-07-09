package user.meistertisch.danissmpplugin.level;

import org.bukkit.Material;

import java.awt.*;
import java.util.List;

public enum LevelType {
    MINING(Color.GRAY),
    FARMING(Color.ORANGE);

    final Color color;

    LevelType(Color color) {
        this.color = color;
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
