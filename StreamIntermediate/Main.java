package StreamIntermediate;

import java.util.stream.IntStream;
import java.util.Arrays;

public class Main {

    public static void main(String[] args) {

        // =====================================
        // 實驗一：數值統計終端操作 (summaryStatistics)
        // =====================================
        // 透過區域變數型別推斷 (var)，IntelliJ 會自動推斷 result 為 IntSummaryStatistics 型別
        // 當需要快速了解一組數據的資訊（總數、總和、最小、最大、平均）時，這是一個不錯的起點
        var result = IntStream
                // 從 0 開始，每次遞增 3，直到大於 1000 為止
                .iterate(0, i -> i <= 1000, i -> i = i + 3)
                // 終端操作：一口氣計算出 count, sum, min, average, max 並封裝回傳
                .summaryStatistics();
        System.out.println("Result = " + result);

        // =================================
        // 實驗二：利用 peek 觀察 Stream 的內部運作
        // =================================
        // 在執行資料 Reductions 時，peek 是個很有用的工具
        // 它就像一扇窗戶，讓你能在不破壞 Stream 管道的前提下，看清楚哪些元素成功通過了 filter
        var leapYearData = IntStream
                // 產生 2000 到 2025 的年份資料
                .iterate(2000, i -> i <= 2025, i -> i = i + 1)
                // 中間操作：篩選出能被 4 整除的年份（簡化版的閏年邏輯）
                .filter(i -> i % 4 == 0)
                // 中間操作：偷偷瞄一眼通過篩選的年份並印出，不影響後續的終端操作
                .peek(System.out::println)
                // 終端操作：收集這些閏年的統計數據
                .summaryStatistics();
        System.out.println("Leap Year Data = " + leapYearData);

        // ===============================
        // 實驗三：建立資料源
        // ===============================
        Seat[] seats = new Seat[100];
        // 根據索引 i 動態產生 100 個座位（排數 A~J，每排 1~10 號）
        Arrays.setAll(seats, i -> new Seat((char) ('A' + i / 10), i % 10 + 1));
        // Arrays.asList(seats).forEach(System.out::println);

        // ==================================
        // 實驗四：條件計算 (count)
        // ====================================
        // count() 會回傳一個 long 類型的數值，代表符合篩選條件的元素總數
        long reservationCount = Arrays
                .stream(seats)
                // 篩選出已被預約的座位
                .filter(Seat::isReserved)
                // 終端操作：統計個數
                .count();
        System.out.println("reservationCount = " + reservationCount);

        // =============================
        // 實驗五：條件匹配終端操作 (Matching Operations)
        // 這三個方法都接受一個 Predicate 作為參數，並回傳 boolean
        // 它們不需要事先呼叫 filter()，因為條件判斷邏輯已經內建在方法中了
        // ==============================

        // 1. anyMatch：只要「至少有一個」元素符合條件，就回傳 true
        boolean hasBookings = Arrays
                .stream(seats)
                .anyMatch(Seat::isReserved);
        System.out.println("hasBookings = " + hasBookings);

        // 2. allMatch：必須「每一個」元素都符合條件，才回傳 true
        boolean fullyBooked = Arrays
                .stream(seats)
                .allMatch(Seat::isReserved);
        System.out.println("fullyBooked = " + fullyBooked);

        // 3. noneMatch：必須「沒有任何」元素符合條件，才回傳 true
        boolean eventWashedOut = Arrays
                .stream(seats)
                .noneMatch(Seat::isReserved);
        System.out.println("eventWashedOut = " + eventWashedOut);
    }
}