package models;

import java.util.ResourceBundle;
import javafx.scene.control.Alert;

public class LoginModel {

    private ResourceBundle alerts;
    private Alert alert;

    public LoginModel() {
        alerts = ResourceBundle.getBundle("AlertMessagesBundle");
    }

    public boolean loginFieldValidation(String username, String password) {
        // Tarkistaa, että Stringit eivät ole tyhjiä
        if (username.equals("") || password.equals("")) {
            Alert fieldAlert = new Alert(Alert.AlertType.ERROR);
            fieldAlert.setTitle(alerts.getString("error"));
            fieldAlert.setHeaderText(alerts.getString("enterUsernameAndPassword"));
            this.alert = fieldAlert;
            return false;
        } else {
            return true;
        }
    }

    public Alert getAlert() {
        return this.alert;
    }

}
