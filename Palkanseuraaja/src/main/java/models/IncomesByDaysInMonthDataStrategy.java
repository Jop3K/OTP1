package models;

import java.time.Month;
import java.time.Year;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Data;


public class IncomesByDaysInMonthDataStrategy extends AbstractDataStrategy {

    /**
     * Strategy class that generate data for BarChart in StatsView.
     *
     * @author Joonas
     */
    private List<SalaryPerDayModel> list = new ArrayList();
    private YearMonth yearMonth;

    public IncomesByDaysInMonthDataStrategy(Year year, Month month) {
        list = Calculation.calcPayForEveryDayInMonth(year, month);
        yearMonth = YearMonth.of(year.getValue(), month);
    }

 
    @Override
    public ObservableList getData() {
      
        ObservableList<XYChart.Data<String, Double>> xyList = FXCollections.observableArrayList();

        for (int day = 1; day < yearMonth.lengthOfMonth() + 1; day++) {
            Data data = new XYChart.Data<>();
            data.setXValue(Integer.toString(day));

            for (SalaryPerDayModel s : list) {

                if (Integer.parseInt(s.getDay()) == day) {
                    data.setYValue(s.getSalary());

                }
            }
            if (data.getYValue() == null) {
                data.setYValue(0); //default value
            }
            xyList.add(data);
            System.out.println(data.getXValue() + " " + data.getYValue());
        }

        return xyList;

    }
}

