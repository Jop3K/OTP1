package controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

/**
 * FXML Controller class
 *
 * @author artur
 */
public class TabsViewController implements Initializable {

    @FXML
    private TabPane tabPane;
    @FXML
    private Tab calendarTab;
    @FXML
    private Tab profileTab;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
    
    public void refresh() {
        //TODO
    }
}
