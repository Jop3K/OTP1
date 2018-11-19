package views;

import java.util.Locale;
import java.util.ResourceBundle;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

public class RegisterView {
    private ResourceBundle labels;
    private ResourceBundle buttons;

    private Label fname;
    private TextField fnameField;
    private Label lname;
    private TextField lnameField;
    private Label uname;
    private TextField unameField;
    private Label pword1;
    private PasswordField pword1Field;
    private Label pword2;
    private PasswordField pword2Field;
    private Button create;
    private Button back;
    private Label required;

    public RegisterView() {
        labels = ResourceBundle.getBundle("LabelsBundle");
        buttons = ResourceBundle.getBundle("ButtonLabelsBundle");
        
        fname = new Label();
        fname.setText(labels.getString("firstname") + ":");
        fnameField = new TextField();

        lname = new Label();
        lname.setText(labels.getString("lastname") + ":");
        lnameField = new TextField();

        uname = new Label();
        uname.setText(labels.getString("username") + ":");
        unameField = new TextField();

        pword1 = new Label();
        pword1.setText(labels.getString("password") + ":");
        pword1Field = new PasswordField();

        pword2 = new Label();
        pword2.setText(labels.getString("passwordAgain") + ":");
        pword2Field = new PasswordField();

        create = new Button();
        create.setText(buttons.getString("createUser"));
        back = new Button();
        back.setText(buttons.getString("back"));
        
        required = new Label();
        required.setText(labels.getString("required"));
    }

    public Parent getView() {

        // ruudukko asettelua varten
        GridPane grid = new GridPane();

        // välit ja positiot
        grid.setAlignment(Pos.CENTER);
        grid.setVgap(10);
        grid.setHgap(10);
        grid.setPadding(new Insets(10, 10, 10, 10));

        // välit
        grid.setPadding(new Insets(10, 10, 10, 10));

        // asetetaan ne ruudukkoon
        grid.addRow(0, fname, fnameField);
        grid.addRow(1, lname, lnameField);
        grid.addRow(2, uname, unameField);
        grid.addRow(3, pword1, pword1Field);
        grid.addRow(4, pword2, pword2Field);
        grid.add(required, 0, 5);
        grid.addRow(6, create, back);

        // muokkaan nappien asettelua
        GridPane.setMargin(required, new Insets(20, 0, 0, 0));
        GridPane.setMargin(create, new Insets(20, 0, 0, 50));
        GridPane.setMargin(back, new Insets(20, 0, 0, 50));

        return grid; // Palautetaan ruudukkoolio

    }

    public void addBackButtonEventHandler(EventHandler event) {
        back.setOnAction(event);
    }

    public void addCreateButtonEventHandler(EventHandler event) {
        create.setOnAction(event);
    }

    public void showAlert(Alert alert) {
        alert.showAndWait();
    }

    public String getFname() {
        return fnameField.getText();
    }

    public String getLname() {
        return lnameField.getText();
    }

    public String getPword1() {
        return pword1Field.getText();
    }

    public String getPword2() {
        return pword2Field.getText();
    }

    public String getUname() {
        return unameField.getText();
    }

}
