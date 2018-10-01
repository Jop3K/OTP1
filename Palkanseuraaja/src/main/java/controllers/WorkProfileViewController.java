package controllers;

import dataAccessObjects.UserDAO;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;

import javafx.scene.text.Font;
import javafx.util.Callback;
import javafx.util.StringConverter;
import models.CurrentCalendarViewController;
import models.CurrentUser;
import models.Extrapay;
import models.WorkProfile;

/**
 * FXML Controller class
 *
 * @author artur, joni
 */
public class WorkProfileViewController implements Initializable {

    private UserDAO dao;

    @FXML
    private TextField tuntipalkka;
    @FXML
    private Font x12;
    @FXML
    private TextField profileName;
    @FXML
    private Button saveLisa;
    @FXML
    private TextField yolisa;
    @FXML
    private ComboBox<?> muulisa;
    @FXML
    private Button saveProfile;
    @FXML
    private Button editButton;
    @FXML
    private Button newProfile;
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

    private List<WorkProfile> profileList;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        dao = new UserDAO();
        dao.openCurrentSession();

        profileList = dao.getUsersWorkProfiles();

        for (WorkProfile w : profileList) {
            profileChooser.getItems().add(w);
        }

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

            //  TODO
            @Override
            public WorkProfile fromString(String string) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        });

    }

    @FXML
    private void handleSaveProfileButtonClick(ActionEvent event) {

        if (!profileChooser.getSelectionModel().isEmpty()) {

            profileChooser.getSelectionModel().getSelectedItem().setNimi(profileName.getText());
            profileChooser.getSelectionModel().getSelectedItem().setPay(Double.parseDouble(tuntipalkka.getText()));

            dao.openCurrentSessionWithTransaction().saveOrUpdate(profileChooser.getSelectionModel().getSelectedItem());
            dao.closeCurrentSessionWithTransaction();

        } else {

            if (!profileName.getText().isEmpty()) {

                WorkProfile workProfile = new WorkProfile();

                workProfile.setNimi(profileName.getText());
                workProfile.setUser(CurrentUser.getUser());

                if (!tuntipalkka.getText().isEmpty()) {
                    workProfile.setPay(Double.parseDouble(tuntipalkka.getText()));
                }
                if (!yolisa.getText().isEmpty()) {

                    Extrapay lisa = new Extrapay();
                    lisa.setExtrapay(Double.parseDouble(yolisa.getText()));
                    lisa.setWorkProfile(workProfile);

                    dao.openCurrentSessionWithTransaction().saveOrUpdate(lisa);
                    dao.closeCurrentSessionWithTransaction();

                }

                dao.openCurrentSessionWithTransaction().saveOrUpdate(workProfile);
                dao.closeCurrentSessionWithTransaction();

                profileChooser.getItems().add(workProfile);
                
                CurrentCalendarViewController.getCalendarViewController().loadWorkProfilesToProfileChooser();

            }
        }
    }

    //NOT READY!!
    @FXML
    private void handleSaveLisaButtonClick(ActionEvent event) {
//        if (!lisanNimi.getText().isEmpty()) {
//            Extrapay lisa = new Extrapay();
//            lisa.setName(lisanNimi.getText());
//            lisa.setStartHour(Integer.parseInt(lisanStartHour.getSelectionModel().getSelectedItem().toString()));
//            lisa.setStartMinute(Integer.parseInt(lisanStartMinute.getSelectionModel().getSelectedItem().toString()));
//            lisa.setEndHour(Integer.parseInt(lisanEndHour.getSelectionModel().getSelectedItem().toString()));
//            lisa.setEndMinute(Integer.parseInt(lisanEndMinute.getSelectionModel().getSelectedItem().toString()));
//            System.out.println(lisa);
//        }
    }

    @FXML
    private void handleNewProfileButtonClick() {

        profileName.clear();
        tuntipalkka.clear();
        yolisa.clear();

        profileChooser.getSelectionModel().clearSelection();

        profileName.setDisable(false);
        tuntipalkka.setDisable(false);
        yolisa.setDisable(false);

        editButton.setText("Muokkaa");
        editButton.setDisable(true);

    }

    @FXML
    private void handleProfileChooserClick(ActionEvent event) {

        if (profileChooser.getSelectionModel().getSelectedItem() != null) {

            loadValuesToTextFields();

            editButton.setDisable(false);

        }

    }

    @FXML
    private void handleEditButtonClick() {

        if (editButton.getText().equals("Muokkaa")) {

            profileName.setDisable(false);
            tuntipalkka.setDisable(false);
            yolisa.setDisable(false);

            editButton.setText("Peruuta");

        } else {

            loadValuesToTextFields();

        }

    }

    private void loadValuesToTextFields() {

        profileName.setText(profileChooser.getSelectionModel().getSelectedItem().getNimi());
        tuntipalkka.setText(Double.toString(profileChooser.getSelectionModel().getSelectedItem().getPay()));
        // yolisa.setText(Double.toString(profileChooser.getSelectionModel().getSelectedItem()));
        profileName.setDisable(true);
        tuntipalkka.setDisable(true);
        yolisa.setDisable(true);

        editButton.setText("Muokkaa");

    }

}
