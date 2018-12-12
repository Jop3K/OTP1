package models;

import java.util.ArrayList;
import java.util.List;

public enum CurrentUserRefactored implements ICurrentUser {

    INSTANCE;

    private User user;
    private List<WorkProfile> profileList;

    @Override
    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public User getUser() {
        return user;
    }

    @Override
    public void nullUser() {
        user = null;
    }

    public void setWorkProfiles(List<WorkProfile> profileList) {
        this.profileList = profileList;
    }

    public List<WorkProfile> getWorkProfiles() {
        return profileList;
    }

    public List<EventModel> getAllEvents() {
        List<EventModel> eventList = new ArrayList<>();
        profileList.forEach((w) -> {
            w.getEvents().forEach((e) -> {
                eventList.add(e);
            });
        });
        return eventList;
    }

}
