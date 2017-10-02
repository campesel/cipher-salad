package gui.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import ciphers.CaesarCipher;
import ciphers.Cipher;
import ciphers.HillCipher;
import ciphers.SimpleSubstitutionCipher;
import ciphers.VigenereCipher;
import exceptions.CharacterNotSupportedException;
import exceptions.InvalidKeyException;
import gui.Dialogs;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import keys.Key;
import utils.Normalizer;

public class CipherController {
	
	// References to panes and controllers
	private List<Parent> panes;
	private List<KeyGeneratorController> controllers;
	
	private int selected;
	
	private boolean noCipherSelected;
	
	// References to GUI elements
	@FXML private TextArea cleartextArea;
	@FXML private TextArea ciphertextArea;
	
	@FXML private ComboBox<Cipher> cipherComboBox;
	@FXML private VBox keyVBox;
	@FXML private Label noCipherSelectedLabel;
	@FXML private Button encryptButton;
	@FXML private Button decryptButton;
	
	@FXML
	public void initialize() {
		// Add implemented ciphers
		cipherComboBox.getItems().addAll(
			new CaesarCipher(),
			new SimpleSubstitutionCipher(),
			new VigenereCipher(),
			new HillCipher()
		);
		
		// Add panes and controllers in the same order
		panes = new ArrayList<>();
		controllers = new ArrayList<>();
		
		try {
			loadPane("/gui/CaesarPane.fxml");
			loadPane("/gui/SimpleSubstitutionPane.fxml");
			loadPane("/gui/VigenerePane.fxml");
			loadPane("/gui/HillPane.fxml");
		} catch (IOException e) {
			Dialogs.showErrorDialog(e.getMessage());
		}
		
		// No cipher is initially selected
		noCipherSelected = true;
	}
	
	private void loadPane(String path) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource(path));

		panes.add(loader.load());
		controllers.add(loader.getController());
	}
	
	public String getCleartext() {
		return cleartextArea.getText();
	}
	
	public void setCleartext(String cleartext) {
		cleartextArea.setText(cleartext);
	}
	
	public String getCiphertext() {
		return ciphertextArea.getText();
	}
	
	public void setCiphertext(String ciphertext) {
		ciphertextArea.setText(ciphertext);
	}
	
	@FXML
	private void onCipherChanged() {
		if (noCipherSelected) {
			keyVBox.getChildren().remove(noCipherSelectedLabel);
			
			encryptButton.setDisable(false);
			decryptButton.setDisable(false);
			
			noCipherSelected = false;
		}
		
		// Remove previous pane
		keyVBox.getChildren().remove(panes.get(selected));
		
		// Update index
		selected = cipherComboBox.getSelectionModel().getSelectedIndex();
		
		// Add selected pane
		keyVBox.getChildren().add(panes.get(selected));
	}
	
	@FXML
	private void encrypt() {
		String normalized = null;
		Key key = null;
		
		try {
			// Normalize cleartext
			normalized = Normalizer.normalize(cleartextArea.getText());

			// Generate key
			key = controllers.get(selected).generateKey();
		} catch (CharacterNotSupportedException | InvalidKeyException e) {
			Dialogs.showErrorDialog(e.getMessage());
			
			return;
		}
		
		cleartextArea.setText(normalized);
		
		// Encrypt normalized text
		Cipher selectedCipher = cipherComboBox.getValue();
		String ciphertext = selectedCipher.encrypt(normalized, key);
		
		ciphertextArea.setText(ciphertext);
	}
	
	@FXML
	private void decrypt() {
		String normalized = null;
		Key key = null;
		
		try {
			// Normalize ciphertext
			normalized = Normalizer.normalize(ciphertextArea.getText());

			// Generate key
			key = controllers.get(selected).generateKey();
		} catch (CharacterNotSupportedException | InvalidKeyException e) {
			Dialogs.showErrorDialog(e.getMessage());
			
			return;
		}

		ciphertextArea.setText(normalized);
		
		// Decrypt normalized text
		Cipher selectedCipher = cipherComboBox.getValue();
		String cleartext = selectedCipher.decrypt(normalized, key);
		
		cleartextArea.setText(cleartext);
	}
	
}
