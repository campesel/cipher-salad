package exceptions;

@SuppressWarnings("serial")
public class CharacterNotInAlphabetException extends RuntimeException {

	public CharacterNotInAlphabetException(char ch) {
		super("The alphabet in use does not contain " + ch + ".");
	}
	
}
