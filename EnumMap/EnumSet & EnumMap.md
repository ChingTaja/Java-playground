==讓 enum 當 key / element 時更快、更省記憶體==

Collection's Framework 的類別 , 它們是專門為了更有效率地支援列舉型別（Enum Types）而創建的

`EnumSet` 和 `EnumMap` 各自擁有特殊的底層實作方式，這與傳統的 `HashSet` 或 `HashMap` 是完全不同的

這些特殊的實作方式，使得這兩種型別變得**極其緊湊（節省記憶體）且非常高效**

另外，Java 並沒有專門為列舉型別設計特殊的 List 實作類別 -> 因為傳統的 `ArrayList` 本身底層就已經是一個連續的陣列了，它在位置存取上已經做到了最優化（時間複雜度為 $O(1)$），沒有辦法再透過列舉的特性來榨出更多的效能。因此 Java 官方認為沒有必要大費周章去寫一個 `EnumList`



普通的 `HashSet` 和 `HashMap` 底層要處理複雜的雜湊演算法（Hashing）、解決雜湊碰撞、維護拉鏈結構（Buckets），還要耗費不少記憶體空間。

但因為 **列舉（Enum）在 Java 中是「數量固定、且順序已知（有 index）」的**，所以：

- **`EnumSet` 的底層**：其實只是用一個簡單的 **位元向量（Bit Vector，通常是一個隨附的 `long` 變數）** 來實作。每一個列舉常數只佔用 `1個 bit`（0 代表不在 Set 裡，1 代表在）。在 CPU 裡做位元運算（Bitwise operations）的速度是極快、甚至接近硬體層級的
    
- **`EnumMap` 的底層**：其實就是一個極其簡單、超輕量級的 **極速陣列（Array）**。它不需要計算 Hash Code，直接拿列舉的順序（Ordinal）當作陣列的索引值（Index），速度就像直接去陣列拿資料一樣快

###  EnumSet

一個專門為了與列舉值（Enum values）搭配使用，而進行優化的特化版 Set 實作

同一個 `EnumSet` 裡面的所有元素，都必須來自於**同一個單一的列舉型別**

`EnumSet` 是一個**抽象類別（Abstract Class）**，這代表我們無法直接使用 `new` 關鍵字來將其實例化

```java
enum Day { MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY }

// 使用工廠方法 of 建立包含特定元素的 EnumSet
EnumSet<Day> workDays = EnumSet.of(Day.MONDAY, Day.FRIDAY);

// 使用 allOf 建立包含該列舉所有元素的集合
EnumSet<Day> allDays = EnumSet.allOf(Day.class);
// 註解：這解決了重複手動添加元素的問題，且底層使用位元向量（Bit Vector）存儲，極其快速
```

###  EnumMap

`EnumMap` 是一個專門為了與列舉型別的鍵值（Enum type keys）搭配使用，而進行優化的特化版 Map 實作

EnumMap 要求所有的鍵必須來自同一個 enum
它在內部使用陣列（Array）實作，因此存取速度極快
且鍵的順序會==嚴格遵循列舉定義的原始順序==


#### 1. 內部其實只是個超輕量「陣列（Array）」

傳統 `HashMap` 為了找到 Key 放在哪裡
要算 `hashCode()`、要處理雜湊碰撞（Hash Collisions）
有時候還要把結構轉成紅黑樹，既費時又耗記憶體。

但 `EnumMap` 非常聰明，因為列舉的數量在編譯時就固定了
而且每個列舉常數都有一個內建的數字序號（`ordinal`）。例如：`MON` 是 0，`TUE` 是 1，`WED` 是 2

`EnumMap` 底層直接用一個**最普通的二進位陣列 `Object[]`** 來存資料
當執行 `map.get(Day.TUE)` 時，它在底層做的事其實只是 `array[1]`。這就是為什麼它同樣是 $O(1)$ 的速度
但實際執行效率卻遠遠把 `HashMap` 甩在後面的原因

#### 2. 它會自動幫你排序（照宣告順序）

普通的 `HashMap` 是完全沒有順序的
但 `EnumMap` 因為底層是陣列
當你走訪（Iterate）它時
它輸出的順序會**嚴格按照你在定義 `enum` 時的先後順序**（也就是 `ordinal` 的大小）排列
這讓它同時兼具了 `HashMap` 的速度與類似 `TreeMap` 的排序特性。

### 3. 如何在程式碼中建立它？

講者提到建立它時，不能像普通 Map 那樣空空地 `new`，必須明確告訴它你要綁定哪一個 Enum 類別：

```Java
// 明確傳入 Class 物件的建立方式（最常見）：
Map<Day, String> plan = new EnumMap<>(Day.class); 

// 之後的操作就跟普通 HashMap 一模一樣
plan.put(Day.MON, "去健身房");
String activity = plan.get(Day.MON);
```

### 4. 限制：不能用 `null` 當作 Key

傳統 `HashMap` 允許你存入一筆 `null` 作為 Key
但是 `EnumMap` 的 Key **絕對不能是 `null`**，因為 `null` 沒有序號（ordinal），陣列會不知道要把資料放在哪一個格子