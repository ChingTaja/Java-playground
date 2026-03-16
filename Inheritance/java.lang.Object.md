1. 隱形繼承：即使你寫 public class Dog {}（後面沒寫 extends），Java 編譯器也會自動幫你變成 public class Dog extends Object {}

2. Object 是所有 Java 類別的「Root」

3. 因為繼承了 Object，所以任何類別（像是你的 Dog 或 Animal）都自動擁有了一些方法，例如：

- toString()：把物件變成字串

- equals()：比較兩個物件是否相等

- hashCode()：取得物件的雜湊值