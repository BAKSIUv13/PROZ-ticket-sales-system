/*
 * Ticket Sales System
 * Bartlomiej Kulik
 * 22 May 2018
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
        // connect with view.fxml
        Parent root = FXMLLoader.load(getClass().getResource("view.fxml"));
        // set stage parameters
        primaryStage.setTitle("Ticket Sales System");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }
}
