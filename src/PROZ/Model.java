package PROZ;

import java.sql.*;
import java.util.LinkedList;

/**
 * This class is a main class responsible for data.
 * It helps connect to the database, disconnect to the database and
 * provides methods to modify the database.
 *
 * @author BAKSIUv13
 */
public class Model
{
    static final public int PASSWORD_LENGTH = 4;
    static private int counter; // only getter...
    
    static
    {
        counter = 0;
    }
    
    private Connection connection;
    
    /**
     * It connect to the DataBase.
     *
     * @throws SQLException when the connection is not possible
     */
    public Model()
    throws SQLException
    {
        this.connection = DriverManager.getConnection(
                "jdbc:mysql://localhost/mydb?useUnicode=true" +
                "&useJDBCCompliantTimezoneShift=true" +
                "&useLegacyDatetimeCode=false&serverTimezone=UTC&useSSL"
                + "" + "=false", "root", "root");
        if (this.connection == null)
        {
            throw new SQLException("DriverManager.getConnection() returns "
                                   + "null");
        }
    
        ++counter;
    }
    
    public static int getCounter()
    {
        return counter;
    }
    
    // Specific model methods below
    
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
    
    /**
     * Add new client to the DataBase. Login is a primary key.
     *
     * @param client first argument in constructor must be given not null -
     *               primary key
     */
    public void addClient(ClientDB client)
    throws SQLException
    {
        PreparedStatement preparedStatement = this.connection
                .prepareStatement("INSERT INTO client "
                                  + "VALUES (?, ?, ?, ?, ?);");
        
        preparedStatement.setString(1, client.getLogin());
    
        if (client.getName() != null && !client.getName().equals(""))
        {
            preparedStatement.setString(2, client.getName());
        }
        else
        {
            preparedStatement.setNull(2, java.sql.Types.VARCHAR);
        }
    
        if (client.getSurName() != null && !client.getSurName().equals(""))
        {
            preparedStatement.setString(3, client.getSurName());
        }
        else
        {
            preparedStatement.setNull(3, java.sql.Types.VARCHAR);
        }
    
        if (client.getCity() != null && !client.getCity().equals(""))
        {
            preparedStatement.setString(4, client.getCity());
        }
        else
        {
            preparedStatement.setNull(4, java.sql.Types.VARCHAR);
        }
    
        preparedStatement.setNull(5, Types.INTEGER);
        
        preparedStatement.executeUpdate();
    }
    
    /**
     * Remove client and clients tickets form the DataBase.
     */
    public void removeClient(ClientDB client)
    throws SQLException
    {
        LinkedList<TicketDB> clientTickets = getClientTickets(client);
    
        for (TicketDB ticket : clientTickets)
        {
            removeTicket(ticket);
        }
        
        PreparedStatement preparedStatement = this.connection
                .prepareStatement("DELETE FROM client "
                                  + "WHERE login = ?;");
    
        preparedStatement.setString(1, client.getLogin());
        
        preparedStatement.executeUpdate();
    }
    
    /**
     * Remove ticket from the DataBase.
     *
     * @param ticket first argument in constructor must be given not null -
     *               primary key
     */
    public void removeTicket(TicketDB ticket)
    throws SQLException
    {
        PreparedStatement preparedStatement = this.connection
                .prepareStatement("DELETE FROM ticket "
                                  + "WHERE idTicket = ?;");
    
        preparedStatement.setInt(1, ticket.getIdTicket());
        
        preparedStatement.executeUpdate();
    }
    
    /**
     * Update client.
     *
     * @param client first argument in constructor must be given not null -
     *               primary key
     */
    public void updateClient(ClientDB client)
    throws SQLException
    {
        PreparedStatement preparedStatement = this.connection
                .prepareStatement("UPDATE client "
                                  + "SET "
                                  + "name = ?, "
                                  + "surname = ?, "
                                  + "city = ? "
                                  + "WHERE login = ?;");
    
        if (client.getName() != null && !client.getName().equals(""))
        {
            preparedStatement.setString(1, client.getName());
        }
        else
        {
            preparedStatement.setNull(1, java.sql.Types.VARCHAR);
        }
    
        if (client.getSurName() != null && !client.getSurName().equals(""))
        {
            preparedStatement.setString(2, client.getSurName());
        }
        else
        {
            preparedStatement.setNull(2, java.sql.Types.VARCHAR);
        }
    
        if (client.getCity() != null && !client.getCity().equals(""))
        {
            preparedStatement.setString(3, client.getCity());
        }
        else
        {
            preparedStatement.setNull(3, java.sql.Types.VARCHAR);
        }
    
        preparedStatement.setString(4, client.getLogin());
        
        preparedStatement.executeUpdate();
    }
    
    /**
     * @param client first argument in constructor must be given not null -
     *               primary key
     * @return LinkedList containing tickets
     */
    public LinkedList<TicketDB> getClientTickets(ClientDB client)
    throws SQLException
    {
        PreparedStatement preparedStatement = this.connection
                .prepareStatement("SELECT * "
                                  + "FROM ticket "
                                  + "WHERE Client_login = ?;");
    
        preparedStatement.setString(1, client.getLogin());
        ResultSet resultSet = preparedStatement.executeQuery();
        
        LinkedList<TicketDB> tickets = new LinkedList<>();
        while (resultSet.next())
        {
            tickets.add(new TicketDB(resultSet.getInt(1), resultSet.getInt(2),
                    resultSet.getBoolean(3), resultSet.getString(4),
                    resultSet.getInt(5)));
        }
        
        return tickets;
    }
    
    /**
     * @return all cultural events
     */
    public LinkedList<CulturalEventDB> getCulturalEvents()
    throws SQLException
    {
        PreparedStatement preparedStatement = this.connection
                .prepareStatement("SELECT * "
                                  + "FROM culturalevent;");
        
        ResultSet resultSet = preparedStatement.executeQuery();
        
        LinkedList<CulturalEventDB> events = new LinkedList<>();
        while (resultSet.next())
        {
            events.add(new CulturalEventDB(resultSet.getInt(1),
                    resultSet.getString(2), resultSet.getDate(3),
                    resultSet.getInt(4), resultSet.getString(5),
                    resultSet.getString(6)));
        }
        
        return events;
    }
    
    /**
     * @param client   first argument in constructor must be given not null -
     *                 primary key
     * @param password must be given not null
     */
    public void changePassword(ClientDB client, String password)
    throws SQLException
    {
        PreparedStatement preparedStatement = this.connection
                .prepareStatement("UPDATE client "
                                  + "SET "
                                  + "password = ? "
                                  + "WHERE login = ?;");
    
        if (password != null)
        {
            preparedStatement.setInt(1, password.hashCode());
        }
        else
        {
            throw new SQLException("password is null");
        }
    
        preparedStatement.setString(2, client.getLogin());
    
        preparedStatement.executeUpdate();
    }
    
    public boolean isCorrectLogIn(String username, String password)
    throws SQLException
    {
        PreparedStatement preparedStatement = this.connection
                .prepareStatement("SELECT password "
                                  + "FROM client "
                                  + "WHERE login = ? ;");
        
        preparedStatement.setString(1, username);
        ResultSet result = preparedStatement.executeQuery();
        
        if (result.next())
        {
            if (result.getInt(1) == password
                    .hashCode())
            {
                return true;
            }
            else
            {
                return false;
            }
        }
        else
        {
            return false;
        }
    }
    
    /**
     * @param client first argument in constructor must be given not null -
     *               primary key
     * @return number of tickets belonging to the client
     */
    public int getNumberOfTickets(ClientDB client)
    throws SQLException
    {
        PreparedStatement preparedStatement = this.connection
                .prepareStatement("SELECT COUNT(idTicket) "
                                  + "FROM ticket "
                                  + "WHERE Client_login = ? "
                                  + "GROUP BY Client_login;");
    
        preparedStatement.setString(1, client.getLogin());
        
        ResultSet result = preparedStatement.executeQuery();
        
        if (result.next())
        {
            return result.getInt(1);
        }
        else
        {
            return 0; // no client = no tickets
        }
    }
    
    /**
     * @param client first argument in constructor must be given not null -
     *               primary key
     * @return max client ticket price
     */
    public int getMaxTicketPrice(ClientDB client)
    throws SQLException
    {
        PreparedStatement preparedStatement = this.connection
                .prepareStatement("SELECT MAX(price) "
                                  + "FROM ticket "
                                  + "WHERE Client_login = ?;");
    
        preparedStatement.setString(1, client.getLogin());
        
        ResultSet result = preparedStatement.executeQuery();
        
        if (result.next())
        {
            return result.getInt(1);
        }
        else
        {
            return 0;
        }
    }
    
    /**
     * @return true, if client is in the DataBase
     */
    public boolean isClient(ClientDB client)
    throws SQLException
    {
        PreparedStatement preparedStatement = this.connection
                .prepareStatement("SELECT login "
                                  + "FROM client "
                                  + "WHERE login = ? ;");
        
        preparedStatement.setString(1, client.getLogin());
        ResultSet result = preparedStatement.executeQuery();
        
        if (result.next())
        {
            return true;
        }
        else
        {
            return false;
        }
    }
}