package controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.text.Font;

/**
 * FXML Controller class
 *
 * @author artur
 */
public class WorkProfileViewController {

    @FXML
    private Font x12;
    @FXML
    private Font x121;
    @FXML
    private Button submit;
    @FXML
    private ComboBox profiilinNimi;
    @FXML
    private TextField tuntipalkka;

    /**
     * Initializes the controller class.
     */


    public void handleSaveButtonClick() {
        System.out.println(tuntipalkka.getText());
    }

}
