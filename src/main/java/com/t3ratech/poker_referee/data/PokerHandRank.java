package com.t3ratech.poker_referee.data;

import lombok.Getter;

/***************************************************************************
 * Created:     08 / 12 / 2022                                             *
 * Platform:    Ubuntu Linux x86_64                                        *
 * Author:      Tsungai Kaviya                                             *
 * Contact:     tsungai.kaviya@gmail.com                                   *
 ***************************************************************************/

@Getter
public enum PokerHandRank {
    ROYAL_FLUSH(10), STRAIGHT_FLUSH(9), FOUR_OF_A_KIND(8), FULL_HOUSE(7),
    FLUSH(6), STRAIGHT(5), THREE_OF_A_KIND(4), TWO_PAIRS(3),
    ONE_PAIR(2), HIGH_CARD(1), NONE(0);

    private final int numPoints;

    PokerHandRank(int numPoints) {
        this.numPoints = numPoints;
    }
}
