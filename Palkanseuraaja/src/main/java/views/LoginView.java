package views;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import models.ViewManager;

public class LoginView {

    // tekstikentät
    private Label username;
    private TextField usernameField;
    private Label password;
    private PasswordField passwordField;

    private Label languageLabel;
    private ComboBox<String> languageSelect;

    // napit
    private Button login;
    private Button register;

    public LoginView() {
        
        // tekstikentät
        username = new Label();
        usernameField = new TextField();
        password = new Label();
        languageLabel = new Label();
        passwordField = new PasswordField();

        languageSelect = new ComboBox<>();
        languageSelect.getItems().addAll("Suomi", "Íslensku");

        languageSelect.getSelectionModel().select(0);

        languageSelect.valueProperty().addListener((ov, t, t1) -> {

            if(t.equals(t1)) {
                return;
            }

            if(t1.equals("Suomi")) {
                Locale.setDefault(new Locale("fi"));
            } else if(t1.equals("Íslensku")) {
                Locale.setDefault(new Locale("is"));
            }

            setLabels();

        });
        
        // napit
        login = new Button();
        register = new Button();

        setLabels();
    }

    private void setLabels() {
        ResourceBundle labels = ResourceBundle.getBundle("LabelsBundle");
        ResourceBundle buttons = ResourceBundle.getBundle("ButtonLabelsBundle");

        username.setText(labels.getString("username") + ":");
        password.setText(labels.getString("password") + ":");
        languageLabel.setText(labels.getString("language") + ":");
        login.setText(buttons.getString("login"));
        register.setText(buttons.getString("register"));
    }

    // Luodaan rekisteröintinäkymä ja palautetaan se Parent oliona.
    public Parent getView() {

        // ruudukko asettelua varten
        GridPane grid = new GridPane();

        // välit ja positiot
        grid.setAlignment(Pos.CENTER);
        grid.setVgap(10);
        grid.setHgap(10);
        grid.setPadding(new Insets(10, 10, 10, 10));

        login.setDefaultButton(true);

        // komponentien asettelu ruudukkoon
        grid.add(username, 0, 0);
        grid.add(usernameField, 1, 0);
        grid.add(password, 0, 1);
        grid.add(passwordField, 1, 1);
        grid.addRow(2, login, register);
        grid.addRow(3, languageLabel, languageSelect);

        // muokkaan nappien asettelua
        GridPane.setMargin(login, new Insets(20, 0, 0, 30));
        GridPane.setMargin(register, new Insets(20, 0, 0, 30));
        GridPane.setMargin(languageLabel, new Insets(20, 0, 0, 30));
        GridPane.setMargin(languageSelect, new Insets(20, 0, 0, 30));

        return grid; // Palautetaan ruudukkoolio

    }

    public void addRegisterButtonEventListener(EventHandler event) {
        register.setOnAction(event);
    }

    public void addLoginButtonEventListener(EventHandler event) {
        login.setOnAction(event);
    }

    public void showAlert(Alert alert) {
        alert.showAndWait();
    }

    public String getUsernameField() {
        return usernameField.getText();
    }

    public String getPasswordField() {
        return passwordField.getText();
    }
    
    public void clearFields() {
        usernameField.clear();
        passwordField.clear();
    }

}
