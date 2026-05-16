package Hashing;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.HashSet;

public class Main {
    public static void main(String[] args) {
        String aText = "Hello";
        String bText = "Hello";
        // 指向 String Pool（字串池）中的同一個 "Hello" 物件

        String cText = String.join("", "H", "e", "l", "l", "o");
        // 透過執行時拼接產生的新 String 物件（不一定進入字串池）
        String dText = "He".concat("llo");
        String eText = "hello";

        List<String> hellos = Arrays.asList(aText, bText, cText, dText, eText);

        hellos.forEach(s -> System.out.println(s + ":" + s.hashCode()));

        System.out.println(aText == bText);
        // true：兩者指向同一個 String Pool 物件（記憶體位置相同）

        System.out.println(aText == cText);
        // false：cText 是新建物件，與 String Pool 中的 aText 不同物件

        System.out.println(aText.equals(cText));
        // true：String 已覆寫 equals()，比較的是「字元內容」而非記憶體位置

        Set<String> mySet = new HashSet<>(hellos);

        System.out.println("my Set" + mySet);
        System.out.println(" of element" + mySet.size());

        for (String setValue : mySet) {
            System.out.print(setValue + ": ");
            for (int i = 0; i < hellos.size(); i++) {
                if (setValue == hellos.get(i)) {
                    System.out.print(i + ", ");
                }
            }
            System.out.println(" ");
        }

        PlayingCard aceHearts = new PlayingCard("Hearts", "Ace");
        PlayingCard kingClubs = new PlayingCard("Clubs", "King");
        PlayingCard queenSpades = new PlayingCard("Spades", "Queen");

        List<PlayingCard> cards = Arrays.asList(aceHearts, kingClubs, queenSpades);
        cards.forEach(s -> System.out.println(s + ": " + s.hashCode()));

        Set<PlayingCard> deck = new HashSet<>();
        for (PlayingCard c : cards) {
            /*
             * 當你呼叫 set.add(object) 時HashSet 的運作邏輯如下：
             * 
             * 計算 hashCode()：判斷物件應該放入哪個「桶」。
             * 
             * 檢查桶位是否衝突：
             * 
             * - 如果桶子是空的：直接放入
             * 
             * - 如果桶子已有元素：呼叫 equals() 逐一比對桶內的物件
             * 
             * 判定重複：只有當 hashCode 相同且 equals 回傳 true 時，才視為重複，拒絕加入
             */
            if (!deck.add(c)) {
                System.out.println("Found a duplicate for " + c);
            }
        }
        System.out.println(deck);
    }
}
