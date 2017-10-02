package utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import gui.Main;

public class FrequencyAnalyzer {
	
	private Alphabet alphabet;

	// Frequencies of letters
	private double[] expected;
	private Map<Character, Double> mapOfExpectedFrequencies;
	
	// Occurrences of the last text analyzed
	private int[] counts;

	public FrequencyAnalyzer(String path) throws IOException {
		alphabet = Main.getAlphabetInUse();
		int size = alphabet.size();
		
		counts = new int[size];
		
		// Load frequencies from file
		expected = new double[size];
		
		try (BufferedReader input = new BufferedReader(new FileReader(path))) {
			String line;
			
			int i = 0;
			
			while ((line = input.readLine()) != null) {
				expected[i++] = Double.parseDouble(line);
			}
		}
		
		// Initialize map
		mapOfExpectedFrequencies = new HashMap<>();
		
		for (int i = 0; i < expected.length; i++) {
			mapOfExpectedFrequencies.put(alphabet.getChar(i), expected[i]);
		}
	}
		
	public int[] countOccurrences(String text) {
		Arrays.fill(counts, 0);

		for (char c : text.toCharArray()) {
			counts[alphabet.getIndex(c)]++;
		}
		
		return counts;
	}
	
	public Map<Character, Double> getFrequencies(String text) {
		Map<Character, Double> result = new HashMap<>();
		
		int[] occurrences = countOccurrences(text);
		int length = text.length();
		
		for (int i = 0; i < occurrences.length; i++) {
			double frequency = ((double) occurrences[i]) / length;
			
			result.put(alphabet.getChar(i), frequency);
		}
		
		return result;
	}
	
	public Map<Character, Double> getExpectedFrequencies() {
		return mapOfExpectedFrequencies;
	}
	
	public double computeChiSquaredTest(String text) {
		// Count occurrences
		countOccurrences(text);
		
		int totalCount = text.length();

		// Chi-squared test
		double result = 0;
		
		for (int i = 0; i < counts.length; i++) {
			result += Math.pow(counts[i] - (totalCount * expected[i]), 2) / (totalCount * expected[i]);
		}
		
		return result;
	}
	
}
