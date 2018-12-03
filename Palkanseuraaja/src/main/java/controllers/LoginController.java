package controllers;

import dataAccessObjects.UserDAO;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.Event;
import javafx.event.EventHandler;
import models.LoginModel;
import views.LoginView;

public class LoginController {

    private final LoginView loginView;
    private final LoginModel loginModel;

    /**
     * The constructor for LoginController
     *
     * @param loginView
     * @param loginModel
     */
    public LoginController(LoginView loginView, LoginModel loginModel) {
        this.loginModel = loginModel;
        this.loginView = loginView;
        //Lisätään listenerit loginViewin buttoneille
        loginView.addRegisterButtonEventListener(new RegisterButtonListener());
        loginView.addLoginButtonEventListener(new LoginButtonListener());
    }
    //Kuuntelee register-buttonia

    /**
     * The class we use for handling the register button onclick event
     *
     * @author Joni, Artur, Joonas
     *
     */
    class RegisterButtonListener implements EventHandler {

        @Override
        //Ohjaa registerViewiin
        public void handle(Event event) {
            ModelsRefactored.ViewManagerRefactored.ViewManager.INSTANCE.switchToRegisterView();
            //sulkee tietokantayhteyden
        }

    }
    //Kuuntelee login-buttonia

    /**
     * The class we use for handling the "Login" button onclick event
     *
     * @author Joni, Artur, Joonas
     *
     */
    class LoginButtonListener implements EventHandler {

        /**
         * Method for handling the login event
         */
        @Override
        public void handle(Event arg0) {
            //Varmistaa, että kumpikaan kentistä ei ole tyhjä
            if (loginModel.loginFieldValidation(loginView.getUsernameField(), loginView.getPasswordField())) {
                try {
                    // new PasswordHashing(loginView.getPasswordField()).get_SHA_256_SecurePassword()
                    //Yritetään sisäänkirjaumista ottamalla yhteys tietokantaan
                    if (UserDAO.login(loginView.getUsernameField(), loginView.getPasswordField())) {
                        //Kirjautuminen onnistui ja luodaan ilmoitus siitä.
                        //loginView.showAlert(dao.getAlert());
                        //Ohjataan ohjelmaan
                        ModelsRefactored.ViewManagerRefactored.ViewManager.INSTANCE.switchToApplicationView();
                    } else {
                        //Kirjautuminen epäonnistui. Ilmoitetaan siitä
                        loginView.showAlert(UserDAO.getAlert());
                    }
                } catch (NoSuchAlgorithmException | NoSuchProviderException ex) {
                    Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                //Jompikumpi tai molemmat kentät ovat tyhjät. Ilmoitetaan siitä
                loginView.showAlert(loginModel.getAlert());
            }

        }

    }
}
