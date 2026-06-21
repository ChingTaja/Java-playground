# ResourceBundle 回溯機制 + UI 國際化整合流程

一、這段系統在做什麼

這個範例是在模擬一個：

支援多國語言的桌面應用程式（Swing GUI + I18N）

系統需要同時處理三件事：

不同語言文字（ResourceBundle）
不同 UI 元件文字（UIComponents）
最終 GUI 顯示（JOptionPane）

實際使用場景像是：

多國客服系統
全球化桌面工具
國際版管理系統
多語系安裝程式

二、ResourceBundle 回溯尋找鏈（核心機制）

當你執行：
```java
ResourceBundle.getBundle("BasicText", new Locale("fr", "CA"));
```
並查詢：
```java
rb.getString("hello")
```
Java 會啟動一個「逐層降級搜尋機制」，確保一定能找到可用資源

三、四階段搜尋流程（Fallback Chain）
### 第一關：完全匹配（最精準）
BasicText_fr_CA.properties
意義
語言 + 國家完全吻合
最精準、最在地化版本

👉 如果找到 key → 直接回傳（結束）

### 第二關：語言匹配（通用區域語言）
BasicText_fr.properties
意義
忽略國家差異
使用「純法文通用版本」

例如：

法國法文
加拿大法文
比利時法文

共用一份翻譯

### 第三關：系統預設 Locale（JVM fallback）
BasicText_en_US.properties（或系統預設）
意義

如果使用者語言完全沒有支援：Java 會改用「電腦本身的預設語言」

例如：

作業系統語言
JVM default locale

### 第四關：基底檔案（最後保底）
BasicText.properties

這是「最後安全網」：

沒有語言
沒有國家
沒有條件

👉 一定會存在的預設內容

⚠️ 如果這裡還找不到 key

就會拋出：MissingResourceException

四、整體搜尋本質（你要記的核心觀念）

一句話：

ResourceBundle 是「從最精準一路退到最通用的字典搜尋系統」

五、IntelliJ Resource Bundle Editor（開發工具概念）

這個工具的本質是：

把分散的 properties 檔案變成「Excel 式翻譯表」