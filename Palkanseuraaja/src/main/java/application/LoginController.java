package application;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.stage.Stage;

public class LoginController {

    private ViewChanger viewChanger;
    private LoginView loginView;
    private LoginModel loginModel;

    public LoginController(LoginView loginView, LoginModel loginModel, ViewChanger viewChanger) {

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

                //Yritetään sisäänkirjaumista ottamalla yhteys tietokantaan
//                if (dao.login(loginView.getUsernameField(), new PasswordHashing(loginView.getPasswordField()).get_SHA_256_SecurePassword())) {
                    //Kirjautuminen onnistui ja luodaan ilmoitus siitä. 
//                    loginView.showAlert(dao.getAlert());

                    //Luodaan sessionUser, joka pitää tallessaan uuden istunnon käyttäjätiedot.

                    /*Tähän sitten luodaan ohjaus itse ohjelmaan sekä passataan jollain tavalla 
					 * sessionUser eteenpäin sinne
                     */
                    viewChanger.switchStage("kalenteriView", viewChanger);
//                } else {
//                    //Kirjautuminen epäonnistui. Ilmoitetaan siitä
//                    loginView.showAlert(dao.getAlert());
//                }
//            } else {
//                //Jompikumpi tai molemmat kentät ovat tyhjät. Ilmoitetaan siitä
//                loginView.showAlert(loginModel.getAlert());
            }
        }

    }

}
