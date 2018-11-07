package dataAccessObjects;

import application.GoogleCalendar;
import com.google.api.client.auth.oauth2.Credential;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.hibernate.criterion.Restrictions;
import javafx.scene.control.Alert;
import models.CurrentUser;
import models.CurrentWorkProfile;
import models.EventModel;
import models.ExtraPay;
import models.PasswordHashing;
import models.User;
import models.WeekDays;
import models.WorkProfile;
import org.hibernate.query.Query;

public class UserDAO extends DataAccessObject {

	
    private static CurrentUser currentUser;
    private static Alert alert;

    public UserDAO() {

        super();

    }

    public static boolean login(String username, String password) throws NoSuchAlgorithmException, NoSuchProviderException {

        openCurrentSession();
        Query q = getCurrentSession().createQuery("FROM User WHERE username='" + username + "'");
        currentUser = new CurrentUser((User) q.uniqueResult());

        closeCurrentSession();

        if (currentUser.getUser() == null || !(currentUser.getUser().getPassword().equals(new PasswordHashing().get_SHA_256_SecurePassword(password, currentUser.getUser().getSalt().getBytes())))) {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Kirjautuminen epäonnistui.");
            alert.setHeaderText("Tarkista käyttäjätunnus ja salasana");

            return false;
        }

        currentUser.setWorkProfiles(getUsersWorkProfilesFromDatabase());

        alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Kirjautuminen onnistui!");
        alert.setHeaderText("Tervetuloa!");

        return true;

    }
    //Metodi palauttaa truen, jos käyttäjän lisäys onnistuu.

    public static boolean save(User user) {
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

    public static  boolean save(EventModel event) {


        openCurrentSessionWithTransaction().saveOrUpdate(event);
        closeCurrentSessionWithTransaction();

        alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Onnistui!");
        alert.setHeaderText("Tapahtuma lisätty");

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

    // palauttaa falsen jos käyttäjänimi löytyy tietokannasta
    public static boolean checkForDuplicateUser(User user) {

        List users = openCurrentSession().createCriteria(User.class)
                .add(Restrictions.eq("username", user.getUsername())).list();
        closeCurrentSession();

        //Jos käyttäjiä ei löytynyt
        if (users.isEmpty()) {
            return true;
        }
        return false;
    }

    public static List<EventModel> getEvents() {

        openCurrentSession();
        Query q = getCurrentSession().createQuery("FROM WorkProfile WHERE user_id='" + CurrentUser.getUser().getId() + "'");

        List<WorkProfile> profiles = q.list();

        Iterator itr = profiles.iterator();
        List<EventModel> events = new ArrayList<EventModel>();
        while (itr.hasNext()) {
            WorkProfile tmp = (WorkProfile) itr.next();
            List<EventModel> eventlist = (List<EventModel>) tmp.getEvents();
            Iterator itr1 = eventlist.iterator();
            while (itr1.hasNext()) {
                EventModel tmp2 = (EventModel) itr1.next();
                events.add(tmp2);
            }

        }

        closeCurrentSession();

        return events;
    }

    public static List<WorkProfile> getUsersWorkProfilesFromDatabase() {

        openCurrentSession();
        Query q = getCurrentSession().createQuery("FROM WorkProfile WHERE user_id='" + CurrentUser.getUser().getId() + "'");

        List<WorkProfile> profiles = q.list();

        closeCurrentSession();

        return profiles;

    }

    public static List<ExtraPay> getProfilesExtraPays() {
        openCurrentSession();
        Query q = getCurrentSession().createQuery("FROM ExtraPay WHERE workprofile_id='" + CurrentWorkProfile.getWorkProfile().getId() + "'");

        List<ExtraPay> profiles = q.list();

        closeCurrentSession();

        return profiles;
    }

    public static Alert getAlert() {
       return alert;
    }

}
