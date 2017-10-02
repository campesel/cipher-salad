package gui.controllers;

import javafx.beans.property.SimpleStringProperty;

public class Mapping {

	private SimpleStringProperty letter;
	private SimpleStringProperty mappedTo;
	
	public Mapping() {
		letter = new SimpleStringProperty();
		mappedTo = new SimpleStringProperty();
	}
	
	public Mapping(String letter) {
		this();
		
		setLetter(letter);
	}

	public String getLetter() {
		return letter.get();
	}

	public void setLetter(String letter) {
		this.letter.set(letter);
	}
	
	public SimpleStringProperty letterProperty() {
		return letter;
	}

	public String getMappedTo() {
		return mappedTo.get();
	}

	public void setMappedTo(String mappedTo) {
		this.mappedTo.set(mappedTo);
	}

	public SimpleStringProperty mappedToProperty() {
		return mappedTo;
	}
	
}
