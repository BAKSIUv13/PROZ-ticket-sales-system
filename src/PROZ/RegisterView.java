package PROZ;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class RegisterView
        implements Initializable
{
    private Model model;
    
    @FXML
    private Button signUp;
    
    @Override public void initialize(URL location, ResourceBundle resources)
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
    
    public void signUpAction(ActionEvent event)
    throws IOException
    {
        // TODO
        
        Parent root = FXMLLoader.load(
                getClass().getResource("LogInView.fxml"));
        Scene newScene = new Scene(root);
        
        //This line gets the Stage information
        Stage window = (Stage) ((Node) event.getSource()).getScene()
                                                         .getWindow();
        
        window.setScene(newScene);
        window.show();
    }
    
    private void testModel()
    throws SQLException
    {
    
    }
}
