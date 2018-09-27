
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
import javax.persistence.Table;

@Entity
@Table(name = "tyotapahtumat")

public class WorkEvent {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    
    @ManyToOne(cascade = CascadeType.ALL, targetEntity = User.class)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    
    
    
    @ManyToOne(cascade = CascadeType.ALL, targetEntity = Palkka.class)
    @JoinColumn(name = "palkka_id", nullable = false)
    private Palkka palkka;
    
    private Timestamp timestampStart; // not sure about timestamp, must check
    
    private Timestamp timestampEnd;   // not sure about timestamp, must check

}
