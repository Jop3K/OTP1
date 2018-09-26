package dataAccessObjects;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

import javafx.scene.control.Alert;
import models.User;

public class UserDAO extends DataAccessObject {

  
    private Alert alert;

    public UserDAO() {

        super();

    }

    public boolean login(String username, String password) {
        openCurrentSession();
        
       Criteria crit = getCurrentSession().createCriteria(User.class);
       //Asetetaan kriteerit, joilla haku tehdään taulusta
       crit.add(Restrictions.eq("username", username));
       crit.add(Restrictions.eq("password", password));
       closeCurrentSession();
       
       List user = crit.list();
       //Jos ei löydy käyttäjää tietokannasta
       if (user.isEmpty()) {
    	   alert = new Alert(Alert.AlertType.ERROR);
           alert.setTitle("Kirjautuminen epäonnistui.");
           alert.setHeaderText("Tarkista käyttäjätunnus ja salasana");
           
    	   return false;
       }
   
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
    .add(Restrictions.eq("username", user.getUsername())).list() ;
    
    //Jos käyttäjiä ei löytynyt
	if (users.isEmpty()) {
		return true;
	}
	return false;
    }
	
    public Alert getAlert() {
        return this.alert;
    }


}