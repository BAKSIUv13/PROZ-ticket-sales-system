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
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.sql.SQLException;

/**
 * RegisterView.fxml controller.
 *
 * @author BAKSIUv13
 */
public class RegisterView
{
    @FXML Label signUpLabel;
    
    @FXML Button signUpButton;
    @FXML Button goBackButton;
    
    @FXML TextField loginField;
    @FXML TextField nameField;
    @FXML TextField surNameField;
    @FXML TextField cityField;
    
    @FXML PasswordField passwordField;
    @FXML PasswordField passwordAgainField;
    
    private Model model;
    
    /**
     * Go back to LogInView.
     *
     * @param event is necessary to get primary stage
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
        logInViewController.setLoginField(this.loginField.getText());
        
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
        this.signUpLabel.setText(""); // clear message
    
        if (this.loginField.getText().equals(""))
        {
            this.signUpLabel.setTextFill(Color.web("#b90000"));
            this.signUpLabel.setText("Empty loginField field.");
        }
        else if (!isClient(this.loginField.getText()))
        {
            if (this.passwordField.getText().length()
                < this.model.MIN_PASSWORD_LENGTH)
            {
                this.signUpLabel.setTextFill(Color.web("#b90000"));
                this.signUpLabel.setText("Minimum " +
                                         this.model.MIN_PASSWORD_LENGTH +
                                         " characters");
            }
            else if (this.passwordField.getText().equals(
                    this.passwordAgainField.getText()))
            {
                ClientDB clientDB = createClient(this.loginField.getText(),
                        this.nameField.getText(), this.surNameField.getText(),
                        this.cityField.getText());
                
                addClient(clientDB);
                changePassword(clientDB.getLogin(),
                        this.passwordField.getText());
                
                this.signUpLabel.setTextFill(Color.web("#6bd700"));
                this.signUpLabel.setText("Signed Up!");
            }
            else
            {
                this.signUpLabel.setTextFill(Color.web("#b90000"));
                this.signUpLabel.setText("Different passwords");
            }
        }
        else
        {
            this.signUpLabel.setTextFill(Color.web("#b90000"));
            this.signUpLabel.setText(
                    "Client with loginField: " + this.loginField
                            .getText() + " exist");
        }
    }
    
    /**
     * It try sign up a user.
     *
     * @param event is necessary to recognize key
     */
    public void signUpEnterAction(KeyEvent event)
    {
        if (event.getCode().equals(KeyCode.ENTER))
        {
            signUpAction();
        }
    }
    
    // model functions packaged in error display
    
    private boolean isClient(String clientLogin)
    {
        boolean isClient = false;
        try
        {
            isClient = this.model.isClient(clientLogin);
        }
        catch (SQLException ex)
        {
            ViewMethods.exceptionHandler(ex);
        }
        
        return isClient;
    }
    
    private ClientDB createClient(String login, String name, String
            surname, String city)
    {
        ClientDB client = null;
        client = new ClientDB(login, name, surname, city);
        
        return client;
    }
    
    /**
     * try add given client
     */
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
    
    private void changePassword(String clientLogin, String password)
    {
        try
        {
            this.model.changePassword(clientLogin, password);
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