package breakers;

import ciphers.CaesarCipher;
import gui.Main;
import keys.Shift;
import utils.TextStatistics;

public class CaesarBreaker extends Breaker {

	private CaesarCipher caesarCipher;
	
	private boolean loggingEnabled;
	
	public CaesarBreaker() {
		caesarCipher = new CaesarCipher();
		
		loggingEnabled = true;
	}
	
	public CaesarBreaker(boolean loggingEnabled) {
		this();
		
		this.loggingEnabled = loggingEnabled;
	}
	
	@Override
	public String recover(String message) {
		String recovered = null;
		int shift = 0;
		
		String decrypted = null;
		double minValue = Double.MAX_VALUE;
		
		int size = Main.getAlphabetInUse().size();

		for (int i = 0; i < size; i++) {
			decrypted = caesarCipher.decrypt(message, new Shift(i));
			
			double value = TextStatistics.computeChiSquaredTest(decrypted);
			
			if (value < minValue) {
				minValue = value;
				
				recovered = decrypted;
				shift = i;
			}
		}
		
		if (loggingEnabled)
			log("Best key found: " + shift);
		
		return recovered;
	}
	
	public String toString() {
		return "Caesar";
	}

}
