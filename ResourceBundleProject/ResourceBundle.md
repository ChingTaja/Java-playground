一、這套機制在解決什麼問題

在實際軟體系統中，應用程式通常需要支援不同國家與語言，例如：

美國英文（en_US）
加拿大法文（fr_CA）
日本日文（ja_JP）
中文（zh_TW）

如果每個語言都寫一套程式碼，會變成：

維護困難
重複邏輯大量增加
語言切換成本極高

因此 Java 提供：

ResourceBundle：用來「依 Locale 動態載入對應語言資源」

讓同一份程式碼可以根據使用者所在地，自動切換顯示內容。

二、ResourceBundle 核心概念
1. 本質
java.util.ResourceBundle

是一個：

專門管理「國際化資源」的抽象類別

2. 主要用途

用來解決：

多語言文字
UI 標籤
訊息提示
錯誤訊息
3. 常見實作

最常見的是：PropertyResourceBundle

它會從 .properties 檔案載入資料

三、Properties 檔案規則
1. 基本結構
hello=Hello
world=World
2. Key-Value 模型
類型	說明
Key	程式查詢用（固定）
Value	顯示內容（可變語言）
3. 檔案命名規則
BaseName + Locale + .properties

例如：

BasicText.properties
BasicText_en.properties
BasicText_en_US.properties
BasicText_fr_CA.properties
4. 註解規則

properties 檔案支援：

# comment
! comment
四、Key 的限制與規則
1. 必須一致

所有語言檔案中：

hello
world

必須完全一致

2. Case-sensitive（大小寫敏感）
world ≠ World
3. 查無 Key 的結果

如果 key 不存在：

MissingResourceException
4. 設計意義

Key 是「跨語言穩定接口」，Value 才是變動內容

五、ResourceBundle 載入機制（核心重點）
1. 基本呼叫
```java
ResourceBundle.getBundle("BasicText", locale);
```
2. 搜尋階層（Hierarchy）

Java 會依照 Locale 逐層查找：

Language → Script → Country → Variant
3. 查找順序概念

例如：

en_CA

會依序找：

BasicText_en_CA.properties
BasicText_en.properties
BasicText.properties（fallback）

4. Script 優先權說明

在新版 Java Locale 模型中：

Script > Country > Variant

表示語言變體（如簡繁體）可能優先於國家

5. fallback 機制

如果找不到：

👉 最後一定會回到 base file：

BasicText.properties

6. 設計價值

這讓你可以：

只寫差異部分
不需要每個語言重複全部內容
降低維護成本

六、IntelliJ / 開發環境設定
1. Resource root 設定

在 IntelliJ 中：

resources folder → Mark as Resources Root
2. 為什麼要設定？

因為：

properties 檔案必須在 classpath 中才會被 ResourceBundle 找到

3. 沒設定會發生什麼？

會出現：

MissingResourceException

或：

Can't find bundle for base name
七、整體運作流程

可以用這個流程記：

Step 1：程式指定 Locale
en_CA
Step 2：呼叫 ResourceBundle
getBundle("BasicText", locale)
Step 3：開始搜尋檔案

從最精準 → 最通用：

en_CA → en → base
Step 4：找到後載入 PropertyResourceBundle
Step 5：用 key 查 value
rb.getString("hello")
Step 6：輸出對應語言內容
八、這整套機制的本質

一句話總結：

ResourceBundle = 「用 Locale 決定要讀哪一份字典檔」

九、
Key 固定、Value 多語言、Locale 決定載入哪一份 properties