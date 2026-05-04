##  四種 nested class

🏠 用「房子」比喻（超好懂）
```java
class Outer {
    ...
}
```

👉 Outer = 一棟房子

1. Static Nested Class（靜態巢狀類別）
```java
class Outer {
    static class Nested {}
}
```

 特點：

- 有 static
- 用 Outer.Nested 存取 不需要 outer instance

e.g.
存取方式：
```java
Outer.Nested obj = new Outer.Nested();
obj.hello();
```

❌ inner class（需要 outer instance）
```java
Outer outer = new Outer();
Outer.Inner inner = outer.new Inner(); // 必須靠 outer
```

2. Inner Class（內部類別）
```java
class Outer {
    class Inner {}
}
```
👉 特點：

- 沒有 static
- 必須透過 outer instance

```java
Outer outer = new Outer();
Outer.Inner inner = outer.new Inner();
```
3. Local Class（區域類別）

👉 在 method 裡定義：

```java
void method() {
    class LocalClass {}
}
```

👉 特點：

- 只在方法內存在 = 外部不能使用

4. Anonymous Class（匿名類別）

👉 沒名字、同時定義+建立：

```java
Runnable r = new Runnable() {
    public void run() {}
};
```

👉 特點：

- 沒有 class 名字
- 一次性使用

## enum / record / interface

👉 也可以放在 class 裡當 nested type

enum = 列舉（本質也是 class）
record = 簡化資料類別（本質也是 class）
interface = 抽象行為定義

## Java 版本變化（JDK16）
- before JDK 16：
只有 static nested class 可以有 static methods

- after JDK 16：

👉 所有 nested class 都可以有 static members ,包含：static method , static field
🎯 一句話總結

👉 Nested types = 把相關的 class/interface/enums/records 高相依性的東西 放在同一個 class 裡管理


