package controllers;

import dataAccessObjects.UserDAO;
import java.net.URL;
import java.util.List;
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
import javafx.scene.control.Label;
import javafx.scene.text.Font;
import javafx.util.Callback;
import models.*;

/**
 * The controller class for WorkProfileView
 *
 * @author Joni, Artur, Joonas
 *
 */
public class WorkProfileViewController implements Initializable {

    private ResourceBundle labels;
    private ResourceBundle buttons;
    private ResourceBundle alerts;

    @FXML
    private Label workInfo;
    @FXML
    private Label chooseProfile;
    @FXML
    private Label profilesName;
    @FXML
    private Label hourPay;
    @FXML
    private Label extraPay;
    @FXML
    private Label createNewExtraPay;
    @FXML
    private Label extraPayName;
    @FXML
    private Label days;
    @FXML
    private Label timeFrame;
    @FXML
    private Label hoursStart;
    @FXML
    private Label minutesStart;
    @FXML
    private Label hoursEnd;
    @FXML
    private Label minutesEnd;
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
        labels = ResourceBundle.getBundle("LabelsBundle");
        buttons = ResourceBundle.getBundle("ButtonLabelsBundle");
        alerts = ResourceBundle.getBundle("AlertMessagesBundle");

        setLabels();
        setButtons();

        loadValuesToProfileChooser();
        generateTimes();

        extrapay.setDisable(true);

    }

    /**
     * This methods is for handling the saving of a Work Profile onclick of the
     * "save" button
     *
     * @param event required parameter for ActionEvent
     */
    public void setLabels() {
        workInfo.setText(labels.getString("workInfo"));
        chooseProfile.setText(labels.getString("chooseProfile"));
        profilesName.setText(labels.getString("profileName"));
        hourPay.setText(labels.getString("hourPay"));
        extraPay.setText(labels.getString("extraPay"));
        createNewExtraPay.setText(labels.getString("createNewExtraPay"));
        extraPayName.setText(labels.getString("extraPayName"));
        days.setText(labels.getString("days"));
        timeFrame.setText(labels.getString("timeFrame"));
        hoursStart.setText(labels.getString("h"));
        minutesStart.setText(labels.getString("m"));
        hoursEnd.setText(labels.getString("h"));
        minutesEnd.setText(labels.getString("m"));
        monday.setText(labels.getString("mo"));
        tuesday.setText(labels.getString("tu"));
        wednesday.setText(labels.getString("we"));
        thursday.setText(labels.getString("th"));
        friday.setText(labels.getString("fr"));
        saturday.setText(labels.getString("sa"));
        sunday.setText(labels.getString("su"));
        setBeginHour.promptTextProperty().setValue(labels.getString("h"));
        setBeginMinute.promptTextProperty().setValue(labels.getString("m"));
        setEndHour.promptTextProperty().setValue(labels.getString("h"));
        setEndMinute.promptTextProperty().setValue(labels.getString("m"));

    }

    public void setButtons() {
        newProfile.setText(buttons.getString("newProfile"));
        extrapayChooser.promptTextProperty().set(buttons.getString("createOrChoose"));
        saveProfile.setText(buttons.getString("save"));
        editButton.setText(buttons.getString("edit"));
        saveLisa.setText(buttons.getString("createOrUpdateExtraPay"));
    }

    @FXML // button handler for "save profile" button click
    private void handleSaveProfileButtonClick(ActionEvent event) {

        if (!profileChooser.getSelectionModel().isEmpty()) { // if profile is chosen, updates it

            if (!profileName.getText().isEmpty()) {
                profileChooser.getSelectionModel().getSelectedItem().setName(profileName.getText());
            }
            if (!tuntipalkka.getText().isEmpty()) {
                profileChooser.getSelectionModel().getSelectedItem().setPay(Double.parseDouble(tuntipalkka.getText()));
            }
            if (!extrapay.getText().isEmpty()) {
                extrapayChooser.getSelectionModel().getSelectedItem().setExtraPay(Double.parseDouble(extrapay.getText()));
                UserDAO.save(extrapayChooser.getSelectionModel().getSelectedItem());
            }

            profileChooser.getSelectionModel().getSelectedItem().calculateEventPays();

            UserDAO.save(profileChooser.getSelectionModel().getSelectedItem());

            profileChooser.getItems().add(profileChooser.getSelectionModel().getSelectedItem());
            profileChooser.getSelectionModel().selectLast();

            disableFields();
            editButton.setText(buttons.getString("edit"));

        } else {

            if (!profileName.getText().isEmpty()) { // verifies that profiles' name is entered

                WorkProfile workProfile = new WorkProfile();

                workProfile.setName(profileName.getText());
                workProfile.setUser(CurrentUser.getUser());

                if (!tuntipalkka.getText().isEmpty()) {
                    workProfile.setPay(Double.parseDouble(tuntipalkka.getText()));
                }

                if (extrapayChooser.getSelectionModel().getSelectedItem() != null) {

                    extrapayChooser.getSelectionModel().getSelectedItem().setExtraPay(Double.parseDouble(extrapay.getText()));

                    UserDAO.save(extrapayChooser.getSelectionModel().getSelectedItem());

                }

                UserDAO.save(workProfile);

                // Tallennetaan eventit uudelleen jos palkka extrapayt vaihtuu (palkka lasketaan tässä tilanteessa uudelleen)
                if (!tuntipalkka.getText().isEmpty() || extrapayChooser.getSelectionModel().getSelectedItem() != null) {

                    for (EventModel profileEvent : workProfile.getEvents()) {

                        profileEvent.calcPay();
                        UserDAO.update(profileEvent);

                    }
                }

                profileChooser.getItems().add(workProfile);
                profileChooser.getSelectionModel().selectLast();

                disableFields();
                editButton.setDisable(false);
                editButton.setText(buttons.getString("edit"));

                CurrentCalendarViewController.getCalendarViewController().profileChooser.getItems().add(workProfile);

            }
        }
    }

    /**
     * We use this ActionEvent method to save a new additional pay
     *
     * @param event required parameter
     */
    @FXML
    private void handleSaveLisaButtonClick(ActionEvent event) {
        if (!profileChooser.getSelectionModel().isEmpty()) { // profile must be chosen to create ExtraPay for it

            if (!extrapayChooser.getSelectionModel().isEmpty() && timesAreValid()) { // if selected, updates

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

                UserDAO.save(extrapayChooser.getSelectionModel().getSelectedItem());

            } else { // when no ExtraPay is selected, creates a new ExtraPay

                if (!lisanNimi.getText().isEmpty() && timesAreValid()) {
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
                    UserDAO.save(lisa);

                    // Moved to here because no need to load values if adding failed
                    clearTextFieldsExtraPay();
                    loadValuesToExtrapayChooser();

                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle(alerts.getString("error"));
                    alert.setHeaderText(alerts.getString("error"));
                    alert.showAndWait();
                }
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);

            alert.setTitle(alerts.getString("error"));
            alert.setHeaderText(alerts.getString("error"));
            alert.showAndWait();
        }

    }

    /**
     * handler for "Uusi profiili" button
     */
    @FXML
    private void handleNewProfileButtonClick() {

        clearTextFieldsProfile();
        enableFields();

        profileChooser.getSelectionModel().clearSelection();
        extrapayChooser.getItems().clear();

        editButton.setText(buttons.getString("edit"));
        editButton.setDisable(true);

        extrapay.setDisable(true);

    }

    /**
     * handler for "Valitse Profiili" dropdown
     *
     * @param event required parameter
     */
    @FXML
    private void handleProfileChooserClick(ActionEvent event) {

        if (profileChooser.getSelectionModel().getSelectedItem() != null) {

            currentWorkProfile = new CurrentWorkProfile(profileChooser.getSelectionModel().getSelectedItem());

            loadValuesToTextFields();
            extrapay.clear();

            editButton.setDisable(false);

        }

    }

    /**
     * handler for "Palkkalisä" dropdown
     *
     * @param event a required parameter
     */
    @FXML
    private void handleExtrapayChooserClick(ActionEvent event) {
        if (extrapayChooser.getSelectionModel().getSelectedItem() != null) {
            extrapay.setText(Double.toString(extrapayChooser.getSelectionModel().getSelectedItem().getExtraPay()));

            loadValuesToExtrapayFields();
        }
    }

    /**
     * handler for "Muokkaa" button
     */
    @FXML
    private void handleEditButtonClick() {

        if (editButton.getText().equals(buttons.getString("edit"))) { // Changes button text when successfully clicked to "Peruuta"

            enableFields();

            editButton.setText(buttons.getString("cancel"));

        } else {

            loadValuesToTextFields();

            disableFields();

        }

    }

    /**
     * Loads values to textfield from selected WorkProfile object
     */
    private void loadValuesToTextFields() {

        clearTextFieldsProfile();
        disableFields();

        editButton.setText(buttons.getString("edit"));

        profileName.setText(profileChooser.getSelectionModel().getSelectedItem().getName());
        tuntipalkka.setText(Double.toString(profileChooser.getSelectionModel().getSelectedItem().getPay()));

        if (extrapayChooser.getSelectionModel().getSelectedItem() != null) {
            extrapay.setText(Double.toString(extrapayChooser.getSelectionModel().getSelectedItem().getExtraPay()));
        } else {
            extrapay.clear();
        }

        loadValuesToExtrapayChooser();

    }

    /**
     * Clears text fields on the Profile side
     */
    private void clearTextFieldsProfile() {
        profileName.clear();
        tuntipalkka.clear();
        extrapay.clear();
    }

    /**
     * Clears text fields on the ExtraPay side
     */
    private void clearTextFieldsExtraPay() {
        lisanNimi.clear();
        monday.setSelected(false);
        tuesday.setSelected(false);
        wednesday.setSelected(false);
        thursday.setSelected(false);
        friday.setSelected(false);
        saturday.setSelected(false);
        sunday.setSelected(false);

        // Changed to clearSelection because previous method removed all values from dropdown
        setBeginHour.getSelectionModel().clearSelection();
        setBeginMinute.getSelectionModel().clearSelection();
        setEndHour.getSelectionModel().clearSelection();
        setEndHour.getSelectionModel().clearSelection();
    }

    /**
     * disables textfields on the left side
     */
    private void disableFields() {
        profileName.setDisable(true);
        tuntipalkka.setDisable(true);
        extrapay.setDisable(true);
    }

    /**
     * enables textfields on the left side
     */
    private void enableFields() {
        profileName.setDisable(false);
        tuntipalkka.setDisable(false);
        if (extrapayChooser.getSelectionModel().getSelectedItem() != null) {
            extrapayChooser.setDisable(false);
        }
        extrapay.setDisable(false);
    }

    /**
     * Loads values to ExtraPay side from selected ExtraPay object
     */
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

        if (extrapayChooser.getSelectionModel().getSelectedItem().getBeginHour() != null) {
            String beginHour = extrapayChooser.getSelectionModel().getSelectedItem().getBeginHour();
            setBeginHour.getSelectionModel().select(beginHour);
        }
        if (extrapayChooser.getSelectionModel().getSelectedItem().getBeginMinute() != null) {
            String beginMinute = extrapayChooser.getSelectionModel().getSelectedItem().getBeginMinute();
            setBeginMinute.getSelectionModel().select(beginMinute);
        }
        if (extrapayChooser.getSelectionModel().getSelectedItem().getEndHour() != null) {
            String endHour = extrapayChooser.getSelectionModel().getSelectedItem().getEndHour();
            setEndHour.getSelectionModel().select(endHour);
        }
        if (extrapayChooser.getSelectionModel().getSelectedItem().getEndMinute() != null) {
            String endMinute = extrapayChooser.getSelectionModel().getSelectedItem().getEndMinute();
            setEndMinute.getSelectionModel().select(endMinute);
        }

    }

    /**
     * Loads current users' values to "Valitse Profiili" dropdown
     */
    private void loadValuesToProfileChooser() {
        profileChooser.getItems().clear();

        profileList = CurrentUser.getWorkProfiles();

        for (WorkProfile w : profileList) {
            profileChooser.getItems().add(w);
        }

    }

    /**
     * Loading the values for ExtraPay chooser
     */
    private void loadValuesToExtrapayChooser() {

        extrapayChooser.getItems().clear();

        extrapayList = CurrentWorkProfile.getProfilesExtraPays();

        if (!extrapayList.isEmpty()) {

            for (ExtraPay e : extrapayList) {
                extrapayChooser.getItems().add(e);
            }

        }

    }

    /**
     * Generate numbers to hours/minutes dropdowns
     */
    public void generateTimes() {
        for (int i = 0; i < 60; i++) {

            String tmp;

            if(i < 10) {
                tmp = "0" + i;
            } else {
                tmp = "" + i;
            }

            if(i < 24) {
                setBeginHour.getItems().add(tmp);
                setEndHour.getItems().add(tmp);
            }

            setBeginMinute.getItems().add(tmp);
            setEndMinute.getItems().add(tmp);

        }
    }

    /**
     * time validation for ExtraPay
     *
     * @return the boolean for the result of validation
     */
    public boolean timesAreValid() {
        if (setBeginHour.getSelectionModel().isEmpty() || setEndHour.getSelectionModel().isEmpty()
                || setBeginMinute.getSelectionModel().isEmpty() || setEndMinute.getSelectionModel().isEmpty()) {
            return false;
        } else {
            return true;
        }
    }

}
