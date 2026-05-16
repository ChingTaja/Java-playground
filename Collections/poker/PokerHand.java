package Collections.poker;

import Collections.Card;

import java.util.ArrayList;

import java.util.List;

import java.util.Collections;

/**
 * PokerHand
 * 
 * 用來代表：某位玩家目前的一手撲克牌
 * 
 * 這個 class 不只是存牌
 * 還會記錄：
 * 
 * 1. 玩家編號
 * 2. 牌型分數 (例如 Pair、Flush...)
 * 3. 哪些牌要保留
 * 4. 哪些牌要丟棄
 */

/**
 * PokerHand
 * 用來代表一位玩家的一手牌
 */
public class PokerHand {

    private List<Card> hand;
    private List<Card> keepers;
    private List<Card> discards;

    private Ranking score = Ranking.NONE;

    private int playerNo;

    public PokerHand(int playerNo, List<Card> hand) {

        // 先排序（高 → 低）
        hand.sort(Card.sortRankReversedSuit());

        this.hand = hand;
        this.playerNo = playerNo;

        this.keepers = new ArrayList<>();
        this.discards = new ArrayList<>();
    }

    @Override
    public String toString() {
        return "%d. %-16s Rank:%d %-40s %s".formatted(
                playerNo,
                score,
                score.ordinal(),
                hand,
                (discards.isEmpty() ? "" : "Discards:" + discards));
    }

    private void setRank(int count) {

        switch (count) {
            case 4 -> score = Ranking.FOUR_OF_A_KIND;

            case 3 -> {
                if (score == Ranking.NONE)
                    score = Ranking.THREE_OF_A_KIND;
                else
                    score = Ranking.FULL_HOUSE;
            }

            case 2 -> {
                if (score == Ranking.NONE)
                    score = Ranking.ONE_PAIR;
                else if (score == Ranking.THREE_OF_A_KIND)
                    score = Ranking.FULL_HOUSE;
                else
                    score = Ranking.TWO_PAIR;
            }
        }
    }

    public void evalHand() {

        List<String> faceList = new ArrayList<>();

        for (Card c : hand) {
            faceList.add(c.face());
        }

        List<String> duplicates = new ArrayList<>();

        for (String face : faceList) {
            if (!duplicates.contains(face)
                    && Collections.frequency(faceList, face) > 1) {
                duplicates.add(face);
            }
        }

        for (String dup : duplicates) {

            int start = faceList.indexOf(dup);
            int end = faceList.lastIndexOf(dup);

            setRank(end - start + 1);

            List<Card> sub = hand.subList(start, end + 1);
            keepers.addAll(sub);
        }

        pickDiscards();
    }

    private void pickDiscards() {

        List<Card> temp = new ArrayList<>(hand);
        temp.removeAll(keepers);

        Collections.reverse(temp);

        int index = 0;

        for (Card c : temp) {

            if (index++ < 3 && (keepers.size() > 2 || c.rank() < 9)) {
                discards.add(c);
            } else {
                keepers.add(c);
            }
        }
    }
}
