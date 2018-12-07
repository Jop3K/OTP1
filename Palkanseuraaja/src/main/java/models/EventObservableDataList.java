package models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.LocalDateTime;
import java.util.List;

public class EventObservableDataList {

	private static ObservableList<EventModel> data = null;
	
	private EventObservableDataList() {

		List<EventModel> events = models.CurrentUserRefactored.INSTANCE.getAllEvents();

		// Sort by begin DateTime
		events.sort((o1, o2) -> {

			LocalDateTime timeOne = o1.getBeginDay().atTime(Integer.parseInt(o1.getBeginHour()), Integer.parseInt(o1.getBeginMinute()));

			LocalDateTime timeTwo = o2.getBeginDay().atTime(Integer.parseInt(o2.getBeginHour()), Integer.parseInt(o2.getBeginMinute()));

			int result = 0;

			if(timeOne.isBefore(timeTwo)) {
				result = -1;
			}  else if(timeOne.isAfter(timeTwo)) {
				result = 1;
			}

			return result;
		});

		data = FXCollections.observableArrayList(events);
	}
	
	public static ObservableList<EventModel> getInstance(){
		
		if (data == null) return new EventObservableDataList().data;
		else{
			return data;
		}
	}
}
