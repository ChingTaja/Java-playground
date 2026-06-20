


- **計分卡機制與 EnumMap 應用**：玩家計分卡（scoreCard）使用 `EnumMap` 實作，這比一般 Map 更有效率。初始化時會將所有的 `ScoredItem` 列舉值放入 Map 中並預設為 `null`，代表該類別尚未被計分
    
- **Stream 串流管線篩選**：在 `getItemList` 方法中，利用 Stream 串流機制對計分卡的 `entrySet()` 進行 `filter` 篩選，過濾出值為 `null` 的項目，再透過 `map` 將 Enum 的 Key 轉成字串名稱並收集成 `List`，以動態顯示當前合法的未計分類別
    
- **隨機與重擲邏輯機制**：透過 `Random().ints()` 方法動態計算出需要補擲的骰子數量（`5 - currentDice.size()`），經由排序（`sorted`）與封裝（`boxed`）後加入玩家清單。玩家可透過輸入指令來移除、全清（ALL）或保留骰子
    
- **控制台流程解耦（Decoupling）**：為了讓 `DicePlayer` 能直接獲取輸入，在 `GameConsole` 中新增了 `public static` 的 `getUserInput` 封裝 Scanner 的讀取邏輯，使玩家類別不需要自己維護 Scanner 實例
    
- **選單排序與標準動作結合**：`DiceGame` 繼承自 `Game`，在 `getGameActions` 中使用 `LinkedHashMap` 來確保行為選項 R（Roll Dice）能以預期順序顯示，並透過 `map.putAll(getStandardActions())` 繼承內建的 Q（離開）與 I（資訊）等標準動作



一、隨機數與 Stream 基礎處理
1. 隨機數產生（Random Int Stream）
- random.ints(count, origin, bound)
- 產生「指定數量」的隨機整數串流
- bound 是 不包含（exclusive）上限

用途：一次產生多顆骰子

2. Stream Pipeline 操作流程

流程：
```
ints() → sorted() → boxed() → toList()
```

sorted() → 排序骰子
boxed() → int → Integer（才能進 List）
toList() → 收集成 List

用途：把「原始數據流」變成「可操作集合」

二、集合操作與 Mutation（核心邏輯）
1. 直接修改原物件（Mutation）
使用 .addAll()
使用 .clear()

- 不回傳新 List
- 直接改 currentDice

✔ 優點：簡化流程
⚠ 缺點：狀態不可預測性較高

2. List.remove 的陷阱
```
List<Integer>
```
會有兩種 remove：

- remove(int index) → 移除位置
- remove(Object o) → 移除數值

⚠ 問題
currentDice.remove(1); 

===> 可能被當成 index，而不是 value

✔ 正確寫法
```java
currentDice.remove(Integer.valueOf(value));
```

===> 強制走「刪數值」版本

三、遊戲流程控制（Interaction Design）

1. do-while 互動模式

用在： 重骰 , 確認骰子 ,選擇計分類別

「一直重骰，直到玩家按 Enter 確認」

```java
do {
    rollDice();
} while (!pickLosers());
```

2. Scanner + blank 控制流程

空輸入 = 結束重骰流程

```java
if (userInput.isBlank()) {
    return true;
}
```

四、計分系統設計（EnumMap）
1. 使用 EnumMap

```java
Map<ScoredItem, Integer>
```
✔ 優點：
比 HashMap 更快
專為 Enum 設計

2. 初始化方式
```java
for (ScoredItem item : ScoredItem.values()) {
    scoreCard.put(item, null);
}
```
===> null = 尚未使用

3. 概念
狀態	意義
null	未計分
number	已使用

五、Stream 過濾計分卡
1. 取得可用項目
```java
// entrySet() 是 Map 用來「一次拿到 key + value」的標準方法
scoreCard.entrySet()
    .stream()
    .filter(e -> e.getValue() == null)
    .map(e -> e.getKey().name())
    .toList();
```
2. 流程拆解
entrySet → (key, value)
filter → 找未使用
map → 只取 enum name
toList → 變選單

用途：動態顯示「還能選的分類」

六、骰子重擲機制
核心邏輯

5 - currentDice.size()

每次補齊到 5 顆骰子

流程
1. 計算缺少幾顆
2. Random 產生
3. sorted 排序
4. addAll 加回去

玩家操作

- ALL → 全重骰
- 數字 → 移除指定骰子
- Enter → 確認計分

七、架構設計（Game System）

1. 控制台解耦（Console Decoupling）
```java
GameConsole.getUserInput()
```

將 input 邏輯集中管理

✔ 好處：

- Player 不需要 Scanner
- 降低耦合
- 可測試性提高

2. GameAction 系統

每個動作是一個 function
用 Character 當 key（選單）

3. LinkedHashMap 順序控制
```java
new LinkedHashMap<>(Map.of(...))
```
👉 保證：

R → Roll Dice
I → Info
Q → Quit

依照順序顯示