package dataAccessObjects;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

import javafx.scene.control.Alert;
import models.CurrentUser;
import models.User;
import models.WorkProfile;

public class UserDAO extends DataAccessObject {


    private Alert alert;

    public UserDAO() {

        super();

    }

    public boolean login(String username, String password) {
        openCurrentSession();
        System.out.print(username);
        System.out.print(password);
       Criteria crit = getCurrentSession().createCriteria(User.class);
       //Asetetaan kriteerit, joilla haku tehdään taulusta
       crit.add(Restrictions.eq("username", username));
       crit.add(Restrictions.eq("password", password));
       List<User> user = new ArrayList<User>();
       user = crit.list();
       closeCurrentSession();
       //Jos ei löydy käyttäjää tietokannasta
       if (user.isEmpty()) {
    	   alert = new Alert(Alert.AlertType.ERROR);
           alert.setTitle("Kirjautuminen epäonnistui.");
           alert.setHeaderText("Tarkista käyttäjätunnus ja salasana");
           
    	   System.out.print(user.toString());
    	   return false;
       }
       //Luodaan käyttäjästä staattinen currentuser luokkaan.
       	CurrentUser currentUser = new CurrentUser(user.get(0));
       	System.out.print(user.get(0));
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

	@SuppressWarnings("deprecation")
	public List<WorkProfile> getUsersWorkProfiles(User user) {
		@SuppressWarnings("unchecked")
		List<WorkProfile> profiles = openCurrentSession().createCriteria(WorkProfile.class)
			    .add(Restrictions.eq("user_id", user.getId())).list();
		closeCurrentSession();
		return profiles;
	}

    public Alert getAlert() {
        return this.alert;
    }


}
