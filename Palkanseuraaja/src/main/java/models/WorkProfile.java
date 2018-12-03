package models;

import java.util.ArrayList;
import java.util.List;
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
    private List<ExtraPay> extrapays = new ArrayList<>();

    @OneToMany(mappedBy = "workProfile")
    private List<EventModel> events = new ArrayList<>();

    @Column(name = "isDeleted", nullable = true)
    private boolean isDeleted = false;

    public WorkProfile() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getPay() {
        return pay;
    }

    public void setPay(double pay) {
        this.pay = pay;
    }

    public void calculateEventPays() {
        List<EventModel> events = EventObservableDataList.getInstance();

        for (EventModel event : events) {
            event.calcPay();
        }
    }

    public String getName() {
        return name;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ExtraPay> getExtraPays() {
        return extrapays;
    }

    public void setExtraPays(List<ExtraPay> extrapays) {
        this.extrapays = extrapays;
    }

    public List<EventModel> getEvents() {
        return events;
    }

    public void setEvents(List<EventModel> events) {
        this.events = events;
    }

    public List<ExtraPay> getExtrapays() {
        return extrapays;
    }

    public void setExtrapays(List<ExtraPay> extrapays) {
        this.extrapays = extrapays;
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
