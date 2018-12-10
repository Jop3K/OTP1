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
    ExtraPay extrapay;
    WeekDays weekdays;

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

        if(weekdays != null) {
            UserDAO.delete(weekdays);
            weekdays = null;
            extrapay = null;
        }

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
    public void eventWorkProfileExtraPayCanBeSaved() {

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

        weekdays = new WeekDays();

        weekdays.setWednesday(true);

        UserDAO.save(weekdays);

        extrapay = new ExtraPay();

        weekdays.setExtraPay(extrapay);
        extrapay.setWeekdays(weekdays);
        extrapay.setName("testextra");
        extrapay.setExtraPay(10);

        extrapay.setWorkProfile(profile);

        UserDAO.save(extrapay);

        LocalDateTime beginDate = LocalDateTime.now();
        LocalDateTime endDate = beginDate.plusHours(10);

        EventModel event = new EventModel(beginDate, endDate, profile);

        UserDAO.save(event);

        List<WorkProfile> profiles = UserDAO.getUsersWorkProfilesFromDatabase();

        WorkProfile retrievedProfile = profiles.get(0);

        // Retrieved profile is the same as the one that was saved
        assertEquals(profile.getName(), retrievedProfile.getName());

        ExtraPay retrievedExtraPay = retrievedProfile.getExtraPays().get(0);

        // Retrieved extrapay is the same as the one that was saved
        assertEquals(extrapay.getName(), retrievedExtraPay.getName());

        WeekDays retrievedWeekDays = retrievedExtraPay.getWeekdays();

        // Retrieved weekdays is the same as the one that was saved
        assertEquals(retrievedWeekDays.isWednesday() && retrievedWeekDays.isMonday(), weekdays.isWednesday() && weekdays.isMonday());

        List<EventModel> events = retrievedProfile.getEvents();

        EventModel retrievedEvent = events.get(0);

        // Retrieved event is the same as the one that was saved
        assertEquals(retrievedEvent.getBeginDay(), event.getBeginDay());



    }
}