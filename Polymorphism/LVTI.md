**區域變數型別推斷**

在 Java 10 引入的
優點
1. 提升程式碼的**可讀性**
2. 減少**樣板程式碼 (Boilerplate code)**
   
- 舊寫法：`VeryLongClassName obj = new VeryLongClassName();`
- LVTI 寫法：`var obj = new VeryLongClassName();`

被稱作 **區域變數** 是因為
1.  他**不能**用於類別的 field declarations
2. 它也**不能**用於 method signatures , 無論是作為參數型別 (parameter type )或回傳型別 (return type)都不行
3.  他**不能**在沒有賦值（等號右邊沒有內容）的情況下使用，因為在那種情況下無法推斷出型別
4.  他**不能**被指定為 `null` 字面量，因為同樣無法推斷出其型別