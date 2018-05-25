package PROZ;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

/**
 * LogInView create a model and it's connected to view.
 */
public class LogInView
        implements Initializable
{
    private Model model;
    
    @FXML
    private TextField login;
    
    @FXML
    private PasswordField password;
    
    @FXML
    private Button signIn;
    
    @FXML
    private Button signUp;
    
    @FXML
    private Label signInFailed;
    
    /**
     * Initialize model before GUI start.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        try
        {
            this.model = new Model();
            testModel();
        }
        catch (SQLException ex)
        {
            System.out.println("Problem with connection to MySQL. Exception "
                               + "message: ");
            System.out.println(ex.getMessage());
            System.exit(-1);
        }
    }
    
    private void testModel()
    throws SQLException
    {
    
    }
    
    /**
     * If username and password is correct, change stage. Else, try again.
     */
    public void signInAction()
    {
        try
        {
            if (this.model.isCorrectLogIn(this.login.getText(),
                    this.password.getText()))
            {
                // change stage
                this.signInFailed.setVisible(false);
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
    
    public void logInEnterAction(KeyEvent event)
    {
        if (event.getCode().equals(KeyCode.ENTER))
        {
            signInAction();
        }
    }
    
    public void signUpAction(ActionEvent event)
    throws IOException
    {
        Parent root = FXMLLoader.load(
                getClass().getResource("RegisterView.fxml"));
        Scene newScene = new Scene(root);
        
        //This line gets the Stage information
        Stage window = (Stage) ((Node) event.getSource()).getScene()
                                                         .getWindow();
        
        window.setScene(newScene);
        window.show();
    }
    
    
}