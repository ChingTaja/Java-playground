package Math.RandomChallenge;

import Math.RandomChallenge.dice.DiceGame;
import Math.RandomChallenge.game.GameConsole;

public class MainGame {

    public static void main(String[] args) {

        // List<Integer> currentDice = new ArrayList<>(
        // List.of(2, 2, 4, 4, 4));
        //
        // for (ScoredItem s : ScoredItem.values()) {
        // System.out.printf(
        // "Score for %s is %d%n",
        // s,
        // s.score(currentDice)
        // );
        // }

        /*
         * 建立遊戲控制台實例
         * 並傳入一個名為 Dice Rolling Game 的骰子遊戲
         * 啟動遊戲並新增玩家開始遊玩
         */
        var console = new GameConsole<>(
                new DiceGame("Dice Rolling Game"));

        console.playGame(console.addPlayer());
    }
}