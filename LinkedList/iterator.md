一、什麼是 Iterator？
定義：Iterator 是 Java 的一個 介面 (Interface)
用來統一訪問集合 (Collection) 中的元素
而不必暴露集合的內部結構

為什麼需要：
對於 ArrayList，你可以用索引 i 來找人
但對於 LinkedList，你沒有連續的門牌號碼
Iterator 就是專門設計來解決「如何順著鏈結一個一個往後找」的標準工具

二、Iterator 的運作邏輯
可以把 Iterator 想像成一個落在元素之間的「游標（Cursor）」

### 方法
iterator(): 取得 Iterator	游標落在第一個元素 之前
hasNext(): 判斷	看游標後面是否有下一個元素，回傳 true/false
next(): 讀取 & 移動	游標跨過下一個元素，並回傳該元素內容
結束	-	不斷呼叫 next()，直到游標落在最後元素之後，hasNext() → false

## 範例用法
```java
Iterator<String> it = list.iterator();
while (it.hasNext()) {
    String town = it.next();
    System.out.println(town);
}
```
每次 next() → 游標跨過一個元素
hasNext() → 檢查是否還有元素

## 為什麼在 LinkedList 裡，Iterator 比索引快？
 使用索引 get(i) 遍歷
for (int i = 0; i < list.size(); i++) {
    list.get(i);
}
LinkedList 沒有連續記憶體
get(i) → 每次都要從頭開始數到第 i 個元素
每次下一輪都重頭開始
效能：O(n²) => 效能超級差

比喻：每去一個景點，都要回飯店重新出發，非常浪費時間

🔹 使用 Iterator 遍歷
```java
Iterator<String> it = list.iterator();
while (it.hasNext()) {
    it.next();
}
```
Iterator 記住目前位置
next() → 直接指向下一個節點，不用重頭數
效能：O(n) ✅

比喻：導遊帶你一路走下去，不需要回頭