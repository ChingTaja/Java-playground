Java 在 java.util.function 套件中
提供了一整套 functional interface（函數式介面）庫


# Java 的 functional interfaces 可以分成四大類

| Interface Category| asic Method Signature | Purpose | 核心邏輯 |
|-----------------|-----------------|------------|----------|
| Consumer | void accept(T t) | 執行程式碼但不回傳任何數據 | 只進不出：單純消耗 (消費) 傳入的參數 |
| Function | R apply(T t) | 回傳運算或函數處理後的結果(轉換資料) | 一進一出：將輸入的資料轉換為另一種型態或結果。 |
| Predicate | boolean test(T t) | 測試一個條件是否為真或假 | 判斷真偽：用於過濾 (Filter) 或邏輯檢查 |
| Supplier | T get() | 回傳某個東西的實例(用於提供物件，不接收參數) | 只出不進：像工廠一樣提供 (供應) 資料|

# Lambda 有兩種常見使用方式

✔ 方式一：宣告成變數
```java
Function<String, String> func =
        s -> s.toUpperCase();
```
👉 Lambda 被存進變數

✔ 方式二：直接傳給 method
```java
list.forEach(s -> System.out.println(s));
```
👉 Lambda 直接當參數