package seanmeenaghanims;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 This class creates a JavaFX inventory management system application that stores Part and Product objects.
 @author Sean
 */
public class SeanMeenaghanIMS extends Application {
    
    /**
     This is the main method. This is the first method called by the SeanMeenaghanIMS application. 
     @param args the command line arguments
     */
    public static void main(String[] args) {
        
        launch(args);
        
    }

    /**
     This method is called by the launch method in main(). This method opens the main screen of the
     application when it starts.
     @param stage the stage object for setting the scene.
     @throws Exception the exception to throw
     */
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/View_Controller/mainScreen.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Inventory Management System");
        stage.show();
    }
    
}
