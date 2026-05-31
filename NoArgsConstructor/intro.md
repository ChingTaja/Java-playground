###  Instance Initializer Block

實例初始化區塊是直接在 Class Body 中宣告的一段程式碼區塊

這段程式碼會在類別的 Instance 被建立時執行

實例初始化區塊的執行時機，永遠早於 Class Constructors 中的任何程式碼

可以擁有`複數個`初始化區塊
它們將會依照被宣告的順序依序執行

Java 的 `Instance Initializer`（實例初始化區塊）是：

> 每次建立物件時，會自動執行的一段程式碼區塊。

寫法：

```java
class Person {    
		{ 
			System.out.println("Instance Initializer");   
		}    
public Person() {       
			 System.out.println("Constructor");    
		}
}
```

建立物件：

```java
new Person();
```

輸出：

```
Instance InitializerConstructor
```


### 1. 隱式無參數建構子的「消失機制」

- Java 的預設行為：當一個類別完全沒有宣告任何建構子時，Java 編譯器會自動幫你補上一個隱式的無參數建構子（Implicit No-Args Constructor）
    
- 消失的條件：一旦你手動宣告了任何一個「顯式建構子（Explicit Constructor）」例如帶有參數的建構子）
Java 官方就會立刻收回這個恩惠，`不再`為你提供隱式的無參數建構子。
    
- 解決方案：此時如果你或其他子類別仍需要呼叫無參數建構子，你必須在類別中手動將它寫出來, 手動宣告

### 2. 實例初始化區塊與建構子的執行順序

- 當物件被 new 出來時，不論是 `Parent` 還是 `Child` 類別，**實例初始化區塊（Instance Initializer）的程式碼永遠會比建構子（Constructor）先執行**
    
- 在繼承關係中，這個順序依然打破不了：當建立子類別實例時，父類別的初始化區塊與建構子會先跑完，接著才會執行子類別的初始化區塊與建構子
    

### 3. 用初始化區塊處理 `final` 欄位的致命盲點

- 初始化區塊的定位：實例初始化區塊很適合用來設定物件的「預設值（Default Values）」
    
- 處理 `final` 的嚴重衝突：初始化區塊`非常不適合`用來初始化 `final` 實例欄位
    
- 原因（建構子失去彈性）：因為 `final` 欄位在記憶體中**只能被指派一次數值（Single Assignment）**
如果你在先執行的初始化區塊中硬把值寫死，後續執行的建構子就再也無法透過傳入的參數（Constructor Arguments）來動態調整這個 `final` 欄位的值了（會直接引發編譯錯誤）

###  Static Initializer Block

靜態初始化區塊的底層運作行為：

- 唯一識別特徵
    
    - 靜態初始化區塊與實例初始化區塊在語法上唯一的差別，就是有無加上 `static` 關鍵字。
        
- 「一生只有一次」的執行時機
    
    - 觸發條件：當該類別在應用程式生命週期中`第一次被引用`（例如存取其靜態成員）或`第一次被 new 建構`時，底層的 Class Loader 載入類別時就會立刻觸發。
        
    - 執行次數：不論後續該類別被 new 出多少個實例（Instances），靜態初始化區塊`保證只會執行這麼一次`
        
    - 執行先後：它的執行時間點非常早，必定是在你因任何原因真正使用到該類別之前就先跑完
        
- 多重靜態區塊的管轄
    
    - 單一類別內部允許存在複數個靜態初始化區塊，且可以散落在類別主體內的任何位置
        
    - 它們的執行順序完全不受位置影響，而是嚴格遵循在原始碼中由上而下的編排順序（Appearance Order）依序觸發


### Instance Initializer 和 Static Initializer 的使用情境?


## 一、 Static Initializer（靜態初始化區塊）的使用情境

靜態初始化區塊屬於**類別層級（Class Level）**，在類別第一次被載入記憶體時**只執行一次**。

### 1. 初始化複雜的靜態常數（`static final`）

當你的靜態常數（如 `Map`、`List` 或設定值）無法透過簡單的一行 code 完成初始化，需要邏輯判斷（`try-catch` 或迴圈）時，就必須使用它。

Java

```java
public class SystemConfig {
    public static final Map<String, String> SETTINGS = new HashMap<>();

    // 💡 情境：從外部檔案讀取設定，需要處理異常
    static {
        try {
            // 模擬讀取環境設定檔案
            SETTINGS.put("db.url", "jdbc:mysql://localhost:3306/mydb");
            SETTINGS.put("db.user", "root");
        } catch (Exception e) {
            System.err.println("系統設定載入失敗！" + e.getMessage());
        }
    }
}
```

### 2. 預先載入原生程式庫（Native Libraries）

在進行跨平台底層開發（如 JNI）時，必須在類別使用前確保驅動程式或動態連結檔（`.dll`、`.so`）已載入。

Java

```java
public class ImageProcessor {
    // 💡 情境：確保 OpenCV 或其他 C++ 核心庫在物件 new 出來前就載入記憶體
    static {
        System.loadLibrary("opencv_java4");
    }
}
```

### 3. 一生一次的全局環境初始化

例如註冊資料庫驅動程式、初始化全域的 Log 日誌框架設定等。

## 二、 Instance Initializer（實例初始化區塊）的使用情境

實例初始化區塊屬於**物件層級（Object Level）**，每當你 `new` 一個新物件，它就會在**建構子之前**被執行一次。

### 1. 匿名內部類別（Anonymous Inner Class）的初始化

這是實例初始化區塊最靈魂、也最無可替代的情境
因為匿名內部類別「沒有名字」，所以它**完全無法宣告建構子**
如果你在 new 的當下順便幫它初始化內部資料，只能靠`實例初始化區塊`

Java

```java
public class Main {
    public static void main(String[] args) {
        // 💡 情境：快速建立一個內含預設資料的唯讀 List (雙括號初始化語法)
        List<String> tags = new ArrayList<>() {
            // 外層大括號是匿名內部類別，內層大括號就是「實例初始化區塊」！
            
            {
                // 這就是 instance initializer
                add("Java");
                add("Spring");
                add("Cloud");
            }
        };
    }
}
```

### 2. 提取多個自訂建構子之間的「公共程式碼」

如果你的類別設計了非常多個多載（Overload）的建構子，且你不想用 `this()` 互相呼叫，或`每個建構子不論傳入什麼參數，都必須強制執行某段邏輯`（例如：生成流水號、記錄物件建立的 Log、初始化通用的一般欄位），就可以放在這裡

Java

```java
public class Order {
    private String orderId;
    private long createdAt;

    // 💡 情境：不管用哪個建構子成立訂單，都要自動帶入建立時間與 Log
    {
        this.createdAt = System.currentTimeMillis();
        System.out.println("偵測到新訂單初始化...");
    }

    public Order() {
        this.orderId = "GUEST-" + createdAt;
    }

    public Order(String memberId) {
        this.orderId = memberId + "-" + createdAt;
    }
}
```