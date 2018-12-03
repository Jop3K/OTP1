package models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.XYChart;

public abstract class AbstractDataStrategy implements BarChartData{

	public abstract ObservableList<XYChart.Data<String,Double>> getData();
	
	public ObservableList<XYChart.Series<String, Double>> setBarChartData() {
		ObservableList<XYChart.Series<String, Double>> IncomesBarChartData = FXCollections.observableArrayList();
		XYChart.Series<String, Double>incomes = new XYChart.Series<String, Double>(getData());
		IncomesBarChartData.add(incomes);

		return IncomesBarChartData;
	}
}
