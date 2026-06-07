所有的 pipeline 都是以一個 stream 作為開始
所以在這個範例中，我們需要呼叫 `bingoPool` List 上的 `stream()` 方法來取得一個 stream


### 1. Stream Pipeline（串流管道）的定義

- 這套將所有操作串聯在一起的完整「操作鏈結」，被稱為 **Stream Pipeline**
    

### 2. Stream 的來源（Source）

- 所有 Pipeline 都是由一個 Stream 開始
    
- 來源是提供資料元素的地方（如範例中 List 物件呼叫 `stream()` 方法）
    
- 建立新 Stream 的來源與方法有很多種，其中也包括了無限串流（Infinite Streams）
    

### 3. 中間操作（Intermediate Operation）的特性

- **非必要性**：中間操作在 Pipeline 中**並非必須具備**，常見的 Pipeline 也可以只由「來源」與「終端操作」直接組成
    
- **關鍵**：每一個中間操作在處理完 Stream 上的元素後，**其回傳值必定還是另一個 Stream**
    

### 4. 終端操作（Terminal Operation）的特性

- **必要性鐵律**：所有的 Stream Pipeline **都必須具備**一個終端操作來做為結尾
    
- **功能與作用**：終端操作會啟動執行並產生最終結果，或者產生副作用（Side-effect，例如範例中藉由實作 `Consumer` 來執行列印資料的 `forEach`）
    
- **回傳值型別**：終端操作會傳回許多不同的資料型別。唯有 **`forEach`** 與 **`forEachOrdered`** 這兩個操作不會傳回任何東西（其傳回型別為 `void`）


### 1. 透過「回傳型別」識別 Stream 操作類型

- **終端操作（Terminal Operation）**：回傳的不是 Stream。例如回傳 boolean 的 `allMatch`、`anyMatch`...
    
- **中間操作（Intermediate Operation）**：回傳值必定是另一個 Stream。例如 `distinct`、`dropWhile`、以及 `filter`。
    

### ### 2. 基本型別專屬的 Stream

- **設計目的**：由於 Java 的 `Stream` 介面是泛型（Generic），無法直接宣告基本型別（Primitive type）。
    
- **效益**：Java 提供特製的 `DoubleStream`、`IntStream` 和 `LongStream`，讓開發者在處理數字時，能**避免包裝型別（Wrapper type）所帶來的額外記憶體與運算開銷（Overhead）**。
    

### 3. Stream 與 Functional Interface（函數式介面）的緊密結合

- Stream 所有的操作幾乎都重度依賴函數式介面，構成了充滿 Lambda 運算式的環境

- 對應關係如下：
    
    - **`forEach`** ➔ 接受 **`Consumer`**
        
    - **`generate`** ➔ 接受 **`Supplier`**
        
    - **`iterate`** ➔ 接受 **`UnaryOperator`**
        
    - **`map`** ➔ 接受 **`Function`**
        
- Lambda 運算式、方法參考與 Stream 是相輔相成、密不可分的，皆為 Java 轉向 Functional programming 的核心
    

### 4. 延遲執行（Lazy Execution）與「黑盒子」機制

- **觸發鐵律**：對來源資料的實際運算，只有在「終端操作被正式啟動」的那一刻才會執行。在此之前，中間操作什麼都不會做。
    
- **按需消耗**：資料來源的元素只會在有需要的時候才會被消耗
    
- **黑盒子最佳化與非保證順序**：
    
    - **對比傳統方法鏈結**：傳統的方法鏈結（Method chaining）在呼叫時，保證會依照你定義的順序、百分之百針對所有已知元素逐一執行。
        
    - **Stream Pipeline**：它就像一個黑盒子，內部實際發生的執行順序或方式，**可能不會完全等同於你寫程式碼時描述的步驟，也不一定符合你指定的順序**。
      ==> 中間操作的執行完全取決於「終端操作是否被指定」以及「底層最佳化程序（Optimization process）」的結果

來源就是你的輸入，你終端操作的結果就是你的輸出
夾在兩者之間的所有事情，在某個東西叫那個終端操作開始執行之前，都是絕對不會發生的
在那個黑盒子裡實際上發生的事情，可能不會完全像你所描述的那樣發生，或者可能不會按照你所指定的順序發生
這與方法鏈結（chaining methods）相當不同，在方法鏈結中，每個方法的執行都是保證會發生的
而且它是按照你定義的順序，針對一組已知的元素來執行。 中間操作的執行是具備相依性的



### 5. Stream 的運算是針對效能進行最佳化的

#### 1. Stream Pipeline 只是「工作流程建議」

-  `.filter().map().sorted()` 底層並非死板板的執行步驟，而是一個給編譯器與 JVM 的「工作流程建議（Workflow suggestion）」
    
- 在實際執行前，Stream 內部會先進行整體評估，自己抓出一條最快、最有效率的執行路徑來達到最終結果
    

####  2. 結果一致，但過程不保證相同

- 雖然不論執行幾次，Stream 給你的**最終輸出結果（Result）絕對都是相同且一致的**
    
- 但是底層為了達到這個結果所跑的**中間處理程序（Process）卻不保證一樣**
    

####  3. 底層優化的三大手段

在評估階段，Stream 為了追求極致效能，可能會對你寫的中間操作進行以下調整：

- **調整順序**：改變中間操作的先後執行順序。
    
- **合併操作**：將數個操作融合在一起執行。
    
- **直接跳過（省略）**：例如你寫了 `.filter(篩選G與O)`，但如果資料來源裡本來就只有 G 標籤的資料，Stream 評估後發現這步是多餘的，就會**直接把整個 filter 拔掉不執行**
    

####  4. ⚠️ 鐵律禁忌：避免在中間操作中產生「副作用（Side Effects）」

- **什麼是副作用**：在中間操作的 Lambda 運算式中，寫了「過濾與轉換資料以外的外部改動程式碼」（例如：偷偷去累加外部某個物件的 Counter 計數器、修改外部變數等）
    
- **為什麼不行**：因為根據前述的優化手段，某些中間操作可能會被 Stream 直接跳過或省略
  一旦該操作被省略，你寫在裡面的外部計數器就**永遠不會被執行到**，進而導致嚴重的邏輯錯誤（Bug）




https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/util/stream/Stream.html#method-summary




### 1. Terminal Operation 是「啟動水流的開關」

- 呼叫終端操作的那一刻，就像是打開了水管的「閥門（Valve）」，原本處於延遲（Lazy）狀態的資料流（Flow）才會真正開始流動，並一路執行完所有的中間程序。
    

### 2. 🚨 核心鐵律：Stream 的生命週期是一次性的（不可重用）

- **水流結束，水管鎖死**：當資料全部流完、終端操作產生最終結果之後，這個閥門就會被永久關閉，整條 Stream 管道也會隨之「關閉（Closed）」。
    
- **不可重啟、不可換料**：**絕對無法**重新啟動一條已經執行過終端操作的 Stream，也**無法**更換新的資料來源（Source）來重用這條舊水管。
    

### 3. 想換參數或換變數？請重新蓋一條新水管

- 如果你在寫中間操作時，想要更換不同的變數或條件來做類似的資料處理，唯一的作法就是**重新宣告並建立一條完全獨立、全新的 Stream Pipeline**，不能在原本的 Stream 物件上繼續操作。