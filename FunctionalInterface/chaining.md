鏈式組合（Chaining）

| 方法        | 意義           |
| ----------- | -------------- |
| `andThen()` | 先做我，再做你 |
| `compose()` | 先做你，再做我 |


# 沒有 compose
1. IntFunction
2. DoubleFunction
3. LongFunction

因為 primitive 型別不容易做泛型 chaining

# Function chaining 核心:
每個 function只需要:
前面的輸出 = 後面的輸入
中間型別不需要全部一樣

# 在 Function 類型的介面中
像是 andThen() 與 compose()，最大的特色是「中間型別不需要完全相同」。因為 chaining 的本質是：

前一個函式的輸出
→ 成為下一個函式的輸入
Example: 
```java
Function<String, Integer>
Function<Integer, Boolean>
```

# Consumer 的 chaining 則完全不同

因為 Consumer<T> 沒有回傳值，它只有：
```java
void accept(T t)
```
所以 andThen() 並不是在傳遞資料結果，而是在「串接動作」

先執行 consumer1
再執行 consumer2
```java
Consumer<String> c1 = s -> System.out.println(s);
Consumer<String> c2 = s -> System.out.println(s.toUpperCase());

c1.andThen(c2).accept("java");
```

# Predicate 的 chaining 又是另一種思維

因為 Predicate<T> 永遠回傳 boolean
所以它的 chaining 本質是在組合條件判斷
```java
p1.and(p2)
```
等同於
```java
p1 && p2
```


| Function   → 資料轉換
| Consumer   → 動作串接
| Predicate  → 條件組合
| Comparator → 排序組合