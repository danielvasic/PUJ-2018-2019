package postRecord.view;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.stage.Stage;


public class SettingsController implements Initializable {

    @FXML
    private JFXTextField username;
    @FXML
    private JFXPasswordField password;

    LoginController preferences;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initDefaultValues();
    }    

    @FXML
    private void handleSaveButtonAction(ActionEvent event){
        String uname = username.getText();
        String pass = password.getText();
        
        
        preferences.setPassword(pass);
        preferences.setUsername(uname);
        
    }
    
    private void initDefaultValues() {
        username.setText(String.valueOf(preferences.getUsername()));
        password.setText(String.valueOf(preferences.getPassword()));
    }

    @FXML
    private void handleCancelButtonAction(ActionEvent event) {
        ((Stage)username.getScene().getWindow()).close();
    }
    
}
