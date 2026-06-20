一、為什麼 Java 需要 BigDecimal

在 Java 中，float / double 使用的是 IEEE 754 二進位浮點數，這種設計在科學計算沒問題，但在「十進位金額」會出現誤差。

例如：

0.1 + 0.2

結果不是 0.3，而是：

0.30000000000000004

二、BigDecimal 概念

1. 不可變性（Immutable）
   背景問題：

數值如果可以被修改，很容易出現不可預期錯誤（尤其是多線程或連鎖計算）

BigDecimal 的設計：

```java
value.add(x);
```

不會改 value，而是：

👉 回傳一個新的 BigDecimal

正確使用方式：
value = value.add(x);

BigDecimal = 每次運算都「產生新數字」，不是改原本數字

2. String 建構子的意義（避免誤差來源）
   背景問題：

```java
new BigDecimal(0.1);
```

這裡的 0.1 在進入 BigDecimal 前就已經是「浮點誤差版本」

正確方式：

```java
new BigDecimal("0.1");
```

為什麼重要： String 是「字面精確值」，不經過二進位轉換

Scale 觀念（小數結構）：
"100.00" → scale = 2
"100" → scale = 0
記憶： 用 String = 保證數字從出生就是精確的

3. Scale + setScale（控制小數精度）
   背景問題（實務）：金融系統一定要統一小數位數，例如：

價格：2 位
稅金：2 位
匯率：6 位
使用 setScale：
value.setScale(2, RoundingMode.HALF_UP);

- 作用： 強制把數字變成「指定小數格式」

重要規則：

如果縮小 scale 且會失真：

new BigDecimal("1.236").setScale(2);

會直接：ArithmeticException

原因：Java 不允許「默默丟失精度」

- setScale = 我要精確變成某個格式，不是幫你隨便截斷

4. RoundingMode（當數字切不開時）
   背景：
   1.235 → 2 位小數 → 要變 1.24

但也可以是其他策略

常見模式：
模式 行為
HALF_UP 四捨五入（最常用）
UP 永遠進位
DOWN永遠截斷

===> RoundingMode = 決定「砍掉小數時怎麼處理」

5. divide + MathContext（無限小數問題核心）

- 背景問題：1 / 3

- 結果：

```
0.333333333333333333...
```

- 問題：這種數字無法完整表示

所以 Java 要你明確告訴它：

- new MathContext(60, RoundingMode.UP);

MathContext 做兩件事：

- precision（保留幾位有效數字）
- rounding mode（怎麼截斷）

===> divide = 最容易產生無限小數，所以一定要限制精度

6. multiply 的 scale 行為（財務常見坑）
   背景：

```java
1.2 × 3.4
```

scale 規則：

```
1.2 (scale 1)
3.4 (scale 1)
→ result scale = 2
```

結果可能變：
4.0800
問題：

財務只要： 4.08

所以必須補： result.setScale(2, RoundingMode.HALF_UP);

multiply 只負責算，==格式要你自己決定==

7. 負 Scale（科學記號的內部表示）
   背景：

```java
new BigDecimal("100e6");
```

可能內部表示：

```
100000000
scale = -6
```

===> 小數點被「往右移」
===> 負 scale = 數字被放大表示（科學記號）

8. 靜態常數（安全與效能）
   BigDecimal.ZERO
   BigDecimal.ONE
   BigDecimal.TEN

背景： 避免一直 new object

好處：

- 提升效能
- 語意清楚

====> 常用數字用共用實例，不要重複建立

9. Arbitrary Precision
   背景： float / double 有固定誤差範圍

BigDecimal 不一樣：

精度由你決定

MathContext 例子：

```java
new MathContext(60);
```

===> 60 位有效數字

記憶：

===> BigDecimal = 精度由開發者控制，而不是系統決定

三、總結

當你用 BigDecimal 時，其實是在做這三件事：

Step 1：決定「數字來源」

===> 用 String 確保精確

Step 2：決定「運算精度」

===> divide / MathContext 控制誤差

Step 3：決定「顯示格式」

===> setScale + RoundingMode 控制輸出

四、總結

BigDecimal 的核心不是「算數」，而是「你要自己定義精度、捨入與格式的十進位計算系統」
