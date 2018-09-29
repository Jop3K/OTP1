
package models;

import java.sql.Timestamp;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

//@Entity
@Table(name = "tyotapahtumat")

public class WorkEvent {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    
    @ManyToOne(cascade = CascadeType.ALL, targetEntity = User.class)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    
    @OneToMany(cascade = CascadeType.ALL, targetEntity = WorkProfile.class)
    @JoinColumn(name = "tyoprofiili_id")
    private WorkProfile tyoprofiili;
    
    @ManyToOne(cascade = CascadeType.ALL, targetEntity = Palkka.class)
    @JoinColumn(name = "palkka_id", nullable = false)
    private Palkka palkka;
    
    private Timestamp timestampStart; // not sure about timestamp, must check
    
    private Timestamp timestampEnd;   // not sure about timestamp, must check

    public WorkEvent() {
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

    public WorkProfile getTyoprofiili() {
        return tyoprofiili;
    }

    public void setTyoprofiili(WorkProfile tyoprofiili) {
        this.tyoprofiili = tyoprofiili;
    }

    public Palkka getPalkka() {
        return palkka;
    }

    public void setPalkka(Palkka palkka) {
        this.palkka = palkka;
    }

    public Timestamp getTimestampStart() {
        return timestampStart;
    }

    public void setTimestampStart(Timestamp timestampStart) {
        this.timestampStart = timestampStart;
    }

    public Timestamp getTimestampEnd() {
        return timestampEnd;
    }

    public void setTimestampEnd(Timestamp timestampEnd) {
        this.timestampEnd = timestampEnd;
    }
    
    

}
