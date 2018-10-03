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
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;

import javafx.scene.text.Font;
import javafx.util.Callback;
import javafx.util.StringConverter;
import models.CurrentCalendarViewController;
import models.CurrentUser;
import models.CurrentWorkProfile;
import models.ExtraPay;
import models.WeekDays;
import models.WorkProfile;
import org.hibernate.Hibernate;

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
    private ComboBox<ExtraPay> extrapayChooser;
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
    private TextField extrapay;
    @FXML
    private ComboBox<String> setEndHour;
    @FXML
    private ComboBox<String> setBeginHour;
    @FXML
    private ComboBox<String> setEndMinute;
    @FXML
    private ComboBox<String> setBeginMinute;
    @FXML
    private ComboBox<WorkProfile> profileChooser;
    @FXML
    private CheckBox monday;
    @FXML
    private CheckBox tuesday;
    @FXML
    private CheckBox wednesday;
    @FXML
    private CheckBox thursday;
    @FXML
    private CheckBox friday;
    @FXML
    private CheckBox saturday;
    @FXML
    private CheckBox sunday;

    private List<WorkProfile> profileList;

    private List<ExtraPay> extrapayList;

    public CurrentWorkProfile currentWorkProfile;

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

            if (!profileName.getText().isEmpty()) {
                profileChooser.getSelectionModel().getSelectedItem().setNimi(profileName.getText());
            }
            if (!tuntipalkka.getText().isEmpty()) {
                profileChooser.getSelectionModel().getSelectedItem().setPay(Double.parseDouble(tuntipalkka.getText()));
            }
            if (!yolisa.getText().isEmpty()) {
                profileChooser.getSelectionModel().getSelectedItem().getYolisa().setExtraPay(Double.parseDouble(yolisa.getText()));
                dao.save(profileChooser.getSelectionModel().getSelectedItem().getYolisa());
            }
            if (!extrapay.getText().isEmpty()) {
                extrapayChooser.getSelectionModel().getSelectedItem().setExtraPay(Double.parseDouble(extrapay.getText()));
                dao.save(extrapayChooser.getSelectionModel().getSelectedItem());
            }

            disableFields();
            
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

                    ExtraPay lisa = new ExtraPay();
                    lisa.setName("Yölisä");
                    lisa.setExtraPay(Double.parseDouble(yolisa.getText()));
                    lisa.setWorkProfile(workProfile);

                    dao.save(lisa);

                }

                if (extrapayChooser.getSelectionModel().getSelectedItem() != null) {

                    extrapayChooser.getSelectionModel().getSelectedItem().setExtraPay(Double.parseDouble(extrapay.getText()));

                    dao.save(extrapayChooser.getSelectionModel().getSelectedItem());

                }

                dao.openCurrentSessionWithTransaction().saveOrUpdate(workProfile);
                dao.closeCurrentSessionWithTransaction();

                profileChooser.getItems().add(workProfile);

                CurrentCalendarViewController.getCalendarViewController().loadWorkProfilesToProfileChooser();

                disableFields();
                editButton.setDisable(false);

            }
        }
    }

    @FXML
    private void handleSaveLisaButtonClick(ActionEvent event) {
        if (!profileChooser.getSelectionModel().isEmpty()) {

            if (!lisanNimi.getText().isEmpty()) {
                ExtraPay lisa = new ExtraPay();
                lisa.setName(lisanNimi.getText());
                lisa.setBeginHour(setBeginHour.getSelectionModel().getSelectedItem());
                lisa.setBeginMinute(setBeginMinute.getSelectionModel().getSelectedItem());
                lisa.setEndHour(setEndHour.getSelectionModel().getSelectedItem());
                lisa.setEndMinute(setEndMinute.getSelectionModel().getSelectedItem());
                
                WeekDays weekdays = new WeekDays();
                
                if (monday.isSelected()) {
                    weekdays.setMonday(true);
                }
                if (tuesday.isSelected()) {
                    weekdays.setTuesday(true);
                }
                if (wednesday.isSelected()) {
                    weekdays.setWednesday(true);
                }
                if (thursday.isSelected()) {
                    weekdays.setThursday(true);
                }if (friday.isSelected()) {
                    weekdays.setFriday(true);
                }if (saturday.isSelected()) {
                    weekdays.setSaturday(true);
                }
                if (sunday.isSelected()) {
                    weekdays.setSunday(true);
                }

                lisa.setWorkProfile(profileChooser.getSelectionModel().getSelectedItem());
                lisa.setWeekdays(weekdays);
                weekdays.setExtrapay(lisa);
                dao.save(weekdays);
                dao.save(lisa);
                extrapayChooser.getItems().add(lisa);
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Virhe!");
            alert.setHeaderText("Valitse työprofiili");
            alert.showAndWait();
        }

    }

    @FXML
    private void handleNewProfileButtonClick() {

        clearTextFields();
        enableFields();

        profileChooser.getSelectionModel().clearSelection();

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
    private void handleExtrapayChooserClick(ActionEvent event) {
        extrapay.setText(Double.toString(extrapayChooser.getSelectionModel().getSelectedItem().getExtraPay()));
    }

    @FXML
    private void handleEditButtonClick() {

        if (editButton.getText().equals("Muokkaa")) {

            enableFields();

            editButton.setText("Peruuta");

        } else {

            loadValuesToTextFields();

            disableFields();

        }

    }

    private void loadValuesToTextFields() {

        currentWorkProfile = new CurrentWorkProfile(profileChooser.getSelectionModel().getSelectedItem());

        clearTextFields();
        disableFields();

        editButton.setText("Muokkaa");

        profileName.setText(profileChooser.getSelectionModel().getSelectedItem().getNimi());
        tuntipalkka.setText(Double.toString(profileChooser.getSelectionModel().getSelectedItem().getPay()));
        if (profileChooser.getSelectionModel().getSelectedItem().getExtraPays() != null) {
            if (profileChooser.getSelectionModel().getSelectedItem().getYolisa() != null) {
                yolisa.setText(Double.toString(profileChooser.getSelectionModel().getSelectedItem().getYolisa().getExtraPay()));
            }
        }
        if (extrapayChooser.getSelectionModel().getSelectedItem() != null) {
            extrapay.setText(Double.toString(extrapayChooser.getSelectionModel().getSelectedItem().getExtraPay()));
        }

        loadValuesToExtrapayChooser();

    }

    private void clearTextFields() {
        profileName.clear();
        tuntipalkka.clear();
        yolisa.clear();
        extrapay.clear();
    }

    private void disableFields() {
        profileName.setDisable(true);
        tuntipalkka.setDisable(true);
        yolisa.setDisable(true);
        extrapay.setDisable(true);
    }

    private void enableFields() {
        profileName.setDisable(false);
        tuntipalkka.setDisable(false);
        yolisa.setDisable(false);
        extrapay.setDisable(false);
    }

    private void loadValuesToExtrapayChooser() {

        extrapayChooser.getItems().clear();

        extrapayList = dao.getProfilesExtraPays();

        if (!extrapayList.isEmpty()) {
            for (ExtraPay e : extrapayList) {
                extrapayChooser.getItems().add(e);
            }
        }

        if (!extrapayList.isEmpty()) {

            extrapayChooser.setCellFactory(new Callback<ListView<ExtraPay>, ListCell<ExtraPay>>() {
                @Override
                public ListCell<ExtraPay> call(ListView<ExtraPay> w) {
                    ListCell cell = new ListCell<ExtraPay>() {
                        @Override
                        protected void updateItem(ExtraPay item, boolean empty) {
                            super.updateItem(item, empty);
                            if (empty) {
                                setText("");
                            } else {
                                setText(item.getName());
                            }
                        }
                    };
                    return cell;
                }
            });

            extrapayChooser.setConverter(new StringConverter<ExtraPay>() {
                private Map<String, ExtraPay> map = new HashMap<>();

                @Override
                public String toString(ExtraPay w) {
                    if (w != null) {
                        String str = w.getName();
                        map.put(w.getName(), w);
                        return str;
                    } else {
                        return "";
                    }

                }

                //  TODO
                @Override
                public ExtraPay fromString(String string) {
                    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                }
            });

        }

    }
}
