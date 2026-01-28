/* ch54 */

package Base;

public class Overload {
    public static void main(String[] args) {
       int newScore =  calculateScore("taja", 0);
       System.out.println(newScore);
    }

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
}

/*
 * 
 * overloading 的判斷標準
 * 
 * Java 判斷是不是不同方法，只看：
 * 
 * 參數列表（method signature）
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
 */