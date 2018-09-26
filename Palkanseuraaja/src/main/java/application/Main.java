package application;

import controllers.LoginController;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import models.LoginModel;
import views.LoginView;

public class Main extends Application {

    @Override
    public void start(Stage stage) {
        stage.setTitle("Tervetuloa Palkanseuraajaan");

        //Valjastetaan ensinmäin MVC-hommeli käyttöön. stage kulkee kokoajan mukana kirjautumisen
        //ja rekisteröinnin välissä
        ViewChanger viewChanger = new ViewChanger(stage);
        LoginView loginView = new LoginView();
        LoginModel loginModel = new LoginModel();
        LoginController loginViewController = new LoginController(loginView, loginModel, viewChanger); // Tarvitaanko tätä tässä?

        Scene window = new Scene(loginView.getView(), 800, 800);

        stage.setScene(window);

        stage.show();
    }

    public static void main(String[] args) {
        launch(Main.class);
    }

}
