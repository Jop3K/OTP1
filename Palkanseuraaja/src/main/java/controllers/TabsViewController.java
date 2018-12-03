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
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;
import javafx.stage.Stage;
import models.CurrentUser;

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
  
    }

    public void refresh() {
        //TODO
    }

    public void logoutEvent(ActionEvent event) {

    	
    }

}
