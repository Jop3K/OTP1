package models;

import java.time.LocalDate;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "events")
public class EventModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "event_id")
    private int id;

    @ManyToOne(cascade = CascadeType.ALL, targetEntity = WorkProfile.class)
    @JoinColumn(name = "workprofile_id")
    private WorkProfile workProfile;

    @Column(name = "beginTime")
    private Date beginTime;

    @Column(name = "endTime")
    private Date endTime;

    @Transient
    private LocalDate beginDay;
    @Transient
    private String beginHour;
    @Transient
    private String beginMinute;
    @Transient
    private LocalDate endDay;
    @Transient
    private String endHour;
    @Transient
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
        System.out.println(beginTime);
    }

    public String getBeginHour() {
        return beginHour;
    }

    public void setBeginHour(String beginHour) {
        beginTime.setHours(Integer.parseInt(beginHour));
        System.out.println(beginTime);
        this.beginHour = beginHour;
    }

    public String getBeginMinute() {
        return beginMinute;
    }

    public void setBeginMinute(String beginMinute) {
        beginTime.setMinutes(Integer.parseInt(beginMinute));
        this.beginMinute = beginMinute;
    }

    public LocalDate getEndDay() {
        return endDay;
    }

    public void setEndDay(LocalDate endDay) {
        endTime = new Date(endDay.getYear()-1900, endDay.getMonthValue() - 1, endDay.getDayOfMonth());
    }

    public String getEndHour() {
        return endHour;
    }

    public void setEndHour(String endHour) {
        endTime.setHours(Integer.parseInt(endHour));
        this.endHour = endHour;
    }

    public String getEndMinute() {
        return endMinute;
    }

    public void setEndMinute(String endMinute) {
        endTime.setMinutes(Integer.parseInt(endMinute));
        this.endMinute = endMinute;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
    	
        this.id = id;
    }


 

}
