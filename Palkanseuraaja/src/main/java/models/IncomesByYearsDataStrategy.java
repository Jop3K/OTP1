package models;

import java.time.Year;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.input.MouseEvent;

public class IncomesByYearsDataStrategy implements BarChartData {

	private StatsModel statsModel;
	private List<SalaryPerYearModel> salaryPerYearList;
	private ObservableList<XYChart.Series<String, Double>> IncomesByYearsBarChartData;

	public IncomesByYearsDataStrategy() {
		
		salaryPerYearList = Calculation.calcPayForEveryYear();
		IncomesByYearsBarChartData = FXCollections.observableArrayList();
		
	}


	public ObservableList<XYChart.Series<String, Double>> setBarChartData() {
		XYChart.Series<String, Double> yearlyIncome = new XYChart.Series<String, Double>(getData());
		IncomesByYearsBarChartData.add(yearlyIncome);
		ObservableList<XYChart.Series<String, Double>> sortedList = IncomesByYearsBarChartData.sorted();
		
		return IncomesByYearsBarChartData;
	}
	
	public ObservableList<XYChart.Data<String,Double>> getData(){
		update();
		ObservableList<XYChart.Data<String,Double>> xyList = FXCollections.observableArrayList();	           
		
		for(SalaryPerYearModel s : salaryPerYearList) {
			
			xyList.add(new XYChart.Data(s.getYear().toString(), s.getTotalSalary()));		
		}
		
		return xyList;
	}
	
	public void addMouseClickListeners(StatsModel statsModel) {


		for (Series<String,Double> serie: (ObservableList<XYChart.Series<String, Double>>)IncomesByYearsBarChartData){
            for (XYChart.Data<String, Double> item: serie.getData()){
                item.getNode().setOnMouseClicked((MouseEvent event) -> {
                	Year year = Year.parse(item.getXValue());
                	
                    statsModel.setDataGenerator(new IncomesByMonthsDataStrategy(year));
                    statsModel.updateAllData();
                });
            }
		}
	}
	@Override
	public void update() {
		// TODO Auto-generated method stu
		salaryPerYearList = Calculation.calcPayForEveryYear();
	}


}
