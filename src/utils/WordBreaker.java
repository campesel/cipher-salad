package utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import exceptions.CharacterNotSupportedException;

public class WordBreaker {
	
	private Set<String> dictionary;
	
	private int longestWordLength;
	
	private String[] toAdd = { "a", "i" };
	private String[] toRemove = { "th" };
	
	public WordBreaker(String path) throws CharacterNotSupportedException, IOException {
		dictionary = new HashSet<>();
		longestWordLength = 0;
		
		try (BufferedReader input = new BufferedReader(new FileReader(path))) {
			String line;
			int length;
			
			while ((line = input.readLine()) != null) {
				length = line.length();

				if (length == 1)
					continue;
				
				if (length > longestWordLength)
					longestWordLength = length;
				
				dictionary.add(Normalizer.normalize(line));
			}
		}
		
		for (String word : toAdd) {
			dictionary.add(Normalizer.normalize(word));
		}
		
		for (String word : toRemove) {
			dictionary.remove(Normalizer.normalize(word));
		}
	}

	public String addSpaces(String string) {
		int stringLength = string.length();
		
		if (stringLength == 0) {
			return string;
		}
		
		int[] indices = findIndices(string);
		
		if (indices[stringLength - 1] < 0) {
			return string;
		}
		
		StringBuilder result = new StringBuilder();
		
		int currentIndex = stringLength - 1;
		int startOfCurrentWord = indices[currentIndex];
		
		while (currentIndex >= 0) {
			result.insert(0, string.charAt(currentIndex));
			
			if (currentIndex == startOfCurrentWord && currentIndex > 0) {
				result.insert(0, ' ');
				
				startOfCurrentWord = indices[currentIndex - 1];
			}
			
			currentIndex--;
		}
		
		return result.toString();
	}
	
	private int[] findIndices(String string) {
		int stringLength = string.length();
		
		int[] indices = new int[stringLength];
		Arrays.fill(indices, -1);
		
		for (int i = 0; i < stringLength; i++) {
			
			if (dictionary.contains(string.substring(0, i + 1)))
				indices[i] = 0;
			
			if (indices[i] < 0)
				continue;
			
			int lastPos = Math.min(i + longestWordLength + 1, stringLength);

			for (int j = i + 1; j < lastPos; j++) {

				if (indices[j] < 0 && dictionary.contains(string.substring(i + 1, j + 1))) {
					indices[j] = i + 1;
				}

			}
			
		}
		
		return indices;
	}

}
