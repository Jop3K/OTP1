package controllers;

import java.net.URL;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;
import dataAccessObjects.UserDAO;
import java.util.HashMap;
import java.util.Map;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.util.Callback;
import javafx.util.StringConverter;
import models.CurrentUser;
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
<<<<<<< HEAD
    @FXML
    private ComboBox<String> workProfile;
    
    private List<WorkProfile> profiles;
    private Iterator<WorkProfile> list;
=======
    
>>>>>>> a4d0a7d6eccae4ba58409826a7edbcf3856b5b23

    public CalendarViewController() {
        dao = new UserDAO();
        //this.eventModel = e;

    }

    @Override

    public void initialize(URL location, ResourceBundle resources) {
        // TODO Auto-generated method stub
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

<<<<<<< HEAD
        profiles = dao.getUsersWorkProfiles();
        list = profiles.iterator();
       while (list.hasNext()) {
       	WorkProfile tmp = list.next();
       	workProfile.getItems().add(tmp.getNimi());
       }
    }

  public void saveEvent(ActionEvent e) {
	  EventModel eventModel = new EventModel();
	  eventModel.setBeginDay(startDay.getValue());
	  eventModel.setEndDay(endDay.getValue());
	  eventModel.setBeginHour(startHour.getValue());
	  eventModel.setBeginMinute(startMinute.getValue());
	  eventModel.setEndHour(endHour.getValue());
	  eventModel.setEndMinute(endMinute.getValue());
	  eventModel.setUser(CurrentUser.getUser());
	  // Etsitään oikea workprofile-olio eventtiin
	  list = profiles.iterator();
	  while(list.hasNext()) {
		  WorkProfile tmp = list.next();
		  if (tmp.getNimi().equals(workProfile.getValue())) {
			//  eventModel.setWorkProfile(tmp);
			  break;
		  }
	  }
	  //System.out.println(eventModel.getBeginTime());
	  dao.save(eventModel);
	  
		  
  }
=======
        List<WorkProfile> list = dao.getUsersWorkProfiles();

        for (WorkProfile w : list) {
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


    public void saveEvent(ActionEvent even) {

    }

    public ComboBox<WorkProfile> getProfileChooser() {
        return profileChooser;
    }
    
    

>>>>>>> a4d0a7d6eccae4ba58409826a7edbcf3856b5b23
}
