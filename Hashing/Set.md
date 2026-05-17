Set 本身並不具備隱含的順序（無序）雖然某些特定的 Set 實作類別是有序的
Set **不包含重複的元素**
Set 可能（最多）包含一個 `null` 元素


### Set Method

```java
Set<String> emailSet = new HashSet<>();

// 解決了動態添加與移除數據的需求
emailSet.add("user@example.com");
emailSet.remove("user@example.com");
emailSet.clear(); // 清空整個集合

// 註解：這些操作在 HashSet 中效率極高，通常在 O(1) 時間內完成。
```

資料遺失風險：
單純做 Set 的聯集雖然能得到唯一的名字清單
但會失去該名字對應的多個電話或 Email 資訊（實務上若要保留，可能需要其他資料結構輔助）

HashSet 的底層是 HashMap：

從 Java 的 HashSet 原始碼中（查看建構子）可以清楚看到
當執行 new HashSet() 時，它在底層其實是去 new 了一個 ==HashMap==

HashSet 與 HashMap 在目前的 Java 版本中是高度緊密結合、互相依賴的

