# POJO 是一個「簡單的 Java 物件」
通常只包含 實例變數（instance fields）

1. 用途：儲存資料，並在不同功能類別之間傳遞資料。

2. POJO 通常 很少方法，主要只有 getter / setter 或建構子

3. 別名：JavaBean、Bean、Entity、DTO（Data Transfer Object）

JavaBean = POJO + 額外規則（方便 Java 框架操作）
Entity = 與資料庫實體對應
DTO = 僅作為資料傳遞的物件

# POJO 特點
資料導向：主要用來保存資料，較少使用方法
可傳遞：可以在程式不同部分傳遞資料
可被框架使用：像資料庫框架、檔案讀寫框架等會用 POJO 來操作資料。

建構子與 Getter/Setter：
POJO 通常有建構子（constructor）用來初始化資料
Getter / Setter 提供讀寫屬性的方法