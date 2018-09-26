package views;

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

    public RegisterView() {

    }
    Label fname = new Label("* Etunimi: ");
    TextField fnameField = new TextField();

    Label lname = new Label("* Sukunimi: ");
    TextField lnameField = new TextField();

    Label uname = new Label("* Käyttäjätunnus: ");
    TextField unameField = new TextField();

    Label gmail = new Label("Gmail-tili (vaihtoehtoinen)");
    TextField gmailField = new TextField();

    Label pword1 = new Label("* Salasana: ");
    PasswordField pword1Field = new PasswordField();

    Label pword2 = new Label("* Salasana uudelleen: ");
    PasswordField pword2Field = new PasswordField();

    Button create = new Button("Luo käyttäjä");
    Button back = new Button("Palaa");

    public Parent getView() {

        // ruudukko asettelua varten
        GridPane grid = new GridPane();

        // välit ja positiot
        grid.setAlignment(Pos.CENTER);
        grid.setVgap(10);
        grid.setHgap(10);
        grid.setPadding(new Insets(10, 10, 10, 10));

        // tekstikentät
        Label required = new Label("* Tähdillä merkityt - pakolliset");

        // välit
        grid.setPadding(new Insets(10, 10, 10, 10));

        // asetetaan ne ruudukkoon
        grid.addRow(0, fname, fnameField);
        grid.addRow(1, lname, lnameField);
        grid.addRow(2, uname, unameField);
        grid.addRow(3, gmail, gmailField);
        grid.addRow(4, pword1, pword1Field);
        grid.addRow(5, pword2, pword2Field);
        grid.add(required, 0, 6);
        grid.addRow(7, create, back);

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

    public String getGmail() {
        return gmailField.getText();
    }

}
