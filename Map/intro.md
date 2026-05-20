### Map 介面的基本架構與特性
Map 是 Java 集合框架的一部分
但它在層級上是獨立的
==並不繼承自 Collection 介面==
它以鍵值對（Key-Value pairs）的形式儲存數據，模擬了字典的行為

- 泛型約束：Map 要求兩個型別參數 K（Key）與 V（Value），兩者都必須是引用型別（Reference types），不能使用基本型別（Primitives）

- 鍵的唯一性：
1. 每個鍵在 Map 中必須是唯一的
2. 且每個鍵只能映射到一個特定的值

HashMap 是無序的（unordered）
LinkedHashMap 則是依插入順序排序（ordered by insertion order）而 TreeMap 是一個排序的 Map（sorted map）


### 不同實作類別的排序行為
根據應用需求的不同
Java 提供了多種 Map 實作類別
其主要差異在於元素的排列順序

HashMap：數據儲存是無序的

LinkedHashMap：根據元素被插入的順序進行排列

TreeMap：根據鍵（Key）的自然順序或自定義比較器進行排序

```java
// 使用 HashMap，因為我們目前不需要特定順序，只在乎存取效率
Map<String, Contact> hashMap = new HashMap<>();

// 若需要記錄聯絡人加入的先後順序，應改用此實作類別
Map<String, Contact> linkedMap = new java.util.LinkedHashMap<>();
```

- 如果不這樣做會導致：若在需要「排序顯示」的場景中錯誤地選用了 HashMap，則每次程式執行時輸出的順序可能不一致，導致使用者體驗不佳或邏輯錯誤。


### 數據寫入邏輯：put 方法與覆蓋機制

與 Set.add() 會因為重複而拒絕寫入不同
Map.put() 採取的是「覆蓋」策略。

- 新增邏輯：如果 Key 不存在，則將 Key-Value 對存入

- 更新邏輯：不論 Key 是否存在，put 永遠會把資料塞進去
如果 Key 已經存在，它會用新的 Value 直接覆蓋（Replace） 掉舊的 Value

Map.put(K key, V value) 的回傳值帶有動態檢查的狀態：

- 情境 A：Key 不存在
    - 將 key 與 value 建立映射關係並存入 Map。

    - 方法回傳：null（代表在此之前，這個 Key 沒有對應任何舊值）。

- 情境 B：Key 已經存在
    - 用新的 value 覆蓋掉該 Key 原本對應的舊 Value
    - 方法回傳：舊的 Value（它會把被你踢掉的那個前任 Value 傳回來給你，方便你做紀錄或比對）

```java
Map<String, String> dict = new HashMap<>();

// 第一次放入 "put" 的定義
dict.put("put", "To place something in a location"); 

// 第二次放入相同 Key "put" 的不同定義
// 這行代碼解決了數據更新的需求：新的定義會替換掉舊的
String oldDefinition = dict.put("put", "A verb meaning to set"); 

System.out.println("當前定義: " + dict.get("put")); // 輸出新的定義
```

- 如果不這樣做會導致：若開發者誤以為 put 在 Key 重複時會失敗或拋出異常，可能會導致原本重要的舊數據在不知情的情況下被後來的數據覆蓋，造成邏輯上的錯誤（例如在處理 Mickey Mouse 的多筆聯絡資訊時，只剩下了最後一筆紀錄）

### 數據檢索與預設值處理 

Map 提供方式來查找與 Key 關聯的數據
但也必須考慮到「鍵不存在」的情況

- get(Object key)：回傳關聯的值，若找不到則回傳 null

- getOrDefault(Object key, V defaultValue)：這是 JDK 8 引入的便利方法，若找不到鍵，則回傳指定的預設值，以避免後續產生 NullPointerException

```java
Map<String, Contact> contacts = new HashMap<>();

// 嘗試獲取不存在的聯絡人 "Chuck Brown"
// 解決了直接獲取 null 導致後續程式崩潰的風險
Contact chuck = contacts.getOrDefault("Chuck Brown", new Contact("Default Name"));

System.out.println(chuck.getName()); // 即使找不到也不會報錯，會印出 "Default Name"
```

- 如果不這樣做會導致：若直接使用 get 且未檢查是否回傳 null 就進行操作（例如調用該物件的方法），會導致程式在執行期間拋出 NullPointerException 並閃退

### 條件式寫入：putIfAbsent 的應用

putIfAbsent 僅在鍵不存在時才執行插入，這對於「保留原始版本」的場景非常有用

```java
// 僅當 "Mickey" 不在 map 中時才加入
Contact duplicate = contacts.putIfAbsent(current.getName(), current);

if (duplicate != null) {
    // 註解：此處 duplicate 是 map 中現有的舊值
    // 如果我們仍想合併，可以使用 put 將合併後的結果覆蓋進去
    contacts.put(current.getName(), current.mergeContactData(duplicate));
}
```

- 如果不這樣做會導致：普通的 put 會無條件覆蓋。若業務規則要求「優先保留第一筆輸入的資訊」，使用普通 put 將導致重要初始數據被後續重複項洗掉

### 高效數據合併：JDK 8 merge 方法

merge 方法是目前最優雅的處理方式

它接收一個 Key、一個 Value，以及一個定義「衝突時如何處理」的 BiFunction


```java
// 極簡化合併：使用方法引用 (Method Reference)
fullList.forEach(contact -> 
    contacts.merge(contact.getName(), contact, Contact::mergeContactData)
);
/* 
註解：
1. 若鍵不存在：直接放入 contact。
2. 若鍵已存在：執行 Contact::mergeContactData 邏輯，
   將「地圖中的舊值」與「當前的 contact」合併，並自動更新回地圖。
這解決了多行 if-else 判斷的冗長問題。
*/
```
- 如果不這樣做會導致：開發者必須手動編寫繁瑣的判斷邏輯（先 get、判斷 null、執行合併、再 put）
- 這不僅增加了代碼量，也提高了邏輯出錯的可能性，且難以維護
