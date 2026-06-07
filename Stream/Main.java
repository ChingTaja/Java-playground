package Stream;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.LinkedHashMap;
import java.util.Arrays;
import java.util.Random;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Main {

    public static void main(String[] args) {

        // 建立一個初始容量為 75 的 ArrayList，用來存放所有的 Bingo 球標籤（B1 ~ O75）
        List<String> bingoPool = new ArrayList<>(75);

        // 巢狀迴圈：動態產生 Bingo 球，每個英文字母（B、I、N、G、O）分別對應 15 個遞增的數字
        int start = 1;

        for (char c : "BINGO".toCharArray()) {

            for (int i = start; i < (start + 15); i++) {

                // 提到：開頭加上空字串 "" 是為了確保字元 c 與整數 i 進行字串鏈結（Concatenation），否則會變成整數相加
                bingoPool.add("" + c + i);

                // System.out.println("" + c + i);
            }

            start += 15;
        }

        // 將產生的 75 顆 Bingo 球進行隨機洗牌（Shuffle）
        Collections.shuffle(bingoPool);

        // 傳統作法：使用 For 迴圈列印出洗牌後的前 15 顆球
        for (int i = 0; i < 15; i++) {
            System.out.println(bingoPool.get(i));
        }

        System.out.println("------------------------------------");

        // 傳統 Collection 的陷阱與 View 觀念：
        // 原本寫：List<String> firstOnes = bingoPool.subList(0, 15);
        // 🚨 警示：subList 傳回的只是原始清單的一個「檢視（View）」。直接操作它會同步修改到原始的 bingoPool！
        // 💡 解決方案： 建立一個全新的 ArrayList 實例並將 subList 當作參數傳入，產生一個「可修改的複本（Modifiable
        // Copy）」，藉此保護原始資料

        // List<String> firstOnes = bingoPool.subList(0, 15);

        List<String> firstOnes = new ArrayList<>(bingoPool.subList(0, 15));

        firstOnes.sort(Comparator.naturalOrder());

        // 使用 replaceAll 對元素進行資料轉換（Transformation）
        firstOnes.replaceAll(s -> {

            if (s.indexOf('G') == 0 || s.indexOf("O") == 0) {

                String updated = s.charAt(0) + "-" + s.substring(1);

                System.out.print(updated + " ");

                return updated; // 傳回加工後的標籤，取代清單中的舊值
            }

            return s;
        });

        System.out.println("\n----------------------------------");

        // 驗證檢查：再次列印原始 bingoPool 的前 15 個元素
        // 因為上面使用了「可修改的複本（new ArrayList）」，所以此處確認原始資料未被污染
        for (int i = 0; i < 15; i++) {
            System.out.println(bingoPool.get(i));
        }

        System.out.println("------------------------------------");

        // 🚀 現代作法：使用 Java 8 Stream（串流）寫法
        // 呼叫 Collection 介面內建的 stream() 方法，啟動一條 Stream 管道（Stream Pipeline）
        // 每個鏈結在一起的步驟都稱為一項「操作（Operation）」

        // 所有的 pipeline 都是以一個 stream 作為開始
        // 所以在這個範例中，我們需要呼叫 `bingoPool` List 上的 `stream()` 方法來取得一個 stream
        var tempStream = bingoPool.stream()

                .limit(15)

                .filter(s -> s.indexOf('G') == 0 || s.indexOf("O") == 0)

                .map(s -> s.charAt(0) + "-" + s.substring(1))

                .sorted();

        // forEach 如果被省略 , 會無法觸發 , 因為導致這段會變成完全沒有 terminal operation

        // terminal operation
        // 🔴 會「觸發整條 stream pipeline 執行」
        // 🔴 執行完之後 Stream 就會被消耗（不能再重用）

        tempStream.forEach(s -> System.out.print(s + " "));

        System.out.println("\n----------------------------------");

        /*
         * Java Stream 來源（Sources）的建立方式，包含陣列、集合檢視、有限與無限串流（Infinite Streams）的動態生成
         * 並展示了多個串流串接（Concat）、型別轉換（Map）以及效能最佳化的延遲評估特性
         */

        // 陣列來源 Stream
        String[] strings = { "One", "Two", "Three" };

        var firstStream = Arrays.stream(strings)
                .sorted(Comparator.reverseOrder());

        var secondStream = Stream.of("Six", "Five", "Four")
                .map(String::toUpperCase);

        Stream.concat(secondStream, firstStream)

                .map(s -> s.charAt(0) + " - " + s)

                .forEach(System.out::println);

        // Map Stream
        Map<Character, int[]> myMap = new LinkedHashMap<>();

        int bingoIndex = 1;

        for (char c : "BINGO".toCharArray()) {

            int[] numbers = new int[15];

            int labelNo = bingoIndex; // effectively final

            Arrays.setAll(numbers, i -> i + labelNo);

            myMap.put(c, numbers);

            bingoIndex += 15;
        }

        myMap.entrySet()

                .stream()

                .map(e -> e.getKey() + " has range: " + e.getValue()[0] + " - " +
                        e.getValue()[e.getValue().length - 1])

                .forEach(System.out::println);

        // 無限串流範例 1
        Random random = new Random();

        Stream.generate(() -> random.nextInt(2))

                .limit(10)

                .forEach(s -> System.out.print(s + " "));

        System.out.println();

        // 無限串流範例 2
        IntStream.iterate(1, n -> n + 1)

                .filter(Main::isPrime)

                .limit(20)

                .forEach(s -> System.out.print(s + " "));

        System.out.println();

        // 無限串流範例 3
        IntStream.iterate(1, n -> n + 1)

                .limit(100)

                .filter(Main::isPrime)

                .forEach(s -> System.out.print(s + " "));

        System.out.println();

        // 有限串流（iterate 三參數）
        IntStream.iterate(1, n -> n <= 100, n -> n + 1)

                .filter(Main::isPrime)

                .forEach(s -> System.out.print(s + " "));

        System.out.println();

        // rangeClosed
        IntStream.rangeClosed(1, 100)

                .filter(Main::isPrime)

                .forEach(s -> System.out.print(s + " "));
    }

    public static boolean isPrime(int wholeNumber) {

        if (wholeNumber <= 2) {
            return wholeNumber == 2;
        }

        for (int divisor = 2; divisor < wholeNumber; divisor++) {
            if (wholeNumber % divisor == 0) {
                return false;
            }
        }

        return true;
    }
}