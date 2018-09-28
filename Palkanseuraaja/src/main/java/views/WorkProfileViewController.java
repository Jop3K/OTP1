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
import models.Palkka;
import models.Palkkalisa;
import models.WorkProfile;

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
    private ComboBox<?> profileChooser;
    @FXML
    private TextField yolisa;
    @FXML
    private TextField lauantailisa;
    @FXML
    private Button saveLisa;
    @FXML
    private ComboBox<?> lisanStartHour;
    @FXML
    private ComboBox<?> lisanStartMinute;
    @FXML
    private ComboBox<?> lisanEndHour;
    @FXML
    private ComboBox<?> lisanEndMinute;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
//
//    @FXML
//    private void handleSaveProfileButtonClick(ActionEvent event) {
//            WorkProfile workProfile = new WorkProfile();
//            workProfile.setNimi(profileChooser.getSelectionModel().getSelectedItem().toString());
//            if (!(tuntipalkka.getText().isEmpty())) {
//                Palkka palkka = new Palkka();
//                palkka.setTuntipalkka(Double.parseDouble(tuntipalkka.getText()));
//                workProfile.setPalkka(palkka);
//            }
//            if (!yolisa.getText().isEmpty()) {
//                workProfile.getPalkka().addPalkkalisa(getYolisa());
//            }
//            if (!lauantailisa.getText().isEmpty()) {
//                workProfile.getPalkka().addPalkkalisa(getYolisa());
//            }
//    }
    
//    @FXML
//    private void handleSaveLisaButtonClick(ActionEvent event) {
//        Palkkalisa lisa = new Palkkalisa();
//        lisa.setNimi(lisanNimi.getText());
//        lisa.setStartHour(Integer.parseInt(lisanStartHour.getSelectionModel().getSelectedItem().toString()));
//        lisa.setStartMinute(Integer.parseInt(lisanStartMinute.getSelectionModel().getSelectedItem().toString()));
//        lisa.setEndHour(Integer.parseInt(lisanEndHour.getSelectionModel().getSelectedItem().toString()));
//        lisa.setEndMinute(Integer.parseInt(lisanEndMinute.getSelectionModel().getSelectedItem().toString()));
//    }

//    private Palkkalisa getYolisa() {
//        Palkkalisa lisa = new Palkkalisa();
//        lisa.setNimi("Yölisä");
//        lisa.setPalkkalisa(Double.parseDouble(yolisa.getText()));
//        return lisa;
//    }
//
//    private Palkkalisa getLauantaiLisa() {
//        Palkkalisa lisa = new Palkkalisa();
//        lisa.setNimi("Lauantailisä");
//        lisa.setPalkkalisa(Double.parseDouble(lauantailisa.getText()));
//        return lisa;
//    }
//    
    
}
