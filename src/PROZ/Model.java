package PROZ;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * This class is a main class responsible for data.
 * It helps connect to the database, disconnect to the database and
 * provides methods to modify our database.
 */
public class Model
{
    private Connection connection;
    
    /**
     * Based on the given arguments, it connects to the database.
     *
     * @param mysqlURL Example: "jdbc:mysql://localhost/databaseName"
     * @param user     Example: "root"
     * @param password Example: "admin"
     * @throws SQLException when the connection is not possible
     */
    public Model(String mysqlURL, String user, String password)
    throws SQLException
    {
        this.connection = DriverManager.getConnection(mysqlURL, user, password);
        if (this.connection == null)
        {
            throw new SQLException("DriverManager.getConnection() returns "
                                   + "null");
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