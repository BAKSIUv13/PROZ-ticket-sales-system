package PROZ;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Connector
{
    private static Connection connection;
    
    public static void connect()
    {
        try
        {
            connection = DriverManager.getConnection
                    ("jdbc:mysql://localhost/mydb?useUnicode=true" +
                     "&useJDBCCompliantTimezoneShift=true" +
                     "&useLegacyDatetimeCode=false&serverTimezone=UTC&useSSL"
                     + "=false", "root", "root");
        }
        catch (SQLException ex)
        {
            System.out.println("Connection Failed! Check output console");
            System.out.println(ex.getMessage());
            return;
        }
        if (connection == null)
        {
            System.out.println("Connection failed: connection == null is true");
        }
        else
        {
            System.out.println("Connected with database!");
        }
    }
    
    public static void disconnect()
    {
        if (connection != null)
        {
            try
            {
                connection.close();
                System.out.println("Disconnected with database!");
                
            }
            catch (SQLException ex)
            {
                System.out.println("Disconnection Failed! Check output " +
                                   "console");
                System.out.println(ex.getMessage());
                return;
            }
        }
        else
        {
            System.out.println("You cannot close null connenction");
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
    
}
