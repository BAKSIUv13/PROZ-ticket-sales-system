package PROZ;

import java.sql.SQLException;

public class connectToMyDatabase
{
    public static Model getModel()
    {
        try
        {
            Model model = new Model
                    ("jdbc:mysql://localhost/mydb?useUnicode=true" +
                     "&useJDBCCompliantTimezoneShift=true" +
                     "&useLegacyDatetimeCode=false&serverTimezone=UTC&useSSL"
                     + "" + "=false", "root", "root");
            
            return model;
        }
        catch (SQLException ex)
        {
            System.out.println("Problem with connection to MySQL. Exception "
                               + "message:");
            System.out.println(ex.getMessage());
            System.exit(-1);
        }
        
        return null;
    }
}
