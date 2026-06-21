package LocaleProject;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        /* 將 JVM 預設的 Locale 強制設定為美國（Locale.US） */
        Locale.setDefault(Locale.US);
        /* 透過呼叫靜態方法 getDefault 取得並印出目前系統預設的 Locale */
        System.out.println("Default Locale = " + Locale.getDefault());

        Locale en = new Locale("en");
        Locale enAU = new Locale("en", "AU");
        Locale enCA = new Locale("en", "CA");

        /*
         * 使用 Locale.Builder 靜態巢狀類別透過鏈結呼叫 setLanguage、setRegion 與 build 方法來建立印度英文 Locale
         */
        Locale enIN = new Locale.Builder().setLanguage("en").setRegion("IN").build();
        /* 使用 Locale.Builder 建立紐西蘭英文（NZ）的 Locale 實例 */
        Locale enNZ = new Locale.Builder().setLanguage("en").setRegion("NZ").build();

        /* 建立一個使用中等長度（MEDIUM）格式樣式的在地化日期時間格式化器 */
        var dtf = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM);

        /* 透過增強型 for 迴圈遍歷多個不同國家與地區的英文 Locale 實例 */
        for (var locale : List.of(
                Locale.getDefault(), Locale.US, en, enAU, enCA,
                Locale.UK, enNZ, enIN)) {

            /*
             * 呼叫 getDisplayName 取得該 Locale 的顯示名稱，並透過 withLocale 產生帶有特定在地化設定的新格式化器實例來輸出目前時間
             */
            System.out.println(locale.getDisplayName() + "= "
                    + LocalDateTime.now().format(dtf.withLocale(locale)));
        }

        /*
         * 自訂日期模式符號說明
         * 根據 Java 官方文件，自訂 Pattern 時字母大小寫與數量至關重要：
         * - 4個大寫 E（EEEE）：代表完整星期名稱（如 Monday、星期二）
         * - 4個大寫 M（MMMM）：代表完整月份名稱（如 May、五月）
         * - 1個小寫 d（d）：代表數值日期，不強制補零
         * - 4個小寫 y（yyyy）或 u：代表四位數年份
         */
        DateTimeFormatter wdayMonth = DateTimeFormatter.ofPattern(
                "EEEE, MMMM d, yyyy");

        LocalDate May5 = LocalDate.of(2020, 5, 5);

        System.out.println("--------------------");

        for (var locale : List.of(Locale.CANADA, Locale.CANADA_FRENCH,
                Locale.FRANCE, Locale.GERMANY, Locale.TAIWAN,
                Locale.JAPAN, Locale.ITALY)) {

            /*
             * 觀念：
             * - locale.getDisplayName()：未傳參版本，會以 JVM 目前預設語系（此處為英文）印出該國家名稱
             * - locale.getDisplayName(locale)：傳入自身 Locale，會以「該國家的母語」印出自己（如 France 印出
             * français）
             */
            System.out.println(
                    locale.getDisplayName() + " : " +
                            locale.getDisplayName(locale) + "=\n\t" +
                            May5.format(wdayMonth.withLocale(locale)));

            /*
             * 展示 String.format 與 printf 同樣支援傳入 Locale 作為第一參數，能達到與 DateTimeFormatter
             * 相同的在地化輸出效果
             */
            System.out.printf(locale, "\t%1$tA, %1$tB %1$te, %1$tY %n", May5);

            /*
             * 💡 數字在地化差異（千分位與小數點）：
             * NumberFormat.getNumberInstance(locale) 揭示了世界各國對數字格式的巨大差異：
             * - 英文（US/CANADA）：小數點為句點（.），千分位為逗點（,）
             * - 法文（FRANCE）：千分位為「空格」，小數點為逗點（,）
             * - 德文（GERMANY）：千分位為句點（.），小數點為逗點（,）
             */
            NumberFormat decimalInfo = NumberFormat.getNumberInstance(locale);
            decimalInfo.setMaximumFractionDigits(6); /* NumberFormat 是可變（Mutable）類別，此處設定最大底數為 6 位 */
            System.out.println(decimalInfo.format(123456789.123456));

            /*
             * 💡 貨幣在地化與 Currency 類別：
             * - NumberFormat.getCurrencyInstance(locale)：自動附加該國貨幣符號（如
             * NT$、￥、€），並依該國習慣進位（例如日本日圓無小數點，會自動四捨五入到整數）
             * - Currency.getInstance(locale)：可取得國際標準貨幣代碼（Currency Code，如 TWD,
             * EUR）以及在地化的貨幣名稱。
             */
            NumberFormat currency = NumberFormat.getCurrencyInstance(locale);
            Currency localCurrency = Currency.getInstance(locale);

            System.out.println(currency.format(555.555) + " [" +
                    localCurrency.getCurrencyCode() + "] " +
                    localCurrency.getDisplayName(locale) + "/" +
                    localCurrency.getDisplayName());
        }

        /*
         * 主要展示 LocalDate 的新方法、LocalTime、LocalDateTime 以及字串解析與格式化
         */

        System.out.println("--------------------");

        /* 使用 JDK 9 的 datesUntil 方法產生特定日期區間的串流並印出 */
        May5.datesUntil(May5.plusDays(7)).forEach(System.out::println);

        System.out.println("--------------------");

        /*
         * 使用 datesUntil 的多載版本
         * 搭配 Period.ofDays 設定以 7 天為級距，產生為期一年的日期串流
         */
        May5.datesUntil(May5.plusYears(1), java.time.Period.ofDays(7))
                .forEach(System.out::println);

        /* 取得目前的 LocalTime 實例並印出 */
        java.time.LocalTime nowTime = java.time.LocalTime.now();
        System.out.println(nowTime);

        /* 使用 of 工廠方法傳入時、分來建立 LocalTime 實例 */
        java.time.LocalTime sevenAM = java.time.LocalTime.of(7, 0);
        System.out.println(sevenAM);

        /* 使用 of 工廠方法傳入時、分、秒來建立 LocalTime 實例 */
        java.time.LocalTime sevenThirty = java.time.LocalTime.of(7, 30, 15);
        System.out.println(sevenThirty);

        /* 使用 parse 方法將符合格式的字串解析為 LocalTime 實例 */
        java.time.LocalTime sevenPM = java.time.LocalTime.parse("19:00");
        java.time.LocalTime sevenThirtyPM = java.time.LocalTime.parse("19:30:15.000001000");
        System.out.println(sevenPM);
        System.out.println(sevenThirtyPM);

        /* 使用 ChronoField.AMPM_OF_DAY 取得時間屬於上午或下午（0 代表上午，1 代表下午） */
        System.out.println(sevenPM.get(ChronoField.AMPM_OF_DAY));
        System.out.println(sevenThirtyPM.get(ChronoField.AMPM_OF_DAY));

        /* 展示 getHour() 與使用 ChronoField.HOUR_OF_DAY 兩種獲取小時的方式 */
        System.out.println(sevenThirtyPM.getHour());
        System.out.println(sevenThirtyPM.get(ChronoField.HOUR_OF_DAY));

        /*
         * 注意：不能在 LocalTime 上呼叫不支援的欄位（如 ChronoField.YEAR）或單位（如 ChronoUnit.DAYS）
         * 以下為修改為正確小時單位的加法操作
         */
        System.out.println(sevenThirtyPM.plus(24, ChronoUnit.HOURS));

        /* 使用 range 方法查詢特定時態欄位（TemporalField）在該類別支援的有效數值範圍 */
        System.out.println(sevenPM.range(ChronoField.HOUR_OF_DAY));
        System.out.println(sevenPM.range(ChronoField.MINUTE_OF_HOUR));
        System.out.println(sevenPM.range(ChronoField.MINUTE_OF_DAY));
        System.out.println(sevenPM.range(ChronoField.SECOND_OF_MINUTE));
        System.out.println(sevenPM.range(ChronoField.SECOND_OF_DAY));

        /* 取得目前的 LocalDateTime 實例並印出 */
        java.time.LocalDateTime todayAndNow = java.time.LocalDateTime.now();
        System.out.println(todayAndNow);

        /* 使用 of 工廠方法組合年月日時分來建立 LocalDateTime 實例 */
        java.time.LocalDateTime May5Noon = java.time.LocalDateTime.of(2022, 5, 5, 12, 0);

        /* 使用 printf 搭配時態指定子格式化輸出日期與時間 */
        System.out.printf("%1$tD %1$tr %n", todayAndNow);
        System.out.printf("%1$tF %1$tT %n", todayAndNow);

        /* 使用 DateTimeFormatter 內建的預設格式（如 ISO_WEEK_DATE）進行格式化輸出 */
        System.out.println(todayAndNow.format(java.time.format.DateTimeFormatter.ISO_WEEK_DATE));

        /* 使用 ofLocalizedDate 與 FormatStyle.FULL 建立帶在地化樣式的格式化器 */
        java.time.format.DateTimeFormatter dtf2 = java.time.format.DateTimeFormatter
                .ofLocalizedDate(java.time.format.FormatStyle.FULL);

        System.out.println(todayAndNow.format(dtf2));

        /*
         * ⚠️ 講稿後半段核心技術：Scanner 與 Locale 的文化衝突地雷
         * 
         * 1. BigDecimal 建構子限制：
         * `new BigDecimal("1,000.50")` 會直接噴 Exception 崩潰，因為建構子只認純數字與小數點點號。
         * 
         * 2. Scanner 擁有 Locale 感知能力：
         * 但 `Scanner.nextBigDecimal()` 卻允許輸入帶有千分位與對應小數點的字串！它會依據 Scanner 內部的 Locale 來解析。
         * 
         * 3. 當切換為 Locale.ITALY 時：
         * 義大利的文化與美國完全相反：點號（.）是千分位，逗號（,）才是小數點！
         * - 輸入 "1,000.50" ➔ 在義大利 Locale 下會直接炸出 InputMismatchException 崩潰。
         * - 輸入 "1.000,50" ➔ 才能被義大利規矩正確識別為 一千點五。
         */

        System.out.println("--------------------");
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the loan amount (Italy format e.g., 1.000,50): ");

        scanner.useLocale(Locale.ITALY); /* 強制 Scanner 採用義大利文字辨識規矩 */

        if (scanner.hasNextBigDecimal()) {
            BigDecimal myLoan = scanner.nextBigDecimal();

            /* 輸出時，利用義大利當地的 NumberFormat 將成果印出，達到輸入與輸出格式完美一致 */
            NumberFormat italyCurrency = NumberFormat.getCurrencyInstance(Locale.ITALY);
            System.out.println("Parsed Loan Amount = " + italyCurrency.format(myLoan));
        }
    }
}