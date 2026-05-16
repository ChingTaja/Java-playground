package Collections;

import java.util.List;
import java.util.Arrays;
import java.util.Collections;
import java.util.ArrayList;
import java.util.Comparator;

public class Main {

    public static void main(String[] args) {
        Card[] cardArray = new Card[13];
        Card aceOfHearts = Card.getFaceCard(Card.Suit.HEART, 'A');
        Arrays.fill(cardArray, aceOfHearts);
        Card.printDeck(Arrays.asList(cardArray), "Ace of heart", 1);

        /*
         * 很多人以為 Collections.fill 可以像 Arrays.fill 一樣把空集合填滿
         * 但其實它只==能「替換」現有的元素==
         * 
         * - Capacity：這是底層陣列實際分配的記憶體空間，決定了容器「能裝多少」
         * - Size：這是目前集合中實際存放的元素數量，決定了 Collections.copy 或 fill 「能操作多少」
         * 
         */

        // ❌ 錯誤示範：這不會產生 52 張牌
        List<Card> cards = new ArrayList<>(52); // 僅設定容量(Capacity)，大小(Size)仍為 0
        Collections.fill(cards, aceOfHearts);
        System.out.println(cards.size()); // 輸出：0 (因為裡面沒有元素可以被替換)

        // ✅ 正確做法：使用 nCopies 產生包含初始資料的清單
        List<Card> acesOfHearts = Collections.nCopies(13, aceOfHearts);
        Card.printDeck(acesOfHearts, "13張紅心A", 1);

        Card kingOfClubs = Card.getFaceCard(Card.Suit.CLUB, 'K');
        List<Card> kingsOfClubs = Collections.nCopies(13, kingOfClubs);

        Collections.addAll(cards, cardArray);
        Card.printDeck(cards, "Card Collection with Ace added", 1);

        // Collections.copy(目標清單 , 來源清單 )
        // 目標清單的大小(size) 必須大於或等於 來源清單
        Collections.copy(cards, kingsOfClubs);
        Card.printDeck(cards, "Card Collection with Kings copyied", 1);

        cards = List.copyOf(kingsOfClubs);
        Card.printDeck(cards, "List Copy of Kings", 1);

        List<Card> deck = Card.getStandardDeck();
        Card.printDeck(deck);

        Collections.shuffle(deck);

        Collections.reverse(deck);

        // 定義一個局部變數，存儲排序演算法
        var sortingAlgorithm = Comparator.comparing(Card::rank) // 第一優先：按點數排序
                .thenComparing(Card::suit); // 第二優先：點數相同時，按花色排序

        // 使用 Collections 工具類進行排序
        Collections.sort(deck, sortingAlgorithm);

        List<Card> kings = new ArrayList<>(deck.subList(4, 8));
        List<Card> tens = new ArrayList<>(deck.subList(16, 20));

        // 1. 搜尋子清單位置 (必須連續)
        int subListIndex = Collections.indexOfSubList(deck, tens);
        System.out.println("子清單起始索引: " + subListIndex); // 若洗牌後不連續，會回傳 -1

        // 2. 檢查是否包含 (只要有就好，不論位置)
        boolean hasAll = deck.containsAll(tens);
        System.out.println("是否包含所有元素: " + hasAll); // 只要元素都在，永遠為 true

        boolean isDisjoint = Collections.disjoint(deck, tens);
        boolean isDisjoint2 = Collections.disjoint(kings, tens);

        // 1. 搜尋前必須先排序，且 Comparator 必須一致 , Comparator --> sortingAlgorithm , Comparator
        // 像是一把尺
        // 步驟 A：用這把尺「排序」
        deck.sort(sortingAlgorithm);
        // 步驟 B：用「同一把尺」搜尋
        Card tenOfHearts = Card.getNumericCard(Card.Suit.HEART, 10);
        int foumdIndex = Collections.binarySearch(deck, tenOfHearts, sortingAlgorithm);

        Card tenOfClubs = Card.getNumericCard(Card.Suit.CLUB, 10);

        // 將所有的梅花 10 換成紅心 10
        boolean changed = Collections.replaceAll(deck, tenOfClubs, tenOfHearts);
        if (changed) {
            System.out.println("已完成替換！");
        }

        int count = Collections.frequency(deck, tenOfClubs);

        // 使用之前定義的 sortingAlgorithm (按等級和花色排序)
        Card bestCard = Collections.max(deck, sortingAlgorithm);
        Card worstCard = Collections.min(deck, sortingAlgorithm);

        var sortBySuit = Comparator.comparing(Card::suit)
                .thenComparing(Card::rank);
        deck.sort(sortBySuit);

        List<Card> copied = new ArrayList<>(deck.subList(0, 13)); // 取得 13 張梅花

        // 1. rotate (正數：末尾移到前面；負數：前端移到後面)
        Collections.rotate(copied, 2); // 最後兩張（Q, K）跑到最前面
        Collections.rotate(copied, -2); // 最前兩張（2, 3）跑到最後面

        copied = new ArrayList<>(deck.subList(0, 13)); // 取得 13 張梅花
        // 應用：利用 swap 進行自定義反轉
        for (int i = 0; i < copied.size() / 2; i++) {
            Collections.swap(copied, i, copied.size() - 1 - i);
        }

    }

}