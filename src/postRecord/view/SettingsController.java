package postRecord.view;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.stage.Stage;
import static postRecord.model.Baza.DB;


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
        //System.out.println(String.valueOf(preferences.getPassword()));
        //String lozinka=new String();
        //lozinka= preferences.getPassword();
        
        try {
        PreparedStatement upit = DB.exec("UPDATE korisnik SET lozinka=? WHERE lozinka=?");
        upit.setString(1, password.getText());
        //upit.setString(2, preferences.getPassword());
        upit.executeUpdate();
        } catch (SQLException ex) {
        System.out.println("Greška prilikom spremanja lozinke u bazu:" + ex.getMessage());
        }
        
    }
    
    private void initDefaultValues() {
    }

    @FXML
    private void handleCancelButtonAction(ActionEvent event) {
        ((Stage)username.getScene().getWindow()).close();
    }
    
}
