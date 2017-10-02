package ciphers;

import keys.Key;

public class VigenereCipher extends Cipher {

	@Override
	public String encrypt(String message, Key key) {
		String keyword = (String) key.getValue();

		StringBuilder encrypted = new StringBuilder();

		int m, k;

		for (int i = 0, j = 0; i < message.length(); i++) {
			m = alphabet.getIndex(message.charAt(i));
			k = alphabet.getIndex(keyword.charAt(j));

			encrypted.append(alphabet.getChar(m + k));

			j = (j + 1) % keyword.length();
		}

		return encrypted.toString();
	}

	@Override
	public String decrypt(String message, Key key) {
		String keyword = (String) key.getValue();

		StringBuilder decrypted = new StringBuilder();

		int m, k;

		for (int i = 0, j = 0; i < message.length(); i++) {
			m = alphabet.getIndex(message.charAt(i));
			k = alphabet.getIndex(keyword.charAt(j));
			
			decrypted.append(alphabet.getChar(m - k));

			j = (j + 1) % keyword.length();
		}

		return decrypted.toString();
	}

	public String toString() {
		return "Vigenère";
	}

}
