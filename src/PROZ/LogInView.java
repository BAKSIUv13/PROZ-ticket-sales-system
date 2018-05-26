package PROZ;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.sql.SQLException;

/**
 * LogInView create a model and it's connected to view.
 *
 * @author BAKSIUv13
 */
public class LogInView
{
    @FXML private TextField login;
    
    @FXML private PasswordField password;
    
    @FXML private Label signInFailed;
    
    private Model model;
    
    /**
     * If login and password is correct, change stage. Else, try again.
     */
    public void signInAction(Event event)
    throws Exception
    {
        String login = this.login.getText();
        try
        {
            if (this.model.isCorrectLogIn(login, this.password.getText()))
            {
                this.signInFailed.setVisible(false);
    
                // get root
                FXMLLoader fxmlLoader = new FXMLLoader();
                Parent root = fxmlLoader.load(
                        getClass().getResource("ClientView.fxml").openStream());
    
                // set model in new controller
                ClientView clientViewController = (ClientView) fxmlLoader
                        .getController();
                clientViewController.setModel(this.model);
                clientViewController.setLogin(login);
    
                // get stage
                Stage primaryStage = (Stage) ((Node) event.getSource())
                        .getScene().getWindow();
                primaryStage.hide();
    
                // set new stage
                Scene clientView = new Scene(root);
                primaryStage.setScene(clientView);
                primaryStage.setMinWidth(1024);
                primaryStage.setMinHeight(576);
                primaryStage.setResizable(true);
    
                primaryStage.show();
            }
            else
            {
                this.signInFailed.setVisible(true);
            }
        }
        catch (SQLException ex)
        {
            ViewMethods.exceptionHandler(ex);
        }
    }
    
    /**
     * If login and password is correct, change stage. Else, try again.
     */
    public void logInEnterAction(KeyEvent event)
    throws Exception
    {
        if (event.getCode().equals(KeyCode.ENTER))
        {
            signInAction((Event) event);
        }
    }
    
    /**
     * Change scene.
     */
    public void signUpAction(ActionEvent event)
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
    
    public void setModel(Model model)
    {
        this.model = model;
    }
    
    public void setLogin(String text)
    {
        this.login.setText(text);
    }
    
    
}