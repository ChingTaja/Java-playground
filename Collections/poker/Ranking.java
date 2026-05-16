package Collections.poker;

// 定義手牌等級
public enum Ranking {
    NONE, ONE_PAIR, TWO_PAIR, THREE_OF_A_KIND, FULL_HOUSE, FOUR_OF_A_KIND;

    @Override
    public String toString() {
        return this.name().replace('_', ' ');
    }
}