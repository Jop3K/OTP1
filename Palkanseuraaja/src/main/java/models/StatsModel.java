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
	private EventObservableDataList data;
	private List<SalaryPerMonthModel> IncomesByMonths;
	private ObservableList<XYChart.Series<String, Double>> IncomesByMonthsBarChartData;

	public StatsModel() {
		IncomesByMonths = Calculation.calcPayForEveryMonthInYear(data);
		IncomesByMonthsBarChartData = FXCollections.observableArrayList();
		
		}

	public BarChart setUpIncomesByMonthsBarChart(BarChart incomesByMonthsBarChart) {
	
        setUpIncomesByMonthsBarChartData();
		incomesByMonthsBarChart.setData(IncomesByMonthsBarChartData);
		
		return incomesByMonthsBarChart;
		
		}
	
	public void updateAllData(){
		
				IncomesByMonths = Calculation.calcPayForEveryMonthInYear(data);
				updateIncomesByMonthsBarChartData();
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
