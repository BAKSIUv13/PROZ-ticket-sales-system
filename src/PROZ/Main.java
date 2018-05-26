/*
 * Ticket Sales System
 * Bartlomiej Kulik
 * 26 May 2018
 */
package PROZ;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Starting class in Ticket Sales System.
 *
 * @author BAKSIUv13
 */
public class Main
        extends Application
{
    public static void main(String[] args)
    {
        launch(args); // JavaFX
    }
    
    /**
     * JavaFX start method.
     */
    @Override
    public void start(Stage primaryStage)
    throws Exception
    {
        // connect with LogInView.fxml
        Parent root = FXMLLoader.load(getClass().getResource("LogInView.fxml"));
        // set stage parameters
        primaryStage.setTitle("Ticket Sales System");
        primaryStage.setScene(new Scene(root));
        primaryStage.setResizable(false);
        // show first stage
        primaryStage.show();
    }
}
