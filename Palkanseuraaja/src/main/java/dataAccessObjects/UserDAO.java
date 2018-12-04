package dataAccessObjects;

import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.List;

import org.hibernate.Hibernate;
import java.util.ResourceBundle;
import org.hibernate.criterion.Restrictions;
import javafx.scene.control.Alert;
import models.EventModel;
import models.ExtraPay;
import models.PasswordHashingModelRefactored;
import models.User;
import models.WeekDays;
import models.WorkProfile;
import org.hibernate.query.Query;

public class UserDAO extends DataAccessObject {

    private static Alert alert;
    private static ResourceBundle alerts;

    public UserDAO() {

        super();

        alerts = ResourceBundle.getBundle("AlertMessagesBundle");

    }

    public static boolean login(String username, String password) throws NoSuchAlgorithmException, NoSuchProviderException {

        openCurrentSession();
        Query q = getCurrentSession().createQuery("FROM User WHERE username='" + username + "'");
        models.CurrentUserRefactored.INSTANCE.setUser((User) q.uniqueResult());

        closeCurrentSession();

        if (models.CurrentUserRefactored.INSTANCE.getUser() == null
                || !(models.CurrentUserRefactored.INSTANCE.getUser().getPassword().equals(new PasswordHashingModelRefactored().get_SHA_256_SecurePassword(password, models.CurrentUserRefactored.INSTANCE.getUser().getSalt().getBytes())))) {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(alerts.getString("loginFailed"));
            alert.setHeaderText(alerts.getString("checkUsernamePassword"));

            return false;
        }

        models.CurrentUserRefactored.INSTANCE.setWorkProfiles(getUsersWorkProfilesFromDatabase());

        alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(alerts.getString("loginSuccessful"));
        alert.setHeaderText(alerts.getString("welcome"));

        return true;

    }
    //Metodi palauttaa truen, jos käyttäjän lisäys onnistuu.

    public static boolean save(User user) {
        //Katsotaan löytyykö tietokannasta käyttäjää
        if (!checkForDuplicateUser(user)) {
            // Luodaan uusi alert, koska käyttäjätunnus on varattu
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(alerts.getString("error"));
            alert.setHeaderText(alerts.getString("usernameReserved"));

            return false;
        }

        openCurrentSessionWithTransaction().save(user);
        closeCurrentSessionWithTransaction();

        alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(alerts.getString("registration"));
        alert.setHeaderText(alerts.getString("userCreated"));

        return true;
    }

    public static boolean update(User user) {

        openCurrentSessionWithTransaction().update(user);
        closeCurrentSessionWithTransaction();

        return true;
    }

    public static boolean update(EventModel event) {

        openCurrentSessionWithTransaction().update(event);
        closeCurrentSessionWithTransaction();

        return true;
    }

    public static boolean save(EventModel event) {

        openCurrentSessionWithTransaction().saveOrUpdate(event);
        closeCurrentSessionWithTransaction();

        alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(alerts.getString("success"));
        alert.setHeaderText(alerts.getString("eventAdded"));

        return true;
    }

    public static boolean save(WorkProfile profile) {
        openCurrentSessionWithTransaction().saveOrUpdate(profile);
        closeCurrentSessionWithTransaction();

        return true;
    }

    public static boolean save(ExtraPay extrapay) {

        openCurrentSessionWithTransaction().saveOrUpdate(extrapay);
        closeCurrentSessionWithTransaction();

        return true;
    }

    public static boolean save(WeekDays weekdays) {

        openCurrentSessionWithTransaction().saveOrUpdate(weekdays);
        closeCurrentSessionWithTransaction();

        return true;
    }

    public static boolean delete(EventModel event) {
        openCurrentSessionWithTransaction().delete(event);

        closeCurrentSessionWithTransaction();

        return true;
    }

    public static boolean delete(WorkProfile workProfile) {
        openCurrentSessionWithTransaction().delete(workProfile);

        closeCurrentSessionWithTransaction();

        return true;
    }

    public static boolean delete(ExtraPay extrapay) {
        openCurrentSessionWithTransaction().delete(extrapay);

        closeCurrentSessionWithTransaction();

        return true;
    }

    // palauttaa falsen jos käyttäjänimi löytyy tietokannasta
    public static boolean checkForDuplicateUser(User user) {

        List users = openCurrentSession().createCriteria(User.class)
                .add(Restrictions.eq("username", user.getUsername())).list();
        closeCurrentSession();
        //Jos käyttäjiä ei löytynyt

        return users.isEmpty();
    }

    public static List<WorkProfile> getUsersWorkProfilesFromDatabase() {

        openCurrentSession();
        Query q = getCurrentSession().createQuery("FROM WorkProfile WHERE user_id='" + models.CurrentUserRefactored.INSTANCE.getUser().getId() + "'");

        List<WorkProfile> profiles = q.list();

        for (WorkProfile profile : profiles) {
            Hibernate.initialize(profile.getEvents());
            Hibernate.initialize(profile.getExtraPays());
        }

        closeCurrentSession();

        return profiles;

    }

    public static Alert getAlert() {
        return alert;
    }

}
