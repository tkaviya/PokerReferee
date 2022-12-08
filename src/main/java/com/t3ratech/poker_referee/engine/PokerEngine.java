package com.t3ratech;

import com.t3ratech.poker_referee.data.Player;
import com.t3ratech.poker_referee.data.PokerHand;
import com.t3ratech.poker_referee.engine.Referee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Logger;

import static com.t3ratech.poker_referee.common.Utils.readFileContents;

/***************************************************************************
 * Created:     08 / 12 / 2022                                             *
 * Platform:    Ubuntu Linux x86_64                                        *
 * Author:      Tsungai Kaviya                                             *
 * Contact:     tsungai.kaviya@gmail.com                                   *
 ***************************************************************************/

@Component
public class PokerEngine {

    private static final Logger logger = Logger.getLogger(PokerEngine.class.getSimpleName());
    final Referee referee;

    @Autowired
    public PokerEngine(Referee referee) {
        this.referee = referee;
        evaluateGames();
    }

    public void evaluateGames() {

        var filename = "p054_poker.txt";
        Player player1 = new Player("Player 1");
        Player player2 = new Player("Player 2");
        var pokerGames = readFileContents(filename);
        AtomicInteger p1Wins = new AtomicInteger();
        pokerGames.forEach(gameString -> {
            var cards = gameString.split(" ");
            // extract first 5 cards as player 1 cards, last five as player 2 cards
            var p1Cards = new ArrayList<>(Arrays.asList(cards).subList(0, 5));
            var p2Cards = new ArrayList<>(Arrays.asList(cards).subList(5, 10));
            player1.setCurrentHand(new PokerHand(p1Cards));
            player2.setCurrentHand(new PokerHand(p2Cards));
            var winner = referee.getWinner(gameString, player1, player2);
            if (winner.equals(player1)) {
                p1Wins.incrementAndGet();
            }
        });
        logger.info("Player 1 wins " + p1Wins.get() + " matches");
    }


}
