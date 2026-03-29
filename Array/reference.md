1. 處理陣列時，必須分清「變數」與「物件」：

物件 (Object)：實際存在於記憶體（堆積區 Heap）中的資料
引用 (Reference)：存放物件「地址」的變數。它不是物件本身，而是指向物件的指標

2. Assignment 的本質

當你寫下 int[] anotherArray = myIntArray; 時

Java 不會 複製一份新的陣列
Java 只是把 myIntArray 裡的「地址」複製給 anotherArray。

結果：
兩個變數現在都指向 同一個 記憶體位置
如果透過 anotherArray[0] = 1 修改了內容
查看 myIntArray[0] 時
也會發現它變成了 1
因為它們改的是同一個東西

3. assing to Methods

Java 依然是 「值傳遞」(Pass by Value)，但傳遞的值是 「地址」
方法內部的參數會拿到該陣列的地址副本

危險區：
如果你在方法內部修改了陣列元素，這個修改是 永久性 的，會影響到原始陣列

(1) 關鍵字 new：
看到 new 代表在記憶體中產生了一個「新物件」
如果沒有 new，只是單純的 =，通常只是在複製地址

(2) 副作用 (Side Effects)：
當你把陣列交給一個方法處理時
要非常小心，因為該方法可能會改動你的原始資料

(3) 防禦性拷貝 (Defensive Copy)：
如果你不希望方法改動你的陣列
最安全的方式是傳入陣列的 副本（Copy），而不是原始陣列

(4) 不可變性 (Immutability)：
雖然有些集合（Collections）可以設定為不可修改
但 Java 陣列（Arrays）本身永遠是可變的