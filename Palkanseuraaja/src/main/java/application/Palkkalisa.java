
package application;

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
@Table(name = "palkkalisat")


public class Palkkalisa {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    
    @Column(name = "palkkalisa")
    private double palkkalisa;
    
    @ManyToOne(cascade = CascadeType.ALL, targetEntity = User.class)
    @JoinColumn(name = "palkka_id", nullable = false)
    private Palkka palkka;

    public Palkkalisa() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getPalkkalisa() {
        return palkkalisa;
    }

    public void setPalkkalisa(double palkkalisa) {
        this.palkkalisa = palkkalisa;
    }

    public Palkka getPalkka() {
        return palkka;
    }

    public void setPalkka(Palkka palkka) {
        this.palkka = palkka;
    }
    
    
}
