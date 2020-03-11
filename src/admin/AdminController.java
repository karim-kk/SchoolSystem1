package admin;

import com.sun.xml.internal.bind.v2.model.core.ID;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import dbUtil.dbConnection;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import loginapp.LoginController;
import loginapp.LoginModel;
import student.StudentController;


import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import static loginapp.LoginController.*;

public class AdminController implements Initializable {

   //// AdminFXML fxml components
    @FXML
    private TableView<StudentData> studentTable;
    @FXML
    private TableColumn<StudentData, String> IDColumn;
    @FXML
    private TableColumn<StudentData, String> FirstColumn;
    @FXML
    private TableColumn<StudentData, String> LastColumn;
    @FXML
    private TableColumn<StudentData, String> DOBColumn;
    @FXML
    private Label greetingBtn;
    @FXML
    private Button addStudentBtn;
    @FXML
    private Button refreshBtn;
    @FXML
    private Button opeChangeGradesBtn;


    public static String greetingString;

    /////addStudent fxml components
    @FXML
    private TextField IDaddBtn;
    @FXML
    private TextField FirstAddBtn;
    @FXML
    private TextField LastAddBtn;
    @FXML
    private DatePicker DOBAddBtn;
    @FXML
    private Button AddStudentFinalbtn;
    @FXML
    private Label SuccessfulEntryBtn;
    @FXML
    private Label adminName;
    @FXML
    private TextField PassAddBtn;
    @FXML
    private Label addStudentGreeting;

    @FXML
    private TextField mathGradeBtn;
    @FXML
    private TextField englishGradeBtn;
    @FXML
    private TextField scienceGradeBtn;
    @FXML
    private TextField historyGradeBtn;
    @FXML
    private Label adminNameInGrades;
    @FXML
    private Button addGradesBtn;
    @FXML
    private Label successfulGrades;


    public static String IDForGrades;
    @FXML
    private TextField IDChange;
    @FXML
    private TextField MathChange;
    @FXML
    private TextField EnglishChange;
    @FXML
    private TextField ScienceChange;
    @FXML
    private TextField HistoryChange;
    @FXML
    private Label changeGradesGreeting;
    @FXML
    private Button changeGradesFinalBtn;
    @FXML
    private Label successfullChangeBtn;





    private dbConnection dbCon;
    private ObservableList<StudentData> studentData;


    public void initialize(URL url, ResourceBundle rb){

        this.dbCon = new dbConnection();
    }

    public String loggedin;

    /*
    loads the student data from the database
     */
    @FXML
    public void loadData(ActionEvent loadEvent) throws SQLException {
       //ResultSet rs = null;
       //String sql = "SELECT * FROM students";
        try{
            this.greetingBtn.setText(greetingString);
            Connection connection = dbConnection.getConnection();
            this.studentData = FXCollections.observableArrayList();

            ResultSet rs = connection.createStatement().executeQuery("SELECT * FROM students");
            while (rs.next()){
                this.studentData.add(new StudentData(rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4)));

            }
        } catch (SQLException e){
            System.out.println("ERROR AdminController loadData " + e );
            e.printStackTrace();
        }

        this.IDColumn.setCellValueFactory(new PropertyValueFactory<StudentData, String>("ID"));
        this.FirstColumn.setCellValueFactory(new PropertyValueFactory<StudentData, String>("firstName"));
        this.LastColumn.setCellValueFactory(new PropertyValueFactory<StudentData, String>("lastName"));
        this.DOBColumn.setCellValueFactory(new PropertyValueFactory<StudentData, String>("DOB"));

        this.studentTable.setItems(null);
        this.studentTable.setItems(this.studentData);



    }

    /*
    Adds a new student data to the database
     */
    @FXML
    private void addStudent(ActionEvent addEvent){
        //String sql = "INSERT INTO students (ID,First,Last,DOB) VALUES (?,?,?,?)";

        try{

            Connection connection = dbConnection.getConnection();
            PreparedStatement ps = connection.prepareStatement("INSERT INTO students (ID,First,Last,DOB) VALUES (?,?,?,?)");

            IDForGrades = this.IDaddBtn.getText();

            ps.setString(1,this.IDaddBtn.getText());
            ps.setString(2,this.FirstAddBtn.getText());
            ps.setString(3,this.LastAddBtn.getText());
            ps.setString(4,this.DOBAddBtn.getEditor().getText());

            ps.execute();
            ps.close();

            PreparedStatement pp = connection.prepareStatement("INSERT INTO login (ID,Password,Type) VALUES (?,?,?)");

            pp.setString(1,this.IDaddBtn.getText());
            pp.setString(2,this.PassAddBtn.getText());
            pp.setString(3,"Student");

            pp.execute();
            pp.close();
            connection.close();

            try {
                gradesPage();
            }
            catch(Exception LocalException ){
                System.out.println("ERROR AdminController addStudent" + LocalException );
                LocalException.printStackTrace();
            }


            this.SuccessfulEntryBtn.setText("Successful entry, please close this window and hit refresh");

        }catch (SQLException ex){
            System.out.println("ERROR AdminController addStudent " + ex );
            ex.printStackTrace();
        }
    }

    /*
    on Action event that performs openAddPage that opens the student addition page
    */
    @FXML
    public void openAddStudentPage (ActionEvent addEvent){
        try {
            openAddPage();
        }
        catch(Exception LocalException ){
            System.out.println("ERROR AdminController addStudent" + LocalException );
            LocalException.printStackTrace();
        }

    }

/*
 method that opens a new page that includes the student addition
 */
    public void openAddPage(){
       try {
           Stage addStage = new Stage();
           FXMLLoader addStudentLoader = new FXMLLoader();
           Pane addStudentRoot = (Pane) addStudentLoader.load(getClass().getResource("/admin/addStudent.fxml").openStream());
           AdminController adminController = (AdminController) addStudentLoader.getController();

           Scene addStudentScene = new Scene(addStudentRoot);
           addStage.setScene(addStudentScene);
           addStage.setTitle("Add Student");
           addStage.setResizable(false);
           addStage.show();
           this.addStudentGreeting.setText(greetingString);
       }
       catch (IOException ex) {
           System.out.println("ERROR AdminController openAddPage" + ex);
           ex.printStackTrace();
       }
    }
    public void gradesPage(){
        try {
            Stage addStage = new Stage();
            FXMLLoader addGradesLoader = new FXMLLoader();
            Pane addGradesRoot = (Pane) addGradesLoader.load(getClass().getResource("/admin/NewStudentGrades.fxml").openStream());
            AdminController adminController = (AdminController) addGradesLoader.getController();

            Scene addStudentScene = new Scene(addGradesRoot);
            addStage.setScene(addStudentScene);
            addStage.setTitle("New Student Grades");
            addStage.setResizable(false);
            addStage.show();
            this.adminNameInGrades.setText(greetingString);
        }
        catch (IOException ex) {
            System.out.println("ERROR AdminController openAddPage" + ex);
            ex.printStackTrace();
        }
    }

    public void addGrades(){
        System.out.println("addGrades" + IDForGrades);
        try{
            Connection connection = dbConnection.getConnection();
            PreparedStatement pd = connection.prepareStatement("INSERT INTO grades (ID,math,english,science,history) VALUES (?,?,?,?,?)");
            pd.setString(1, IDForGrades);
            pd.setString(2,mathGradeBtn.getText());
            pd.setString(3,englishGradeBtn.getText());
            pd.setString(4,scienceGradeBtn.getText());
            pd.setString(5,historyGradeBtn.getText());

            pd.execute();
            pd.close();
            connection.close();
            this.successfulGrades.setText("Grades added successfully please close this window and hit refresh");
        }catch (SQLException ex) {
            System.out.println("ERROR AdminController addGrades " + ex);
            ex.printStackTrace();
        }

    }
    public void changeGradesPage(){
        try {
            Stage addStage = new Stage();
            FXMLLoader changeGradesLoader = new FXMLLoader();
            Pane changeGradesRoot = (Pane) changeGradesLoader.load(getClass().getResource("/admin/changeGrades.fxml").openStream());
            AdminController adminController = (AdminController) changeGradesLoader.getController();

            Scene changeGradesScene = new Scene(changeGradesRoot);
            addStage.setScene(changeGradesScene);
            addStage.setTitle("Change Student Grades");
            addStage.setResizable(false);
            addStage.show();
        }
        catch (IOException ex) {
            System.out.println("ERROR AdminController changeGradesPage" + ex);
            ex.printStackTrace();
        }
    }

    public void changeGrades(){
      try{
        Connection connection = dbConnection.getConnection();
        //PreparedStatement pd = connection.prepareStatement("INSERT INTO grades (math,english,science,history) VALUES (?,?,?,?) Where ID =" + IDChange.getText() );
        PreparedStatement pd = connection.prepareStatement("UPDATE grades SET math=?, english=?, science=?, history=? WHERE ID =" + IDChange.getText());
        pd.setString(1,MathChange.getText());
        pd.setString(2,EnglishChange.getText());
        pd.setString(3,ScienceChange.getText());
        pd.setString(4,HistoryChange.getText());

        pd.execute();
        pd.close();
        connection.close();
        this.successfullChangeBtn.setText("The Grades were changes successfully!");

      }catch (SQLException ex) {
          System.out.println("ERROR AdminController changeGrades " + ex);
          ex.printStackTrace();
      }
    }




}



