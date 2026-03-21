測試一個物件或實體的型別


```java
unknownObject instanceof Adventure;
((Adventure) unknownObject).watchAdventure();
```
-> 重要的是要看到 Adventure 沒有加引號
意味著我不是在測試型別名稱（字串）
而是在測試型別本身


不同版本的 instance operateor(JDK 16)
pattern matching support for the instance of operator
JVM 能夠識別物件符合該型別，它就可以從物件中提取數據而無需轉型

```java
unknownObject instanceof ScienceFiction syfy;
syfy.watchScienceFiction();
```