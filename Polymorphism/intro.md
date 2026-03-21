多型 = 同一個型別，但執行不同的行為
編譯時與執行時型別不同

#  多型 (Polymorphism) 的優勢
- **通用性 (Generality)**：你可以用一個父類別變數來處理所有子類別物件
- **可擴充性 (Extensibility)**：
  - 如果你今天想新增一個 `Documentary` (紀錄片) 類別，你只需要修改 `getMovie` 工廠方法
  - **Main 方法的程式碼（呼叫端）完全不必動**。它依然是 `movie.watchMovie()`，但卻能跑出紀錄片的邏輯

多型的三大要素：

1. Inheritance：子類別繼承父類別（或實作介面
2. Override：子類別重新定義父類別的方法
3. 向上轉型（Upcasting）：用父類別型別來宣告物件變數
