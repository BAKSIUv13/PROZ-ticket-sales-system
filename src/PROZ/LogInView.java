package PROZ;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.sql.SQLException;

/**
 * LogInView.fxml class controller.
 *
 * @author BAKSIUv13
 */
public class LogInView
{
    @FXML private TextField loginField;
    
    @FXML private PasswordField passwordField;
    
    @FXML private Label signInFailedLabel;
    
    private Model model;
    
    public void setModel(Model model)
    {
        this.model = model;
    }
    
    public void setLoginField(String text)
    {
        this.loginField.setText(text);
    }
    
    /**
     * If loginField and passwordField is correct, change stage. Else, try
     * again.
     *
     * @param event is necessary to get primary stage
     */
    @FXML private void signInAction(Event event)
    throws Exception
    {
        String login = this.loginField.getText();
        try
        {
            if (this.model.isCorrectLogIn(login, this.passwordField.getText()))
            {
                this.signInFailedLabel.setVisible(false);
                
                // get root
                FXMLLoader fxmlLoader = new FXMLLoader();
                Parent root = fxmlLoader.load(
                        getClass().getResource("ClientView.fxml").openStream());
    
                // set model in new controller
                ClientView clientViewController = (ClientView) fxmlLoader
                        .getController();
                clientViewController.setModel(this.model);
                clientViewController.setClientLogin(login);
                
                // get stage
                Stage primaryStage = (Stage) ((Node) event.getSource())
                        .getScene().getWindow();
    
                // set new stage and scene
                Scene clientView = new Scene(root);
    
                Stage wideStage = new Stage();
                wideStage.setScene(clientView);
    
                // set stage parameters
                wideStage.setTitle("Ticket Sales System");
                wideStage.getIcons().add(new Image("PROZ/icon.jpg"));
                wideStage.setResizable(false);
    
                primaryStage.close();
                wideStage.show();
            }
            else
            {
                this.signInFailedLabel.setVisible(true);
            }
        }
        catch (SQLException ex)
        {
            ViewMethods.exceptionHandler(ex);
        }
    }
    
    /**
     * If loginField and passwordField is correct, change stage. Else, try
     * again.
     *
     * @param event is key pressed on the keyboard
     */
    @FXML private void logInEnterAction(KeyEvent event)
    throws Exception
    {
        if (event.getCode().equals(KeyCode.ENTER))
        {
            signInAction(event);
        }
    }
    
    /**
     * Change scene.
     *
     * @param event is necessary to get primary stage
     */
    @FXML private void signUpAction(Event event)
    throws Exception
    {
        // get root
        FXMLLoader fxmlLoader = new FXMLLoader();
        Parent root = fxmlLoader.load(
                getClass().getResource("RegisterView.fxml").openStream());
        
        // set model in new controller
        RegisterView registerViewController = (RegisterView) fxmlLoader
                .getController();
        registerViewController.setModel(this.model);
        
        // set new stage parameters
        Stage window = (Stage) ((Node) event.getSource()).getScene()
                                                         .getWindow();
        window.setScene(new Scene(root));
    }
}