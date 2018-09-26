package models;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "palkat")

public class Palkka {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "palkka")
    private double tuntipalkka;

    @Column(name = "palkkalisa")
    private double palkkalisa;

    @OneToOne
    @JoinColumn(name = "profiili_id")
    private Tyoprofiili tyoprofiili;
    
    @OneToMany
    @JoinColumn(name = "palkkalisa_id")
    private Set<Palkkalisa> palkkalisat;

    public Palkka() {
        palkkalisat = new HashSet<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getTuntipalkka() {
        return tuntipalkka;
    }

    public void setTuntipalkka(double tuntipalkka) {
        this.tuntipalkka = tuntipalkka;
    }

    public double getPalkkalisa() {
        return palkkalisa;
    }

    public void setPalkkalisa(double palkkalisa) {
        this.palkkalisa = palkkalisa;
    }

    public Tyoprofiili getTyoprofiili() {
        return tyoprofiili;
    }

    public void setTyoprofiili(Tyoprofiili tyoprofiili) {
        this.tyoprofiili = tyoprofiili;
    }
    
    public void addPalkkalisa(Palkkalisa palkkalisa) {
        palkkalisat.add(palkkalisa);
    }

    public Set<Palkkalisa> getPalkkalisat() {
        return palkkalisat;
    }

    public void setPalkkalisat(Set<Palkkalisa> palkkalisat) {
        this.palkkalisat = palkkalisat;
    }

}
