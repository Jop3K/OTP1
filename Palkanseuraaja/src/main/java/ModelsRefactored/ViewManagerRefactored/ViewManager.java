package ModelsRefactored.ViewManagerRefactored;

import application.Main;
import controllers.LoginController;
import controllers.RegisterController;
import java.io.IOException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import models.CurrentUser;
import models.LoginModel;
import models.RegisterModelRefactored;
import views.LoginView;
import views.RegisterView;

/**
 *
 * @author Artur
 */
public enum ViewManager implements IViewManager {

    INSTANCE;

    private Stage stage;
    private LoginView loginView;
    private LoginController loginController;
    private RegisterView registerView;
    private RegisterController registerController;
    
    private final ResourceBundle labels;

    private ViewManager() {
        loginView = new LoginView();
        loginController = new LoginController(loginView);
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
        Scene window = new Scene(registerView.getView(), 800, 800);
        stage.setScene(window);
        stage.show();
    }

    public void switchToApplicationView() {
        try {
            System.out.println("test5");
            Scene window = new Scene(FXMLLoader.load(Main.class.getResource("/fxml/TabsView.fxml")));
            System.out.println("test6");
            stage.setScene(window);
//            stage.setTitle(labels.getString("title") + " v0.03a // " + labels.getString("username") + ": " + CurrentUser.getUser().getUsername());
//            stage.getIcons().add(new Image("/img/salarypal.png"));
            stage.show();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            System.out.println("error");
        }
    }

    public void clearLoginViewFields() {
        loginView.clearFields();
    }

}
