# Understanding Side Effects of Mutability: Defensive Coding Techniques

當我的方法只需要對文字進行**唯讀**的操作（例如讀取長度、檢查內容、列印輸出）
而不在乎對方是用 `String` 還是 `StringBuilder` 傳進來時，我就會將參數宣告為 `CharSequence`
這樣做符合物件導向中**物件導向設計原則：針對介面編程，而不是針對實現編程（Interface-driven design**
能大幅提高程式碼的相容性與架構彈性！

# Explicit Indexing

### 1. 傳統的做法（沒使用明確索引）

如果想同時印出日期與時間，必須把同一個時間變數傳入好幾次，編譯器才會知道誰是誰：

```java
LocalDateTime now = LocalDateTime.now();
// ❌ 缺點：now 必須重複傳入兩次，後續維護很麻煩
System.out.printf("今天是 %tD，現在時間是 %tT，訊息：%s%n", now, now, "系統啟動");
```


### .2 進階做法：使用明確索引（Explicit Indexing）

透過在 `%` 後面加入 `數字$`（例如 `1$` 代表第一個引數，`2$` 代表第二個引數），你就可以**只傳入一次時間變數，卻在字串中重複重複利用它**

```java
LocalDateTime now = LocalDateTime.now();
String msg = "系統啟動";

// 💡 語法解析：
// %1$tD -> 抓取第 1 個引數 (now)，並用大寫 D (日期) 格式化
// %1$tT -> 再次抓取第 1 個引數 (now)，並用大寫 T (時間) 格式化
// %2$s  -> 抓取第 2 個引數 (msg)，並用字串格式化
System.out.printf("今天是 %1$tD，現在時間是 %1$tT，訊息：%2$s%n", now, msg);
```


# 絕對、永遠不要使用『可變物件（Mutable Object）』作為 Map 的 Key！

### 「禁止變更」防禦武器

### 1. 防止變更物件狀態（State）$\rightarrow$ 使用 `private` 與刪除 Setter

- **如何實現**：將所有欄位宣告為 `private`，並且**絕對不要**提供自動產生的 Setter 方法
    
- **效益**：這就是「封裝」
  物件的內部狀態只能由自己掌控，外部客戶端或子類別想改也改不到，彻底杜絕了半路被篡改的風險
    

### 2. 防止方法被變更 $\rightarrow$ 使用 `final` 方法

- **如何實現**：在方法前加上 `final` 關鍵字（例如 `public final void log()`）
    
- **技術效益**：
    
    - 對於**實例方法**：禁止子類別進行 **覆寫（Override）**
    
    - 對於**靜態方法**：禁止子類別進行 **方法隱藏（Method Hiding）**
        
        確保核心商業邏輯或框架的主骨架不會在繼承鏈中被魔改
        

### 3. 防止類別被擴充（繼承）$\rightarrow$ 使用 `final` 類別

- **如何實現**：直接宣告類別為 `public final class ToolKit`
    
- **效益**：直接截斷繼承樹！不允許任何人透過 `extends` 來繼承這個類別
  Java 內建最核心的 `String` 類別就是最經典的 `final class`，確保全台灣、甚至全世界的 JVM 上跑的 `String` 行為完全一致，沒有人能透過繼承去惡搞它
    

### 4. 防止類別被實體化 $\rightarrow$ 使用 `private` 建構子或 `abstract`

- **如何實現**：
    
    - 寫一個 `private ToolKit() {}`（私有建構子）
        
    - 或是將其宣告為 `abstract class`
        
- **效益**：這通常用在「靜態工具類別（Utility Class，如 `java.lang.Math`）」
因為裡面通通都是 `static` 方法，根本不需要 `new` 物件出來浪費記憶體空間，直接把建構子鎖死，只要有人敢寫 `new ToolKit()`，編譯器直接當場攔截