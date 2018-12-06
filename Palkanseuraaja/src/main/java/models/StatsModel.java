package models;

import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.Chart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Series;

import java.time.*;
import java.time.format.TextStyle;
import java.util.Locale;

public class StatsModel{
	/**
	 * Model class for StatsController. Used to setting up and creating data for BarChart in StatsView.
	 * @author Joonas
	 */
	private BarChartData dataGenerator;
	private BarChart incomesBarChart;
	public StatsModel() {
		
		dataGenerator = new IncomesByMonthsDataStrategy(Year.now());
		
		}

	public void setDataGenerator(BarChartData dataGenerator) {
		this.dataGenerator = dataGenerator;
		
	}
	/**
	 * Method for setting up IncomesBarChart
	 * @param incomesBarChart BarChart
	 * @return BarChart
	 */
	public BarChart setUpIncomesBarChart(BarChart incomesBarChart) {
		this.incomesBarChart = incomesBarChart;
		this.incomesBarChart.setData(dataGenerator.setBarChartData());
		return this.incomesBarChart;
		
		}
	
	/**
	 * Switches BarChartDatas strategy for generating data based on parameters given.
	 * @param year Year
	 * @param month Month  
	 */
	public void updateAllData(Year year, Month month){
		
		
		if (month == null && year!=null) {
			setDataGenerator(new IncomesByMonthsDataStrategy(year));		
		}
		else if (year != null && month !=null) {
			setDataGenerator(new IncomesByDaysInMonthDataStrategy(year, month));
			
		}
		else{
			setDataGenerator(new IncomesByYearsDataStrategy());
		}

		incomesBarChart.getData().clear();
		incomesBarChart.layout();
		incomesBarChart.setData(dataGenerator.setBarChartData());		
		
	}
	public BarChartData getDataGenerator() {
		return dataGenerator;
	}
	
	



	
	
	

}
