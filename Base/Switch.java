/* ch59 */

/*
1. 
Switch 只能用在有限幾種資料型別上。

我們只能使用一半的基本型別（primitive types）：
byte、short、int、char，以及它們對應的包裝類別（wrapper classes）

另外，也可以使用 String，還有 enum 的型別。

要注意：
boolean、long、float、double 這些基本型別不能用在 switch 裡面
如果使用它，程式會直接報錯

2. fall through 的概念

一旦某個 case 標籤與 switch 的變數匹配成功後
就不會再檢查後面的 case

從那個符合的 case 開始
後面的程式碼都會被執行
直到遇到 break
或是 switch 結束為止

*/
package Base;

public class Switch {
    public static void main(String[] args) {
        int switchValue = 1;

        switch (switchValue) {
            case 1:
                System.out.println("Value was1");
                break;
            case 2:
                System.out.println("Value was 2");
            case 3: case 4: case 5:
                System.out.println("Value was 3, 4, or 5");
                break;
            default:
                System.out.println("Was not 1, 2, 3, 4, or 5");
                break;
        }

    }
}
