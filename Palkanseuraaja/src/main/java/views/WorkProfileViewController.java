package views;

import application.ViewChanger;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.Tab;
import javafx.scene.control.TextField;
import javafx.scene.text.Font;
import javafx.util.Callback;
import javafx.util.StringConverter;
import models.Palkka;
import models.Palkkalisa;
import models.WorkProfile;
import application.*;
import javafx.stage.Stage;

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
    private TextField lauantailisa;
    @FXML
    private Button saveLisa;
    @FXML
    private TextField yolisa;
    @FXML
    private ComboBox<?> muulisa;
    @FXML
    private Button saveProfile;
    @FXML
    private TextField lisanNimi;
    @FXML
    private Font x121;
    @FXML
    private ComboBox<?> lisanEndHour;
    @FXML
    private ComboBox<?> lisanStartHour;
    @FXML
    private ComboBox<?> lisanEndMinute;
    @FXML
    private ComboBox<?> lisanStartMinute;
    @FXML
    private ComboBox<WorkProfile> profileChooser;


    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
//         TODO

        WorkProfile eka = new WorkProfile();
        WorkProfile toka = new WorkProfile();
        WorkProfile kolmas = new WorkProfile();
        WorkProfile neljas = new WorkProfile();
        eka.setNimi("eka");
        toka.setNimi("toka");
        kolmas.setNimi("kolmas");
        neljas.setNimi("neljas");
        profileChooser.getItems().addAll(eka, toka, kolmas, neljas);

        profileChooser.setCellFactory(
                new Callback<ListView<WorkProfile>, ListCell<WorkProfile>>() {
            @Override
            public ListCell<WorkProfile> call(ListView<WorkProfile> w) {
                ListCell cell = new ListCell<WorkProfile>() {
                    @Override
                    protected void updateItem(WorkProfile item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setText("");
                        } else {
                            setText(item.getNimi());
                        }
                    }
                };
                return cell;
            }
        });

        profileChooser.setButtonCell(
                new ListCell<WorkProfile>() {
            @Override
            protected void updateItem(WorkProfile w, boolean bln) {
                super.updateItem(w, bln);
                if (bln) {
                    setText("");
                } else {
                    setText(w.getNimi());
                }
            }
        });

        profileChooser.setConverter(
                new StringConverter<WorkProfile>() {
                    private Map<String, WorkProfile> map = new HashMap<>();
                    
            @Override
            public String toString(WorkProfile w) {
                if (w != null) {
                    String str = w.getNimi();
                    map.put(w.getNimi(), w);
                    return str;
                } else {
                    return "";
                }
                
            }

            @Override
            public WorkProfile fromString(String string) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        });

    }

    @FXML
    private void handleSaveProfileButtonClick(ActionEvent event) {

        WorkProfile workProfile = new WorkProfile();

        workProfile.setNimi(profileChooser.getSelectionModel().getSelectedItem().toString());

        if (!(tuntipalkka.getText().isEmpty())) {
            Palkka palkka = new Palkka();
            palkka.setTuntipalkka(Double.parseDouble(tuntipalkka.getText()));
            workProfile.setPalkka(palkka);
        }

        if (!yolisa.getText().isEmpty()) {
            workProfile.getPalkka().addPalkkalisa(getYolisa());
        }

        if (!lauantailisa.getText().isEmpty()) {
            workProfile.getPalkka().addPalkkalisa(getYolisa());
        }
        
        profileChooser.getItems().add(workProfile);

    }

    @FXML
    private void handleSaveLisaButtonClick(ActionEvent event) {
        if (!lisanNimi.getText().isEmpty()) {
            Palkkalisa lisa = new Palkkalisa();
            lisa.setNimi(lisanNimi.getText());
            lisa.setStartHour(Integer.parseInt(lisanStartHour.getSelectionModel().getSelectedItem().toString()));
            lisa.setStartMinute(Integer.parseInt(lisanStartMinute.getSelectionModel().getSelectedItem().toString()));
            lisa.setEndHour(Integer.parseInt(lisanEndHour.getSelectionModel().getSelectedItem().toString()));
            lisa.setEndMinute(Integer.parseInt(lisanEndMinute.getSelectionModel().getSelectedItem().toString()));
            System.out.println(lisa);
        }
    }
    
    private Palkkalisa getYolisa() {
        Palkkalisa lisa = new Palkkalisa();
        lisa.setNimi("Yölisä");
        lisa.setPalkkalisa(Double.parseDouble(yolisa.getText()));
        return lisa;
    }

    private Palkkalisa getLauantaiLisa() {
        Palkkalisa lisa = new Palkkalisa();
        lisa.setNimi("Lauantailisä");
        lisa.setPalkkalisa(Double.parseDouble(lauantailisa.getText()));
        return lisa;
    }
    
    

}