package models;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.*;

@Entity
@Table(name = "workprofile")

public class WorkProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "workprofile_id")
    private int id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "pay")
    private double pay;

    @ManyToOne(targetEntity = User.class)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToMany(mappedBy = "workProfile")
    private Set<ExtraPay> extrapays = new HashSet<>();

    @OneToMany(mappedBy = "workProfile")
    private List<EventModel> events = new ArrayList<>();

    public WorkProfile() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public double getPay() {
        return pay;
    }

    public void setPay(double pay) {
        this.pay = pay;
    }

    public void calculateEventPays() {
        List<EventModel> events = CurrentCalendarViewController.getCalendarViewController().getData();

        for(EventModel event : events) {
            event.calcPay();
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<ExtraPay> getExtraPays() {
        return extrapays;
    }

    public void setExtraPays(Set<ExtraPay> extrapays) {
        this.extrapays = extrapays;
    }

    public List<EventModel> getEvents() {
        return events;
    }

    public void setEvents(List<EventModel> events) {
        this.events = events;
    }
    
    @Override
    public String toString(){
    	return getName();
    }

}
