package application;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import javafx.application.Application;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class GoBoom {
    private List<String> deck; // Store the cards in the deck
    private List<List<String>> players; // Store the cards for each player
    private List<String> centerDeck; // Store the cards played in the center
    private List<Integer> playerScores; // Store the score for each player
    private int currentPlayer; // Represent the current player
    private int numPlayers; // Represent the total number of players
    private int trickCount; // Represent the current trick
    private int totalCards; // Represent the amount of cards distributed to each player
    private Scanner scanner; // Scanner for user input

    public GoBoom() {
        deck = new ArrayList<>();
        players = new ArrayList<>();
        centerDeck = new ArrayList<>();
        currentPlayer = 1;
        numPlayers = 4;
        trickCount = 1;
        totalCards = 7;
        initializeDeck();
        initializeScore();
        shuffleDeck();
        dealCards();
        determineStartingPlayer();
        scanner = new Scanner(System.in);
    }

    public static void main(String[] args) {
        // Launch the JavaFX application
        Application.launch(Main.class, args);
        GoBoom game = new GoBoom();
        game.play();
        // Close the scanner after the game is complete
        game.closeScanner();
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

    private void initializeScore() {
        playerScores = new ArrayList<>();
        for (int i = 0; i < numPlayers; i++) {
            playerScores.add(0);
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
        switch (leadCard) {
            case "A", "5", "9", "K" -> currentPlayer = 1;
            case "2", "6", "10" -> currentPlayer = 2;
            case "3", "7", "J" -> currentPlayer = 3;
            case "4", "8", "Q" -> currentPlayer = 4;
        }        
    }

    public void play() {
        while (true) {
            int movesCount = 0;
            String winningPlayer = null;
            int highestRank = 0;

            while (movesCount < numPlayers) {
                displayGameState();

                System.out.print("> ");
                String userInput = scanner.nextLine();
                System.out.println();

                if (userInput.equals("x")) {
                    System.out.println("Exiting the game... See ya!");
                    System.exit(0);
                } else if (userInput.equals("s")) {
                    System.out.println("Starting a new game...\n");
                    GoBoom newGame = new GoBoom();
                    newGame.play();
                    return;
                } else if (userInput.equals("d")) {
                    if (deck.isEmpty()) {
                        System.out.println("No more cards in the deck!\n");
                        continue;
                    }
                    String drawnCard = deck.remove(0);
                    List<String> currentPlayerCards = players.get(currentPlayer - 1);
                    currentPlayerCards.add(drawnCard);

                    System.out.println("Player" + currentPlayer + " draws a card: " + drawnCard + "\n");
                    continue;
                } else if (userInput.equals("skip")) {
                    movesCount++;
                    currentPlayer = getNextPlayer();
                    System.out.println("Player" + currentPlayer + " takes the turn.\n");
                    continue;
                } else if (userInput.equals("h")) {
                    System.out.println("------------------------------");
                    System.out.println("           Command            ");
                    System.out.println("------------------------------");
                    System.out.println("1) s => Start a new game      ");
                    System.out.println("2) x => Exit the game         ");
                    System.out.println("3) d => Draw cards from deck  ");
                    System.out.println("4) skip => Skip to next player");
                    System.out.println("5) save => Save the game      ");
                    System.out.println("6) load => Load the game      ");
                    System.out.println("------------------------------\n");
                    System.out.print("Press Enter To Continue . . . .\n\n");
                    scanner.nextLine();
                    continue;
                } else if (userInput.equals("save")) {
                    saveGame();
                    continue;
                } else if (userInput.equals("load")) {
                    loadGame();
                    play();
                }

                List<String> currentPlayerCards = players.get(currentPlayer - 1);
                String playerCard = userInput;

                if (currentPlayerCards.contains(playerCard) && !centerDeck.contains(playerCard)) {
                    // Remove the played card from the player's deck
                    currentPlayerCards.remove(playerCard);

                    // Add the played card to the center deck
                    centerDeck.add(playerCard);

                    if (currentPlayerCards.isEmpty()) {
                        System.out.println("BOOM!! Congrats Player" + currentPlayer + " for becoming the winner!");
                        System.out.println("Brace yourself, a new round is about to begin!\n");

                        updateScores(); // Update the score based on remaining cards
                        startNewRound(); // Start a new round
                        return;
                    }

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
                currentPlayer = getNextPlayer();
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
    }

    private void startNewRound() {
        trickCount = 1;
        currentPlayer = 1;
    
        // Clear the center deck
        centerDeck.clear();
    
        // Clear the players' decks
        for (List<String> playerCards : players) {
            playerCards.clear();
        }
    
        // Clear the deck
        deck.clear();
    
        // Clear the players' list
        players.clear();

        // Add a new set of 52 cards to the deck
        initializeDeck();
        shuffleDeck();
        dealCards();
        determineStartingPlayer();
        play();
    }

    private int getNextPlayer() {
        int nextPlayer = currentPlayer + 1;
        if (nextPlayer > numPlayers) {
            nextPlayer = 1;
        }
        return nextPlayer;
    }

    private void updateScores() {
        for (int i = 0; i < numPlayers; i++) {
            List<String> playerCards = players.get(i);
            int score = playerScores.get(i);

            for (String card : playerCards) {
                int rankValue = getRankValue(card.substring(1));
                // Calculate the score
                switch (rankValue) {
                    case 11, 12, 13 -> score += 10;
                    case 14 -> score += 1;
                    default -> score += rankValue;
                }
            }
            playerScores.set(i, score);
        }
    }

    private void displayScores() {
        System.out.print("Score   : ");
        for (int i = 0; i < numPlayers; i++) {
            System.out.print("Player" + (i + 1) + " = " + playerScores.get(i) + ((i != numPlayers - 1) ? " | " : ""));
        }
        System.out.println();
    }

    private void displayGameState() {
        String trickName = "Trick #" + trickCount + "   (Press h for help)";
        System.out.println(trickName);

        for (int i = 0; i < numPlayers; i++) {
            // Display each player's cards
            System.out.println("Player " + (i + 1) + ": " + players.get(i));
        }

        // Display the cards in the center deck
        System.out.println("Center  : " + centerDeck);

        // Display the remaining cards in the deck
        System.out.println("Deck    : " + deck);

        // Display the score of each player
        displayScores();

        // Display the current player's turn
        System.out.println("Turn    : Player" + currentPlayer);
    }

    public void saveGame() {
        try {
            // Create a new file named "savegame.txt"
            File file = new File("savegame.txt");
            FileWriter writer = new FileWriter(file);

            // Write the game state to the file
            for (List<String> playerDeck : players) {
                writer.write(String.join(",", playerDeck) + "\n");
            }
            writer.write(String.join(",", centerDeck) + "\n");
            writer.write(String.join(",", deck) + "\n");
            writer.write(currentPlayer + "\n");
            writer.write(trickCount + "\n");
            writer.write(
                    String.join(",", playerScores.stream().map(Object::toString).collect(Collectors.toList())));

            // Close the writer
            writer.close();

            System.out.println("Game saved successfully!\n");
        } catch (IOException e) {
            System.out.println("Failed to save the game!");
        }
    }

    public void loadGame() {
        try {
            // Read the saved game state from the file
            File file = new File("savegame.txt");
            Scanner scanner = new Scanner(file);

            // Analyze the game state from the file
            for (List<String> playerDeck : players) {
                playerDeck.clear();
                playerDeck.addAll(List.of(scanner.nextLine().split(",")));
            }
            centerDeck.clear();
            centerDeck.addAll(List.of(scanner.nextLine().split(",")));

            deck.clear();
            deck.addAll(List.of(scanner.nextLine().split(",")));

            currentPlayer = Integer.parseInt(scanner.nextLine());
            trickCount = Integer.parseInt(scanner.nextLine());

            playerScores.clear();
            playerScores.addAll(Arrays.stream(scanner.nextLine().split(","))
                    .map(Integer::parseInt)
                    .collect(Collectors.toList()));
            scanner.close();

            System.out.println("Game loaded successfully!\n");
        } catch (IOException e) {
            System.out.println("Failed to load the game!");
        }
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

    private void closeScanner() {
        scanner.close();
    }
}
