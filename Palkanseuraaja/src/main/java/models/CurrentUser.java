package models;

import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

/*
 * Staattinen luokka, joka pitää välimuistissa kirjautuneen käyttäjän tiedot
 * 
 */
public class CurrentUser extends User {

    private static User user;

    public CurrentUser(User user) throws NoSuchAlgorithmException, NoSuchProviderException {
        CurrentUser.setUser(user);
    }

    public static User getUser() {
        return user;
    }

    public static void setUser(User user) {
        CurrentUser.user = user;
    }

}
