Comparable = 讓「物件可以被排序」的介面

1. 基本定義
用途：定義物件的「自然排序」標準
若類別**實作**此介面
這個物件就能直接使用 Arrays.sort() 或 Collections.sort() 進行排序
泛型設計：Comparable<T> 是一個泛型介面
T 代表要與之比較的物件型別

2. compareTo 方法規則
這是實作該介面唯一需要完成的方法
假設呼叫方式為：A.compareTo(B)

回傳值類型	｜ 語意 (A 與 B 比較)	｜ 說明
0	｜ A == B	兩者相等
負整數 (< 0) ｜	A < B	｜ 當前物件較小，應排在前面
正整數 (> 0) ｜	A > B ｜ 當前物件較大，應排在後面

3. 常見範例
String：依照字典順序（Unicode 編碼）比較
Integer / Double：依照數值大小比較
Character：依照字元編碼比較

# Comparable 時最常遇到的兩個陷阱：
1. 未實作 Comparable 介面導致無法排序
2. 使用 Raw Type（原始型別）導致的執行期錯誤

# Comparator 介面和 Comparable 

很相似因此這兩個常常會被混淆。

它的宣告以及主要的抽象方法如下（並和 Comparable 做對照）：

- 注意方法名稱不同：

Comparator → compare
Comparable → compareTo

- 參數數量：

compare 需要 兩個參數
compareTo 只需要 一個參數

這代表：

compareTo 是「拿別人跟自己（this）比」
compare 是「拿兩個物件互相比」