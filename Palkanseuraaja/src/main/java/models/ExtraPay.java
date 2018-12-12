package models;

import javax.persistence.*;
import java.time.LocalTime;

/**
 *
 * Palkkalisä luokka. Käytetään palkkalisien esittämiseen ja tallentamiseen.
 * Liittyvät aina johonkin työprofiiliin.
 *
 */
@Entity
@Table(name = "extrapay")
public class ExtraPay {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "extrapay_id")
    private int id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "extrapay")
    private double extrapay;

    @Column(name = "beginhour")
    private String beginHour;

    @Column(name = "beginminute")
    private String beginMinute;

    @Column(name = "endhour")
    private String endHour;

    @Column(name = "endminute")
    private String endMinute;

    @ManyToOne(targetEntity = WorkProfile.class)
    @JoinColumn(name = "workprofile_id")
    private WorkProfile workProfile;

    @OneToOne(targetEntity = WeekDays.class)
    @JoinColumn(name = "weekdays_id")
    private WeekDays weekdays;

    @Column(name = "isDeleted")
    private boolean isDeleted;

    public ExtraPay() {
        isDeleted = false;
    }

    // Tämä lisätty sitä varten että olisi helpompi tehdä ExtraPay olioita testeissä
    public ExtraPay(LocalTime begin, LocalTime end, WeekDays weekDays) {

        setWeekdays(weekDays);

        setBeginHour("" + begin.getHour());
        setBeginMinute("" + begin.getMinute());

        setEndHour("" + end.getHour());
        setEndMinute("" + end.getMinute());

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getExtraPay() {
        return extrapay;
    }

    public void setExtraPay(double extrapay) {
        this.extrapay = extrapay;
    }

    public String getBeginHour() {
        return beginHour;
    }

    public void setBeginHour(String startHour) {
        this.beginHour = startHour;
    }

    public String getBeginMinute() {
        return beginMinute;
    }

    public void setBeginMinute(String startMinute) {
        this.beginMinute = startMinute;
    }

    public String getEndHour() {
        return endHour;
    }

    public void setEndHour(String endHour) {
        this.endHour = endHour;
    }

    public String getEndMinute() {
        return endMinute;
    }

    public void setEndMinute(String endMinute) {
        this.endMinute = endMinute;
    }

    public WorkProfile getWorkProfile() {
        return workProfile;
    }

    public void setWorkProfile(WorkProfile workProfile) {
        this.workProfile = workProfile;
    }

    public WeekDays getWeekdays() {
        return weekdays;
    }

    public void setWeekdays(WeekDays weekdays) {
        this.weekdays = weekdays;
    }

    public double getExtrapay() {
        return extrapay;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    @Override
    public String toString() {
        return getName();
    }
}
