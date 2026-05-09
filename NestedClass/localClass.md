定義在「方法裡面的 class」

👉 本質：
把舊資料 → 加工成新資料

特性

1️⃣ 沒有 access modifier
public / private / protected ❌ 都不能用

2️⃣ 只在方法內有效
方法執行期間才存在
方法結束就消失

3️⃣ 可以存取外層 class 的所有東西
✔ outer class fields
✔ outer class methods
✔ even private

4️⃣ 可以使用 method 參數（但有限制）
只能用：
✔ final
✔ effectively final（沒被改過）


# 為什麼要這樣做

只想做「一次性功能」

例如：
1. 加 computed field（pig latin name）
2. 暫時包裝資料
3. 不想污染外部 class

# captured

當你建立一個 local class 的實例時，
如果這個 class 用到了「外面 method 的變數」，

這些變數會被 capture

```java
class ShowFinal {
    private void doThis(final int methodArgument) {

        final int field30 = 30;

        // 🔥 local class
        class Test {
            void print() {
                // 👉 使用外部變數 → 發生 capture
                System.out.println(methodArgument);
                System.out.println(field30);
            }
        }

        Test t = new Test();
        t.print();
    }
}
```

# capture 是什麼？

Java 會「複製一份」這些變數，放進這個物件裡

# 為什麼要複製？

因為：

local variable（method 裡的變數）
存在 stack（暫時記憶體）

object（new 出來的 instance）
存在 heap（長期記憶體）

＝＝> 兩個地方不同！

所以 Java 要這樣做： 把變數「複製一份」帶走

⚠️ 為什麼一定要 final / effectively final？

```java
class showDinal {
    private void doThis (final int methodArgument) {
        final int Field30 = 30;
    }
}
```

既然是「複製」 , 就不能讓原本的值再改

Java 不允許這種不確定性, 所以規定：必須是 **final 或 effectively final**

# effectively final 是什麼？

沒寫 final，但「實際上沒有被改過」
```java
String name = "Tom"; // 沒寫 final，但沒改 → OK

class A {
    void print() {
        System.out.println(name); // ✅ 可以用
    }
}
```

# JDK 16 新功能

可以在 method 裡宣告： record , interface , enum
🔥 2. 這些不是 inner class

👉 它們是：

✅ static nested types（靜態巢狀型別）

因為：

自動 static
不依賴 outer instance
🔥 3. 舊版本 vs 新版本
版本	支援
JDK 16 以前	❌ method 內不能寫 interface / enum
JDK 16 之後	✅ 可以
🔥 4. 核心觀念

👉 nested types 分三種：

1️⃣ inner class
非 static
依賴 outer instance
2️⃣ static nested class
static
不依賴 outer instance
3️⃣ local class / record / enum / interface（method 內）
也是 static（隱式）
作用範圍只在 method