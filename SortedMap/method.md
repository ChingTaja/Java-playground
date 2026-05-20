## merge 和 compute 的差異?

假設我們要統計課程銷量，看看這兩個方法寫起來有什麼不同：

#### A. 使用 `merge`（優雅）

因為是單純的數字累加，`merge` 知道如果資料不在就直接填 `1`，完全不需要防禦 `null`：

```Java
// 語意：不在就填 1，在就把舊值(prev)加上新傳入的 1(current)
weeklyCounts.merge(courseId, 1, (prev, current) -> prev + current);
```

#### B. 使用 `compute`（累贅）

因為 `compute` 不管在不在都會觸發 Lambda，所以你必須自己寫三元運算子去卡 `null`，否則會發生 `NullPointerException`：


```Java
// 語意：我要自己看 oldValue 是不是 null，是的話拿 0 來加 1，不是的話拿舊值加 1
weeklyCounts.compute(courseId, (key, oldValue) -> {
    return (oldValue == null) ? 1 : oldValue + 1;
});
```

---

### 3. 程式碼直觀對比：以「資料分組 (List)」為例

換個場景，如果是要把訂單塞進 `List` 裡面呢？

#### **A. 使用 `compute`（非常適合）**

當我們需要動態建立集合（如 `ArrayList`）並對物件進行操作時，`compute` 的掌控度最高：


```Java
datedPurchases.compute(date, (key, plist) -> {
    List<Purchase> list = (plist == null) ? new ArrayList<>() : plist;
    list.add(p);
    return list; // 自由回傳加工後的物件
});
```

#### **B. 使用 `merge`（非常彆扭）**

如果你硬要用 `merge` 來做 List 分組，代碼會變得極度反直覺且難讀：


```java
List<Purchase> newTempList = new ArrayList<>(List.of(p));

// 語意：不在就直接塞入 newTempList。在的話，把新建立的 list 全部倒進舊 list 裡...
datedPurchases.merge(date, newTempList, (oldList, newList) -> {
    oldList.addAll(newList);
    return oldList;
});
// 缺點：不管格子裡有沒有東西，每次客人都得在外面先 new 一個暫時的 ArrayList，極度浪費記憶體。
```

- **選 `merge` 的時機**：當你要做的動作是 **「加總、數字累加、計數」**，而且格字是空的時候只要填入一個固定常數。=> 適合用在「單純數字的累加、計數器」，你只需要給他一個預設的數字（例如 1），剩下的他自己會加
    
- **選 `compute` 的時機**：當你要做的動作是 **「複雜的物件加工、動態建立 List 容器」**，需要完全掌控資料從無到有的完整建立過程。=> 適合用在「從無到有、需要自己動手蓋容器（如 List）」的複雜情況


# SortedMap 介面 以及它的子介面 NavigableMap 介面 所定義的方法 (TreeMap 的方法)


TreeMap / NavigableMap 重點整理

1 區段截取視圖 headMap tailMap

TreeMap 可以依照 Key 的排序 將資料切成子視圖 View

## headMap

定義
從開頭 cutoffKey 之前

特性
預設不包含 cutoffKey Exclusive

範圍
[ start cutoffKey )

### tailMap 

定義
從 cutoffKey 結尾

特性
預設包含 cutoffKey Inclusive

範圍
[ cutoffKey end ]

多載版本

headMap key boolean inclusive
tailMap key boolean inclusive

可手動控制是否包含邊界值

2 TreeMap 導航與搜尋方法

TreeMap 提供排序導航能力 HashMap 沒有

基本邊界

firstKey lastKey
取得最小 Key 最大 Key

firstEntry lastEntry
取得最小 最大 Entry Key Value

可用 getValue 取得值

鄰近查找

lowerKey lowerEntry
嚴格小於 key

floorKey floorEntry
小於或等於 key
可能回傳自己 key == key
容易造成無限迴圈

higherKey higherEntry
嚴格大於 key

ceilingKey ceilingEntry
大於或等於 key

3 反轉視圖與 poll 方法

descendingMap

TreeMap 的反轉視圖
Key 由新到舊排列
是 View 不是複製

pollFirstEntry pollLastEntry

讀取並刪除資料 Mutate

## 重要陷阱

descendingMap 是 View
對 reversed 使用 poll
會同步影響原始 TreeMap
可能導致原始資料被清空

4 Map merge 計數用法

用途: 快速統計數量

語法
```java
map.merge(key 1 oldValue newValue -> oldValue newValue)
```
流程

第一次出現
key -> 1

之後出現
key -> 舊值 + 1

優點

不用 if else
一行完成統計
簡潔安全

總結

TreeMap 提供排序 切片 導航 反轉能力
但 View 搭配 poll 會影響原始資料需要注意


| 尋找方向  | 嚴格小於 / 大於（`<` 或 `>`）            | 包含等於（`≤` 或 `≥`）                   | 讀取並同時刪除（Poll）              |
| ----- | ------------------------------- | --------------------------------- | -------------------------- |
| 往前（小） | `lowerKey()` / `lowerEntry()`   | `floorKey()` / `floorEntry()`     | `pollFirstEntry()`（拿最小並刪除） |
| 往後（大） | `higherKey()` / `higherEntry()` | `ceilingKey()` / `ceilingEntry()` | `pollLastEntry()`（拿最大並刪除）  |
