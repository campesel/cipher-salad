package gui.controllers;

import java.io.File;
import java.io.IOException;

import exceptions.CharacterNotSupportedException;
import gui.Dialogs;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import utils.AppProperties;
import utils.FileLoader;
import utils.FileSaver;
import utils.Normalizer;

public class MainController {

	private final String about = AppProperties.getProperty("about");

	private final FileChooser fileChooser = new FileChooser();

	// Views and dialogs
	private Parent cipherView;
	private Parent breakerView;
	private Stage statisticsDialog;

	// Controllers
	private CipherController cipherController;
	private BreakerController breakerController;
	private StatisticsController statisticsController;
	
	// References to GUI elements
	@FXML private BorderPane root;
	@FXML private Button rightArrowButton;
	@FXML private Button leftArrowButton;
	
	@FXML
	public void initialize() {
		// Load FXML files
		try {
			// Cipher view
			FXMLLoader cipherLoader = new FXMLLoader(getClass().getResource("/gui/CipherView.fxml"));
			cipherView = cipherLoader.load();
			cipherController = cipherLoader.getController();
			
			// Breaker view
			FXMLLoader breakerLoader = new FXMLLoader(getClass().getResource("/gui/BreakerView.fxml"));
			breakerView = breakerLoader.load();
			breakerController = breakerLoader.getController();

			// Statistics dialog
			loadStatisticsDialog();
			
			// Configure file chooser
			configureFileChooser();
		} catch (IOException e) {
			Dialogs.showErrorDialog(e.getMessage());
		}
		
		// Set initial view
		root.setCenter(cipherView);
	}
	
	private void loadStatisticsDialog() throws IOException {
		// Load FXML file
		FXMLLoader statisticsLoader = new FXMLLoader(getClass().getResource("/gui/StatisticsDialog.fxml"));
		Parent statisticsParent = statisticsLoader.load();
		statisticsController = statisticsLoader.getController();
		
		// Set up stage
		statisticsDialog = new Stage();
		
		statisticsDialog.setTitle(AppProperties.getProperty("statistics.title"));
		
		int minWidth = Integer.parseInt(AppProperties.getProperty("statistics.minWidth"));
		int minHeight = Integer.parseInt(AppProperties.getProperty("statistics.minHeight"));

		statisticsDialog.setMinWidth(minWidth);
		statisticsDialog.setMinHeight(minHeight);
		
		statisticsDialog.setScene(new Scene(statisticsParent));
	}
	
	private void configureFileChooser() {
		fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Plain Text", "*.txt"));
	}
	
	@FXML
	private void showBreakerView() {
		rightArrowButton.setDisable(true);
		
		root.setCenter(breakerView);
		
		leftArrowButton.setDisable(false);
	}
	
	@FXML
	private void showCipherView() {
		leftArrowButton.setDisable(true);
		
		root.setCenter(cipherView);
		
		rightArrowButton.setDisable(false);
	}
	
	@FXML
	private void loadCleartext() {
		File file = showOpenDialog();
		
		if (file != null) {
			try {
				String text = FileLoader.readTextFile(file);
				
				cipherController.setCleartext(text);
			} catch (IOException e) {
				Dialogs.showErrorDialog(e.getMessage());
			}
		}
	}
	
	@FXML
	private void loadCiphertext() {
		File file = showOpenDialog();
		
		if (file != null) {
			try {
				String text = FileLoader.readTextFile(file);
				
				cipherController.setCiphertext(text);
			} catch (IOException e) {
				Dialogs.showErrorDialog(e.getMessage());
			}
		}
	}
	
	@FXML
	private void saveCleartext() {
		File file = showSaveDialog();
		
		if (file != null) {
			try {
				String text = cipherController.getCleartext();
				
				FileSaver.saveTextFile(file, text);
			} catch (IOException e) {
				Dialogs.showErrorDialog(e.getMessage());
			}
		}
	}
	
	@FXML
	private void saveCiphertext() {
		File file = showSaveDialog();
		
		if (file != null) {
			try {
				String text = cipherController.getCiphertext();
				
				FileSaver.saveTextFile(file, text);
			} catch (IOException e) {
				Dialogs.showErrorDialog(e.getMessage());
			}
		}
	}
	
	private File showOpenDialog() {
		return fileChooser.showOpenDialog(root.getScene().getWindow());
	}
	
	private File showSaveDialog() {
		return fileChooser.showSaveDialog(root.getScene().getWindow());
	}
	
	@FXML
	private void addSpaces() {
		breakerController.addSpaces();
	}
	
	@FXML
	private void copyCiphertext() {
		String ciphertext = cipherController.getCiphertext();
		
		breakerController.pasteCiphertext(ciphertext);
	}
	
	@FXML
	private void showFrequencies() {
		String cleartext = cipherController.getCleartext();
		String ciphertext = cipherController.getCiphertext();
		
		try {
			cleartext = Normalizer.normalize(cleartext);
			ciphertext = Normalizer.normalize(ciphertext);
		} catch (CharacterNotSupportedException e) {
			Dialogs.showErrorDialog(e.getMessage());
			
			return;
		}

		statisticsController.updateStatistics(cleartext, ciphertext);
		
		statisticsDialog.show();
	}
	
	@FXML
	private void showAboutDialog() {
		Dialogs.showInformationDialog("About", about);
	}
	
	@FXML
	private void quit() {
		Platform.exit();
	}

}