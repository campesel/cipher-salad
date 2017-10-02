package gui.controllers;

import exceptions.InvalidKeyException;
import gui.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import keys.Key;
import keys.Shift;
import utils.Alphabet;

public class CaesarController implements KeyGeneratorController {
	
	private Alphabet alphabet;
	
	private ObservableList<Mapping> mappings;
	
	@FXML private ComboBox<Integer> shiftComboBox;
	
	@FXML private TableView<Mapping> mappingsTableView;
	@FXML private TableColumn<Mapping, String> letterColumn;
	@FXML private TableColumn<Mapping, String> mappedToColumn;
	
	@FXML
	public void initialize() {
		alphabet = Main.getAlphabetInUse();

		mappings = FXCollections.observableArrayList();

		int size = alphabet.size();

		for (int i = 0; i < size; i++) {
			shiftComboBox.getItems().add(new Integer(i));
			
			mappings.add(new Mapping(Character.toString(alphabet.getChar(i))));
		}
		
		letterColumn.setCellValueFactory(new PropertyValueFactory<>("letter"));
		mappedToColumn.setCellValueFactory(new PropertyValueFactory<>("mappedTo"));
		
		mappingsTableView.setItems(mappings);
	}
	
	@FXML
	private void updateMappings() {
		int shift = shiftComboBox.getValue();
		
		for (Mapping mapping : mappings) {
			int index = alphabet.getIndex(mapping.getLetter().charAt(0));

			mapping.setMappedTo(Character.toString(alphabet.getChar(index + shift)));
		}
	}
	
	@Override
	public Key generateKey() throws InvalidKeyException {
		// If no shift has been selected
		if (shiftComboBox.getValue() == null) {
			throw new InvalidKeyException("No shift has been selected.");
		}
		
		// Otherwise, generate key
		int shift = shiftComboBox.getValue();
		
		return new Shift(shift);
	}
	
}
