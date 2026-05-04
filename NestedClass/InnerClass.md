Inner class 可以是：

public
private
protected
package-private（不寫）

🔑 重要特性

Inner class 可以存取外部類別的 instance 成員
👉 包括 private 成員

📌 Java 16 更新

從 JDK 16 開始，inner class 也支援所有型別的 static 成員

👉 inner class 一定要依附某個 outer instance 才能建立，因為它本身綁定在那個物件上
# .new 是什麼？

- 不是 method
- 是 Java 特殊語法

意思： 用這個 outer object 建立 inner object