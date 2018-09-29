
package models;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
    
    @ManyToOne(cascade = CascadeType.ALL, targetEntity = WorkProfile.class)
    @JoinColumn(name = "tyoprofiili_id")
    private WorkProfile tyoprofiili;
    
    @Column(name = "startTime", nullable = false)
    private DateTime dateTimeStart; // not sure about timestamp, must check
    
    @Column(name = "endTime", nullable = false)
    private DateTime dateTimeEnd;   // not sure about timestamp, must check

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

    public DateTime getDateTimeStart() {
        return dateTimeStart;
    }

    public void setDateTimeStart(DateTime dateTimeStart) {
        this.dateTimeStart = dateTimeStart;
    }

    public DateTime getDateTimeEnd() {
        return dateTimeEnd;
    }

    public void setDateTimeEnd(DateTime dateTimeEnd) {
        this.dateTimeEnd = dateTimeEnd;
    }
    
    
}
