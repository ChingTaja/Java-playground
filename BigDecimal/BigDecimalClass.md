# Java BigDecimal 重點整理（精準財務計算必備）
1. 為什麼不能用 float / double
✔ 浮點數本質問題（核心觀念）
float（約 6–7 位有效數字）
double（約 15–16 位有效數字）

它們儲存的是：二進位近似值（approximation），不是精確十進位

❌ 財務上的問題（致命點）

在金額計算中會出現：

無法整除（如 1 / 3）
累積誤差（越算越歪）
小數遺失（0.1 + 0.2 ≠ 0.3）

👉 結果：

多付錢（溢付）
少付錢（帳目不平）
對帳失敗

2. BigDecimal 是什麼

👉 Java java.math.BigDecimal 是：

用「十進位字面精確值」進行運算的高精度數字類別

3. BigDecimal 的內部結構（必考）
✔ 由兩部分組成：
① Unscaled Value（未縮放值）
型態：BigInteger
作用：去掉小數點後的「整數」

例：

15.45 → 1545

② Scale（標度）
int
表示小數點右邊的位數

例：

15.45 → scale = 2
✔ 數字真正表示方式：
value = unscaledValue × 10^(-scale)
4. Precision（精度） vs Scale（標度）
名稱	意思
Precision	總位數
Scale	小數點右邊位數

例：

15.456
precision = 5
scale = 3
5. 建構方式（重要～～）

❌ 絕對不要這樣子使用
```java
new BigDecimal(double)
```
為什麼危險？
double 本身已經是「不精確值」
BigDecimal 會完整還原誤差

結果：0.1 → 0.1000000000000000055...

6. 正確用法（3種）

✔ ① 最安全（推薦）
```java
new BigDecimal("0.1")
```
完全精確（字面解析）

✔ ② 官方推薦替代
```java
BigDecimal.valueOf(0.1)
```

本質： double → String → BigDecimal

✔ ③ 大型商務標準用法
```java
new BigDecimal("100000000.00")
```

適用：

金融
保險
帳務系統

7. 運算規則
✔ 除法（一定要注意）
```java
// 用 60 位精度計算 1/3，並且「永遠往上進位」避免低估結果
BigDecimal.ONE.divide(
    BigDecimal.valueOf(3),
    new MathContext(60, RoundingMode.UP)
);
```

因為： 
- 1 / 3 = 無限循環小數
必須指定：
- 精度（precision）
- rounding mode

✔ 乘法特性
scale 會相加
2 decimals × 2 decimals → 4 decimals

✔ 財務修正（關鍵）
setScale(2, RoundingMode.HALF_UP)

用途：統一金額格式（兩位小數）
避免帳務誤差

8. 總結

float/double 是「近似值」，
BigDecimal 是「精確十進位數字系統」

9. 

float/double = approximation（近似）
BigDecimal = exact decimal（精確）
禁止 new BigDecimal(double)
一律用 String 或 valueOf
財務上要更精確一定要 setScale + rounding