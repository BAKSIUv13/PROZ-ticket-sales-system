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
import javafx.scene.image.Image;
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
        launch(args);
    }
    
    /**
     * JavaFX start method.
     */
    @Override public void start(Stage primaryStage)
    throws Exception
    {
        Model model = new Model();
    
        // get root
        FXMLLoader fxmlLoader = new FXMLLoader();
        Parent root = fxmlLoader.load(
                getClass().getResource("LogInView.fxml").openStream());
    
        // set model in new controller
        LogInView logInViewController = (LogInView) fxmlLoader.getController();
        logInViewController.setModel(model);
        
        // set stage parameters
        primaryStage.setTitle("Ticket Sales System");
        primaryStage.setScene(new Scene(root));
        primaryStage.setResizable(false);
        primaryStage.getIcons().add(new Image("PROZ/icon.jpg"));
        // show first stage
        primaryStage.show();
    }
}
