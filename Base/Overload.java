
package Base;

public class Overload {
    public static void main(String[] args) {
       int newScore =  calculateScore("taja", 0);
       System.out.println(newScore);
    }

    /* ch54 */
    public static int calculateScore(String playerName, int score) {
        System.out.println(playerName + score);
        return score * 1000;
    }

    public static int calculateScore(int score) {
        return calculateScore("Anonymous" , score);
    }

    public static int calculateScore() {
        return 0;
    }

    /* ch55 */
    public static double convertTocentiments(int inches) {
        return 2.54 * inches;
    }
    
    public static double convertTocentiments(int feet, int inches) {
        int feetToInches = 12 * feet;
        int totalInches = feetToInches + inches;
        double result = convertTocentiments(totalInches);
        return result;
    }

}

/*
 * 
 * overloading 的判斷標準
 * 
 * Java 判斷是不是不同方法
 * 只看參數列表（method signature）
 * 
 * 包含：參數數量,參數型別,參數順序
 * 
 * 以下都「不算」多載
 * 1. ❌ 參數名字不同 => 參數名稱不重要
 * doSomething(int a)
 * doSomething(int b)
 * 2. ❌ 只改回傳型別
 * int doSomething(int a)
 * double doSomething(int a)
 * 
 * 當你有 overloaded methods 時
 * 不要在每個方法裡都寫同樣的轉換邏輯。
 * 
 * 把真正做事的程式碼放在「其中一個方法」
 * 其他 overload 的方法只負責整理參數，然後呼叫它
 */