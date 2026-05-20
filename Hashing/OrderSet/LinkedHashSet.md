# 有順序的 Set
如果你需要一個==有順序的== Set，你會想要考慮 LinkedHashSet 或 TreeSet

LinkedHashSet 會維護元素的插入順序

TreeSet 是一個排序集合，根據元素的自然順序排序，或者在建立集合時指定所需的排序方式

### LinkedHashSet 的結構與可預測性

LinkedHashSet 結合了雜湊表與雙向鏈結串列的優點
它保證了迭代時的順序與資料加入（Insertion）的順序完全一致

LinkedHashSet 繼承了 HashSet 類別

LinkedHashSet 的所有方法都與 HashSet 的方法相同


```java
// 使用 LinkedHashSet 維護插入順序
Set<String> linkedSet = new LinkedHashSet<>();
linkedSet.add("Zebra");
linkedSet.add("Apple");
linkedSet.add("Mango");

// 輸出結果必為: [Zebra, Apple, Mango]
System.out.println("LinkedHashSet 順序: " + linkedSet); 

// 邏輯說明：底層透過 Doubly Linked List 紀錄每個 Entry 的先後順序，
// 即使元素被重新雜湊到不同的 Bucket，鏈結關係依然保留，確保順序可預測。
```

