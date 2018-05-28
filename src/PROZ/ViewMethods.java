package PROZ;

import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

/**
 * @author BAKSIUv13
 */
public class ViewMethods
{
    static public void exceptionHandler(Throwable ex)
    {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Fatal Error");
        alert.setHeaderText("SQLException");
        alert.setContentText(ex.getMessage());
        
        alert.showAndWait();
        System.exit(-1);
    }
    
    static public void logOutAction(Event event, Object object, String login,
            Model model)
    throws Exception
    {
        // get root
        FXMLLoader fxmlLoader = new FXMLLoader();
        Parent root = fxmlLoader.load(
                object.getClass().getResource("LogInView.fxml").openStream());
        
        // set model in new controller
        LogInView logInViewController = (LogInView) fxmlLoader
                .getController();
        logInViewController.setModel(model);
        logInViewController.setLogin(login);
        
        // get stage
        Stage primaryStage = (Stage) ((Node) event.getSource())
                .getScene().getWindow();
        primaryStage.setMinWidth(0);
        primaryStage.setMinHeight(0);
        primaryStage.hide();
        
        // set new stage
        Scene clientView = new Scene(root);
        primaryStage.setScene(clientView);
        primaryStage.setMaxWidth(309);
        primaryStage.setMaxHeight(500);
        primaryStage.setMinWidth(309);
        primaryStage.setMinHeight(500);
        
        primaryStage.setResizable(false);
        
        primaryStage.show();
    }
    
    static public void changeSceneClientAction(Event event, Object object,
            String login, Model model)
    throws Exception
    {
        // get root
        FXMLLoader fxmlLoader = new FXMLLoader();
        Parent root = fxmlLoader.load(
                object.getClass().getResource("ClientView.fxml").openStream());
        
        // set model in new controller
        ClientView clientViewController = (ClientView) fxmlLoader
                .getController();
        clientViewController.setModel(model);
        clientViewController.setLogin(login);
        
        // set new stage parameters
        Stage window = (Stage) ((Node) event.getSource()).getScene()
                                                         .getWindow();
        window.setScene(new Scene(root));
    }
    
    static public void changeSceneTicketAction(Event event, Object object,
            String login, Model model)
    throws Exception
    {
        // get root
        FXMLLoader fxmlLoader = new FXMLLoader();
        Parent root = fxmlLoader.load(
                object.getClass().getResource("TicketView.fxml").openStream());
        
        // set model in new controller
        TicketView ticketViewController = (TicketView) fxmlLoader
                .getController();
        ticketViewController.setModel(model);
        ticketViewController.setLogin(login);
        ticketViewController.refreshTicketsAction();
        
        // set new stage parameters
        Stage window = (Stage) ((Node) event.getSource()).getScene()
                                                         .getWindow();
        window.setScene(new Scene(root));
    }
    
    
}