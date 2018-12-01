package models;

import java.time.Month;
import java.time.Year;
import java.time.YearMonth;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.input.MouseEvent;

public class IncomesByMonthsDataStrategy implements BarChartData {

	private StatsModel statsModel;
	private List<SalaryPerMonthModel> salaryPerMonthList;
	private ObservableList<XYChart.Series<String, Double>> IncomesByMonthsBarChartData;
	private Year year;

	public IncomesByMonthsDataStrategy(Year year) {
		this.year = year;
		salaryPerMonthList = Calculation.calcPayForEveryMonthInYear(year);
		IncomesByMonthsBarChartData = FXCollections.observableArrayList();
		
	}
	@Override
	public void addMouseClickListeners(StatsModel statsModel) {
		for (Series<String,Double> serie: (ObservableList<XYChart.Series<String, Double>>)IncomesByMonthsBarChartData){
            for (XYChart.Data<String, Double> item: serie.getData()){
                item.getNode().setOnMouseClicked((MouseEvent event) -> {
                	
                	Month month = null;
                	month = month.valueOf(item.getXValue());
                	YearMonth yearMonth = YearMonth.of(year.getValue(), month);
                   // statsModel.setDataGenerator(new IncomesByDaysInMonthDataStrategy(yearMonth));
                });
            }
		}
		
	}

	@Override
	public ObservableList<XYChart.Series<String, Double>> setBarChartData() {
		XYChart.Series<String, Double> monthIncome = new XYChart.Series<String, Double>(getData());
		IncomesByMonthsBarChartData.add(monthIncome);
		ObservableList<XYChart.Series<String, Double>> sortedList = IncomesByMonthsBarChartData.sorted();
		
		return sortedList;
	}
	public ObservableList<XYChart.Data<String,Double>> getData(){
		update();
		ObservableList<XYChart.Data<String,Double>> xyList = FXCollections.observableArrayList();	           
		
		for(SalaryPerMonthModel s : salaryPerMonthList) {
			
			xyList.add(new XYChart.Data(s.getMonth().toString(), s.getTotalSalary()));	
			System.out.println(s.getTotalSalary() + "  " + s.getMonth());
		}
		
		return xyList;
	}


	@Override
	public void update() {
		// TODO Auto-generated method stub
		salaryPerMonthList = Calculation.calcPayForEveryMonthInYear(year);
	}

}
