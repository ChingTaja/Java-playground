比 POJO 更佳的替代方案：record。

record 在 JDK 14 引入，並在 JDK 16 正式成為 Java 的一部分。

它的目的是 取代 POJO 的樣板程式碼，但同時更加受限

Java 稱 record 為 "plain data carriers"

這裡的 “carrier” 很重要
意味著 record 內建了比 POJO 更多的規則

record 是一種特殊的 class，用來存放**不應被更改的資料**

換句話說，它希望實現成員資料的 **不可變性（immutability**

record 只包含最基本的方法
例如建構子（constructor）和存取方法（accessors / getters）

最棒的是
開發者不需要自己撰寫或生成這些程式碼


```java
public record Student(String id) //() 裡面的叫做 record header 
```
`record header` 由 `record components`組成
是一個以逗號分隔的元件列表

`record components`會自動生成：
1. 一個與元件同名、且型別與元件相同的 field

- 這些欄位就是括號中設定的欄位
- 這些欄位會成為 record 的欄位
- 欄位會被宣告為 private 和 final
- 這些欄位有時也稱為 component field

2. toString() 方法
Java 會自動生成這個方法，用來以格式化的字串列印每個屬性

3. 存取方法（accessor method）
- 對每個元件生成一個 public 的存取方法
- 方法名稱與元件名稱及型別相同
但不會像傳統 getter 那樣加上 get 
e.g. 對於 id 元件
存取方法就是直接叫做 id()

record **沒有 setter 方法**
而且設計上就是不允許有 setter。
原因是 record 的核心設計目標是**immutable**

為什麼 record 被設計成不可變（immutable）？

因為在很多情況下
immutable data transfer objects 很常被使用
而且能讓資料保持良好的封裝性encapsulation

你會希望保護資料，避免它被意外修改（unintended mutations）

如果需要在 class 中修改資料
那麼就不會使用 record

但如果你的情境是：

從 資料庫（database）
或 檔案來源（file source）讀取大量資料
然後只是把這些資料在程式中傳遞來傳遞去
那麼 record 就是一個很大的優勢