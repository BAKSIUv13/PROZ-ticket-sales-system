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

public class RegisterView
        implements Initializable
{
    @FXML Label signUpFailed;
    @FXML Label signUpOK;
    @FXML Button signUp;
    @FXML Button goBack;
    @FXML TextField username;
    @FXML PasswordField password;
    @FXML PasswordField passwordAgain;
    @FXML TextField name;
    @FXML TextField surname;
    @FXML TextField city;
    
    private Model model;
    
    private void testModel()
    throws SQLException
    {
    
    }
    
    @Override public void initialize(URL location, ResourceBundle resources)
    {
        this.model = Model.getModel();
        try
        {
            testModel();
        }
        catch (SQLException ex)
        {
            System.out.println("testModel. Exception "
                               + "message: ");
            System.out.println(ex.getMessage());
            System.exit(-1);
        }
    }
    
    public void goBackAction(ActionEvent event)
    throws IOException
    {
        Parent root = FXMLLoader.load(
                getClass().getResource("LogInView.fxml"));
        Scene newScene = new Scene(root);
        
        //This line gets the Stage information
        Stage window = (Stage) ((Node) event.getSource()).getScene()
                                                         .getWindow();
        
        window.setScene(newScene);
        window.show();
    }
    
    public void signUpAction()
    {
        this.signUpFailed.setText(""); // clear message
    
        if (this.username.getText().equals(""))
        {
            this.signUpFailed.setText("Empty username field.");
        }
        else if (!isClient(createClient(this.username.getText())))
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
                ClientDB clientDB = createClient(this.username.getText(),
                        this.name.getText(), this.surname.getText(),
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
            this.signUpFailed.setText("Client with username: " + this.username
                    .getText() + " exist");
        }
    }
    
    public void signUpEnterAction(KeyEvent event)
    {
        if (event.getCode().equals(KeyCode.ENTER))
        {
            signUpAction();
        }
    }
    
    private boolean isClient(ClientDB client)
    {
        boolean isClient = false;
        try
        {
            isClient = this.model.isClient(
                    new ClientDB(this.username.getText(), null, null,
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
}
/*
        try
        {
        
        }
        catch (SQLException ex)
        {
            ViewMethods.exceptionHandler(ex);
        }
 */