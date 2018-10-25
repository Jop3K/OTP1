package models;

import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
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
    
    public static void setWorkProfiles(List<WorkProfile> profileList) {
        CurrentUser.profileList = profileList;
    }
    
    public static List<WorkProfile> getWorkProfiles() {
        return profileList;
    }

}
