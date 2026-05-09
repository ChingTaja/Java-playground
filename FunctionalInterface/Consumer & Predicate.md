# Consumer / BiConsumer 應用

Consumer：單一參數、無回傳值
`void accept(T t)`
    
BiConsumer：兩個參數、無回傳值
`void accept(T t, U u)`
常用於「執行動作但不回傳結果」


# Lambda 可以存入變數 , 但不會自動執行，必須透過 method 或 functional method 呼叫
```java
BiConsumer<Double, Double> p1 = (lat, lon) -> ...
```
不會自動執行
必須透過 .accept() 或傳入方法觸發

# Predicate 概念
- 接收 1 或 2 個參數
- 回傳 boolean
- 用於條件判斷（filter / test）

| Interface Name | Method Signature | 輸入參數數量 | 用途說明 |
|--------------|------------------|-------------|----------|
| Predicate | boolean test(T t) | 1 個 | 測試單一對象是否符合特定條件 |
| BiPredicate | boolean test(T t, U u) | 2 個 | 測試**兩個不同型態（或相同）**的對象之間的關係或條件 |

Lambda 的本質是「把行為當成資料傳遞」
讓方法可以接受不同的執行邏輯
而不需要改寫方法本身

