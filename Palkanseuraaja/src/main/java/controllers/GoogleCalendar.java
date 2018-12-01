package controllers;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.DateTime;
import com.google.api.client.util.store.DataStore;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.calendar.CalendarScopes;
import com.google.api.services.calendar.model.CalendarList;
import com.google.api.services.calendar.model.CalendarListEntry;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventDateTime;
import com.google.api.services.calendar.model.Events;
import dataAccessObjects.UserDAO;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import models.CurrentUser;
import models.EventModel;

public class GoogleCalendar {

    private static final String APPLICATION_NAME = "Palkanseuraaja";
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    private static final String pathToTokens = "tokens/" + CurrentUser.getUser().getUsername();
    private static final String TOKENS_DIRECTORY_PATH = pathToTokens;

    /**
     * Global instance of the scopes required by this quickstart. If modifying
     * these scopes, delete your previously saved tokens/ folder.
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

    public static void main(String... args) throws IOException, GeneralSecurityException {
        // Build a new authorized API client service.
        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        service = new com.google.api.services.calendar.Calendar.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT))
                .setApplicationName(APPLICATION_NAME)
                .build();

    }

    public static void getNextTenEvents() throws IOException {
        // List the next 10 events from the primary calendar.
        DateTime now = new DateTime(System.currentTimeMillis());
        Events events = service.events().list("primary")
                .setMaxResults(10)
                .setTimeMin(now)
                .setOrderBy("startTime")
                .setSingleEvents(true)
                .execute();
        List<Event> items = events.getItems();
        if (items.isEmpty()) {
            System.out.println("No upcoming events found.");
        } else {
            System.out.println("Upcoming events");
            for (Event event : items) {
                DateTime start = event.getStart().getDateTime();
                if (start == null) {
                    start = event.getStart().getDate();
                }
                System.out.printf("%s (%s)\n", event.getSummary(), start);
            }
        }
    }

    public static void sendSelectedEventToGoogleCalendar(EventModel eventModel, String calendarId) throws IOException {
        //TODO
        // Refer to the Java quickstart on how to setup the environment:
// https://developers.google.com/calendar/quickstart/java
// Change the scope to CalendarScopes.CALENDAR and delete any stored
// credentials.

        if (eventModel.getGoogleId() == null) {

            Event event = new Event()
                    .setSummary(eventModel.getWorkProfile().getName())
                    .setDescription(eventModel.getDescription());

            DateTime startDateTime = new DateTime(eventModel.getBeginDateTime());
            EventDateTime start = new EventDateTime()
                    .setDateTime(startDateTime)
                    .setTimeZone("Europe/Helsinki");
            event.setStart(start);

            DateTime endDateTime = new DateTime(eventModel.getEndDateTime());
            EventDateTime end = new EventDateTime()
                    .setDateTime(endDateTime)
                    .setTimeZone("Europe/Helsinki");
            event.setEnd(end);

            event = service.events().insert(calendarId, event).execute();
            System.out.printf("Event created: %s\n", event.getHtmlLink());

            eventModel.setGoogleId(event.getId());
            System.out.println(eventModel.getGoogleId());
            UserDAO dao = new UserDAO();
            dao.update(eventModel);
            System.out.println(event.getSummary());

        } else {
            updateSelectedEvent(eventModel, calendarId);
        }

    }

    public static void updateSelectedEvent(EventModel eventModel, String calendarId) throws IOException {

        System.out.println(eventModel.getGoogleId());
        // Retrieve the event from the API
        Event event = service.events().get(calendarId, eventModel.getGoogleId()).execute();

        // Make a change
        DateTime startDateTime = new DateTime(eventModel.getBeginDateTime());
        EventDateTime start = new EventDateTime()
                .setDateTime(startDateTime)
                .setTimeZone("Europe/Helsinki");
        event.setStart(start);

        DateTime endDateTime = new DateTime(eventModel.getEndDateTime());
        EventDateTime end = new EventDateTime()
                .setDateTime(endDateTime)
                .setTimeZone("Europe/Helsinki");
        event.setEnd(end);

        // Update the event
        Event updatedEvent = service.events().update(calendarId, event.getId(), event).execute();

    }

    public static void disconnect() throws IOException {
        try {
            flow.getCredentialDataStore().delete("user");
        } catch (Exception e) {
            System.out.println("Not Connected");
        }
    }

    public static boolean isConnected() {
        if (service != null) {
            return true;
        }
        return false;
    }

    public static List<CalendarListEntry> getCalendars() throws IOException {
        CalendarList calendarList = service.calendarList().list().execute();
        List<CalendarListEntry> items = calendarList.getItems();
        return items;
    }

    public static void createCalendar(String name) throws IOException, GeneralSecurityException {
        com.google.api.services.calendar.model.Calendar calendar = new com.google.api.services.calendar.model.Calendar();
        calendar.setSummary(name);
        calendar.setTimeZone("Europe/Helsinki");

        // Insert the new calendar
        com.google.api.services.calendar.model.Calendar createdCalendar = service.calendars().insert(calendar).execute();

        System.out.println(createdCalendar.getId());
    }
}
