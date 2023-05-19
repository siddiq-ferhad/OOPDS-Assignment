import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GoBoom {
    private List<String> deck;           // Store the cards in the deck
    private List<List<String>> players;  // Store the cards for each player
    private List<String> centerDeck;     // Store the cards played in the center
    private int currentPlayer;           // Represent the current player
    private int numPlayers;              // Represent the total number of players
    private int totalCards;              // Represent the amount of cards distributed to each player

    public GoBoom() {
        deck = new ArrayList<>();
        players = new ArrayList<>();
        centerDeck = new ArrayList<>();
        currentPlayer = 1;
        numPlayers = 4;
        totalCards = 7;
        initializeDeck();
        shuffleDeck();
        dealCards();
        determineStartingPlayer();
    }

    public static void main(String[] args) {
        GoBoom game = new GoBoom();
        game.displayGameState();
    }

    private void initializeDeck() {
        String[] suits = { "h", "d", "c", "s" };
        String[] ranks = { "A", "K", "Q", "J", "10", "9", "8", "7", "6", "5", "4", "3", "2" };

        for (String suit : suits) {
            for (String rank : ranks) {
                // Add each card combination to the deck
                deck.add(suit + rank);
            }
        }
    }

    private void shuffleDeck() {
        // Shuffle the cards in the deck randomly
        Collections.shuffle(deck);
        // Move the top card from the deck to the center
        centerDeck.add(deck.remove(0));
    }

    private void dealCards() {
        for (int i = 0; i < numPlayers; i++) {
            List<String> playerCards = new ArrayList<>();
            // Add an empty list for each player
            players.add(playerCards);

            for (int j = 0; j < totalCards; j++) {
                // Deal 7 cards to each player from the deck
                playerCards.add(deck.remove(0));
            }
        }
    }

    private void determineStartingPlayer() {
        String leadCard = centerDeck.get(0).substring(1);

        // Determine the starting player based on the rank of the first lead card
        if (leadCard.matches("[A|5|9|K]")) {
            currentPlayer = 1;
        } else if (leadCard.matches("[2|6]")) {
            currentPlayer = 2;
        } else if (leadCard.matches("[3|7|J]")) {
            currentPlayer = 3;
        } else if (leadCard.matches("[4|8|Q]")) {
            currentPlayer = 4;
        } else if (leadCard.startsWith("10")) {
            currentPlayer = 2;
        }
    }

    private void displayGameState() {
        for (int i = 0; i < numPlayers; i++) {
            // Display each player's cards
            System.out.println("Player " + (i + 1) + ": " + players.get(i));
        }

        // Display the cards in the center deck
        System.out.println("Center  : " + centerDeck);

        // Display the remaining cards in the deck
        System.out.println("Deck    : " + deck);

        // Display the score of each player
        System.out.print("Score   : ");
        for (int i = 1; i <= numPlayers; i++) {
            System.out.print("Player" + i + " = 0" + ((i != numPlayers) ? " | " : ""));
        }
        System.out.println();

        // Display the current player's turn
        System.out.println("Turn    : Player" + currentPlayer);
    }
}
