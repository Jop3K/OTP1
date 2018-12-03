package ModelsRefactored.UserRefactored;

import java.util.ArrayList;
import java.util.List;
import models.EventModel;
import models.User;
import models.WorkProfile;

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
    public void reset() {
        user = null;
    }

    @Override
    public void setWorkProfiles(List<WorkProfile> profileList) {
        this.profileList = profileList;
    }

    @Override
    public List<WorkProfile> getWorkProfiles() {
        return profileList;
    }

    @Override
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
