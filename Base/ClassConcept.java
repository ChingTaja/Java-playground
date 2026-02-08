package Base;

/*

- Class 與 Object 的關係

把 Class 想成一張空白表單
object 則是填好的表單
描述了要收集哪些資料, 或有哪些欄位
但"真正的資料"是在表單交給某個人填寫之後才出現的

建立物件的過程
就像是：
把空白表單（class）影印一份
交給不同的人填寫
每一份填完的表單資料都不一樣


物件也被稱為某個類別的實例（instance）
*/


/*

-  類別中建立欄位（field）有兩種方式

1. 使用 static 關鍵字
2. 另一種是不使用 static

使用 static 關鍵字時，這個欄位就稱為 靜態欄位（static field）
這表示這個欄位的值**永遠屬於類別本身**
而不是某個物件
這個值會被儲存在一個特殊的記憶體位置中
用來存放**不會頻繁改變**的資料

如果用「表單」的比喻來看：
static 欄位就像是一個事先填好的欄位

這個欄位在記憶體中的存取方式也不同：
我們可以直接使用 類別名稱 + 點號（dot notation）

例如：Integer.MAX_VALUE

----
如果沒有使用 static 關鍵字
這個欄位就稱為 實例欄位（instance field）

在類別還沒被實例化（instantiate）之前
這些實例欄位在記憶體中是**不存在的**

存取方式：物件變數名稱 + 點號 + 欄位名稱

*/

/*
Static Method（靜態方法） &  Instance Method（實例方法） 差異

🔹 Static Method（靜態方法）

1. 不需要物件
2. 直接用類別名稱呼叫

🔹 Instance Method（實例方法）

1. 一定要先有物件
2. 操作該物件本身的資料

*/
public class ClassConcept {

}
