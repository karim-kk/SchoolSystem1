package register;

import dbUtil.dbConnection;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import loginapp.logintype;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;

public class RegisterController implements Initializable {


    @FXML
    private TextField IDRegisterBtn;
    @FXML
    private TextField passwordRegisterBtn;
    @FXML
    private TextField FirstRegisterBtn;
    @FXML
    private TextField LastRegisterBtn;
    @FXML
    private DatePicker DOBRegisterBtn;
    @FXML
    private ComboBox<logintype> comboBoxRegisterBtn;
    @FXML
    private Button RegisterFinalButton;
    @FXML
    private Label unallowedEntryBtn1;
    @FXML
    private Label unallowedEntryBtn2;




    private dbConnection conn;
    public void initialize(URL url, ResourceBundle rb){

        this.conn = new dbConnection();
        this.comboBoxRegisterBtn.setItems(FXCollections.observableArrayList(logintype.values()));
    }

   @FXML
   private void RegisterStudent(ActionEvent event){

        try {
            if(((logintype) this.comboBoxRegisterBtn.getValue()).toString().equals("Student")) {
                Connection connection = dbConnection.getConnection();
                PreparedStatement ps = connection.prepareStatement("INSERT INTO login (Username,Password,Type) VALUES(?,?,?)");

                ps.setString(1, this.IDRegisterBtn.getText());
                ps.setString(2, this.passwordRegisterBtn.getText());
                ps.setString(3, ((logintype) this.comboBoxRegisterBtn.getValue()).toString());

                ps.execute();
                ps.close();


                PreparedStatement pp = connection.prepareStatement("INSERT INTO students (ID,First,Last,DOB) VALUES (?,?,?,?)");

                pp.setString(1, this.IDRegisterBtn.getText());
                pp.setString(2, this.FirstRegisterBtn.getText());
                pp.setString(3, this.LastRegisterBtn.getText());
                pp.setString(4, this.DOBRegisterBtn.getEditor().getText());

                pp.execute();
                pp.close();
                connection.close();

                this.unallowedEntryBtn1.setText("    Registration Successful !");
                this.unallowedEntryBtn2.setText("");

            } else if(((logintype) this.comboBoxRegisterBtn.getValue()).toString().equals("Admin")){
                this.unallowedEntryBtn1.setText("Sorry, you are only allowed to");
                this.unallowedEntryBtn2.setText("register as student!");

                /*
                //sleep(2000);
                //clear();
                //this.unallowedEntryBtn1.setText("");
                //this.unallowedEntryBtn2.setText("");
                //this.comboBoxRegisterBtn.valueProperty().set(null);

                 */

            }

        }catch (SQLException ex){
            System.out.println("ERROR RegisterController RegisterStudent " + ex );
            ex.printStackTrace();
        }

    }

    /*public static void sleep(int time){
        try{
            Thread.sleep(time);
        }catch(Exception e){

        }
    }
    public void clear(){
        this.unallowedEntryBtn1.setText("");
        this.unallowedEntryBtn2.setText("");
        this.comboBoxRegisterBtn.valueProperty().set(null);
    }

     */


}