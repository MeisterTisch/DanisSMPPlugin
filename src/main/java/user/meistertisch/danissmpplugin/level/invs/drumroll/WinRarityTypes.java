package user.meistertisch.danissmpplugin.level.invs.drumroll;

import net.kyori.adventure.text.format.TextColor;

public enum WinRarityTypes {
    COMMON(50, "level.rewarding.common", TextColor.color(0xc9caca)),
    UNCOMMON(30, "level.rewarding.uncommon", TextColor.color(0x7cd702)),
    RARE(15, "level.rewarding.rare", TextColor.color(0x00bfdf)),
    EPIC(4, "level.rewarding.epic", TextColor.color(0xc42fe8)),
    LEGENDARY(1, "level.rewarding.legendary", TextColor.color(0xeea866));

    private final int winningChance;
    private final String name;
    private final TextColor color;

    WinRarityTypes(int winningChance, String name, TextColor color) {
        this.winningChance = winningChance;
        this.name = name;
        this.color = color;
    }

    public int getWinningChance() {
        return winningChance;
    }

    public String getName() {
        return name;
    }

    public TextColor getColor() {
        return color;
    }

    public static WinRarityTypes getRandomRarity(){
        int random = (int) (Math.random() * 100);
        if(random < LEGENDARY.getWinningChance()){
            return LEGENDARY;
        } else if(random < EPIC.getWinningChance()){
            return EPIC;
        } else if(random < RARE.getWinningChance()){
            return RARE;
        } else if(random < UNCOMMON.getWinningChance()){
            return UNCOMMON;
        } else {
            return COMMON;
        }
    }
}
