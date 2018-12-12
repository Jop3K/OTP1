package models;

import javafx.collections.ObservableList;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;

/**
 * Interface for BarChartData
 * For getting data out of the BarChart object
 */
public interface BarChartData {
	
	
	public ObservableList setBarChartData();
	public ObservableList getData();
}
