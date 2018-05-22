package PROZ;

import javafx.fxml.Initializable;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

/**
 * Controller create a model and it's connected to view.
 */
public class Controller
        implements Initializable
{
    private Model model;
    
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
    {
    
    }
}