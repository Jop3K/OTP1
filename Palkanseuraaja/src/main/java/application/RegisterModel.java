package application;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.scene.control.Alert;

public class RegisterModel {

	private String fName;
	private String lName;
	private String uName;
	private String pw1;
	private String pw2;
	private String gmail;

	private Alert alert;

	public RegisterModel () {

	}
	public RegisterModel (String fName, String lName, String uName, String pw1, String pw2, String gmail) {
		this.fName = fName;
		this.lName = lName;
		this.uName = uName;
		this.pw1 = pw1;
		this.pw2 = pw2;
		this.gmail = gmail;

	}
	// Metodia käytetään tarkistamaan, että onko kaikki pakolliset kentät täytetty viewissä
	public boolean formValidation(RegisterModel tmp) {
		//Tarkistetaan, onko kaikki kentät täytetty
		if(tmp.getfName().equals("")|| tmp.getlName().equals("") ||tmp.getuName().equals("") || tmp.getPw1().equals("")||
				tmp.getPw2().equals("")) {

			 Alert fieldAlert = new Alert(Alert.AlertType.ERROR);
		        fieldAlert.setTitle("Virhe!");
		        fieldAlert.setHeaderText("Täytä pakolliset kentät!");
		        this.alert = fieldAlert;
			return false;
		}
			//Salasanan validaation
			else if (!tmp.getPw1().equals(tmp.getPw2())) {

		        Alert pwAlert = new Alert(Alert.AlertType.ERROR);
		        pwAlert.setTitle("Virhe!");
		        pwAlert.setHeaderText("Salasanat eivät täsmää!");
		        this.alert = pwAlert;
		        return false;
				}
			//Sähköpostin validaation
			else if (!tmp.getGmail().equals("")) {
				String regex = "^[\\w.+\\-]+@gmail\\.com$";
	            Pattern pattern = Pattern.compile(regex);
	            Matcher matcher = pattern.matcher(tmp.getGmail());
	             if (!matcher.matches()) {
			        Alert emailAlert = new Alert(Alert.AlertType.ERROR);
			        emailAlert.setTitle("Virhe!");
			        emailAlert.setHeaderText("Sähköposti on virheellinen tai se ei ole Gmail-osoite");
			        this.alert = emailAlert;
			        return false;
	            }
			}
		
			//Jos käyttäjänimi on alle kolme merkkiä
			if (tmp.getuName().length() < 3) {
				
		        Alert unameAlert = new Alert(Alert.AlertType.ERROR);
		        unameAlert.setTitle("Virhe!");
		        unameAlert.setHeaderText("Käyttäjänimen pitää olla vähintään kolme merkkiä!");
		        this.alert = unameAlert;
		        return false;
			}


			return true;

	}
	
	public String getfName() {
		return fName;
	}

	public void setfName(String fName) {
		this.fName = fName;
	}

	public String getlName() {
		return lName;
	}

	public void setlName(String lName) {
		this.lName = lName;
	}

	public String getuName() {
		return uName;
	}

	public void setuName(String uName) {
		this.uName = uName;
	}

	public String getPw1() {
		return pw1;
	}

	public void setPw1(String pw1) {
		this.pw1 = pw1;
	}

	public String getPw2() {
		return pw2;
	}

	public void setPw2(String pw2) {
		this.pw2 = pw2;
	}

	public String getGmail() {
		return gmail;
	}

	public void setGmail(String gmail) {
		this.gmail = gmail;
	}

	public Alert getAlert() {
		return alert;
	}
	public void setAlert(Alert alert) {
		this.alert = alert;
	}





// Metodi joka luo uuden käyttäjän ja asettaa se väliaikaiseen UserDatabaseen. Sitten kun on valmista, UserDatabase vaihtuu meidän mariaDB tietokantaan.

/*public void createUser(String username, String password1,
		String password2, String firstname, String lastname, String gmail) {

	User tmpUser = new User(username, password1, firstname, lastname, gmail);  // Luodaan user Olio jonka myöhemmin lisätään (tai ei) tietokantaan

	System.out.println(tmpUser);

    if (username.isEmpty() || password1.isEmpty() || firstname.isEmpty() || lastname.isEmpty()) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Virhe!");
        alert.setHeaderText("Täytä pakolliset kentät!");
        alert.showAndWait();
    } else if (!password1.equals(password2)) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Virhe!");
        alert.setHeaderText("Salasanat eivät täsmää!");
        alert.showAndWait();
    } else if (!database.checkForDuplicateUser(tmpUser)) { // Jos käyttäjää ei ole olemassa UserDatabasessa

        database.addUserDB(tmpUser);

        // Ilmoitus uuden käyttäjän luomisesta

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Success!");
        alert.setHeaderText("New user added");
        alert.showAndWait();

        // Vaihdetaan näkymä

        viewChanger.viewBuilder(new LoginView(database, viewChanger).getView()); // Jos kaikki ok, salasanat täsmää niin näkymä palautetaan kirjautumisnäkymään (LoginView)

    } else if (database.checkForDuplicateUser(tmpUser)) { // Jos käyttäjätunnus on jo olemassa, antaa virheen

    	Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Virhe!");
        alert.setHeaderText("Username not available");
        alert.showAndWait();

    }
    }*/

}
