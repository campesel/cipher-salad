package exceptions;

@SuppressWarnings("serial")
public class CharacterNotSupportedException extends Exception {

	public CharacterNotSupportedException(char ch) {
		super("The character " + ch + " is not supported.");
	}

}