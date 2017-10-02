package gui;

import java.util.Optional;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextInputDialog;

public class Dialogs {

	public static void showInformationDialog(String title, String message) {
		Alert informationDialog = new Alert(AlertType.INFORMATION);
		
		informationDialog.setTitle(title);
		informationDialog.setHeaderText(null);
		informationDialog.setContentText(message);
		
		informationDialog.showAndWait();
	}
	
	public static void showErrorDialog(String message) {
		Alert errorDialog = new Alert(AlertType.ERROR);
		
		errorDialog.setTitle("Error");
		errorDialog.setHeaderText(null);
		errorDialog.setContentText(message);
		
		errorDialog.showAndWait();
	}
	
	public static String showTextInputDialog(String title, String message) {
		TextInputDialog textInputDialog = new TextInputDialog();
		
		textInputDialog.setTitle(title);
		textInputDialog.setHeaderText(null);
		textInputDialog.setContentText(message);
		
		Optional<String> result = textInputDialog.showAndWait();
		
		if (result.isPresent())
			return result.get();
		else
			return null;
	}
	
}
