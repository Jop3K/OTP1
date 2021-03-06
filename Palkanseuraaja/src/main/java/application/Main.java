package application;

import dataAccessObjects.UserDAO;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;

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
     * @throws java.io.IOException
     */
    @Override
    public void start(Stage stage) throws IOException {
        Locale.setDefault(new Locale("fi"));
        stage.getIcons().add(new Image("/img/salarypal.png"));

        UserDAO dao = new UserDAO();

        models.ViewManager.INSTANCE.setStage(stage);
        models.ViewManager.INSTANCE.switchToLoginView();
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
