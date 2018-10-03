package dataAccessObjects;

import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.List;

import org.hibernate.criterion.Restrictions;

import javafx.scene.control.Alert;
import models.CurrentUser;
import models.EventModel;
import models.Extrapay;
import models.PasswordHashing;
import models.User;
import models.WorkProfile;
import org.hibernate.query.Query;

public class UserDAO extends DataAccessObject {

    private CurrentUser currentUser;
    private Alert alert;

    public UserDAO() {

        super();

    }

    public boolean login(String username, String password) throws NoSuchAlgorithmException, NoSuchProviderException {

        openCurrentSession();
        Query q = session.createQuery("FROM User WHERE username='" + username + "'");
        User user = (User) q.uniqueResult();

        closeCurrentSession();

        if (user == null || !(user.getPassword().equals(new PasswordHashing().get_SHA_256_SecurePassword(password, user.getSalt().getBytes())))) {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Kirjautuminen epäonnistui.");
            alert.setHeaderText("Tarkista käyttäjätunnus ja salasana");

            return false;
        }

        currentUser = new CurrentUser(user);

        alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Kirjautuminen onnistui!");
        alert.setHeaderText("Tervetuloa!");

        return true;

    }
    //Metodi palauttaa truen, jos käyttäjän lisäys onnistuu.

    public boolean save(User user) {
        //Katsotaan löytyykö tietokannasta käyttäjää
        if (!checkForDuplicateUser(user)) {
            // Luodaan uusi alert, koska käyttäjätunnus on varattu
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Virhe!");
            alert.setHeaderText("Käyttäjätunnus on varattu!");

            return false;
        }

        openCurrentSessionWithTransaction().save(user);
        closeCurrentSessionWithTransaction();

        alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Rekisteröinti");
        alert.setHeaderText("Uuden käyttäjän luominen onnistui!");

        return true;
    }

    public boolean save(EventModel event) {

        openCurrentSessionWithTransaction().save(event);
        closeCurrentSessionWithTransaction();

        alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Onnistui!");
        alert.setHeaderText("Tapahtuma lisätty");

        return true;
    }

    public boolean save(Extrapay event) {

        openCurrentSessionWithTransaction().save(event);
        closeCurrentSessionWithTransaction();

        alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Onnistui!");
        alert.setHeaderText("Tapahtuma lisätty");

        return true;
    }

    // palauttaa falsen jos käyttäjänimi löytyy tietokannasta
    public boolean checkForDuplicateUser(User user) {

        List users = openCurrentSession().createCriteria(User.class)
                .add(Restrictions.eq("username", user.getUsername())).list();
        closeCurrentSession();

        //Jos käyttäjiä ei löytynyt
        if (users.isEmpty()) {
            return true;
        }
        return false;
    }

    public List<WorkProfile> getUsersWorkProfiles() {

        openCurrentSession();
        Query q = session.createQuery("FROM WorkProfile WHERE user_id='" + CurrentUser.getUser().getId() + "'");

        List<WorkProfile> profiles = q.list();

        closeCurrentSession();

        return profiles;

    }

    public Alert getAlert() {
        return this.alert;
    }

}
