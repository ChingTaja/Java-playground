package Collections.poker;

import Collections.Card;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.function.Consumer;

public class PokerGame {

    // 建立一副標準牌組
    private final List<Card> deck = Card.getStandardDeck();

    // 玩家數
    private int playerCount;

    // 每位玩家手牌數
    private int cardsInHand;

    // 所有玩家的手牌
    private List<PokerHand> pokerHands;

    // 剩餘牌
    private List<Card> remainingCards;

    public PokerGame(int playerCount, int cardsInHand) {

        this.playerCount = playerCount;
        this.cardsInHand = cardsInHand;

        // 初始化玩家手牌集合
        pokerHands = new ArrayList<>(playerCount);
    }

    public void startPlay() {

        // 洗牌
        Collections.shuffle(deck);

        Card.printDeck(deck, "洗牌後", 4);

        // 模擬切牌
        int randomMiddle = new Random().nextInt(15, 35);

        Collections.rotate(deck, randomMiddle);

        Card.printDeck(deck, "切牌後", 4);

        // 發牌
        deal();

        System.out.println("---------------------------");

        // 評估牌型
        Consumer<PokerHand> checkHand = PokerHand::evalHand;

        pokerHands.forEach(
                checkHand.andThen(System.out::println));

        // 已發出的牌數
        int cardsDealt = playerCount * cardsInHand;

        // 取得剩餘牌
        remainingCards = new ArrayList<>(
                deck.subList(cardsDealt, deck.size()));

        System.out.println("---------------------------");

        Card.printDeck(remainingCards, "剩餘牌", 4);
    }

    private void deal() {

        // 玩家 x 手牌數
        Card[][] hands = new Card[playerCount][cardsInHand];

        /*
         * 外層:
         * 第幾輪發牌
         *
         * 內層:
         * 發給哪位玩家
         */

        for (int deckIndex = 0, i = 0; i < cardsInHand; i++) {

            for (int j = 0; j < playerCount; j++) {

                hands[j][i] = deck.get(deckIndex++);
            }
        }

        int playerNo = 1;

        // 將陣列轉成 PokerHand
        for (Card[] hand : hands) {

            pokerHands.add(
                    new PokerHand(
                            playerNo++,
                            Arrays.asList(hand)));
        }
    }
}