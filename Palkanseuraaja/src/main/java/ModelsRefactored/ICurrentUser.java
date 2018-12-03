/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ModelsRefactored;

import java.util.List;
import models.EventModel;
import models.User;
import models.WorkProfile;

/**
 *
 * @author Artur
 */
public interface ICurrentUser {
    
    public void setUser(User user);
    public User getUser();
    public void reset();
    public void setWorkProfiles(List<WorkProfile> profileList);
    public List<WorkProfile> getWorkProfiles();
    public List<EventModel> getAllEvents();
    
}
