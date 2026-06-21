# Locale 與在地化格式輸 心機制（含日期、數字、貨幣）


- 在地化：同一種語言（如英文）在不同國家或地區（如美國、澳洲、加拿大、英國、紐西蘭、印度）之間
會因文化習慣不同而導致日期時間與數字格式呈現差異
例如日期順序（MM/dd vs dd/MM vs yyyy-MM-dd）
12/24 小時制、AM/PM 標示方式、千分位分隔符（逗號/空格/句點）以及貨幣符號與小數點規則等
這些差異並不是語言本身造成，而是 locale（地區設定）所決定的顯示規則
- Locale 類別的組成與操作：Locale 位於 java.util，底層由 BaseLocale 結構組成 
包含語言（language）、國家/地區（region/country）、變體（variant）、書寫系統（script）與擴充資訊（extensions）
可透過 Locale.getDefault() 取得 JVM 當前預設語系
也可以用 Locale.setDefault(Locale) 在程式入口點（main 開始前）強制改變全域語系設定
但一旦某些 API 已初始化就可能被快取影響而不再變動。
- Locale 實例的建立方式：第一種是使用內建常數如 Locale.US、Locale.UK
第二種是透過建構子手動指定語言或語言+地區（如 new Locale("en") 或 new Locale("en","AU")）
第三種是使用 Locale.Builder 透過鏈式 API 建立更精細的 locale（例如 setLanguage("en").setRegion("IN").build()），適合需要精準控制語系與地區行為的場景
- ResourceBundle 與國際化（I18n）：國際化的核心思想是將「程式邏輯」與「使用者可見內容」分離，例如文字訊息、UI 標籤、提示字串等，透過 ResourceBundle 依不同 Locale 動態載入對應語言資源檔（properties），讓同一套程式能在不同國家自動切換語言與顯示內容，而不需要修改程式碼本身
- 格式化器的語系動態套用：DateTimeFormatter.ofLocalizedDateTime(...) 或 withLocale(Locale) 不會修改原本 formatter，而是回傳一個新的 formatter instance，因此可以在迴圈中針對不同 locale 重複使用同一個 base formatter 進行輸出，這種 immutable 設計確保 thread-safe 並避免狀態污染，也讓在地化輸出可以非常靈活地切換語系呈現


❌ 舊寫法（現在用的）
```java
Locale enAU = new Locale("en", "AU");
Locale enCA = new Locale("en", "CA");
```
會出現 deprecation warning。

建議寫法 1：Locale.forLanguageTag（推薦）

最現代、最推薦：
```java
Locale enAU = Locale.forLanguageTag("en-AU");
Locale enCA = Locale.forLanguageTag("en-CA");
```