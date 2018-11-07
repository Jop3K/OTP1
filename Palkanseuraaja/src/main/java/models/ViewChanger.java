package models;

import application.Main;
import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

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
        stage.setTitle("Palkanseuraaja v0.02a // Käyttäjä: " + CurrentUser.getUser().getUsername());
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

    }

    public void viewBuilder(Parent ikkuna) {

        Scene window = new Scene(ikkuna, 800, 800);

        stage.setScene(window);

        stage.show();
    }

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }
}
