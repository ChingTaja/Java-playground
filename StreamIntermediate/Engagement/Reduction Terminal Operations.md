- Reduction Terminal Operations 定義

`count()`、`summaryStatistics()`、`reduce()` 與 `collect()` 都屬於 Reduction Terminal Operations  
這類操作的核心任務是將整個 Stream 串流中的所有元素內容進行結合、收斂
最終縮減並回傳成一個單一的數值、一個物件，或是一個集合容器

- 簡化解讀複雜的方法簽章（Method Signatures） 建議在閱讀 `collect` 或 `reduce` 那些看似艱深晦澀的方法簽章時
  先忽略複雜的泛型宣告（Generic Types）
  核心關鍵在於辨識參數中所帶入的經典函數式介面，例如：`Supplier`、`BiConsumer`、`BinaryOperator`、`BiFunction` 與 `IntFunction`
  這代表我們可以使用相對應的 Lambda 表達式或方法參考來傳遞行為
- 專屬工具類別 `Collectors` 的引入 在第一個 `collect(Collector)` 操作中
  出現了一個非函數式介面的新面孔——`Collector`
  Java 另外提供了一個擁有複數變數名稱的實用輔助類別 `Collectors`（有加 s）
  它內建了許多靜態工廠方法，能為我們快速產出各式各樣的 `Collector` 實例，以便輕鬆將串流元素打包進任何我們想要的集合型態中
- 串流導出至常見容器（List 與 Array）
  - `toList()`：由於將串流元素放進 `List` 是開發中最常見的需求，因此 Java 官方直接在 `Stream` 介面上提供了一個最直覺的 `toList()` 終端操作
  - `toArray()`：若需要導出成陣列，可以使用 `toArray()` 的多載版本，它允許我們不指定型別直接拿回 `Object[]`，或者傳入型別建構式（如 `IntFunction`）來拿回指定型別的陣列
- 客製化容器的靈活性（Map 與 Set） 雖然單看 `reduce` 和 `collect` 的方法名稱並不直覺
  但透過這兩個高階 Reduction Operations，其實可以自由地將 Stream 元素轉換、組織成 `Map`、`Set`，或是任何自訂的複雜結果型態

https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/util/stream/Collector.html

# Java Stream Terminal Operations

| Return Type | Terminal Operations                                                                              |
| ----------- | ------------------------------------------------------------------------------------------------ |
| R           | `collect(Collector<? super T, A, R> collector)`                                                  |
| R           | `collect(Supplier<R> supplier, BiConsumer<R, ? super T> accumulator, BiConsumer<R, R> combiner)` |
| Optional    | `reduce(BinaryOperator<T> accumulator)`                                                          |
| Ｔ          | `reduce(T identity, BinaryOperator<T> accumulator)`                                              |
| Ｕ          | `reduce(U identity, BiFunction<U, ? super T, U> accumulator, BinaryOperator<U> combiner)`        |
| Object      | `toArray()`                                                                                      |
| Ａ          | `toArray(IntFunction<A[]> generator)`                                                            |
| List<T>     | `toList()`                                                                                       |

簡化版本

# Java Stream Terminal Operations

| Return Type | Terminal Operations                                                       |
| ----------- | ------------------------------------------------------------------------- |
| R           | `collect(Collector collector)`                                            |
| R           | `collect(Supplier supplier, BiConsumer accumulator, BiConsumer combiner)` |
| Optional    | `reduce(BinaryOperator accumulator)`                                      |
| T           | `reduce(T identity, BinaryOperator accumulator)`                          |
| U           | `reduce(U identity, BiFunction accumulator, BinaryOperator combiner)`     |
| Object      | `toArray()`                                                               |
| A           | `toArray(IntFunction generator)`                                          |
| List        | `toList()`                                                                |

- Stream 終端操作後的鏈結誤區
  在 pipeline 中呼叫 `.toList().forEach(...)` 時
  程式能正常執行且不會引發重複調用終端操作的異常
  是因為 `toList()` 已經結束了 Stream  pipeline 並返回了一個 List 集合
  其後的 `forEach` 是屬於該 List 集合物件本身的方法，而非 Stream 的終端操作
- `.toList()` 與 `collect(Collectors.toList())` 的可變性（Mutability）差異
  - `Stream.toList()`：直接隸屬於 Stream 介面，回傳的是一個不可變（Unmodifiable）的 List。若嘗試對其進行修改或呼叫 `Collections.shuffle()` 等異動操作，將會引發 Exception
  - `collect(Collectors.toList())`：屬於可變歸約（Mutable Reduction）
    它會將元素收集進一個允許被修改、排序或變更的 Mutable 容器。如果需要對產出的集合進行後續修改，就必須使用此方法或將結果重新包裝進 `new ArrayList<>()`
- `toArray()` 的無引數與強型別多載版本
  - 呼叫不帶參數的 `toArray()` 時，Java 串流會引發 Type Erasure，最終只能拿回一個未具備具體型別的 `Object[]` 陣列
  - 為了取得特定型別的陣列，必須傳入一個 `IntFunction` 函數式介面（例如 `Student[]::new` 方法參考）
    該建構式會以串流實際算出的 size 填入方括號中來完成強型別陣列的宣告與構造
- 解構 `Collector` 介面的三大泛型引數（T, A, R） 在 API 文件中，`Collector<T, A, R>` 介面由三個核心泛型參數所定義：
  - `T`：輸入元素的型別（即當前 Stream 流動的元素型別，如 `Student`）
  - `A`：累積器（Accumulator）在內部進行可變變更時所使用的暫存容器型別（例如 `StringBuilder` 或用來存放元素的特定 Bucket 容器）
  - `R`：歸約操作最終產出並回傳的結果（Result）型別（通常與 `A` 相同，但亦可轉換為其他客製化型態）
- 建構可變歸約結果的四大核心函數 一個完整的 `Collector` 運作邏輯由四個內部函數共同協作：
  1. Supplier：負責初始化並建立一個全新的可變結果容器
  2. Accumulator：負責將串流中的元素依序累積、置入該結果容器中
  3. Combiner：在進行平行處理時，負責將多個執行緒各自產出的結果容器進行合併
  4. Finisher（較少操作）：負責在所有結合完成後，對累積的實例進行最終的型態轉換
- `collect()` 的引數多載與 `Collectors` 工具類別 Java 官方在輔助類別 `Collectors`（有加 s）中提供了大量的靜態方法（如 `averaging`、`filtering`、`groupingBy`、`joining`、`partitioningBy`）
  這些命名高度類似資料庫的 Data Queries 語言
  另外，`collect` 方法也提供了接收三個參數（Supplier, Accumulator, Combiner）的多載版本，允許直接傳遞 Lambda 表達式，無需手動寫一個類別去實作 `Collector` 介面

=================================================

- Stream.generate() 的無參數 `Stream.generate()` 接收一個 `Supplier` 函數式介面作為參數
其 Lambda 區塊沒有任何輸入參數（以空括號 `()` 表示）
它能持續調用類別工廠方法來生成資料，搭配 `limit(n)` 中間操作可以精確固定化出指定規模的初始資料集

- `Collectors.toSet()` 的底層實作與無序性 `Collectors.toSet()` 靜態方法在底層預設是建立並回傳一個 `HashSet` 實例
在 `HashSet` 的結構中
元素唯一性的去重複完全仰賴物件的 `hashCode` 與 `equals` 機制，且其內部的 Bucket 雜湊分配不具有任何可預測的走訪順序

- 大數據場景下的 Set Math vs Stream Pipeline 當原始資料量非常龐大，而經過初步篩選拿到的子集合體積非常小時，直接利用集合內建的數學方法（如代表求交集的 `retainAll()`）來處理資料，在效能表現與運算成本上，會比重新啟動一條全新的 Stream 串流去遍歷掃描還要更具優勢

- 無序容器（Set）中的排序紅利失效（Redundant sorted） 
在 pipeline 中若是將資料導向無序的 `HashSet`（即呼叫 `Collectors.toSet()`）
在其上游呼叫 `.sorted()` 中間操作將被視為完全冗餘
串流流水線的內部機制會識別出下游容器的無序特性，因此在此處進行排序只是白白浪費運算效能

- 解構三參數 `collect()` 的三大行為核心 當官方的 `Collectors` 靜態工廠方法無法滿足需求時（例如需要將元素直接收集至帶有特定比較器的 `TreeSet` 中），可以使用自訂的三參數 `collect` 版本：
  - Supplier（供應器）：負責定義並建立一個全新的結果容器實例（可傳入 Lambda 或方法參考）
  - Accumulator（累積器）：負責定義如何將串流中的單一元素逐一塞入、累積至該結果容器中
  - Combiner（組合器）：負責定義在多執行緒或平行串流運算時，如何將各個區塊（Buckets）各自累積出來的暫存容器，透過 `addAll` 的方式結合收攏成單一集合

- TreeSet 元素自訂排序的異常防範 若要將元素收集至 `TreeSet` 中，該元素類別必須實作 `Comparable` 介面
若類別本身未具備自然排序能力（例如未實作該介面的 `Student` 物件）
則在自訂 `collect` 的 Supplier 時，無法直接使用 `TreeSet::new` 方法參考
必須改寫為 Lambda 表達式並在構造函數中主動注入自訂的 `Comparator`，否則執行期將會拋出 Exception 崩潰

- `collect()` 與 `reduce()` 的區別
  - `collect()`：屬於「 Mutable Reduction」，核心目標是把分散的串流元素，累積並收集進一個封裝好的「可變容器（Container）」內（如 `List`、`Set`、`StringBuilder`）
  - `reduce()`：屬於一般的「Reduction」，它並不使用容器，而是透過一組初始種子值（Identity）與累加邏輯（如字串拼接或數值相加），將整條串流中的所有資料最終不斷疊代、濃縮凝結成一個「單一型別的物件或數值」

# `collect()` 的兩種玩法

### 1. `collect()` 的第一種多載：現成工廠版

第一種寫法最常用，你只需要把 `Collectors` 類別裡的「靜態工廠方法」當作參數塞進去就好了。丟一行 `Collectors.toList()` 或 `Collectors.toSet()`，Java 就在底層自動幫你蓋好標準的 `ArrayList` 或 `HashSet`，你不需要知道它是怎麼蓋的，直接用就對了。

### 2. `collect()` 的第二種多載：三參數客製版

- 第二種寫法（也就是 `collect(Supplier, Accumulator, Combiner)`）雖然看起來比較複雜、參數一堆，但它給了你**最多掌控權與靈活性**。

### 3. 自訂收集器類別

-如果你連上述三參數的寫法都不滿意，你甚至可以自己寫一個獨立的類別（Class）去實作 `Collector` 介面，並覆寫裡面的抽象方法。

只是這種玩法，在一般商務開發中非常罕見，因為 Java 官方提供的 `Collectors` 工具類別已經能滿足我們 95% 以上的需求了

### 4. `collect()` 與 `reduce()` 的差異

-  `reduce()` 它跟 `collect()` 有一個最根本、最巨大的差別——**`reduce()` 的大腦裡完全沒有「容器（Container）」的概念**
- **差異對比**：
  - **`collect()` 是「收集」**：水管後面接的是一個**大桶子（容器）**。流出來 100 個學生的資料，它就把這 100 個物件整整齊齊地疊進 List 或 Set 桶子裡
  - **`reduce()` 是「折疊 / 歸約」**：水管後面沒有桶子，只有一個**小熔爐（單一變數）**。流進來一個元素
  它就把這個元素跟目前的狀態「融合成一體」。例如把所有學生的年齡不斷相加，最後融合成一個「總年齡數字」