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
        printDayofWeek((0));
        // enchanded statement
        int switchValue1 = 2;

        switch (switchValue1) {
            case 1 -> System.out.println("Value was1");
            case 2 -> System.out.println("Value was 2");
            case 3, 4, 5 -> System.out.println("Value was 3, 4, or 5");
            default -> System.out.println("Was not 1, 2, 3, 4, or 5");
        }

        // traditional statement
        int switchValue = 1;

        switch (switchValue) {
            case 1:
                System.out.println("Value was1");
                break;
            case 2:
                System.out.println("Value was 2");
            case 3:
            case 4:
            case 5:
                System.out.println("Value was 3, 4, or 5");
                break;
            default:
                System.out.println("Was not 1, 2, 3, 4, or 5");
                break;
        }
    }

    public static String getQuater(String month) {
        // return 出現在 switch 前面 , 在 JDK 14 正式成為標準
        return switch (month) {
            case "January", "February", "March" -> {
                yield "1st quarter";
            }
            case "April", "May", "June" -> "2nd quarter";
            case "July", "August", "September" -> "3rd quarter";
            case "October", "November", "December" -> "4th quarter";
            default -> {
                String badResponse = month + "is bad";
                yield badResponse;
            }
        };

        /*
         * traditional statement
         * 
         * 沒有用 break，而是用 return。
         * return 會直接離開 switch 同時離開方法，所以也不會 fall through
         * 
         * switch (month) {
         * case "January":
         * case "February":
         * case "March":
         * return "1st quarter";
         * 
         * case "April":
         * case "May":
         * case "June":
         * return "2nd quarter";
         * 
         * case "July":
         * case "August":
         * case "September":
         * return "3rd quarter";
         * 
         * case "October":
         * case "November":
         * case "December":
         * return "4th quarter";
         * }
         * return "Invalid month";
         * 
         */
    }

    public static void printDayofWeek(int day) {
        String dayOfWeek = switch(day){
            case 0 -> "Sunday";
            case 1 -> "Monday";
            case 2 -> "Tuseday";
            default -> "Invalid Day";
        };
        System.out.println(dayOfWeek);
    }
}
