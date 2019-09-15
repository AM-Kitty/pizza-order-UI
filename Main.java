package application;
/*
 * Tongxu Ge,20054696
 * This program is used to create a GUI platform for pizza order system.
 */

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;

public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			//set Grid pane 
			GridPane root = (GridPane)FXMLLoader.load(getClass().getResource("FXML.fxml"));
			Scene scene = new Scene(root,850,750);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.setTitle("Pizza Order System");  //set title
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
