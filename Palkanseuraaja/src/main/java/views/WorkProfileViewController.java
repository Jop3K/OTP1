package views;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.text.Font;

/**
 * FXML Controller class
 *
 * @author artur
 */
public class WorkProfileViewController implements Initializable {

    @FXML
    private TextField tuntipalkka;
    @FXML
    private Font x12;
    @FXML
    private Button submit;
    @FXML
    private TextField lisanNimi;
    @FXML
    private Font x121;
    @FXML
    private ComboBox<?> profiilinNimi;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void handleSaveButtonClick(ActionEvent event) {
        submit.setText("TEST");
    }
    
}
