package controllers;

import com.google.api.services.calendar.model.CalendarListEntry;
import com.sun.javafx.scene.control.skin.DatePickerContent;
import dataAccessObjects.UserDAO;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Callback;
import javafx.util.StringConverter;
import models.EventModel;
import models.EventObservableDataList;
import models.WorkProfile;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.security.GeneralSecurityException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import static controllers.ControllerUtil.isDayCell;

/**
 * This is the controller for the "Calendar Tab" -View
 *
 * @author Joni, Artur, Joonas
 *
 */
public class CalendarViewController implements Initializable {

    @FXML
    private Color x2;
    @FXML
    private Font x1;

    @FXML
    private DatePicker dateStart;
    @FXML
    private DatePicker dateEnd;
    @FXML
    private Color x21;
    @FXML
    private Font x11;

    @FXML
    private ComboBox<?> hourStart;
    @FXML
    private ComboBox<?> minuteStart;
    @FXML
    private ComboBox<?> hourEnd;
    @FXML
    private ComboBox<?> minuteEnd;
    @FXML
    public ComboBox<WorkProfile> profileChooser;
    @FXML
    private DatePicker startDay;
    @FXML
    private DatePicker endDay;

    @FXML
    private TableView<EventModel> eventTable;
    @FXML
    private DatePicker eventDatePicker;

    // Variables for eventDatePicker
    private LocalDate rangeStart;
    private LocalDate rangeEnd;
    private DateCell startCell;
    private DateCell endCell;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d.MM.uuuu", Locale.ENGLISH);

    @FXML
    private TableColumn<EventModel, Date> endColumn;

    private EventModel eventModel;

    // elements for localization below:
    private ResourceBundle labels;
    private ResourceBundle buttons;
    private ResourceBundle messages;

    @FXML
    private Label events;
    @FXML
    private Label chooseDate1;
    @FXML
    private Label chooseDate2;
    @FXML
    private Label startDate;
    @FXML
    private Label endDate;
    @FXML
    private Label timeFrame;
    @FXML
    private Label hoursStart;
    @FXML
    private Label minutesStart;
    @FXML
    private Label hoursEnd;
    @FXML
    private Label minutesEnd;
    @FXML
    private Label createEvent;
    @FXML
    private Label workProfile;
    @FXML
    private Label eventsFound;
    @FXML
    private ComboBox<String> startHour;
    @FXML
    private ComboBox<String> endHour;
    @FXML
    private ComboBox<String> startMinute;
    @FXML
    private ComboBox<String> endMinute;
    @FXML
    private Button connectToGoogle;
    @FXML
    private Button saveButton;
    @FXML
    private Button clearEventsBtn;
    @FXML
    private Button cancelEventEditBtn;
    @FXML
    private TableColumn<EventModel, String> workProfileColumn;
    @FXML
    private TableColumn<EventModel, String> descriptionColumn;
    @FXML
    private TableColumn<EventModel, Date> startColumn;
    @FXML
    private Label eventCountLabel;
    @FXML
    private Button disconnectGoogle;
    @FXML
    private Text connection;
    @FXML
    private ComboBox<CalendarListEntry> googleCalendarsDropdown;
    @FXML
    private Button sendToGoogleButton;
    @FXML
    private Label descriptionLabel;
    @FXML
    private TextField descriptionTextField;
    @FXML
    private Text chooseCalendarText;
    @FXML
    private Text createCalendarText;
    @FXML
    private TextField newGoogleCalendarField;
    @FXML
    private Button createNewCalendarButton;
    @FXML
    private Button setDefaultCalendarButton;

    /**
     * The constructor for "eventModel"
     */
    public CalendarViewController() {

        eventModel = new EventModel();
    }

    /**
     * (non-Javadoc)
     *
     * @see javafx.fxml.Initializable#initialize(java.net.URL,
     * java.util.ResourceBundle)
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // loading resources

        labels = ResourceBundle.getBundle("LabelsBundle");
        buttons = ResourceBundle.getBundle("ButtonLabelsBundle");
        messages = ResourceBundle.getBundle("MessagesBundle");

        setLabels();
        setButtons();

        CurrentCalendarViewController.setCalendarViewController(this);

        //Tooltip test (does not work yet)
        final Tooltip tooltipHour = new Tooltip();
        final Tooltip tooltipMinute = new Tooltip();

        tooltipHour.setText("0-23");
        tooltipMinute.setText("0-59");

        startHour.setTooltip(tooltipHour);
        endHour.setTooltip(tooltipHour);
        startMinute.setTooltip(tooltipMinute);
        endMinute.setTooltip(tooltipMinute);

        //Täytetään taulu
        setTable();

        // Täytetään comboboxit
        generateTimeToComboboxes();

        loadWorkProfilesToProfileChooser();

        if (GoogleCalendar.isConnected()) {
            connection.setText(labels.getString("connected"));
            connection.setFill(Color.GREEN);
        } else {
            connection.setText(labels.getString("disconnected"));
            connection.setFill(Color.RED);
        }

        formatDatePickerForRangeSelect();

    }

    /**
     * The method we use for connecting to google's calendar
     *
     * @throws IOException error handling for google calendar
     * @throws GeneralSecurityException error handling for google calendar
     */
    @FXML
    public void connectToGoogle() throws IOException, GeneralSecurityException {
        try {
            GoogleCalendar.connect();
            connection.setText(labels.getString("connected"));
            connection.setFill(Color.GREEN);
            loadGoogleCalendarsToCombobox();
            for (CalendarListEntry c : googleCalendarsDropdown.getItems()) {
                if (c.getId().equals(models.CurrentUserRefactored.INSTANCE.getUser().getDefaultGoogleCalendarId())) {
                    googleCalendarsDropdown.getSelectionModel().select(c);
                } else {
                    if (c.isPrimary()) {
                        googleCalendarsDropdown.getSelectionModel().select(c);
                    }
                }
            }
        } catch (IOException ex) {
            System.out.println(labels.getString("error"));
        }
    }

    /**
     * onClick method for disconnecting google account
     * @throws IOException
     * @throws GeneralSecurityException
     */
    @FXML
    public void disconnectFromGoogle() throws IOException, GeneralSecurityException {
        GoogleCalendar.disconnect();
        connection.setText(labels.getString("disconnected"));
        connection.setFill(Color.RED);
        googleCalendarsDropdown.getItems().clear();
    }

    /**
     * Load google calendars from the active account to combobox
     * @throws IOException
     */
    public void loadGoogleCalendarsToCombobox() throws IOException {
        googleCalendarsDropdown.getItems().clear();

        for (CalendarListEntry c : GoogleCalendar.getCalendars()) {
            googleCalendarsDropdown.getItems().add(c);
        }

        googleCalendarsDropdown.setCellFactory(
                new Callback<ListView<CalendarListEntry>, ListCell<CalendarListEntry>>() {
            @Override
            public ListCell<CalendarListEntry> call(ListView<CalendarListEntry> w) {
                ListCell cell = new ListCell<CalendarListEntry>() {
                    @Override
                    protected void updateItem(CalendarListEntry item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setText("");
                        } else {
                            setText(item.getSummary());
                        }
                    }
                };
                return cell;
            }
        });

        googleCalendarsDropdown.setConverter(
                new StringConverter<CalendarListEntry>() {
            private Map<String, CalendarListEntry> map = new HashMap<>();

            @Override
            public String toString(CalendarListEntry w) {
                if (w != null) {
                    String str = w.getSummary();
                    map.put(w.getSummary(), w);
                    return str;
                } else {
                    return "";
                }

            }

            @Override
            public CalendarListEntry fromString(String string) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        });
    }

    /**
     * Method for creating a new calendar in the active google calendar account
     * @throws IOException
     * @throws GeneralSecurityException
     */
    @FXML
    public void createGoogleCalendar() throws IOException, GeneralSecurityException {
        if (!newGoogleCalendarField.getText().isEmpty()) {
            GoogleCalendar.createCalendar(newGoogleCalendarField.getText());
            loadGoogleCalendarsToCombobox();
        }
    }

 /**
 * For Localizing CalendarView labels using resourceBundles
 */
    public void setLabels() {
        events.setText(labels.getString("events"));
        chooseDate1.setText(labels.getString("chooseDate"));
        chooseDate2.setText(labels.getString("chooseDate"));
        startDate.setText(labels.getString("start"));
        endDate.setText(labels.getString("end"));
        timeFrame.setText(labels.getString("timeFrame"));
        hoursStart.setText(labels.getString("hour"));
        minutesStart.setText(labels.getString("minute"));
        hoursEnd.setText(labels.getString("hour"));
        minutesEnd.setText(labels.getString("minute"));
        createEvent.setText(labels.getString("createEvent"));
        workProfile.setText(labels.getString("workProfile") + " :");
        eventsFound.setText(labels.getString("eventsFound") + " :" );
        workProfileColumn.setText(labels.getString("workProfile"));
        descriptionColumn.setText(labels.getString("description"));
        startColumn.setText(labels.getString("starts"));
        endColumn.setText(labels.getString("ends"));
        startHour.promptTextProperty().setValue(labels.getString("h"));
        startMinute.promptTextProperty().setValue(labels.getString("m"));
        endHour.promptTextProperty().setValue(labels.getString("h"));
        endMinute.promptTextProperty().setValue(labels.getString("m"));
        descriptionLabel.setText(labels.getString("description"));
        chooseCalendarText.setText(labels.getString("chooseCalendar"));
        createCalendarText.setText(labels.getString("createCalendar"));
    }

    /**
     * For Localizing CalendarView buttons using resourceBundles
     */
    public void setButtons() {
        saveButton.setText(buttons.getString("save"));
        cancelEventEditBtn.setText(buttons.getString("cancel"));
        createNewCalendarButton.setText(buttons.getString("create"));
        sendToGoogleButton.setText(buttons.getString("sendEvent"));
        connectToGoogle.setText(buttons.getString("connectToGoogle"));
        disconnectGoogle.setText(buttons.getString("disconnectFromGoogle"));
        setDefaultCalendarButton.setText(buttons.getString("makeDefault"));
    }

    /**
     * This method is for generating the numbers for the comboboxes with a
     * simple loop
     */
    public void generateTimeToComboboxes() {
        for (int i = 0; i < 60; i++) {
            if (i < 10) {
                String tmp = "0" + i;
                startMinute.getItems().add(tmp);
                endMinute.getItems().add(tmp);

                startHour.getItems().add(tmp);
                endHour.getItems().add(tmp);

            } else {
                if (i < 24) {
                    startHour.getItems().add(Integer.toString(i));
                    endHour.getItems().add(Integer.toString(i));
                }
                startMinute.getItems().add(Integer.toString(i));
                endMinute.getItems().add(Integer.toString(i));
            }

        }
    }

    /**
     * We use this method for validating the creation of a workshift event
     *
     * @return we return the boolean value for the validation
     */
    boolean isValid() {
        return startDay.getValue() != null && endDay.getValue() != null
                && !startHour.getSelectionModel().isEmpty() && !endHour.getSelectionModel().isEmpty()
                && !startMinute.getSelectionModel().isEmpty() && !endMinute.getSelectionModel().isEmpty()
                && !profileChooser.getSelectionModel().isEmpty();
        //startDay != null || endDay != null || startHour != null || endHour != null || startMinute != null || endMinute != null || profileChooser != null;
    }

    /**
     * The method for saving an event when pressing "Save" button
     *
     * @param e ActionEvent parameter that is required when creating an
     * ActionEvent
     * @throws IOException error handling for saving an event
     */
    public void saveEvent(ActionEvent e) throws IOException {
        LocalDate startDate = startDay.getValue();
        LocalDate endDate = endDay.getValue();

        try {
            if (!isValid()) {

                JOptionPane.showMessageDialog(null, messages.getString("fillAllFields"));
                return;
            }

            if (startDate.isBefore(endDate)) {
                eventModel.setBeginDay(startDay.getValue());
                eventModel.setEndDay(endDay.getValue());

            } else if (startDate.isAfter(endDate)) {
                JOptionPane.showMessageDialog(null, messages.getString("checkDay"));
                return;
            } else if (startDate.equals(endDate)) { // jos aloitus pvm ja lopetus pvm sama niin...
                eventModel.setBeginDay(startDay.getValue());
                eventModel.setEndDay(endDay.getValue());

                if (Integer.parseInt(startHour.getValue()) > Integer.parseInt(endHour.getValue())) { // jos aloitusaika on isompi kuin lopetusaika
                    JOptionPane.showMessageDialog(null, messages.getString("checkHours"));
                    return;
                } else if (Integer.parseInt(startHour.getValue()) == Integer.parseInt(endHour.getValue())) { // jos aloitustunti on sama kuin lopetustunti
                    if (Integer.parseInt(startMinute.getValue()) >= Integer.parseInt(endMinute.getValue())) {
                        JOptionPane.showMessageDialog(null, messages.getString("checkMinutes"));
                        return;
                    }
                }
            }

            eventModel.setDescription(descriptionTextField.getText());

            eventModel.setBeginHour(startHour.getValue());
            eventModel.setBeginMinute(startMinute.getValue());
            eventModel.setEndHour(endHour.getValue());
            eventModel.setEndMinute(endMinute.getValue());
            eventModel.setBeginDateTime(startDay.getValue());
            eventModel.setEndDateTime(endDay.getValue());
            // Etsitään oikea workprofile-olio eventtiin

            eventModel.setWorkProfile(profileChooser.getSelectionModel().getSelectedItem());

        } catch (Exception err) {
            JOptionPane.showMessageDialog(null, messages.getString("fillAllFields"));
            return;
        }

        if (isValid()) {

            if (eventModel.getId() == 0) {
                System.out.print(eventModel.getId());
                // Lasketaan palkka ennen tallennusta
                eventModel.calcPay();
                UserDAO.save(eventModel);
                EventObservableDataList.getInstance().add(eventModel);
                clearChoices();
                JOptionPane.showMessageDialog(null, "Luonti onnistui!");
                eventCountLabel.setText(Integer.toString(EventObservableDataList.getInstance().size()));
            } else {
                // Lasketaan palkka ennen tallennusta
                eventModel.calcPay();
                UserDAO.update(eventModel);
                eventTable.getColumns().get(0).setVisible(false); //Workaround for fireing changelistener in observablelist(updates object to table)
                eventTable.getColumns().get(0).setVisible(true);
                clearChoices();
            }

            eventModel = new EventModel();

        } else {
            JOptionPane.showMessageDialog(null, "Täytä kaikki kentät ennen tapahtuman luomista");
        }

    }

    /**
     * We use this method to get the workprofiles to the Dropdown menu
     *
     */
    public void loadWorkProfilesToProfileChooser() {

        profileChooser.getItems().clear();

        for (WorkProfile w : models.CurrentUserRefactored.INSTANCE.getWorkProfiles()) {
            profileChooser.getItems().add(w);
        }

    }

    /**
     * Method for sending selected events to active google account's calendar
     * @throws IOException
     */
    @FXML
    public void sendToGoogle() throws IOException {
        if (GoogleCalendar.isConnected() && eventTable.getSelectionModel().getSelectedItems() != null) {

            String calendarId;

            if (googleCalendarsDropdown.getSelectionModel().getSelectedItem() != null) {
                calendarId = googleCalendarsDropdown.getSelectionModel().getSelectedItem().getId();
            } else {
                calendarId = null;
            }
            System.out.println(calendarId);
            for (EventModel e : eventTable.getSelectionModel().getSelectedItems()) {

                GoogleCalendar.sendSelectedEventToGoogleCalendar(e, calendarId);

            }

        } else {
            System.out.println("Not Connected");
        }
    }

    /**
     * Method for choosing a default calendar on the active google account
     */
    @FXML
    public void setDefaultCalendar() {
        if (GoogleCalendar.isConnected()) {
            if (googleCalendarsDropdown.getSelectionModel().getSelectedItem() != null) {
                models.CurrentUserRefactored.INSTANCE.getUser().setDefaultGoogleCalendarId(googleCalendarsDropdown.getSelectionModel().getSelectedItem().getId());
                UserDAO.update(models.CurrentUserRefactored.INSTANCE.getUser());
            }
        }
    }

    /**
     * This method is for getting the events from the
     * EventObservableDataListbase to the table on the Calendar Tab
     */
    public void setTable() {

        eventCountLabel.setText(Integer.toString(EventObservableDataList.getInstance().size()));

        workProfileColumn.setCellValueFactory(new PropertyValueFactory<EventModel, String>("workProfile"));
        workProfileColumn.setResizable(true);
        //Formatoidaan "alkaa" kolumni näyttämää päivämäärän dd.mm.yy hh:mm muodossa
        ControllerUtil.formatColumnDate(startColumn);
        //Formatoidaan "loppuu" kolumni myös samaan muotoon
        ControllerUtil.formatColumnDate(endColumn);
        startColumn.setCellValueFactory(new PropertyValueFactory<EventModel, Date>("beginTime"));
        startColumn.setResizable(true);
        endColumn.setCellValueFactory(new PropertyValueFactory<EventModel, Date>("endTime"));
        endColumn.setResizable(true);
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<EventModel, String>("description"));
        descriptionColumn.setResizable(true);

        // Adds menu for editing and deleting objects from eventTable. Fired by mouses right-click
        MenuItem info = new MenuItem(labels.getString("info"));
        MenuItem edit = new MenuItem(labels.getString("edit"));
        MenuItem delete = new MenuItem(labels.getString("delete"));
        MenuItem sendToGoogle = new MenuItem(labels.getString("sendToGoogle"));

        // Showing useful information to user
        info.setOnAction((ActionEvent event) -> {

            EventModel tmp = eventTable.getSelectionModel().getSelectedItem();

            JOptionPane.showMessageDialog(null, labels.getString("eventDuration") + ": " + tmp.getHours() + " " + labels.getString("hoursPartitiivi")
                    + "\n" + labels.getString("pay") + ": " + tmp.getEventPay() + " " + labels.getString("eur") + "\n"
                    + labels.getString("description") + ": " + tmp.getDescription(),
                    labels.getString("eventInfo"), JOptionPane.INFORMATION_MESSAGE);

        });
        edit.setOnAction((ActionEvent event) -> {

            cancelEventEditBtn.setDisable(false);

            eventModel = eventTable.getSelectionModel().getSelectedItem();
            System.out.print(eventModel.getId());
            startHour.setValue(eventModel.getBeginHour());
            endHour.setValue(eventModel.getEndHour());
            startMinute.setValue(eventModel.getBeginMinute());
            endMinute.setValue(eventModel.getEndMinute());
            startDay.setValue(eventModel.getBeginDay());
            endDay.setValue(eventModel.getEndDay());
            profileChooser.setValue(eventModel.getWorkProfile());
            descriptionTextField.setText(eventModel.getDescription());
            googleCalendarsDropdown.getSelectionModel().clearSelection();
            if (GoogleCalendar.isConnected()) {
                for (CalendarListEntry c : googleCalendarsDropdown.getItems()) {
                    if (c.getId().equals(eventModel.getGoogleCalendarId())) {
                        googleCalendarsDropdown.getSelectionModel().select(c);
                    }
                }
            }

        });
        delete.setOnAction((ActionEvent event) -> {
            for (EventModel e : eventTable.getSelectionModel().getSelectedItems()) {
                EventObservableDataList.getInstance().remove(e);
                UserDAO.delete(e);
            }
            eventCountLabel.setText(Integer.toString(EventObservableDataList.getInstance().size()));
        });

        sendToGoogle.setOnAction((ActionEvent event) -> {
            if (GoogleCalendar.isConnected()) {
                try {
                    sendToGoogle();
                } catch (IOException ex) {
                    Logger.getLogger(CalendarViewController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

        // Palkanlaskennan testausta varten
        eventTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            EventModel event = eventTable.getSelectionModel().getSelectedItem();
            System.out.println("Palkka: " + event.getEventPay() + "€" + " Tunnit: " + event.getHours());
        });

        ContextMenu menu = new ContextMenu();
        menu.getItems().add(info);
        menu.getItems().add(edit);
        menu.getItems().add(delete);
        menu.getItems().add(sendToGoogle);
        eventTable.setContextMenu(menu);

        //Add items to table
        eventTable.setItems(EventObservableDataList.getInstance());
        eventTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    }

    /**
     * We use this method to clear the values on the event creation fields
     */
    public void clearChoices() {
        startDay.setValue(null);
        endDay.setValue(null);
        startHour.setValue(null);
        startMinute.setValue(null);
        endMinute.setValue(null);
        endHour.setValue(null);
        profileChooser.setValue(null);
    }

    /**
     * This method is for clearing the Event search with DatePicker field when
     * the ClearEvents button is pressed
     *
     * @param e a required parameter for defining an ActionEvent
     */
    public void clearEventDatePicker(ActionEvent e) {

        rangeStart = null;
        rangeEnd = null;
        eventDatePicker.setValue(null);
        eventTable.setItems(EventObservableDataList.getInstance());
        clearEventsBtn.setDisable(true);
    }

    /**
     * Canceling the editing of an event when pressing the "Cancel" button
     *
     * @param e a required parameter for defining an ActionEvent
     */
    public void cancelEventEdit(ActionEvent e) {

        eventModel = null;
        clearChoices();

        cancelEventEditBtn.setDisable(true);
    }

    /**
     * Method for formatting DatePicker for selected dates
     */
    public void formatDatePickerForRangeSelect() {

        eventDatePicker.setConverter(new StringConverter<LocalDate>() {

            @Override
            public String toString(LocalDate object) {

                if (object == null) {
                    return null;
                }

                if (rangeStart != null && rangeStart.equals(rangeEnd)) {
                    return rangeStart.format(formatter);
                } else if (rangeStart != null) {
                    return rangeStart.format(formatter) + " - " + rangeEnd.format(formatter);
                }

                return object.format(formatter);
            }

            @Override
            public LocalDate fromString(String string) {
                if (string.contains("-")) {
                    try {
                        rangeStart = LocalDate.parse(string.split("-")[0].trim(), formatter);
                        rangeEnd = LocalDate.parse(string.split("-")[1].trim(), formatter);
                    } catch (DateTimeParseException dte) {
                        return LocalDate.parse(string, formatter);
                    }
                    return rangeStart;
                }
                return LocalDate.parse(string, formatter);
            }
        });

        eventDatePicker.showingProperty().addListener((obs, b, b1) -> {

            if (b1) {

                DatePickerContent content = ControllerUtil.getDatePickerContent(eventDatePicker);
                List<DateCell> cells = ControllerUtil.getDatePickerDateCells(content);

                if (rangeStart != null && rangeEnd != null) {
                    ControllerUtil.formatRangeToSelected(cells, rangeStart, rangeEnd);
                }

                startCell = null;
                endCell = null;
                content.setOnMouseDragged(e -> {

                    Node n = e.getPickResult().getIntersectedNode();
                    DateCell c = ControllerUtil.extractDateCellFromNode(n);

                    if (isDayCell(c)) {
                        if (startCell == null) {
                            startCell = c;
                        }
                        endCell = c;
                    }

                    if (startCell != null && endCell != null) {

                        LocalDate start = ControllerUtil.extractEarlierDate(startCell, endCell);
                        LocalDate end = ControllerUtil.extractLaterDate(startCell, endCell);

                        ControllerUtil.formatRangeToSelected(cells, start, end);
                    }
                });
                content.setOnMouseReleased(e -> {

                    if (startCell != null && endCell != null) {

                        rangeStart = ControllerUtil.extractEarlierDate(startCell, endCell);
                        rangeEnd = ControllerUtil.extractLaterDate(startCell, endCell);
                        eventDatePicker.setValue(rangeStart);
                        ControllerUtil.formatRangeToSelected(cells, rangeStart, rangeEnd);

                        endCell = null;
                        startCell = null;

                    } else {
                        Node n = e.getPickResult().getIntersectedNode();
                        DateCell c = ControllerUtil.extractDateCellFromNode(n);
                        rangeStart = c.getItem();
                        rangeEnd = c.getItem();
                    }

                    eventTable.setItems(filterTableData());

                });
            }
        });

    }

    /**
     * Filters table event list to only include events in the given date range
     * 
     * @return filtered table data
     */
    public FilteredList<EventModel> filterTableData() {

        FilteredList<EventModel> filteredData = new FilteredList<>(EventObservableDataList.getInstance(), p -> true);

        filteredData.setPredicate(EventModel -> {

            LocalDate eventDate = EventModel.getBeginTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

            return ControllerUtil.filterRangeDateSelection(eventDate, rangeStart, rangeEnd);

        });

        clearEventsBtn.setDisable(false);
        eventCountLabel.setText(Integer.toString(filteredData.size()));

        return filteredData;

    }
}
