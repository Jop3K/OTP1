package models;

import application.Main;
import controllers.LoginController;
import java.io.IOException;
import java.util.ResourceBundle;
import javafx.application.Platform;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import views.LoginView;

// luokka joka vaihtaa näkymiä
public class ViewChanger {

    private Stage stage;

    public ViewChanger() {

    }

    public ViewChanger(Stage stage) {
        this.stage = stage;
    }
    // Vaihtaa Stagea sekä ottaa parametrinä halutun fxml tiedoston etuliitteen, jonka haluaa ladata roottiin
    //sekä ViewChangerin, jonka stagen se sitten sulkee(sulkee kirjautumisikkunan)

    public void switchStage(String name, ViewChanger viewChanger) {

        Parent root = null;
        Stage stage = new Stage();

        stage.setTitle(ResourceBundle.getBundle("LabelsBundle").getString("title") + " v0.03a // " + ResourceBundle.getBundle("LabelsBundle").getString("username") + ": " + CurrentUser.getUser().getUsername());
//        stage.getIcons().add(new Image("/img/salarypal.png"));
        try {
            root = FXMLLoader.load(Main.class.getResource(name));
            Scene window = new Scene(root);
            stage.setScene(window);
            stage.show();
            viewChanger.getStage().close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        stage.setOnCloseRequest(e -> {
            Platform.exit();
            System.exit(0);
        });

    }

    public void viewBuilder(Parent ikkuna) {
        Scene window = new Scene(ikkuna, 800, 800);
        Stage stage = new Stage();
        stage.setScene(window);
        stage.show();
    }

    public void switchToLoginView(ViewChanger viewChanger) {
        LoginView loginView = new LoginView();
        LoginModel loginModel = new LoginModel();
        LoginController loginController = new LoginController(loginView, loginModel, viewChanger);
        viewChanger.viewBuilder(loginView.getView());

        viewChanger.getStage().setOnCloseRequest(e -> {
            Platform.exit();
            System.exit(0);
        });
    }

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }
}
