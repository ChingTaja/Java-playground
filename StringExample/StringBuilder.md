String v.s String Builder


這兩者的差異主要體現在記憶體管理與運算效率

## 1. String：產生新物件 (New Object Creation) - Immutable

當你對 `String` 進行操作（如 `concat`, `replace`, `substring`）時：

- 記憶體行為：JVM 會在記憶體中建立一個**全新**的字串物件
    
- 回傳值：方法回傳的是指向這個**新物件**的參考（Reference）
    
- 操作要點：必須使用**賦值（Assignment）**來接住回傳值，否則操作結果會遺失

- 缺點：在大量拼接字串時（如迴圈中），會產生大量無用的暫時物件，消耗記憶體並增加垃圾回收 (GC) 負擔
    
錯誤做法：`myString.concat("!");` (原字串不變，新字串被丟棄)
正確做法：`myString = myString.concat("!");`

---

## 2. StringBuilder：自我參考 (Self-Reference / This) - Mutable

當你對 `StringBuilder` 進行操作（如 `append`, `insert`, `delete`）時：

- 記憶體行為：直接在**原始物件**的記憶體空間進行修改（In-place modification）
    
- 回傳值：方法回傳的是 **`this`（自我參考）**，也就是指向**同一個物件**的參考。
    
- 操作要點：不需要重新賦值，原始變數所指向的內容就已經更新了

- 優點: 效能極高，特別適合用於需要頻繁修改字串內容的場景
    
---

## 3. 為什麼 StringBuilder 要回傳自己？ (Method Chaining)

既然 `StringBuilder` 會直接修改原始物件，為什麼還要回傳一個參考呢？
這是為了支援**「方法鏈（Method Chaining）」**，讓程式碼更簡潔美觀

String **字串是不可變的（immutable）** 
 每次呼叫方法都會回傳一個新的字串實例

作為替代方案
Java 提供了一個 **可變（mutable）的類別**，讓我們可以改變它的文字值
這就是 **StringBuilder Class**

4個 new StringBuilder的方式

```java
// 1. 傳入字串  
StringBuilder sb1 = new StringBuilder("Hello");  
  
// 2. 無參數  
StringBuilder sb2 = new StringBuilder();  
  
// 3. 傳入整數（設定初始容量）  
StringBuilder sb3 = new StringBuilder(50);  
  
// 4. 傳入其他 charater sequence（例如 sb1）  
StringBuilder sb4 = new StringBuilder(sb1);
```
