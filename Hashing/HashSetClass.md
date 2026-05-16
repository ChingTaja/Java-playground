### 1. 常數時間複雜度 $O(1)$

`HashSet` 特性是：無論集合中有 10 筆還是 1,000,000 筆數據，基礎操作（新增、刪除、包含）的速度幾乎相同

### 2. 雜湊機制與桶子 (Hashing & Buckets)

`HashSet` 透過 `hashCode()` 方法將物件分配到不同的「桶子」中。如果分配均勻，搜尋就會非常快

```java
// 自定義物件若要放入 HashSet，必須正確覆寫 hashCode
@Override
public int hashCode() {
    return Objects.hash(id, name);
}
// 註解：這遵守了「分散元素」的規則。
// 良好的 hashCode 能讓物件平均分佈，減少碰撞（Collision）。
```

### 3. 底層實作：HashSet 其實是 HashMap

這是一個有趣的設計細節
當你建立一個 `HashSet` 時
Java 底層其實是幫你建立了一個 `HashMap`

- **對應關係**：你的 `Set` 元素會變成 `HashMap` 的 **Key**，而 Value 則統一放一個沒意義的常數物件（PRESENT）

```java
// HashSet 內部 code 示意（簡化版）
public class HashSet<E> {
    private transient HashMap<E, Object> map;
    private static final Object PRESENT = new Object();

    public boolean add(E e) {
        // 利用 Map 的 Key 不能重複的特性來實現 Set
        return map.put(e, PRESENT) == null;
    }
}
// 註解：這解決了代碼重複開發的問題。Java 團隊直接複用 HashMap 成熟的雜湊邏輯來實作 Set。
```

`HashSet` 是高效能的代表，但它是一把雙刃劍。它追求速度，因此**放棄了順序**


### 4 . hashCode 與 equals
當自定義類別（如 Contact）放入 HashSet 時，Java 使用以下邏輯判斷是否重複：

    1. 呼叫 hashCode()：計算物件應放入哪個 Bucket（桶位）
    2. 若桶位已有物件：呼叫 equals() 比對內容是否真正相等
    
```java
// 在 Contact 類別中改寫方法，以 "name" 作為唯一性判斷基準
@Override
public boolean equals(Object o) {
    if (this == o) return true; // 參照相同必相等
    if (o == null || getClass() != o.getClass()) return false; // 類型檢查
    Contact contact = (Contact) o;
    return Objects.equals(getName(), contact.getName()); // 根據名稱判斷
}

@Override
public int hashCode() {
    // 使用質數 31 作為乘數是標準做法，可減少雜湊碰撞
    return 31 * Objects.hash(getName()); 
}
```
- Objects.equals 處理了 null 檢查；hashCode 使用乘數確保不同名稱的物件能均勻分散在不同桶位中

- 異常情況：若只改寫 equals 而未改寫 hashCode，兩個「相等」的物件可能會因雜湊碼不同而被放入不同桶位，導致 Set 中出現重複資料