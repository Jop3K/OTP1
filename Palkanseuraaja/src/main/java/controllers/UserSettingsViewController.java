/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

/**
 * FXML Controller class
 *
 * @author Artur
 */
public class UserSettingsViewController implements Initializable {

    private ResourceBundle labels;
    private ResourceBundle buttons;
    private ResourceBundle messages;

    @FXML
    private Label userSettingsLabel;
    @FXML
    private Button userSettingsLogoutBtn;
    @FXML
    private Button userSettingsLinkGoogleBtn;
    @FXML
    private Button userSettingsBackBtn;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        labels = ResourceBundle.getBundle("LabelsBundle");
        buttons = ResourceBundle.getBundle("ButtonLabelsBundle");

        userSettingsLabel.setText(labels.getString("userSettings"));
        userSettingsBackBtn.setText(buttons.getString("back"));
        userSettingsLinkGoogleBtn.setText(buttons.getString("linkGoogleAccount"));
        userSettingsLogoutBtn.setText(buttons.getString("logout"));

    }

    @FXML
    private void logout(ActionEvent event) {
    }

    @FXML
    private void linkGoogleAccout(ActionEvent event) {
    }

    @FXML
    private void back(ActionEvent event) {
    }

}
