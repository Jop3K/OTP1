package models;

import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class CalculationTest {

    WorkProfile profile;

    @Before
    public void setUp() throws Exception {

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

    private void addExtraPay(Set<ExtraPay> extraPays) {
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
        Set<ExtraPay> extraPays = new HashSet<>();

        addExtraPay(extraPays);

        testEvent.getWorkProfile().setExtraPays(extraPays);

        double calculated = Calculation.calcExtraPay(testEvent);

        // extrapay * tunnit = 10 * 5 = 50
        double expected = 50;

        assertEquals(expected, calculated, 0.1);

    }

    @Test
    public void calcExtraPayWorksWhenEventDayChanges() {
        fail("Not yet implemented");
    }

    @Test
    public void calcExtraPayWorksWithMultipleOverlappingExtras() {
        fail("Not yet implemented");
    }

    @Test
    public void totalPayIsCalculatedCorrectly() {
        EventModel testEvent = createTestEvent();

        Set<ExtraPay> extraPays = new HashSet<>();

        addExtraPay(extraPays);

        testEvent.getWorkProfile().setExtraPays(extraPays);

        double calculated = Calculation.Calculate(testEvent);

        // palkka * tunnit + extrapay = 10 * 10 + 10 * 5 = 150
        double expected = 150;

        assertEquals(expected, calculated, 0.1);

    }

    @Test
    public void totalPayForTimePeriodIsCalculatedCorrectly() {
        fail("Not yet implemented");
    }

}
