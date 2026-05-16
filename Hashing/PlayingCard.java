package Hashing;

public class PlayingCard {

    private String suit;
    private String face;
    private int internalHash;

    public PlayingCard(String suit, String face) {
        this.suit = suit;
        this.face = face;
        // 真正好的 hashCode 用法這裡只是示範
        // 控制 哪些牌進同一個 bucket
        this.internalHash = (suit.equals("Hearts")) ? 11 : 12;
    }

    @Override
    public String toString() {
        return face + " of " + suit;
    }

    @Override
    // 如果 equals 為 true，hashCode 必須相同
    public boolean equals(Object o) {
        if (this == o)
            return true; // 先檢查是否為同一個記憶體位址
        if (o == null || getClass() != o.getClass()) // 檢查類別是否一致
            return false;

        PlayingCard that = (PlayingCard) o; // 內容相等則視為物件相等

        if (!suit.equals(that.suit))
            return false;
        return face.equals(that.face);
    }

    // 用質數（如 31）作為乘數，有助於雜湊碼均勻分佈
    @Override
    public int hashCode() {
        // 典型的雜湊演算法實作
        int result = suit.hashCode();
        result = 31 * result + face.hashCode();
        return result;
    }
}
