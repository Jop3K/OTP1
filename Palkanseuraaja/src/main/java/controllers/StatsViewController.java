/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.text.Font;

/**
 * FXML Controller class
 *
 * @author Artur
 */
public class StatsViewController implements Initializable {

    private ResourceBundle labels;
    private ResourceBundle buttons;

    @FXML
    private Label stats;
    @FXML
    private Label settings;
    @FXML
    private Font x12;
    @FXML
    private Label revenueDay;
    @FXML
    private TextField incomeToday;
    @FXML
    private Label workShifts;
    @FXML
    private TextField incomeMonthly;
    @FXML
    private TextField incomeYearly;
    @FXML
    private Label revenueMonth;
    @FXML
    private TextField workshiftUpcoming;
    @FXML
    private Label revenueYear;
    @FXML
    private Font x121;
    @FXML
    private Label payLimit;
    @FXML
    private TextField incomeLimit;
    @FXML
    private Label currency;
    @FXML
    private ComboBox<?> currencyDrop;
    @FXML
    private Button saveButton;
    @FXML
    private Button editButton;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        labels = ResourceBundle.getBundle("LabelsBundle");
        buttons = ResourceBundle.getBundle("ButtonLabelsBundle");
        
        setLabels();
        setButtons();
    }

    public void setLabels() {
        stats.setText(labels.getString("stats"));
        revenueDay.setText(labels.getString("revenueDay"));
        revenueMonth.setText(labels.getString("revenueMonth"));
        revenueYear.setText(labels.getString("revenueYear"));
        workShifts.setText(labels.getString("workShifts"));
        settings.setText(labels.getString("settings"));
        payLimit.setText(labels.getString("payLimit"));
        currency.setText(labels.getString("currency"));
    }
    
    public void setButtons() {
        saveButton.setText(buttons.getString("save"));
        editButton.setText(buttons.getString("edit"));
    }
}
