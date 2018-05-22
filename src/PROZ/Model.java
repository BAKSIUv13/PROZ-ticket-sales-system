package PROZ;

import java.sql.*;
import java.util.LinkedList;

/**
 * This class is a main class responsible for data.
 * It helps connect to the database, disconnect to the database and
 * provides methods to modify the database.
 */
public class Model
{
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
    
    // Specific model methods below
    
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
                                  + "VALUES (?, ?, ?, ?);");
        
        if (client.getLogin() != null)
        {
            preparedStatement.setString(1, client.getLogin());
        }
        else
        {
            throw new SQLException("Primary key 'login' is null");
        }
        
        if (client.getName() != null)
        {
            preparedStatement.setString(2, client.getName());
        }
        else
        {
            preparedStatement.setNull(2, java.sql.Types.VARCHAR);
        }
        
        if (client.getSurname() != null)
        {
            preparedStatement.setString(3, client.getSurname());
        }
        else
        {
            preparedStatement.setNull(3, java.sql.Types.VARCHAR);
        }
        
        if (client.getCity() != null)
        {
            preparedStatement.setString(4, client.getCity());
        }
        else
        {
            preparedStatement.setNull(4, java.sql.Types.VARCHAR);
        }
        
        preparedStatement.executeUpdate();
    }
    
    /**
     * Remove client and clients tickets form the DataBase. private - TODO
     *
     * @param clientLogin first argument in constructor must be given not null -
     *                    primary key
     */
    private void removeClient(String clientLogin)
    throws SQLException
    {
        PreparedStatement preparedStatement = this.connection
                .prepareStatement("DELETE FROM client "
                                  + "WHERE login = ?;");
        
        preparedStatement.setString(1, clientLogin);
        
        preparedStatement.executeUpdate();
    }
    
    /**
     * Remove ticket from the DataBase.
     *
     * @param ticketId must be given not null - primary key
     */
    public void removeTicket(int ticketId)
    throws SQLException
    {
        PreparedStatement preparedStatement = this.connection
                .prepareStatement("DELETE FROM ticket "
                                  + "WHERE idTicket = ?;");
        
        preparedStatement.setInt(1, ticketId);
        
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
        
        if (client.getName() != null)
        {
            preparedStatement.setString(1, client.getName());
        }
        else
        {
            preparedStatement.setNull(1, java.sql.Types.VARCHAR);
        }
        
        if (client.getSurname() != null)
        {
            preparedStatement.setString(2, client.getSurname());
        }
        else
        {
            preparedStatement.setNull(2, java.sql.Types.VARCHAR);
        }
        
        if (client.getCity() != null)
        {
            preparedStatement.setString(3, client.getCity());
        }
        else
        {
            preparedStatement.setNull(3, java.sql.Types.VARCHAR);
        }
        
        if (client.getLogin() != null)
        {
            preparedStatement.setString(4, client.getLogin());
        }
        else
        {
            throw new SQLException("Primary key 'login' is null");
        }
        
        preparedStatement.executeUpdate();
    }
    
    /**
     * @param clientLogin first argument in constructor must be given not null -
     *                    primary key
     * @return LinkedList containing tickets
     */
    public LinkedList<TicketDB> getClientTickets(String clientLogin)
    throws SQLException
    {
        PreparedStatement preparedStatement = this.connection
                .prepareStatement("SELECT * "
                                  + "FROM ticket "
                                  + "WHERE Client_login = ?;");
        
        preparedStatement.setString(1, clientLogin);
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
     * @param clientLogin first argument in constructor must be given not null -
     *                    primary key
     * @return number of tickets belonging to the client
     */
    public int getNumberOfTickets(String clientLogin)
    throws SQLException
    {
        PreparedStatement preparedStatement = this.connection
                .prepareStatement("SELECT COUNT(idTicket) "
                                  + "FROM ticket "
                                  + "WHERE Client_login = ? "
                                  + "GROUP BY Client_login;");
        
        preparedStatement.setString(1, clientLogin);
        
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
     * @param clientLogin first argument in constructor must be given not null -
     *                    primary key
     * @return max client ticket price
     */
    public int getMaxTicketPrice(String clientLogin)
    throws SQLException
    {
        PreparedStatement preparedStatement = this.connection
                .prepareStatement("SELECT MAX(price) "
                                  + "FROM ticket "
                                  + "WHERE Client_login = ?;");
        
        preparedStatement.setString(1, clientLogin);
        
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
    
    
}