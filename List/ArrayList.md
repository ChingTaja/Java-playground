# 常見的類別實作 List：

AbstractList
ArrayList
LinkedList
Stack
CopyOnWriteArrayList
=> 這些「都是 List 的實作者」

# ArrayList
Resizable Array（可變大小的陣列）

- 為什麼可以變大？
內部其實是：
一個「比較大的陣列（capacity）」
同時記錄：size（實際使用數量）

- 背景`自動調整 capacity`

當元素變多：超過 capacity
系統會：自動建立更大的陣列,把資料搬過去