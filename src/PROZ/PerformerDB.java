package PROZ;

import java.sql.SQLException;

/**
 * PerformerDB is a Class which helps to send a data between DataBase and
 * * Model&LogInView.
 */
public class PerformerDB
{
    private String name;
    
    public PerformerDB(String name)
    throws SQLException
    {
        if (name != null)
        {
            this.name = name;
        }
        else
        {
            throw new SQLException("name is a primary key and it cannot be "
                                   + "null");
        }
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
