package Math.RandomChallenge;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Main {
    /* 建立全域靜態的隨機數產生器物件 */
    private static final Random random = new Random();
    /* 建立全域靜態的 Scanner 物件用來接收主控台輸入 */
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        /* 建立一個 ArrayList 用來存放目前玩家擁有的骰子點數 */
        List<Integer> currentDice = new ArrayList<>();

        /*
         * 使用 do-while 迴圈
         * 不論如何都會先擲第一次骰子，然後根據 pickLosers 的回傳值決定是否繼續重擲
         */
        do {
            rollDice(currentDice);
        } while (!pickLosers(currentDice));

        /* 當玩家按下 Enter 結束迴圈後印出結算提示 */
        System.out.println("Game over.  Real game would score and continue.");
    }

    /* 負責處理擲骰子與補滿 5 顆骰子的方法 */
    private static void rollDice(List<Integer> currentDice) {
        /* 計算這次需要新補幾顆骰子（總共 5 顆，減去目前保留的數量） */
        int randomCount = 5 - currentDice.size();

        /*
         * 利用 Random 的 ints 方法產生指定數量的隨機整數
         * 範圍設定為 1 到 7（Exclusive），所以實際數字為 1 到 6
         */
        var newDice = random
                .ints(randomCount, 1, 7) /* 產生隨機數串流 */
                .sorted() /* 將骰子點數由小到大排序 */
                .boxed() /* 將基本型態 int 包裝成 Integer 物件 */
                .toList(); /* 收集成一個不可變的 List */

        /* 將這次新擲出來的骰子加入到目前的骰子清單中（此處會改變 currentDice 的狀態） */
        currentDice.addAll(newDice);
        /* 印出玩家目前擁有的所有骰子點數 */
        System.out.println("You're dice are: " + currentDice);
    }

    /* 負責提示使用者並取得重擲選擇的方法，若決定結束則回傳 true */
    private static boolean pickLosers(List<Integer> currentDice) {
        /* 畫面提示文字區塊 */
        String prompt = """
                Press Enter to Score.
                Type "ALL" to re-roll all the dice.
                List numbers (separated by spaces) to re-roll selected dice.
                    """;
        System.out.print(prompt + "-->  ");
        /* 讀取使用者輸入的整行文字 */
        String userInput = scanner.nextLine();

        /* 如果使用者什麼都沒打直接按 Enter（Blank），代表要保留目前點數並進行結算 */
        if (userInput.isBlank()) {
            return true;
        }

        try {
            /*
             * 將使用者輸入的字串用空格 " " 拆開成陣列
             * 傳入 removeDice 方法，把不想要的骰子從清單中剔除
             */
            removeDice(currentDice, userInput.split(" "));
        } catch (Exception e) {
            /* 若發生任何異常，印出錯誤追蹤訊息並提示重新輸入 */
            e.printStackTrace(System.out);
            System.out.println("Bad input, Try again");
        }
        /* 回傳 false 讓 do-while 迴圈繼續執行，進行下一輪補骰子與重擲 */
        return false;
    }

    /* 負責將使用者決定不要的骰子點數從清單中移除的方法 */
    private static void removeDice(List<Integer> currentDice, String[] selected) {
        /* 如果使用者輸入的是 "ALL"，代表全部都不要，直接清空整個骰子清單 */
        if (selected.length == 1 && selected[0].contains("ALL")) {
            currentDice.clear();
        } else {
            /* 逐一檢查使用者輸入的每一個要丟棄的點數 */
            for (String removed : selected) {
                /*
                 * 關鍵：List.remove() 有多載機制
                 * 傳入 int 會被當成索引值值（index），傳入物件才會刪除對應的元素值
                 * 因此必須使用 Integer.valueOf(removed) 轉成物件，確保是刪除該「骰子點數」
                 */
                currentDice.remove(Integer.valueOf(removed));
            }
            /* 印出過濾後、目前保留下來的骰子點數 */
            System.out.println("Keeping " + currentDice);
        }
    }
}