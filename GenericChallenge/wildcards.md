### 先回答這個問題：為什麼 List<Student> 不能放 List<Graduate>？

## 1. 核心觀念：泛型是不變的 (Invariant)

在 Java 中，雖然 Graduate（研究生）是 Student 的子類別，但 List<Graduate> 並不是 List<Student> 的子類別。

可以賦值：
```
Student s = new Graduate(); (多型)
```

不可以賦值：  
```
List<Student> list = new ArrayList<Graduate>(); (編譯錯誤！)
```
---

## 2. 為什麼這樣設計？（安全性考量）

假設 Java 允許這種賦值，會發生可怕的事：

```java
List<Graduate> graduates = new ArrayList<>();
List<Student> students = graduates; // 假設這行可行

students.add(new ElementaryStudent()); // 小學生也是 Student，合法
// 慘了！現在你的「研究生清單」裡面混進了一個「小學生」
// 當你執行 graduates.get(0).research() 時，程式就會崩潰！
``` 
    
請謹慎使用程式碼

為了防止這種「髒資料」混入
Java 在編譯階段就會擋下它

3. 快速記法：精準匹配

宣告為 `List<Student>`：
就像一個「只能裝 Student 標籤」的容器。你只能給它 `ArrayList<Student>` 或 `LinkedList<Student>`

裡面裝什麼：
雖然清單必須是 List<Student>，但清單內部還是可以透過 add() 加入 Graduate 物件，因為 Graduate 確實是一個 Student
    
    
4. 如果我真的想接收「任何學生子類別」的清單呢？
你需要的語法叫做 「Wildcards」：
語法： 
```java
List<? extends Student>
```

意思： 「這是一個 List，裡面裝的是 Student 或任何它的子類別」。
代價： 使用 ? extends 後，為了安全，你將無法再往這個 list 裡面 add() 任何東西（只能讀取）



# 如何讓方法「自己擁有泛型能力」，而不是依賴 class 的泛型

方法一  generic method


![image](https://hackmd.io/_uploads/HkiYxoap-g.png)

1️⃣ type parameter 的位置

👉 放在：

修飾子之後、回傳型別之前
```
public <T> T method(T input)
```

👉 重點是這個 <T>
T 是「方法自己的型別參數」

2️⃣ 可以使用的地方

泛型方法的 type parameter 可以用在：

方法參數 ✔
回傳型別 ✔
方法內部 ✔

👉 和 class 泛型用法類似

 3️⃣ 泛型方法用途
✔ 用在 Collections
避免 raw type
增加彈性

為什麼我們需要泛型方法（generic method）來處理集合?
```java
public static <T> void printList(List<T> list) {
    for (T item : list) {
        System.out.println(item);
    }
}

List<String> names = List.of("A", "B", "C");
printList(names);

List<Integer> nums = List.of(1, 2, 3);
printList(nums);

```
👉 同一個方法，通吃不同型別


✔ 用在 static 方法

👉 static 方法 不能用 class 的 T

所以要用：
```java
static <T> void method(T value)
```
static 方法不能用「class 等級的泛型 T」
只能自己在方法上重新宣告 <T>


🔥 1️⃣ 什麼是「class 的 T」？
```java
class Box<T> {
    T value;
}
```
👉 這個 T 是「整個 class 的型別」

你可以這樣理解：

T = 這個 Box 裡面裝什麼（整個類別決定）

⚠️ 2️⃣ static 為什麼不能用 class T？
```java
class Box<T> {

    static void print(T value) { // ❌ 錯
    }
}
```
❌ 為什麼錯？

因為：

👉 static 是「屬於 class 本身」
👉 但 T 是「屬於物件 instance」


🔥 3️⃣ 正確寫法（泛型方法）
```java
class Box<T> {

    static <T> void print(T value) {
        System.out.println(value);
    }
}
```
🧠 這裡的 <T> 是什麼？

👉 注意！！這個 T 跟 class 的 T 不是同一個

可以理解成：
class T = 整個盒子的型別
method T = 這個方法自己用的型別


# Wildcard 
wildcard (?) 讓泛型可以接受「多種型別」，而不是固定一種

萬用字元（wildcard）只能用在 **型別引數（type argument）** 中
而不能用在型別參數（type parameter）的宣告中
萬用字元用 ? 表示，代表「未知的型別」

1. 先釐清兩個詞
✔ type parameter（型別參數）：設計時的空格

就是「定義泛型時用的變數」

```java
class Box<T> { }
```

這裡的 T 是型別參數（你在「設計盒子」時還不知道裡面裝什麼）

✔ type argument（型別實際參數）：使用時填進去的答案

就是「使用泛型時填進去的型別」

```java
Box<Integer> box = new Box<>();
```

這裡的 Integer 是型別實參（你已經決定盒子裝整數）

2. 為什麼只能用在「type argument」？

❌ 不能這樣寫：
```java
class Box<?> {} // 錯
```

因為：

T 是「你在設計類別時要命名的變數」
? 不是命名，它是「匿名的未知型別」
Java 不允許你用「未知」來當作設計參數名稱

- 萬用字元（wildcard）不能用在泛型類別的實例化（instantiation）中

```java
var myList = new ArrayList<?>(); ❌ 編譯錯誤
```

# unbounded / upper / super
1.  unbounded wildcard（無界）

```
Box<?> box;
```
👉 意思：

裡面是某種型別，但我不知道是什麼

限制
```java
box.setValue("hello");  // ❌ 不行
Object obj = box.getValue();  // ✔ 可以
```
👉 只能讀，不能寫

2. upper bound（上界）
```java
Box<? extends Number> box
```

👉 意思：

裡面是 Number 或其子類（Integer, Double...）

範例
```java
Box<Integer> intBox = new Box<>();
Box<? extends Number> box = intBox;
```

限制
```java
box.setValue(123);  // ❌ 不行
Number n = box.getValue();  // ✔ 可以
```
👉 還是不能寫！（因為可能是 Double）

4️⃣ lower bound（下界）
Box<? super Integer> box;

👉 意思：

裡面是 Integer 或其父類（Number, Object）

範例
```java
Box<Number> numBox = new Box<>();
Box<? super Integer> box = numBox;
```

限制
```java
box.setValue(123);  // ✔ 可以
Object obj = box.getValue();  // ✔ 但只能當 Object
```
👉 可以寫，但讀出來只能當 Object

# 不能同時有上下界


❌ 錯誤：
```
Box<? extends Number super Integer> box;  // ❌
 ```

不合法

👉 Java 不允許這種寫法


# 總結

? → 只能讀
? extends T → 安全讀（當 T）
? super T → 安全寫（放 T）
