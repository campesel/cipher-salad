package utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public final class AppProperties {
	
	private static Properties properties;
	
	// Prevents instantiation
	private AppProperties() { }
	
	public static void loadProperties(String path) throws IOException {
		properties = new Properties();
		InputStream input = null;
		
		try {
			input = new FileInputStream(path);
			
			properties.load(input);
		} finally {
			if (input != null) {
				input.close();
			}
		}
	}
	
	public static String getProperty(String key) {
		return properties.getProperty(key);
	}

}
