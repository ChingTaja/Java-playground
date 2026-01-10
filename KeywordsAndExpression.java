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
        
        if (score < 5000 && score > 1000) {
            System.out.println("Your score was less than 5000");
        } else if (score < 1000) {
            System.out.println("Your score was less than 1000");

        } else {
            System.out.println("Got here");
        }
    }
}
