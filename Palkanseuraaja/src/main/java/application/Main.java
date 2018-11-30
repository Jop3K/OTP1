package application;

import models.ViewChanger;
import controllers.LoginController;
import dataAccessObjects.UserDAO;

import java.io.IOException;

import java.security.GeneralSecurityException;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import models.LoginModel;
import views.LoginView;

/**
 *
 * @author Joni, Artur, Joonas
 * @version 0.01a
 *
 */
public class Main extends Application {

    /**
     * This method launches the Login view
     *
     * @param stage we give this parameter to start method for it to know what
     * we are starting
     */
    @Override
    public void start(Stage stage) throws IOException {
        Locale.setDefault(new Locale("is"));
//        stage.getIcons().add(new Image("/img/salarypal.png"));

        stage.setTitle(ResourceBundle.getBundle("MessagesBundle").getString("welcome"));

        //Valjastetaan ensinmäin MVC-hommeli käyttöön. stage kulkee kokoajan mukana kirjautumisen
        //ja rekisteröinnin välissä
        ViewChanger viewChanger = new ViewChanger(stage);
        LoginView loginView = new LoginView();
        LoginModel loginModel = new LoginModel();
        LoginController loginViewController = new LoginController(loginView, loginModel, viewChanger);
        UserDAO dao = new UserDAO();

        Scene window = new Scene(loginView.getView(), 800, 800);

        stage.setScene(window);

        stage.setOnCloseRequest(e -> Platform.exit());

        stage.show();

    }

    /**
     * The basic main method for launching an application
     *
     * @param args the arguments that we are launching
     */
    public static void main(String[] args) {
        launch(Main.class);
    }

}
