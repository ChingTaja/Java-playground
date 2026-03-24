1. 傳統 for 迴圈 (Traditional for)
適合：需要知道 位置（索引） 或 跳著讀取 時

```java
for (int i = 0; i < newArray.length; i++) {
    System.out.print(newArray[i] + " "); // 必須用 [i] 拿資料
}
```

2. Enhanced for
適合：單純想 把裡面每個東西都拿出來看一遍。

```java
//  [型別] [變數名] : [陣列名]
for (int element : newArray) {
    System.out.print(element + " "); // 直接用 element，不用 [i]
}
```

# java.util.Arrays
3. 印出陣列
```java
System.out.println(newArray) // [I@@214c265e --> 非預期的數字
```
會印出非預期的數字是因為陣列其實就是一種特殊的 Java 類別
 ```[ 加上大寫字母 I```，這代表它是一個基本型別整數陣列（primitive integer array），後面跟著的是雜湊碼（hash code）的十六進位表示法

 但這在檢查或操作陣列時，可能沒什麼幫助

 Java 提供了一個輔助類別（helper class），其中包含許多你可以用於陣列的靜態方法（static methods）
 這個類別就是 java.util.Arrays

Java 的陣列型別非常基礎
內建的功能很少
只有一個名為 length 的屬性（欄位）
並且繼承了 java.lang.Object 的功能
他提供 static methods (class methods)
而非 instance methods