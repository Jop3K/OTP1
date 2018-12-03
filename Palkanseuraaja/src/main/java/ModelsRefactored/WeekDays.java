/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ModelsRefactored;

import java.time.DayOfWeek;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import models.ExtraPay;

/**
 *
 * @author Artur
 */
public class WeekDays implements IWeekDays {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "weekdays_id")
    private int id;

    @Column(name = "monday")
    private boolean monday;

    @Column(name = "tuesday")
    private boolean tuesday;

    @Column(name = "wednesday")
    private boolean wednesday;

    @Column(name = "thursday")
    private boolean thursday;

    @Column(name = "friday")
    private boolean friday;

    @Column(name = "saturday")
    private boolean saturday;

    @Column(name = "sunday")
    private boolean sunday;

    @OneToOne(cascade = CascadeType.ALL, targetEntity = ExtraPay.class)
    @JoinColumn(name = "extrapay_id")
    private ExtraPay extrapay;

    public WeekDays() {
    }

    @Override
    public boolean isDayOfWeek(DayOfWeek dow) {
        switch (dow) {
            case MONDAY:
                return isMonday();
            case TUESDAY:
                return isTuesday();
            case WEDNESDAY:
                return isWednesday();
            case THURSDAY:
                return isThursday();
            case FRIDAY:
                return isFriday();
            case SATURDAY:
                return isSaturday();
            case SUNDAY:
                return isSunday();
            default:
                return false;
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isMonday() {
        return monday;
    }

    public void setMonday(boolean monday) {
        this.monday = monday;
    }

    public boolean isTuesday() {
        return tuesday;
    }

    public void setTuesday(boolean tuesday) {
        this.tuesday = tuesday;
    }

    public boolean isWednesday() {
        return wednesday;
    }

    public void setWednesday(boolean wednesday) {
        this.wednesday = wednesday;
    }

    public boolean isThursday() {
        return thursday;
    }

    public void setThursday(boolean thursday) {
        this.thursday = thursday;
    }

    public boolean isFriday() {
        return friday;
    }

    public void setFriday(boolean friday) {
        this.friday = friday;
    }

    public boolean isSaturday() {
        return saturday;
    }

    public void setSaturday(boolean saturday) {
        this.saturday = saturday;
    }

    public boolean isSunday() {
        return sunday;
    }

    public void setSunday(boolean sunday) {
        this.sunday = sunday;
    }

    public ExtraPay getExtrapay() {
        return extrapay;
    }

    public void setExtrapay(ExtraPay extrapay) {
        this.extrapay = extrapay;
    }

}
