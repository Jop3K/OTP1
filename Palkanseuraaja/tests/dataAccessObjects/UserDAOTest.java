package dataAccessObjects;

import de.saxsys.javafx.test.JfxRunner;
import de.saxsys.javafx.test.TestInJfxThread;
import models.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(JfxRunner.class)
public class UserDAOTest {

    UserDAO dao = new UserDAO();
    User user;

    /**
     * Builds a new user before every test
     */
    @Before
    public void setUp() {

        RegisterModelRefactored rmodel = new RegisterModelRefactored("junit", "tester", "junit", "test", "test");

        try {
            user = rmodel.buildUser();
        } catch (NoSuchAlgorithmException | NoSuchProviderException e) {
            e.printStackTrace();
        }

        UserDAO.save(user);

    }

    /**
     * Removes test user after every test
     */
    @After
    public void tearDown() {
        UserDAO.delete(user);
        CurrentUserRefactored.INSTANCE.setUser(null);
    }

    /**
     * Tests user login: checks that a user is found only after a successful login
     *
     */
    @Test
    @TestInJfxThread
    public void userCanLoginSuccesfully() {

        try {

            // No current user exists before login
            assertNull(CurrentUserRefactored.INSTANCE.getUser());

            UserDAO.login("wrong", "login");

            // No current user exists before login
            assertNull(CurrentUserRefactored.INSTANCE.getUser());

            UserDAO.login("junit", "test");

            //  After succesful login, current user exists and is same as expected
            assertEquals(CurrentUserRefactored.INSTANCE.getUser().getUsername(), user.getUsername());


        } catch (NoSuchAlgorithmException | NoSuchProviderException e) {
            e.printStackTrace();
        }

    }

    /**
     * Tests event and workprofile saving into database
     */
    @Test
    @TestInJfxThread
    public void eventAndWorkProfileCanBeSaved() {

        try {
            UserDAO.login("junit", "test");
        } catch (NoSuchAlgorithmException | NoSuchProviderException e) {
            e.printStackTrace();
        }

        WorkProfile profile = new WorkProfile();
        profile.setName("testprofile");
        profile.setPay(10);
        profile.setUser(user);

        UserDAO.save(profile);

        LocalDateTime beginDate = LocalDateTime.now();
        LocalDateTime endDate = beginDate.plusHours(10);

        EventModel event = new EventModel(beginDate, endDate, profile);

        UserDAO.save(event);

        List<WorkProfile> profiles = UserDAO.getUsersWorkProfilesFromDatabase();

        WorkProfile retrievedProfile = profiles.get(0);

        // Retrieved profile is the same as the one that was saved
        assertEquals(profile.getName(), retrievedProfile.getName());

        List<EventModel> events = retrievedProfile.getEvents();

        EventModel retrievedEvent = events.get(0);

        // Retrieved event is the same as the one that was saved
        assertEquals(retrievedEvent.getBeginDay(), event.getBeginDay());

    }

    @Test
    @TestInJfxThread
    public void extraPayCanBeSaved() {
        fail("Not yet implemented");
    }

}