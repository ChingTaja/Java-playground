一、datesUntil（JDK 9+）— 日期序列產生器

以前如果要做：

排程（每天/每週）
日期區間報表
UI 日期列表

都要自己寫 loop

Java 9 解法：
  自 JDK 9 起，LocalDate 提供了 datesUntil 方法來產生日期串流；其結果包含起始日、不含結束日

基本行為： 包含起始日，不包含結束日
```java
may5.datesUntil(may12)
```
👉 產生：

5
6
7
8
9
10
11

加強版本
```java
datesUntil(end, Period.ofDays(7))
```
控制「步進間隔」

例如：

每 7 天
每 1 個月
每 2 年


二、LocalTime（時間專用）
背景設計
LocalTime 只處理： 時 / 分 / 秒 / 奈秒

1. 24 小時制
0 ~ 23
2. 不含日期、不含時區

👉 只是「一天內的時間」

建立方式：
LocalTime.now()
LocalTime.of(7, 30)
LocalTime.parse("19:30:15")

parse 限制: 字串必須符合 ISO 格式

例如：

✔ 19:30
✔ 19:30:15
❌ 7:30（不行）


三、Temporal 限制（支援範圍概念）
背景問題: 不是所有時間類型都支援所有操作

例如：
❌ 錯誤：
```
LocalTime.plus(1, ChronoUnit.DAYS)
```

為什麼？ 因為LocalTime 沒有「日期概念」


如果你用錯：UnsupportedTemporalTypeException


四、range（欄位支援範圍查詢）
功能：
range(ChronoField)

背景用途： 查這個時間「合法範圍」

例子：
hour → 0 ~ 23
minute → 0 ~ 59
second → 0 ~ 59

range = 查這個欄位能取什麼值

五、LocalDateTime

結構：
LocalDate + LocalTime

特點：
有日期
有時間
沒有時區

表示方式：
2026-06-20T14:30

- 中間的 T 是 ISO 標準

plusHours(24)
行為： 會「跨天」

例如：

6/20 14:00
→ +24h
→ 6/21 14:00

六、格式化（輸出系統）
1. printf（舊式但仍支援）

```java
%tF  → YYYY-MM-DD
%tT  → HH:MM:SS
%tr  → 12 小時制
```

位置參照： %1$tF %1$tT

 同一個參數重複使用



2. DateTimeFormatter（推薦）
標準格式：
```java
ISO_WEEK_DATE
```
本地化格式：
```java
FormatStyle.FULL
FormatStyle.MEDIUM
```
行為： 會根據系統 locale（地區）輸出

例如：

中文
英文
日文格式


七、

Java Time API 的設計其實分三層：

1. 建立（Create）
now()
of()
parse()

2. 操作（Modify）
plus()
minus()
with()
datesUntil()

3. 查詢（Inspect）
get()
range()
isAfter()
compareTo()