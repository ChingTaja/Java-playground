package DateTimeLocalizationProject2;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.time.zone.ZoneRules;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.time.format.DateTimeFormatter.*;

public class Main {

    /* 定義一個 Employee record，用來封裝員工的姓名、Locale 與時區資訊 */
    private record Employee(String name, Locale locale, ZoneId zone) {

        /* 自訂建構子，允許傳入字串形式的 Locale 標籤與時區識別碼 */
        public Employee(String name, String locale, String zone) {
            this(name, Locale.forLanguageTag(locale), ZoneId.of(zone));
        }

        /* 自訂建構子，允許傳入 Locale 物件與字串形式的時區識別碼 */
        public Employee(String name, Locale locale, String zone) {
            this(name, locale, ZoneId.of(zone));
        }

        /* 根據傳入的 ZonedDateTime 與格式化器，輸出符合該員工 Locale 的在地化日期時間資訊 */
        String getDateInfo(ZonedDateTime zdt, DateTimeFormatter dtf) {
            return "%s [%s] : %s".formatted(
                    name,
                    zone,
                    zdt.format(dtf.localizedBy(locale)));
        }
    }

    public static void main(String[] args) {

        /* 建立美國東岸員工 Jane 與澳洲雪梨員工 Joe 的資料 */
        Employee jane = new Employee("Jane", Locale.US, "America/New_York");
        Employee joe = new Employee("Joe", "en-AU", "Australia/Sydney");

        /* 獲取兩位員工所在時區的 ZoneRules（時區規則） */
        ZoneRules joesRules = joe.zone.getRules();
        ZoneRules janesRules = jane.zone.getRules();

        System.out.println(jane + " " + janesRules);
        System.out.println(joe + " " + joesRules);

        /* 取得美國東岸目前的 ZonedDateTime，並以此為基準建立同一個 LocalDateTime 於雪梨時區的物件 */
        ZonedDateTime janeNow = ZonedDateTime.now(jane.zone);
        ZonedDateTime joeNow = ZonedDateTime.of(
                janeNow.toLocalDateTime(),
                joe.zone);

        /* 計算兩者之間的時間差 */
        long hoursBetween = Duration.between(joeNow, janeNow).toHours();
        long minutesBetween = Duration.between(joeNow, janeNow).toMinutesPart();

        System.out.println(
                "Joe is " + Math.abs(hoursBetween) + " hours " +
                        Math.abs(minutesBetween) + " minutes " +
                        ((hoursBetween < 0) ? "behind" : "ahead"));

        /* 檢查 Joe 目前是否處於日光節約時間（Daylight Saving Time），並印出其時區的完整與縮寫名稱 */
        System.out.println(
                "Joe in daylight savings? " +
                        joesRules.isDaylightSavings(joeNow.toInstant()) + " " +
                        joesRules.getDaylightSavings(joeNow.toInstant()) + ": " +
                        joeNow.format(ofPattern("zzzz z")));

        /* 檢查 Jane 目前是否處於日光節約時間，並印出其時區的完整與縮寫名稱 */
        System.out.println(
                "Jane in daylight savings? " +
                        janesRules.isDaylightSavings(janeNow.toInstant()) + " " +
                        janesRules.getDaylightSavings(janeNow.toInstant()) + ": " +
                        janeNow.format(ofPattern("zzzz z")));

        int days = 10;

        /* 呼叫排程方法搜尋未來 10 天內兩位員工皆符合工作時間的重疊時段 */
        var map = schedule(joe, jane, days);

        /* 建立日期為完整樣式（FULL）、時間為縮寫樣式（SHORT）的時態格式化器 */
        DateTimeFormatter dtf = ofLocalizedDateTime(
                FormatStyle.FULL,
                FormatStyle.SHORT);

        /* 走訪 Map 中所有的日期 Key（此處以第二個員工 Jane 的本地日期為準分類） */
        for (LocalDate ldt : map.keySet()) {

            System.out.println(
                    ldt.format(ofLocalizedDate(FormatStyle.FULL)));

            /* 走訪該日期下所有符合條件的時段（ZonedDateTime 串列） */
            for (ZonedDateTime zdt : map.get(ldt)) {

                /* 印出 Jane 的時間，並將該時段透過 withZoneSameInstant 轉換為 Joe 當地的時間後印出 */
                System.out.println(
                        "\t" +
                                jane.getDateInfo(zdt, dtf) +
                                " <---> " +
                                joe.getDateInfo(
                                        zdt.withZoneSameInstant(joe.zone()),
                                        dtf));
            }
        }
    }

    /*
     * 核心排程方法：尋找未來指定天數內，兩位員工共同符合工作日且工作時間（07:00-21:00，最晚 20:00 開始）的時段
     */
    private static Map<LocalDate, List<ZonedDateTime>> schedule(
            Employee first,
            Employee second,
            int days) {

        /* 定義篩選規則的 Predicate：必須為工作日（週一至週五），且小時必須在 7 點到 21 點之間（不含 21 點） */
        Predicate<ZonedDateTime> rules = zdt -> zdt.getDayOfWeek() != DayOfWeek.SATURDAY
                && zdt.getDayOfWeek() != DayOfWeek.SUNDAY
                && zdt.getHour() >= 7
                && zdt.getHour() < 21;

        /* 設定起始日期為兩天後，以避免時區領先者（如雪梨）因時間差跨日而排到目前當天 */
        LocalDate startingDate = LocalDate.now().plusDays(2);

        /* 透過 Stream 處理日期與每小時時段 */
        return startingDate.datesUntil(startingDate.plusDays(days + 1))

                /* 將 LocalDate 轉為第一個員工時區的當天起點時間（00:00） */
                .map(dt -> dt.atStartOfDay(first.zone()))

                /* 將每天攤平成 24 個小時的 ZonedDateTime 串流 */
                .flatMap(dt -> IntStream.range(0, 24)
                        .mapToObj(dt::withHour))

                /* 篩選出符合第一個員工工作時間的時段 */
                .filter(rules)

                /* 將該時段轉換成第二個員工在相同時間點（Instant）的當地日期時間 */
                .map(dtz -> dtz.withZoneSameInstant(second.zone()))

                /* 篩選出同時也符合第二個員工工作時間的時段 */
                .filter(rules)

                /* 依據第二個員工當地的 LocalDate 進行分組，並使用 TreeMap 確保日期自然排序，最後收集成 List */
                .collect(
                        Collectors.groupingBy(
                                ZonedDateTime::toLocalDate,
                                TreeMap::new,
                                Collectors.toList()));
    }
}
