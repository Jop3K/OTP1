package models;

import javafx.collections.ObservableList;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;

public interface BarChartData {
	
	
	public ObservableList setBarChartData();
	public void update();
	public ObservableList getData();
}
