package application;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class Controller2 implements Initializable {

    private Stage stage;
    private Scene scene;
    private Parent root;
    private List<String> deck;
    private List<List<String>> players;
    private List<String> centerDeck;
    private int currentPlayer;
    private int numPlayers;
    private int totalCards;
    private int movesCount; // New variable to track current center deck index
    private String winningPlayer; 
    private int highestRank; 

    @FXML
    private ImageView card1;
    @FXML
    private ImageView card2;
    @FXML
    private ImageView card3;
    @FXML
    private ImageView card4;
    @FXML
    private ImageView card5;
    @FXML
    private ImageView card6;
    @FXML
    private ImageView card7;
    @FXML
    private ImageView centerDeckView;
    @FXML
    private ImageView remainingDeckView;
    @FXML
    private Label turnLabel;
    @FXML
    private ImageView centerDeck1;
    @FXML
    private ImageView centerDeck2;
    @FXML
    private ImageView centerDeck3;
    @FXML
    private ImageView centerDeck4;
    @FXML
    private Label winnerLabel;

    public Controller2() {
        deck = new ArrayList<>();
        players = new ArrayList<>();
        centerDeck = new ArrayList<>();
        currentPlayer = 1;
        numPlayers = 4;
        totalCards = 7;
        movesCount = 1; // Initialize current center deck index to 1
        winningPlayer = null;
        highestRank = 0;
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        initializeDeck();
        shuffleDeck();
        dealCards();
        determineStartingPlayer();
        displayCards();
        // Clear the winner label
        winnerLabel.setText("");
    }

    public void switchToScene1(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Scene.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
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

        // Clear the center deck
        centerDeck.clear();

        // Move the top card from the deck to the center
        centerDeck.add(deck.remove(0));

        // Reset the current center deck index to 1
        movesCount = 1;
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

    private void displayCards() {
        // Clear the card images for the current player's cards
        for (int i = 0; i < totalCards; i++) {
            ImageView imageView = getImageViewByIndex(i + 1);
            imageView.setImage(null);
        }

        // Set the image for the center card
        String centerCardImagePath = "C:/Users/User/eclipse-workspace/HelloFX/src/application/cards/"
                + centerDeck.get(0) + ".png";
        Image centerCardImage = new Image(centerCardImagePath);
        centerDeckView.setImage(centerCardImage);

        // Set the card images for the current player's cards
        List<String> currentPlayerCards = players.get(currentPlayer - 1);
        for (int i = 0; i < currentPlayerCards.size(); i++) {
            String imagePath = "C:/Users/User/eclipse-workspace/HelloFX/src/application/cards/" + currentPlayerCards.get(i) + ".png";
            Image cardImage = new Image(imagePath);
            setCardImage(i + 1, cardImage);
        }

        // Display the remaining deck
        if (!deck.isEmpty()) {
            String topCardImagePath = "C:/Users/User/eclipse-workspace/HelloFX/src/application/cards/"
                    + deck.get(deck.size() - 1) + ".png";
            Image topCardImage = new Image(topCardImagePath);
            remainingDeckView.setImage(topCardImage);
        }

        turnLabel.setText("PLAYER " + currentPlayer + "'S CARDS");
    }

    private void setCardImage(int cardIndex, Image cardImage) {
        ImageView imageView = getImageViewByIndex(cardIndex);
        if (imageView != null) {
            imageView.setImage(cardImage);
        }
    }

    private ImageView getImageViewByIndex(int cardIndex) {
        switch (cardIndex) {
            case 1:
                return card1;
            case 2:
                return card2;
            case 3:
                return card3;
            case 4:
                return card4;
            case 5:
                return card5;
            case 6:
                return card6;
            case 7:
                return card7;
            default:
                return null;
        }
    }

    @FXML
    private void moveCardToCenter(MouseEvent event) {
        // Get the clicked ImageView
        ImageView sourceImageView = (ImageView) event.getSource();

        // Get the image from the clicked ImageView
        Image cardImage = sourceImageView.getImage();

        // Find the index of the clicked card in the player's deck
        int cardIndex = getPlayerCardIndex(sourceImageView);

        if (cardIndex != -1) {
            // Remove the card from the player's deck
            List<String> currentPlayerCards = players.get(currentPlayer - 1);
            String playerCard = currentPlayerCards.remove(cardIndex);

            // Set the image to the destination ImageView based on currentCenterDeckIndex
            switch (movesCount) {
                case 1 -> centerDeck1.setImage(cardImage);
                case 2 -> centerDeck2.setImage(cardImage);
                case 3 -> centerDeck3.setImage(cardImage);
                case 4 -> centerDeck4.setImage(cardImage);
            }

            // Remove the card image from the clicked ImageView
            sourceImageView.setImage(null);

            int rankValue = getRankValue(playerCard.substring(1));

            // Determine the winner based on the highest ranking card
            if (rankValue > highestRank) {
                highestRank = rankValue;
                winningPlayer = "PLAYER " + currentPlayer;
            }
            // Update the player turn
            updatePlayerTurn();

            // Increment the currentCenterDeckIndex for the next player's turn
            movesCount++;
            if (movesCount > 4) {
                movesCount = 1;
            }

            // Display the cards for the next player
            displayCards();
        }
        // Check if all center decks are fully contained
        if (movesCount == 1) {
            // Update the winner label
            winnerLabel.setText(winningPlayer + " WINS!");

            // Clear the center decks
            centerDeck.clear();
            centerDeckView.setImage(null);
            centerDeck1.setImage(null);
            centerDeck2.setImage(null);
            centerDeck3.setImage(null);
            centerDeck4.setImage(null);
        }
    }

    private int getPlayerCardIndex(ImageView imageView) {
        // Iterate over the ImageView objects for the current player's cards
        for (int i = 0; i < 7; i++) {
            ImageView playerCardImageView = getImageViewByIndex(i + 1);
            if (imageView == playerCardImageView) {
                return i;
            }
        }
        return -1; // Card not found
    }

    private void updatePlayerTurn() {
        // Increment the currentPlayer variable to the next player
        currentPlayer++;
        if (currentPlayer > numPlayers) {
            currentPlayer = 1;
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
    
    @FXML
    private void exit() {
    	Alert alert = new Alert(AlertType.CONFIRMATION);
    	alert.setTitle("Exit");
    	alert.setHeaderText("You're about to exit!");
    	alert.setContentText("Are you sure you want to exit?");
    	
    	if(alert.showAndWait().get() == ButtonType.OK) {
        	System.exit(0);
    	}
    }
}
