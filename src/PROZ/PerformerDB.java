package PROZ;

/**
 * PerformerDB is a Class which helps to send a data between DataBase and
 * Model&LogInView.
 *
 * @author BAKSIUv13
 */
public class PerformerDB
{
    private String name;
    
    public PerformerDB(String name)
    {
        this.name = name;
    }
    
    public PerformerDB(PerformerDB performer)
    {
        this.name = performer.name;
    }
    
    public String getName()
    {
        return this.name;
    }
}