
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

    @Override
    public String toString() {
        return name + ", startTime: " + beginHour + ":" + beginMinute + " , endTime: " + endHour + ":" + endMinute;
    }
    
    
}
