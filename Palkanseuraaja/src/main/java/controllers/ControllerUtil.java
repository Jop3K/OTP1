package controllers;

import javafx.scene.control.*;
import models.EventModel;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Class for shared controller functionality or any controller functionality that can be made static. Made to avoid duplicate code.
 */
public class ControllerUtil {

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

}
