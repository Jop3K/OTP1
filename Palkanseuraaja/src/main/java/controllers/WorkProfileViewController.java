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
import models.CurrentUser;
import models.CurrentWorkProfile;
import models.ExtraPay;
import models.WeekDays;
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

        loadValuesToProfileChooser();

        extrapay.setDisable(true);

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

                if (extrapayChooser.getSelectionModel().getSelectedItem() != null) {

                    extrapayChooser.getSelectionModel().getSelectedItem().setExtraPay(Double.parseDouble(extrapay.getText()));

                    dao.save(extrapayChooser.getSelectionModel().getSelectedItem());

                }

                dao.openCurrentSessionWithTransaction().saveOrUpdate(workProfile);
                dao.closeCurrentSessionWithTransaction();

                profileChooser.getItems().add(workProfile);
                profileChooser.getSelectionModel().selectLast();

                loadValuesToProfileChooser();
                profileChooser.getSelectionModel().selectLast();

                disableFields();
                editButton.setDisable(false);

            }
        }
    }

    @FXML
    private void handleSaveLisaButtonClick(ActionEvent event) {
        if (!profileChooser.getSelectionModel().isEmpty()) {

            if (!extrapayChooser.getSelectionModel().isEmpty()) {

                if (!extrapay.getText().isEmpty()) {
                    extrapayChooser.getSelectionModel().getSelectedItem().setName(lisanNimi.getText());
                }

                if (monday.isSelected()) {
                    extrapayChooser.getSelectionModel().getSelectedItem().getWeekdays().setMonday(true);
                } else {
                    extrapayChooser.getSelectionModel().getSelectedItem().getWeekdays().setMonday(false);
                }
                if (tuesday.isSelected()) {
                    extrapayChooser.getSelectionModel().getSelectedItem().getWeekdays().setTuesday(true);
                } else {
                    extrapayChooser.getSelectionModel().getSelectedItem().getWeekdays().setTuesday(false);
                }
                if (wednesday.isSelected()) {
                    extrapayChooser.getSelectionModel().getSelectedItem().getWeekdays().setWednesday(true);
                } else {
                    extrapayChooser.getSelectionModel().getSelectedItem().getWeekdays().setWednesday(false);
                }
                if (thursday.isSelected()) {
                    extrapayChooser.getSelectionModel().getSelectedItem().getWeekdays().setThursday(true);
                } else {
                    extrapayChooser.getSelectionModel().getSelectedItem().getWeekdays().setThursday(false);
                }
                if (friday.isSelected()) {
                    extrapayChooser.getSelectionModel().getSelectedItem().getWeekdays().setFriday(true);
                } else {
                    extrapayChooser.getSelectionModel().getSelectedItem().getWeekdays().setFriday(false);
                }
                if (saturday.isSelected()) {
                    extrapayChooser.getSelectionModel().getSelectedItem().getWeekdays().setSaturday(true);
                } else {
                    extrapayChooser.getSelectionModel().getSelectedItem().getWeekdays().setSaturday(false);
                }
                if (sunday.isSelected()) {
                    extrapayChooser.getSelectionModel().getSelectedItem().getWeekdays().setSunday(true);
                } else {
                    extrapayChooser.getSelectionModel().getSelectedItem().getWeekdays().setSunday(false);
                }

                extrapayChooser.getSelectionModel().getSelectedItem().setBeginHour(setBeginHour.getSelectionModel().getSelectedItem());
                extrapayChooser.getSelectionModel().getSelectedItem().setBeginMinute(setBeginMinute.getSelectionModel().getSelectedItem());
                extrapayChooser.getSelectionModel().getSelectedItem().setEndHour(setEndHour.getSelectionModel().getSelectedItem());
                extrapayChooser.getSelectionModel().getSelectedItem().setEndMinute(setEndMinute.getSelectionModel().getSelectedItem());

                dao.openCurrentSessionWithTransaction().saveOrUpdate(extrapayChooser.getSelectionModel().getSelectedItem().getWeekdays());
                dao.closeCurrentSessionWithTransaction();
                dao.openCurrentSessionWithTransaction().saveOrUpdate(extrapayChooser.getSelectionModel().getSelectedItem());
                dao.closeCurrentSessionWithTransaction();

            } else {

                if (!lisanNimi.getText().isEmpty() && !setBeginHour.getSelectionModel().getSelectedItem().isEmpty() && !setBeginMinute.getSelectionModel().getSelectedItem().isEmpty()
                        && !setEndHour.getSelectionModel().getSelectedItem().isEmpty() && !setEndMinute.getSelectionModel().getSelectedItem().isEmpty()) {
                    ExtraPay lisa = new ExtraPay();
                    lisa.setName(lisanNimi.getText());
                    lisa.setBeginHour(setBeginHour.getSelectionModel().getSelectedItem());
                    lisa.setBeginMinute(setBeginMinute.getSelectionModel().getSelectedItem());
                    lisa.setEndHour(setEndHour.getSelectionModel().getSelectedItem());
                    lisa.setEndMinute(setEndMinute.getSelectionModel().getSelectedItem());

                    WeekDays weekdays = new WeekDays();

                    if (monday.isSelected()) {
                        weekdays.setMonday(true);
                    } else {
                        weekdays.setMonday(false);
                    }
                    if (tuesday.isSelected()) {
                        weekdays.setTuesday(true);
                    } else {
                        weekdays.setTuesday(false);
                    }
                    if (wednesday.isSelected()) {
                        weekdays.setWednesday(true);
                    } else {
                        weekdays.setWednesday(false);
                    }
                    if (thursday.isSelected()) {
                        weekdays.setThursday(true);
                    } else {
                        weekdays.setThursday(false);
                    }
                    if (friday.isSelected()) {
                        weekdays.setFriday(true);
                    } else {
                        weekdays.setFriday(false);
                    }
                    if (saturday.isSelected()) {
                        weekdays.setSaturday(true);
                    } else {
                        weekdays.setSaturday(false);
                    }
                    if (sunday.isSelected()) {
                        weekdays.setSunday(true);
                    } else {
                        weekdays.setSunday(false);
                    }

                    lisa.setWorkProfile(profileChooser.getSelectionModel().getSelectedItem());
                    lisa.setWeekdays(weekdays);
                    weekdays.setExtrapay(lisa);
                    dao.openCurrentSessionWithTransaction().saveOrUpdate(weekdays);
                    dao.closeCurrentSessionWithTransaction();
                    dao.openCurrentSessionWithTransaction().saveOrUpdate(lisa);
                    dao.closeCurrentSessionWithTransaction();

                }
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Virhe!");
            alert.setHeaderText("Valitse työprofiili");
            alert.showAndWait();
        }

        clearTextFieldsExtraPay();
        loadValuesToExtrapayChooser();

    }

    @FXML
    private void handleNewProfileButtonClick() {

        clearTextFieldsProfile();
        enableFields();

        profileChooser.getSelectionModel().clearSelection();
        extrapayChooser.getSelectionModel().clearSelection();

        editButton.setText("Muokkaa");
        editButton.setDisable(true);

        extrapayChooser.setDisable(true);
        extrapay.setDisable(true);

    }

    @FXML
    private void handleProfileChooserClick(ActionEvent event) {

        if (profileChooser.getSelectionModel().getSelectedItem() != null) {

            currentWorkProfile = new CurrentWorkProfile(profileChooser.getSelectionModel().getSelectedItem());

            loadValuesToTextFields();

            editButton.setDisable(false);

        }

    }

    @FXML
    private void handleExtrapayChooserClick(ActionEvent event) {
        if (extrapayChooser.getSelectionModel().getSelectedItem() != null) {
            extrapay.setText(Double.toString(extrapayChooser.getSelectionModel().getSelectedItem().getExtraPay()));

            loadValuesToExtrapayFields();
        }
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

        clearTextFieldsProfile();
        disableFields();

        editButton.setText("Muokkaa");

        profileName.setText(profileChooser.getSelectionModel().getSelectedItem().getNimi());
        tuntipalkka.setText(Double.toString(profileChooser.getSelectionModel().getSelectedItem().getPay()));

        if (extrapayChooser.getSelectionModel().getSelectedItem() != null) {
            extrapay.setText(Double.toString(extrapayChooser.getSelectionModel().getSelectedItem().getExtraPay()));
        }

        loadValuesToExtrapayChooser();

    }

    private void clearTextFieldsProfile() {
        profileName.clear();
        tuntipalkka.clear();
        extrapay.clear();
    }

    private void clearTextFieldsExtraPay() {
        lisanNimi.clear();
        monday.setSelected(false);
        tuesday.setSelected(false);
        wednesday.setSelected(false);
        thursday.setSelected(false);
        friday.setSelected(false);
        saturday.setSelected(false);
        sunday.setSelected(false);
        setBeginHour.getItems().clear();
        setBeginMinute.getItems().clear();
        setEndHour.getItems().clear();
        setEndHour.getItems().clear();
    }

    private void disableFields() {
        profileName.setDisable(true);
        tuntipalkka.setDisable(true);
        extrapay.setDisable(true);
    }

    private void enableFields() {
        profileName.setDisable(false);
        tuntipalkka.setDisable(false);
        if (extrapayChooser.getSelectionModel().getSelectedItem() != null) {
            extrapayChooser.setDisable(false);
        }
        extrapay.setDisable(false);
    }

    private void loadValuesToExtrapayFields() {

        lisanNimi.setText(extrapayChooser.getSelectionModel().getSelectedItem().getName());

        if (extrapayChooser.getSelectionModel().getSelectedItem().getWeekdays().isMonday()) {
            monday.setSelected(true);
        } else {
            monday.setSelected(false);
        }
        if (extrapayChooser.getSelectionModel().getSelectedItem().getWeekdays().isTuesday()) {
            tuesday.setSelected(true);
        } else {
            tuesday.setSelected(false);
        }
        if (extrapayChooser.getSelectionModel().getSelectedItem().getWeekdays().isWednesday()) {
            wednesday.setSelected(true);
        } else {
            wednesday.setSelected(false);
        }
        if (extrapayChooser.getSelectionModel().getSelectedItem().getWeekdays().isThursday()) {
            thursday.setSelected(true);
        } else {
            thursday.setSelected(false);
        }
        if (extrapayChooser.getSelectionModel().getSelectedItem().getWeekdays().isFriday()) {
            friday.setSelected(true);
        } else {
            friday.setSelected(false);
        }
        if (extrapayChooser.getSelectionModel().getSelectedItem().getWeekdays().isSaturday()) {
            saturday.setSelected(true);
        } else {
            saturday.setSelected(false);
        }
        if (extrapayChooser.getSelectionModel().getSelectedItem().getWeekdays().isSunday()) {
            sunday.setSelected(true);
        } else {
            sunday.setSelected(false);
        }

        setBeginHour.getItems().add(extrapayChooser.getSelectionModel().getSelectedItem().getBeginHour());
        setBeginMinute.getItems().add(extrapayChooser.getSelectionModel().getSelectedItem().getBeginMinute());
        setEndHour.getItems().add(extrapayChooser.getSelectionModel().getSelectedItem().getEndHour());
        setEndHour.getItems().add(extrapayChooser.getSelectionModel().getSelectedItem().getEndMinute());

    }

    private void loadValuesToProfileChooser() {
        profileChooser.getItems().clear();

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

    private void loadValuesToExtrapayChooser() {

        extrapayChooser.getItems().clear();

        extrapayList = dao.getProfilesExtraPays();

        if (!extrapayList.isEmpty()) {
            for (ExtraPay e : extrapayList) {
                if (e.getName().equals("Yölisä")) {
                    continue;
                }
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
