package utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public final class FileLoader {
	
	// Prevents instantiation
	private FileLoader() { }
	
	public static String readTextFile(File file) throws IOException {
		StringBuilder result = new StringBuilder();
		
		try (BufferedReader input = new BufferedReader(new FileReader(file))) {
			String line;
			
			while ((line = input.readLine()) != null) {
				result.append(String.format("%s%n", line));
			}
		}
		
		return result.toString();
	}
	
}
