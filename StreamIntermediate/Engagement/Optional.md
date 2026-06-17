`Optional` 這個類別為了解決 「空指標異常（`NullPointerException`）」而誕生的工具

### 1. 什麼是 `Optional`？

- 本質：它是一個**泛型容器類別（Generic Class）**，裡面可能裝有某個物件值，也可能什麼都沒裝（即值為 `null`）
    
- 目的：用來處理 `null` 值問題，藉此防範並減少 `NullPointerException`的發生
    

### 2. 

- 官方文件明確指出：`Optional` 主要的設計意圖是**作為「方法的傳回值型態（Method Return Type）」**
    
- 它用來明確區分兩種情境：
    
    1. **「沒有資料」是完全合理且正常的業務狀況**（適合回傳 `Optional`）
        
    2. **「沒有資料」其實是一個真正的異常或錯誤**（適合直接拋出 Exception）
        

### 3. 「沒有資料合理」的生活範例

講稿中提到了幾個「沒有值並不代表出錯」的實際場景：

- 個人姓名：並非每個人的名字都有「中間名縮寫（Middle Initial）」，甚至有些人沒有姓氏
    
- 出生日期：在「譜系（家譜）分析軟體」中，古人的出生日期不知道是很正常的（合理）；但在「求職就業系統」中，出生日期就是必填項（不填就是錯誤）
    
- 商品價格：剛進貨的全新商品可能還沒定出銷售價。如果硬用 `0` 元當作預設值，可能會導致嚴重的商務虧損與系統問題，此時「沒有價格」才是正確的狀態
    

> 觀念：`Optional` 就是一種明確的宣告，告訴系統：**這個值目前可能不存在，因此在後續處理中可以被安全地忽略或妥善處理**

### 4. `Optional` 的語法特性與建立方式

- 無法直接實例化：它雖然是 generic 類別，但你**不能**使用 `new Optional(...)` 來建立它
    
- 靜態工廠方法（Static Factory Methods）：必須透過以下三種官方提供的靜態方法來產生實例：
    
    1. `Optional.empty()`：建立一個空的 `Optional` 容器
        
    2. `Optional.of(value)`：建立一個包含確定值的容器（如果傳入的值是 `null`，會立刻拋出 NPE）
        
    3. `Optional.ofNullable(value)`：最寬容的方法，傳入的值可以是具體物件，也可以是 `null`（如果是 `null` 就會自動轉成空容器）


--

- Optional 的核心本質與誕生目的
    
    - `Optional` 是一個泛型容器類別，裡面可能裝著一個非空的值，也可能什麼都沒裝（Empty）
    - 它的出現是 Java 工程師為了對抗 Java 最常見的系統崩潰兇手——空指標異常（`NullPointerException`, NPE）所提出的解決方案
        
- 官方設計的真正意圖與嚴格反模式警告
    
    - 設計初衷：官方文件明確指出，`Optional` **主要且唯一推薦的用途是作為「方法的傳回值型態（Method Return Type）」**
    - 它用來明確表達「沒有結果」在業務上是一個完全合法且合理的正常現象（例如：新進貨商品還沒定價、人們不一定有中間名縮寫等），而非系統發生了嚴重錯誤
        
    - **避坑警告**：許多開發者為了消滅 NPE 而過度濫用它
    官方極度不建議將 `Optional` 用於類別的成員變數（Fields）、Getter 方法或方法參數（Method Parameters）
    這不僅會耗費更多記憶體、大幅降低系統執行效能，還會額外增加程式碼的複雜度與降低可讀性
    此外，**`Optional` 是不可序列化的（Not Serializable）**，在 I/O 流序列化時會引發問題
        
- 開發者的第一鐵律（The First Rule）
    
    - **絕對不要讓回傳型態為 `Optional` 的方法傳回 `null`！** 
    如果方法回傳了 `null`，呼叫端在對其調用 `.isEmpty()` 或 `.isPresent()` 時依然會直接引發 `NullPointerException` 崩潰。當沒有資料可提供時，唯一正確的應變方式是回傳 **`Optional.empty()`**
        
- 建立 `Optional` 容器的三大靜態工廠方法
    
    - `Optional.empty()`：建立一個百分之百為空的 `Optional` 容器
        
    - `Optional.of(value)`：建立包含特定值的容器。**警告：若傳入的值不幸為 `null`，會當場觸發 `NullPointerException` 崩潰**
        
    - `Optional.ofNullable(value)`：最安全且寬容的管道。如果傳入的值是具體物件則正常包裝；如果傳入的值是 `null`，底層會自動妥協並轉化為 `Optional.empty()` 空容器
        
- 安全消費與存取機制
    
    - **不要盲目 get()**：當容器為空時，直接呼叫 `.get()` 會噴出 `NoSuchElementException` 異常。必須先透過 `.isPresent()` 或 `.isEmpty()` 判斷，或者使用現代化的函數式寫法
        
    - **ifPresent(Consumer)**：當值存在時才執行對應動作，為空則安全忽略
        
    - **ifPresentOrElse(Consumer, Runnable)**：兼顧兩種情境。若存在則消費值；若為空則觸發 Runnable Lambda（無參數、無回傳值）來印出預設文字或執行應變措施
        
- orElse 與 orElseGet 的效能比對（Eager vs Lazy）
    
    - **`orElse(T other)` 屬於 Eager Evaluation（及早求值）**：不論 `Optional` 容器裡到底有沒有值，作為參數的方法（如 `getDummyStudent()`）**一定會被強制執行並評估**。若該方法涉及高負載運算或資料庫查詢，將會演變成災難性的效能漏洞
        
    - **`orElseGet(Supplier)` 屬於 Lazy Evaluation（延遲求值）**：它接受一個 Supplier Lambda 函數
    只有在 `Optional` 容器真正確認為空（Empty）的臨界點，才會回頭去觸發與執行 Lambda 內的方法，系統效能較佳
        
- Optional 的類 Stream pipeline特性
    
    - `Optional` 類別內部設計了 `map` 與 `filter` 方法，其外觀與用法高度模仿了 Stream Pipeline 的鏈式風格
    - 這能讓開發者在拿到方法結果後，以高度一致、簡潔且具備高度可讀性的函數式風格，進行資料的形態轉換與條件過濾，完全免除傳統 `if (obj != null)` 的冗長巢狀檢查


# 介紹 會回傳一個 Optional 的 terminal operations

| **Return Type**  | **Terminal Operations**                 |
| ---------------- | --------------------------------------- |
| `OptionalDouble` | `average()`                             |
| `Optional<T>`    | `findAny()`                             |
| `Optional<T>`    | `findFirst()`                           |
| `Optional<T>`    | `max(Comparator<? super T> comparator)` |
| `Optional<T>`    | `min(Comparator<? super T> comparator)` |
| `Optional<T>`    | `reduce(BinaryOperator<T> accumulator)` |

 average 這個操作只適用於基本型態的串流（primitive streams）
 也就是 IntStream、LongStream 和 DoubleStream
 它會回傳一個特殊的 Optional 型態叫做 OptionalDouble
 這是一個包裝了 double 基本型態的容器
 
 另外還有 findAny 和 findFirst，它們通常會配合 filter 一起使用
 用來從串流中獲取單一元素
 最後，還有另一種形式的 reduce 方法，它只接收一個  accumulator


- 元素搜尋機制差異：`findAny` 回傳串流中的任意元素（執行多次結果可能不同），效率通常較好
而 `findFirst` 則嚴格保證回傳符合串流處理順序的第一個元素
    
- 極值操作重構（min/max）：`sorted().findFirst()` 的組合可以被單一終端操作 `min` 或 `max` 所取代
這兩個操作皆必須傳入 `Comparator`，重構後的管線意圖更加清晰、易讀
    
- **基本型態串流的平均值**：透過 `mapToInt` 將串流轉換為 `IntStream` 後可使用 `average` 操作，其回傳型態為特殊的 `OptionalDouble`，同樣具備 `Optional` 的方法特性
    
- **reduce 的單參數變體**：接收一個 `BinaryOperator`（操作兩個同型態參數並回傳同型態結果），可用於將串流元素進行記憶體中的累加處理（例如用 `String.join` 做字串串接），其回傳值為 `Optional`
    
- **Optional 流程控制與管線整合**：利用 `Optional` 提供的 `ifPresentOrElse` 方法，不需宣告額外的區域變數或進行 null 檢查，即可將 Stream 的終端結果與後續處理邏輯完美融合成一條龍式的 seamless pipeline