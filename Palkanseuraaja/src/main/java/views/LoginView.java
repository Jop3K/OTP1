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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import models.ViewManager;
import javafx.scene.text.Font;


public class LoginView {

    // tekstikentät
    private Label username;
    private TextField usernameField;
    private Label password;
    private PasswordField passwordField;
    private Label welcome;

    private Label languageLabel;
    private ComboBox<String> languageSelect;

    // napit
    private Button login;
    private Button register;
    
    //ImageView
    
    private ImageView imgView;
    private Image salarypalImg;

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
        
        welcome = new Label();
        welcome.setFont(new Font("Arial", 18));
        
        // napit
        login = new Button();
        register = new Button();

        setLabels();
    }

    private void setLabels() {
        ResourceBundle labels = ResourceBundle.getBundle("LabelsBundle");
        ResourceBundle buttons = ResourceBundle.getBundle("ButtonLabelsBundle");

        welcome.setText(labels.getString("welcome"));
        username.setText(labels.getString("username") + ":");
        password.setText(labels.getString("password") + ":");
        languageLabel.setText(labels.getString("language") + ":");
        login.setText(buttons.getString("login"));
        register.setText(buttons.getString("register"));
        
        // image
        
        imgView = new ImageView();
        salarypalImg = new Image("/img/salarypal.png");
        imgView.setImage(salarypalImg);
        imgView.setFitHeight(130);
        imgView.setFitWidth(130);
    }

    // Luodaan rekisteröintinäkymä ja palautetaan se Parent oliona.
    public Parent getView() {

        // ruudukko asettelua varten
        GridPane grid = new GridPane();
        //grid.setGridLinesVisible(true);

        // välit ja positiot
        grid.setAlignment(Pos.CENTER);
        grid.setVgap(10);
        grid.setHgap(10);
        grid.setPadding(new Insets(10, 10, 10, 10));

        login.setDefaultButton(true);

        // komponentien asettelu ruudukkoon
        
        grid.add(imgView,0, 0, 2, 1);
        grid.add(welcome, 0, 1, 2, 1);
        grid.add(username, 0, 2, 1, 1);
        grid.add(usernameField, 1, 2 , 1 ,1);
        grid.add(password, 0, 3 , 1 ,1);
        grid.add(passwordField, 1, 3 ,1, 1);
        grid.addRow(4, login, register);
        grid.addRow(5, languageLabel, languageSelect);
        // muokkaan nappien asettelua
        GridPane.setMargin(login, new Insets(20, 0, 0, 30));
        GridPane.setMargin(register, new Insets(20, 0, 0, 30));
        GridPane.setMargin(languageLabel, new Insets(20, 0, 0, 30));
        GridPane.setMargin(languageSelect, new Insets(20, 0, 0, 30));

        GridPane.setMargin(imgView, new Insets(0,0,0,50));
        GridPane.setMargin(welcome, new Insets(20,0,20,5));
        
        
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
