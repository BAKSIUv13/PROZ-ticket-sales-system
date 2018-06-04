/*
 * Ticket Sales System
 * Bartlomiej Kulik
 * 4 June 2018
 */
package PROZ;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 * Starting class in High Quality Ticket Sales System.
 *
 * @author BAKSIUv13
 */
public class Main
        extends Application
{
    public static void main(String[] args)
    {
        launch(args);
    }
    
    /**
     * JavaFX start method.
     */
    @Override public void start(Stage primaryStage)
    throws Exception
    {
        Model model = new Model(); // only 1 instance
        
        // get root
        FXMLLoader fxmlLoader = new FXMLLoader();
        Parent root = fxmlLoader.load(
                getClass().getResource("LogInView.fxml").openStream());
    
        // set model in new controller
        LogInView logInViewController = (LogInView) fxmlLoader.getController();
        logInViewController.setModel(model);
    
        // set main stage stage
        primaryStage.setScene(new Scene(root));
        
        // set main stage parameters
        primaryStage.setTitle("Ticket Sales System");
        primaryStage.getIcons().add(new Image("PROZ/icon.jpg"));
        primaryStage.setResizable(false);
    
        // show main stage
        primaryStage.show();
    }
}