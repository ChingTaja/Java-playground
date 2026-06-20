package Math.RandomChallenge.dice;

import Math.RandomChallenge.game.GameConsole;
import Math.RandomChallenge.game.Player;

import java.util.*;

public class DicePlayer implements Player {

    private final String name;
    private final List<Integer> currentDice = new ArrayList<>();
    private final Map<ScoredItem, Integer> scoreCard = new EnumMap<>(ScoredItem.class);

    /*
     * DicePlayer 建構子
     * 初始化玩家名稱
     * 並將計分卡中的所有 ScoredItem 欄位預設為 null
     * 表示尚未計分
     */
    public DicePlayer(String name) {

        this.name = name;

        for (ScoredItem item : ScoredItem.values()) {
            scoreCard.put(item, null);
        }
    }

    @Override
    public String name() {
        return name;
    }

    @Override
    public String toString() {

        return "DicePlayer{" +
                "name='" + name + '\'' +
                ", currentDice=" + currentDice +
                ", scoreCard=" + scoreCard +
                '}';
    }

    public void rollDice() {

        int randomCount = 5 - currentDice.size();

        var newDice = new Random()
                .ints(randomCount, 1, 7)
                .sorted()
                .boxed()
                .toList();

        currentDice.addAll(newDice);

        System.out.println("You're dice are: " + currentDice);
    }

    /*
     * 提示玩家是否要重新擲骰子
     * 按下 Enter 代表確認目前點數並進行計分
     * 輸入 ALL 或指定數字則代表要淘汰並重擲哪些骰子
     */
    private boolean pickLosers() {

        String prompt = """
                Press Enter to Score.
                Type "ALL" to re-roll all the dice.
                List numbers (separated by spaces) to re-roll selected dice.
                """;

        String userInput = GameConsole.getUserInput(prompt + "--> ");

        if (userInput.isBlank()) {
            return true;
        }

        try {
            removeDice(userInput.split(" "));
        } catch (Exception e) {
            e.printStackTrace(System.out);
            System.out.println("Bad input, Try again");
        }

        return false;
    }

    /*
     * 從目前骰子清單中移除玩家不想保留的骰子
     * 如果輸入 ALL 則清空所有骰子點數
     */
    private void removeDice(String[] selected) {

        if (selected.length == 1 && selected[0].contains("ALL")) {
            currentDice.clear();
        } else {
            for (String removed : selected) {
                currentDice.remove(Integer.valueOf(removed));
            }
            System.out.println("Keeping " + currentDice);
        }
    }

    /*
     * 執行擲骰子與選擇計分類別的完整流程
     * 利用 do-while 迴圈重複投擲直到玩家滿意點數
     * 接著強迫玩家從未計分的類別中選擇一項填入分數，最後清空骰子
     */
    public boolean rollDiceAndSelect() {

        do {
            rollDice();
        } while (!pickLosers());

        do {
            System.out.println("You must select a score category:");
        } while (!scoreDice());

        currentDice.clear();

        return (getItemList().isEmpty());
    }

    public List<String> getItemList() {

        return scoreCard.entrySet()
                .stream()
                .filter(e -> e.getValue() == null)
                .map(e -> e.getKey().name())
                .toList();
    }

    /*
     * 處理骰子點數的計分邏輯
     * 顯示所有可用的計分類別並獲取玩家輸入
     * 若輸入有效則計算分數填入 EnumMap 計分卡中
     */
    private boolean scoreDice() {

        List<String> remainingItems = getItemList();

        String prompt = String.join(
                "\n",
                remainingItems.toArray(new String[0]));

        String userInput = GameConsole
                .getUserInput(prompt + "\n--> ")
                .toUpperCase();

        if (userInput.isBlank()) {
            return false;
        }

        if (!remainingItems.contains(userInput)) {
            System.out.println("Invalid selection");
            return false;
        }

        ScoredItem item = ScoredItem.valueOf(userInput);
        scoreCard.put(item, item.score(currentDice));

        return true;
    }
}