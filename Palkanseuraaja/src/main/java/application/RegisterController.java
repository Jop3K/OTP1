package application;

import java.security.NoSuchAlgorithmException;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;

public class RegisterController {

    private UserDAO dao;
    private RegisterView registerView;
    private RegisterModel registerModel;
    private ViewChanger viewChanger;

    public RegisterController(RegisterView registerView, RegisterModel registerModel, ViewChanger viewChanger) {

        dao = new UserDAO();
        this.registerView = registerView;
        this.registerModel = registerModel;
        this.viewChanger = viewChanger;

        this.registerView.addCreateButtonEventHandler(new createButtonListener());
        this.registerView.addBackButtonEventHandler(new backButtonListener());

    }

    class backButtonListener implements EventHandler {

        @Override
        public void handle(Event event) {
            LoginView loginView = new LoginView();
            LoginModel loginModel = new LoginModel();
            LoginController loginController = new LoginController(loginView, loginModel, viewChanger);
            viewChanger.viewBuilder(loginView.getView());
            //suljetaan tietokantayhteys
            dao.closeConnection();

        }
    }

    // Luokka kuuntelee create-buttonia ja luo uuden käyttäjän.
    class createButtonListener implements EventHandler {

        @Override
        public void handle(Event arg0) {
            /*
			 * 1. Tarkistetaan, että rekisteröinti-formi on täytetty oikein
			 * 1a. Ohjelma ilmoittaa, jos jokin kohta on täytetty väärin
			 * 2. Lisätään käyttäjä tietokantaan
			 * 2a. Ei lisätä käyttäjää tietokantaan, koska käyttäjänimi on varattu
             */

            //Luodaan uusi RegisterModel-olio, joka varastoi tietoonsa registerViewin textfieldien
            RegisterModel tmpRegisterModel = new RegisterModel(registerView.getFname(), registerView.getLname(), registerView.getUname(),
                    registerView.getPword1(), registerView.getPword2(), registerView.getGmail());

            //Tarkistetaan formi
            if (tmpRegisterModel.formValidation(tmpRegisterModel)) {

                User user = new User();
                user.setFirstname(tmpRegisterModel.getfName());
                user.setLastname(tmpRegisterModel.getlName());
                user.setPassword(new PasswordHashing(tmpRegisterModel.getPw1()).get_SHA_256_SecurePassword());
                user.setUsername(tmpRegisterModel.getuName());
                user.setGmail(tmpRegisterModel.getGmail());

                //luodaan käyttäjä tietokantaan, jos käyttäjänimeä ei ole varattu.
                if (dao.addUserDB(user)) {
                    //ilmoitetaan onnistumisesta
                    registerView.showAlert(dao.getAlert());
                    //Ohjataan takaisin loginiin
                    /*
				         * TODO: Joku builderi, jolla saadaan luotua controllerit yhdellä rivillä koodia nätisti.
                     */
                    LoginView loginView = new LoginView();
                    LoginModel loginModel = new LoginModel();
                    LoginController loginViewController = new LoginController(loginView, loginModel, viewChanger);
                    viewChanger.viewBuilder(loginView.getView());
                    //Suljetaan tietokantayhteys
                    dao.closeConnection();
                } else {
                    //Ilmoitetaan käyttäjätunnuksen olevan varattu
                    registerView.showAlert(dao.getAlert());
                }
            } else {//ilmoitetaan formin validaatiosta tapahtuneesta virheestä
                registerView.showAlert(tmpRegisterModel.getAlert());
            }
        }
    }

}
