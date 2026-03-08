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