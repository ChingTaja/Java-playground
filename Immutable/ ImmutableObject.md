**Immutable Object（不可變物件）設計原則**，目的是避免「物件被意外或惡意修改」造成 side effects


## 一、 為什麼要使用不可變物件（Immutable Object）？

- **防止非預期的副作用**：物件一旦被建立，其狀態（State）就絕對不會再發生改變，藉此消除在半路被偷改資料的風險
    
- **提升物件安全性（Secure Object）**：呼叫端的程式碼無法透過惡意或誤用的方式去修改它
    
- **簡化並行設計（Concurrency Design）**：由於狀態不可變，在多執行緒環境下共享物件會變得非常簡單且安全
    

## 二、 打造不可變類別的五大策略

五個核心策略

1. **將實例欄位（Instance Fields）宣告為 `private` 與 `final`**：限制存取權限並鎖死引用
    
2. **絕對不要定義任何 Setter 方法**：不提供外部修改欄位數值的管道
    
3. **在任何 Getter 方法中建立防禦性複製（Defensive Copies）**：回傳資料時複製一份副本出去，避免暴露內部的可變引用
    
4. **使用建構子（Constructor）或工廠方法（Factory Method）來設定資料**：傳入參數時，若包含可變的引用資料（Mutable reference data），必須先進行複製（Make copies）再存入欄位
    
5. **將類別標記為 `final`，或將所有建構子設為 `private`**：防止類別被繼承或惡意擴充


# 實作

- **防線一：落實 `private final` 與拔除 Setter**
    
    - 成員變數加上 `final` 後，編譯器（Compiler）會嚴格把關，只要有任何方法試圖去變更（Mutate）該欄位，當場拋出編譯錯誤
        
- **防線二：雙向防禦性複製（Defensive Copying）**
    
    - **入口端（建構子）**：當接收的參數是陣列（Array）或集合（Collection）等可變物件時，必須使用 `Arrays.copyOf()` 複製一份存入。防止呼叫端在物件建立後，回頭修改當初傳進來的原始陣列。
        
    - **出口端（Getter）**：回傳陣列時，絕對不能直接回傳內部欄位的原始指針（Reference），同樣必須當場影印一份副本丟出去，避免外部呼叫端直接透過索引（如 `kids[0] = xxx`）改壞內部資料。
        
- **防線三：警惕 `protected` 權限對不可變性的破壞**
    
    - 惡意修改情境證明：僅僅是把欄位改成 `protected` 方便子類別開發，就會門戶大開。子類別可以輕易繞過 Getter，直接向外部暴露父類別的原始可變欄位。
        
- **防線四：使用 `final` 方法截斷多型漏洞**
    
    - 為了防範子類別透過覆寫（Override）Getter 來搞鬼，必須在關鍵的 Getter 方法加上 **`final`** 關鍵字（例如 `public final PersonImmutable[] getKids()`），阻止多型（Polymorphism）帶來的穿透威脅
        
- **未來展望：密封類別（Sealed Classes）**
    
    - 開放繼承（Inheritance）是不可變物件的潛在天敵。除了把整個類別宣告為 `final class` 這種全面封鎖的手段之外，現代 Java 更推薦使用 **`sealed class`**，它可以精準指定「只有哪些合法的類別允許繼承我」，在擴充性與安全性之間取得最佳平衡