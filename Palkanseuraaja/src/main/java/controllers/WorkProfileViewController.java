package controllers;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.text.Font;

/**
 * FXML Controller class
 *
 * @author artur, joni
 */
public class WorkProfileViewController implements Initializable {
	
	ObservableList<String> workProfileList = FXCollections.observableArrayList("Profile1", "Profile2", "Profile3");

    @FXML
    private Font x12;
    @FXML
    private Font x121;
    
    @FXML
    private ComboBox<String> tyoprofiiliDrop;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    	tyoprofiiliDrop.setValue("Profile1");
    	tyoprofiiliDrop.setItems(workProfileList);
    	
    }    
    
}
