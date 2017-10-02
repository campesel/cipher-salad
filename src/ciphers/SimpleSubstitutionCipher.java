package ciphers;

import keys.Key;

public class SimpleSubstitutionCipher extends Cipher {

	@Override
	public String encrypt(String message, Key key) {
		String substitutionAlphabet = (String) key.getValue();
		
		StringBuilder encrypted = new StringBuilder();
		
		for (char c : message.toCharArray()) {
			int index = alphabet.getIndex(c);
			
			encrypted.append(substitutionAlphabet.charAt(index));
		}
		
		return encrypted.toString();
	}

	@Override
	public String decrypt(String message, Key key) {
		String substitutionAlphabet = (String) key.getValue();
		
		StringBuilder decrypted = new StringBuilder();
		
		for (char c : message.toCharArray()) {
			int index = substitutionAlphabet.indexOf(c);
			
			decrypted.append(alphabet.getChar(index));
		}
		
		return decrypted.toString();
	}
	
	public String toString() {
		return "Simple substitution";
	}

}
