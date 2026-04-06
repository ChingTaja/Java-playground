1. 兩者的共通點 (What they share)
儘管結構不同，但它們在邏輯上有許多相似之處：

有序性： 兩者都根據索引 (Index) 排序，第一個元素永遠是 0

可重複性： 都允許存放重複的值

空值允許： 都允許存放 null

繼承關係： 兩者最終都繼承自 java.lang.Object

可變性 (Mutable)： 兩者內容都是可以修改的（你可以更換裡面的元素）

2.

3. 初始化方式的詳細對比
原生陣列 (Array)
- 宣告與大小： `String[] array = new String[3]`; (必指`定大小)
- 匿名初始化： `String[] array = {"Apple", "Banana"};` (簡潔、自動判斷長度)

ArrayList
- 宣告： `ArrayList<String> list = new ArrayList<>();` (使用<>)
- 快速填充： ArrayList<String> list = new ArrayList<>(List.of("Apple", "Banana"));

這裡使用了 List.of() 產生一個不可變清單，再丟進建構子轉成可變的 ArrayList

當你明確知道資料數量且需要最高效能時：使用 Array。

當資料數量會增減，或需要強大的操作方法（排序、搜尋）時：使用 ArrayList。

| 特性 |      原生陣列 (Array)             | ArrayList (集合框架) |
|------|----------------------------------|---------------------|
| 大小 (Size) | 固定 (Fixed)，宣告後不可改變 | 動態 (Resizable)，會根據需求自動擴容。 |
| 資料型別.    | 支援基本型別 (int, double) 與物件 | 僅支援物件（透過 Autoboxing 支援 Integer 等）。 |
| 宣告語法     | 使用中括號 `[]`.   | 使用尖括號（泛型）`<T>`。 |
| 型別檢查    | 編譯時檢查（若有指定型別）   | 強烈建議指定型別，否則會變為 Raw Type。 |
| 初始化工具   | 使用大括號 `{}` (Array Initializer) | 使用建構子或 `List.of()` / `Arrays.asList()` |
| 元素存取  | 使用索引訪問 `array[index]`。 | 使用方法 `.get(index)` 與 `.set(i, val)`|

# 搜尋與排序 (Search & Sort)

1. 搜尋 (Searching)
Array: 必須先排序，再使用 `Arrays.binarySearch()`。若有重複元素，不保證返回第一個索引。

ArrayList:  `.contains(val)` / `.containsAll(collection)`：回傳布林值。

`.indexOf(val)` / `.lastIndexOf(val)`：回傳索引，找不到則回傳 -1。

2. 排序 (Sorting)
Array: 使用 `Arrays.sort(arr)`。支援基本型別與 Wrapper。

ArrayList: 使用 `.sort(Comparator)`

正序：`list.sort(Comparator.naturalOrder())`

反序：`list.sort(Comparator.reverseOrder())`



# asList ＆ List.of用法

- Arrays.asList 是舊時代的橋樑，List.of 是現代的防護罩，而 toArray 則是回歸原始的方法

Arrays.asList(...) / Arrays 類別 / 固定長度 / 但可修改 (Mutable),它是原陣列的「視圖」，修改 List 會影響原陣列

List.of(...) / List 介面 / 完全不可變 (Immutable) / 它是資料的「副本」，保證內容不會被任何方式修改。