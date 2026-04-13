enum 是 Java 用來支援「列舉（enumeration）」的一種型別

enum 有點像陣列（array），但有幾個重要差異：
1. 元素是「已知且固定的」
2. 不能改變
3. 每個元素是用「名稱」來存取，而不是用索引（index）

- 建議使用「大駝峰命名法（Upper Camel Case）」

# method


name() → 取得名稱
ordinal() → 取得順序（索引）
values() → 它會回傳一個「包含所有 enum 常數的陣列」

```java
for (Topping topping : Topping.values()) {
    System.out.println(
        "name = " + topping.name() +
        ", ordinal = " + topping.ordinal()
    );
}`


# enum 裡面可以寫

enum 其實可以「寫方法」 , 以用 switch(this)
👉 直接針對「當前 enum 值」做邏輯判斷

```java
return switch (this) {
    case BACON -> 1.5;
    case CHEDDAR -> 1.0;
    default -> 0.0;
};
```

3️⃣ 分號 ; 什麼時候需要？


❌ 只有常數 → 不用分號
```java
enum Topping {
    BACON, CHEDDAR
}
```


✅ 有方法 / 欄位 → 要加分號
```java
enum Topping {
    BACON, CHEDDAR;  // ← 這個分號很重要！

    public double getPrice() {
        return 0.5;
    }
}
```

