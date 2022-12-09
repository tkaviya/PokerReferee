package com.t3ratech.poker_referee.data;

import java.util.*;

import static com.t3ratech.poker_referee.data.PokerHandRank.*;

/***************************************************************************
 * Created:     08 / 12 / 2022                                             *
 * Platform:    Ubuntu Linux x86_64                                        *
 * Author:      Tsungai Kaviya                                             *
 * Contact:     tsungai.kaviya@gmail.com                                   *
 ***************************************************************************/

public class PokerHand {

    // all cards in the hand
    private final List<Card> hand = new ArrayList<>();

    // all the values in the hand
    private final List<Integer> cardValues = new ArrayList<>();

    // all the suits in the hand
    private final Set<String> cardSuits = new HashSet<>();

    // the count of each value in the hand
    private final HashMap<Integer, Integer> cardCount = new HashMap<>();

    // the rank of the hand
    private PokerHandRank pokerHandRank = PokerHandRank.NONE;

    public PokerHand(List<String> cards) {
        cards.forEach(card -> hand.add(Card.fromString(card)));
        processHand();
        rankHand();
    }

    public PokerHandRank getHandRank() {
        return pokerHandRank;
    }

    public List<Integer> getCardValues() {
        return cardValues;
    }

    private void rankHand() {
        // test for rank starting with the highest going to the lowest
        if (isRoyalFlush()) { this.pokerHandRank = ROYAL_FLUSH; return; }

        // test flush first because it's less resource intensive, then use short circuit evaluation
        if (isFlush() && isStraight()) { this.pokerHandRank = STRAIGHT_FLUSH; return; }

        // test if any card value has been found 4 times
        cardCount.values().stream().filter(c -> c == 4).findFirst().ifPresent(c -> this.pokerHandRank = FOUR_OF_A_KIND);
        if (this.pokerHandRank != NONE) { return; }

        // test if any card value has been found 3 times
        cardCount.values().stream().filter(c -> c == 3).findFirst().ifPresent(c -> this.pokerHandRank = THREE_OF_A_KIND);
        // if we have 3 of a kind, check to see if we also have a pair
        if (this.pokerHandRank == THREE_OF_A_KIND) {
            cardCount.values().stream().filter(c -> c == 2).findFirst().ifPresent(c -> this.pokerHandRank = FULL_HOUSE);
        }
        if (this.pokerHandRank != NONE) { return; }

        if (isFlush()) { this.pokerHandRank = FLUSH; return; }

        if (isStraight()) { this.pokerHandRank = STRAIGHT; return; }

        // test if any card value has been found 2 times
        var pairs = cardCount.values().stream().filter(c -> c == 2).toList();
        if (pairs.size() == 2) {
            this.pokerHandRank = TWO_PAIRS;
        } else if (pairs.size() == 1) {
            this.pokerHandRank = ONE_PAIR;
        }
    }

    private void processHand() {
        // iterate through the cards and pull out all information we need to rank the hand
        hand.forEach(card -> {
            // remember all the suits we discover in the hand
            cardSuits.add(card.getSuit());
            // remember all the card values you have
            cardValues.add(card.getCardValue());
            // count how many of each card you have
            cardCount.merge(card.getCardValue(), 1, Integer::sum);
        });
    }

    public boolean isRoyalFlush() {
        return isFlush() &&
            cardValues.contains(10) &&
            cardValues.contains(11) &&
            cardValues.contains(12) &&
            cardValues.contains(13) &&
            cardValues.contains(14);
    }

    public boolean isFlush() {
        return this.cardSuits.size() == 1;
    }

    public boolean isStraight() {
        Integer previousCardValue = null;

        cardValues.sort(Collections.reverseOrder());
        for (Integer cardValue : cardValues) {
            if (previousCardValue == null) {
                previousCardValue = cardValue;
            } else if ((previousCardValue - 1) != cardValue) {
                return false;
            } else {
                previousCardValue = cardValue;
            }
        }
        return true;
    }
}
