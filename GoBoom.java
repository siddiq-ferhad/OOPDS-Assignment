import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GoBoom {
    public static void main(String[] args) {
        List<String> deck = new ArrayList<>();
        List<List<String>> players = new ArrayList<>();
        int numPlayers = 4;
        int totalCards = 7;

        initializeDeck(deck);
        shuffleDeck(deck);

        for (int i = 0; i < numPlayers; i++) {
            List<String> playerCards = new ArrayList<>();
            players.add(playerCards);

            for (int j = 0; j < totalCards; j++) {
                playerCards.add(deck.remove(0));
            }
        }
        displayGameState(players, deck);
    }

    private static void initializeDeck(List<String> deck) {
        String[] suits = {"h", "d", "c", "s"};
        String[] ranks = {"A", "K", "Q", "J", "10", "9", "8", "7", "6", "5", "4", "3", "2"};

        for (String suit : suits) {
            for (String rank : ranks) {
                deck.add(suit + rank);
            }
        }
    }

    private static void shuffleDeck(List<String> deck) {
        Collections.shuffle(deck);
    }

    private static void displayGameState(List<List<String>> players, List<String> deck) {
        int numPlayers = players.size();

        // Print the cards for each player
        for (int i = 0; i < numPlayers; i++) {
            System.out.println("Player " + (i + 1) + ": " + players.get(i));
        }

        // Print the remaining cards in the deck
        System.out.println("Deck    : " + deck);
    }
}
