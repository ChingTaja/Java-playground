# Lambda 單一參數的寫法
1. 最簡寫法（最常見）

```java
element -> System.out.println(element);
```

✔ 特色
只有一個參數
沒有型別
可以省略括號

2. 保留括號

```java
(element) -> System.out.println(element);
```

✔ 特色
括號可有可無
功能與第一種完全相同

3. 指定型別

```java
(String element) -> System.out.println(element);
```

✔ 特色
明確指定型別
括號不能省略

4. 使用 var（Java 11+）

```java
(var element) -> System.out.println(element);
```

✔ 特色
Java 自動推斷型別
仍然需要括號

⚠️ 重要規則
✔ 只有「沒型別」時才能省略括號

✅ 合法：
```java
x -> x + 1
```
✅ 合法：
```java
(x) -> x + 1
```
❌ 不合法：
```java
String x -> x + 1
```
正確：
```java
(String x) -> x + 1 // 有型別時一定要加括號
```

# Lambda 基本結構

```java
(parameters) -> expression
```

或：

```java
(parameters) -> {
    statements
}
```

## Lambda 的目的

用最簡潔的方式==實作 Functional Interface==的唯一方法

Java 會根據：

1. Functional Interface
2. Target Type

來推斷：

- 參數型別
- 回傳型別

所以很多時候：型別可以省略

## Lambda 兩種 Body 寫法
1. 單一 Expression（最簡潔）
```java
x -> x * 2
```

✔ 特色
只有一行
不需要 { }
不需要 return
❌ 錯誤寫法

```java
x -> return x * 2
```

因為：單一 expression 已經會自動回傳

```java
x -> {
    int result = x * 2;
    return result;
}
```

✔ 特色
多行程式
必須使用 { }
必須寫 return

⚠️ Code Block 規則
每行 statement 都要有分號

❌ 錯誤：
```java
x -> {
    int y = x + 1
    return y // 少了分號
}
```

✅ 正確：
```java
x -> {
    int y = x + 1;
    return y;
}
```

# Lambda 可以使用外部變數，但這個變數不能被改變

更準確來說：
1. Lambda 可以「捕捉」外部變數

像 prefix 這種在方法內宣告的變數，可以在 Lambda 裡使用

2. 但條件是：必須是 final 或 effectively final

意思是：

final：明確加上 final，不能改
effectively final：沒有寫 final，但「實際上也沒有被重新賦值」


```java
import java.util.List;

public class Main {
    public static void main(String[] args) {

        String prefix = "nato"; // 沒有被修改 → effectively final

        List<String> list = List.of("alpha", "bravo", "charlie");

        list.forEach(item -> {
            System.out.println(prefix + item);
        });
    }
}
```
✔ 為什麼這個可以？
- prefix 宣告後 沒有再被改過
- 所以 Java 視為 effectively final
- Lambda 可以安全使用


但如果你這樣做就不行：

```java
import java.util.List;

public class Main {
    public static void main(String[] args) {

        String prefix = "nato";

        List<String> list = List.of("alpha", "bravo", "charlie");

        prefix = "abc"; // ❌ 這行讓 prefix 不再是 effectively final

        list.forEach(item -> {
            System.out.println(prefix + item); // ❌ 編譯錯
        });
    }
}
```

## 為什麼 Java 要限制使用外部變數時不能被改變？

重點在於 避免 Lambda 在不同執行時機造成資料不一致

Lambda 可能： 延後執行 (deferred lambda expressions)
在不同 thread 執行

如果外部變數可以變，會出現：
Lambda 用到「已經變掉的值」，導致 bug 很難追

所以 Java 乾脆規定：你可以用，但不能變

## lambda 的參數名稱不能跟外層 scope 的變數或參數衝突

```java
String myString = "enclosing method's my string";
list.forEach(var myString) -> {  //❌  myString 命名衝突
    char first = myString.charAt(0);
}
```
# Lambda 總結
有些人認為
=> Lambda 只是「語法糖（syntactic sugar）」

只是讓寫 anonymous class 更簡潔方便而已，本質沒有新增能力

因為：

Lambda 可以 assign 給變數
也可以當參數傳遞
=> 這些 anonymous class 以前也可以做到

但也有人認為：
=> Lambda 是 Java 進入 functional programming 的第一步

functional programming 的核心是： 專注於「計算與回傳結果」

# streams

它可以建立「處理 pipeline」並串接多個操作

很多 stream API 都接受 functional interface 當參數
=> 所以可以直接用 lambda 寫