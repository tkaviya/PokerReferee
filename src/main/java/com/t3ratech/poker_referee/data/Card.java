package com.t3ratech.poker_referee.data;

import static com.t3ratech.poker_referee.common.Utils.isNumeric;

/***************************************************************************
 * Created:     08 / 12 / 2022                                             *
 * Platform:    Ubuntu Linux x86_64                                        *
 * Author:      Tsungai Kaviya                                             *
 * Contact:     tsungai.kaviya@gmail.com                                   *
 ***************************************************************************/

public class Card {

    private String cardAsString;

    private Card() {}

    public static Card fromString(String cardAsString) {
        Card newCard = new Card();
        newCard.cardAsString = cardAsString;
        return newCard;
    }

    public Integer getCardValue() {
        String cardValueString = cardAsString.substring(0,1);
        if (isNumeric(cardValueString)) {
            return Integer.valueOf(cardValueString);
        } else {
            switch (cardValueString) {
                case "T" -> { return 10; }
                case "J" -> { return 11; }
                case "Q" -> { return 12; }
                case "K" -> { return 13; }
                case "A" -> { return 14; }
                default -> { return null; } // invalid data
            }
        }
    }

    public String getSuit() {
        return cardAsString.substring(1,2);
    }
}
