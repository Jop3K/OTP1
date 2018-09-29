
package models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "palkkalisat")


public class Palkkalisa {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    
    @Column(name = "nimi")
    private String nimi;
    
    @Column(name = "palkkalisa")
    private double palkkalisa;
    
    @Column(name = "starthour")
    private int startHour;
    
    @Column(name = "startminute")
    private int startMinute;
    
    @Column(name = "endhour")
    private int endHour;
    
    @Column(name = "endminute")
    private int endMinute;
    
//    @ManyToOne(cascade = CascadeType.ALL, targetEntity = User.class)
//    @JoinColumn(name = "palkka_id")
//    private Palkka palkka;
    

    public Palkkalisa() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    public String getNimi() {
        return nimi;
    }

    public void setNimi(String nimi) {
        this.nimi = nimi;
    }

    public double getPalkkalisa() {
        return palkkalisa;
    }

    public void setPalkkalisa(double palkkalisa) {
        this.palkkalisa = palkkalisa;
    }

//    public Palkka getPalkka() {
//        return palkka;
//    }
//
//    public void setPalkka(Palkka palkka) {
//        this.palkka = palkka;
//    }

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

    @Override
    public String toString() {
        return nimi + ", startTime: " + startHour + ":" + startMinute + " , endTime: " + endHour + ":" + endMinute;
    }
    
    
}
