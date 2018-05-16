package PROZ;

import java.sql.SQLException;

/**
 * Controller gets a model and it's connected to view.
 */
public class Controller
{
    private Model model;
    
    /**
     * Construct controller based on model.
     *
     * @param model on which we will operate
     */
    public Controller(Model model)
    {
        this.model = model;
    }
}