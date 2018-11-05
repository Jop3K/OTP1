package application;

import models.ViewChanger;
import controllers.LoginController;
import java.io.IOException;
import java.security.GeneralSecurityException;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import models.CurrentCalendarViewController;
import models.LoginModel;
import views.LoginView;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws IOException, GeneralSecurityException {
        stage.setTitle("Tervetuloa Palkanseuraajaan");
        stage.getIcons().add(new Image("/img/salarypal.png"));
        
        //Valjastetaan ensinmäin MVC-hommeli käyttöön. stage kulkee kokoajan mukana kirjautumisen
        //ja rekisteröinnin välissä
        ViewChanger viewChanger = new ViewChanger(stage);
        LoginView loginView = new LoginView();
        LoginModel loginModel = new LoginModel();
        LoginController loginViewController = new LoginController(loginView, loginModel, viewChanger);
        
        Scene window = new Scene(loginView.getView(), 800, 800);

        stage.setScene(window);

        stage.show();

    }

    public static void main(String[] args) throws IOException, GeneralSecurityException {
        launch(Main.class);
    }

}
