package exceptions;

@SuppressWarnings("serial")
public class InvalidKeyException extends Exception {

	public InvalidKeyException() {
		super("The key you have entered is invalid.");
	}
	
	public InvalidKeyException(String message) {
		super(message);
	}
	
}
