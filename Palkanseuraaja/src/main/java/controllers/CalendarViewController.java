package controllers;


import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;

import dataAccessObjects.UserDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
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
	private EventModel eventModel;
    @FXML
    private Color x2;
    @FXML
    private Font x1;
    @FXML
    private Color x21;
    @FXML
    private Font x11;
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
    private ComboBox<String> workProfile;

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
        for (int i = 0; i<24; i ++) {
        	if(i <10) {
        		String tmp = "0"+i;
        		startHour.getItems().add(tmp);
        		endHour.getItems().add(tmp);
        	}
        	else {
        		startHour.getItems().add(Integer.toString(i));
        		endHour.getItems().add(Integer.toString(i));
        	}
        }

      List <WorkProfile> profiles = new ArrayList<WorkProfile>();
      profiles = dao.getUsersWorkProfiles(CurrentUser.getUser());
        Iterator<WorkProfile> list = profiles.iterator();
        while (list.hasNext()) {
        	WorkProfile tmp = list.next();
        	workProfile.getItems().add(tmp.getNimi());
        }
	}

    public void init() {

       System.out.print("Alkaako");
        // Täytetään comboboxit
        for (int i = 0; i < 59; i++) {
        	startMinute.getItems().add(Integer.toString(i));
        	endMinute.getItems().add(Integer.toString(i));

        }
        for (int i = 0; i<24; i ++) {
        	if(i <10) {
        		String tmp = "0"+i;
        		startHour.getItems().add(tmp);
        		endHour.getItems().add(tmp);
        	}
        	else {
        		startHour.getItems().add(Integer.toString(i));
        		endHour.getItems().add(Integer.toString(i));
        	}
        }

        List <WorkProfile> profiles = dao.getUsersWorkProfiles(CurrentUser.getUser());
        Iterator<WorkProfile> list = profiles.iterator();
        while (list.hasNext()) {
        	WorkProfile tmp = list.next();
        	workProfile.getItems().add(tmp.getNimi());
        }
    }


    public void saveEvent (ActionEvent even) {


    }



}
