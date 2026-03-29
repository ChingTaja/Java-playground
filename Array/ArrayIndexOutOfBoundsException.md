這是 Java 開發者最常遇到的「大魔王」之一，它的意思是：「索引超出邊界異常」。

發生原因：你試圖存取一個不存在的 index

使用「增強型 For 迴圈」
如果你只是想把陣列從頭到尾印出來（不需要修改值，也不需要知道現在是第幾個），請直接使用 Enhanced for-loop (for-each)：

```java
for (int element : myArray) {
    System.out.println(element);
}
```