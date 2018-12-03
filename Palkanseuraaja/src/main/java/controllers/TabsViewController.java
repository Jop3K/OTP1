package controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.stage.Stage;
import models.CurrentUser;
import models.ViewChanger;

/**
 * FXML Controller class
 *
 * @author artur, joni
 */
public class TabsViewController implements Initializable {

    private ResourceBundle labels;
    private ResourceBundle alerts;
    
    @FXML
    private Tab calendarTab;
    @FXML
    private Tab profileTab;
    @FXML
    private Tab statsTab;
    @FXML
    private Button logoutButton;

    @FXML

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        labels = ResourceBundle.getBundle("LabelsBundle");
        alerts = ResourceBundle.getBundle("AlertMessagesBundle");

        calendarTab.setText(labels.getString("calendar"));
        profileTab.setText(labels.getString("workProfile"));
        statsTab.setText(labels.getString("statistics"));
        logoutButton.setText(labels.getString("logout"));
        
    }

    @FXML
    public void logoutButtonHandler() {
        CurrentUser.setUser(null);
        ModelsRefactored.ViewManagerRefactored.ViewManager.INSTANCE.closeStage();
        ModelsRefactored.ViewManagerRefactored.ViewManager.INSTANCE.switchToLoginView();
    }

    public void openUserSettings(ActionEvent event) {

        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/fxml/UserSettingsView.fxml"));

            Scene scene = new Scene(fxmlLoader.load(), 600, 400);
            Stage stage = new Stage();
            stage.setTitle(labels.getString("userSettings"));
            stage.setScene(scene);
            stage.show();
        } catch (IOException er) {
            Logger logger = Logger.getLogger(getClass().getName());
            logger.log(Level.SEVERE, alerts.getString("failedToCreateWindow"), er);
        }
    }

}
