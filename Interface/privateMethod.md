在 JDK 9 中，Java 引入了 private methods（私有方法）
這些方法可以是 static 或 非 static

這個改進主要是為了解決一個問題：
在 interface 的**concrete methods** 中**重複使用程式碼**

🔒 private static 方法

private static 方法可以被以下幾種方法呼叫：

public static method
default method
private 非 static method

==> 也就是說，它是一種「只在 interface 內部使用的工具方法」

🔒 private 非 static 方法

private 非 static 方法主要是用來：

==> 支援 default method 或其他 private method