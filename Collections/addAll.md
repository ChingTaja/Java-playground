Java 提供了兩種 addAll，差別在於接收資料的格式

| 方法來源        | 參數型別        | 特色          |
| ---------------- | ---------------- | ------------------- |
| `List.addAll()`  | `Collection` | 必須傳入另一個集合或清單（如 `List.of(...)`）  |
| `Collections.addAll()` | `T... elements` | 使用可變參數（varargs），可以直接傳入多個物件或一個陣列 |


```java
Card[] cardArray = {aceOfHearts, kingOfClubs};
List<Card> deck = new ArrayList<>();

// Collections 版：支援直接傳入陣列
Collections.addAll(deck, cardArray); 

// List 版：必須轉成 Collection 才能傳入
deck.addAll(Arrays.asList(cardArray));
```