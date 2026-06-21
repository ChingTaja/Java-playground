1. DateTimeFormatter 自訂 Pattern 規則（日期格式控制核心）

在 `DateTimeFormatter.ofPattern` 中
pattern 字母大小寫與重複次數會直接影響輸出格式：
EEEE 代表完整星期名稱（Monday / 星期一）
MMMM 代表完整月份名稱（January / 一月）
d 代表日期數值（1–31，不補零）
y 或 u 代表年份（通常用 yyyy 表示四位數年份）
這套 pattern 是 Java 日期輸出的底層語法，理解後可以完全控制輸出格式而不依賴預設 locale

2. IntelliJ IDEA 懸浮文件設定（開發輔助工具）

IntelliJ 可透過 Settings → Editor → Code Editing → Show documentation on Hover 開啟 API 懸浮說明功能
當滑鼠移動到類別或方法上時會即時顯示官方 Javadoc，對學習 DateTimeFormatter、Locale、Temporal 等 API 非常重要
能快速理解方法用途而不必跳轉文件

3. Locale 名稱顯示差異（語言 vs 母語顯示）

`locale.getDisplayName()` 會使用 JVM 預設語言（通常英文）來顯示該地區名稱
而 locale.getDisplayName(locale) 則會使用「該 Locale 自己的語言」來顯示名稱
例如 France 會顯示 français。這種差異本質上反映「系統語言 vs 使用者語言」的雙層國際化設計概念

4. printf / String.format 的在地化輸出能力（另一種 i18n 工具）

Java 除了 DateTimeFormatter，也可以用 String.format(Locale, ...) 或 printf(Locale, ...) 做在地化輸出，例如 %1$tA %1$tB %1$te %1$tY 可以輸出星期、月份、日期與年份，效果與 DateTimeFormatter 類似
但屬於 legacy style formatting。適合快速輸出，但彈性與可維護性較低。

5. 數字與貨幣格式國際化（NumberFormat 行為差異）

不同國家的數字格式差異非常大，例如美式為 1,000.50，德文/義大利為 1.000,50，法文為 1 000,50，這些差異完全由 Locale 控制NumberFormat 是 mutable 類別，可以動態設定如 setMaximumFractionDigits() 來控制小數精度
因此在多國輸出時必須特別注意格式一致性問題

6. 貨幣格式與 Currency 類別（金融標準化）

`NumberFormat.getCurrencyInstance(locale)` 會根據地區自動加上貨幣符號（NT$, $, €, ¥）並處理小數規則（例如日圓無小數）Currency.getInstance(locale) 則提供 ISO 標準貨幣代碼（TWD / USD / EUR）與該貨幣的本地名稱，用於金融系統中的標準化表示與交換

7. Scanner 與 Locale 的文化解析差異（輸入層陷阱）

BigDecimal 建構子非常嚴格，只接受純數字格式（例如 1234.56），不接受千分位符號。但 Scanner.nextBigDecimal() 會依 Locale 解析輸入格式，例如在 Locale.ITALY 中，. 是千分位、, 是小數點，因此正確輸入為 1.000,50，輸入 1,000.50 會直接拋出 InputMismatchException
這代表「輸入解析」與「物件建構」遵守不同規則，是國際化常見坑

8. 國際化（i18n）完整架構延伸（系統級設計）

Java 的國際化不只包含格式化，還包含完整架構設計：日期/時間/數字/貨幣由 java.time + NumberFormat 處理，而文字內容（UI、訊息、提示）則透過 ResourceBundle 管理，將語言內容從程式碼中抽離，形成可替換的多語系系統
這種設計讓同一套程式可以在不同國家自動切換顯示邏輯，而不需要修改核心程式碼