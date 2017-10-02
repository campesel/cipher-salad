package utils;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import exceptions.CharacterNotSupportedException;

public final class TextStatistics {

	// Index of coincidence for English
	private static final double IOFC_ENG = 0.067;
	
	// Maximum distance from the expected IC
	private static final double MAX_DISTANCE_FROM_IC = 0.01;

	private static FrequencyAnalyzer frequencyAnalyzer;
	private static NgramAnalyzer ngramAnalyzer;
	
	// Prevents instantiation
	private TextStatistics() { }
	
	public static void initialize(String frequenciesPath, String ngramsPath)
		throws CharacterNotSupportedException, IOException {

		frequencyAnalyzer = new FrequencyAnalyzer(frequenciesPath);
		ngramAnalyzer = new NgramAnalyzer(ngramsPath);
	}
	
	public static Map<Character, Double> getFrequencies(String text) {
		return frequencyAnalyzer.getFrequencies(text);
	}

	public static Map<Character, Double> getExpectedFrequencies() {
		return frequencyAnalyzer.getExpectedFrequencies();
	}

	public static double computeChiSquaredTest(String text) {
		return frequencyAnalyzer.computeChiSquaredTest(text);
	}
	
	public static double computeIndexOfCoincidence(String text) {
		int[] counts = frequencyAnalyzer.countOccurrences(text);
		
		double result = 0;
		
		for (int i = 0; i < counts.length; i++) {
			result += counts[i] * (counts[i] - 1);
		}
		
		long totalCount = text.length();
		
		return result / (totalCount * (totalCount - 1));
	}
	
	public static boolean isCloseToExpectedIC(double indexOfCoincidence) {
		return indexOfCoincidence > (IOFC_ENG - MAX_DISTANCE_FROM_IC);
	}
	
	public static double computeScore(String text) {
		return ngramAnalyzer.computeScore(text);
	}
	
	public static String findBestCandidate(List<String> candidates) {
		String bestCandidate = null;
		double maxScore = Double.NEGATIVE_INFINITY;
		
		for (String candidate : candidates) {
			double score = ngramAnalyzer.computeScore(candidate);
			
			if (score > maxScore) {
				maxScore = score;
				
				bestCandidate = candidate;
			}
		}
		
		return bestCandidate;
	}

}
