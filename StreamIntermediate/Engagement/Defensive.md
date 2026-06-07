### 1. 模型設計中的防禦性複製（Defensive Copy）

- 為了解決封裝性漏洞，在 `getEngagementMap()` 中不能直接回傳內部 `HashMap` 的原始物件參照
- 使用 **`Map.copyOf(engagementMap)`** 回傳一份不可變（Unmodifiable）的拷貝，阻止外部程式繞過 `Student` 類別定義的商務邏輯去偷改進度
    

### 2. 基於多元資料結構的計算型欄位（Calculated Fields）

- 類別內部設計了多個動態計算欄位（如 `getYearsSinceEnrolled`、`getAge` 等），利用方法多載區分查詢「特定單一課程」或「所有課程中最接近當前時間」的未活動月數（`getMonthsSinceActive`）
- 這些高度多樣化的資料屬性是專門為了後續在 Stream Pipeline 中展示多元聚合與 fil而設計的
    

### 3. `Stream.generate` 在測試 Data Set 建置的角色

- 面對需要無限生成、非離散、具隨機特性的擬真數據時，**`Stream.generate`** 是最合適的工廠 soruce
    
- 它接受一個 **`Supplier`** 函數式介面（無參數、有傳回值），開發者可以在 Lambda 本體中呼叫內含隨機數產生器（`random.nextInt`）的靜態工廠方法（`Student.getRandomStudent`），並能自由傳入外部具備 Effectively final 特性的常數物件。
    
------------------------------------------

### 1. 中間操作對 Stream 參考的失效（Invalidation）機制

- 不可重複使用性：當在一個 Stream 物件上呼叫了中間操作（例如 `.filter(...)`）時，即使它是一項延遲執行（Lazy）的操作，原本的 Stream 參考也會立即宣告失效（Invalidated）
    
- 正確開發習慣：在逐步構建 Stream Pipeline 時，必須將後續的操作直接鏈結（Chain）在一起，或者將中間操作回傳的**全新 Stream 參考**指派給變數，否則後續觸發終端操作時會引發執行期異常（Exception）
    

### 2. 透過重用陣列（Array）克服 Stream 僅能耗用一次的限制

- 由於 Stream 的終端操作（如 `count()`）一旦觸發執行，該條串流水管便會關閉且無法再次重用。為了針對「同一批學生數據」進行性別、年齡等不同維度的多次交叉查詢，講者先利用 `Arrays.setAll` 將隨機數據固化至 `Student[]` 陣列中，隨後多次藉由 `Arrays.stream(students)` 重新產生獨立的串流源進行查詢
    

### 3. 將 `Predicate` 集合化以進行宣告式分組計數

- 講者展示了將邏輯條件物件化的技巧：利用 `List.of(Predicate<T>...)` 將多個不同範圍的 Lambda 表達式存入集合中。在傳統的迴圈內，可以直接透過 `list.get(i)` 動態將這些條件注入 `.filter()` 中
    

### 4. 串流運算中的效能優勢與優化意識

- 在分組統計多個互斥區間（如年齡層）時，講者特別指出，不需要為每一個區間都單獨跑一次 Stream 流程。對於最後一個區間，直接用總資料量扣除前幾個區間的累加總數（`students.length - total`） 即可。這樣能省去一次完整的 Pipeline 掃描，在處理海量數據時能帶來顯著的效能提升