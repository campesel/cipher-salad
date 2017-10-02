package utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public final class FileSaver {

	// Prevents instantiation
	private FileSaver() { }
	
	public static void saveTextFile(File file, String text) throws IOException {
		try (BufferedWriter output = new BufferedWriter(new FileWriter(file))) {
			output.write(text);
		}
	}

}
