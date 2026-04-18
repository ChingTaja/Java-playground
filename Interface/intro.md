介面（interface） 跟 abstract class 有點類似，但它本身**並不是**一個 class

它是一種特殊的**型別**
更像是一種**規範**
存在於 class 與使用這個類別的 client code 之間
而且由**編譯器來強制檢查**

當你的類別宣告「實作某個介面」時
就**必須實作**該介面中的所有抽象方法

類別之所以會這樣做
是因為它希望被外界以那個「型別」來認識（也就是被當作那種介面型別來使用）

介面讓一些本來可能沒有太多**共通點**的類別
可以被歸類為同一種「可辨識的參考型別」

# 宣告方式
```java
public interface FlightEnabled {}
```
宣告一個介面的方法，跟宣告類別很像，只是把 class 關鍵字換成 interface

介面的命名通常會根據它描述的**一組行為**來取名

很多介面會以 -able 結尾
例如 Comparable、Iterable
代表「能夠做某件事情」或「具備某種能力」

# 實作 interface

```java
public class Bird implements FlightEnabled {}
```

# interface 可以當型別用
```java
FlightEnabled flier = new Bird();
```

# 繼承
一個類別**只能繼承一個類別**
Java 只支援單一繼承（single inheritance）

一個類別可以實作多個介面，這提供了更大的彈性與模組化設計

# interface body
我不需要把 interface（介面）宣告成 abstract
因為這個修飾詞對所有介面來說是**隱含自動成立**的
```java
abstract interface xxx // 不用寫成這樣
```

在介面中，只要是沒有 method body的方法
**預設就會被當作 abstract 方法**

```java
public abstract void method(); // 不用寫成這樣 public abstract  是多餘的

abstract void method(); // 不用寫成這樣 abstract  是多餘的

void fly(); // OK !~
```

# All menber on an interface are implicityly public

 1. 類別（class）省略 access modifier → package-private
 ```java
class Animal {
    void eat() {  // 沒寫 public/protected → package-private
        System.out.println("Animal is eating");
    }
}
```
重點：
a. 沒寫 modifier = 同 package 才能用
b. 外部 package 不能存取

2. interface成員省略 → 一律 public
 ```java
interface FlightEnabled {

    void fly();  // 自動變成 public abstract
}
```

等同於：
```java
public interface FlightEnabled {
    public abstract void fly();
}
```

3. interface 的限制
❌ 錯誤：**不能用** protected
```java
interface Trackable {

    protected void track(); // ❌ 編譯錯誤
}
```
原因：
interface 的方法**預設就是 public**
protected 在語意上不合理（外部要能實作/使用）

4. interface 允許 **private concrete** method（Java 9+）
==> 給 **interface 自己重複使用邏輯** 用的
```java
interface Logger {

    default void log(String msg) {
        writeLog(msg);
    }

    private void writeLog(String msg) {
        System.out.println("LOG: " + msg);
    }
}
```
重點：
private 方法只能在 interface 內部用
不能被實作類別看到