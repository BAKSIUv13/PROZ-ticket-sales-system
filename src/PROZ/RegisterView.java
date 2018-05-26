package PROZ;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
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

import java.sql.SQLException;

/**
 * @author BAKSIUv13
 */
public class RegisterView
{
    @FXML Label signUpFailed;
    @FXML Label signUpOK;
    @FXML Button signUp;
    @FXML Button goBack;
    @FXML TextField login;
    @FXML PasswordField password;
    @FXML PasswordField passwordAgain;
    @FXML TextField name;
    @FXML TextField surName;
    @FXML TextField city;
    
    private Model model;
    
    /**
     * Go back to LogInView.
     */
    public void goBackAction(Event event)
    throws Exception
    {
        // get root
        FXMLLoader fxmlLoader = new FXMLLoader();
        Parent root = fxmlLoader.load(
                getClass().getResource("LogInView.fxml").openStream());
        
        // set model in new controller
        LogInView logInViewController = (LogInView) fxmlLoader.getController();
        logInViewController.setModel(this.model);
        
        // set new stage parameters
        Stage window = (Stage) ((Node) event.getSource()).getScene()
                                                         .getWindow();
        window.setScene(new Scene(root));
    }
    
    /**
     * It try sign up a user.
     */
    public void signUpAction()
    {
        this.signUpFailed.setText(""); // clear message
        
        if (this.login.getText().equals(""))
        {
            this.signUpFailed.setText("Empty login field.");
        }
        else if (!isClient(createClient(this.login.getText())))
        {
            if (this.password.getText().length() < this.model.PASSWORD_LENGTH)
            {
                this.signUpFailed.setText("Minimum " +
                                          this.model.PASSWORD_LENGTH +
                                          " characters");
            }
            else if (this.password.getText().equals(
                    this.passwordAgain.getText()))
            {
                ClientDB clientDB = createClient(this.login.getText(),
                        this.name.getText(), this.surName.getText(),
                        this.city.getText());
                
                addClient(clientDB);
                changePassword(clientDB, this.password.getText());
                
                this.signUpFailed.setText("");
                this.signUpOK.setVisible(true);
            }
            else
            {
                this.signUpFailed.setText("Different passwords");
            }
        }
        else
        {
            this.signUpFailed.setText("Client with login: " + this.login
                    .getText() + " exist");
        }
    }
    
    /**
     * It try sign up a user.
     */
    public void signUpEnterAction(KeyEvent event)
    {
        if (event.getCode().equals(KeyCode.ENTER))
        {
            signUpAction();
        }
    }
    
    // model functions packaged in error display
    
    private boolean isClient(ClientDB client)
    {
        boolean isClient = false;
        try
        {
            isClient = this.model.isClient(
                    new ClientDB(this.login.getText(), null, null,
                            null));
        }
        catch (SQLException ex)
        {
            ViewMethods.exceptionHandler(ex);
        }
        
        return isClient;
    }
    
    private ClientDB createClient(String username)
    {
        ClientDB client = null;
        
        try
        {
            client = new ClientDB(username, null, null, null);
        }
        catch (SQLException ex)
        {
            ViewMethods.exceptionHandler(ex);
        }
        
        return client;
    }
    
    private ClientDB createClient(String login, String name, String
            surname, String city)
    {
        ClientDB client = null;
        
        try
        {
            client = new ClientDB(login, name, surname, city);
        }
        catch (SQLException ex)
        {
            ViewMethods.exceptionHandler(ex);
        }
        
        return client;
    }
    
    private void addClient(ClientDB client)
    {
        try
        {
            this.model.addClient(client);
        }
        catch (SQLException ex)
        {
            ViewMethods.exceptionHandler(ex);
        }
    }
    
    private void changePassword(ClientDB client, String password)
    {
        try
        {
            this.model.changePassword(client, password);
        }
        catch (SQLException ex)
        {
            ViewMethods.exceptionHandler(ex);
        }
    }
    
    public void setModel(Model model)
    {
        this.model = model;
    }
}