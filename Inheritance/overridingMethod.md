當子類 (Subclass) 建立了一個與父類 (Superclass) **完全相同「簽名 (Signature)」的方法**，就稱為 overriding

1. 什麼是「方法簽名」？ (Method Signature)
要達成覆寫，這兩個要素必須完全一致：

- 方法名稱 (Method Name)
- 參數的數量與類型 (Number and types of parameters)

註：回傳類型 (Return Type) 通常也要一致。

3. 為什麼要覆寫？ 
當父類提供的通用行為（例如 Animal 的 makeNoise）
不符合子類的需求時
子類可以提供自己專屬的實作方式（例如 Dog 改成發出 Woof 的聲音）

多型 (Polymorphism) 的基礎：讓同一個方法名稱，在不同的物件上展現出不同的行為。

4. IntelliJ IDEA
在 IntelliJ 中，被覆寫的方法左側行號處會出現一個 圓圈中間有箭頭 的小圖示
