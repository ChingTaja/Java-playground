package Base;
public class KeywordsAndExpression {
    public static void main(String[] args) {

        /* ch44 Expression */

        double kilometer = (100 * 1.5566);
        // 100 * 1.5566 -> expression 算出一個值
        // double kilometer = (100 * 1.5566) -> statement 執行一個動作的指令

        int highscroe = 50;

        int health = 100;

        if ((health < 25) && (highscroe > 100)) {
            highscroe = highscroe - 1000;
        }

        /* ch45 Statement */
        int myTest = 50;
        myTest++;
        myTest--;

        System.out.println("This is test");

        /* ch46 if-then-else */
        boolean gameOver = true;
        int score = 100;
        int levelComplete = 5;
        int bonus = 100;
        
        int highScroe = calculateScore(gameOver, score, levelComplete, bonus);
        System.out.println("Your high Scroe was:" + highScroe);

        if (score < 5000 && score > 1000) {
            System.out.println("Your score was less than 5000");
        } else if (score < 1000) {
            System.out.println("Your score was less than 1000");

        } else {
            System.out.println("Got here");
        }
    }
    
    /* ch48 reuse code */
    /* ch49 return value */
    public static int calculateScore(boolean gameOver, int score, int levelComplete, int bonus) {
        int finalScore = score;

        if (gameOver) {
            finalScore += (levelComplete * bonus);
            finalScore += 1000;
        }
        
        return finalScore;
    }
    /* ch50 */

    // 不支援預設值：Java 與許多語言不同，不支援在參數中設定預設值
    // 輸入 psvm 並按 Enter 即可快速生成方法
}
