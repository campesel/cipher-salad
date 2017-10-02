package gui.controllers;

import exceptions.CharacterNotSupportedException;
import exceptions.InvalidKeyException;
import gui.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import keys.Key;
import keys.SubstitutionAlphabet;
import utils.Alphabet;
import utils.Normalizer;

public class SimpleSubstitutionController implements KeyGeneratorController {
	
	private Alphabet alphabet;
	
	private ObservableList<Mapping> mappings;
	
	@FXML private TextField alphabetTextField;
	
	@FXML private TableView<Mapping> mappingsTableView;
	@FXML private TableColumn<Mapping, String> letterColumn;
	@FXML private TableColumn<Mapping, String> mappedToColumn;
	
	@FXML
	public void initialize() {
		alphabet = Main.getAlphabetInUse();

		mappings = FXCollections.observableArrayList();

		int size = alphabet.size();

		for (int i = 0; i < size; i++) {
			mappings.add(new Mapping(Character.toString(alphabet.getChar(i))));
		}
		
		letterColumn.setCellValueFactory(new PropertyValueFactory<>("letter"));
		mappedToColumn.setCellValueFactory(new PropertyValueFactory<>("mappedTo"));
		
		mappingsTableView.setItems(mappings);
		
		updateMappings(alphabetTextField.getText());
	}
	
	private void updateMappings(String substitutionAlphabet) {
		for (int i = 0; i < substitutionAlphabet.length(); i++) {
			mappings.get(i).setMappedTo(Character.toString(substitutionAlphabet.charAt(i)));
		}
	}
	
	@Override
	public Key generateKey() throws CharacterNotSupportedException, InvalidKeyException {
		// Normalize entered substitution alphabet
		String substitutionAlphabet = Normalizer.normalize(alphabetTextField.getText());
		
		if (substitutionAlphabet.length() != alphabet.size()) {
			throw new InvalidKeyException("The alphabet you have entered is not "
										 + alphabet.size() + " characters long.");
		}
		
		// Update text field
		alphabetTextField.setText(substitutionAlphabet);
		
		// Update table
		updateMappings(substitutionAlphabet);
		
		// Create key
		return new SubstitutionAlphabet(substitutionAlphabet);
	}

}
