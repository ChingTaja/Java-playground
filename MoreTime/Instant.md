# 把時間拆成三種完全不同的概念

1. Instant
Instant = timeline 上的單一不可分割事件點

只回答一件事：「某件事什麼時候發生？」

內部結構：
```
long seconds + int nanoseconds
```

特性：
- 永遠是 UTC
- 不含時區
- 不含日期語意（人類視角）

用途：
- log timestamp
- 系統事件
- 分散式系統同步
- 資料庫時間戳

Instant = 世界時間軸上的點

2. Period（日期差｜人類時間）
Period = 年 / 月 / 日 的時間跨度
核心概念：

描述「日曆上的距離」

特性：
受月份長度影響
受閏年影響
有人類語意

用途：

幾歲
幾個月後
日期差距計算

3. Duration（時間差｜機器時間）
Duration = 秒 / 奈秒的時間跨度

===>  完全線性時間

特性：
不管日期
不管月份

純數學計算

用途：
- performance measurement
- timeout
- sleep / delay


二、三者關係（很重要）
Instant → 點
Period  → 日曆距離
Duration → 物理距離

 Instant 是時間軸，Period/Duration 是在時間軸上量距離的方法

三、架構設計核心
Period / Duration 的共同點
Period + Duration = TemporalAmount
但它們不是：
❌ Temporal
❌ TemporalAccessor

它們是：
===> 「時間的量」，不是「時間的點」

本質：

TemporalAmount = 可以加減的時間數量

四、Epoch（時間系統的起點）

定義：
1970-01-01 00:00:00 UTC

又稱：
Unix Time
POSIX Time
Epoch Time

Java 對應：
```
Instant.EPOCH
LocalDate.EPOCH
```
核心用途： 把「時間」變成可以計算的數字基準

==>  Epoch = 時間的零點

五、UTC vs GMT（時間標準演進）
- GMT（舊）
太陽時間
天文觀測基準

- UTC（現代）
原子時間
精準穩定

關係：
UTC ≈ GMT（但更精準）

Java 使用： 幾乎全部基於 UTC


===>  UTC = 現代標準時間核心

六、Time Zone（時區的本質）
一個時區 = 兩個東西：
UTC offset + DST rules
1. Offset（固定偏移）

例如：

+08:00
-05:00
2. DST（夏令時間）

會隨季節變化

重點：
同一城市 ≠ 永遠同一時間

Java 處理方式： IANA TZDB（已內建）

TimeZone = 規則 + 偏移

七、Java 時間 API 新舊差異

- 舊系統（不推薦）
Date
Calendar
TimeZone
SimpleDateFormat
 - 問題：
    可變
    不安全
    API 混亂
- 新系統（java.time）
    - immutable
    - thread-safe

分層清楚
核心三角：
LocalDate（人類）
Instant（機器）
ZonedDateTime（地理）

 java.time = 現代標準時間系統

八、System 時間 API（本質差異）

1. currentTimeMillis()
Epoch-based real-world time

用途：
timestamp
logging

2. nanoTime()

JVM monotonic clock

用途：
performance measurement
不能當時間
差異總結：

方法	            是否真實時間	    用途
currentTimeMillis	YES         	世界時間
nanoTime        	NO	               計時

millis = 世界時間 / nano = 計時器

九、

Java 時間系統其實是：

Instant（世界時間軸）
    ↓
ZoneId（地區解釋）
    ↓
LocalDateTime（人類時間）


- 時間軸上的核心概念區分
    
    - Instant（時間點） ：代表時間軸上一個特定且不可分割的單一時刻（Event），例如某個具體事件的發生時間
	      在 Instant 類別中，底層由兩個欄位構成：一個 long 型態的秒數欄位，以及一個 int 型態的奈秒欄位
        
    - Period（日期區間） ：指日期時間軸上的「時間跨度」或「時間段」，主要以日期單位（如年、月、日）來計算與呈現流逝的時間
        
    - Duration（時間區間） ：同樣代表時間的跨度，但其著重於以時間為基礎的計量單位（如小時、分鐘、秒數）
        
- 核心介面與設計架構
    
    - Period 與 Duration 在 Java 的類別架構中，並沒有實作 Temporal 或是 TemporalAccessor 介面
        
    - 它們實作的是 TemporalAmount 介面，這代表它們在架構上的本質並非特定的時間點或日期，而是屬於一種「時間的量」或「時間計數器」，專門用來處理並**記錄兩個時態物件之間所經歷的流逝時間**
        
- 基準時間與 Unix Epoch
    
    - 為了讓儲存的秒數具有實質的測量意義，許多程式語言與作業系統需要一個共同的預設時間起點，這被稱為 Epoch Time
        
    - 標準的 Epoch 起點被設定為：1970 年 1 月 1 日星期四 00:00:00 UTC，也被輪流稱為 Unix Time、POSIX Time 或 Unix Epoch Time
        
    - 這個日期是早期 Unix 工程師為了方便比較不同時間戳記而制定的任意起點，目前在 Java 的 LocalDate 以及 Instant 類別中，都內建了一個名為 EPOCH 的常數來代表這個時間起點
        
- 國際標準時間（UTC 與 GMT）
    
    - 地球上的經線（子午線）歷史上以英國倫敦郡的格林威治（Greenwich）作為本初子午線（Prime Meridian），即零度測量值，成為統一時間的起點
        
    - GMT（格林威治標準時間）是基於太陽在天空中位置的太陽時；而 UTC（國際標準時間）則是在 1972 年取代 GMT、基於極高精確度的原子時所制定的標準
        
    - GMT 與 UTC 在一整天之中的誤差最多可達 0.9 秒，因此在一般不需要極高精密微秒級計算的常規開發情境下，兩者經常可以互換使用
        
- 時區與本地化規則（Time Zone 与 Offset）
    
    - 一個完整的時區（Time Zone）是由兩個核心部分所組成：第一個是 UTC offset（相對於國際標準時間的偏移量），第二個則是選填的日光節約時間（Daylight Savings Time）規則資訊
        
    - 偏移量的標示方式包含正負號、兩位數小時與兩位數分鐘（例如紐約為 -05 00，代表 UTC 減 5 小時；雪梨為 +11 00，代表 UTC 加 11 小時）。其中正號代表偏移量在本初子午線以西，負號則在本初子午線以東
        
    - 由於日光節約時間規則，同一個城市的偏移量會根據一當中的具體日期而發生改變（例如雪梨在 10 月的第一個星期日會增加 1 小時偏移量，代碼由 AEST 轉為 AEDT）。Java 的時區資料庫（TZDB）主要源自 IANA（網際網路號碼分配局），並且這些複雜的動態更動規則都已經完整封裝在 Java 的內建類別中，開發者不需手動撰寫複雜邏輯
        
- Java 時間 API 的新舊更迭
    
    - 舊版 API 限制 ：在 JDK 8 之前，傳統程式碼廣泛使用 java.util 套件中的 Date、TimeZone、GregorianCalendar，以及 java.text 套件中的 DateFormat 和 SimpleDateFormat
        
    - 新版 API 推薦 ：在現代 Java 開發中，不鼓勵在新的程式碼中使用上述舊類別，應全面改用 java.time 套件所提供之 immutable（不可變）且 thread-safe（執行緒安全）的現代時間類別，避免併發安全性與設計缺陷問題
        
- System 類別兩種時間測量方法的本質差異
    
    - System.currentTimeMillis() ：此方法回傳的是自 1970 年 1 月 1 日午夜 UTC 基準時間以來所流逝的毫秒數。其數值精確度依賴於底層的作業系統時鐘，適合用來提供與真實世界掛鉤的時間戳記（Wall Clock Time），或者記錄資料創建時間
        
    - System.nanoTime() ：此方法使用的是 JVM 內部的高解析度時間來源，回傳自某個任意起源點（該點與 Epoch Time 無關，甚至可能是未來的某個時間點）所流逝的奈秒數。由於不同 JVM 實例的起源點不同，它絕對不能用於呈現真實時間或當作資料庫的時間戳記，其核心目的是為了精確測量單一 JVM 實例內部某段程式碼、方法調用（Invocations）所流逝的精細效能時間跨度