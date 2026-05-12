除了能重複且有序的 List
接下來將介紹處理數據的另外兩種邏輯：

## Set (集合)
- 處理唯一性  
- 就像一袋不重複的硬幣，裡面不會有兩個完全相同的東西  

## Map (映射)
- 處理鍵值對 (Key-Value)  
- 就像字典或你熟悉的 JSON 物件  
- 透過一個獨一無二的 Key（如帳號 ID）快速找到對應的 Value  

# 從「使用」轉向「架構」(Judgment)

理解框架 (Framework) 的設計：

- 為什麼要區分介面 (Interface) 與實現類 (Class)？
- 不同的情境（如：頻繁搜尋 vs. 頻繁增刪）該選擇哪種工具？

# 強化物件管理能力

掌握了這套框架，你處理物件群組的能力會大幅提升：

- 更高效的搜尋（Map 的 O(1) 查找）
- 更嚴謹的資料維護（Set 的自動去重）
- 更靈活的代碼（透過介面進行多型操作）

# Collection
- 特性：其中的元素通常具有共同的關係或用途。

- 常見類型：包括 Array (陣列)、List (列表)、Set (集合)、Queue (隊列)、Map (映射/字典) 等。

- 區分標準：不同集合之間的差異在於：

1. 記憶體儲存方式
2. 元素的存取與排序邏輯
3. 是否允許 null 值
4. 是否允許重複元素

# Collection frames

統一架構：Java 提供了一套統一的設計來表示與操作 collections

介面導向（Interface-based）： 操作獨立於實作細節

開發者通常針對 interface 進行 coding
而不需過於依賴底層具體的 實作類（Concrete Classes）

這增加了代碼的靈活性與可維護性


透過將變數宣告為介面型別（如 Collection）
可以輕鬆抽換底層的實作類別（如 ArrayList 或 TreeSet）
而不需要修改大部分的邏輯程式碼


```java
import java.util.*;

public class Main {
    public static void main(String[] args) {
        // 使用 interface Collection 作為變數型別，展現多型特質
        // Collection 宣告為 Collection 與 List 會決定你之後能呼叫哪些方法 
        // 越抽象的宣告（如 Collection），未來更換實作類別（如換成 HashSet）的代價越低
        // 1. 使用 ArrayList：元素會按照「加入順序」排列
        Collection<String> list = new ArrayList<>();
        addAndPrint(list);

        // 2. 抽換成 TreeSet：元素會自動按「字母順序」排序
        list = new TreeSet<>();
        addAndPrint(list);
    }

    private static void addAndPrint(Collection<String> col) {
        col.add("Zoe");
        col.add("Anna");
        col.add("Bob");
        // 無論底層是哪種實作，都能使用 Collection 介面定義的 println 直接輸出
        System.out.println("當前集合實作: " + col.getClass().getSimpleName() + " -> " + col);
    }
}
```



## Collection interface 定義了所有集合必須具備的基本行為，如增加、刪除、檢查是否存在 => 不管哪一種 Collection 都一定需要的能力


```java
Collection<String> names = new ArrayList<>();

// 1. addAll: 一次加入多個元素
names.addAll(Arrays.asList("Anna", "Bob", "Carol", "David"));

// 2. contains: 檢查元素是否存在 (回傳 boolean)
boolean hasGary = names.contains("Gary"); // false

// 3. removeIf: 使用 Lambda 運算式進行條件刪除
// 刪除所有以 'G' 開頭的名字
names.removeIf(name -> name.startsWith("G"));

System.out.println(names);
```


| 介面類型 | 是否有序 | 是否允許重複 | 特色說明 |
|----------|----------|--------------|----------|
| List     | 是       | 是           | 像序列一樣，可以透過 Index（索引）存取（例如 ArrayList） |
| Set      | 否       | 否           | 數學意義上的集合，保證裡面沒有重複的元素（例如 HashSet） |
| Map      | 否       | Key 唯一     | 儲存 Key-Value（鍵值對）。注意：它不繼承自 Collection 介面 |


# 為什麼 Collection 介面沒有 sort() 方法

List 介面有 sort()，但 Collection 沒有

並非所有集合都有「順序」的概念
例如 HashSet 是雜亂存放的，對它進行排序沒有意義

如果你需要排序，變數型別必須宣告為 List
或者使用 Collections.sort() 工具類

```java
Collection<String> col = new ArrayList<>(Arrays.asList("Z", "A", "B"));
// col.sort(); // ❌ 編譯錯誤：Collection 介面沒有定義 sort 方法

List<String> list = new ArrayList<>(Arrays.asList("Z", "A", "B"));
list.sort(Comparator.naturalOrder()); // ✅ 只有 List 支援索引相關的排序

# Map 不屬於 Collection 的子介面

結構：存取 Key-Value (鍵值對)。

限制：

Key (鍵)：必須是唯一的（底層實作類似 Set）

Value (值)：可以重複

組成單位：每一個元素儲存在一個稱為「Node」或「Entry」的結構中。

Collection 是「一群元素」

[A, B, C]

Map 是「鍵值對」

key → value
A → 1
B → 2

==>  結構完全不同，所以不繼承 Collection

# LinkedList 的特性

LinkedList 同時屬於：
- List
- Deque

# List
定義：一種有序的集合，也被稱為「序列（Sequence）」

實作方式：

ArrayList：在記憶體中以連續序列儲存（類似陣列）

LinkedList：透過節點維護指向「上一個」與「下一個」元素的連結

# Queue（隊列）與 Deque（雙端隊列）

用途：主要用於「處理前的緩存」

排序邏輯：強調處理順序，重視頭部（Head）與尾部（Tail）

FIFO (First In, First Out)：先進先出，標準隊列。

LIFO (Last In, First Out)：後進先出，類似 Stack（堆疊）

Deque：功能最強大，同時支援從兩端進行存取與操作

# Set（集合）
數學概念：基於數學集合論，不允許重複元素

特性：本質上是無序的（像是一群關在圍欄裡的混亂物件）

三種主要實作：

HashSet：效能最優，但不保證順序

TreeSet：元素會依特定順序排列（SortedSet）

LinkedHashSet：結合了 Hash 效能並保留插入順序

# Polymorphic Algorithms
這是在集合框架中非常強大的「可重複使用功能」：

定義：指那些可以套用到不同集合實作上的演算法（例如：排序、搜尋、反轉等）

演算法來源：
 - 傳統方式：大部分作為「靜態方法」存在於 java.util.Collections 這個工具類別中
 - 現代方式 (JDK 8 之後)：由於介面功能的增強，部分方法已移至介面本身（作為 Default Method 或 Static Method）

重要性：在維護舊程式碼（Legacy Code）時，仍會頻繁看到大量對 Collections 類別的呼叫