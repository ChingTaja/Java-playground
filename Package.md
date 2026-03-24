套件（Package）是一個命名空間（Namespace）
用來組織一組相關的型別
- Namespace：主要功能是避免「菜單類別」跟別人的「菜單類別」撞名
- 階層化 (Hierarchical)：就像電腦資料夾一樣，例如 com.company.project
- 小寫慣例：這是為了跟 類別名稱（通常大寫開頭） 做區分，一眼就能看出 java.util.Scanner 中，java.util 是套件，Scanner 是類別

```java
import java.util.*; // 這裡的星號代表「所有類別」

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();
        // 依然可以正常運作
    }
}
```

套件 (Packages) 的三大核心作用

1. 解決命名衝突 (Namespace)
開發一個餐廳系統
自己寫了一個 Item 類別
而你用的第三方金流庫也有一個 Item 類別：
你的：com.myrestaurant.Item
銀行的：com.bank.api.Item
透過套件，Java 就能分辨誰是誰，不會撞名
2. 限定名稱 (Qualifying Name)
如果你不想用 import
或者同時要用上面兩個同名的類別，你可以直接寫出「全名」：
```java
com.myrestaurant.Item myItem = new com.myrestaurant.Item();
```

3. 封裝與存取權限 (Encapsulation)
這是最實用的部分
如果你把類別或方法設為 default（不寫 public/private）
它就具備 「套件私有 (Package-Private)」 權限：
同一個套件內 的類別可以自由互相呼叫
套件外部 的類別完全看不到它
這在處理像 DeluxeBurger 和它私有的 Item 關係時非常有用，可以防止外部程式隨意修改內部的邏輯

在自訂套件命名規範時，通用的做法是使用反向網域名稱
```Reverse Domain Name```

# Fully Qualified Class Name, 簡稱 FQCN


- 沒 import
```java
java.util.Scanner sc = new java.util.Scanner(System.in);
```
（這就像每次要跟小明說話都得喊「台北市文山區的王小明，請喝茶」，非常累贅）

- 有 import
先在頂部寫 import java.util.Scanner;
程式碼只要寫 Scanner sc = new Scanner(System.in); 即可

# 避免使用預設或未命名的套件
主要的原因和缺點是：你無法將預設套件中的型別，匯入到預設套件之外的其他類別中
換句話說，如果類別在預設套件中，你就無法使用限定名稱（Qualifying Name），也無法匯入該類別