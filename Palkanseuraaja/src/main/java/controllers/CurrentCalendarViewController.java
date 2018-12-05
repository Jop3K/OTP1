package controllers;

import controllers.CalendarViewController;

public class CurrentCalendarViewController extends CalendarViewController {

    private static CalendarViewController calendarViewController;

    public CurrentCalendarViewController(CalendarViewController c) {
        CurrentCalendarViewController.setCalendarViewController(c);
    }

    public static CalendarViewController getCalendarViewController() {
        return calendarViewController;
    }

    public static void setCalendarViewController(CalendarViewController c) {
        CurrentCalendarViewController.calendarViewController = c;
    }

}
