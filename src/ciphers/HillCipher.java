package ciphers;

import gui.Main;
import keys.Key;
import keys.KeyMatrix;
import math.SquareMatrix2;
import math.Vector2;

public class HillCipher extends Cipher {

	@Override
	public String encrypt(String message, Key key) {
		SquareMatrix2 keyMatrix = (SquareMatrix2) key.getValue();
		
		StringBuilder encrypted = new StringBuilder();
		
		// Get the order of the key
		int order = keyMatrix.getOrder();

		// Add padding
		String padded = pad(message, order);
		
		// Reuse the same vector
		Vector2 vector = new Vector2();

		for (int i = 0; i < padded.length(); i += order) {
			vector.a = alphabet.getIndex(padded.charAt(i));
			vector.b = alphabet.getIndex(padded.charAt(i + 1));
			
			Vector2 result = keyMatrix.times(vector);
			
			encrypted.append(alphabet.getChar(result.a));
			encrypted.append(alphabet.getChar(result.b));
		}

		return encrypted.toString();
	}

	@Override
	public String decrypt(String message, Key key) {
		SquareMatrix2 keyMatrix = (SquareMatrix2) key.getValue();
		
		Key decryptionKey = new KeyMatrix(keyMatrix.invert());
		
		return encrypt(message, decryptionKey);
	}
	
	private String pad(String message, int blockSize) {
		StringBuilder result = new StringBuilder(message);
		
		char padding = Main.getAlphabetInUse().getChar(0);
		int paddingSize = message.length() % blockSize;
		
		for (int i = 0; i < paddingSize; i++) {
			result.append(padding);
		}
		
		return result.toString();
	}
	
	public String toString() {
		return "Hill";
	}

}
