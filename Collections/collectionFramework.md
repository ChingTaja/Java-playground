```bash
Collection
 ├── List
 │     ├── ArrayList
 │     └── LinkedList
 │
 ├── Set
 │     ├── HashSet
 │     ├── LinkedHashSet
 │     └── TreeSet
 │
 └── Queue
       └── Deque
             └── LinkedList
```

Collections ≠ Collections Framework

# Collections Framework（集合框架）

Collections Framework 「整套系統」 

裡面有：
    - 介面（List / Set / Map）
    - 實作類別（ArrayList / HashSet / HashMap）
    - 工具類別（Collections）

👉 就像「一整套工具箱」

---

## 2. 那 `Collections` 是什麼？

java.util.Collections

它是一個「工具類別（helper class）」

裡面放的是一些**操作集合的便利方法**，例如：

- 排序（sort）
- 打亂（shuffle）
- 找最大最小（max / min）
- 讓集合變成不可修改（unmodifiableList）

👉 可以想成：  
「幫 List / Set / Map 做額外操作的工具箱」

---

## 3. 為什麼會有這個 Collections class？

以前 Java 比較舊版本時：

- interface（介面）**不能寫 static / default 方法**
- 所以很多工具方法「沒地方放」

解法：  Java 就做了一個 `Collections` 類別，把工具方法集中放裡面

---

## 4. 後來發生什麼事？

Java 後來升級：

- interface 開始可以有：
    - default method
    - static method

👉 所以：

- 有些 `Collections` 的功能  
    → 被搬到 List / Set / Map 介面裡

但不是全部都有搬走



### 新舊方法對比：為何有些功能在介面（如 List）與工具類（Collections）中重複出現，以及該如何選擇

```java
List<String> names = new ArrayList<>(Arrays.asList("Bob", "Anna", "Zoe"));

// --- 舊式做法 (透過工具箱) ---
Collections.sort(names); 
Collections.addAll(names, "Charlie", "David");

// --- 現代做法 (透過介面本身) ---
// 語意更直觀：告訴 names 列表「去排序自己」
names.sort(Comparator.naturalOrder()); 
names.addAll(List.of("Charlie", "David"));
```


# java.util.Collections 工具類別中用於「重組、排序與比對」集合的方法


### 順序重組 (Shuffle & Reverse)：
如何將有序的 collections 隨機化（洗牌），以及如何將集合順序完全反轉
```java
// 1. 洗牌 (隨機化)
Collections.shuffle(deck);
Card.printDeck(deck, "洗牌後的牌組", 4);

// 2. 反轉 (首尾對調)
// 若原本是 A, B, C，反轉後會變成 C, B, A
Collections.reverse(deck);
Card.printDeck(deck, "反轉後的牌組", 4);
```

### 邏輯排序 (Sort with Comparator)
**兩種排序工具的並存**：區分 `Collections.sort()` (舊式/工具類) 與 `List.sort()` (新式/介面方法) 的關係

探討工具類別的排序方法
並透過自定義比較器（Comparator）處理未實作 Comparable 介面的物件

當物件（如 Card record）本身沒有排序規則時，我們可以自定義一個「演算法」傳給 Collections.sort

在 Java 8 之前，`List` 介面沒有自己的 `sort` 方法，所以大家只能用 `Collections.sort(list)`
Java 8 之後，`List` 擁有了 `sort()` 方法
為了不讓舊程式碼壞掉，Java 工程師修改了 `Collections.sort` 的內容，讓它直接去呼叫 `List.sort`
```java
// 定義排序規則：先比等級(Rank)，再比花色(Suit)
var sortingAlgorithm = Comparator.comparing(Card::rank)
                                 .thenComparing(Card::suit);

// 方法 1：舊式 (Legacy Style) - 透過工具類別
Collections.sort(deck, sortingAlgorithm);

// 註記：Collections.sort(list) 底層其實是呼叫 list.sort(null)
// 建議直接用：
// 方法 2：現代 (Modern Style) - 直接呼叫 List 的方法
// 這種寫法更符合物件導向 (OO) 的邏輯：物件自己知道怎麼排序 deck.sort(sortingAlgorithm);
deck.sort(sortingAlgorithm);
Card.printDeck(deck, "按等級與花色排序", 13);
```

### 位置檢索 (indexOfSubList)：
如何在大清單中尋找一段連續的子清單，並與 containsAll 的搜尋邏輯進行對比

這兩個方法都用於搜尋，但「精確度」不同：indexOfSubList 要求連續且順序一致，而 containsAll 只要求存在

對於大型清單，containsAll 的效能較差（IntelliJ 會提醒）
未來學習 HashSet 時會有更好的解決方案

```java
// 準備子清單 (從 deck 中切出 10 點的牌)
List<Card> tens = new ArrayList<>(deck.subList(16, 20));

// 1. 搜尋子清單位置 (必須連續)
int index = Collections.indexOfSubList(deck, tens);
System.out.println("子清單起始索引: " + index); // 若洗牌後不連續，會回傳 -1

// 2. 檢查是否包含 (只要有就好，不論位置)
boolean hasAll = deck.containsAll(tens);
System.out.println("是否包含所有元素: " + hasAll); // 只要元素都在，永遠為 true

```

### collections 關聯分析 Disjoint

判斷兩個集合之間是否完全沒有交集，這是集合邏輯運算中的重要判斷工具

```java
// 準備另一組清單
List<Card> kings = new ArrayList<>(deck.subList(4, 8));

// 判斷 deck 與 tens 是否「不相交」
// 因為 tens 是從 deck 切出來的，所以一定有交集，結果為 false
boolean isDisjoint = Collections.disjoint(deck, tens); 

// 判斷 kings 與 tens 是否「不相交」
// 國王與 10 點沒有共同元素，結果為 true
boolean isDisjoint2 = Collections.disjoint(kings, tens);

System.out.println("Kings 與 Tens 沒有交集嗎? " + isDisjoint2);
```


# Collections 提供「搜尋、修改、統計、重排 」List 的工具集合


### 二分搜尋與定位 (binarySearch & indexOf)
binarySearch 速度極快
但它有一個==絕對前提==：集合必須已經依照相同的比較器（Comparator）排好序
```java
// 1. 搜尋前必須先排序，且 Comparator 必須一致
deck.sort(sortingAlgorithm); 

Card tenOfHearts = Card.getNumericCard(Card.Suit.HEART, 10);
int foundIndex = Collections.binarySearch(deck, tenOfHearts, sortingAlgorithm);

// 如果沒排序就搜尋，會回傳負數（代表找不到），甚至拋出錯誤
System.out.println("找到的索引: " + foundIndex);
```


###  搜尋方法的選擇：indexOf vs. binarySearch

這是一個關於「時間複雜度」的抉擇

- **indexOf (線性搜尋)**：
    
    - **原理**：從頭走到尾，一張一張比對。
        
    - **優點**：清單**不需要排序**也能用；有重複項時，它保證回傳**第一個**找到的。
        
    - **適用**：小資料量（如 52 張牌）、資料沒排過序、或資料常變動。
        
- **binarySearch (二分搜尋)**：
    
    - **原理**：從中間切一半搜尋。
        
    - **優點**：速度極快（對數時間）。
        
    - **缺點**：**必須先排序**。如果為了找一次資料而特地去排 52 張牌，排序花的成本反而比搜尋更高。
        
    - **適用**：大數據量（如 10 萬筆使用者資料）、資料已經排好序且不會頻繁更換順序。
        

###  關於「重複資料 (Duplicates)」的陷阱

如果清單裡有重複的元素，`binarySearch` 「不保證」回傳哪一個。

- 如果你有三張「紅心 A」，`binarySearch` 可能回傳第 1 張，也可能是第 2 張。
    
- 如果你需要確切知道「第一張出現的位置」，你只能用 `indexOf`

### 元素替換與統計 (replaceAll & frequency)
如何==批量更新==集合中的特定元素，以及統計某個元素==出現的次數==

`
replaceAll  可以將清單中所有的「舊愛」換成「新歡」
frequency  方法會掃描整個集合，並利用元素的 `equals()` 方法來計算目標出現的次數

```java
Card tenOfClubs = Card.getNumericCard(Card.Suit.CLUB, 10);
Card tenOfHearts = Card.getNumericCard(Card.Suit.HEART, 10);

// 將所有的梅花 10 換成紅心 10
boolean changed = Collections.replaceAll(deck, tenOfClubs, tenOfHearts);
if (changed) {
    System.out.println("已完成替換！");
}

// 統計紅心 10 在牌組中出現了幾次
int count = Collections.frequency(deck, tenOfHearts);
System.out.println("紅心 10 出現次數: " + count); // 預期會是 2 (原本的一張 + 替換來的一張)
```


### 極限值尋找 (min & max)：利用自定義的排序演算法（Comparator）找出集合中的「最優」與「最差」元素

根據你提供的排序演算法（Comparator），找出集合中的最大值（例如最強的牌）與最小值

```java
// 使用之前定義的 sortingAlgorithm (按等級和花色排序)
Card bestCard = Collections.max(deck, sortingAlgorithm);
Card worstCard = Collections.min(deck, sortingAlgorithm);

System.out.println("最強的牌: " + bestCard); // 例如：A♠
System.out.println("最弱的牌: " + worstCard); // 例如：2♣
```

### 位置位移與交換 (rotate & swap)
掌握如何平移集合元素（如循環位移）
精確交換兩個指定位置的元素，完成對集合順序的細膩微調

rotate 可以想像成跑馬燈或循環位移
swap 則是精確對調兩個位置的值

```java
List<Card> clubs = new ArrayList<>(deck.subList(0, 13)); // 取得 13 張梅花

// 1. rotate (正數：末尾移到前面；負數：前端移到後面)
Collections.rotate(clubs, 2); // 最後兩張（Q, K）跑到最前面
Collections.rotate(clubs, -2); // 最前兩張（2, 3）跑到最後面

// 2. swap (精確交換兩個位置)
// 例如：手動將第 0 個和第 12 個位置對調
Collections.swap(clubs, 0, 12); 

// 應用：利用 swap 進行自定義反轉
for (int i = 0; i < clubs.size() / 2; i++) {
    Collections.swap(clubs, i, clubs.size() - 1 - i);
}
```
### Collections.replaceAll v.s List.replaceAll(lambda)

Collections.replaceAll 只能「一換一」，若需要透過條件邏輯大量修改（如：所有點數大於 10 的換成 Joker)

要使用 List.replaceAll(lambda)
```java
// 案例：牌組裡可能有兩張梅花 10 (假設)
// 它會找出「所有」梅花 10，把它們都換成紅心 10
Collections.replaceAll(deck, tenOfClubs, tenOfHearts);

/* 內部邏輯其實是：
if (card.equals(tenOfClubs)) { 
    card = tenOfHearts; 
} 
*/
```

- Collections.replaceAll：精確的「一對一」
它的邏輯是：「只要長得跟 A 一樣的，通通換成 B」
它不能處理「大於、小於、包含...」這種邏輯判斷，它只能做 equals() 的比對

- List.replaceAll (Lambda)：強大的「條件運算」
它的邏輯是：「把清單裡的每一張牌都拿出來檢查，符合我的規則就換掉」

```java
// 需求：所有等級大於 10 的牌（J, Q, K, A），通通換成 Joker」
deck.replaceAll(card -> {
    if (card.rank() > 10) {
        return new Card(Card.Suit.SPADE, "Joker", 99); // 符合條件就回傳新牌
    }
    return card; // 不符合條件就維持原樣
});
```