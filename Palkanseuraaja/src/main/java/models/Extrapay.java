
package models;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "extrapay")


public class Extrapay {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "extrapay_id")
    private int id;
    
    @Column(name = "name")
    private String name;
    
    @Column(name = "extrapay")
    private double extrapay;
    
    @Column(name = "starthour")
    private int startHour;
    
    @Column(name = "startminute")
    private int startMinute;
    
    @Column(name = "endhour")
    private int endHour;
    
    @Column(name = "endminute")
    private int endMinute;
    
    @ManyToOne(cascade = CascadeType.ALL, targetEntity = WorkProfile.class)
    @JoinColumn(name = "workprofile_id")
    private WorkProfile workProfile;
    

    public Extrapay() {
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

    public double getExtrapay() {
        return extrapay;
    }

    public void setExtrapay(double extrapay) {
        this.extrapay = extrapay;
    }

    public int getStartHour() {
        return startHour;
    }

    public void setStartHour(int startHour) {
        this.startHour = startHour;
    }

    public int getStartMinute() {
        return startMinute;
    }

    public void setStartMinute(int startMinute) {
        this.startMinute = startMinute;
    }

    public int getEndHour() {
        return endHour;
    }

    public void setEndHour(int endHour) {
        this.endHour = endHour;
    }

    public int getEndMinute() {
        return endMinute;
    }

    public void setEndMinute(int endMinute) {
        this.endMinute = endMinute;
    }

    public WorkProfile getWorkProfile() {
        return workProfile;
    }

    public void setWorkProfile(WorkProfile workProfile) {
        this.workProfile = workProfile;
    }

    @Override
    public String toString() {
        return name + ", startTime: " + startHour + ":" + startMinute + " , endTime: " + endHour + ":" + endMinute;
    }
    
    
}
