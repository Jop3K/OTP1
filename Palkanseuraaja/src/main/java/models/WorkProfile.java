package models;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

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

    @ManyToOne(cascade = CascadeType.ALL, targetEntity = User.class)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "workProfile")
    private Set<ExtraPay> extrapays = new HashSet<>();

    @OneToMany(mappedBy = "workProfile")
    private Set<EventModel> events = new HashSet<>();

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

    public String getNimi() {
        return name;
    }

    public void setNimi(String name) {
        this.name = name;
    }

    public double getPay() {
        return pay;
    }

    public void setPay(double pay) {
        this.pay = pay;
    }

    public Set<ExtraPay> getPalkkalisat() {
        return extrapays;
    }

    public void setPalkkalisat(Set<ExtraPay> extrapays) {
        this.extrapays = extrapays;
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

    public ExtraPay getYolisa() {
        if (extrapays != null) {
            for (ExtraPay e : extrapays) {
                if (e.getName().equals("Yölisä")) {
                    return e;
                }
            }
        }
        return null;
    }

    public Set<EventModel> getEvents() {
        return events;
    }

    public void setEvents(Set<EventModel> events) {
        this.events = events;
    }

}
