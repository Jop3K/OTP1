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

public class StatsModel{
	
	private List<SalaryPerMonthModel> IncomesByMonths;
	private ObservableList<XYChart.Series<String, Double>> IncomesByMonthsBarChartData;
	private BarChartData dataGenerator;
	private BarChart incomesByMonthsBarChart;
	public StatsModel() {

		IncomesByMonthsBarChartData = FXCollections.observableArrayList();
		dataGenerator = new IncomesByYearsDataStrategy();
		
		}

	public void setDataGenerator(BarChartData dataGenerator) {
		this.dataGenerator = dataGenerator;
		
	}

	public BarChart setUpIncomesByMonthsBarChart(BarChart incomesByMonthsBarChart) {
	this.incomesByMonthsBarChart = incomesByMonthsBarChart;
	//IncomesByMonthsBarChartData = dataGenerator.setBarChartData();
	incomesByMonthsBarChart.setData(dataGenerator.setBarChartData());
	dataGenerator.addMouseClickListeners(this);
		
		return incomesByMonthsBarChart;
		
		}
	
	public void updateAllData(){
		
		incomesByMonthsBarChart.setData(dataGenerator.setBarChartData());
	}
	
	private void setUpIncomesByMonthsBarChartData() {
	
		XYChart.Series<String, Double> monthIncome = new XYChart.Series<String, Double>(getDataForIncomesByMonthsBarChartData());
		IncomesByMonthsBarChartData.add(monthIncome);
		
	}
	
	private ObservableList<XYChart.Data<String,Double>> getDataForIncomesByMonthsBarChartData(){
		
		ObservableList<XYChart.Data<String,Double>> xyList = FXCollections.observableArrayList();	           
		
		for(SalaryPerMonthModel s : IncomesByMonths) {
			
			xyList.add(new XYChart.Data(s.getMonth().toString(), s.getTotalSalary()));		
		}
		
		return xyList;
	}
	
	private void updateIncomesByMonthsBarChartData() {
		
		for (int i = 0; i<IncomesByMonthsBarChartData.size(); i++)
			IncomesByMonthsBarChartData.get(i).setData(getDataForIncomesByMonthsBarChartData());
	}



	
	
	

}
