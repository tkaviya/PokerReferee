package com.t3ratech.poker_referee.engine;

import com.t3ratech.poker_referee.data.Player;
import com.t3ratech.poker_referee.data.PokerHand;
import com.t3ratech.poker_referee.engine.Referee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
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
    private final Referee referee;

    @Autowired
    public PokerEngine(Referee referee) {
        this.referee = referee;
        var filename = "p054_poker.txt";
        var pokerGames = readFileContents(filename);
        var winners = evaluateGames(pokerGames);
        if (winners.get(1) != null) {
            logger.info("Player 1 wins " + winners.get(1) + " matches");
        } else if (winners.get(2) != null) {
            logger.info("Player 2 wins " + winners.get(2) + " matches");
        }
    }

    public HashMap<Integer, Integer> evaluateGames(ArrayList<String> pokerGames) {
        HashMap<Integer, Integer> winners = new HashMap<>();
        Player player1 = new Player("Player 1");
        Player player2 = new Player("Player 2");
        pokerGames.forEach(gameString -> {
            var cards = gameString.split(" ");
            // extract first 5 cards as player 1 cards, last five as player 2 cards
            var p1Cards = new ArrayList<>(Arrays.asList(cards).subList(0, 5));
            var p2Cards = new ArrayList<>(Arrays.asList(cards).subList(5, 10));
            player1.setCurrentHand(new PokerHand(p1Cards));
            player2.setCurrentHand(new PokerHand(p2Cards));
            var winner = referee.getWinner(gameString, player1, player2);
            if (winner.equals(player1)) {
                winners.merge(1, 1, (a, b) -> winners.get(b) + 1);
            } else {
                winners.merge(2, 1, (a, b) -> winners.get(b) + 1);
            }
        });

        return winners;
    }


}
