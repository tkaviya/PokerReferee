package com.t3ratech;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;

import static com.t3ratech.HAND_RANK.*;
import static java.lang.System.out;

/***************************************************************************
 * Created:     06 / 12 / 2022                                             *
 * Platform:    Ubuntu Linux x86_64                                        *
 * Author:      Tsungai Kaviya                                             *
 * Contact:     tsungai.kaviya@gmail.com                                   *
 ***************************************************************************/

enum HAND_RANK {
    ROYAL_FLUSH, STRAIGHT_FLUSH, FOUR_OF_A_KIND, FULL_HOUSE,
    FLUSH, STRAIGHT, THREE_OF_A_KIND, TWO_PAIRS, ONE_PAIR, HIGH_CARD
}

class Result {

    private int points;
    private HashMap<String, Integer> processedHand;

    Result(int points, HashMap<String, Integer> processedHand) {
        this.points = points;
        this.processedHand = processedHand;
    }

    public int getPoints() {
        return points;
    }

    public HashMap<String, Integer> getProcessedHand() {
        return processedHand;
    }
}

public class PokerReferee {

    public static void main(String[] args) {

        var filename = "p054_poker.txt";

        File pokerFile = new File(filename);
        try (var fileReader = new FileReader(pokerFile);
             var bufferedReader = new BufferedReader(fileReader)) {
            String line;
            int lineNumber = 1;
            int p1Wins = 0;
            while ((line = bufferedReader.readLine()) != null) {

                var cards = line.split(" ");

                // extract first 5 cards as player 1 cards, last five as player 2 cards
                var p1Cards = new ArrayList<>(Arrays.asList(cards).subList(0, 5));
                var p2Cards = new ArrayList<>(Arrays.asList(cards).subList(5, 10));
                int winner = getWinner(p1Cards, p2Cards);
                if (winner == 1) {
                    ++p1Wins;
                }
                out.printf("Winner of match %s : Player %s%n", lineNumber++, winner);
            }
            out.printf("Player 1 wins %d times%n", p1Wins);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static int getWinner(ArrayList<String> p1Cards, ArrayList<String> p2Cards) {
        // return player with the highest points
        var p1Results = getResults(1, p1Cards);
//        out.printf("P1: %s | %s points ", getHand(p1Cards), p1Results.getPoints());

        var p2Results = getResults(2, p2Cards);
//        out.printf("P2: %s | %s points ", getHand(p2Cards), p2Results.getPoints());

        if (p1Results.getPoints() > p2Results.getPoints()) {
            return 1;
        }
        else if (p1Results.getPoints() < p2Results.getPoints()) {
            return 2;
        } else {// if points are equal, look for the highest number of each player
            Integer winner = 0;

            // check cards starting with each players' highest, going downwards on each draw until a winner is found
            while (winner == 0) {
                int p1Highest = 0, p2Highest = 0;
                // search for the highest card each player has, starting from the top for efficiency and stop at the first
                for (int h = 14; h > 0; h--) {
                    if (p1Highest == 0 && p1Results.getProcessedHand().get(String.valueOf(h)) != null) {
                        p1Highest = h;
                        // remove the number from the hashmap so it wont be compared again next time
                        p1Results.getProcessedHand().remove(String.valueOf(h));
                    }
                    if (p2Highest == 0 && p2Results.getProcessedHand().get(String.valueOf(h)) != null) {
                        p2Highest = h;
                        // remove the number from the hashmap so it wont be compared again next time
                        p2Results.getProcessedHand().remove(String.valueOf(h));
                    }
                }
                out.println("p1Highest = " + p1Highest);
                out.println("p2Highest = " + p2Highest);
                // check if we have finally found a winner
                     if (p1Highest > p2Highest) { winner = 1; }
                else if (p1Highest < p2Highest) { winner = 2; }
            }
            return winner;
        }
    }

    private static Result getResults(int player, ArrayList<String> hand) {

        var results = processHand(hand);

        if (results.get(ROYAL_FLUSH.name()) != null && results.get(ROYAL_FLUSH.name()) == 1) {
            out.printf("Player %s: ROYAL FLASH! 10 points%n", player);
            return new Result(10, results);
        }

        if (results.get(STRAIGHT.name()) == 1 && results.get(FLUSH.name()) == 1) {
            out.printf("Player %s: STRAIGHT FLUSH! 9 points%n", player);
            return new Result(9, results);
        }

        // test for 4 of a kind
        for (int c = 1; c < 14; c++) {
            if (results.get(String.valueOf(c)) != null && results.get(String.valueOf(c)) == 4) {
                out.printf("Player %s: Four of a Kind! 8 points%n", player);
                return new Result(8, results);
            }
        }

        // test for 3 of a kind
        for (int c = 1; c < 14; c++) {
            if (results.get(String.valueOf(c)) != null && results.get(String.valueOf(c)) == 3) {
                // if there is a triple, restart and test for a pair
                for (int p = 1; p < 14; p++) {
                    if (results.get(String.valueOf(p)) != null && results.get(String.valueOf(p)) == 2) {
                        out.printf("Player %s: Full House. 7 points%n", player);
                        return new Result(7, results);
                    }
                }
                // if there are 3 of the same kind but no pair it's not a full house, but 3 of a kind
                out.printf("Player %s: Three of a Kind. 4 points%n", player);
                return new Result(4, results);
            }
        }

        if (results.get(FLUSH.name()) == 1) {
            out.printf("Player %s: Flush. 6 points%n", player);
            return new Result(6, results);
        }

        if (results.get(STRAIGHT.name()) == 1) {
            out.printf("Player %s: Straight. 5 points%n", player);
            return new Result(5, results);
        }


        // test for 2 of a pairs
        for (int c = 1; c < 14; c++) {
            if (results.get(String.valueOf(c)) != null && results.get(String.valueOf(c)) == 2) {
                // if there is a pair, restart and test for another pair
                for (int p = 1; p < 14; p++) {
                    // skip if we have already tested this value
                    if (p == c ) { continue; }
                    if (results.get(String.valueOf(p)) != null && results.get(String.valueOf(p)) == 2) {
                        out.printf("Player %s: 2 Pairs. 3 points%n", player);
                        return new Result(3, results);
                    }
                }
                // if is only 1 pair we score it as a single Pair
                out.printf("Player %s: 1 Pair. 2 points%n", player);
                return new Result(2, results);
            }
        }

        out.printf("Player %s: No special hand%n", player);
        return new Result(0, results);
    }

    private static HashMap<String, Integer> processHand(ArrayList<String> hand) {

        HashMap<String, Integer> results = new HashMap<>();
        boolean hasTen = false, hasJack = false, hasQueen = false, hasKing = false, hasAce = false;
        String firstSuit = hand.get(0).substring(1,2);

        // deck will be tested and this value will be updated if not a royal flush
        results.put(ROYAL_FLUSH.name(), 1);

        // deck will be tested and this value will be updated if not a flush
        results.put(FLUSH.name(), 1);

        // deck will be tested and this value will be updated if not a straight
        results.put(STRAIGHT.name(), 1);

        ArrayList<Integer> cardsByValue = new ArrayList<>();

        // iterate once over all the cards and collect all the information you can
        for (String card : hand)
        {
            // test if we have all cards required for a royal flush
                 if (card.startsWith("T")) { hasTen = true; }
            else if (card.startsWith("J")) { hasJack = true; }
            else if (card.startsWith("Q")) { hasQueen = true; }
            else if (card.startsWith("K")) { hasKing = true; }
            else if (card.startsWith("A")) { hasAce = true; }

            // if at any point we find a different suit, a flush & royal flush cannot be achieved so end the test
            if (!card.substring(1,2).equalsIgnoreCase(firstSuit)) {
                results.put(FLUSH.name(), 0);
                results.put(ROYAL_FLUSH.name(), 0);
            }

            String cardValueAsString = card.substring(0,1);
            Integer cardValueAsInteger = getCardValue(cardValueAsString);
            cardsByValue.add(cardValueAsInteger);

            // count how many of each card you have
            if (results.get(String.valueOf(cardValueAsInteger)) == null) {
                results.put(String.valueOf(cardValueAsInteger), 1);
            } else {
                results.put(String.valueOf(cardValueAsInteger), results.get(String.valueOf(cardValueAsInteger)) + 1);
            }
        }

        // test for royal flush
        if (hasTen && hasJack && hasQueen && hasKing && hasAce) {
            // We have already tested for matching suit, so at this point this is a ROYAL FLUSH!
            // Return, no need to test anything else
            return results;
        } else {
            results.put(ROYAL_FLUSH.name(), 0);
        }

        // test for straight
        Integer previousCardValue = null;
        cardsByValue.sort(Collections.reverseOrder());
        for (Integer cardValue : cardsByValue) {
            if (previousCardValue == null) {
                previousCardValue = cardValue;
            } else if (previousCardValue - 1 != cardValue) {
                results.put(STRAIGHT.name(), 0);
            } else {
                previousCardValue = cardValue;
            }
        }

        return results;
    }

    private static String getHand(ArrayList<String> cards) {
        StringBuilder hand = new StringBuilder();
        for (String card : cards) {
            hand.append(card).append(" ");
        }
        return hand.toString();
    }

    private static Integer getCardValue(String cardValue) {
        if (isNumeric(cardValue)) {
            return Integer.valueOf(cardValue);
        } else {
            switch (cardValue) {
                case "T" -> { return 10; }
                case "J" -> { return 11; }
                case "Q" -> { return 12; }
                case "K" -> { return 13; }
                case "A" -> { return 14; }
            }
        }

        // invalid data
        return null;
    }

    private static boolean isNumeric(Object testObject) {
        if (testObject == null) return false;
        if (testObject instanceof Number) return true;
        try {
            new BigDecimal(String.valueOf(testObject));
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
