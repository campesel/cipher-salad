package breakers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

import ciphers.SimpleSubstitutionCipher;
import gui.Main;
import keys.SubstitutionAlphabet;
import utils.Alphabet;
import utils.TextStatistics;

public class SimpleSubstitutionBreaker extends Breaker {

	private SimpleSubstitutionCipher simpleSubCipher;
	
	private Alphabet alphabet;
	
	// Hard-coded values which seem to work pretty well
	private int randomRestarts = 50;
	private int lateralMoves = 10;
	
	public SimpleSubstitutionBreaker() {
		simpleSubCipher = new SimpleSubstitutionCipher();
		
		alphabet = Main.getAlphabetInUse();
	}
	
	@Override
	public String recover(String message) {
		// Guess parent key using frequency analysis
		String currentKey = guessParentKey(message);
		
		log("Initial guess: " + currentKey);
		
		// Random-restart hill climbing
		int counter = 0;
		
		String nextKey;

		double currentScore = 0;
		double nextScore = 0;
		double adjScore = 0;
		double bestScore = 0;
		
		// Initialize best key
		String bestKey = currentKey;

		bestScore = score(message, bestKey);

		// If random restarts will not be used
		if (randomRestarts == 1)
			log("Random restarts not enabled");
	
		for (int iteration = 0; iteration < randomRestarts; iteration++) {
			
			while (counter < lateralMoves) {
				currentScore = score(message, currentKey);

				nextKey = currentKey;
				nextScore = currentScore;

				// Try every possible swap
				for (int i = 0; i < alphabet.size() - 1; i++) {

					for (int j = i + 1; j < alphabet.size(); j++) {
						String adjKey = swapTwoCharacters(currentKey, i, j);
						
						adjScore = score(message, adjKey);
						
						if (adjScore >= nextScore) {
							nextKey = adjKey;
							nextScore = adjScore;
						}
					}

				}

				// If no better key has been found
				if (nextKey.equals(currentKey)) {
					break;
				}

				if (nextScore == currentScore) {
					counter++;
				} else {
					counter = 0;
				}
				
				// Update current key
				currentKey = nextKey;
				currentScore = nextScore;
			}
		
			// If a better key has been found
			if (currentScore > bestScore) {
				log("Iteration " + iteration + ": " + currentKey);

				bestKey = currentKey;
				bestScore = currentScore;
			}
			
			// Reset counter
			counter = 0;
			
			// Generate random key
			currentKey = generateRandomKey();
		}
		
		log("Best key found: " + bestKey);
		
		return simpleSubCipher.decrypt(message, new SubstitutionAlphabet(bestKey));
	}
	
	private String guessParentKey(String message) {
		// Get frequencies
		Map<Character, Double> mapOfFrequencies = TextStatistics.getFrequencies(message);
		Map<Character, Double> mapOfExpectedFrequencies = TextStatistics.getExpectedFrequencies();
		
		// Sort lists by frequency
		List<Map.Entry<Character, Double>> listOfFrequencies =
			new ArrayList<>(mapOfFrequencies.entrySet());
		List<Map.Entry<Character, Double>> listOfExpectedFrequencies =
			new ArrayList<>(mapOfExpectedFrequencies.entrySet());
		
		Comparator<Map.Entry<Character, Double>> compareByValue = new Comparator<Map.Entry<Character, Double>>() {

			@Override
			public int compare(Entry<Character, Double> o1, Entry<Character, Double> o2) {
				return o1.getValue().compareTo(o2.getValue());
			}

		};
	
		Collections.sort(listOfFrequencies, compareByValue);
		Collections.sort(listOfExpectedFrequencies, compareByValue);
		
		// Guess key
		char[] guessedKey = new char[alphabet.size()];
		
		for (int i = 0; i < listOfFrequencies.size(); i++) {
			int index = alphabet.getIndex(listOfExpectedFrequencies.get(i).getKey());
			
			guessedKey[index] = listOfFrequencies.get(i).getKey();
		}
		
		return new String(guessedKey);
	}
	
	private double score(String message, String key) {
		String decrypted = simpleSubCipher.decrypt(message, new SubstitutionAlphabet(key));

		return TextStatistics.computeScore(decrypted);
	}
	
	private String swapTwoCharacters(String key, int i, int j) {
		char[] chars = key.toCharArray();
		
		char temp = chars[i];
		chars[i] = chars[j];
		chars[j] = temp;
		
		return new String(chars);
	}
	
	private String generateRandomKey() {
		Random random = new Random();

		char[] key = alphabet.getAlphabet().toCharArray();
		
		int n = key.length;
		int j;

		for (int i = 0; i < n - 1; i++) {
			// Choose j such that i <= j < n
			j = i + random.nextInt(n - i);
			
			// Swap key[i] and key[j]
			char temp = key[i];
			key[i] = key[j];
			key[j] = temp;
		}

		return new String(key);
	}
	
	public void enableRandomRestarts(boolean enabled) {
		if (enabled)
			randomRestarts = 50;
		else
			randomRestarts = 1;
	}
	
	public String toString() {
		return "Simple substitution";
	}

}
