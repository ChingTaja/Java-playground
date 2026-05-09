# Functional Interface 是什麼

Functional Interface：

- 只擁有一個 abstract instance method 的 interface
    因為它其實還可以有：
     - default method
     - static method
     - private method（Java 9+）
     - 從 Object 繼承的方法
- Lambda Expression 的目標型別（target type）

```java

/*
@FunctionalInterface 用於檢查該介面是否符合函數式介面的規則（只能有一個抽象方法）

告訴其他開發者：
- 這個 interface 是設計給 Lambda 使用的
- 不要隨便增加 abstract method
*/

@FunctionalInterface 
public interface Operation<T> {

    /**
     * 抽象方法：這是此介面唯一的抽象方法
     * 在使用 Lambda 表達式時，就是在實作這個方法的內容
     */
    T operate(T value1, T value2); // 兩個參數型別相同 , 回傳型別也相同

    /**
     * 預設方法 (Default Method)：
     * 函數式介面可以包含多個預設方法，這不會影響 Lambda 的使用。
     */
    default void log() {
        System.out.println("執行日誌紀錄");
    }

    /**
     * 靜內方法 (Static Method)：
     * 函數式介面也可以包含靜態方法，這同樣不會影響其定義。
     */
    static void print() {
        System.out.println("這是靜態工具方法");
    }
}
```

## 為什麼 Lambda 能對應 Functional Interface 裡面的那個唯一 abstract instance method

因為：
```java
(value1, value2) -> ...
```
Java 需要知道：

- 參數型別
- 回傳型別

而 Functional Interface 的唯一 abstract method
就提供了這份 method contract

Example:
```java
Operation<Integer> add = (a, b) -> a + b;
```

Java 根據 generic type（<Integer>）
把原本的 abstract method 型別具體化後的結果：
```java
Integer operate(Integer value1, Integer value2)
```

Generic Interface（泛型介面）

<T> 代表型別參數

意思是：這個 interface 可以套用到不同型別

```java
Operation<Integer>
Operation<Double>
Operation<String>
```

# Lambda Expression：本質上是對 Functional Interface 的實作

例如：
```java
Operation<Integer> add = (a, b) -> a + b;
```
如果 interface 多了一個 abstract method：

Lambda 就不知道要對應哪個方法，程式會壞掉
    
建立 Interface
```java
@FunctionalInterface
public interface Operation<T> {

    T operate(T value1, T value2);
}
```

使用 Lambda Expression
```java
public class Main {

    public static void main(String[] args) {

        Operation<Integer> add =
                (a, b) -> a + b;

        System.out.println(add.operate(10, 20));
    }
}
```

# IntelliJ 方便的功能


Gutter Icons

位置：
```bash
File
 └─ Settings
     └─ Editor
         └─ General
             └─ Gutter Icons
```
用途：

IntelliJ 會在程式碼左側顯示：

Lambda 對應資訊
Functional Interface 提示
Override/Implement 標記
    
# Generic Method（泛型方法）
Functional Interface 常常本身就是 Generic
```java
Function<T, R>
Consumer<T>
Predicate<T>
```

```java
public static <T> T calculator(...)
```
方法可處理不同型別
`<T>` ->  type parameter (這個方法使用泛型)


# Lambda 自動推斷型別

```java
...
    public static void main(String[] args) {
...
        // 型別是根據：5, 2 , 推斷成：Integer
        int result = calculator((var a, var b) -> a + b,5, 2);
        var result2 = calculator((a, b) -> a / b, 10.0, 2.5);
        var result3 = calculator(
                (a, b) -> a.toUpperCase() + " " + b.toUpperCase(),
                "Ralph", "Kramden");

    }

    public static <T> T calculator(Operation<T> function, T value1, T value2) {

        T result = function.operate(value1, value2);
        System.out.println("Result of operation: " + result);
        retun result;
    }
...
``