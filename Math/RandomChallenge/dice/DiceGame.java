package Math.RandomChallenge.dice;

import Math.RandomChallenge.game.Game;
import Math.RandomChallenge.game.GameAction;

import java.util.LinkedHashMap;
import java.util.Map;

public class DiceGame extends Game<DicePlayer> {

    public DiceGame(String gameName) {
        super(gameName);
    }

    @Override
    public DicePlayer createNewPlayer(String name) {
        return new DicePlayer(name);
    }

    /*
     * 定義此遊戲可執行的行為清單
     * 使用 LinkedHashMap 以確保選單項目會按照新增的順序顯示
     * 加入專屬的 Roll Dice 行為並結合 super 提供的標準行為（如說明與離開）
     */
    @Override
    public Map<Character, GameAction> getGameActions(int playerIndex) {

        Map<Character, GameAction> map = new LinkedHashMap<>(
                Map.of(
                        'R',
                        new GameAction(
                                'R',
                                "Roll Dice",
                                this::rollDice)));

        map.putAll(getStandardActions());

        return map;
    }

    private boolean rollDice(int playerIndex) {
        return getPlayer(playerIndex).rollDiceAndSelect();
    }
}