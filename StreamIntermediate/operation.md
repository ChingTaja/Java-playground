### 1. 串流處理的三大核心行為

- 串流處理管道（Stream processing pipeline）中最常見的核心操作主要圍繞在三種行為：過濾（Filter）、轉換（Transform）與排序（Sort）
    

### 2. `map` 操作的強大與彈性

- 轉型：可以將一種 Stream 轉換為另一種完全不同的類型
    
- 無限鏈結：在一個管道中，可以根據需求串聯、使用任意多次的 `map` 操作
    
- 複雜度範圍：轉換邏輯可以被設計得極其簡單，也可以非常複雜
    

### 3. `peek` 操作的定位與風險

- 主要用途：最常用於在 Interim operations 的執行過程中把元素印出來，協助開發者理解黑盒子內部的實際運作狀況
    
- 不僅限於列印：其功能並不只限於輸出資訊
    
- 副作用陷阱：這個操作極易受到開發者有意或無意寫入的 Side effects 影響
    

### 4. `sorted` 操作的排序機制

- 結構相似性：其設計與運作結構和傳統 Collection（集合）的 `sort` 方法非常相似
    
- 無引數預設：若 Stream 內的元素本身已經實作了 `Comparable` 介面，則可以直接呼叫 `sorted()`，不需傳入任何引數
    
- 客製化排序：若元素未實作 `Comparable`，或者需要不同的排序規則，則必須主動傳入一個 `Comparator`
    
- 平行串流複雜度：在平行串流（Parallel streams）環境下使用 `sorted` 會變得很複雜，此特性屬於並行處理（Concurrency）的範疇



### 1. 泛型 Stream 與 Primitive（基本型別）專屬串流的轉換機制

串流在不同資料型別間切換的完整對應關係：

- **轉為基本型別串流（Primitive Streams）**
    
    - 使用 **`mapToDouble`** 可將一般泛型串流轉型為 **`DoubleStream`**（同理有 `mapToInt` 轉為 `IntStream`、`mapToLong` 轉為 `LongStream`）
        
    - 效能優勢：在處理海量數值資料時，使用基本型別專屬串流能免除包裝類別（Wrapper）帶來的記憶體開銷與自動裝箱/拆箱的計算成本
        
- 轉回泛型物件串流（Generic Stream）
    
    - 當身處 `DoubleStream` 內部時，一般的 `map` 操作被強制約束只能傳回 `double` 若想轉換為其他物件型別（如 String），必須改用 **`mapToObj`** 方法
        
    - 另一種常見的做法是呼叫 **`boxed()`** 方法，將數值直接裝箱（如 `DoubleStream` 轉為 `Stream<Double>`），一旦回復成物件串流，就能自由使用標準的 `map` 操作
        

### 2. 未實作 `Comparable` 介面的排序防錯機制

- 當 Stream 內部的元素型別（如 `Seat` record）**沒有實作 `Comparable` 介面**時，直接串接無參數的 `.sorted()` 操作會在執行期（Runtime）拋出異常（Exception）
    
- 解決手段：必須主動為 `sorted` 方法傳入一個 `Comparator`
            可善用 `Comparator.comparing(...)` 擷取特定欄位進行一級排序，再藉由 `.thenComparing(...)` 鏈結組合出次級排序規則
    

### 3. `peek` 偵錯操作的定位與 Lazy 特性

- 設計本質：`peek` 接收的是一個 **`Consumer`** 函數式介面，本質是不對串流內的成員造成任何 Side effects 官方文件指出其存在的主要目的是為了支援偵錯（Debugging），讓開發者能目睹元素流經 Pipeline 某個特定節點時的狀態
    
- Lazy 延遲執行鐵律：`peek` 本身依然只是一項 Intermediate operation 
    如果整條串流管道的尾端沒有安插任何終端操作（如 `forEach`），串流便完全不會被觸發，`peek` 內部的列印代碼也絕對不會執行
    
- 實務應用情境：如果最終的終端操作本來就是 `forEach(System.out::println)`，在前面加 `peek` 列印是多餘的；但當終端操作是 `max`、`min`、`count` 等將數據聚合（Aggregate）成單一結果的函數時，`peek` 對於想看清中間處理過程的開發者來說便極具價值



| 類型               | 從 Reference Type → Primitive Stream（Mapping to Primitive） | 從 Primitive Stream → Reference Type（Mapping back to Object） |
| ---------------- | --------------------------------------------------------- | ----------------------------------------------------------- |
| **DoubleStream** | `mapToDouble(ToDoubleFunction<? super T> mapper)`         | `mapToObj(DoubleFunction<? extends U> mapper)` / `boxed()`  |
| **IntStream**    | `mapToInt(ToIntFunction<? super T> mapper)`               | `mapToObj(IntFunction<? extends U> mapper)` / `boxed()`     |
| **LongStream**   | `mapToLong(ToLongFunction<? super T> mapper)`             | `mapToObj(LongFunction<? extends U> mapper)` / `boxed()`    |