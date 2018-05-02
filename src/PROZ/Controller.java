package PROZ;

import java.sql.SQLException;

public class Controller
{
    private Model model;
    private View view;
    
    public Controller(Model model, View view)
    {
        this.model = model;
        this.view = view;
        
        this.main();
    }
    
    /**
     * Main function in the controller.
     */
    private void main()
    {
        this.view.connectionSuccess();
        
        this.view.doSomething("do something");
        
        this.disconnectingTheModelFromTheDatabase();
    }
    
    private void disconnectingTheModelFromTheDatabase()
    {
        try
        {
            this.model.disconnect();
        }
        catch (SQLException ex)
        {
            this.view.disconnectionFail();
        }
        
        this.view.disconnectionSuccess();
    }
}