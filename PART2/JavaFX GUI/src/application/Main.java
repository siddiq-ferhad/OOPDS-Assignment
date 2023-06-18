package application;
	
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;


public class Main extends Application {
	
	@Override
	public void start(Stage stage) {
		try {
			
			Parent root = FXMLLoader.load(getClass().getResource("Scene.fxml"));
			Scene scene = new Scene(root);
			
			Image icon = new Image("icon.png");
			stage.getIcons().add(icon);		
			stage.setTitle("Go Boom Game");
			stage.setWidth(1020);
			stage.setHeight(740);
			stage.setResizable(false);
			//stage.setFullScreen(true);
			stage.setScene(scene);
			stage.show();
			stage.setOnCloseRequest(event -> {
				event.consume();
				exit(stage);
			});
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void exit(Stage stage) {
    	Alert alert = new Alert(AlertType.CONFIRMATION);
    	alert.setTitle("Exit");
    	alert.setHeaderText("You're about to exit!");
    	alert.setContentText("Are you sure you want to exit?");
    	
    	if(alert.showAndWait().get() == ButtonType.OK) {
        	stage.close();
    	}
    }
	
	public static void main(String[] args) {
		launch(args);
	}
}
