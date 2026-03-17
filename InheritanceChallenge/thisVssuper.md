# This v.s. super

1. Keywords Overview

super: Used to access `parent class members` (variables and methods).

this: Used to access `current class members` (variables and methods).

2. When to Use this

Required when method parameters or constructor parameters have the same name as instance variables.

Helps to distinguish instance variables from local variables.

3. Usage Restrictions

Cannot use this or super in static contexts (e.g., static methods).

Doing so will cause compile-time errors.

4. this() and super() Method Calls

Adding parentheses (()) converts the keywords into `constructor calls`:

this() → Calls another constructor in the `same` class.

super() → Calls a constructor of the `parent` class.

The Java compiler puts a `default call to super()` parentheses if we don't add it, and it's always a call to the no argument constructor, which is inserted by the compiler.
 In other words, a call to the constructor that `hasn't got any arguments.`

The call to super() parentheses must be the `first statement` in each constructor. 
And importantly, A constructor can have a call to super() parentheses or this() parentheses, but `never both.` 

❌ 錯誤寫法（兩個一起用）
```java
public Rectangle() {
    this(0, 0);   // ❌
    super();      // ❌ 不能同時存在
}
```

不管我呼叫哪一個建構子
變數最終都會在第 3 個建構子中被初始化

這種方式稱為 constructor chaining
最後一個建構子負責初始化所有變數的責任

`所有初始化集中在「最後一個 constructor」`
```java
class Rectangle {

    private int x;
    private int y;
    private int width;
    private int height;

    // 第 1 個建構子
    public Rectangle() {
        this(0, 0); // 呼叫第 2 個建構子
    }

    // 第 2 個建構子
    public Rectangle(int width, int height) {
        this(0, 0, width, height); // 呼叫第 3 個建構子
    }

    // 第 3 個建構子
    public Rectangle(int x, int y, int width, int height) {
        // 初始化變數
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }
}
```