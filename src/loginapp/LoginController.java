package loginapp;

import admin.AdminController;
import register.RegisterController;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import student.StudentController;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;



public class LoginController implements Initializable {

    LoginModel loginModel = new LoginModel();
    //FXML components
    @FXML
    private Label dbstatusbtn;
    @FXML
    private TextField IDbtn;
    @FXML
    private TextField passwordbtn;
    @FXML
    private ComboBox<logintype> comboboxbtn;
    @FXML
    private Button signinbtn;
    @FXML
    private Label wrongLoginbtn;
    @FXML
    private ComboBox<logintype> comboBoxRegisterBtn;

    public String whoIsLoggedIn = " ";

    public void initialize(URL url, ResourceBundle rb){
        if(this.loginModel.isDBConnected()){ // showing the user a meassge whether the program is successfully connected to DB
            this.dbstatusbtn.setText("Connected to the Database");
        }else{
            this.dbstatusbtn.setText("Not connected to the Database");
        }


        this.comboboxbtn.setItems(FXCollections.observableArrayList(logintype.values()));

    }



    @FXML
    public void Login(ActionEvent loginevent){ //this checks if the sign in information is right or wrong and opens the appropriate screen for the user
        try{
            if(this.loginModel.isLoginConnected(this.IDbtn.getText(),this.passwordbtn.getText(),((logintype)this.comboboxbtn.getValue()).toString())){
                Stage stage = (Stage)this.signinbtn.getScene().getWindow();
                stage.close();

                whoIsLoggedIn = IDbtn.getText();
                System.out.println(whoIsLoggedIn);
                switch(((logintype)this.comboboxbtn.getValue()).toString()){
                    case "Admin":
                        adminLogin(whoIsLoggedIn);
                        break;
                    case "Student":
                        studentlogin(whoIsLoggedIn);
                        break;


                }
            }else{
                this.IDbtn.setText(""); //clearing the form
                this.passwordbtn.setText(""); //clearing the form
                this.comboboxbtn.valueProperty().set(null); //clearing the form
                this.wrongLoginbtn.setText("Sorry wrong information");
            }
        }catch(Exception LocalException ){
            System.out.println("ERROR LoginController Login " + LocalException );
            LocalException.printStackTrace();
        }


    }




    public void studentlogin(String whoIsLoggedIn){
        try{                               //oppening a new page(Student page) for the user (Student in this case)
            Stage studentStage = new Stage();
            FXMLLoader studentLoader = new FXMLLoader();
            Pane studentRoot = (Pane)studentLoader.load(getClass().getResource("/student/StudentFXML.fxml").openStream());
            StudentController studentController = (StudentController) studentLoader.getController();

            Scene studentScene = new Scene(studentRoot);
            studentStage.setScene(studentScene);
            studentStage.setTitle("Student Dashboard");
            studentStage.setResizable(false);
            studentStage.show();
            StudentController.greetingString = whoIsLoggedIn;

        }catch (IOException ex){
            System.out.println("ERROR LoginController studentlogin" + ex );
            ex.printStackTrace();
        }

    }
    public void adminLogin(String whoIsLoggedIn){
        try{                               //oppening a new page(Student page) for the user (Student in this case)
            Stage adminStage = new Stage();
            FXMLLoader adminLoader = new FXMLLoader();
            Pane adminRoot = (Pane)adminLoader.load(getClass().getResource("/admin/AdminFXML.fxml").openStream());
            AdminController adminController = (AdminController) adminLoader.getController();

            Scene adminScene = new Scene(adminRoot);
            adminStage.setScene(adminScene);
            adminStage.setTitle("Admin Dashboard");
            adminStage.setResizable(false);
            adminStage.show();
            AdminController.greetingString = whoIsLoggedIn;
            //System.out.println("admin Logiin "+ this.whoIsLoggedIn);




        }catch (IOException ex){
            System.out.println("ERROR LoginController adminlogin" + ex );
            ex.printStackTrace();
        }

    }


    @FXML
    public void openRegisterPage (ActionEvent registerEvent){
        try {
            RegisterPage();
        }
        catch(Exception LocalException ){
            System.out.println("ERROR LoginController openRegisterPage" + LocalException );
            LocalException.printStackTrace();
        }

    }

    public void RegisterPage(){
        try {
            Stage registerStage = new Stage();
            FXMLLoader registerLoader = new FXMLLoader();
            Pane registerRoot = (Pane) registerLoader.load(getClass().getResource("/register/register.fxml").openStream());
            RegisterController registerController = (RegisterController) registerLoader.getController();

            Scene registerScene = new Scene(registerRoot);
            registerStage.setScene(registerScene);
            registerStage.setTitle("Register");
            registerStage.setResizable(false);
            registerStage.show();
            //this.comboboxbtn.setItems(FXCollections.observableArrayList(logintype.values()));
        }
        catch (IOException ex) {
            System.out.println("ERROR LoginController RegisterPage" + ex);
            ex.printStackTrace();
        }
    }

}
