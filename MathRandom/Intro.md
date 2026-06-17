- **數值溢位防禦機制**：

傳統算術運算（如 `++`）在超越 `Integer.MAX_VALUE` 時會發生靜態且悄悄的溢位（轉為負數）
`Math` 類別提供了 `incrementExact`、`decrementExact`、`addExact` 與 `subtractExact` 等方法
能在溢位發生時立即拋出 `ArithmeticException` 以進行異常處理
    
- Integer.MIN_VALUE 的絕對值陷阱：直接對 `Integer.MIN_VALUE` 呼叫 `Math.abs` 會因為正數範圍溢位而依舊回傳負數。解決方法是使用 `Math.absExact` 拋出異常，或是透過強制轉型 `(long)` 來調用接收 `long` 型態的過載版本以獲取正確的絕對值
    
- 數值型態過載與精確度：`Math.min` 與 `Math.max` 會根據傳入的型態選擇過載版本。若同時傳入 `float` 與 `double`，會自動升級為 `double` 計算。使用 `float` 時需注意其精確度限制，可能會遺失不顯著的低位數值
    
- 捨入與進位函數差異：
    
    - `Math.round`：標準四捨五入（小數達 `.5` 即進位），傳入 `double` 回傳 `long`
        
    - `Math.floor`：天花板以下無條件捨去，回傳 `double`
        
    - `Math.ceil`：地板以上無條件進位，回傳 `double`
        
- 數學乘方與隨機數特性：`Math.sqrt`（平方根）與 `Math.pow`（次方的乘方）皆回傳 `double` 型態。`Math.random()` 產生的隨機數範圍固定為開區間 `[0, 1)`，即包含 0 但絕不包含 1


- Math.random() 的底層機制：Math.random() 內部使用的是 java.util.Random 類別的單一實例，並調用其 nextDouble() 方法；第一次呼叫時建立實例，後續呼叫皆共用此實例
    
- JDK 17 的 Random 變更：JDK 17 將 Random 修改為實作 RandomGenerator 介面，使其 nextInt()、nextDouble() 和 nextLong() 的多載版本擁有預設實作（default implementations），支援直接傳入區間下界與上界
    
- **區間界限規則**：Random.nextInt(lower, upper) 方法中，下界是 inclusive（包含），上界是 exclusive（不包含）
    
- **Random 串流方法 (JDK 8)**：
    
    - `ints()` 不帶參數回傳無限的 IntStream，數值範圍為 int 的全域（含負數）
        
    - `ints(lower, upper)` 回傳限定數值範圍的無限串流
        
    - `ints(streamSize, lower, upper)` 回傳指定大小的有限串流
        
    - `ints(streamSize)` 單一參數代表的是串流大小，而非上界，範圍為全域整數
        
- **No-args 建構子的 seed 生成**：Random 的 No-args 建構子鏈結呼叫了帶有 seed 的建構子，其預設的 seed 是結合 `System.nanoTime()` 與 `seedUniquifier()`（內部使用 AtomicLong）共同運算產生，確保高機率的唯一性
    
- **偽隨機數產生器與固定 Seed**：Java 的隨機數並非真正隨機，而是透過演算法產生的偽隨機數；若將兩個 Random 實例設定完全相同的 seed，兩者將會產生完全相同、可預測的數字序列，此特性適合應用於程式碼測試或統計模型驗證