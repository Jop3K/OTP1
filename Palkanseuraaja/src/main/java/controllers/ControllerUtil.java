package controllers;

import com.sun.javafx.scene.control.skin.DatePickerContent;
import com.sun.javafx.scene.control.skin.DatePickerSkin;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import models.EventModel;
import net.bytebuddy.asm.Advice;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Class for shared controller functionality or any controller functionality that can be made static. Made to avoid duplicate code.
 */
public class ControllerUtil {

    /**
     *
     * Format TableColumn to dd.mm.yyyy HH.mm format
     *
     */
    public static void formatColumnDate(TableColumn<EventModel, Date> tableColumn) {
        tableColumn.setCellFactory(column -> {
            TableCell<EventModel, Date> cell = new TableCell<EventModel, Date>() {
                private SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy HH:mm");

                @Override
                protected void updateItem(Date item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setText(null);
                    } else {
                        this.setText(format.format(item));

                    }
                }
            };

            return cell;
        });
    }

    /**
     *
     * Extracts DatePickerContent object from DatePicker
     *
     *
     * @param datePicker DatePicker to extract
     * @return DatePickerContent for the given DatePicker
     */
    public static DatePickerContent getDatePickerContent(DatePicker datePicker) {
        return (DatePickerContent)((DatePickerSkin)datePicker.getSkin()).getPopupContent();
    }

    /**
     *
     * Extracts a list of DateCells from DatePickerContent object
     *
     * @param content DatePickerContent to extract
     * @return list of DateCells
     */
    public static List<DateCell> getDatePickerDateCells(DatePickerContent content) {
        return content.lookupAll(".day-cell").stream()
                .map(n->(DateCell)n)
                .collect(Collectors.toList());
    }

    /**
     * Method for selecting multiple dates from DatePicker
     * @param cells one date selected is one cell
     * @param start where selecting starts
     * @param end where selecting ends
     */
    public static void formatRangeToSelected(List<DateCell> cells, LocalDate start, LocalDate end) {

        // Set style of cells in range to selected
        cells.forEach(ce -> ce.getStyleClass().remove("selected"));
        cells.stream()
                .filter(ce -> ce.getItem().equals(start) || ce.getItem().isAfter(start))
                .filter(ce -> ce.getItem().equals(end) || ce.getItem().isBefore(end))
                .forEach(ce -> ce.getStyleClass().add("selected"));
    }

    /**
     * Checking if current cell is a DayCell
     * @param c DateCell to check
     * @return true if is a day-cell (has day-cell style class), false otherwise
     */
    public static boolean isDayCell(DateCell c) {
        return c != null && c.getStyleClass().contains("day-cell");
    }

    /**
     * Extracts LocalDate from the DateCell that comes earlier in time
     *
     * @param c1 DateCell to compare to c2
     * @param c2 DateCell to compare to c1
     * @return LocalDate of the earlier DateCell
     */
    public static LocalDate extractEarlierDate(DateCell c1, DateCell c2) {
        if(c1.getItem().isBefore(c2.getItem())) {
            return c1.getItem();
        } else {
            return c2.getItem();
        }
    }

    /**
     * Extracts LocalDate from the DateCell that comes later in time
     *
     * @param c1 DateCell to compare to c2
     * @param c2 DateCell to compare to c1
     * @return LocalDate of the later DateCell
     */
    public static LocalDate extractLaterDate(DateCell c1, DateCell c2) {
        if(c1.getItem().isBefore(c2.getItem())) {
            return c2.getItem();
        } else {
            return c1.getItem();
        }
    }

    public static boolean filterSingleDateSelection(LocalDate eventDate, LocalDate selectedDate) {
        return eventDate.equals(selectedDate);
    }

    /**
     * Checks if eventDate is inside date range between rangeStart and rangEnd
     *
     * @param eventDate date of the event to check
     * @param rangeStart start of the date range
     * @param rangeEnd end of the date range
     * @return true if is inside date range, false otherwise
     */
    public static boolean filterRangeDateSelection(LocalDate eventDate, LocalDate rangeStart, LocalDate rangeEnd) {
        return (eventDate.equals(rangeStart) || eventDate.isAfter(rangeStart)) && (eventDate.equals(rangeEnd) || eventDate.isBefore(rangeEnd));
    }

    public static DateCell extractDateCellFromNode(Node n) {
        DateCell c = null;
        if (n instanceof DateCell) {
            c = (DateCell) n;
        } else if (n instanceof Text) {
            // If event node was Text inside DateCell, get parent instead
            c = (DateCell) (n.getParent());
        }

        return c;
    }

}
