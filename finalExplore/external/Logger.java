// 模擬一個來自外部函數庫（External Library）的類別
package finalExplore.external;

import java.time.LocalDateTime;

public class Logger {

    public static void logToConsole(CharSequence message) {

        LocalDateTime dt = LocalDateTime.now();

        System.out.printf(
                "%1$tD %1$tT : %2$s%n",
                dt,
                message);

        // =====================================================
        // Pattern Matching for instanceof (Java 16+)
        // =====================================================
        //
        // 如果 message 實際上是 StringBuilder：
        //
        // message instanceof StringBuilder sb
        //
        // 則自動完成：
        //
        // StringBuilder sb = (StringBuilder) message;
        //
        // 並進入 if 區塊
        //
        // 否則直接跳過。
        if (message instanceof StringBuilder sb) {

            // 清空內容
            //
            // setLength(0)
            //
            // 不會建立新的 StringBuilder，
            // 而是直接修改原本物件的內部狀態。
            //
            // 因此：
            //
            // StringBuilder tracker =
            // new StringBuilder("abc");
            //
            // Logger.logToConsole(tracker);
            //
            // 執行完後：
            //
            // tracker -> ""
            //
            // 這是一個典型的 Side Effect（副作用）。
            sb.setLength(0);
        }
    }
}