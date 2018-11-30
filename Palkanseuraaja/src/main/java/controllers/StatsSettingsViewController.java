package controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;

import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

/**
 *  FXML Controller class
 *  @author Joni
 */
public class StatsSettingsViewController implements Initializable {
    
    private ResourceBundle labels;
    private ResourceBundle buttons;
    
    @FXML
    private Label statsSettings;
    @FXML
    private Label wageLimit;
    @FXML
    private Label currency;
    @FXML
    private Button statsSettingsSave;
    @FXML
    private Button statsSettingsBack;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
            
		labels = ResourceBundle.getBundle("LabelsBundle");
                buttons = ResourceBundle.getBundle("ButtonLabelsBundle");
		
                statsSettings.setText(labels.getString("statsSettings"));
                wageLimit.setText(labels.getString("wageLimit"));
                currency.setText(labels.getString("currency"));
                statsSettingsSave.setText(buttons.getString("save"));
                statsSettingsBack.setText(buttons.getString("back"));
	}

}
