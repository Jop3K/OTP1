package ModelsRefactored.ViewManagerRefactored;

import application.Main;
import controllers.LoginController;
import controllers.RegisterController;
import java.io.IOException;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import models.CurrentUser;
import models.LoginModel;
import models.RegisterModel;
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
    private final LoginModel loginModel;
    private final LoginController loginController;
    private final RegisterView registerView;
    private final RegisterController registerController;

    private ViewManager() {
        loginView = new LoginView();
        loginModel = new LoginModel();
        loginController = new LoginController(loginView, loginModel);
        registerView = new RegisterView();
        registerController = new RegisterController(registerView);
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
        Scene window = new Scene(registerView.getView(), 800, 800);
        stage.setScene(window);
        stage.show();
    }

    public void switchToApplicationView() {
        try {
            Scene window = new Scene(FXMLLoader.load(Main.class.getResource("/fxml/TabsView.fxml")));
            stage.setScene(window);
            stage.setTitle(ResourceBundle.getBundle("LabelsBundle").getString("title") + " v0.03a // " + ResourceBundle.getBundle("LabelsBundle").getString("username") + ": " + CurrentUser.getUser().getUsername());
            stage.getIcons().add(new Image("/img/salarypal.png"));
            stage.show();
        } catch (IOException e) {
            // TODO Auto-generated catch block
        }
    }

}
