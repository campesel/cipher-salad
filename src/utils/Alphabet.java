package utils;

import exceptions.CharacterNotInAlphabetException;

public abstract class Alphabet {

	protected final String alphabet;

	// Instance containing upper case letters only
	public final static Alphabet UPPERCASE = new Alphabet("ABCDEFGHIJKLMNOPQRSTUVWXYZ") {

		@Override
		public char changeCase(char ch) {
			return Character.toUpperCase(ch);
		}

	};

	// Instances of this class are created within this class only
	private Alphabet(String alphabet) {
		this.alphabet = alphabet;
	}
	
	public String getAlphabet() {
		return alphabet;
	}

	public char getChar(int index) {
		return alphabet.charAt(mod(index));
	}

	public int getIndex(char ch) {
		int index = alphabet.indexOf(ch);
		
		if (index == -1)
			throw new CharacterNotInAlphabetException(ch);
		
		return index;
	}
	
	private int mod(int index) {
		int size = alphabet.length();
		
		return (index + size) % size;
	}
	
	public int size() {
		return alphabet.length();
	}

	abstract public char changeCase(char ch);

}
