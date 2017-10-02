package ciphers;

import keys.Key;
import keys.Shift;

public class CaesarCipher extends Cipher {

	@Override
	public String encrypt(String message, Key key) {
		int shift = (Integer) key.getValue();
		
		StringBuilder encrypted = new StringBuilder();
		
		for (char c : message.toCharArray()) {
			int index = alphabet.getIndex(c);

			encrypted.append(alphabet.getChar(index + shift));
		}
		
		return encrypted.toString();
	}

	@Override
	public String decrypt(String message, Key key) {
		int shift = (Integer) key.getValue();

		return encrypt(message, new Shift(alphabet.size() - shift));
	}

	public String toString() {
		return "Caesar";
	}
	
}
