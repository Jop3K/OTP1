/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import java.io.IOException;
import java.net.URL;
import java.time.Month;
import java.time.Year;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.collections.ListChangeListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import models.Calculation;
import models.EventObservableDataList;
import models.StatsModel;

/**
 * FXML Controller class
 *
 * @author Artur, Joni, Joonas
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
    private Button statsSettingsBtn;
    @FXML
    private BarChart incomesBarChart;
    @FXML
    private ComboBox incomeDropdown;
    @FXML
    private ComboBox workshiftDropdown;
    @FXML
    private ComboBox<Year> yearPick;
    @FXML
    private ComboBox monthPick;
    
    private EventObservableDataList data;
    private StatsModel statsModel;
    private final String SHOW_ALL = "Näytä kaikki";

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
        setComboBoxes();

        data.getInstance().addListener((ListChangeListener)(c -> {
        	statsModel.updateAllData(Year.now(), null);
        	setComboBoxes();
        	
        }));
        incomesBarChart = statsModel.setUpIncomesBarChart(incomesBarChart);
        
      
    }

    private void setComboBoxes() {
    	monthPick.getItems().clear();
    	yearPick.getItems().clear();
    	
		List<Year> yearsFromEvents = Calculation.FindYearsFromEvents();
		monthPick.getItems().add(SHOW_ALL);
		for (Year y : yearsFromEvents){
			yearPick.getItems().add(y);
		}
		for (Month m : Month.values()){
			monthPick.getItems().add(m);
		}
		yearPick.setValue(Year.now());
		monthPick.setValue(SHOW_ALL);
		
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
        incomesBarChart.setTitle(labels.getString("yearlyIncome"));
        //settings.setText(labels.getString("settings"));
        //payLimit.setText(labels.getString("payLimit"));
        //currency.setText(labels.getString("currency"));
    }

    public void setButtons() {

      
      //  saveButton.setText(buttons.getString("save"));
      //  editButton.setText(buttons.getString("edit"));
      //  statsSettingsBtn.setText(buttons.getString("settings"));

    }
   
    public void openSettings(ActionEvent e) {
    	
    	 try {
    	        FXMLLoader fxmlLoader = new FXMLLoader();
    	        fxmlLoader.setLocation(getClass().getResource("/fxml/StatsSettingsView.fxml"));
    	        
    	        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
    	        Stage stage = new Stage();
    	        stage.setTitle(labels.getString("statsSettings"));
    	        stage.setScene(scene);
    	        stage.show();
    	    } catch (IOException er) {
    	        Logger logger = Logger.getLogger(getClass().getName());
    	        logger.log(Level.SEVERE, "Failed to create new Window.", er);
    	    }
     
    }
    
    public void populateBarChartFromYearPick(){
    	
    		Year year =  yearPick.getSelectionModel().getSelectedItem();
    	
	    	if(!monthPick.getSelectionModel().getSelectedItem().equals(SHOW_ALL)) {
	    		
	    	monthPick.setValue(SHOW_ALL); //Reseting monthPick dropdown
	    	statsModel.updateAllData(year, null);
	    	}
	    	else{
	    		statsModel.updateAllData(year, null);
	    	}
    	}
     
    
    public void populateBarChartFromMonthPick(){
    	Year year = (Year)yearPick.getSelectionModel().getSelectedItem();
    	
    	if(monthPick.getSelectionModel().getSelectedItem().equals(SHOW_ALL)) {
    		statsModel.updateAllData(year, null);
    	}
    	else {
    		Month month = (Month) monthPick.getSelectionModel().getSelectedItem();
	    	statsModel.updateAllData(year, month);
    	}
    }
}
