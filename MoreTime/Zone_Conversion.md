# 時區轉換、TemporalAdjusters 與 Temporal Amounts

商業應用場景：跨時區時間轉換、日光節約時間（DST）判斷、日期自動調整，以及計算兩個時間之間的差距。

這些需求幾乎存在於所有大型系統中，例如：

國際航班訂票系統
跨國電商平台
股票交易系統
雲端服務日誌（Log）
排程與預約系統

Java 的 java.time API 為了解決這些問題，提供了：

ZoneRules
TemporalAdjusters
Period
Duration
ChronoUnit

等工具，讓開發者不需要自己處理複雜的日期與時區邏輯

# Daylight Saving Time（DST）與 ZoneRules

為什麼需要 DST？

部分國家會實施： Daylight Saving Time , 夏令時間 , 日光節約時間

目的在於： 夏季提早一小時活動 , 增加日照利用率 , 降低能源消耗

例如：

紐約
冬季： UTC-05:00 EST
夏季：UTC-04:00 EDT

雪梨
冬季：UTC+10:00 AEST
夏季：UTC+11:00 AEDT

因此：

同一個城市
不同日期
可能有不同 UTC Offset

這也是為什麼：ZoneId 不能只用一個固定數字表示

# ZoneRules

每個 ZoneId 內部都持有：ZoneRules 物件

它記錄：

- UTC Offset
- DST 規則
- 歷史時區變更
- 未來已知變更

例如：

```java
ZoneId zone =
        ZoneId.of("Australia/Sydney");

ZoneRules rules =
        zone.getRules();
```

# getDaylightSavings()

查詢某個時間點因 DST 額外增加多少時間。

```java
Duration dst =
        zone.getRules()
                .getDaylightSavings(instant);
```

可能輸出：

- PT1H 代表： Period Time 1 Hour 額外增加 1 小時

常見結果:

- PT0S 代表：目前沒有夏令時間
- PT1H 代表： 目前多增加 1 小時

# isDaylightSavings()

判斷指定時間是否位於 DST 期間

```java
boolean active =
        zone.getRules()
                .isDaylightSavings(instant);
```

為什麼要傳入 Instant？

因為：DST 是否啟用 與日期有關

例如：

2026-01-01 與 2026-07-01 在同一城市可能不同

因此 Java 必須知道： 是哪一個時間點 才能判斷

Instant 的解析與轉換
Instant 的本質

可以把 Instant 理解成 時間軸上的座標

```java
Instant.now();
```

可能得到：

2026-06-21T08:30:00Z

其中： Z 代表 UTC+00:00

# Instant.parse()

解析 UTC ISO 格式字串

```java
Instant instant =
        Instant.parse(
                "2020-01-01T08:01:00Z");
```

結果：

```java
2020-01-01 08:01 UTC
```

注意 只接受標準格式：

```
YYYY-MM-DDTHH:mm:ssZ
```

例如：

```java
Instant.parse(
    "2020/01/01");
```

會直接失敗

# Instant → LocalDateTime

為什麼需要轉換？ 因為：Instant 只有 絕對時間點 , 沒有 地區概念

人類通常需要看到：當地時間

所以需要結合：ZoneId

```java
LocalDateTime taipeiTime =
        LocalDateTime.ofInstant(
                instant,
                ZoneId.of("Asia/Taipei"));
```

結果假設：

Instant = 2026-06-21T00:00Z

則： Asia/Taipei

會變成： 2026-06-21T08:00
Instant → ZonedDateTime

若需要保留時區資訊：

```java
ZonedDateTime zdt =
        ZonedDateTime.ofInstant(
                instant,
                ZoneId.of("Asia/Taipei"));
```

結果：

2026-06-21T08:00+08:00[Asia/Taipei]

# LocalDateTime 與 ZonedDateTime 差異

- LocalDateTime
  2026-06-21T08:00 // 不知道在哪裡

- ZonedDateTime
  2026-06-21T08:00+08:00[Asia/Taipei]

完整知道：

日期
時間
UTC Offset
ZoneId

# withZoneSameInstant()

最常用的時區轉換方法

假設：

```java
ZonedDateTime sydney =
        ZonedDateTime.ofInstant(
                instant,
                ZoneId.of("Australia/Sydney"));
```

轉換：

```java
ZonedDateTime la =
        sydney.withZoneSameInstant(
                ZoneId.of("America/Los_Angeles"));
```

**名稱中的： Same Instant 非常重要**

代表： 保持同一個時間點(同一個 Instant)

只改變： 顯示方式

# TemporalAdjusters

解決什麼問題？

很多日期調整需求都很常見：

下個月第一天
本月最後一天
下一個星期一
今年第一天

如果自己寫：

```
plusDays(...)
minusDays(...)
```

容易出錯。

因此 Java 提供：

```
TemporalAdjusters
```

# firstDayOfNextMonth()

取得：下個月第一天

```java
LocalDate result =
        today.with(
                TemporalAdjusters
                        .firstDayOfNextMonth());
```

可以把：TemporalAdjuster 理解成 日期調整策略

而：

```java
with(...)
```

則是：套用策略

例如：

```java
date.with(
    TemporalAdjusters.firstDayOfNextMonth()
);
```

等於：把「下個月第一天」規則 , 套用到目前日期

# IntelliJ Structure 視窗

開啟：

View
└─ Tool Windows
└─ Structure

快捷鍵： Alt + 7

可以快速查看：

方法
欄位
靜態工廠方法
巢狀類別

例如：

TemporalAdjusters

裡面有大量：

firstDayOfMonth()
lastDayOfMonth()
firstDayOfYear()
next()
previous()

利用 Structure 可以快速瀏覽

# Temporal Amounts

為什麼有 Period 和 Duration？

Java 將： 日期差距 與 時間差距 分開設計

因為：

一個月不一定 30 天
一年不一定 365 天

# Period

計算： 年 , 月, 日 差距

```java
Period p =
        Period.between(
                LocalDate.of(2020,1,1),
                LocalDate.of(2025,6,21));
```

結果： P5Y5M20D

代表：

5 年
5 個月
20 天

# Duration

計算： 時 , 分 , 秒 , 奈秒 差距

```java
Duration d =
        Duration.between(
                Instant.EPOCH,
                Instant.now());
```

結果：

```
PT494563H...
```

格式：PT 代表 Period Time

# ChronoUnit 的支援度

## LocalDate

只有日期

支援：

```
DAYS
WEEKS
MONTHS
YEARS
```

不支援：

```
SECONDS
MINUTES
HOURS
```

例如：

```java
LocalDate.now()
        .plus(1, ChronoUnit.HOURS);
```

會拋出：

UnsupportedTemporalTypeException

## LocalDateTime

同時擁有： 日期 , 時間

因此支援：

NANOS
MICROS
MILLIS
SECONDS
MINUTES
HOURS
DAYS
WEEKS
MONTHS
YEARS
DECADES
CENTURIES

幾乎所有 ChronoUnit。

# 總結

Instant = 時間軸上的絕對時間點

ZoneId = 某地區的時區規則

ZonedDateTime = 時間點 + 時區

withZoneSameInstant = 保持時間點不變 , 改變時區表示方式(負責在不同地區之間轉換同一個時間點)

TemporalAdjusters = 日期調整工具箱

Period = 算年月日

Duration = 算時分秒

ChronoUnit = 時間單位列舉 , 是否支援取決於操作的 Temporal 類型
