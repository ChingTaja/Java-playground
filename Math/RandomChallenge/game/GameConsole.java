package Math.RandomChallenge.game;

import java.util.Scanner;

/*
 * final 關鍵字用在類別上最直接的語法功能：
 * 當一個類別被宣告為 final 時，
 * 任何其他類別都無法使用 extends 來繼承它。
 */
public final class GameConsole<T extends Game<? extends Player>> {

    private final T game;
    private static final Scanner scanner = new Scanner(System.in);

    public GameConsole(T game) {
        this.game = game;
    }

    public int addPlayer() {

        System.out.print("Enter your playing name: ");
        String name = scanner.nextLine();

        System.out.printf(
                "Welcome to %s, %s!%n",
                game.getGameName(),
                name);

        return game.addPlayer(name);
    }

    /*
     * 遊玩遊戲的主迴圈
     *
     * 不斷獲取當前可執行的 gameActions，
     * 並顯示在控制台上供玩家選擇。
     *
     * 讀取玩家輸入後，
     * 交由 game 執行對應的動作。
     *
     * 直到 executeGameAction 回傳 true，
     * 結束遊戲。
     */
    public void playGame(int playerIndex) {

        boolean done = false;

        while (!done) {

            var gameActions = game.getGameActions(playerIndex);

            System.out.println(
                    "Select from one of the following Actions: ");

            for (Character c : gameActions.keySet()) {

                String prompt = gameActions.get(c).prompt();

                System.out.println(
                        "\t" + prompt + " (" + c + ")");
            }

            System.out.print("Enter Next Action: ");

            char nextMove = scanner
                    .nextLine()
                    .toUpperCase()
                    .charAt(0);

            GameAction gameAction = gameActions.get(nextMove);

            if (gameAction != null) {

                System.out.println(
                        "-------------------------------------------");

                done = game.executeGameAction(
                        playerIndex,
                        gameAction);

                if (!done) {
                    System.out.println(
                            "-------------------------------------------");
                }
            }
        }
    }

    public static String getUserInput(String prompt) {

        System.out.print(prompt + ":  ");
        return scanner.nextLine();
    }
}