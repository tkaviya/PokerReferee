package com.t3ratech.poker_referee.engine;

import com.t3ratech.poker_referee.data.Player;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Comparator;
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
        } else { // if points are equal, look for the highest number of each player

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

            // invalid hand
            return null;
        }
    }

}
