import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class GoBoom {
    private List<String> deck;           // Store the cards in the deck
    private List<List<String>> players;  // Store the cards for each player
    private List<String> centerDeck;     // Store the cards played in the center
    private int currentPlayer;           // Represent the current player
    private int numPlayers;              // Represent the total number of players
    private int trickCount;              // Represent the current trick count
    private int totalCards;              // Represent the amount of cards distributed to each player

    public GoBoom() {
        deck = new ArrayList<>();
        players = new ArrayList<>();
        centerDeck = new ArrayList<>();
        currentPlayer = 1;
        numPlayers = 4;
        trickCount = 1;
        totalCards = 7;
        initializeDeck();
        shuffleDeck();
        dealCards();
        determineStartingPlayer();
    }

    public static void main(String[] args) {
        GoBoom game = new GoBoom();
        game.play();
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

    public void play() {
        Scanner scanner = new Scanner(System.in);

        while (trickCount < 5) {
            int movesCount = 0;
            String winningPlayer = null;
            int highestRank = 0;

            while (movesCount < numPlayers) {
                displayGameState();

                System.out.print("> ");
                String playerCard = scanner.nextLine().trim();
                System.out.println();

                List<String> currentPlayerCards = players.get(currentPlayer - 1);

                if (currentPlayerCards.contains(playerCard) && !centerDeck.contains(playerCard)) {
                    // Remove the played card from the player's deck
                    currentPlayerCards.remove(playerCard);

                    // Add the played card to the center deck
                    centerDeck.add(playerCard);

                    int rankValue = getRankValue(playerCard.substring(1));
                    // Determine the winner based on the highest ranking card
                    if (rankValue > highestRank) {
                        highestRank = rankValue;
                        winningPlayer = "Player" + currentPlayer;
                    }
                    movesCount++;
                } else {
                    System.out.println("Invalid card! Please enter a card from your deck.\n");
                    continue;
                }

                currentPlayer++;
                if (currentPlayer > numPlayers) {
                    currentPlayer = 1;
                }
            }
            // Display the winner
            System.out.println("*** " + winningPlayer + " wins Trick #" + trickCount + " ***\n");
            int winningPlayerIndex = Integer.parseInt(winningPlayer.substring(6));

            trickCount++;

            // Set the winner as the current player
            currentPlayer = winningPlayerIndex;

            // Clear the center deck for the next trick
            centerDeck.clear();
        }
        scanner.close();
    }        

    private int getRankValue(String rank) {
        switch (rank) {
            // Convert rank to an integer value
            case "A":
                return 14;
            case "K":
                return 13;
            case "Q":
                return 12;
            case "J":
                return 11;
            default:
                return Integer.parseInt(rank);
        }
    }
}
