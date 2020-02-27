package student;

import admin.StudentData;
import dbUtil.dbConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class StudentController implements Initializable {

    @FXML
    private TableView<StudentGrades> gradesTable;
    @FXML
    private TableColumn<StudentGrades, String> IDColumn;
    @FXML
    private TableColumn<StudentGrades, String> MathColumn;
    @FXML
    private TableColumn<StudentGrades, String> EnglishColumn;
    @FXML
    private TableColumn<StudentGrades, String> ScienceColumn;
    @FXML
    private TableColumn<StudentGrades, String> HistoryColumn;

    @FXML
    private Label greetingBtn;
    @FXML
    private Button refreshBtn;

    public static String greetingString;
    private dbConnection dbCon;
    private ObservableList<StudentGrades> studentGrades;

    @FXML
    public void loadData() throws SQLException {
        //ResultSet rs = null;
        //String sql = "SELECT * FROM students";
        try{
            System.out.println("loadDaata greetingString " + greetingString);
            String sqlgreetingString = greetingString;
            System.out.println("loadDaata sqlgreetingString " + sqlgreetingString);
            this.greetingBtn.setText(greetingString);

            Connection connection = dbConnection.getConnection();
            this.studentGrades = FXCollections.observableArrayList();

            ResultSet rs = connection.createStatement().executeQuery("SELECT * FROM grades Where ID =" + greetingString );
            while (rs.next()){//"SELECT * FROM grades Where ID = '%' ||" + greetingString + "|| '%' "
                this.studentGrades.add(new StudentGrades(rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5)));

            }
        } catch (SQLException e){
            System.out.println("ERROR AdminController loadData " + e );
            e.printStackTrace();
        }

        this.IDColumn.setCellValueFactory(new PropertyValueFactory<StudentGrades, String>("ID"));
        this.MathColumn.setCellValueFactory(new PropertyValueFactory<StudentGrades, String>("Math"));
        this.EnglishColumn.setCellValueFactory(new PropertyValueFactory<StudentGrades, String>("Science"));
        this.ScienceColumn.setCellValueFactory(new PropertyValueFactory<StudentGrades, String>("History"));
        this.HistoryColumn.setCellValueFactory(new PropertyValueFactory<StudentGrades, String>("English"));

        this.gradesTable.setItems(null);
        this.gradesTable.setItems(this.studentGrades);



    }

    public void initialize(URL location, ResourceBundle resources) {
        this.dbCon = new dbConnection();
    }
}
