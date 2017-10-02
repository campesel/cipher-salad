package utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import exceptions.CharacterNotSupportedException;

public class NgramAnalyzer {

	private Map<String, Double> ngrams;
	
	private int ngramLength;

	private double floor;
	
	public NgramAnalyzer(String path) throws CharacterNotSupportedException, IOException {
		// Initialize map
		ngrams = new HashMap<>();
		
		// Total occurrences of all ngrams read
		long numberOfNgrams = 0;
		
		// Read counts from file
		try (BufferedReader input = new BufferedReader(new FileReader(path))) {
			String line = null;
			
			String ngram = null;
			int count;

			while ((line = input.readLine()) != null) {
				String[] entry = line.split(" ");
				
				ngram = Normalizer.normalize(entry[0]);
				count = Integer.parseInt(entry[1]);
				
				numberOfNgrams += count;
				
				ngrams.put(ngram, (double) count);
			}
			
			// Set the length of the ngrams read
			ngramLength = ngram.length();
		}
		
		// Calculate log probability
		for (Map.Entry<String, Double> entry : ngrams.entrySet()) {
			double probability = Math.log10(entry.getValue() / numberOfNgrams);
			
			entry.setValue(probability);
		}
		
		// Set floor for unknown ngrams
		floor = Math.log10(0.01 / numberOfNgrams);
	}
	
	public double computeScore(String text) {
		double score = 0;
		
		for (int i = 0; i <= text.length() - ngramLength; i++) {
			String currentNgram = text.substring(i, i + ngramLength);
			
			if (ngrams.containsKey(currentNgram)) {
				score += ngrams.get(currentNgram);
			} else {
				score += floor;
			}
		}
		
		return score;
	}

}
