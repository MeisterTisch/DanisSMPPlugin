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
}
