package models;

import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;

@Entity
@Table(name = "users")

public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
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
    private String salt;

    @Column(name = "gmail")
    private String gmail;

    @OneToMany(mappedBy = "user")
    private Set<WorkProfile> profiles;
    
    @OneToMany(mappedBy = "user")
    private Set<EventModel> users;

    public User() throws NoSuchAlgorithmException, NoSuchProviderException {
        salt = new PasswordHashing().generateSalt().toString();
        profiles = new HashSet<>();
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

    public String getSalt() {
        return salt;
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

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setSalt(String salt) {
        this.salt = salt;
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

    public Set<EventModel> getUsers() {
        return users;
    }

    public void setUsers(Set<EventModel> users) {
        this.users = users;
    }

    public Set<WorkProfile> getProfiilit() {
        return profiles;
    }

    public void setProfiilit(Set<WorkProfile> profiilit) {
        this.profiles = profiilit;
    }

    public Set<WorkProfile> getProfiles() {
        return profiles;
    }

    public void setProfiles(Set<WorkProfile> profiles) {
        this.profiles = profiles;
    }

    @Override
    public String toString() {
        return "Käyttäjä: " + username + "\nEtunimi: " + firstname + "\nSukunimi: " + lastname;
    }

}
