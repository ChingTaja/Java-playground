泛型讓我們可以用更通用的方式來建立與設計類別
而不需要關心它們實際會包含哪一種具體型別的元素
一個很典型的泛型類別例子就是 ArrayList
我們可以用 ArrayList 來儲存任何型別的物件
因為它的許多方法都適用於各種型別

之前有使用過泛型類別，其中兩個例子是 ArrayList 和 LinkedList

Java 支援泛型型別
ex: class、record以及interface
它也支援泛型方法

聽起來很複雜嗎？其實透過一段程式碼來看泛型類別會更容易理解

泛型 class
```java
class Box<T> {
    T value;
}
```


# 最常見的型別參數識別字有：

E：代表 Element（元素，廣泛用於 Java 集合框架）
K：代表 Key（鍵，用於對應類型）
N：代表 Number（數值）
T：代表 Type（型別）
V：代表 Value（值）
S、U、V 等：用於第 2、第 3、第 4 個型別參數

### 泛型的 extend  Upper Bound（上界）
```java
class Team<T extends Player>
```
意思: T 只能是 Player 或 Player 的子類

❌ 為什麼 Team<String> 會報錯？
```java
Team<String> ❌
```
意思: String ❌ 不是 Player
所以 → 超出範圍（not within bounds）

- extends 在這裡的真正意思: 不等於一般 class 的 extends

一般：
`class A extends B` => A 繼承 B

泛型：
`<T extends Player>` => 意思是：T 必須是 Player 或其子類（不是在說 T 去繼承）

 extend xxx => 支援 class / interface
<T extends Player>

👉 Player 可以是：

class ✅
interface ✅

=> 都用 extends（不是 implements！）

### 為什麼要加限制？
原因一：限制型別

避免這種奇怪情況

Team<String> ❌
Team<Integer> ❌

只允許合理的：
`Team<FootballPlayer>` 

 原因二

你可以安全使用 Player 的方法！

例如：
```java
class Team<T extends Player> {
    void print() {
        T player = ...
        player.getName(); // ✅ 一定可以用
    }
}
```

因為編譯器知道：T 一定是 Player！

📌 Java 物件比較：Comparable vs. Comparator
1. Comparable 介面 (自然排序)
核心方法：compareTo(T o)
呼叫方式：由物件實例本身呼叫，與另一個物件進行比較
限制：類別必須實作（implement）Comparable 介面
最佳實踐：若 equals(Object o) 為 true，則 compareTo 應回傳 0（確保自然排序與相等性定義一致）

2. Comparator 介面 (外部比較器)
核心方法：compare(T o1, T o2)
呼叫方式：傳入兩個同類型的物件進行比較
優點：
被比較的物件不需要實作 Comparator 介面
更具彈性，可定義多種不同的排序規則（例如：按姓名排、按年齡排）

3. Arrays.sort 的用法
單一參數 Arrays.sort(array)：陣列元素必須實作 Comparable，否則會噴錯誤
雙參數 Arrays.sort(array, comparator)：**不要求元素實作 Comparable**，會直接根據傳入的 Comparator 規則排序