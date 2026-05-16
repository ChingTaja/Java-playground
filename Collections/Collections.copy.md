Collections.copy
# Collections.copy vs List.copyOf

```java
// 1. Collections.copy(dest, src) -> 覆寫模式
// 要求：目標清單(dest)的大小(size) 必須大於或等於 來源清單(src)
List<Card> destination = new ArrayList<>(Collections.nCopies(20, null));
Collections.copy(destination, sourceList); // 將 sourceList 的內容覆蓋到前段

// 2. List.copyOf(collection) -> 快照模式
// 特色：回傳一個全新的、不可修改(Immutable)的清單
List<Card> immutableCopy = List.copyOf(sourceList);
// immutableCopy.add(newCard); // ❌ 會拋出 UnsupportedOperationException
```

List.copyOf 和 Collections.nCopies 回傳的 List 通常是不可修改的
適合用於 read-only 資料
| 方法           | 本質          | 是否可修改       | 行為        |
| ---------------- | ----------- | ----------- | --------- |
| `List.copyOf()`     | 建立「不可變副本」 | ❌ 不可 | 產生唯讀 List |
| `Collections.nCopies()` | 建立「固定內容視圖」  | ❌ 不可    | 重複同一元素    |
| `Collections.copy()`    | 「覆蓋既有 List」 | ✔ 可（原 List） | 不產生新 List |


👉 `Collections.copy()` = 塞進原本的 list（改舊的）  
👉 `List.copyOf()` = 建立新的 list（但不能改）