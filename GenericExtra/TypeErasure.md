型別擦除是Java 泛型(Generics)設計時的一種核心機制

泛型 = 編譯器幫你做的「類型安全檢查工具」
type erasure = 這些檢查用的資訊，在編譯後全部消失

泛型（Generics）的存在
是為了**在編譯時**提供更嚴格的型別檢查
 編譯器會把泛型類別轉換成「已具體型別化的類別」
也就是說，在 byte code 或 class 檔案中(編譯後的結果)，其實**不會保留**任何型別參數。

當類別中使用到型別參數時
如果沒有指定上界（upper bound）
就會被替換成 Object；如果有指定上界
則會被替換成該上界型別本身
這個轉換過程稱為「型別擦除（type erasure）」
因為像 T（或 S、U、V 等）這些型別參數會被「擦掉」
並被真正的型別取代


類別的泛型 <T> **不能**用在 static 方法裡

```java
public static void someMethod(List<T> items) { } // ❌ 錯
```

💥 為什麼 <T> 不能用？

👉 因為：

<T> 是「物件建立時才決定的型別」
static 方法在「還沒建立物件前就存在了


❌ 現在這段的問題
```java
public static List<T> getMatches(List<T> items, String field, String value)
```

👉 問題：

1. T 來自 class 的泛型（如果你在 class 有寫 <T>）
2. 但這是 static 方法 , static 方法沒有實例（沒有 this）
3. → static 不能用 class 的 T
👉 要用就自己在 method 上宣告 <T>


✅ 正確寫法

👉 把方法改成「泛型方法」：
```java
public static <T extends QueryItem> List<T> getMatches(List<T> items, String field, String value) {
    List<T> matches = new ArrayList<>();
    return matches;
}
```

方法的泛型型別，和類別的泛型型別是兩個完全不同的東西

# multiple bounds 多重上界

如果有　class 一定要排第一個
```java
public class GenericClass<T extends AbstractClass & InterfaceA & InterfaceB>
```
👉 T 必須同時符合三個條件


3️⃣ 為什麼只能一個 class？

You can extend only one class at most

👉 Java 本身限制：

❌ 不能多重繼承 class
✔ 只能繼承 一個 class
4️⃣ 但 interface 可以很多個

zero to many interfaces

##  類別裡可以放什麼？

一個 class 不只可以有：

- fields（欄位）
- methods（方法）

還可以有：

👉 其他型別（nested types）

- class
- interface
- enum
- record
==> 這些統稱為 Nested Types

##  為什麼要用 nested class？

1. 情境:當兩個類別：

- 功能高度綁定（tightly coupled）
- 邏輯互相依賴

👉 就可以放在同一個 class 裡

