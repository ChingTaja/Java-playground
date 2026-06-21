# Java 時區（ZoneId）與時間點（Instant）基礎觀念

在學習 java.time API 時
會先接觸 LocalDate、LocalTime 與 LocalDateTime
但當系統開始需要處理跨國用戶、伺服器部署於不同國家、或儲存具有全球一致性的時間資料時
就必須理解 時區（Time Zone） 與 時間點（Instant） 的概念

Java 8 的 java.time API 將時區、偏移量、本地時間與絕對時間點分開設計
讓開發者能更清楚地區分「某地看到的時間」與「時間軸上的實際時間點」
避免舊版 API 容易發生的時區混亂問題

在現實世界中，地球被劃分成許多時區（Time Zone），每個地區相對於國際標準時間（UTC）都有不同的偏移量（Offset）

| 城市 | 時區               | UTC Offset      |
| -- | ---------------- | --------------- |
| 台北 | Asia/Taipei      | +08:00          |
| 東京 | Asia/Tokyo       | +09:00          |
| 紐約 | America/New_York | -05:00 或 -04:00 |
| 雪梨 | Australia/Sydney | +10:00 或 +11:00 |


## 第一部分：UTC Offset

表示與 UTC 相差多少時間

UTC+08:00 // 台灣 08:00
UTC-05:00
UTC+09:00


## 第二部分：區域規則（Zone Rules）

除了固定偏移量之外，許多國家還有：

- 夏令時間（DST）
- 日光節約時間
- 歷史時區變更

例如：America/New_York

並不永遠是：UTC-05:00

夏季可能變成：UTC-04:00

因此：ZoneId 不只是偏移量
而是： 時區名稱 + 偏移量規則 + DST 規則 + 歷史變更規則 的完整封裝

# ZoneId 的命名規則

Java 使用 IANA Time Zone Database（TZDB）中的標準命名方式

格式通常為：
```
區域/城市
```
例如：
```
Asia/Taipei
Asia/Tokyo
Europe/Paris
America/New_York
America/Los_Angeles
Australia/Sydney
```

若城市名稱包含空格： Los Angeles

會寫成：America/Los_Angeles
使用底線取代空格


## ZoneId.systemDefault()

用途 : 取得 JVM 目前採用的預設時區
```java
System.out.println(
        ZoneId.systemDefault());
```
可能輸出：Asia/Taipei

或：America/Los_Angeles

取決於：

作業系統設定
JVM 設定
user.timezone 屬性

為什麼重要？

許多方法都會依賴系統時區：
```java
LocalDate.now()
LocalTime.now()
LocalDateTime.now()
```
例如：
```java
LocalDateTime.now();
```
其實等價於：
```java
LocalDateTime.now(
        ZoneId.systemDefault());
```
因此不同時區執行同一段程式：LocalDateTime.now()

可能得到不同結果

## ZoneId.getAvailableZoneIds()

用途

取得 Java 支援的所有時區
```java
Set<String> zones =
        ZoneId.getAvailableZoneIds();
```
例如：

System.out.println(zones.size());

常見用途

搜尋某地區時區：
```java
ZoneId.getAvailableZoneIds()
      .stream()
      .filter(id -> id.startsWith("US"));
```
輸出：

US/Alaska
US/Eastern
US/Hawaii

## ZoneId.of(String)
用途: 透過字串建立 ZoneId

```java
ZoneId taipei =
        ZoneId.of("Asia/Taipei");
ZoneId tokyo =
        ZoneId.of("Asia/Tokyo");
ZoneId ny =
        ZoneId.of("America/New_York");
```

若輸入不存在的時區
```java
ZoneId.of("ABC");
```
會拋出： java.time.zone.ZoneRulesException

# 舊版 TimeZone 與新版 ZoneId

JDK 8 以前主要使用： java.util.TimeZone

例如： TimeZone.getDefault();

## 舊版問題

早期 Java 支援許多縮寫： BET ,EST ,PST ,MST

例如：

TimeZone.getTimeZone("BET");

可以正常執行

新版限制

Java 8 的：

ZoneId.of("BET");

會直接失敗： Unknown time-zone ID

因為：BET 不是標準 IANA 時區名稱

## ZoneId.SHORT_IDS

為了相容舊系統：
```java
ZoneId.of(
        "BET",
        ZoneId.SHORT_IDS);
```
Java 會查詢映射表：
```
BET
↓
America/Sao_Paulo
```
成功建立：
```java
ZoneId bet =
        ZoneId.of(
                "BET",
                ZoneId.SHORT_IDS);
```
這是 Java 提供給 Legacy Code 的過渡方案

新系統仍建議直接使用：
```
America/Sao_Paulo
```
而非： BET


# 動態修改 JVM 預設時區

可以透過：
```java
System.setProperty(
        "user.timezone",
        "America/Los_Angeles");
```
修改 JVM 時區。

必須在程式一開始執行

正確：
```java
public static void main(String[] args) {

    System.setProperty(
            "user.timezone",
            "America/Los_Angeles");

    LocalDateTime.now();
}
```
錯誤：
```java
LocalDateTime.now();

System.setProperty(
        "user.timezone",
        "America/Los_Angeles");
```
原因是：時區資訊載入後會被 JVM 快取

後面再改：user.timezone

通常不會影響已初始化的時間 API

# LocalDateTime 與 Instant 的根本差異

## LocalDateTime 代表：某地區看到的日期與時間

例如：
```java
LocalDateTime.now(); // 2026-06-21T10:30:15
```

它只知道：

2026年
6月
21日
10點30分15秒

但不知道：在哪個國家？哪個時區？

因此：LocalDateTime 本身不具備全球唯一性

## Instant 代表：時間軸上的絕對時間點

例如：
```java
Instant.now(); // 2026-06-21T02:30:15.123Z

```

尾端：Z 代表 UTC+00:00

也就是：
Zulu Time
UTC Time
同一個 Instant

假設：

2026-06-21T02:30Z

轉換到不同時區：

台北：

2026-06-21 10:30

東京：

2026-06-21 11:30

紐約：

2026-06-20 22:30

雖然顯示不同，

但本質上：

都是同一個 Instant

# 總結
把 Java 時間 API 想成兩個世界：

1. 第一個世界：當地時間
LocalDate
LocalTime
LocalDateTime

關心的是：人類看到什麼時間

例如：

今天幾號？
現在幾點？

2. 第二個世界：全球時間
Instant
ZoneId
ZonedDateTime

關心的是：

時間軸上的真實時間點

例如：

資料何時建立？
事件何時發生？


- LocalDateTime 是某地看到的時間
- Instant 是全世界共同的時間點
- ZoneId 則負責把同一個 Instant 轉換成各地區所看到的當地時間