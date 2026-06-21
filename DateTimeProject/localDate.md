一、LocalDate 在整個 Java 時間系統的位置（背景）

在 Java 8 之前：

- Date / Calendar 混亂
- 月份從 0 開始
- 可變（mutable）
- 時區混雜

===>  非常容易出錯

Java 8 解法：
java.time = 全新時間系統

其中 LocalDate = 專門處理「只有日期」的類別

它的定位：LocalDate = 不含時間、不含時區的純日期模型

二、LocalDate 的建立方式
1. now（取得當前日期）
```java
LocalDate.now();
```
===>  來自系統時鐘（OS）

用途：
取得今天
log 記錄時間點

2. of（手動建立）
```java
LocalDate.of(2022, 5, 5);
LocalDate.of(2022, Month.MAY, 5);
```
背景： 用於「已知日期」

Month enum 的好處： 避免寫錯數字（5 vs MAY）

3. ofYearDay（年 + 第幾天）
LocalDate.ofYearDay(2022, 125);

背景： 某些系統用「年度進度」表示時間

例如：

第 125 天 = 5/5

用來處理「第幾天」而不是月日格式

4. parse（字串轉日期）
```java
LocalDate.parse("2022-05-05");
```
背景：API / DB / JSON 傳輸常用

特點：
- 必須符合 ISO 格式
- 預設 yyyy-MM-dd

三、欄位存取（getter 的設計目的）

1. 基本欄位（直接存值）
```java
getYear()
getMonth()
getMonthValue()
getDayOfMonth()
```
背景：直接從內部資料取出

2. 計算欄位（動態算出）
```java
getDayOfWeek()
getDayOfYear()
```
背景：這些不是直接存的，而是：Java 根據日期演算法算出來

有些欄位是「存的」，有些是「算的」

3. 統一存取方式（ChronoField）
```java
get(ChronoField.YEAR)
get(ChronoField.DAY_OF_YEAR)
```

Java 希望「所有時間類型都能用同一種方式取值」

ChronoField = 通用欄位存取 API

四、不可變性 + with
背景問題（舊 Java）
```java
date.setYear(2000); // 會改原物件
```
👉 很危險（共享狀態會被污染）

Java 8 設計： 全部改成 immutable

with 的設計：
```
may5.withYear(2000);
```
真正行為： 回傳「新物件」，不是修改原本


也可以用通用版本：
```java
with(ChronoField.DAY_OF_YEAR, 126);
```

五、時間加減（plus / minus 的設計）
背景：需要「自然語意」操作時間

方法：
plusYears()
plusMonths()
plusDays()
plusWeeks()
plusWeeks 
52 weeks ≠ 365 days（一定不完全相同）

原因：

閏年
月份長度不同
記憶：


通用版本：
plus(long, .DAYS)
分類：

類型	用途
ChronoUnit	時間「量」
ChronoField	時間「欄位」

六、ChronoField vs ChronoUnit

ChronoField（欄位） 

- 「時間的組成部分」
```
YEAR
MONTH_OF_YEAR
DAY_OF_WEEK
ChronoUnit（單位）
```

- 「時間的長度」
```
DAYS
MONTHS
YEARS
```

Field = 長什麼樣
Unit  = 加多少

七、比較機制（排序與判斷）
1. isAfter / isBefore
may5.isAfter(today)

👉 回傳 boolean

2. compareTo（排序）
may5.compareTo(today)

回傳：

< 0 → 較早
= 0 → 相同

0 → 較晚

3. equals（是否完全相同）
may5.equals(today)

4. isLeapYear（閏年）

isLeapYear()

👉 is = 判斷 / compareTo = 排序 / equals = 是否相同

八、整體設計核心（最重要理解）

LocalDate 其實在做三件事：

1. 提供建立方式  now / of / parse

2. 提供操作方式 with / plus / minus

3. 提供查詢方式  get / ChronoField