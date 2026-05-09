nonymous class 是沒名字的 local class
# 基本概念

匿名類別是一種：

沒有名稱的類別
在單一敘述中宣告 + 建立實例
通常用來：
1. 實作 interface
2. 繼承 class 並立即覆寫方法


```java
var c4 = new comparator<StoreEmployee>(){}
```

# new 後面的「型別」是什麼？

這裡的 `Comparator<StoreEmployee>``：

❌ 不是匿名類別本身的名字（因為它沒有名字）
✅ 是匿名類別的 父型別
可以是 interface
或 superclass

# 實際意思

等同於：
```java
    class AnonymousClass implements Comparator<StoreEmployee> {
    @Override
    public int compare(StoreEmployee o1, StoreEmployee o2) {
        return o1.getName().compareTo(o2.getName());
    }
}
```

# 使用時機 👉 用來「一次性的行為」

匿名類別適合：

只用一次的行為（one-off implementation）
event handler / callback
快速實作 interface

# 現代替代寫法（Lambda）

如果是 functional interface（只有一個 abstract method）：
```java
Comparator<StoreEmployee> c4 =
    (o1, o2) -> o1.getName().compareTo(o2.getName());
```

2️⃣ 兩種使用方式
🔹 (1) 實作 Interface
```java
var c4 = new Comparator<StoreEmployee>() {
    @Override
    public int compare(StoreEmployee o1, StoreEmployee o2) {
        return o1.getName().compareTo(o2.getName());
    }
};
```

✔ 意思：

- 建立一個「匿名 class」
- implements Comparator<StoreEmployee>

🔹 (2) 繼承 Class

```java
var e1 = new Employee() {
    @Override
    public String toString() {
        return "Custom Employee";
    }
};
```
✔ 意思：

- 建立一個匿名 class
- extends Employee
- 可以 override 方法


# 重要觀念
🔹 new 後面的型別是什麼？

```java
var c4 = new Comparator<StoreEmployee>() { ... };
var e1 = new Employee() { ... };
```

👉 這些都不是匿名 class 的名字
👉 而是：

interface（要實作）
或 superclass（要繼承）

# 重要的語法細節
✔ 一定要有分號 ;
var c4 = new Comparator<StoreEmployee>() { ... };
var e1 = new Employee() { ... };
❗ 為什麼要分號？

因為這不是：

❌ class declaration（類別宣告）

而是：

✅ expression（表達式）
👉「建立物件並賦值」


# 特色
1. 沒有 class 名稱
2. 直接「邊寫邊 new」
3. 只能用一次

```java
Runnable r = new Runnable() {
    @Override
    public void run() {
        System.out.println("Hello");
    }
};
```

# 為什麼現在比較少用？

因為 Lambda 更簡潔
```java
Runnable r = () -> System.out.println("Hello");
```

# 還是要學的原因

✔ 舊 code 還會看到
✔ 某些情況 lambda 做不到
✔ 幫助理解 JVM / functional interface
