package PROZ;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Model
{
    private static Connection connection;
    
    /**
     * If it fails, the program ends.
     *
     * @param url      address to MySQL database
     * @param user
     * @param password
     */
    public static void connect(String url, String user, String password)
    {
        try
        {
            connection = DriverManager.getConnection(url, user, password);
        }
        catch (SQLException ex)
        {
            System.out.println("Problem with connection to MySQL. Exception "
                               + "message:");
            System.out.println(ex.getMessage());
            System.exit(1);
        }
        
        if (connection != null)
        {
            System.out.println("Connect successfully");
        }
        else
        {
            System.out.println("Problem with connection to MySQL. " +
                               "getConnection returns null");
            System.exit(1);
        }
    }
    
    /**
     * If it fails, the program ends.
     */
    public static void disConnect()
    {
        try
        {
            connection.close();
            System.out.println("Disconnect successfully");
        }
        catch (SQLException ex)
        {
            System.out.println("Problem with disconnection to MySQL. " +
                               "Exception " + "message:");
            System.out.println(ex.getMessage());
            System.exit(1);
        }
    }
}


    /*
        public static void execute()
    {
        String sql = "INSERT INTO client (login, name, surname, city)" +
                     "VALUES " +
                     "(?, ?, ?, ?)";
        try
        {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, "Cacek");
            statement.setString(2, "Grzegorz");
            statement.setString(3, "Kulik");
            statement.setString(4, "Huta Nowa");
            
            statement.executeUpdate();
        }
        catch (SQLException ex)
        {
            System.out.println(ex.getMessage());
        }
    }
    */