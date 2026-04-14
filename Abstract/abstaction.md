### abstract 的樣子
```java
abstract class Animal {}
```

抽象類別是一種「不完整的類別」
因此，你不能直接建立它的instance

不過，抽象類別仍然可以有 constructor
而這個 constructor 會在**子類別被建立時被呼叫**

抽象類別的目的: 是用來定義子類別必須具備的行為，
所以它一定會參與在「繼承（inheritance）」當中


請假設 Animal 是一個抽象類別

1. 類別可以繼承抽象類別，並且自己是具體類別（concrete class）
```java
// Dog -> concrete class
// Animal ->　abstract class
class Dog extends Animal {}
```


2. 繼承抽象類別的類別，本身也可以是抽象的
```java
// Mammal -> abstract class
// Animal ->　abstract class
abstract class Mammal extends Animal {}
```
3. 抽象類別也可以繼承一個具體類別

```java
// Mammal -> abstract class
// Dog ->　concrete class
abstract class Mammal extends Dog {}
```

## abstract method
```java
abstract class Animal {
    // 這個方法只有「宣告」，沒有「實作內容」，所以它直接用分號結束，而不是像一般方法那樣有大括號 {}。
    // 也就是說，它沒有方法本體（body），連空的大括號都沒有。
    public static void move();
}
```

任何使用 Animal 子類別的程式碼都知道，它可以呼叫 move 方法，而不同的子類別會用自己的方式來實作這個方法

abstract method 因為沒有實作，所以它不能單獨存在，必須放在：

- 抽象類別（abstract class）
- 或 介面（interface）

# 你可能會問：「這有什麼差別？什麼時候要用 abstract class？」


情況一: concrete 繼承 concrete

1️⃣ 直接繼承父類別行為

子類別可以直接使用父類別的方法，不需要自己寫。

👉 代表：完全沿用父類別的行為

2️⃣ 覆寫（override）父類別方法

子類別可以寫一個同樣方法名稱與參數的方法，
但用自己的程式碼，完全取代父類別的行為。

👉 代表：自己重新定義行為

3️⃣ 覆寫但保留父類別邏輯（用 super）

子類別可以在 override 時，呼叫父類別的方法（用 super），
再加上自己的額外邏輯。

👉 代表：在原本行為基礎上加強

情況二:  concrete 繼承 abstract

    當 Animal 是抽象類別，且方法也是抽象的時候，
子類別就不再有剛剛說的那些選擇了。

👉 也就是說：

沒有任何「可直接繼承使用的實作方法」
子類別不能直接拿父類別的程式碼來用
🔹改變後的規則

子類別現在一定要做一件事：

👉 必須自己實作（implement）所有從父類別繼承來的 abstract 方法

例如：

abstract class 有 abstract move()
concrete class 就「一定要自己寫 move() 的程式碼」

🔹 這種設計的好處

✔ 強迫設計者一定要實作必要行為
✔ 避免錯誤的預設邏輯
✔ 確保每個子類別的行為是正確的
