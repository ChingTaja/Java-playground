### Collections.unmodifiableList

### 1. 它是 Java 內建的 Wrapper

這個方法並不會去複製或重新 new 出一個 ArrayList 的資料結構
相反地，它是在你傳進去的原始 List（例如 `students`）外面，**套上一個唯讀的外衣**

這個外衣類別在 JDK 內部的運作邏輯非常簡單粗暴：

- 當你呼叫 `get(index)` 讀取資料時，它會老老實實地幫你穿透過去，跟原始 List 拿資料
    
- 當你呼叫 `add()`、`remove()`、`set()` 等會修改集合結構的方法時，它內部沒有任何實作邏輯，就是**直接拋出 `UnsupportedOperationException` 異常**
    

### 2. 它是設計模式中的「代理/裝飾模式（Proxy/Decorator）」

這不是我們自己寫的限制，而是 Java 透過這個內建方法提供給開發者的防禦機制

- 好處：你不需要自己用 `for` 迴圈去寫一個禁止別人呼叫 `add` 的自訂類別，只要一行 `Collections.unmodifiableList`，Java 就幫你封鎖了所有修改結構的管道
    
- 限制：
	  正因為它是 Java 內建的「視窗（View）」，它跟原始 List 之間有一條無形的線連著
	  一旦「原始 List」在其他地方被新增了元素，這個內建的唯讀視窗也會同步看到最新的變化
	  因此它才被稱為**不可修改（Unmodifiable）視圖**，而非完全獨立不變的不可變集合

# 不可修改集合（Unmodifiable Collections）「並不等於」不可變集合（Immutable Collections）


只有當集合本身的元素也完全是不可變的（Fully Immutable）時，它們才會成為真正的不可變集合

它們只是功能受到限制的集合，目的在於協助我們將「可變性（Mutability）」降到最低

你無法從一個不可變集合中移除、新增或清除（Clear）元素。你同樣也無法替換（Replace）或排序（Sort）裡面的元素

所有會改變狀態的 Mutator 方法都會直接拋出 `UnsupportedOperationException` 異常

此外，你無法建立一個包含 `null` 值的這類集合

|**特性 / 概念**|**Unmodifiable Collections (不可修改集合)**|**Immutable Collections (不可變集合)**|
|---|---|---|
|**集合結構（增刪）**|**被封鎖**（無法 `add`, `remove`, `clear`）|**被封鎖**（無法 `add`, `remove`, `clear`）|
|**集合內部物件狀態**|**允許改變**（如果元素是 Mutable 型別）|**絕對無法改變**（元素必須是 Fully Immutable）|
|**常見實現方式**|`Collections.unmodifiableList()`|`List.of()`, `List.copyOf()` 且元素為不可變|
|**底層角色**|它是原始集合的一扇**唯讀視窗（View）**|它是一個完全獨立、凍結狀態的**資料快照（Snapshot）**|

個主要的集合介面（Collection Interfaces）
也就是 `List`、`Set` 和 `Map`——在它們各自的介面上，都提供了能根據其集合型別來獲取「不可修改複本（Unmodifiable Copy）」的方法

此外，`java.util.Collections` 工具類別也提供了可以用來獲取「不可修改視圖（Unmodifiable Views）」的方法



## Unmodifiable Collections (不可修改的集合複本) vs. Unmodifiable Collection Views (不可修改的集合視圖) 

| 集合型別 | Unmodifiable Copy of Collection    | Unmodifiable View of Collection                     |
| ---- | ----------------------------------------------------------- | -------------------------------------------------------------------------------------------------------- |
| List | `List.copyOf` `List.of`          | `Collections.unmodifiableList`                                |
| Set  | `Set.copyOf` `Set.of`    | `Collections.unmodifiableSet` `Collections.unmodifiableNavigableSet` `Collections.unmodifiableSortedSet` |
| Map  | `Map.copyOf` `Map.entry(K k, V v)` `Map.of` `Map.ofEntries` | `Collections.unmodifiableMap` `Collections.unmodifiableNavigableMap` `Collections.unmodifiableSortedMap` |
