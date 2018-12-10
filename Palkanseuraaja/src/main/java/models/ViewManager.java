package models;

import application.Main;
import controllers.LoginControllerRefactored;
import controllers.RegisterController;
import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import views.LoginView;
import views.RegisterView;

/**
 *
 * @author Artur
 */
public enum ViewManager implements IViewManager {

    INSTANCE;

    private Stage stage;
    private final LoginView loginView;
    private final LoginControllerRefactored loginController;
    private final RegisterView registerView;
    private final RegisterController registerController;

    private final ResourceBundle labels;

    ViewManager() {
        loginView = new LoginView();
        loginController = new LoginControllerRefactored(loginView);
        registerView = new RegisterView();
        registerController = new RegisterController(registerView);

        labels = ResourceBundle.getBundle("LabelsBundle");
    }

    @Override
    public void setStage(Stage stage) {
        this.stage = stage;
        this.stage.setOnCloseRequest(e -> {
            Platform.exit();
            System.exit(0);
        });
    }

    @Override
    public void closeStage() {
        stage.close();
    }

    public void switchToLoginView() {
        Scene window = new Scene(loginView.getView(), 800, 800);
        stage.setScene(window);
        stage.show();
    }

    public void switchToRegisterView() {
        registerView.setLabels();
        Scene window = new Scene(registerView.getView(), 800, 800);
        stage.setScene(window);
        stage.show();
    }

    public void switchToApplicationView() throws IOException {
        Scene window = new Scene(FXMLLoader.load(Main.class.getResource("/fxml/TabsView.fxml")));
        stage.setScene(window);
        stage.setTitle(labels.getString("title") + " v0.03a // " + labels.getString("username") + ": " + models.CurrentUserRefactored.INSTANCE.getUser().getUsername());
        stage.getIcons().add(new Image("/img/salarypal.png"));
        stage.show();

    }

    public void clearLoginViewFields() {
        loginView.clearFields();
    }
}
