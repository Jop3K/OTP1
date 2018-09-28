package controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

/**
 * FXML Controller class
 *
 * @author artur
 */
public class CalendarViewController implements Initializable {

    @FXML
    private Color x2;
    @FXML
    private Font x1;
    @FXML
    private DatePicker dateChooser;
    @FXML
    private DatePicker dateStart;
    @FXML
    private DatePicker dateEnd;
    @FXML
    private Color x21;
    @FXML
    private Font x11;
    @FXML
    private Button sendToGoogleCalendar;
    @FXML
    private Button saveButton;
    @FXML
    private ComboBox<?> hourStart;
    @FXML
    private ComboBox<?> minuteStart;
    @FXML
    private ComboBox<?> hourEnd;
    @FXML
    private ComboBox<?> minuteEnd;
    @FXML
    private ComboBox<?> profileChooser;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
