package models;

import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.Year;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertEquals;

public class CalculationTest {

    private WorkProfile profile;

    @Before
    public void setUp() {

        profile = new WorkProfile();
        profile.setPay(10);

    }

    private EventModel createTestEvent() {

        // Kymmenen tunnin eventti
        LocalDateTime beginDate = LocalDateTime.now();
        LocalDateTime endDate = beginDate.plusHours(10);

        return new EventModel(beginDate, endDate, profile);

    }

    private WeekDays createWeekDays() {
        WeekDays weekDays = new WeekDays();

        weekDays.setFriday(true);
        weekDays.setMonday(true);
        weekDays.setSaturday(true);
        weekDays.setSunday(true);
        weekDays.setThursday(true);
        weekDays.setTuesday(true);
        weekDays.setWednesday(true);

        return weekDays;
    }

    private void addExtraPay(List<ExtraPay> extraPays) {
        LocalTime begin = LocalTime.now();
        LocalTime end = begin.plusHours(5);
        WeekDays weekDays = createWeekDays();
        ExtraPay extraPay = new ExtraPay(begin, end, weekDays);
        extraPay.setExtraPay(10);
        extraPays.add(extraPay);
    }


    @Test
    public void basicPayIsCalculatedCorrectly() {

        double calculated = Calculation.calcBasicPay(createTestEvent());

        // palkka * tunnit = 10 * 10 = 100
        double expected = 100;

        assertEquals(expected, calculated, 0.1);

    }

    @Test
    public void basicPayCalculationSetsHoursCorrectly() {

        EventModel testEvent = createTestEvent();

        Calculation.calcBasicPay(testEvent);

        assertEquals(10, testEvent.getHours(), 0.1);

    }

    @Test
    public void extraPayIsCalculatedCorrectly() {

        EventModel testEvent = createTestEvent();

        // Lisätään ExtraPay profiiliin
        List<ExtraPay> extraPays = new ArrayList<>();

        addExtraPay(extraPays);

        testEvent.getWorkProfile().setExtraPays(extraPays);

        double calculated = Calculation.calcExtraPay(testEvent);

        // extrapay * tunnit = 10 * 5 = 50
        double expected = 50;

        assertEquals(expected, calculated, 0.1);

    }

    @Test
    public void calcExtraPayWorksWhenEventDayChanges() {

        LocalDateTime beginDate = LocalDateTime.of(2018, 1, 1, 20, 30);
        LocalDateTime endDate = beginDate.plusHours(10);

        EventModel testEvent = new EventModel(beginDate, endDate, profile);

        List<ExtraPay> extraPays = new ArrayList<>();

        LocalTime begin = LocalTime.of(23, 0);
        LocalTime end = LocalTime.of(4, 0);
        WeekDays weekDays = createWeekDays();

        ExtraPay extrapay = new ExtraPay(begin, end, weekDays);
        extrapay.setExtraPay(10);

        extraPays.add(extrapay);

        testEvent.getWorkProfile().setExtraPays(extraPays);

        double calculated = Calculation.Calculate(testEvent);

        double expected = 150;

        assertEquals(expected, calculated, 0.1);

    }

    @Test
    public void calcExtraPayWorksWithMultipleOverlappingExtras() {
        LocalDateTime beginDate = LocalDateTime.of(2018, 1, 1, 20, 30);
        LocalDateTime endDate = beginDate.plusHours(10);

        EventModel testEvent = new EventModel(beginDate, endDate, profile);

        List<ExtraPay> extraPays = new ArrayList<>();

        LocalTime begin = LocalTime.of(23, 0);
        LocalTime end = LocalTime.of(4, 0);
        WeekDays weekDays = createWeekDays();

        ExtraPay extrapay = new ExtraPay(begin, end, weekDays);
        extrapay.setExtraPay(10);

        extraPays.add(extrapay);

        LocalTime begin2 = LocalTime.of(23, 30);
        LocalTime end2 = LocalTime.of(2, 30);

        ExtraPay extrapay2 = new ExtraPay(begin2, end2, weekDays);
        extrapay2.setExtraPay(10);

        extraPays.add(extrapay2);

        testEvent.getWorkProfile().setExtraPays(extraPays);

        double calculated = Calculation.Calculate(testEvent);

        double expected = 180;

        assertEquals(expected, calculated, 0.1);
    }

    @Test
    public void totalPayIsCalculatedCorrectly() {
        EventModel testEvent = createTestEvent();

        List<ExtraPay> extraPays = new ArrayList<>();

        addExtraPay(extraPays);

        testEvent.getWorkProfile().setExtraPays(extraPays);

        double calculated = Calculation.Calculate(testEvent);

        // palkka * tunnit + extrapay = 10 * 10 + 10 * 5 = 150
        double expected = 150;

        assertEquals(expected, calculated, 0.1);

    }

    @Test
    public void statisticCalcOnlyIncludesEventsFromChosenTimePeriod() {

        ArrayList<EventModel> events = new ArrayList<>();

        EventModel testEvent = createTestEvent();

        LocalDateTime start = LocalDateTime.of(2000, 1, 1, 1, 1);

        LocalDateTime end = start.plusHours(10);

        EventModel testEvent2 = new EventModel(start, end, profile);

        events.add(testEvent);
        events.add(testEvent2);

        testEvent.calcPay();
        testEvent2.calcPay();

        double calculated = Calculation.calcPayForTimePeriod(0, events);

        double expected = 100;

        assertEquals(expected, calculated, 0.1);

    }

    @Test
    public void statisticCalcSumsTotalForWeekCorrectly() {

        ArrayList<EventModel> events = new ArrayList<>();

        EventModel testEvent = createTestEvent();

        LocalDateTime start = LocalDateTime.now().plusDays(3);

        LocalDateTime end = start.plusHours(10);

        EventModel testEvent2 = new EventModel(start, end, profile);

        start = LocalDateTime.now().plusDays(7);
        end = start.plusHours(10);

        EventModel testEvent3 = new EventModel(start, end, profile);

        start = LocalDateTime.now().plusDays(10);
        end = start.plusHours(10);

        EventModel testEvent4 = new EventModel(start, end, profile);

        events.add(testEvent);
        events.add(testEvent2);
        events.add(testEvent3);
        events.add(testEvent4);

        testEvent.calcPay();
        testEvent2.calcPay();
        testEvent3.calcPay();
        testEvent4.calcPay();

        double calculated = Calculation.calcPayForTimePeriod(7, events);

        double expected = 300;

        assertEquals(expected, calculated, 0.1);

    }

    @Test
    public void amountOfEventsIsCalculatedCorrectly() {
        ArrayList<EventModel> events = new ArrayList<>();

        EventModel testEvent = createTestEvent();

        LocalDateTime start = LocalDateTime.now().plusDays(3);

        LocalDateTime end = start.plusHours(10);

        EventModel testEvent2 = new EventModel(start, end, profile);

        start = LocalDateTime.now().plusDays(7);
        end = start.plusHours(10);

        EventModel testEvent3 = new EventModel(start, end, profile);

        start = LocalDateTime.now().plusDays(10);
        end = start.plusHours(10);

        EventModel testEvent4 = new EventModel(start, end, profile);

        events.add(testEvent);
        events.add(testEvent2);
        events.add(testEvent3);
        events.add(testEvent4);

        testEvent.calcPay();
        testEvent2.calcPay();
        testEvent3.calcPay();
        testEvent4.calcPay();

        int calculated = Calculation.calcAmountOfEvents(7, events);

        int expected = 3;

        assertEquals(expected, calculated);
    }

    @Test
    public void statisticsCalcWorksForThePast() {
        ArrayList<EventModel> events = new ArrayList<>();

        LocalDateTime start = LocalDateTime.now().minusDays(3);

        LocalDateTime end = start.plusHours(10);

        EventModel testEvent2 = new EventModel(start, end, profile);

        start = LocalDateTime.now().minusDays(7);
        end = start.plusHours(10);

        EventModel testEvent3 = new EventModel(start, end, profile);

        start = LocalDateTime.now().minusDays(10);
        end = start.plusHours(10);

        EventModel testEvent4 = new EventModel(start, end, profile);

        events.add(testEvent2);
        events.add(testEvent3);
        events.add(testEvent4);

        testEvent2.setEventPay(Calculation.Calculate(testEvent2));
        testEvent3.setEventPay(Calculation.Calculate(testEvent3));
        testEvent4.setEventPay(Calculation.Calculate(testEvent4));

        double calculated = Calculation.calcPayForTimePeriod(-7, events);

        double expected = 200;

        assertEquals(expected, calculated, 0.1);

        calculated = Calculation.calcAmountOfEvents(-7, events);

        expected = 2;

        assertEquals(expected, calculated, 0.1);

    }

    @Test
    public void canCalcPayForGivenMonth() {
        ArrayList<EventModel> events = new ArrayList<>();

        LocalDateTime start = LocalDateTime.of(2018, 10, 24, 5, 5, 5);

        LocalDateTime end = start.plusHours(10);

        EventModel testEvent2 = new EventModel(start, end, profile);

        start = start.minusDays(7);
        end = start.plusHours(10);

        EventModel testEvent3 = new EventModel(start, end, profile);

        start = start.minusDays(10);
        end = start.plusHours(10);

        EventModel testEvent4 = new EventModel(start, end, profile);

        events.add(testEvent2);
        events.add(testEvent3);
        events.add(testEvent4);

        testEvent2.setEventPay(Calculation.Calculate(testEvent2));
        testEvent3.setEventPay(Calculation.Calculate(testEvent3));
        testEvent4.setEventPay(Calculation.Calculate(testEvent4));
        
        Year year = Year.of(start.getYear());
        Month month = start.getMonth();

        double calculated = Calculation.calcPayForMonth(year, month, events);

        double expected = 300;

        assertEquals(expected, calculated, 0.1);


    }

}