除了 default method 以外
Java 在 JDK 8 中還加入了另一項優化功能
是支援在介面中使用 **public static 方法**

在 JDK 8 之前
interface 通常會搭配一個 helper class 用來提供靜態方法
但有了這個改變之後，這些靜態方法可以直接寫在介面本身

靜態方法不需要特別標註 public 存取修飾詞，因為這是預設的

當呼叫介面中的 public static 方法時，必須使用介面的名稱作為前綴來呼叫