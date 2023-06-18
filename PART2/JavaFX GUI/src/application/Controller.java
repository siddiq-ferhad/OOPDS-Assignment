package application;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class Controller {
	
	private Stage stage;
	private Scene scene;
	private Parent root;
	
	public void switchToScene2(ActionEvent event) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("Scene2.fxml"));
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	
	public void exit() {
    	Alert alert = new Alert(AlertType.CONFIRMATION);
    	alert.setTitle("Exit");
    	alert.setHeaderText("You're about to exit!");
    	alert.setContentText("Are you sure you want to exit?");
    	
    	if(alert.showAndWait().get() == ButtonType.OK) {
        	System.exit(0);
    	}
    }
}

