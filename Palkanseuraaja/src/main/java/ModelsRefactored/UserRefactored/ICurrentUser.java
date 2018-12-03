package ModelsRefactored.UserRefactored;

import java.util.List;
import models.EventModel;
import models.User;
import models.WorkProfile;

public interface ICurrentUser {

    public void setUser(User user);

    public User getUser();

    public void reset();

    public void setWorkProfiles(List<WorkProfile> profileList);

    public List<WorkProfile> getWorkProfiles();

    public List<EventModel> getAllEvents();

}
