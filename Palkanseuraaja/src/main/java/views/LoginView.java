package views;

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

public class LoginView {
    private ResourceBundle labels;
    private ResourceBundle buttons;

    // tekstikentät
    private Label username;
    private TextField usernameField;
    private Label password;
    private PasswordField passwordField;

    // napit
    private Button login;
    private Button register;

    public LoginView() {
        labels = ResourceBundle.getBundle("LabelsBundle");
        buttons = ResourceBundle.getBundle("ButtonLabelsBundle");
        
        // tekstikentät
        username = new Label();
        username.setText(labels.getString("username") + ":");
        usernameField = new TextField();
        password = new Label();
        password.setText(labels.getString("password") + ":");
        passwordField = new PasswordField();
        
        // napit
        login = new Button();
        login.setText(buttons.getString("login"));
        register = new Button();
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

        // muokkaan nappien asettelua
        GridPane.setMargin(login, new Insets(20, 0, 0, 30));
        GridPane.setMargin(register, new Insets(20, 0, 0, 30));

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

}
