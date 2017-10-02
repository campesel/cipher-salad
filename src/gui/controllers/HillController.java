package gui.controllers;

import exceptions.CharacterNotSupportedException;
import exceptions.InvalidKeyException;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import keys.Key;
import keys.KeyMatrix;
import math.SquareMatrix2;

public class HillController implements KeyGeneratorController {
	
	@FXML private TextField aTextField;
	@FXML private TextField bTextField;
	@FXML private TextField cTextField;
	@FXML private TextField dTextField;
	
	@Override
	public Key generateKey() throws CharacterNotSupportedException, InvalidKeyException {
		// Parse text fields
		int a, b, c, d;
		
		try {
			a = Integer.parseInt(aTextField.getText());
			b = Integer.parseInt(bTextField.getText());
			c = Integer.parseInt(cTextField.getText());
			d = Integer.parseInt(dTextField.getText());
		} catch (NumberFormatException e) {
			throw new InvalidKeyException();
		}
		
		// Check that the entered matrix is invertible
		SquareMatrix2 keyMatrix = new SquareMatrix2(a, b, c, d);
		
		if (!keyMatrix.isInvertible()) {
			throw new InvalidKeyException("The matrix you have entered is not invertible.");
		}
		
		return new KeyMatrix(keyMatrix);
	}
	
}
