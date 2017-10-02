package utils;

import exceptions.CharacterNotSupportedException;
import gui.Main;

public final class Normalizer {
	
	private static Alphabet alphabet = Main.getAlphabetInUse();
	
	// Prevents instantiation
	private Normalizer() { }

	public static String normalize(String text) throws CharacterNotSupportedException {
		StringBuilder result = new StringBuilder();
		
		for (char c : text.toCharArray()) {
			if (isASCII(c)) {
				if (isASCIILetter(c)) {
					result.append(alphabet.changeCase(c));
				}
			} else {
				throw new CharacterNotSupportedException(c);
			}
		}
		
		return result.toString();
	}
	
	private static boolean isASCII(char ch) {
		return ch >= 0 && ch <= 127;
	}
	
	private static boolean isASCIILetter(char ch) {
		return (ch >= 65 && ch <= 90) || (ch >= 97 && ch <= 122);
	}
	
}
