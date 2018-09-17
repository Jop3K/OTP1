package application;

import javafx.scene.control.Alert;

public class LoginModel {

	private Alert alert;

	public LoginModel() {

	}

   public boolean loginFieldValidation(String username, String password) {
	   // Tarkistaa, että Stringit eivät ole tyhjiä
		if (username.equals("") || password.equals("")) {
			 Alert fieldAlert = new Alert(Alert.AlertType.ERROR);
		        fieldAlert.setTitle("Virhe!");
		        fieldAlert.setHeaderText("Syötä käyttäjätunnus ja salasana!");
		        this.alert = fieldAlert;
			return false;
		}
		else {
			return true;
		}
   }

   public Alert getAlert() {
	   return this.alert;
   }

}
