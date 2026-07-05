> 「為什麼有些檔案類別（如 `FileReader`）一定要強制寫 `try-catch` 或丟進 `try-with-resources`
> 而建立 `File` 物件時卻什麼都不用？」


# 核心關鍵：File Handle vs File Resource



#### 1. File Handle ：`java.io.File` 類別

當在程式碼寫下 `File file = new File("testing.txt");` 時

**Java 其實根本沒有去硬碟打開這個檔案**

它甚至不在乎這個檔案到底存不存在

- 本質：它只是一個文字路徑（Path Name）的**記憶體抽象包裝**（就像一張寫著地址的便條紙）
    
- 行為：它是用來讓你對操作系統發號施令的（例如：問 OS 這個路徑是檔案還是資料夾？幫我刪除它？幫我建立新目錄？）
    
- 結論：因為它沒有向 OS 索取真正的檔案內容數據，**沒有佔用核心資源，所以它不需要被關閉（Close）
當然也就不需要寫在 `try-with-resources` 裡面
    

#### 2. 檔案資源：`java.io.FileReader` 類別

相反地，當你 `new FileReader(file)` 的那一刻，Java 就會立刻透過 JVM 向作業系統申請：「請幫我把這條路徑的硬碟大門打開，我要讀取裡面的資料了！」

- 本質：它代表的是**真實存在於硬碟上的數據流（Data Stream）**
    
- 行為：作業系統會為它配置記憶體緩衝區、分配 File Descriptor（文件描述符），並鎖定檔案
    
- 結論：它是一個不折不扣的「外部資源」
因為它透過父類別 `Reader` 實作了 `AutoCloseable` 介面
> 一旦開啟就必須被嚴格關閉，否則會造成系統資源枯竭
> 這就是為什麼它必須強制進行例外處理（Exception Handling）的原因


# 傳統 `File` 類別 vs 現代 NIO.2

#### ❌ 傳統 `java.io.File` 模式：「物件導向式行為」

在舊時代，所有的功能都是物件的成員方法（Instance Methods）


```java
File file = new File("files/testing.csv"); // 1. 先用建構子 new 一個實例
if (file.exists()) {                       // 2. 用實例呼叫方法
    System.out.println("找到了");
}
```

- 缺點：功能單薄
如果你問它：「這是不是一個符號連結（Symbolic Link）？」傳統 `File` 直接裝傻，因為它不支援
    

# 現代 NIO.2 模式：「工廠與靜態工具分離」

NIO.2（Java 1.7+）把「路徑的定義」與「對檔案的操作」完全抽離開來：

- `Path` (介面)：純粹用來代表**路徑地址的抽象**
我們不 new 它，而是透過 `Paths.get()` 靜態工廠方法取得它
    
- `Files` (工具類別)：裡面**全部都是靜態方法（Static Methods）**！
它才是真正動手做事的藏鏡人
你想做什麼，就把 `Path` 當作參數丟給它
    


```Java
Path path = Paths.get("files/testing.csv"); // 1. 工廠方法取得 Path 實例
if (Files.exists(path)) {                    // 2. 呼叫 Files 的靜態方法，把 path 丟進去
    System.out.println("2. 找到了");
}
```

#### 為什麼要換成 NIO.2？

除了支援符號連結（`createSymbolicLink`），`Files` 類別還提供了傳統 `File` 完全做不到的超級便利功能（一鍵讀寫）：

- `Files.readAllLines(path)`：一行程式碼，直接把整個文字檔切成 `List<String>` 丟給你，不需要自己開 Reader、寫 while 迴圈
    
- `Files.readString(path)`：直接把整盤檔案內容塞進單一一個巨大的 `String` 裡
    
- 完美相容 Stream API：非常適合現代 Java 的 Functional Programming