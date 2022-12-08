package com.t3ratech.poker_referee.data;

import lombok.Getter;

/***************************************************************************
 * Created:     08 / 12 / 2022                                             *
 * Platform:    Ubuntu Linux x86_64                                        *
 * Author:      Tsungai Kaviya                                             *
 * Contact:     tsungai.kaviya@gmail.com                                   *
 ***************************************************************************/

@Getter
public class Player {

    private final String name;
    private PokerHand currentHand;

    public Player(String name) {
        this.name = name;
    }

    public void setCurrentHand(PokerHand currentHand) {
        this.currentHand = currentHand;
    }

    public String getName() {
        return name;
    }
}
