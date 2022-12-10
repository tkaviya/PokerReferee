package com.t3ratech.poker_referee.engine;

import com.t3ratech.poker_referee.data.Player;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.logging.Logger;

/***************************************************************************
 * Created:     08 / 12 / 2022                                             *
 * Platform:    Ubuntu Linux x86_64                                        *
 * Author:      Tsungai Kaviya                                             *
 * Contact:     tsungai.kaviya@gmail.com                                   *
 ***************************************************************************/

@Service
public class Referee {

    private static final Logger logger = Logger.getLogger(Referee.class.getSimpleName());

    public Player getWinner(String hand, Player player1, Player player2) {
        var p1Points = player1.getCurrentHand().getHandRank().getNumPoints();
        var p2Points = player2.getCurrentHand().getHandRank().getNumPoints();
        // sort the players according to who has the most points
        if (p1Points > p2Points) {
            logger.info("Hand: " + hand + " | " + player1.getName() + " wins with " + p1Points + " points! " + player1.getCurrentHand().getHandRank().name());
            return player1;
        } else if (p1Points < p2Points) {
            logger.info("Hand: " + hand + " | " + player2.getName() + " wins with " + p2Points + " points! " + player2.getCurrentHand().getHandRank().name());
            return player2;
        } else if (p1Points == 0) { // if points are equal & there are no pairs, look for the highest number of each player

            // check cards starting with each players' highest, going downwards on each draw until a winner is found
            var p1CardValues = player1.getCurrentHand().getCardValues();
            var p2CardValues = player2.getCurrentHand().getCardValues();
            Collections.sort(p1CardValues);
            Collections.sort(p2CardValues);

            for (int h = 4; h >= 0; h--) {
                if (p1CardValues.get(h) > p2CardValues.get(h)) {
                    logger.info("Hand: " + hand + " | " + player1.getName() + " wins with highest card " + p1CardValues.get(h) + " vs " + p2CardValues.get(h));
                    return player1;
                }
                else if (p2CardValues.get(h) > p1CardValues.get(h)) {
                    logger.info("Hand: " + hand + " | " + player2.getName() + " wins with highest card " + p2CardValues.get(h) + " vs " + p1CardValues.get(h));
                    return player2;
                }
            }
        } else {
            // check who has the highest number card on the triples
            if (!player1.getCurrentHand().getTriples().isEmpty() && !player2.getCurrentHand().getTriples().isEmpty()) {
                if (player1.getCurrentHand().getTriples().get(0) > player2.getCurrentHand().getTriples().get(0)) {
                    return player1;
                } else if (player2.getCurrentHand().getTriples().get(0) > player1.getCurrentHand().getTriples().get(0)) {
                    return player2;
                }
            }

            // check who has the highest number card on the pair
            if (!player1.getCurrentHand().getPairs().isEmpty() && !player2.getCurrentHand().getPairs().isEmpty()) {
                var p1Pairs = player1.getCurrentHand().getPairs();
                var p2Pairs = player2.getCurrentHand().getPairs();

                // check who has the second-highest pair
                if (p1Pairs.size() > 1 && p2Pairs.size() > 1) {
                    // sort the pairs from highest to lowest
                    Collections.sort(p1Pairs);
                    Collections.sort(p2Pairs);
                    if (p1Pairs.get(1) > p2Pairs.get(1)) {
                        return player1;
                    } else if (p2Pairs.get(1) > p1Pairs.get(1)) {
                        return player2;
                    }
                } else {
                    // check who has the highest pair
                    if (p1Pairs.get(0) > p2Pairs.get(0)) {
                        return player1;
                    } else if (p2Pairs.get(0) > p1Pairs.get(0)) {
                        return player2;
                    }
                }
            }

            // if no winners have been found at this point return the user with the highest card
            var p1cards = player1.getCurrentHand().getCardValues();
            var p2cards = player2.getCurrentHand().getCardValues();
            Collections.sort(p1cards);
            Collections.sort(p2cards);
            for (int c = 0; c < 5; c++) {
                if (p1cards.get(c) > p2cards.get(c)) {
                    return player1;
                } else if (p2cards.get(c) > p1cards.get(c)) {
                    return player2;
                }
            }
        }
        // invalid hand
        return null;
    }

}
