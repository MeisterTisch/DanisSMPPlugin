package user.meistertisch.danissmpplugin.level.drumroll;

public enum WinRarityTypes {
    COMMON(50),
    UNCOMMON(30),
    RARE(15),
    EPIC(4),
    LEGENDARY(1);

    final int winningChance;

    WinRarityTypes(int winningChance) {
        this.winningChance = winningChance;
    }

    public int getWinningChance() {
        return winningChance;
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
