package models;

import static org.junit.Assert.*;

import java.time.LocalDateTime;
import java.time.Month;
import java.time.Year;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import de.saxsys.javafx.test.JfxRunner;
import de.saxsys.javafx.test.TestInJfxThread;
import javafx.collections.ObservableList;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Data;
@RunWith(JfxRunner.class)
public class StatsModelAndIncomesDataStrategyTests {

	Year year;
	Month month;
	YearMonth yearMonth;
	List<WorkProfile> profileList = new ArrayList();
	WorkProfile profile = new WorkProfile();
	IncomesByYearsDataStrategy yearTestObj;
	IncomesByMonthsDataStrategy monthTestObj;
	IncomesByDaysInMonthDataStrategy daysTestObj;
	private BarChart incomesBarChart;
	private StatsModel statsModel;
	@Before

	@TestInJfxThread
	public void setUp() throws Exception {
		year = Year.parse("2018");
		month = Month.DECEMBER;
		yearMonth = YearMonth.of(year.getValue(), month);
		profile.setPay(10);
		profile.setEvents(createTestEvents());
		profileList.add(profile);
		CurrentUserRefactored.INSTANCE.setWorkProfiles(profileList);
		EventObservableDataList.getInstance();
		daysTestObj = new IncomesByDaysInMonthDataStrategy(year,month);
		monthTestObj = new IncomesByMonthsDataStrategy(year);
		yearTestObj  = new IncomesByYearsDataStrategy();
		CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();
		incomesBarChart = new BarChart(xAxis, yAxis);
		statsModel = new StatsModel();
		statsModel.setUpIncomesBarChart(incomesBarChart);



	}

	private List createTestEvents(){
		List testEvents = new ArrayList();
		int year = this.year.getValue();
		int month = this.month.getValue();
		int dayOfMonth = 11;
		int hour = 9;
		int minute = 0;
        LocalDateTime beginDate = LocalDateTime.of(year, month, dayOfMonth, hour, minute);
        LocalDateTime endDate = beginDate.plusHours(10);

        EventModel e1 = new EventModel(beginDate, endDate, profile);
        e1.setId(0);
        e1.calcPay();
        dayOfMonth = 12;
        beginDate = LocalDateTime.of(year, month, dayOfMonth, hour, minute);
        endDate = beginDate.plusHours(10);
        EventModel e2 = new EventModel(beginDate, endDate, profile);
        e2.setId(1);
        e2.calcPay();

        testEvents.add(e1);
        testEvents.add(e2);

        return testEvents;

	}

	@Test
	public void testDaysInMonthsStrategy(){
		ObservableList<XYChart.Data<String,Double>> xyList = daysTestObj.getData();

		Data d1 = xyList.get(10); // known fixed values(dayOfMonth) from createTestEvents
		Data d2 = xyList.get(11); // known fixed values(dayOfMonth) from createTestEvents

		assertEquals(xyList.size(), 31); //There is as many entrys as in days in december
		assertEquals(d1.getXValue(), "11"); //checks if event is in right place in x-axis
		assertEquals(d1.getYValue(), 100.0);
		assertEquals(d2.getXValue(), "12");//checks if event is in right place in x-axis
		assertEquals(d2.getYValue(), 100.0);
	}

	@Test
	public void testIncomesByMonthsStrategy() {
		ObservableList<XYChart.Data<String,Double>> xyList = monthTestObj.getData();

		assertEquals(xyList.size(), 12); //Checks that there are as many entrys as in months in year
		Data d1 = xyList.get(month.getValue()-1);
		double overallIncomesFromDecember = 200.0;
		assertEquals(d1.getXValue(), month.toString());
		assertEquals(d1.getYValue(), overallIncomesFromDecember);

	}

	@Test
	public void testIncomesByYearsStrategy() {
		ObservableList<XYChart.Data<String,Double>> xyList = yearTestObj.getData();

		Data d1 = xyList.get(0);
		double Income = 200.0;
		assertEquals(xyList.size(), 1);
		assertEquals(Income, d1.getYValue());
		assertEquals(year.toString(), d1.getXValue());
	}

	@Test
	public void testUpdateAllDataWithNulls(){
		statsModel.updateAllData(null, null);

		assertEquals(yearTestObj.getClass(), statsModel.getDataGenerator().getClass());
	}

	@Test
	public void testUpdateAllDataWithMonthisNull(){
		statsModel.updateAllData(year, null);
		assertEquals(monthTestObj.getClass(), statsModel.getDataGenerator().getClass());
	}
	@Test
	public void testUpdateAllDataWithMonthIsNull(){
		statsModel.updateAllData(year, month);
		assertEquals(daysTestObj.getClass(), statsModel.getDataGenerator().getClass());
	}


}
