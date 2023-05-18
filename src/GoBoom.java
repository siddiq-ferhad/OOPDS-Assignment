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

        String leadCard = deck.remove(0);

        for (int i = 0; i < numPlayers; i++) {
            List<String> playerCards = new ArrayList<>();
            players.add(playerCards);

            for (int j = 0; j < totalCards; j++) {
                playerCards.add(deck.remove(0));
            }
        }
        displayGameState(players, deck, leadCard);
    }

    private static void initializeDeck(List<String> deck) {
        String[] suits = { "h", "d", "c", "s" };
        String[] ranks = { "A", "K", "Q", "J", "10", "9", "8", "7", "6", "5", "4", "3", "2" };

        for (String suit : suits) {
            for (String rank : ranks) {
                deck.add(suit + rank);
            }
        }
    }

    private static void shuffleDeck(List<String> deck) {
        Collections.shuffle(deck);
    }

    private static void displayGameState(List<List<String>> players, List<String> deck, String leadCard) {
        int numPlayers = players.size();

        // Display the cards for each player
        for (int i = 0; i < numPlayers; i++) {
            System.out.println("Player " + (i + 1) + ": " + players.get(i));
        }

        // Display the first lead card
        System.out.println("Center  : [" + leadCard + "]");

        // Display the remaining cards in the deck
        System.out.println("Deck    : " + deck);

        // Display the player's score
        System.out.print("Score   : ");
        for (int i = 1; i <= numPlayers; i++) {
            System.out.print("Player" + i + " = 0" + ((i != numPlayers) ? " | " : ""));
        }
        System.out.println();
    }
}
