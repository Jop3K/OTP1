package models;

import ModelsRefactored.IValidationModel;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class RegisterModelRefactored implements IValidationModel {

    private String fName;
    private String lName;
    private String uName;
    private String pw1;
    private String pw2;

    private PasswordHashing passwordHashing;

    private ResourceBundle alerts;
    private Alert alert;

    public RegisterModelRefactored() {
    }

    public RegisterModelRefactored(String fName, String lName, String uName, String pw1, String pw2) {
        this.fName = fName;
        this.lName = lName;
        this.uName = uName;
        this.pw1 = pw1;
        this.pw2 = pw2;

        passwordHashing = new PasswordHashing();

        alerts = ResourceBundle.getBundle("AlertMessagesBundle");
        alert = new Alert(null);

    }
    // Metodia käytetään tarkistamaan, että onko kaikki pakolliset kentät täytetty viewissä

    @Override
    public boolean formValidation() {
        //Tarkistetaan, onko kaikki kentät täytetty
        if (fName.equals("") || lName.equals("") || uName.equals("") || pw1.equals("")
                || pw2.equals("")) {
            alert.setAlertType(AlertType.ERROR);
            alert.setTitle(alerts.getString("error"));
            alert.setHeaderText(alerts.getString("fillMandatoryFields"));

            return false;
        }

        //Salasanan validaation
        if (pw1.equals(pw2)) {

            Alert pwAlert = new Alert(Alert.AlertType.ERROR);
            pwAlert.setTitle(alerts.getString("error"));
            pwAlert.setHeaderText(alerts.getString("passwordsDontMatch"));
            this.alert = pwAlert;
            return false;
        }

        if (pw1.length() < 4) {
            Alert pwAlert = new Alert(Alert.AlertType.ERROR);
            pwAlert.setTitle(alerts.getString("error"));
            pwAlert.setHeaderText(alerts.getString("passwordTooShort"));
            this.alert = pwAlert;
            return false;
        }

        //Jos käyttäjänimi on alle kolme merkkiä
        if (uName.length() < 3) {

            Alert unameAlert = new Alert(Alert.AlertType.ERROR);
            unameAlert.setTitle(alerts.getString("error"));
            unameAlert.setHeaderText(alerts.getString("usernameTooShort"));
            this.alert = unameAlert;
            return false;
        }

        //Jos nimet on alle kolme merkkiä
        if (fName.length() < 3 || lName.length() < 3) {
            Alert nameAlert = new Alert(AlertType.ERROR);
            nameAlert.setTitle(alerts.getString("error"));
            nameAlert.setHeaderText(alerts.getString("nameTooShort"));
            this.alert = nameAlert;
            return false;
        }

        Pattern p = Pattern.compile("[^a-z ]", Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(fName);
        Matcher m2 = p.matcher(lName);
        boolean fNameWrong = m.find();
        boolean lNameWrong = m2.find();

        if (fNameWrong || lNameWrong) {
            Alert nameAlert = new Alert(AlertType.ERROR);
            nameAlert.setTitle(alerts.getString("error"));
            nameAlert.setHeaderText(alerts.getString("nameIllegalChars"));
            this.alert = nameAlert;
            return false;
        }

        return true;

    }

    @Override
    public String toString() {
        return "RegisterModel [fName=" + fName + ", lName=" + lName + ", uName=" + uName + ", pw1=" + pw1 + ", pw2="
                + pw2 + ", alert=" + alert + "]";
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

    public Alert getAlert() {
        return alert;
    }

    public void setAlert(Alert alert) {
        this.alert = alert;
    }

    public User buildUser() throws NoSuchAlgorithmException, NoSuchProviderException {
        User user = new User();
        user.setUsername(uName);
        user.setFirstname(fName);
        user.setLastname(lName);
        user.setSalt(passwordHashing.generateSalt().toString());
        user.setPassword(passwordHashing.get_SHA_256_SecurePassword(pw1, user.getSalt().getBytes()));

        return user;
    }

}
