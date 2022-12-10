package com.t3ratech.poker_referee.engine;

import com.t3ratech.poker_referee.data.PokerHand;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

import static com.t3ratech.poker_referee.data.PokerHandRank.*;

/***************************************************************************
 * Created:     09 / 12 / 2022                                             *
 * Platform:    Ubuntu Linux x86_64                                        *
 * Author:      Tsungai Kaviya                                             *
 * Contact:     tsungai.kaviya@gmail.com                                   *
 ***************************************************************************/

@Test
public class PokerEngineTest {

    private PokerEngine pokerEngine;

    @BeforeClass
    public void setUp() {
        Referee referee = new Referee();
        pokerEngine = new PokerEngine(referee);
    }

    @Test
    public void testEvaluateGames() {
        System.out.println("RUNNING TEST: PokerEngine.evaluateGames");
        ArrayList<String> games = new ArrayList<>();

        games.add("QD TS 7D AS AC 2C TD 6H 8H TH"); // P1 wins with High Pair
        games.add("6S AD 8C 4S 8H 8D 9D KH 8S 3C"); // P1 wins with Equal Pair but high card
        games.add("QS QH JD JS TH JH JC QD QC AC"); // P2 wins with 2 Equal Pairs but high card
        games.add("QS 9S 7H KC TD TH 5H JS AC JH"); // P2 wins with 1 Pair
        games.add("6D AC 2S QS 7C AS KS JS QH TS"); // P2 wins with straight
        games.add("6H 8H 5H 4H 7H 2H 5C 9C 9D 6C"); // P1 wins with straight flush
        games.add("AS KS JS QS TS JC AC QD TD 3H"); // P1 wins with ROYAL FLUSH!!!
        games.add("4H QC 8H JD 4C KD KS 5C KC 7S"); // P2 wins with 3 of a kind
        games.add("6D 2D 6H 2S 6D 5S 7H AS TH 6S"); // P1 wins with full house
        games.add("JS 2C TC 6H AD JC AC 7D TD 3H"); // P2 wins with 7 vs 6 points after 3 tie breaks

        var results = pokerEngine.evaluateGames(games);
        Assert.assertEquals(results.get(1), 5);

        games.clear();

        games.add("TC TD QC TH TS AS 2D 2S 2H AC"); //P1 wins with 4 of a kind
        games.add("QS QD QC 9D 9C KH JS 4H KD 9D"); //P1 wins with full house
        games.add("4C 7C 3C JC QS 9C KC AS 8D 3D"); //P2 wins with 14 points vs 12
        games.add("KC 7H QC 5H 8H 6S 5S AS 7S 8S"); //P2 wins with FLUSH
        games.add("3S AD 9H KS 6D JD AS KH 6S JH"); //P2 wins with 1 pair
        games.add("AD 3D TS 5C 7H JH 2D JS QD AC"); //P2 wins with 1 pair
        games.add("9C JD 7C AC TC 6H 6C JC 3D 3S"); //P2 wins with 2 pairs
        games.add("QC KC 3S KS KD 2C 8D AH QS TS"); //P1 wins with 3 of a kind
        games.add("AS KD 3D 7H 8H 7C 8C 5C 4D 6C"); //P2 wins with straight

        results = pokerEngine.evaluateGames(games);
        Assert.assertEquals(results.get(2), 6);
    }

    @Test
    public void testRankHand() {
        System.out.println("RUNNING TEST: PokerHand.rankHand");
        Assert.assertEquals(new PokerHand(List.of("QD TS 7D AS AC".split(" "))).getHandRank(), ONE_PAIR);
        Assert.assertEquals(new PokerHand(List.of("9C KC AS 8D 3D".split(" "))).getHandRank(), NONE);
        Assert.assertEquals(new PokerHand(List.of("6S 5S AS 7S 8S".split(" "))).getHandRank(), FLUSH);
        Assert.assertEquals(new PokerHand(List.of("7C 8C 5C 4C 6C".split(" "))).getHandRank(), STRAIGHT_FLUSH);
        Assert.assertEquals(new PokerHand(List.of("QC KC 3S KS KD".split(" "))).getHandRank(), THREE_OF_A_KIND);
        Assert.assertEquals(new PokerHand(List.of("6H 6C JC 3D 3S".split(" "))).getHandRank(), TWO_PAIRS);
        Assert.assertEquals(new PokerHand(List.of("AS KS JS QS TS".split(" "))).getHandRank(), ROYAL_FLUSH);
        Assert.assertEquals(new PokerHand(List.of("6D 2D 6H 2S 6D".split(" "))).getHandRank(), FULL_HOUSE);
        Assert.assertEquals(new PokerHand(List.of("AH TS AD AS AC".split(" "))).getHandRank(), FOUR_OF_A_KIND);
        Assert.assertEquals(new PokerHand(List.of("7C 8C 5C 4D 6C".split(" "))).getHandRank(), STRAIGHT);
    }
}
