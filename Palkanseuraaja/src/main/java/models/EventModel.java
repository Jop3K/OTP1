package models;

import java.time.LocalDate;
import java.util.Date;
import javax.persistence.*;
import com.google.api.client.util.DateTime;
import java.time.format.DateTimeFormatter;

@Entity
@Table(name = "events")
public class EventModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "event_id")
    private int id;

    @ManyToOne(targetEntity = WorkProfile.class)
    @JoinColumn(name = "workprofile_id")
    private WorkProfile workProfile;

    @Column(name = "beginTime")
    private Date beginTime;

    @Column(name = "endTime")
    private Date endTime;
    
    @Column(name = "beginDateTime")
    private String beginDateTime;
    
    @Column(name = "endDateTime")
    private String endDateTime;

    @Column
    private LocalDate beginDay;
    @Column
    private String beginHour;
    @Column
    private String beginMinute;
    @Column
    private LocalDate endDay;
    @Column
    private String endHour;
    @Column
    private String endMinute;

    public EventModel() {
    }

    public WorkProfile getWorkProfile() {
        return workProfile;
    }

    public void setWorkProfile(WorkProfile workProfile) {
        this.workProfile = workProfile;
    }

    public Date getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public LocalDate getBeginDay() {
        return beginDay;
    }

    public void setBeginDay(LocalDate beginDay) {
        beginTime = new Date(beginDay.getYear() - 1900, beginDay.getMonthValue() - 1, beginDay.getDayOfMonth());
    }

    public void setBeginDateTime(LocalDate beginDay) {
        beginDateTime = (beginDay.format(DateTimeFormatter.ISO_DATE) + "T" + beginHour + ":" + beginMinute + ":" + "00");
    }

    public void setEndDateTime(LocalDate endDay) {
        endDateTime = (endDay.format(DateTimeFormatter.ISO_DATE) + "T" + endHour + ":" + endMinute + ":" + "00");
    }
    
    

    public String getBeginHour() {
        return beginHour;
    }

    public void setBeginHour(String beginHour) {
        if (beginHour.length() < 2) {
            this.beginHour = "0" + beginHour;
        } else {
            this.beginHour = beginHour;
        }
    }

    public String getBeginMinute() {
        return beginMinute;
    }

    public void setBeginMinute(String beginMinute) {
        if (beginMinute.length() < 2) {
            this.beginMinute = "0" + beginMinute;
        } else {
            this.beginMinute = beginMinute;
        }
    }

    public LocalDate getEndDay() {
        return endDay;
    }

    public void setEndDay(LocalDate endDay) {
        endTime = new Date(endDay.getYear() - 1900, endDay.getMonthValue() - 1, endDay.getDayOfMonth());
        
    }

    public String getEndHour() {
        return endHour;
    }

    public void setEndHour(String endHour) {
        if (endHour.length() < 2) {
            this.endHour = "0" + endHour;
        } else {
            this.endHour = endHour;
        }
    }

    public String getEndMinute() {
        return endMinute;
    }

    public void setEndMinute(String endMinute) {
        if (endMinute.length() < 2) {
            this.endMinute = "0" + endMinute;
        } else {
            this.endMinute = endMinute;
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBeginDateTime() {
        return beginDateTime;
    }

    public String getEndDateTime() {
        return endDateTime;
    }
    
}
