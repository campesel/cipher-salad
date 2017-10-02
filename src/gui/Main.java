package gui;
	
import java.io.IOException;

import exceptions.CharacterNotSupportedException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import utils.Alphabet;
import utils.AppProperties;
import utils.TextStatistics;

public class Main extends Application {
	
	@Override
	public void start(Stage primaryStage) {

		try {
			// Load properties
			AppProperties.loadProperties("config.properties");

			// Load resources
			String frequenciesPath = AppProperties.getProperty("frequencies_eng");
			String ngramsPath = AppProperties.getProperty("quadgrams_eng");
			
			TextStatistics.initialize(frequenciesPath, ngramsPath);
			
			// Load main view
			Parent root = FXMLLoader.load(getClass().getResource("MainView.fxml"));

			// Set up window
			primaryStage.setTitle(AppProperties.getProperty("main.title"));
			
			int minWidth = Integer.parseInt(AppProperties.getProperty("main.minWidth"));
			int minHeight = Integer.parseInt(AppProperties.getProperty("main.minHeight"));

			primaryStage.setMinWidth(minWidth);
			primaryStage.setMinHeight(minHeight);

			primaryStage.setScene(new Scene(root));
			
			// Show window
			primaryStage.show();
		} catch(CharacterNotSupportedException | IOException e) {
			Dialogs.showErrorDialog(e.getMessage());
		}

	}
	
	public static Alphabet getAlphabetInUse() {
		return Alphabet.UPPERCASE;
	}
	
	public static void main(String[] args) {
		launch(args);
	}

}