一、為什麼 JDK 8 要重新設計時間 API

在 JDK 8 之前，Java 使用的是：
```
java.util.Date
java.util.Calendar
```

問題很多：
- 可變（mutable）→ 容易被改壞
- API 混亂（Date + Calendar 混用）
- 月份從 0 開始（非常反直覺）
- 時區處理不清楚
- 多執行緒不安全

所以 JDK 8 引入： java.time（全新設計）

目標： 更清楚、更安全、更一致、更好用
二、java.time 整體架構

1. 結構

java.time 是一個「分層設計」：

（1）核心時間 API
LocalDate
LocalTime
LocalDateTime
Instant
Duration
Period

===>  日常 90% 都用這層

（2）temporal（底層能力）
java.time.temporal

用途：

===> 提供「時間操作規則與抽象能力」

例如：

plus / minus
with
get
range
（3）format（格式化）
java.time.format

===> 用途：日期轉字串 / 字串轉日期

例如：DateTimeFormatter

（4）zone（時區）

```
java.time.zone
```

用途： 處理世界各地時間（UTC +8 / UTC -5）

```
ZonedDateTime
ZoneId
```

（5）chrono（歷法）
java.time.chrono

用途：非 ISO 歷法（例如日本年號）


三、不可變 + 執行緒安全
背景問題（舊 API）

```java
Date date = new Date();
date.setTime(...); // 可變
```
===>  多執行緒會被改壞

Java 8 設計：

所有 time 類別：
- immutable（不可變）
- thread-safe（執行緒安全）

例如：

```java
LocalDate newDate = date.plusDays(1);
```

所有修改 = 回傳新物件

java.time = 永遠不修改原時間，只產生新時間

四、三大核心時間類別
1. LocalDate（只有日期）
背景用途：

ex:  生日、紀念日、到期日

2026-06-20
特點：
- 沒有時間
- 沒有時區

===> 只有「年/月/日」

2. LocalTime（只有時間）

用途： 鬧鐘、營業時間

14:30:00


3. LocalDateTime（日期 + 時間）

系統紀錄時間

2026-06-20 14:30

===> 沒有時區 , 有時間但不知道在哪個國家

三者共同特性： 都不包含時區（timezone-less）

五、Temporal 介面（底層能力）

Temporal 是 Java time API 裡「用來描述時間怎麼被操作的介面（規則）」

背景：Java 不想每個類別都重寫時間操作

所以抽象成：

```
Temporal
TemporalAccessor
```

提供能力：
1. 讀取資料 get()
2. 範圍查詢 range()
3. 時間運算
plus()
minus()
4. 修改欄位 with()

Temporal = 所有時間操作的底層規則

六、內部儲存結構
LocalDate：
```
int year
short month
short day
```

LocalTime：
```
byte hour
byte minute
byte second
int nano
```

為什麼這樣設計？
===>  提高效能 + 減少記憶體 + 避免 Date 的混亂結構
===>  時間不是物件，是拆成數字存

七、命名規則
1. 建立方法
now / of / parse
LocalDate.now()
LocalDate.of(2026, 6, 20)
LocalDate.parse("2026-06-20")

===> now = 現在 / of = 手動建立 / parse = 字串轉換

2. 組合方法（at）
```
date.atTime(time)
```
作用：把不同時間拼起來

at = 拼接時間

3. 取得值（get）
getYear()
getMonth()
getHour()
記憶：

===> get = 拿資料

4. 比較（is）
isAfter()
isBefore()
isEqual()
記憶：

===>  is = 判斷關係

八、整體設計邏輯

Java 8 的時間設計其實在解決三件事：

1. 安全性問題 => 不可變 + 執行緒安全

2. 清晰性問題
LocalDate / LocalTime / LocalDateTime 分離

3. 時區混亂問題
明確區分 Local vs Zoned