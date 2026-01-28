package Base;

/* ch51 */
public class MethodChallenge {
    public static void main(String[] args) {
        int highScorePosition = calculateHighScorePosition(1500);
        displayHighScorePosition("Taja", highScorePosition);

        highScorePosition = calculateHighScorePosition(500);
        displayHighScorePosition("Taja2", highScorePosition);
    }
    
    public static void displayHighScorePosition(String playName, int highScroePosition) {
        System.out.println(playName + highScroePosition);
    }
    
    public static int calculateHighScorePosition(int playerScore) {
        int position = 4;

        if (playerScore >= 1000) {
            position = 1;
            return 1;
        } else if (playerScore < 1000) {
            position = 2;
        } else if (playerScore >= 100) {
            position = 3;
        }
        return position;
    }
}
