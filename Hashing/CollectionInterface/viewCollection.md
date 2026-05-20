# 什麼是 View

本身並不儲存元素
而是依賴一個負責儲存資料元素的「後端支援集合（Backing Collection）」

先前在使用 Set 的 headSet、tailSet 和 subSet 方法時已經見過這種機制

# view collections 的目的

它們讓我們能夠直接操作這些集合
而不需要真正去了解資料在底層儲存的確切細節
換句話說
我們不需要為了操作不同型態的資料，而去重複學習一套全新的方法

#  HashMap
以 HashMap 為例，它實作了 Map 介面
並且在其內部擁有一個靜態巢狀類別（static nested class）叫做 Node
這個 Node 實作了 Map.Entry 介面。HashMap 在其內部維護了一個由這些 Node 組成的陣列
該欄位名稱叫做 table
這個陣列的大小由 Java 自動管理，而資料要存放在陣列的哪個索引位置（Index）
則是透過雜湊函數（Hashing Functions）計算決定的
正因如此，HashMap 是無序的（not ordered）

![alt text](<截圖 2026-05-17 晚上10.28.16.png>)

# 可以從 Map 中取得的三個 view collection , 它們分別是：keySet、entrySet 和 values

Map 擁有許多 Keys
且這些鍵是不能重複的
因此，透過在任何 Map 物件上呼叫 keySet 方法
就能以 Set 視圖的形式將這些鍵取回

每一個「鍵值對」在底層都是以 Entry 的實例形式儲存
而這組「鍵與值」的組合也必定是唯一不重複的因為鍵本身就具有唯一性
我們可以透過 entrySet 方法
取回由這些 Entry（在 HashMap 中即為 Node 節點）所組成的 Set 視圖

最後，Map 裡也儲存了 Values
並透過鍵來引用它們
值是可以重複的
這意味著多個不同的鍵有可能同時指向同一個值
可以透過在 Map 實例上呼叫 values 方法

1. keySet() -> 回傳 `Set<K>`
- 背後邏輯：因為 Map 的核心規則是「Key 絕對不能重複」
- 型態選擇：既然裡面的元素具有唯一性，最適合代表它的 Java 集合介面就是 Set

2. entrySet() -> 回傳 `Set<Map.Entry<K, V>>`
- 背後邏輯：這個視圖把一組組的 (Key, Value) 包裝成一個個 Entry 物件打包丟出來
- 型態選擇：因為 Key 是唯一的
所以不管 Value 有沒有重複，(Key + Value) 的這個打包組合在整張地圖裡也絕對是唯一、不可能撞衫的
因此，它同樣回傳 Set
- 用途：當需要同時遍歷（Loop）Key 和 Value 時
用 entrySet() 的效能是最高的
因為它一次就把底層的 Node 整個拿出來，不需要像 keySet() 那樣拿了 Key 還要再回 Map 裡呼叫 get(key) 重新搜尋一次


3. values()  ->  回傳 `Collection<V>`

- 背後邏輯：多個不同的鍵有可能同時指向同一個（例如：員工 A 的部門是 "IT"，員工 B 的部門也是 "IT"）
- 型態選擇：因為 Value 允許重複
所以它絕對不能用 Set
但 Java 官方又不想強迫限制它必須是 List（因為底層不保證有順序性）
所以最終折衷選擇了所有集合的大家長——Collection 介面


# 由 keySet 方法所回傳的 Set，是由原本的 Map 在底層支援（backed）的

對 Map 所做的任何變更都會即時反應在該 Set 中
反之亦然（雙向連動）
此 Set 支援「元素刪除」操作，這會同步將原本 Map 中對應的映射關係（鍵值對）一併移除
可以在此視圖上使用 remove、removeAll、retainAll 以及 clear 方法