package models;

import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/*
 * Staattinen luokka, joka pitää välimuistissa kirjautuneen käyttäjän tiedot
 * 
 */
public class CurrentUser extends User {

    private static User user;
    private static List<WorkProfile> profileList;

    public CurrentUser(User user) throws NoSuchAlgorithmException, NoSuchProviderException {
        CurrentUser.setUser(user);
    }

    public static User getUser() {
        return user;
    }

    public static void setUser(User user) {
        CurrentUser.user = user;
    }

    public static List<EventModel> getEvents() {

        /*
            Moved here because before, all events were loaded twice. First while loading workprofiles (OneToMany events variable) and again when doing getEvents database call.
            This lead to there being two copies of all events and workprofiles (events had their own copy of workprofile so when hourly salary was updated, they didn't know a thing).
            Now events are retrieved from the already existing workprofiles instead.
         */
        List<WorkProfile> profiles = profileList;

        Iterator itr = profiles.iterator();
        List<EventModel> events = new ArrayList<>();
        while (itr.hasNext()) {
            WorkProfile tmp = (WorkProfile) itr.next();
            List<EventModel> eventlist = tmp.getEvents();
            eventlist.forEach((tmp2) -> {
                events.add(tmp2);
            });

        }

        return events;
    }

    public static void setWorkProfiles(List<WorkProfile> profileList) {
        CurrentUser.profileList = profileList;
    }

    public static List<WorkProfile> getWorkProfiles() {
        return profileList;
    }

}
