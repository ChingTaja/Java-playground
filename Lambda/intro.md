Lambda 表達式是在 Java JDK 8 中引入的功能

現在已經成為 Java 中非常重要的一部分

並且被廣泛應用在各種 interface 和 class 的方法中
    
# Lambda 是什麼？

Lambda 是用來實作一種特別的介面： Functional Interface
    
    
# 什麼是 Functional Interface？

只包含「一個抽象方法」的 interface

```java
interface MyFunction {
    int apply(int x);
}
```

這讓 Lambda 可以用來替代它。

# Lambda 的好處
✔  程式更簡潔

不需要寫完整 class 或匿名類別

✔  可讀性更好

邏輯更直觀

✔  可以把「程式碼當參數傳遞」

例如：

- 把一段邏輯傳給方法
- 動態決定行為
    
```java
sort(list, (a, b) -> a.getName().compareTo(b.getName()));
sort(list, (a, b) -> a.getAge() - b.getAge());
```

 
# 實際對照

傳統寫法（匿名類別）
    
    
```java
MyFunction f = new MyFunction() {
    @Override
    public int apply(int x) {
        return x * 2;
    }
};
```
    
=> Lambda 寫法

```java
MyFunction f = x -> x * 2;
```
    
👉 少掉：

class
method 名字
return（有時）

# Lambda 長怎樣
    
| 部分         | 意思        |
| ---------- | --------- |
| `(o1, o2)` | 參數        |
| `->`       | 箭頭（arrow） |
| 右邊         | 要做的事情（邏輯） |

# Java 並不是「看 lambda 本身」知道方法，而是靠「型別」推斷的

關鍵：目標型別（Target Type）


當你寫 lambda 的時候，Java 會看「你這段 lambda 被放在哪裡」，來決定你是在實作哪個方法


Java 怎麼知道你在寫哪個方法

lambda

這題其實是 Java Lambda 的核心觀念：
Java 並不是「看 lambda 本身」知道方法，而是靠「型別」推斷的。

🔑 關鍵：目標型別（Target Type）
當你寫 lambda 的時候，Java 會看「你這段 lambda 被放在哪裡」，來決定你是在實作哪個方法。

因為 lambda 只能對應到「函數式介面（Functional Interface）」
也就是「只有一個抽象方法」的介面

# Example 1
```java
Comparator<Person> c = (p1, p2) -> p1.getAge() - p2.getAge();
```
Java 怎麼知道 (p1, p2) 是什麼？

 因為左邊是 `Comparator<Person>`

而 Comparator 這個介面裡只有一個方法：

```java
int compare(T o1, T o2);
```

Lambda 沒有「方法名」
他是靠「上下文型別」來決定在實作哪個方法


# Functional Interface 的定義
只有 一個 abstract method（抽象方法） 的 interface
這個唯一的方法，就是 Lambda 對應的目標方法

只要多一個 abstract method，就**不能**當 functional interface（Lambda 就無法推斷）

#### SAM（Single Abstract Method）
functional interface 也叫 SAM interface
SAM = 單一抽象方法
這個方法也被稱為 functional method


#### Lambda 為什麼 Java 能推斷參數與回傳值？

方法「長什麼樣子」

因為 functional interface 已經「定義好方法長相」
- 參數是什麼
- 回傳型別是什麼

# Java 用 method signature 來「辨識方法」

method signature = 方法名稱 + 參數型別（順序也重要）
❌ 不包含回傳型別、參數名稱


#### Functional Interface 是 Lambda 的 target type
Lambda 不能單獨存在
一定要「賦值或傳入」某個 functional interface

例如：
```java
Comparator<String> c = (a, b) -> a.compareTo(b);
```

👉 重點：

Comparator 就是 functional interface，Lambda 是它的實作



結論： Lambda ≠ 新語法功能，而是「functional interface 的簡寫實作」
    
✔ Functional Interface 條件
只能有 1 個 abstract method（包含繼承來的）

# 多參數 Lambda 要加括號

正確：
```java
(a, b) -> a + b
```
錯誤：
```java
a, b -> a + b
```

# var 不能混用

正確：

```java
(var a, var b) -> a + b
```

錯誤：

```java
(Integer a, Integer b) -> a + b
```

# Generic 不支援 Primitive Type

Generic = 只支援 Reference Type（物件）

正確寫法
```java
List<Integer> list = new ArrayList<>();
List<Double> list2 = new ArrayList<>();
```

為什麼不能用 primitive？

因為：
Generic 是「編譯時型別機制」
JVM 需要「Object 型別」來處理

Java 會自動： Auto Boxing & Auto Unboxing

# Lambda 的強項

可以把：

「行為（Behavior）」當參數傳入 ==> 函數式程式設計（Functional Programming）

例如下面的 method：
```java
(a, b) -> a + b
(a, b) -> a / b
(a, b) -> a.toUpperCase() + b.toUpperCase()
```
因此：同一個方法 , 能執行完全不同邏輯
    
✔ 不是傳「結果」
✔ 也不是傳「固定方法」
✔ 而是傳「運算規則」

# Lambda  參數規則
| 形式  | 寫法                      |
| --- | ----------------------- |
| 無參數 | () -> value             |
| 單參數 | x -> value              |
| 多參數 | (x, y) -> value         |
| 有型別 | (int x) -> value        |
| var | (var x) -> value（需全部一致） |

1️⃣ 無參數（None）
```java
() -> statement
```

✔ 規則：
一定要有 ()
不能省略括號

2️⃣ 單一參數（One parameter）
```java
s -> statement // 可以省略括號（最常見）
(s) -> statement  // 也可以加括號（合法但多餘）
(var s) -> statement // 可以用 var
(String s) -> statement // 可以指定型別
```

⚠️ 重點規則

👉 只有「單一參數」時才能省略括號

3️⃣ 多參數（Two or more）
```java
(s, t) -> statement
(var s, var t) -> statement
(String s, List t) -> statement
```
✔ 規則整理：

一定要有括號 ()
⚠️ 一致性規則（很重要）

👉 如果有「型別 / var」，每個參數都要一致

❌ 錯誤寫法
(var s, t) -> statement   // ❌ 錯
(String s, var t) -> statement // ❌ 錯

✅ 正確寫法
(var s, var t) -> statement
(String s, String t) -> statement
(s, t) -> statement