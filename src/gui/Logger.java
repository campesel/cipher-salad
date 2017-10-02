package gui;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javafx.scene.control.TextArea;

public final class Logger {

	private static DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss.SSS");
	
	private static TextArea textArea;

	public static void setTextArea(TextArea textArea) {
		Logger.textArea = textArea;
	}
	
	public static void clear() {
		textArea.clear();
	}
	
	public static void log(String message) {
		String logEntry = String.format("<%s>%n%s%n%n", dateFormat.format(new Date()), message);

		textArea.appendText(logEntry);
	}

}
