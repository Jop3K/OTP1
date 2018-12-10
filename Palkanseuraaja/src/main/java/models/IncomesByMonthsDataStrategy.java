package models;

import java.time.Year;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.XYChart;


public class IncomesByMonthsDataStrategy extends AbstractDataStrategy {

    /**
     * Strategy class that generate data for BarChart in StatsView.
     *
     * @author Joonas
     */

    private List<SalaryPerMonthModel> salaryPerMonthList;
    private Year year;

    public IncomesByMonthsDataStrategy(Year year) {
        this.year = year;
        salaryPerMonthList = Calculation.calcPayForEveryMonthInYear(year);
    }


    public ObservableList<XYChart.Data<String, Double>> getData() {
        
        ObservableList<XYChart.Data<String, Double>> xyList = FXCollections.observableArrayList();

        for (SalaryPerMonthModel s : salaryPerMonthList) {

            xyList.add(new XYChart.Data(s.getMonth().getDisplayName(TextStyle.FULL, Locale.getDefault()), s.getTotalSalary()));
            System.out.println(s.getTotalSalary() + "  " + s.getMonth().getDisplayName(TextStyle.FULL, Locale.getDefault()));
        }

        return xyList;
    }
}

  