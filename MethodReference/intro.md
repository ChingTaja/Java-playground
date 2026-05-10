Method Reference它是： Lambda Expression 的簡化寫法

當 lambda expression 只是：

```java
s -> System.out.println(s)
```

這種：

> 「收到參數後，直接呼叫既有方法」

的情況

Java 可以改寫成：

```java
System.out::println
```
# 基本語法
```java
類別或物件::方法名稱
```

# 目的
Method Reference 的目的：

- 減少重複程式碼
- 提高可讀性
- 讓 lambda 更簡潔


# Lambda 與 Method Reference 對照

## 一般 Lambda

```java
list.forEach(s -> System.out.println(s));
```

意思：

- s 是參數
- 將 s 傳給 println

## Method Reference

```java
list.forEach(System.out::println);
```

效果完全相同

##  為什麼可以省略參數？

因為 Functional Interface 已經定義好了方法：

```java
Consumer<T>
```

其方法是：

```java
void accept(T t)
```

所以 Java 自動推斷：

```java
s -> System.out.println(s)
```


等同於：

```java
System.out::println
```
# 為什麼可以簡化？

因為：

```java
s -> System.out.println(s)
```

其實只是：

> 「把參數直接傳給既有方法」

沒有額外邏輯

所以 Java 可以縮寫成：

```java
System.out::println
```

# IntelliJ 為什麼會提示？

當 IntelliJ 發現：

```java
s -> System.out.println(s)
```

這種：

> 「單純轉呼叫方法」

的 lambda 時，

就會提示：

```text
Can be replaced with method reference
```

因為：

```java
System.out::println
```
更簡潔、更容易閱讀

# Method Reference 本質上還是 Lambda

Method Reference 並不是新功能

底層本質仍然是：

- Functional Interface
- Lambda Expression

只是語法更短


# 常見用途

## forEach

```java
list.forEach(System.out::println);
```

---

## sort

```java
list.sort(String::compareToIgnoreCase);
```

---

## constructor reference

```java
Supplier<ArrayList<String>> s = ArrayList::new;
```

等同於：

```java
() -> new ArrayList<String>()
```

---

Method Reference ： 而不是重新撰寫 lambda expression

> 「直接把既有方法當成行為傳入」

# 是否代表可以使用任何方法來做 method reference？

不行 !!!

可以使用的方法取決於 lambda expression 的上下文

也就是：

==method reference 取決於 target functional interface 的方法定義==

Java 怎麼判斷一個 method reference 能不能用
你可以：

- 參考 class 的 static method
- 參考 instance method（外部物件）
- 參考作為參數傳入的物件方法
- 參考 constructor（使用 new）


例如 `System.out::println`，

就是一個 instance method reference，

而這個 instance 是外部的物件。

`System.out` 回傳的是 PrintStream 物件

# Method Reference 必須依賴 Functional Interface


例如：

```java
list.forEach(System.out::println);
```

背後其實是：

```java
Consumer<String>
```

因此 method reference 必須符合：

```java
void accept(String s)
```

# 三種常見 Method Reference 類型

### (1) Static Method

```java
Integer::parseInt
```

等同於：呼叫 Integer 類別的 parseInt 方法

```java
s -> Integer.parseInt(s)
```

---

### (2) Instance Method（外部物件）

```java
System.out::println
```

等同於：System.out 是 System 類別裡的一個「static 欄位」，
這個欄位的型別是一個物件（PrintStream）

```java
s -> System.out.println(s)
```

---

### (3) Constructor Reference

```java
ArrayList::new
```

等同於：把「建立方式」當參數傳出去

```java
() -> new ArrayList()
```

# method reference 本身不會執行

它只是「描述一個行為」，真正執行要等 get() 被呼叫


# 四種 method reference 類型，包含範例與對應 lambda expression

1. 靜態方法（Static Method Reference）
- 語法:
```
ClassName::staticMethodName
```
- 範例:
```
Integer::sum
```
- Lambda 對應
```
(p1, p2) -> p1 + p2
```
- 重點
不需要 object
直接用 class 呼叫

2. 特定物件的實例方法（Bounded）
語法:
```
object::instanceMethod
```
- 範例
```
System.out::println
```
- Lambda 對應
```
p1 -> System.out.println(p1)
```

- 重點
object 已經固定（bound）
方法永遠由同一個物件執行

3. 任意物件的實例方法（Unbounded）
語法:
```
ClassName::instanceMethod
```

- 範例:
```
String::concat
```
- Lambda 對應
(p1, p2) -> p1.concat(p2)

- 重點
object 沒有固定
第一個參數 = 物件本身（receiver） --> p1

4.  建構子（Constructor Reference）
```
ClassName::new
```

- 範例:
```
LPAStudent::new
```
- Lambda 對應
```
() -> new LPAStudent()
```
- 重點
用來「建立物件」
等同於 new，但延遲執行

🎯 總整理（記憶用）
1. Static
Class::method → 沒有 object
2. Bound instance
object::method → 固定 object
3. Unbound instance
Class::method → 第一個參數是 object
4. Constructor
Class::new → 建立物件


# 這個方法，能不能剛好對應某個 functional interface 的 input/output？

① Static method（靜態方法）
長這樣：
```java
ClassName::staticMethod
```

例子：
```java
Math::abs
```
等同於 lambda：
```java
x -> Math.abs(x)
```
對應 interface：

型態	範例
Supplier	❌（通常不適合）
Predicate	✔ x -> boolean
Function	✔ x -> y

👉 重點：

static method = 完全照「參數進 → 回傳」

② Constructor（建構子）
長這樣：
```java
ClassName::new
```

例子：
```java
ArrayList::new
```
等同於
```java
() -> new ArrayList()
```
或：
```java
x -> new ArrayList(x)
```
對應：
Supplier	new 無參數物件
Function	用參數建立物件

👉 重點：

new 就是「回傳一個新物件」

③ Bounded Receiver（已綁定物件）

👉 物件已經存在

長這樣：
```java
instance::method
```
例子：
```java
System.out::println
```
等於：
```java
x -> System.out.println(x)
```

對應 interface：
interface	例子
Consumer	println
Function	❌ 很少

👉 重點：

已經有 receiver（System.out）

④ Unbounded Receiver（未綁定物件）🔥你最困惑的
長這樣：
```java
ClassName::instanceMethod
```

例子：
String::length
List::clear
String::concat

🔥 規則

👉 第一個參數 = 呼叫方法的物件（receiver）

例子 1：String::length
```java
String::length
```

等同：

(String s) -> s.length()

✔ input = s
✔ output = int

例子 2：List::clear
```java
List<String>::clear
```

等同：
```java
(list) -> list.clear()

例子 3：String::concat
```java）
BinaryOperator<String> b = String::concat;
```
等同：

(a, b) -> a.concat(b)

👉 第一個參數 = receiver（a）



### 以下是 Two Arguments
這些介面型別擁有兩個參數，因此在這些情況下，更常看到「無界接收者」類型的方法引用被使用

| Method Reference    | Java 真正理解方式                |
| ------------------- | -------------------------- |
| `String::equals`    | `(a, b) -> a.equals(b)`    |
| `List::add`         | `(list, x) -> list.add(x)` |
| `String::concat`    | `(a, b) -> a.concat(b)`    |
| `String::compareTo` | `(a, b) -> a.compareTo(b)` |


對應 interface
1.  BiPredicate<T,U>
需求：
```java
(T, U) -> boolean
```
例子
```java
BiPredicate<String, String>
```
搭配：
```java
String::equals
```
Java 變成：
```java
(a, b) -> a.equals(b)
```
回傳 boolean 

2. BiConsumer<T,U>
需求：
```java
(T, U) -> void
```
例子
```java
BiConsumer<List<String>, String>
```

搭配：
```java
List::add
```
變成：
```java
(list, x) -> list.add(x)
```

回傳 void 

3. BiFunction<T,U,R>
需求：
```java
(T, U) -> R
```
例子 1
```java
BiFunction<String, String, String>
```
搭配：
```java
String::concat
```
變成：
```java
(a, b) -> a.concat(b)
```

例子 2（static）
```java
Integer::sum
```
變成：
```java
(a, b) -> Integer.sum(a, b)
```