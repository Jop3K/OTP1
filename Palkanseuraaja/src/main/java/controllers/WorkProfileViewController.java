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
import javafx.scene.text.Font;
import javafx.util.Callback;
import models.CurrentUser;
import models.CurrentWorkProfile;
import models.ExtraPay;
import models.WeekDays;
import models.WorkProfile;
import models.CurrentCalendarViewController;
import models.EventModel;

/**
 * The controller class for WorkProfileView
 * @author Joni, Artur, Joonas
 *
 */
public class WorkProfileViewController implements Initializable {

   

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

        loadValuesToProfileChooser();
        generateTimes();

        extrapay.setDisable(true);

    }

    /**
     * This methods is for handling the saving of a Work Profile onclick of the "save" button
     * @param event required parameter for ActionEvent
     */
    @FXML // button handler for "save profile" button click
    private void handleSaveProfileButtonClick(ActionEvent event) {

        if (!profileChooser.getSelectionModel().isEmpty()) { // if profile is chosen, updates it

            if (!profileName.getText().isEmpty()) {
                profileChooser.getSelectionModel().getSelectedItem().setNimi(profileName.getText());
            }
            if (!tuntipalkka.getText().isEmpty()) {
                profileChooser.getSelectionModel().getSelectedItem().setPay(Double.parseDouble(tuntipalkka.getText()));
            }
            if (!extrapay.getText().isEmpty()) {
                extrapayChooser.getSelectionModel().getSelectedItem().setExtraPay(Double.parseDouble(extrapay.getText()));
                UserDAO.save(extrapayChooser.getSelectionModel().getSelectedItem());
            }

            UserDAO.save(profileChooser.getSelectionModel().getSelectedItem());

            profileChooser.getItems().add(profileChooser.getSelectionModel().getSelectedItem());
            profileChooser.getSelectionModel().selectLast();

            disableFields();
            editButton.setText("Muokkaa");

        } else {

            if (!profileName.getText().isEmpty()) { // verifies that profiles' name is entered

                WorkProfile workProfile = new WorkProfile();

                workProfile.setNimi(profileName.getText());
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
                if(!tuntipalkka.getText().isEmpty() || extrapayChooser.getSelectionModel().getSelectedItem() != null) {
                    
                    for(EventModel profileEvent : workProfile.getEvents()) {
                     
                        profileEvent.calcPay();
                        UserDAO.update(profileEvent);
                        
                    }
                }

                profileChooser.getItems().add(workProfile);
                profileChooser.getSelectionModel().selectLast();

                disableFields();
                editButton.setDisable(false);
                editButton.setText("Muokkaa");
                
                CurrentCalendarViewController.getCalendarViewController().profileChooser.getItems().add(workProfile);

            }
        }
    }

    /**
     *  We use this ActionEvent method to save a new additional pay
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

                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Validation error!");
                    alert.setHeaderText("Validation error");
                    alert.showAndWait();
                }
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Virhe!");
            alert.setHeaderText("Joku virhe");
            alert.showAndWait();
        }

        clearTextFieldsExtraPay();
        loadValuesToExtrapayChooser();

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

        editButton.setText("Muokkaa");
        editButton.setDisable(true);

        extrapay.setDisable(true);

    }

    /**
     * handler for "Valitse Profiili" dropdown 
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

        if (editButton.getText().equals("Muokkaa")) { // Changes button text when successfully clicked to "Peruuta"

            enableFields();

            editButton.setText("Peruuta");

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

        editButton.setText("Muokkaa");

        profileName.setText(profileChooser.getSelectionModel().getSelectedItem().getNimi());
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
        setBeginHour.getItems().clear();
        setBeginMinute.getItems().clear();
        setEndHour.getItems().clear();
        setEndHour.getItems().clear();
    }

    /**
     *  disables textfields on the left side
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
            setBeginHour.getItems().add(extrapayChooser.getSelectionModel().getSelectedItem().getBeginHour());
        }
        if (extrapayChooser.getSelectionModel().getSelectedItem().getBeginMinute() != null) {
            setBeginMinute.getItems().add(extrapayChooser.getSelectionModel().getSelectedItem().getBeginMinute());
        }
        if (extrapayChooser.getSelectionModel().getSelectedItem().getEndHour() != null) {
            setEndHour.getItems().add(extrapayChooser.getSelectionModel().getSelectedItem().getEndHour());
        }
        if (extrapayChooser.getSelectionModel().getSelectedItem().getEndMinute() != null) {
            setEndHour.getItems().add(extrapayChooser.getSelectionModel().getSelectedItem().getEndMinute());
        }

    }

    /**
     *  Loads current users' values to "Valitse Profiili" dropdown
     */
    private void loadValuesToProfileChooser() {  
        profileChooser.getItems().clear();

        profileList = CurrentUser.getWorkProfiles();

        for (WorkProfile w : profileList) {
            profileChooser.getItems().add(w);
        }

        profileChooser.setCellFactory( // Customizes cells so that they appear as WorkProfile's name (String) and not as a WorkProfile object
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

    }

    /**
     *  Loading the values for ExtraPay chooser
     */
    private void loadValuesToExtrapayChooser() {

        extrapayChooser.getItems().clear();

        extrapayList = UserDAO.getProfilesExtraPays();

        if (!extrapayList.isEmpty()) {

            for (ExtraPay e : extrapayList) {
                extrapayChooser.getItems().add(e);
            }

            extrapayChooser.setCellFactory(
                    new Callback<ListView<ExtraPay>, ListCell<ExtraPay>>() {
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
        }

    }

    /**
     *  Generate numbers to hours/minutes dropdowns
     */
    public void generateTimes() { 
        for (int i = 0; i < 60; i++) {
            setBeginMinute.getItems().add(Integer.toString(i));
            setEndMinute.getItems().add(Integer.toString(i));

        }
        for (int i = 0; i < 24; i++) {
            if (i < 10) {
                String tmp = "0" + i;
                setBeginHour.getItems().add(tmp);
                setEndHour.getItems().add(tmp);
            } else {
                setBeginHour.getItems().add(Integer.toString(i));
                setEndHour.getItems().add(Integer.toString(i));
            }
        }
    }

    /**
     * time validation for ExtraPay
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
