package controllers;

import java.net.URL;
import java.util.Observer;
import java.util.ResourceBundle;

import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.LineChart;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import models.EventModel;
import models.EventObservableDataList;
import models.StatsModel;

public class StatsController  implements Initializable {

	@FXML
	private ComboBox currencyDropdown;
	@FXML
	private ComboBox workshiftDropdown;
	@FXML
	private LineChart incomeChart;
	@FXML
	private TextField incomeLimit;
	@FXML
	private TextField incomeStatsField;
	@FXML
	private TextField workshiftStatsField;
	@FXML
	private BarChart<String, Double> IncomesByMonthsBarChart;

	private EventObservableDataList data;
	private StatsModel statsModel;

	public StatsController() {
		statsModel = new StatsModel();
		

	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		data.getInstance().addListener((ListChangeListener)(c -> {statsModel.updateAllData();}));
		IncomesByMonthsBarChart = statsModel.setUpIncomesByMonthsBarChart(IncomesByMonthsBarChart);
		

	}

}
