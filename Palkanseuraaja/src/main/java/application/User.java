package application;

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
@Table(name = "users")

public class User {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "username", nullable = false)
    private String username;
    
    @Column(name = "firstname", nullable = false)
    private String firstname;
    
    @Column(name = "lastname", nullable = false)
    private String lastname;
    
    @Column(name = "password", nullable = false)
    private String password;
    
    @Column(name = "salt", nullable = false)
    private byte[] salt;
    
    @Column(name = "gmail")
    private String gmail;
    
    @OneToMany(mappedBy = "user")
    private Set<Tyoprofiili> profiilit;

    @OneToOne
    @JoinColumn(name = "google_id")
    private GoogleAccount google;

    public User() {
        profiilit = new HashSet<>();
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return this.username;
    }

    public String getPassword() {
        return this.password;
    }

    public String getFirstname() {
        return this.firstname;
    }

    public String getLastname() {
        return this.lastname;
    }

    public String getGmail() {
        return this.gmail;
    }

    // Setterit jos etunimi, sukunimi ei pakollisia. Muuten laitetaan ne konstruktoriin.
    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public void setGmail(String gmail) {
        this.gmail = gmail;
    }

    @Override
    public String toString() {
        return "Käyttäjä: " + username + "\nEtunimi: " + firstname + "\nSukunimi: " + lastname;
    }


}
