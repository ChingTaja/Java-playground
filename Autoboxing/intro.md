1. 為什麼 Java 不像某些語言「全都是物件」？
有些語言（如 Smalltalk 或 Ruby）幾乎所有東西都是物件Java 選擇保留原始型別（如 int, double, boolean）
主要是為了 **效能（Performance）**

- 記憶體佔用：
原始型別直接儲存在「堆疊（Stack）」中
佔用的空間非常固定且微小
物件則儲存在「堆積（Heap）」中
除了資料本身
還需要額外的記憶體來存放 Object Header 和Metadata

- 處理速度：電腦硬體（CPU）可以直接對原始型別進行運算
而物件則需要經過 Dereferencing）等額外步驟

2. 什麼是包裝類別（Wrapper Classes）？
雖然原始型別很快
但它們有一個致命缺點：它們不是物件
這意味著它們不能直接放入 Java 的 Collection Framework 
 中
（在還沒有這個框架之前，開發者必須自己寫 Linked List 或用 Array 來管理資料），例如 ArrayList 或 LinkedList

為了讓這些資料能像物件一樣運作，Java 為每一種原始型別都提供了一個對應的包裝類別

原始型態, 包裝類別
int, Integer
char, Character
double, Double
boolean, Boolean
byte, Byte
short, Short
long, Long
float, Float

- Boxing (裝箱)：將原始型態包裹進物件中
例如：int  -> Integer
Unboxing (拆箱)：將數值從物件中取出
例如：Integer -> int


# Box
每種包裝類別都有一個靜態重載的工廠方法 valueOf
它接受原始型別作為參數
並回傳該包裝類別的一個實例（物件）

方式,   程式碼範例,狀態,評價
舊式 constructor , `new Integer(15)`, 已過時 (Deprecated), 效能差，會強制建立新物件
手動工廠方法, `Integer.valueOf(15)`, 建議使用 (Preferred), 效能好，支援 Caching
自動裝箱, `Integer x = 15;`, 最常用 (Standard), 最簡潔，由編譯器自動轉為 valueOf。

### 為什麼 new 被廢棄了？
當你使用 `new Integer(15)` 時
Java 每次都會在記憶體中建立一個全新的物件
而使用 valueOf(15) 時，Java 會先檢查「快取（Cache）」中是否已經有一個代表 15 的物件
如果有，就直接給你舊的。這在處理大量數據時，能節省巨大的記憶體空間與時間

### Autoboxing

Java 自動將「原始型別」轉換為對應的「包裝類別物件」的過程

直接將數值賦值給物件變數，不需呼叫 valueOf()

`Integer boxedInteger = 15;`
-> 編譯器在背後實際上是執行了 Integer.valueOf(15)



# Unbox

Java 自動將「包裝類別物件」轉換回「原始型別」的過程
`int primitiveInt = boxedInteger;`

類型, 自動裝箱 (底層呼叫), 自動拆箱 (底層呼叫)
整數, `Integer.valueOf(int)`, `boxedInt.intValue()`
浮點數, `Double.valueOf(double)`, `boxedDouble.doubleValue()`
布林值, `Boolean.valueOf(boolean)`, `boxedBool.booleanValue()`

注意 null 指針：自動拆箱最危險的地方在於，如果 boxedInteger 是 **null**，執行 
`int x = boxedInteger;` 時會拋出 NullPointerException 
物件可以是 null，但原始型別不行


### Autoboxing

```java
Integar boxedInteger = 15;
int unboxedInt = boxedInteger;
```
