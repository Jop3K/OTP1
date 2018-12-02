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
	
	private List<SalaryPerMonthModel> IncomesByMonths;
	private ObservableList<XYChart.Series<String, Double>> IncomesByMonthsBarChartData;
	private BarChartData dataGenerator;
	private BarChart incomesBarChart;
	public StatsModel() {

		IncomesByMonthsBarChartData = FXCollections.observableArrayList();
		dataGenerator = new IncomesByMonthsDataStrategy(Year.now());
		
		}

	public void setDataGenerator(BarChartData dataGenerator) {
		this.dataGenerator = dataGenerator;
		
	}

	public BarChart setUpIncomesByMonthsBarChart(BarChart incomesBarChart) {
		this.incomesBarChart = incomesBarChart;
		this.incomesBarChart.setData(dataGenerator.setBarChartData());
		return this.incomesBarChart;
		
		}
	
	public void updateAllData(Year year, Month month){
		
		System.out.print(year + " k: " + month);
		if (month == null) {
			setDataGenerator(new IncomesByMonthsDataStrategy(year));		
		}
		else if (year != null && month !=null) {
			setDataGenerator(new IncomesByDaysInMonthDataStrategy(year, month));
			
		}
		else{
			setDataGenerator(new IncomesByYearsDataStrategy());
		}
		System.out.println(1+"***");
		incomesBarChart.getData().clear();
		System.out.println(2+"**");
		incomesBarChart.layout();
		System.out.println(3+"***");
		incomesBarChart.setData(dataGenerator.setBarChartData());
		System.out.println(4+"***");
		
	}
	
	private void setUpIncomesByMonthsBarChartData() {
	
		XYChart.Series<String, Double> monthIncome = new XYChart.Series<String, Double>(getDataForIncomesByMonthsBarChartData());
		IncomesByMonthsBarChartData.add(monthIncome);
		
	}
	
	private ObservableList<XYChart.Data<String,Double>> getDataForIncomesByMonthsBarChartData(){
		
		ObservableList<XYChart.Data<String,Double>> xyList = FXCollections.observableArrayList();	           
		
		for(SalaryPerMonthModel s : IncomesByMonths) {
			
			xyList.add(new XYChart.Data(s.getMonth().getDisplayName(TextStyle.FULL, Locale.getDefault()), s.getTotalSalary()));		
		}
		
		return xyList;
	}
	
	private void updateIncomesByMonthsBarChartData() {
		
		for (int i = 0; i<IncomesByMonthsBarChartData.size(); i++)
			IncomesByMonthsBarChartData.get(i).setData(getDataForIncomesByMonthsBarChartData());
	}



	
	
	

}
