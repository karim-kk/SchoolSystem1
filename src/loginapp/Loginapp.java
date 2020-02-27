package loginapp;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Loginapp extends Application {

    public void start (Stage stage) throws Exception{
        Parent root = (Parent) FXMLLoader.load(getClass().getResource("login.fxml")); //creating root node

        Scene scene = new Scene(root); // creating a new scene
        stage.setScene(scene);     // setting the scene to the stage
        stage.setTitle("University of KN");  //Program title
        stage.setResizable(false);
        stage.show();

    }

    public static void main(String []args){

        launch(args);
    }
}
