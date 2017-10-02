package gui.controllers;

import java.util.Map;

import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import utils.TextStatistics;

public class StatisticsController {

	@FXML private BarChart<String, Double> cleartextBarChart;
	@FXML private BarChart<String, Double> ciphertextBarChart;
	
	public void updateStatistics(String cleartext, String ciphertext) {
		updateCleartextBarChart(createSeries(cleartext));
		updateCiphertextBarChart(createSeries(ciphertext));
	}
	
	private XYChart.Series<String, Double> createSeries(String text) {
		Map<Character, Double> frequencies = TextStatistics.getFrequencies(text);
		
		XYChart.Series<String, Double> series = new XYChart.Series<>();
		
		for (Map.Entry<Character, Double> entry : frequencies.entrySet()) {
			String letter = entry.getKey().toString();
			Double frequency = entry.getValue() * 100;
			
			series.getData().add(new XYChart.Data<>(letter, frequency));
		}

		return series;
	}
	
	private void updateCleartextBarChart(XYChart.Series<String, Double> series) {
		cleartextBarChart.getData().clear();

		cleartextBarChart.getData().add(series);
	}
	
	private void updateCiphertextBarChart(XYChart.Series<String, Double> series) {
		ciphertextBarChart.getData().clear();

		ciphertextBarChart.getData().add(series);
	}
	
}
