package controllers;

import models.ViewChanger;
import dataAccessObjects.UserDAO;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.Event;
import javafx.event.EventHandler;
import models.EventModel;
import models.LoginModel;
import models.RegisterModel;
import views.LoginView;
import views.RegisterView;

public class LoginController {

    private UserDAO dao;
    private ViewChanger viewChanger;
    private LoginView loginView;
    private LoginModel loginModel;

    public LoginController(LoginView loginView, LoginModel loginModel, ViewChanger viewChanger) {
        this.dao = new UserDAO();
        this.viewChanger = viewChanger;
        this.loginModel = loginModel;
        this.loginView = loginView;
        //Lisätään listenerit loginViewin buttoneille
        loginView.addRegisterButtonEventListener(new RegisterButtonListener());
        loginView.addLoginButtonEventListener(new LoginButtonListener());
    }
    //Kuuntelee register-buttonia

    class RegisterButtonListener implements EventHandler {

        @Override
        //Ohjaa registerViewiin
        public void handle(Event event) {

            RegisterView registerView = new RegisterView();
            RegisterModel registerModel = new RegisterModel();
            RegisterController registerViewController = new RegisterController(registerView, registerModel, viewChanger);
            viewChanger.viewBuilder(registerView.getView());
            //sulkee tietokantayhteyden
        }

    }
    //Kuuntelee login-buttonia

    class LoginButtonListener implements EventHandler {

        @Override
        public void handle(Event arg0) {
            //Varmistaa, että kumpikaan kentistä ei ole tyhjä
            if (loginModel.loginFieldValidation(loginView.getUsernameField(), loginView.getPasswordField())) {
                try {
                    // new PasswordHashing(loginView.getPasswordField()).get_SHA_256_SecurePassword()
                    //Yritetään sisäänkirjaumista ottamalla yhteys tietokantaan
                    if (dao.login(loginView.getUsernameField(), loginView.getPasswordField())) {
                        //Kirjautuminen onnistui ja luodaan ilmoitus siitä.
                        //loginView.showAlert(dao.getAlert());
                        //Ohjataan ohjelmaan
                        EventModel eventModel = new EventModel();
                        CalendarViewController calendarViewController = new CalendarViewController();
                        viewChanger.switchStage("/fxml/TabsView.fxml", viewChanger);

                    } else {
                        //Kirjautuminen epäonnistui. Ilmoitetaan siitä
                        loginView.showAlert(dao.getAlert());
                    }
                } catch (NoSuchAlgorithmException ex) {
                    Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
                } catch (NoSuchProviderException ex) {
                    Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                //Jompikumpi tai molemmat kentät ovat tyhjät. Ilmoitetaan siitä
                loginView.showAlert(loginModel.getAlert());
            }

        }

    }
}
