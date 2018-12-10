package models;

import java.time.Year;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.input.MouseEvent;

public class IncomesByYearsDataStrategy extends AbstractDataStrategy {

    /**
     * Strategy class that generate data for BarChart in StatsView.
     *
     * @author Joonas
     */

    private List<SalaryPerYearModel> salaryPerYearList;
    
	public IncomesByYearsDataStrategy() {
		salaryPerYearList = Calculation.calcPayForEveryYear();		
	}

    public ObservableList<XYChart.Data<String, Double>> getData() {
        ObservableList<XYChart.Data<String, Double>> xyList = FXCollections.observableArrayList();

        for (SalaryPerYearModel s : salaryPerYearList) {

            xyList.add(new XYChart.Data(s.getYear().toString(), s.getTotalSalary()));
        }

        return xyList;
    }

}
