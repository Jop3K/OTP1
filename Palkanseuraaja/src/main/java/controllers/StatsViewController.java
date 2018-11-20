/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.text.Font;
import models.EventObservableDataList;
import models.StatsModel;

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
    private Label workShifts;
    @FXML
    private Label revenue;
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
    @FXML
    private BarChart incomesByMonthsBarChart;
    @FXML
    private ComboBox incomeDropdown;
    @FXML
    private ComboBox workshiftDropdown;

    private EventObservableDataList data;
    private StatsModel statsModel;

    public StatsViewController() {
        statsModel = new StatsModel();
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        labels = ResourceBundle.getBundle("LabelsBundle");
        buttons = ResourceBundle.getBundle("ButtonLabelsBundle");

        setLabels();
        setButtons();

        data.getInstance().addListener((ListChangeListener)(c -> {statsModel.updateAllData();}));
        incomesByMonthsBarChart = statsModel.setUpIncomesByMonthsBarChart(incomesByMonthsBarChart);
    }

    public void setLabels() {
        stats.setText(labels.getString("stats"));
        revenue.setText(labels.getString("revenue"));
        incomeDropdown.getItems().add(labels.getString("today"));
        incomeDropdown.getSelectionModel().select(0);
        incomeDropdown.getItems().add(labels.getString("week"));
        incomeDropdown.getItems().add(labels.getString("month"));
        incomeDropdown.getItems().add(labels.getString("year"));
        workshiftDropdown.getItems().add(labels.getString("today"));
        workshiftDropdown.getSelectionModel().select(0);
        workshiftDropdown.getItems().add(labels.getString("week"));
        workshiftDropdown.getItems().add(labels.getString("month"));
        workshiftDropdown.getItems().add(labels.getString("year"));
        workShifts.setText(labels.getString("workShifts"));
        incomesByMonthsBarChart.setTitle(labels.getString("yearlyIncome"));
        //settings.setText(labels.getString("settings"));
        payLimit.setText(labels.getString("payLimit"));
        currency.setText(labels.getString("currency"));
    }

    public void setButtons() {
        saveButton.setText(buttons.getString("save"));
        editButton.setText(buttons.getString("edit"));
    }
}
