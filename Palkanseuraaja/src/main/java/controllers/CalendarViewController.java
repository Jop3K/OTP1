package controllers;

import java.net.URL;
import java.time.Instant;
import java.time.LocalDate;

import java.time.ZoneId;
import java.util.Iterator;

import java.util.List;
import java.util.ResourceBundle;
import dataAccessObjects.UserDAO;

import java.util.Date;

import java.util.HashMap;
import java.util.Map;

import javax.swing.JOptionPane;

import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.util.Callback;
import javafx.util.StringConverter;
import models.Calculation;
import models.CurrentCalendarViewController;
import models.EventModel;
import models.WorkProfile;

/**
 * FXML Controller class
 *
 * @author artur
 */
public class CalendarViewController implements Initializable {

    private UserDAO dao;
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
    private ComboBox<WorkProfile> profileChooser;
    @FXML
    private DatePicker startDay;
    @FXML
    private DatePicker endDay;
    @FXML
    private ComboBox<String> startHour;
    @FXML
    private ComboBox<String> endHour;
    @FXML
    private ComboBox<String> startMinute;
    @FXML
    private ComboBox<String> endMinute;
    @FXML
    private TableView<EventModel> eventTable;
    @FXML
    private TableColumn<EventModel, Date> startColumn;
    @FXML
    private TableColumn<EventModel, Date> endColumn;
    @FXML
    private TableColumn<EventModel, String> workProfileColumn;
    @FXML
    private DatePicker eventDatePicker;

    private ObservableList<EventModel> data;
    private List<WorkProfile> profileList;

    public CalendarViewController() {
        dao = new UserDAO();
    }

    @Override

    public void initialize(URL location, ResourceBundle resources) {
        // TODO Auto-generated method stub
        CurrentCalendarViewController.setCalendarViewController(this);

        //Täytetään taulu
        setTable();


        System.out.print("Alkaako");
        // Täytetään comboboxit
        for (int i = 0; i < 60; i++) {
            startMinute.getItems().add(Integer.toString(i));
            endMinute.getItems().add(Integer.toString(i));

        }
        for (int i = 0; i < 24; i++) {
            if (i < 10) {
                String tmp = "0" + i;
                startHour.getItems().add(tmp);
                endHour.getItems().add(tmp);
            } else {
                startHour.getItems().add(Integer.toString(i));
                endHour.getItems().add(Integer.toString(i));
            }
        }

        dao = new UserDAO();
        dao.openCurrentSession();

        loadWorkProfilesToProfileChooser();

    }

    public boolean isValid() {
        if (startDay.getValue() == null || endDay.getValue() == null || startHour.getSelectionModel().isEmpty() || endHour.getSelectionModel().isEmpty() || startMinute.getSelectionModel().isEmpty() || endMinute.getSelectionModel().isEmpty() || profileChooser.getSelectionModel().isEmpty()) {
            return false;

        } else {
            return true;
        }
        //startDay != null || endDay != null || startHour != null || endHour != null || startMinute != null || endMinute != null || profileChooser != null;
    }

    public void saveEvent(ActionEvent e) {
        LocalDate startDate = startDay.getValue();
        LocalDate endDate = endDay.getValue();
        EventModel eventModel = new EventModel();

        try {

            if (isValid() == false) {
                JOptionPane.showMessageDialog(null, "Täytä kaikki kentät ennen tapahtuman luomista");
                return;
            }

            if (startDate.isBefore(endDate)) {
                eventModel.setBeginDay(startDay.getValue());
                eventModel.setEndDay(endDay.getValue());
            } else if (startDate.isAfter(endDate)) {
                JOptionPane.showMessageDialog(null, "Lopetusajan pitää olla aloitusajan jälkeen (päivä)");
                return;
            } else if (startDate.equals(endDate)) { // jos aloitus pvm ja lopetus pvm sama niin...
                eventModel.setBeginDay(startDay.getValue());
                eventModel.setEndDay(endDay.getValue());
                if (Integer.parseInt(startHour.getValue()) > Integer.parseInt(endHour.getValue())) { // jos aloitusaika on isompi kuin lopetusaika
                    JOptionPane.showMessageDialog(null, "Aloitusaika ei voi olla lopetusajan jälkeen, tarkista tunnit");
                    return;
                } else if (Integer.parseInt(startHour.getValue()) == Integer.parseInt(endHour.getValue())) { // jos aloitustunti on sama kuin lopetustunti
                    if (Integer.parseInt(startMinute.getValue()) >= Integer.parseInt(endMinute.getValue())) {
                        JOptionPane.showMessageDialog(null, "Aloitusaika ei voi olla lopetusajan jälkeen, tarkista minuutit");
                        return;
                    }
                }
            }

            eventModel.setBeginHour(startHour.getValue());
            eventModel.setBeginMinute(startMinute.getValue());
            eventModel.setEndHour(endHour.getValue());
            eventModel.setEndMinute(endMinute.getValue());
            // Etsitään oikea workprofile-olio eventtiin

            eventModel.setWorkProfile(profileChooser.getSelectionModel().getSelectedItem());

        } catch (Exception err) {
            JOptionPane.showMessageDialog(null, "Täytä kaikki kentät ennen tapahtuman luomista");
            return;
        }

        if (isValid() == true) {
            JOptionPane.showMessageDialog(null, "Luonti onnistui!");
            dao.save(eventModel);
            data.add(eventModel);
        } else {
            JOptionPane.showMessageDialog(null, "Täytä kaikki kentät ennen tapahtuman luomista");
            return;
        }

        System.out.print(Calculation.Calculate(eventModel));
    }

    public void loadWorkProfilesToProfileChooser() {

        profileList = new UserDAO().getUsersWorkProfiles();

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
    public void setTable() {
    	
   	 data = FXCollections.observableArrayList(dao.getEvents());
     workProfileColumn.setCellValueFactory(new PropertyValueFactory<EventModel, String>("workProfile"));
     startColumn.setCellValueFactory(new PropertyValueFactory<EventModel, Date>("beginTime"));
     endColumn.setCellValueFactory(new PropertyValueFactory<EventModel, Date>("endTime"));
   	 FilteredList<EventModel> filteredData = new FilteredList<>(data, p -> true);
   	 eventDatePicker.valueProperty().addListener((observable, oldValue,newValue) -> {
   		 System.out.println("äksöni");
   		 filteredData.setPredicate(EventModel -> { 
   			 
   		if (newValue == null) {
            return true;
   	 };
   	java.sql.Date newDate = java.sql.Date.valueOf(newValue);
   	LocalDate ldate = EventModel.getBeginTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
   	java.sql.Date eventDate = java.sql.Date.valueOf(ldate);
   	
   	if(newDate == eventDate) {
   		System.out.println("==");
   		return true;
   	}
   	
   	
   	if(newDate.equals(eventDate)) {
   		System.out.println("equal");
   		return true;
   	}
   	return false;
   		 });
   	 });
   	
    eventTable.setItems(filteredData);
    
   	 
   	
   	 
   
        

    }

    public void populateTableByDate() {

    }

}
