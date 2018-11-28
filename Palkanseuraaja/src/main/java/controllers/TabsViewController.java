package controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuButton;
import javafx.scene.control.Tab;
import models.CurrentUser;


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
    private MenuButton userSettingsMenu;
    
    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        labels = ResourceBundle.getBundle("LabelsBundle");
        
        calendarTab.setText(labels.getString("calendar"));
        profileTab.setText(labels.getString("workProfile"));
        statsTab.setText(labels.getString("statistics"));
        
//        userSettingsMenu.setText(CurrentUser.getUser().getUsername());
       
    }
    
    public void refresh() {
        //TODO
    }
    
    
    
    
}
