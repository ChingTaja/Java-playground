# Raw type
當你使用泛型類別（generic classes）時
無論是參考它們還是建立實例
強烈建議一定要指定型別參數（type parameter）
不過，你仍然可以在不指定型別參數的情況下使用它們

這種用法稱為原始型別（Raw Type）

這些類別的原始用法仍然被保留
是為了**向下相容**舊版本的程式碼
但基於以下幾個原因，它是不建議使用的

泛型讓編譯器可以在編譯階段（compile-time）進行型別檢查，確保在加入或處理 List 中元素時不會出現型別錯誤

泛型也能讓程式碼更簡潔，因為我們不再需要像使用 Object 型別時那樣，自己進行型別檢查與強制轉型（casting）。

你可能會忘記加上型別參數，但 IntelliJ 會嘗試幫助你辨識這類問題

❌ 沒有使用泛型（Raw Type）
```java
import java.util.*;

public class Main {
    public static void main(String[] args) {
        List list = new ArrayList(); // 沒有指定型別

        list.add("Hello");
        list.add(123); // 也可以放 int（會自動裝箱）

        // 取出時
        String s = (String) list.get(0); // 必須自己轉型
        String s2 = (String) list.get(1); // ❌ 這裡會 Runtime Error
    }
```


✅ 使用泛型
```java
import java.util.*;

public class Main {
    public static void main(String[] args) {
        List<String> list = new ArrayList<>(); // 指定只能放 String

        list.add("Hello");
        // list.add(123); ❌ 編譯直接報錯

        String s = list.get(0); // 不需要轉型
    }
}
```
泛型讓錯誤「提早在編譯期發現」

泛型不是只能放某種 class，而是可以放「任何型別」
但前提是「必須是物件，不能是 primitive ❗」 (e.g.  int)
-> 用 Wrapper class 解決（Autoboxing）

```java
Team<int> ❌
Team<Integer> ✅
```