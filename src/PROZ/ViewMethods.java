package PROZ;

import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 * Auxiliary methods for the view.
 *
 * @author BAKSIUv13
 */
public class ViewMethods
{
    /**
     * Show error dialog and ends program.
     *
     * @param ex is exception to handle
     */
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
        logInViewController.setLoginField(login);
        
        // get stage
        Stage primaryStage = (Stage) ((Node) event.getSource())
                .getScene().getWindow();
    
        // set new stage and scene
        Scene logInScene = new Scene(root);
    
        Stage logInStage = new Stage();
        logInStage.setScene(logInScene);
    
        // set main stage parameters
        logInStage.setTitle("Ticket Sales System");
        logInStage.setResizable(false);
        logInStage.getIcons().add(new Image("PROZ/icon.jpg"));
    
        primaryStage.close();
        logInStage.show();
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
        clientViewController.setClientLogin(login);
        
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
        ticketViewController.setClientLogin(login);
        ticketViewController.refreshTicketsAction();
        
        // set new stage parameters
        Stage window = (Stage) ((Node) event.getSource()).getScene()
                                                         .getWindow();
        window.setScene(new Scene(root));
    }
    
    static public void changeSceneEventsAction(Event event, Object object,
            String login, Model model)
    throws Exception
    {
        // get root
        FXMLLoader fxmlLoader = new FXMLLoader();
        Parent root = fxmlLoader.load(
                object.getClass().getResource("EventsView.fxml").openStream());
        
        // set model in new controller
        EventsView eventsViewController = (EventsView) fxmlLoader
                .getController();
        eventsViewController.setModel(model);
        eventsViewController.setClientLogin(login);
        eventsViewController.refreshClientEvents();
        
        // set new stage parameters
        Stage window = (Stage) ((Node) event.getSource()).getScene()
                                                         .getWindow();
        window.setScene(new Scene(root));
    }
}