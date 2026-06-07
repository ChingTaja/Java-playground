### 1. 多樣化的有限串流來源與 Map 介面的處理

- **陣列（Array）來源**：可藉由 `Arrays.stream(array)` 靜態方法將陣列轉為串流
    
- **任意物件序列**：可利用 `Stream.of(T... values)` 接受可變長度參數來動態建立串流
    
- **Map 介面的串流轉換**：因 `Map` 未繼承 `Collection` 介面且無 `stream()` 方法，欲處理 Map 資料時，**必須呼叫其集合檢視方法**（如 `entrySet()`、`keySet()`、`values()`），才能依附於傳回的 Set 或 Collection 啟動串流
    

### 2. Stream 的型別轉換（Transforming Stream Type）

- 中間操作雖然依循其簽章（Signature）必定傳回一個 Stream，但**不代表串流內部的元素型別不能改變**
    
- 藉由 `map` 操作，串流可以從起初的 `Stream<Map.Entry>` 動態轉換為完全不同的型別（如 `Stream<String>`），這在實務開發中非常普遍
    

### 3. 串流網路串接（Stream Concat）的執行順序

- 透過 `Stream.concat(streamA, streamB)` 能將兩條具備不同中間操作的串流拼接在一起
    
- **關鍵行為**：在終端操作被觸發前，各自段落（Segment）宣告的獨立中間操作（例如 A 段落轉大寫、B 段落排序）**只會作用在各自原本的來源資料上**；直到拼接合併後，後續追加的操作才會統一施加在整條整合後的串流
    

### 4. 無限串流（Infinite Streams）與記憶體安全控管

- **產生途徑**：有兩種靜態方法可建立無限串流：
    
    1. `Stream.generate(Supplier)`：依據供應者函數無限產生元素
        
    2. `Stream.iterate(seed, UnaryOperator)`：依據一元運算子對種子數無限遞增/遞減運算
        
- **防禦崩潰鐵律**：無限串流如果不加節制，會持續運算直到引發 Out of Memory 記憶體溢出
- **必須在管道中安插 `limit(...)` 這項中間操作來進行截斷控制。**
    
- **延遲評估（Lazy）的優勢**：當建立無限串流時，Java 不會卡死在建立無限多種可能性的步驟，其優化程序會自行控管需要實際產生多少元素來符合終端結果
    

### 5. 順序調整對效能與邏輯的重大影響

講稿中對比了兩組處理質數的 iterate 串流：

- **質數個數需求（先 filter 後 limit）**：`.filter(質數).limit(20)` ➔ 數據會無限流入，直到**篩選出 20 個質數**為止（此處底層共掃描 71 個數字）
    
- **數值範圍需求（先 limit 後 filter）**：`.limit(100).filter(質數)` ➔ 先將處理範圍**鎖定在前 100 個整數**，再從這 100 個數字中過濾出質數
    

### 6. 三參數 iterate 與 primitive 數值範圍串流

- **有限的 `iterate`**：`Stream.iterate` 具備三參數的多載版本，第二個參數傳入 `Predicate` 條件（例如 `n <= 100`），能直接將無限串流轉化為條件觸發的有限串流
    
- **數值範圍串流（`IntStream` / `LongStream`）**：
    
    - **`range(start, end)`**：產生連續整數，不包含（Exclusive）結束值
        
    - **`rangeClosed(start, end)`**：產生連續整數，包含（Inclusive）結束值
        
    - **DoubleStream 的例外限制**：`DoubleStream` **完全沒有**這兩個方法，因為在數學概念中，任意兩個雙倍精確度浮點數之間存在著無限多個實數
        
- **效能考量**：處理數字時，使用 `IntStream` 等基本型別專屬串流能省去自動裝箱與拆箱（Autoboxing/Unboxing）的效能與額外記憶體開銷



這兩個方法在不加限制的情況下會產生無限串流：

- **`Stream.generate(Supplier)`**：透過傳入的供應者函數無限生成元素。
    
- **`Stream.iterate(seed, UnaryOperator)`**：在**沒有**傳入第二個參數 `Predicate`（終止條件）的多載版本中，會依據一元運算子無限遞增或遞減運算。