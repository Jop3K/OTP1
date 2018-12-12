package controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;

/**
 * FXML Controller class
 *
 * @author artur, joni
 */
public class TabsViewController implements Initializable {

    private ResourceBundle labels;

    @FXML
    private Tab calendarTab;
    @FXML
    private Tab profileTab;
    @FXML
    private Tab statsTab;
    @FXML
    private Button logoutButton;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        labels = ResourceBundle.getBundle("LabelsBundle");

        calendarTab.setText(labels.getString("calendar"));
        profileTab.setText(labels.getString("workProfile"));
        statsTab.setText(labels.getString("statistics"));

        logoutButton.setText(labels.getString("logout"));
    }

    /**
     * Event onClick for user log out
     */
    @FXML
    public void logoutButtonHandler() {
        models.CurrentUserRefactored.INSTANCE.setUser(null);
        models.ViewManager.INSTANCE.clearLoginViewFields();
        models.ViewManager.INSTANCE.switchToLoginView();

    }


}
