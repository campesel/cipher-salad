package gui.controllers;

import exceptions.CharacterNotSupportedException;
import exceptions.InvalidKeyException;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import keys.Key;
import keys.Keyword;
import utils.Normalizer;

public class VigenereController implements KeyGeneratorController {
	
	@FXML private TextField keywordTextField;
	@FXML private Label lengthLabel;
	
	@FXML
	private void updateLength() {
		int length = keywordTextField.getText().length();

		lengthLabel.setText(Integer.toString(length));
	}

	@Override
	public Key generateKey() throws CharacterNotSupportedException, InvalidKeyException {
		String keyword = keywordTextField.getText();
		
		if (keyword.isEmpty()) {
			throw new InvalidKeyException("No keyword has been entered.");
		}

		keyword = Normalizer.normalize(keyword);
		
		keywordTextField.setText(keyword);
		
		return new Keyword(keyword);
	}
	
}
