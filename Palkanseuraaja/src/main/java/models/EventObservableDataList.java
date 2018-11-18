package models;

import dataAccessObjects.UserDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class EventObservableDataList {

	private static ObservableList<EventModel> data = null;
	
	private EventObservableDataList() {

		data = FXCollections.observableArrayList(CurrentUser.getEvents());
	}
	
	public static ObservableList<EventModel> getInstance(){
		
		if (data == null) return new EventObservableDataList().data;
		else{
			return data;
		}
	}
}
