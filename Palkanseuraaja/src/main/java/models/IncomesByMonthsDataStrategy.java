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

public class IncomesByMonthsDataStrategy extends AbstractDataStrategy {
    /**
     * Strategy class that generate data for BarChart in StatsView.
     * @author Joonas
     */

    private List<SalaryPerMonthModel> salaryPerMonthList;
    private Year year;

    public IncomesByMonthsDataStrategy(Year year) {
        this.year = year;
        salaryPerMonthList = Calculation.calcPayForEveryMonthInYear(year);


    }

    

    public ObservableList<XYChart.Data<String,Double>> getData(){
        ObservableList<XYChart.Data<String,Double>> xyList = FXCollections.observableArrayList();

        for(SalaryPerMonthModel s : salaryPerMonthList) {

            xyList.add(new XYChart.Data(s.getMonth().toString(), s.getTotalSalary()));
            System.out.println(s.getTotalSalary() + "  " + s.getMonth());
        }

        return xyList;
    }


}
