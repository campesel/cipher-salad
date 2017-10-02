package breakers;

import java.util.ArrayList;
import java.util.List;

import utils.TextStatistics;

public class VigenereBreaker extends Breaker {
	
	// Key lengths to test
	private static final int MIN_KEY_LENGTH = 2;
	private static final int MAX_KEY_LENGTH = 20;
	
	private CaesarBreaker caesarBreaker;
	
	public VigenereBreaker() {
		caesarBreaker = new CaesarBreaker(false);
	}
	
	@Override
	public String recover(String message) {
		// Guess possible key lengths
		log("Testing keys between " + MIN_KEY_LENGTH + " and " + MAX_KEY_LENGTH + " characters long");
		
		String[] columns = null;
		List<String[]> guessed = new ArrayList<>();
		
		for (int keyLength = MIN_KEY_LENGTH; keyLength <= MAX_KEY_LENGTH; keyLength++) {
			columns = splitMessage(message, keyLength);
			
			double average = 0;
			
			for (String column : columns) {
				average += TextStatistics.computeIndexOfCoincidence(column);
			}
			
			average /= columns.length;
			
			if (TextStatistics.isCloseToExpectedIC(average)) {
				log(String.format("Key length: %d%n"
								+ "Average IC: %.3f", keyLength, average));

				guessed.add(columns);
			}
		}
		
		// Test likely candidates
		List<String> candidates = new ArrayList<>();
		
		for (String[] candidate : guessed) {
			
			// Decrypt every column
			for (int i = 0; i < candidate.length; i++) {
				candidate[i] = caesarBreaker.recover(candidate[i]);
			}
			
			candidates.add(unsplitMessage(candidate));
		}
		
		return TextStatistics.findBestCandidate(candidates);
	}
	
	private String[] splitMessage(String message, int keyLength) {
		StringBuilder[] columns = new StringBuilder[keyLength];
		
		for (int i = 0; i < columns.length; i++) {
			columns[i] = new StringBuilder();
		}
		
		for (int i = 0; i < message.length(); i++) {
			columns[i % keyLength].append(message.charAt(i));
		}
		
		String[] result = new String[keyLength];
		
		for (int i = 0; i < result.length; i++) {
			result[i] = columns[i].toString();
		}
		
		return result;
	}
	
	private String unsplitMessage(String[] columns) {
		StringBuilder result = new StringBuilder();
		
		for (int i = 0; i < columns[0].length(); i++) {

			for (int j = 0; j < columns.length; j++) {

				if (i < columns[j].length())
					result.append(columns[j].charAt(i));

			}

		}
		
		return result.toString();
	}
	
	public String toString() {
		return "Vigenère";
	}
	
}
