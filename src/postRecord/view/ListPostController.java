/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package postRecord.view;



import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import postRecord.model.Baza;
import static postRecord.model.Baza.DB;
import postRecord.view.ListPostController.Post;

/**
 * FXML Controller class
 *
 * @author ACER NITRO
 */
public class ListPostController implements Initializable {

    ObservableList<Post> list = FXCollections.observableArrayList();

    @FXML
    private AnchorPane rootPanel;
    @FXML
    private TableView<Post> tableView;
    @FXML
    private TableColumn<Post, String> name;
    @FXML
    private TableColumn<Post, String> price;
    @FXML
    private TableColumn<Post, String> amount;
    @FXML
    private TableColumn<Post, String> coupon;
    @FXML
    private TableColumn<Post, String> delivery;
    @FXML
    private JFXTextField nazivField;
    @FXML
    private JFXTextField cijenaField;
    @FXML
    private JFXTextField kolicinaField;
    @FXML
    private JFXTextField kuponField;
    @FXML
    private JFXTextField dostavaField;

    public String FK_korisnik;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initCol();
                
        loadData();
    }    

    
    private void initCol() {
        SimpleIntegerProperty id = new SimpleIntegerProperty();
       name.setCellValueFactory(new PropertyValueFactory<>("name"));
        price.setCellValueFactory(new PropertyValueFactory<>("price"));
        amount.setCellValueFactory(new PropertyValueFactory<>("amount"));
        coupon.setCellValueFactory(new PropertyValueFactory<>("coupon"));
        delivery.setCellValueFactory(new PropertyValueFactory<>("delivery"));
    }

    
    private void loadData() {
        Baza handler = new Baza();
        
        // isprazni listu
        list = FXCollections.observableArrayList();
        try {
           
            ResultSet rs = DB.select("SELECT * FROM paket");
            while (rs.next()) {
                        Integer id = rs.getInt("id");
                        String amount = rs.getString("kolicina");
                        String coupon = rs.getString("kupon");
                        String delivery = rs.getString("dostava");
                        
                        list.add(new Post(id, amount, coupon, delivery));
            }
            
        } catch (SQLException ex) {
            System.out.println("Greška: " + ex.getMessage());
        }
        
        tableView.setItems(list);
    }
    
    Post odabranaPosta;

    @FXML
    private void odaberiPostu(MouseEvent event) {
        this.odabranaPosta = (Post) this.tableView.getSelectionModel().getSelectedItem();
        this.kolicinaField.setText(this.odabranaPosta.getAmount());
        this.kuponField.setText(this.odabranaPosta.getCoupon());
        this.dostavaField.setText(this.odabranaPosta.getDelivery());
    }

    @FXML
    private void urediPosiljku(ActionEvent event) {
        
        this.odabranaPosta.setAmount(this.kolicinaField.getText());
        this.odabranaPosta.setCoupon(this.kuponField.getText());
        this.odabranaPosta.setDelivery(this.dostavaField.getText());
        this.odabranaPosta.uredi();   
        this.loadData();
        
    }
    
    
    
    
    @FXML
    private void brisiPosiljku(ActionEvent event) {
        if (this.odabranaPosta != null) {
        this.odabranaPosta.brisi();
        }
        this.loadData(); // Ovo ce ucitati sve ispocetka
        //Ovdje mi treba nešto za refresh stranice kad se izbriše pošta, probavao sam nesto ali nisam uspio rijesit
    }

  
    
     public class Post{
        private SimpleIntegerProperty id;
        private SimpleStringProperty amount;
        private SimpleStringProperty coupon;
        private SimpleStringProperty delivery;
        
        Post(Integer id,String amount, String coupon, String delivery){
            this.id = new SimpleIntegerProperty(id);
            this.amount = new SimpleStringProperty(amount);
            this.coupon = new SimpleStringProperty(coupon);
            this.delivery = new SimpleStringProperty(delivery);
        }
        
        public Integer getId() {
            return id.get();
        }

        

        public String getAmount() {
            return amount.get();
        }

        public String getCoupon() {
            return coupon.get();
        }

        public String getDelivery() {
            return delivery.get();
        }
        
        public void brisi () {
        try {
            PreparedStatement upit = DB.exec("DELETE FROM addpost WHERE id=?");
            upit.setInt(1, this.getId());
            upit.executeUpdate();
            } catch (SQLException ex) {
            System.out.println("Greška prilikom spasavanja poste u bazu:" + ex.getMessage());
            }
        }
        
        public void uredi () {
        try {
        PreparedStatement upit = DB.exec("UPDATE paket SET kolicina=?, kupon=?, dostava=? WHERE id=?");
        upit.setString(3, this.getAmount());
        upit.setString(4, this.getCoupon());
        upit.setString(5, this.getDelivery());
        upit.setInt(6, this.getId());
        upit.executeUpdate();
        } catch (SQLException ex) {
        System.out.println("Greška prilikom spasavanja poste u bazu:" + ex.getMessage());
        }
        }

        public void setId(SimpleIntegerProperty id) {
            this.id = id;
        }


        public void setAmount(String amount) {
            this.amount =  new SimpleStringProperty(amount);
        }

        public void setCoupon( String coupon) {
            this.coupon =  new SimpleStringProperty(coupon);
        }

        public void setDelivery(String delivery) {
            this.delivery =  new SimpleStringProperty(delivery);
        }
        
        
       

       

        
    }
    
    
    
}
