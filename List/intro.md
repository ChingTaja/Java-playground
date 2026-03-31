# 為什麼需要 List
Array（陣列）
✅ 可儲存「相同型別」資料
❌ 長度固定（不能動態改變）

限制：新增 / 刪除資料不方便

# Collections（集合）
Java 提供的資料結構工具庫
是 Array 的進階版

1. 可以動態改變大小
2. 操作更方便（新增 / 刪除 / 查找）

# List 是什麼
一種 Collection（集合）
有順序（ordered）
可重複（duplicate allowed）

# 常見 List 類別

1. ArrayList
底層：動態陣列
特點：
查詢快（index 存取快）
插入 / 刪除慢（需要搬移資料）

2. LinkedList
底層：鏈結串列
特點：
插入 / 刪除快
查詢慢（要一個一個找）
