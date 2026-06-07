Interface --> https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/util/stream/Stream.html



### 1. Stream 的基本定義與本質

- **非 I/O 串流**：此處討論的 Stream 與處理檔案或緩衝輸入的 I/O 串流不同
    
- **官方定義**：Stream 是一個「支援 sequential 與 parallel 聚合操作的元素序列」
    
- **運作本質**：一系列「鏈式運算步驟」（computational steps），強調 pipeline。Stream 自身**不儲存元素**，其中的元素是根據需求（on demand）從資料來源計算出來的
    
- **資料來源**：Stream 的來源可以是 collection、I/O stream 或資料庫的查詢結果
    

### 2. Stream 與 Collection 的核心差異

| **特性 / 動作** | **Collection (集合)**                                | **Stream (串流)**                              |
| ----------- | -------------------------------------------------- | -------------------------------------------- |
| **主要目的**    | 儲存與管理資料，可直接存取元素                                    | 不儲存資料，只負責處理資料流程（on demand 計算）                |
| **核心方法**    | 具備 `add`、`addAll`、`contains`、`get`、`put`、`remove`。 | **完全沒有**上述個別操作單一元素的方法。                       |
| **操作對象**    | 針對獨立的單一資料元素進行操作。                                   | 將資料視為一個單元或整體，進行聚合（aggregating）或縮減（reducing）。 |
| **執行特性**    | 呼叫時立即執行（直接操作或透過 iterator）。                         | 具備延遲執行（lazy execution）特性。                    |

- **能力邊界**：沒有任何事情是只用 stream 能做到而用 collection 絕對做不到的，但 stream 讓開發者只需專注於設計「程序本身」，而非編寫處理資料的底層程式碼。
    

### 3. Stream 的型別系統

- **泛型支援**：Stream 介面本身是泛型（Generic Stream），可以處理任何非基本型別（primitive）的型別（即 `Stream<T>`）
    
- **基本型別專屬 Stream**：為了處理基本型別，Java 額外提供了專屬的 `IntStream`、`DoubleStream` 和 `LongStream`
    

### 4. 延遲執行（Lazy Execution）與終端操作（Terminal Operation）

- **延遲機制**：Stream 的行為與將 lambda expression 指派給變數類似，呼叫其中的許多方法時，執行動作不會立刻發生（這屬於未執行的狀態）
    
- **觸發開關**：必須在 stream 上呼叫一個特殊的特定運作，稱為「終端操作（Terminal Operation）」，整套串流程序才會真正開始執行
    

### 5. 使用 Stream 的效益與技術

- **主要好處**：
    
    1. 讓資料處理程式碼變得統一、簡潔、具備可重複性，且具備類似資料庫 SQL 語法的結構感
        
    2. 處理大型 collection 時，未來可透過 parallel stream 提供效能與並行（concurrency）優勢
        
- **技術依賴**：Stream 的操作高度依賴功能性程式設計（functional programming），大量使用 lambda expression 和方法參考（method references）