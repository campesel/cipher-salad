package breakers;

import ciphers.HillCipher;
import gui.Main;
import keys.KeyMatrix;
import math.SquareMatrix2;
import utils.Alphabet;
import utils.TextStatistics;

public class HillBreaker extends Breaker {

	private static final String DEFAULT_CRIB = "of the";
	
	private Alphabet alphabet;

	private HillCipher hillCipher;

	private String crib;
	
	public HillBreaker() {
		alphabet = Main.getAlphabetInUse();
		
		hillCipher = new HillCipher();
	}
	
	@Override
	public String recover(String message) {
		log("Crib: " + crib);

		String recovered = null;
		
		String decrypted = null;
		double maxScore = Double.NEGATIVE_INFINITY;
		
		String ciphertextSubstring;
		String cribSubstring;
		
		// Assume crib to be at least 5 characters long
		for (int i = 0; i <= message.length() - 5; i++) {

			if (i % 2 == 0) {
				ciphertextSubstring = message.substring(i, i + 4);
				cribSubstring = crib.substring(0, 4);
			} else {
				ciphertextSubstring = message.substring(i + 1, i + 5);
				cribSubstring = crib.substring(1, 5);
			}
			
			SquareMatrix2 cribMatrix = buildMatrix(cribSubstring);
			SquareMatrix2 ciphertextMatrix = buildMatrix(ciphertextSubstring);
			
			// If the system is indeterminate
			if (!ciphertextMatrix.isInvertible())
				continue;
			
			// Otherwise, find decryption key
			SquareMatrix2 decryptionKey = cribMatrix.times(ciphertextMatrix.invert());
			
			KeyMatrix keyMatrix = new KeyMatrix(decryptionKey);
			
			// To decrypt, encrypt using the decryption key
			decrypted = hillCipher.encrypt(message, keyMatrix);
			
			double score = TextStatistics.computeScore(decrypted);
			
			if (score > maxScore) {
				maxScore = score;
				
				recovered = decrypted;
			}

		}
		
		// Reset crib
		resetCrib();
		
		return recovered;
	}
	
	private SquareMatrix2 buildMatrix(String substring) {
		int a = alphabet.getIndex(substring.charAt(0));
		int b = alphabet.getIndex(substring.charAt(2));
		int c = alphabet.getIndex(substring.charAt(1));
		int d = alphabet.getIndex(substring.charAt(3));
	
		return new SquareMatrix2(a, b, c, d);
	}
	
	public String getDefaultCrib() {
		return DEFAULT_CRIB;
	}

	public void setCrib(String crib) {
		this.crib = crib;
	}
	
	private void resetCrib() {
		crib = null;
	}
	
	public String toString() {
		return "Hill";
	}

}
