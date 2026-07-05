- 例外處理機制的基本概念 ：
Exception Handling，當程式存取外部資源（如檔案）時
常會因為檔案不存在或檔名輸入錯誤等預期中的常見問題而引發異常
    
-  Checked Exception 與其繼承關係 ：
java.io.IOException 屬於一種 Checked Exception（受檢例外）
它是許多資源存取錯誤的 Parent Class



# 看到這裡～～～

而 FileNotFoundException 則是其常見的 Subclass
    
- 編譯時期的錯誤提示 ：當呼叫如 Files.readAllLines 等會拋出 Checked Exception 的 Method 時，若未進行處理，編譯器會引發錯誤並提示 unhandled exception
    
- 處理受檢例外的兩種途徑 ：
    
    - 途徑一：修改 Method Signature（方法簽章），在主體後方宣告 throws 子句將 Exception 向上拋出，但若在 main 方法拋出會導致應用程式直接崩潰退出，對使用者較不友善
        
    - 途徑二：使用 try catch block（try catch 區塊）將可能出錯的陳述式包裹起來，在 catch 區塊中進行適當的錯誤處理
        
- 防禦性編程的替代方案 ：除了事後擷取 Exception，也可以使用 File 類別的 Constructor 建立實例，並藉由呼叫 exists 方法在事前主動檢查檔案是否存在，若不存在則印出錯誤訊息並利用 return 提早結束，以防範錯誤發生



**Checked Exception 到底要「丟出去」還是「自己處理」**，先檢查 vs 直接做 的設計差異


##  第一種：直接做 → 讓 Java 丟 Exception


```
Files.readAllLines(path);
```

會報錯：

> unhandled exception: IOException

原因是：

- `IOException` 是 **Checked Exception**
- Java 強迫你「一定要處理」

你有兩個選擇：

### A. throws（往外丟）

```
public static void main(String[] args) throws IOException
```

✔ 優點：

- 寫法乾淨
- 適合「我不處理，交給上層」

❌ 缺點：

- main 丟出去 → 程式直接 crash（不友善）

---

### B. try-catch（自己處理）

```
try {    Files.readAllLines(path);} catch (IOException e) {    e.printStackTrace();}
```

✔ 優點：

- 不會直接 crash
- 可以自訂錯誤處理

❌ 缺點：

- 你可能「抓了但沒真正解決問題」

---

## 第二種：先檢查 → 避免 Exception（防禦式寫法）


```
File file = new File(filename);if (!file.exists()) {    System.out.println("I can't run unless this file exists");    return;}
```


> **Defensive Programming（防禦式程式設計）**

---

### 差異重點

|方法|思想|結果|
|---|---|---|
|try-catch|出事再處理|捕捉錯誤|
|exists() 檢查|先避免出事|不讓錯誤發生|

---


兩種設計哲學：

### 🔴 直接依賴 Exception（被動）

- 假設一切會正常
- 出事再處理

👉 適合：

- 不確定情況（網路、檔案、API）

---

### 🟢 先驗證（主動）

- 先確認條件成立
- 再執行

👉 適合：

- 可以預測的錯誤（檔案不存在）

---

# 1️⃣ LBYL vs EAFP

##  LBYL（Look Before You Leap）

**先檢查再做**

```
if (file.exists()) {    readFile();}
```

### 特點：

- 先確認條件
- 再執行動作
- 避免錯誤發生

### 優點：

- 比較安全
- 比較直觀

### 缺點：

- 程式變長（verbose）
- 有些檢查其實不可靠（race condition）

---

## EAFP（Easier to Ask Forgiveness than Permission）

**先做再處理錯誤**

```
try {    readFile();} catch (IOException e) {    handleError();}
```

### 特點：

- 直接做操作
- 出錯再處理

### 優點：

- 程式乾淨
- 適合「錯誤其實很少發生」的情境
- Python / Java 常用風格

### 缺點：

- debug 有時比較難追

## 哪個比較好？

**看情境**

|情境|建議|
|---|---|
|錯誤很常發生|LBYL|
|錯誤很少發生|EAFP|
|不可預測外部系統（檔案 / 網路）|EAFP 常見|
|可以確定條件|LBYL|

#  Checked vs Unchecked Exception

## Checked Exception

 必須處理（不然不會編譯）

例如：

- `IOException`
- `FileNotFoundException`

 Java 強迫你：

- try-catch
- 或 throws

---

## Unchecked Exception（非受檢例外）

 本質：

```
RuntimeException 的子類
```

例如：

- `NullPointerException`
- `IndexOutOfBoundsException`

---

## 差別：

> Checked Exception = 編譯器逼你處理  
> Unchecked Exception = 編譯器不管你

---

# 3️⃣ Exception propagation（例外傳遞機制）

整個 runtime 行為的核心：

```
發生 exception 的方法        
↓
call method        
↓
main()        
↓
JVM（如果都沒處理）      
↓
程式 crash + stack trace
```

---

### 重點：

- exception 會「往上丟」
- 一層一層找 try-catch
- 找不到 → 程式終止


    
- 傳統用途與現代替代方案 ：finally 最初設計用於執行資源清理（Cleanup Operations），如關閉連線、釋放 Lock 或資源。但自 JDK 7 起，針對關閉資源的操作，官方更推薦使用 try with resources 語法；finally 則多用於 Logging（記錄日誌）或更新使用者介面等其餘必要任務
    
- 潛在缺點 ：過度使用非清理相關的程式碼於 finally 中會降低程式碼的可讀性、增加維護難度，甚至可能因不當處理而隱藏錯誤，使 Debug 更加困難
    
- 多重異常的遮蔽效應 ：若在 try 區塊觸發異常後進到 catch 區塊，而 catch 區塊在處理時又引發了新的異常（例如除以零的 ArithmeticException），finally 依然會執行，但最終向外傳播並呈現給 Call Stack 的將會是最後在 catch 內發生的那一個異常

### 現代寫法（更推薦）

Java 7 之後：

`try-with-resources`

比 finally 更安全：

```
try (FileReader fr = new FileReader(file)) {    // use file}
```

✔ 自動 close  
✔ 不容易寫錯  
✔ 更乾淨