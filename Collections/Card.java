package Collections;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/* 
使用 Record 定義不可變資料
Record 是 Java 中專門用來存放資料的類別結構
它會自動產生 constructor, getter, equals, hashCode 與 toString
*/
public record Card(Suit suit, String face, int rank) {
    public enum Suit {
        // 嵌套 Enum 定義花色，並包含一個獲取 ASCII 圖案的方法
        CLUB, DIAMOND, HEART, SPADE;

        public char getImage() {
            // 使用 ASCII 碼代表 ♣, ♦, ♥, ♠
            return (new char[] { '\u2663', '\u2666', '\u2665', '\u2660' })[this.ordinal()];
        }
    }

    public static Comparator<Card> sortRankReversedSuit() {
        return Comparator.comparing(Card::rank)
                .reversed() // 點數大的在前
                .thenComparing(Card::suit); // 點數相同比花色
    }

    @Override
    public String toString() {
        // 自定義輸出格式，例如：10♥(8)
        int displayIndex = face.equals("10") ? 2 : 1;
        String faceAbbr = face.substring(0, displayIndex);
        return String.format("%s%c(%d)", faceAbbr, suit.getImage(), rank);
    }

    // Static Factory Methods
    /*
     * 為了方便建立牌組
     * 我們不直接使用 new，而是透過靜態方法來封裝邏輯，確保資料的正確性
     * 
     * 透過固定規則建立 Card
     */

    public static Card getNumericCard(Suit suit, int cardNumber) {
        if (cardNumber >= 2 && cardNumber <= 10) {
            // 數值牌：等級(rank)從 0 開始計算 (cardNumber - 2)
            return new Card(suit, String.valueOf(cardNumber), cardNumber - 2);
        }
        System.out.println("無效的數值牌");
        return null;
    }

    // 建立 J/Q/K/A
    public static Card getFaceCard(Suit suit, char abbrev) {
        int charIndex = "JQKA".indexOf(abbrev);
        if (charIndex > -1) {
            // 花色牌：等級銜接在數值牌之後 (9 ~ 12)
            return new Card(suit, String.valueOf(abbrev), charIndex + 9);
        }
        System.out.println("無效的花色牌");
        return null;
    }

    // 產 52張牌

    public static List<Card> getStandardDeck() {
        List<Card> deck = new ArrayList<>(52);

        for (Suit suit : Suit.values()) {
            // 產生 2-10 的數字牌
            for (int i = 2; i <= 10; i++) {
                deck.add(getNumericCard(suit, i));
            }
            // 產生 J, Q, K, A 的花色牌
            for (char c : new char[] { 'J', 'Q', 'K', 'A' }) {
                deck.add(getFaceCard(suit, c));
            }
        }
        return deck;
    }

    public static void printDeck(List<Card> deck) {
        printDeck(deck, "Current Deck", 4);
    }

    public static void printDeck(List<Card> deck, String description, int rows) {
        System.out.println("-".repeat(20));
        if (description != null)
            System.out.println(description);

        int cardsInRow = deck.size() / rows; // 計算每一列幾張牌
        for (int i = 0; i < rows; i++) {
            int startIndex = i * cardsInRow;
            int endIndex = startIndex + cardsInRow;
            // 擷取子清單並使用 Lambda 列印
            // subList 是 List 介面提供的方法，用來「切出一段子清單」
            deck.subList(startIndex, endIndex).forEach(c -> System.out.print(c + " "));
            System.out.println(); // 換行
        }
    }
}