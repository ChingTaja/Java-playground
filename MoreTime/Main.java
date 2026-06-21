package MoreTime;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.time.temporal.TemporalAdjusters;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TimeZone;

public class Main {

    public static void main(String[] args) {

        /*
         * 設定 JVM 預設時區。
         * 必須在任何日期時間 API 被使用前設定，
         * 否則部分時區資訊可能已被 JVM 快取。
         */
        System.setProperty("user.timezone", "America/Los_Angeles");

        /* 取得目前 JVM 的預設時區 */
        System.out.println(ZoneId.systemDefault());

        /* 取得 Java 內建所有可用時區數量 */
        System.out.println("Number of TZs = " + ZoneId.getAvailableZoneIds().size());

        /*
         * 取得所有以 US 開頭的時區 ID，
         * 排序後轉成 ZoneId 物件並印出其規則。
         */
        ZoneId.getAvailableZoneIds().stream()
                .filter(s -> s.startsWith("US"))
                .sorted()
                .map(ZoneId::of)
                .forEach(z -> System.out.println(
                        z.getId() + ": " + z.getRules()));

        /* JDK 8 ZoneId 支援的時區集合 */
        Set<String> jdk8Zones = ZoneId.getAvailableZoneIds();

        /* 舊版 TimeZone API 支援的時區 ID */
        String[] alternate = TimeZone.getAvailableIDs();

        /* 轉成 Set 方便做集合運算 */
        Set<String> oldway = new HashSet<>(Set.of(alternate));

        /*
         * 移除新版 API 已支援的時區，
         * 找出只存在於舊版 API 的代碼。
         */
        oldway.removeAll(jdk8Zones);
        System.out.println(oldway);

        /* 使用 SHORT_IDS 對照表處理舊式縮寫時區代碼 */
        ZoneId bet = ZoneId.of("BET", ZoneId.SHORT_IDS);
        System.out.println(bet);

        /* 取得目前系統時區下的本地日期時間 */
        LocalDateTime now = LocalDateTime.now();
        System.out.println(now);

        /* 取得當前 UTC 時間點（時間軸上的絕對瞬間） */
        Instant instantNow = Instant.now();
        System.out.println(instantNow);

        /* 檢視不同時區對同一個 Instant 的呈現方式 */
        for (ZoneId z : List.of(
                ZoneId.of("Australia/Sydney"),
                ZoneId.of("Europe/Paris"),
                ZoneId.of("America/New_York"))) {

            /*
             * z = 時區縮寫
             * zzzz = 完整時區名稱
             */
            DateTimeFormatter zoneFormat = DateTimeFormatter.ofPattern("z:zzzz");

            System.out.println(z);

            /* 將同一個 Instant 轉換到指定時區顯示 */
            System.out.println(
                    "\t" + instantNow.atZone(z).format(zoneFormat));

            /* 查詢目前 DST（日光節約時間）調整量 */
            System.out.println(
                    "\t" + z.getRules().getDaylightSavings(instantNow));

            /* 是否正處於 DST 期間 */
            System.out.println(
                    "\t" + z.getRules().isDaylightSavings(instantNow));
        }

        /* 將 UTC 格式字串解析成 Instant */
        Instant dobInstant = Instant.parse("2020-01-01T08:01:00Z");

        /* 將同一個 Instant 轉換成洛杉磯時區的 LocalDateTime */
        LocalDateTime dob = LocalDateTime.ofInstant(
                dobInstant,
                ZoneId.systemDefault());

        System.out.println(
                "Your kid's birthdate, LA time = "
                        + dob.format(
                                DateTimeFormatter.ofLocalizedDateTime(
                                        FormatStyle.MEDIUM)));

        /* 將同一個 Instant 轉換成雪梨時區 */
        ZonedDateTime dobSydney = ZonedDateTime.ofInstant(
                dobInstant,
                ZoneId.of("Australia/Sydney"));

        System.out.println(
                "Your kid's birthdate, Sydney Time = "
                        + dobSydney.format(
                                DateTimeFormatter.ofLocalizedDateTime(
                                        FormatStyle.MEDIUM)));

        /* 保持相同時間點，只轉換顯示時區 */
        ZonedDateTime dobHere = dobSydney.withZoneSameInstant(
                ZoneId.systemDefault());

        System.out.println(
                "Your kid's birthdate, Here Time = "
                        + dobHere.format(
                                DateTimeFormatter.ofLocalizedDateTime(
                                        FormatStyle.MEDIUM)));

        /* 取得下個月第一天 */
        ZonedDateTime firstOfMonth = ZonedDateTime.now()
                .with(
                        TemporalAdjusters.firstDayOfNextMonth());

        System.out.printf(
                "First of next Month = %tD %n",
                firstOfMonth);

        /* 計算從 1970-01-01 到出生日期的年月日差距 */
        Period timePast = Period.between(
                LocalDate.EPOCH,
                dob.toLocalDate());

        System.out.println(timePast);

        /* 計算從 Unix Epoch 到出生時間的秒數差距 */
        Duration timeSince = Duration.between(
                Instant.EPOCH,
                dob.toInstant(ZoneOffset.UTC));

        System.out.println(timeSince);

        /* 模擬第二個小孩出生日期 */
        LocalDateTime dob2 = dob.plusYears(2)
                .plusMonths(4)
                .plusDays(4)
                .plusHours(7)
                .plusMinutes(14)
                .plusSeconds(37);

        System.out.println(
                "Your 2nd kid's birthdate, Here Time = "
                        + dob2.format(
                                DateTimeFormatter.ofLocalizedDateTime(
                                        FormatStyle.MEDIUM)));

        /* 第二個日期與 Epoch 的年月日差距 */
        Period timePast2 = Period.between(
                LocalDate.EPOCH,
                dob2.toLocalDate());

        System.out.println(timePast2);

        /* 第二個日期與 Epoch 的時間差距 */
        Duration timeSince2 = Duration.between(
                Instant.EPOCH,
                dob2.toInstant(ZoneOffset.UTC));

        System.out.println(timeSince2);
    }
}