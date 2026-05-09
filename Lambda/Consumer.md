**Consumer 介面**位於 java.util.function 套件中

它**只有一個抽象方法**，這個方法會**接收一個參數**，並且不會回傳任何值

實際看看，這對我們可以使用的 lambda 表達式來說，代表什麼

# Consumer 在幹嘛？

👉 定義一種「只做事，不回傳」的行為

void accept(T t)
有輸入（T）
沒輸出（void）

# 因為它的方法是：
```java
void accept(T t)
```
所以 lambda 會長這樣：
```java
x -> { 做一些事 }
```
例如：

```java
list.forEach(s -> System.out.println(s));
```

# Consumer = 吃進資料，做事情，但不吐東西


| 介面              | 做什麼               |
| --------------- | ----------------- |
| `Consumer<T>`   | 吃資料，不回傳           |
| `Function<T,R>` | 吃資料，回傳結果          |
| `Predicate<T>`  | 吃資料，回傳 true/false |
