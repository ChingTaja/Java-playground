# defer method invocation

lambda 表達式與 method reference 都是「延遲執行（deferred execution）」的機制

而==不是立即執行的程式碼==

當你把 lambda 或 method reference 指派給變數時
程式並不會立刻執行
而只是「把行為保存起來」
真正執行要等到 functional interface 的方法被呼叫（例如 get()）

- lambda expression / method reference 在「定義時不會執行」
- 只有在 functional interface 方法被呼叫時才會執行

接著用 Supplier 搭配 PlainOld::new 說明 constructor reference 的執行時機，並示範必須透過 get() 才會真正建立物件。


進一步延伸這個概念，設計一個 ==seedArray 方法==，透過 Supplier + Arrays.setAll 來「批次產生物件陣列」，說明 method reference 的真正價值在於：可以延後決定「怎麼產生物件」，並在需要時大量使用
==> 程式碼範例
    
> Method reference + Supplier = 把「new 的行為」延後執行，並可重複使用
    