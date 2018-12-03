package controllers;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.DateTime;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.calendar.CalendarScopes;
import com.google.api.services.calendar.model.CalendarList;
import com.google.api.services.calendar.model.CalendarListEntry;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventDateTime;
import dataAccessObjects.UserDAO;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;
import java.util.TimeZone;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import models.CurrentUser;
import models.EventModel;

/**
 * Heavily modified and extended Googles Java Quickstart Example from
 * https://developers.google.com/calendar/quickstart/java
 */
public class GoogleCalendar {

    private static final String APPLICATION_NAME = "Palkanseuraaja";
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    private static final String pathToTokens = "tokens/" + CurrentUser.getUser().getUsername();
    private static final String TOKENS_DIRECTORY_PATH = pathToTokens;

    /**
     * Global instance of the scopes required by this class. If modifying these
     * scopes, delete your previously saved tokens/ folder.
     */
    private static final List<String> SCOPES = Collections.singletonList(CalendarScopes.CALENDAR);
    private static final String CREDENTIALS_FILE_PATH = "/credentials.json";

    private static com.google.api.services.calendar.Calendar service;
    private static GoogleAuthorizationCodeFlow flow;

    /**
     * Creates an authorized Credential object.
     *
     * @param HTTP_TRANSPORT The network HTTP Transport.
     * @return An authorized Credential object.
     * @throws IOException If the credentials.json file cannot be found.
     */
    private static Credential getCredentials(final NetHttpTransport HTTP_TRANSPORT) throws IOException {
        // Load client secrets.
        InputStream in = GoogleCalendar.class.getResourceAsStream(CREDENTIALS_FILE_PATH);
        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));

        // Build flow and trigger user authorization request.
        flow = new GoogleAuthorizationCodeFlow.Builder(
                HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
                .setDataStoreFactory(new FileDataStoreFactory(new java.io.File(TOKENS_DIRECTORY_PATH)))
                .setAccessType("offline")
                .build();
        LocalServerReceiver receiver = new LocalServerReceiver.Builder().setPort(8888).build();
        return new AuthorizationCodeInstalledApp(flow, receiver).authorize("user");
    }

    /**
     * This method is used to connect our application to Google Calendar
     *
     * @throws IOException
     * @throws GeneralSecurityException
     */
    public static void connect() throws IOException, GeneralSecurityException {
        // Build a new authorized API client service.
        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        service = new com.google.api.services.calendar.Calendar.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT))
                .setApplicationName(APPLICATION_NAME)
                .build();
    }

    /**
     * This method is used to send applications events to Google Calendar. It
     * receives event and calendarId as parameters and creates an event in
     * Google Calendar. If an event with the same ID exists in the database,
     * updateSelectedEvent() method is used instead.
     *
     * @param eventModel
     * @param calendarId
     * @throws IOException
     */
    public static void sendSelectedEventToGoogleCalendar(EventModel eventModel, String calendarId) throws IOException {

        if (eventModel.getGoogleId() == null) {

            if (calendarId == null) {

            }

            Event event = new Event()
                    .setSummary(eventModel.getWorkProfile().getName())
                    .setDescription(eventModel.getDescription());

            DateTime startDateTime = new DateTime(eventModel.getBeginDateTime());
            EventDateTime start = new EventDateTime()
                    .setDateTime(startDateTime)
                    .setTimeZone(TimeZone.getDefault().getID());
            event.setStart(start);

            DateTime endDateTime = new DateTime(eventModel.getEndDateTime());
            EventDateTime end = new EventDateTime()
                    .setDateTime(endDateTime)
                    .setTimeZone(TimeZone.getDefault().getID());
            event.setEnd(end);

            event = service.events().insert(calendarId, event).execute();
            System.out.printf("Event created: %s\n", event.getHtmlLink());

            eventModel.setGoogleId(event.getId());
            eventModel.setGoogleCalendarId(calendarId);
            UserDAO.update(eventModel);
            System.out.println(event.getSummary());

        } else {
            updateSelectedEvent(eventModel, calendarId);
        }

    }

    /**
     * Updates selected (in application) event in Google Calendar.
     *
     * @param eventModel
     * @param calendarId
     * @throws IOException
     */
    public static void updateSelectedEvent(EventModel eventModel, String calendarId) throws IOException {

        if (eventModel.getGoogleCalendarId().equals(calendarId)) {

            System.out.println(eventModel.getGoogleId());
            // Retrieve the event from the API
            Event event = service.events().get(eventModel.getGoogleCalendarId(), eventModel.getGoogleId()).execute();

            // Make a change
            DateTime startDateTime = new DateTime(eventModel.getBeginDateTime());
            EventDateTime start = new EventDateTime()
                    .setDateTime(startDateTime)
                    .setTimeZone(TimeZone.getDefault().getID());
            event.setStart(start);

            DateTime endDateTime = new DateTime(eventModel.getEndDateTime());
            EventDateTime end = new EventDateTime()
                    .setDateTime(endDateTime)
                    .setTimeZone(TimeZone.getDefault().getID());
            event.setEnd(end);

            // Update the event
            service.events().update(eventModel.getGoogleCalendarId(), event.getId(), event).execute();
            eventModel.setGoogleId(event.getId());
            UserDAO.update(eventModel);

        } else {

            service.events().delete(eventModel.getGoogleCalendarId(), eventModel.getGoogleId()).execute();
            eventModel.setGoogleId(null);
            sendSelectedEventToGoogleCalendar(eventModel, calendarId);

        }
    }

    /**
     * This method deletes users Google credentials and thus logs the user out
     * of Google.
     *
     * @throws IOException
     */
    public static void disconnect() throws IOException {
        try {
            flow.getCredentialDataStore().delete("user");
            service = null;
        } catch (Exception e) {
            ResourceBundle alerts = ResourceBundle.getBundle("AlertMessagesBundle");
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle(alerts.getString("error"));
            alert.setHeaderText(alerts.getString("notConnected"));
        }
    }

    public static boolean isConnected() {
        if (service != null) {
            return true;
        }
        return false;
    }

    /**
     * This method is used to get and return a list of Google calendars from
     * Google Calendar account.
     *
     * @return
     * @throws IOException
     */
    public static List<CalendarListEntry> getCalendars() throws IOException {
        CalendarList calendarList = service.calendarList().list().execute();
        List<CalendarListEntry> items = calendarList.getItems();
        return items;
    }

    /**
     * This method creates a new secondary calendar in Google Calendar account.
     *
     * @param name
     * @throws IOException
     * @throws GeneralSecurityException
     */
    public static void createCalendar(String name) throws IOException, GeneralSecurityException {
        com.google.api.services.calendar.model.Calendar calendar = new com.google.api.services.calendar.model.Calendar();
        calendar.setSummary(name);
        calendar.setTimeZone("Europe/Helsinki");

        // Insert the new calendar
        com.google.api.services.calendar.model.Calendar createdCalendar = service.calendars().insert(calendar).execute();
    }

    public static String getPrimaryCalendarId() throws IOException {
        com.google.api.services.calendar.model.Calendar calendar = service.calendars().get("primary").execute();
        return calendar.getId();
    }

}
