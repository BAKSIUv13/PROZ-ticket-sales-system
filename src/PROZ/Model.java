package PROZ;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Model
{
    private Connection connection;
    
    public Model(String mysqlURL, String user, String password)
    throws SQLException
    {
        this.connection = DriverManager.getConnection(mysqlURL, user, password);
        if (this.connection == null)
        {
            throw new SQLException(
                    "DriverManager.getConnection() returns null");
        }
    }
    
    /**
     * Disconnecting with the database.
     *
     * @throws SQLException when the disconnection is not possible
     */
    public void disconnect()
    throws SQLException
    {
        this.connection.close();
    }
    
    
}