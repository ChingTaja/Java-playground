# 跨時區員工排程與國際化時間系統設計

一、場景是：你有兩位員工（例如 Joe 在紐約、Jane 在雪梨）

系統需要：

顯示各自「本地語言 + 本地時間格式」
判斷他們是否在工作時間
計算兩地時間差
避免 DST（夏令時間）造成錯誤
找出「兩個人都可以開會」的時間區間
最後整理成一個「按日期排序的會議候選清單」

二、核心資料模型設計：Employee Record（巢狀封裝）

在 Main 類別中使用 private record Employee 封裝員工資訊：
```java
private record Employee(
    String name,
    Locale locale,
    ZoneId zone
)
```
為什麼用 Record

這裡的 Employee 本質上是：

1. 不變資料（immutable）
2. 純資料容器（data carrier）
3. 不需要複雜行為


## 提升可用性：多載建構子（String → 物件轉換）

為了讓呼叫端更方便，不需要自己建立 Locale / ZoneId：
```java
new Employee("Joe", "en-AU", "Australia/Sydney");
```
內部轉換：
```java
this(
    name,
    Locale.forLanguageTag(localeTag),
    ZoneId.of(zoneId)
);
```

這一步的重點是：

把「易錯的 API 使用」封裝起來，讓外部只輸入字串

三、國際化輸出：依員工語系顯示日期
核心方法：getDateInfo
```java
public String getDateInfo(
    ZonedDateTime dateTime,
    DateTimeFormatter dtf
)
```
關鍵設計
```java
dtf.localizedBy(locale)
```
這行代表：同一個 Formatter，可以依不同員工 Locale 動態變化輸出格式

同一時間可能輸出：

美國：Jun 21, 2026
英國：21 Jun 2026
日本：2026年6月21日

這裡不是單純「格式化日期」，而是：同一份資料，用不同文化視角呈現

四、時區系統與 UTC 偏移分析
ZoneRules 的角色
```java
zone.getRules()
```
用途是取得：

- UTC offset
- DST 規則
- 時區變動邏輯

## Offset 觀察
```java
rules.getOffset(Instant.now())
```
可能輸出：

-04:00
+10:00
+09:30
設計重點

這讓你可以做到：不只是知道「在哪個時區」，還知道「現在偏移多少」

五、時間差計算（Duration 精準模型）
基本概念
```java
Duration.between(joeNow, janeNow)
```

夏令時間
半小時時區（+9:30）
四分之一小時時區
跨日問題
Duration 優點

可以直接得到：
```java
diff.toHours()
diff.toMinutesPart()
```
例如：

14 小時 30 分
核心概念

Duration 比較的是「同一個 Instant」，不是表面時間

六、夏令時間（DST）與時區名稱
## 判斷 DST
```java
rules.isDaylightSavings(Instant)
```
結果：

true：夏令時間
false：標準時間

## 格式化時區名稱
```java
DateTimeFormatter.ofPattern("zzzz z")
```
輸出：Eastern Daylight Time EDT
設計價值

同時提供：
- 人類可讀名稱（zzzz）
- 縮寫（z）


七、Static Wildcard Import（語法簡化）
```java
import static java.time.format.DateTimeFormatter.*;
```
效果

原本：
```java
DateTimeFormatter.ofPattern(...)
DateTimeFormatter.ofLocalizedDate(...)
```
變成：
```java
ofPattern(...)
ofLocalizedDate(...)
```
本質: 提高 readable 

八、演算法：跨時區會議排程（Stream Pipeline）
1. 防跨日問題
```java
LocalDate.now().plusDays(2)
```
原因：

避免「今天已過」
避免「時區跨日錯誤」
避免排到過去時間
2. 建立日期區間
```java
datesUntil(endDate)
```
生成：

Day1 → DayN
3. 每日轉換為時間起點
```java
atStartOfDay(zone)
```
變成：

00:00（該員工時區）
4. 展開 24 小時
```java
IntStream.range(0, 24)
```
搭配：withHour(hour)

形成：一天所有可能會議時間點

5. 時區轉換（核心
withZoneSameInstant(second.zone())

時間不變，改變觀測角度


6. TreeMap 排序分組
```java
Collectors.groupingBy(
    ZonedDateTime::toLocalDate,
    TreeMap::new,
    Collectors.toList()
)
```
TreeMap 的作用確保：日期 → 自然排序

而不是亂序 HashMap
