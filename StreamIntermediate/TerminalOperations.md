### 1. 終端操作（Terminal Operations）的分類

依據執行後產生的==最終結果==，將終端操作歸納為以下四大類別：

- 尋找匹配項（Matches）：這類操作大多需要傳入一個 `Predicate` Lambda 表達式作為條件目標
- 轉換型別（Transform）：將串流中的資料蒐集並轉換成 `Collection`、陣列（Array）、`List`，或是其他開發者自行指定的參考型別（Reference type）
- 聚合資訊（Aggregate）：用來計算元素總數、或是找出極值。這類操作通常不需要傳入引數
- 疊代輸出：除了已熟知的 `forEach` 之外，還存在另一個名為 `forEachOrdered` 的操作

### 2. Primitive（基本型別）串流的專屬統計操作

相較於一般的泛型串流，基本型別串流擁有更多專屬的數值聚合方法：

- `sum`：計算總和
- `average`：計算平均值
- `summaryStatistics`：一項非常強大的綜合操作，呼叫後能在單一結果中直接打包獲取 `count`、`min`、`max`、`average` 與 `sum` 這五種完整的統計數據

### 3. 特殊傳回型別 `Optional`

- 串流中有部分的終端操作，執行後並不會直接傳回原始型別或標準物件，而是會傳回一個名為 `Optional` 的特殊封裝型別（講者宣告後續會有專門影片探討）

### 4. Reduction Operation 的本質

- 縮減操作是終端操作的一種特殊形式
- 它的核心行為是將管道中的所有 Stream 元素進行處理、提煉，最終凝結成一個「單一輸出（Single output）」
- **輸出的型別彈性**：縮減產生的單一結果，其型別可以非常多元。它可以是像 `long` 這樣的 Primitive type（例如 `count` 的結果）、也可以是像 `Optional` 或是統計物件這樣的 Reference type，甚至是開發者自己指定的任何集合或型別

#  Terminal Ops:

1.  `summaryStatistics()`
    - 當物件流（Stream of instances）透過 `mapToInt()` 或 `mapToDouble()` 轉換為數值流後，呼叫 `summaryStatistics()` 能夠以 O(1) 的空間複雜度，**同時獲得 Count、Sum、Min、Max 和 Average**
    - 這在初次拿到數據、想要探索結構時是一個非常完美的起點

2.  `peek()`
    - `peek()` 是一個 Intermediate Operation ，它接收一個 `Consumer` 但不改變流的內容。講者強調它在做數據規約（Reductions）時特別實用，能作為除錯的觀測窗口，看清通過 `filter` 的真實元素

3.  三大 Boolean 匹配操作（短路求值特性）- `anyMatch(Predicate)`：有一條過，就是 true

        - `allMatch(Predicate)`：全過，才是 true

        - `noneMatch(Predicate)`：全沒過，才是 true

        - 這些操作具備短路（Short-circuiting）特性
        - ex:  `anyMatch` 只要一遇到符合的元素，就會立刻終止 Stream 並回傳結果，不需要傻傻地走完整個擁有 100 個元素的陣列
                      | 適用 Stream 類型 (Stream)                                                 |



## 終端操作 (Terminal Operations)

| 終端操作 | 適用 Stream 類型 (Stream) | 回傳類型 (Return Type) |
|----------|--------------------------|------------------------|
| `count()` _(計算元素總數)_ | **所有 Stream (ALL)** | `long` |
| `max()` _(尋找最大值)_ | **所有 Stream (ALL)** | `Optional` |
| `min()` _(尋找最小值)_ | **所有 Stream (ALL)** | `Optional` |
| `average()` _(計算平均值)_ | `DoubleStream` / `IntStream` / `LongStream` | `OptionalDouble` |
| `sum()` _(計算總和)_ | `DoubleStream` / `IntStream` / `LongStream` | `double / int / long` |
| `summaryStatistics()` _(獲取完整統計摘要)_ | `DoubleStream` / `IntStream` / `LongStream` | `DoubleSummaryStatistics / IntSummaryStatistics / LongSummaryStatistics` |