/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package postRecord.view;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import postRecord.model.Baza;

/**
 * FXML Controller class
 *
 * @author ACER NITRO
 */
public class AddUserController implements Initializable {

    @FXML
    private JFXTextField ime;
    @FXML
    private JFXTextField prezime;
    @FXML
    private JFXTextField adresa;
    @FXML
    private JFXTextField brojMob;
    @FXML
    private JFXTextField grad;
    @FXML
    private JFXButton saveButton;
    @FXML
    private JFXButton cancelButton;
    @FXML
    private JFXPasswordField lozinka;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void addUser(ActionEvent event) {
             String name = ime.getText();
        String lastname = prezime.getText();
        String password = lozinka.getText();
        String address = adresa.getText();
        String phoneNumber = brojMob.getText();
        String city = grad.getText();
        
        if(name.isEmpty()||lastname.isEmpty()||address.isEmpty()||phoneNumber.isEmpty()||city.isEmpty()||password.isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Molimo popunite sva polja");
            alert.showAndWait();
            return;
        }
        try {
            PreparedStatement stmnt = Baza.DB.konekcija.prepareStatement(
                    "INSERT INTO adduser VALUES (null, ?, ?, ?, ?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS
            );
            stmnt.setString(1, name);
            stmnt.setString(2, lastname);
            stmnt.setString(3, password);
            stmnt.setString(4, address);
            stmnt.setString(5, phoneNumber);
            stmnt.setString(6, city);
            stmnt.executeUpdate();

            ResultSet generatedKeys = stmnt.getGeneratedKeys();
            generatedKeys.next();
          
        } catch (SQLException e) {
            System.out.println("Greska prilikom stvaranja korisnika u bazi:"
                    + e.getMessage());
        }
    }

    @FXML
    private void cancel(ActionEvent event) {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }
    

  
    
}
