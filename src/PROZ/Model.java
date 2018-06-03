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
    static final public int MIN_PASSWORD_LENGTH = 4;
    static private int counter; // only getter...
    
    static
    {
        counter = 0;
    }
    
    private Connection connection; // to MySQL server
    
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
     * @param client first argument in constructor must be given not null and
     *               not empty string - primary key
     */
    public void addClient(ClientDB client)
    throws SQLException
    {
        PreparedStatement preparedStatement = this.connection
                .prepareStatement("INSERT INTO client "
                                  + "VALUES (?, ?, ?, ?, ?);");
    
        if (client.getLogin() != null && !client.getLogin().equals(""))
        {
            preparedStatement.setString(1, client.getLogin());
        }
        else
        {
            throw new SQLException("Primary key login is empty or null");
        }
        
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
        // set password as null - we change it in other query, because some of
        // users can be not register in system
        preparedStatement.setNull(5, Types.INTEGER);
        
        preparedStatement.executeUpdate();
    }
    
    /**
     * Remove client and clients tickets form the DataBase.
     *
     * @param clientLogin first argument must be given not null - primary key
     */
    public void removeClient(String clientLogin)
    throws SQLException
    {
        LinkedList<TicketDB> clientTickets = getClientTickets(clientLogin);
        
        for (TicketDB ticket : clientTickets)
        {
            removeTicket(ticket.getIdTicket());
        }
        
        PreparedStatement preparedStatement = this.connection
                .prepareStatement("DELETE FROM client "
                                  + "WHERE login = ?;");
    
        if (clientLogin != null)
        {
            preparedStatement.setString(1, clientLogin);
        }
        else
        {
            throw new SQLException("Primary key clientLogin is null");
        }
        
        preparedStatement.executeUpdate();
    }
    
    /**
     * Remove ticket from the DataBase.
     *
     * @param ticketId first argument must be given not null - primary key
     */
    public void removeTicket(Integer ticketId)
    throws SQLException
    {
        PreparedStatement preparedStatement = this.connection
                .prepareStatement("DELETE FROM ticket "
                                  + "WHERE idTicket = ?;");
    
        if (ticketId != null)
        {
            preparedStatement.setInt(1, ticketId);
        }
        else
        {
            throw new SQLException("Primary key ticketID is null");
        }
        
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
    
        if (client.getLogin() != null)
        {
            preparedStatement.setString(4, client.getLogin());
        }
        else
        {
            throw new SQLException("Primary key login is empty or null");
        }
        
        preparedStatement.executeUpdate();
    }
    
    /**
     * @param clientLogin first argument must be given not null - primary key
     * @return LinkedList containing tickets
     */
    public LinkedList<TicketDB> getClientTickets(String clientLogin)
    throws SQLException
    {
        PreparedStatement preparedStatement = this.connection
                .prepareStatement("SELECT * "
                                  + "FROM ticket "
                                  + "WHERE Client_login = ?;");
    
        if (clientLogin != null)
        {
            preparedStatement.setString(1, clientLogin);
        }
        else
        {
            throw new SQLException("Primary key clientLogin is null");
        }
        
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
                    resultSet.getString(6), resultSet.getInt(7)));
        }
        
        return events;
    }
    
    /**
     * @param id ticket - not null
     * @return ticket with given id
     */
    public CulturalEventDB getCulturalEvent(Integer id)
    throws SQLException
    {
        PreparedStatement preparedStatement = this.connection
                .prepareStatement("SELECT * "
                                  + "FROM culturalevent "
                                  + "WHERE idCulturalEvent = ?;");
        if (id != null)
        {
            preparedStatement.setInt(1, id);
        }
        else
        {
            throw new SQLException("id is null");
        }
        
        ResultSet resultSet = preparedStatement.executeQuery();
        
        CulturalEventDB event = null;
        
        if (resultSet.next())
        {
            event = new CulturalEventDB(resultSet.getInt(1), resultSet
                    .getString(2), resultSet.getDate(3), resultSet.getInt(4),
                    resultSet.getString(5), resultSet.getString(6), resultSet
                    .getInt(7));
        }
        
        return event;
    }
    
    /**
     * @param clientLogin first argument must be given not null - primary key
     * @param password    must be given not null
     */
    public void changePassword(String clientLogin, String password)
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
    
        if (clientLogin != null)
        {
            preparedStatement.setString(2, clientLogin);
        }
        else
        {
            throw new SQLException("Primary key clientLogin is null");
        }
        
        preparedStatement.executeUpdate();
    }
    
    /**
     * @param clientLogin first argument must be given not null - primary key
     * @return true if LogIn is correct
     */
    public boolean isCorrectLogIn(String clientLogin, String password)
    throws SQLException
    {
        PreparedStatement preparedStatement = this.connection
                .prepareStatement("SELECT password "
                                  + "FROM client "
                                  + "WHERE login = ? ;");
    
        if (clientLogin != null)
        {
            preparedStatement.setString(1, clientLogin);
        }
        else
        {
            throw new SQLException("Primary key clientLogin is null");
        }
        
        ResultSet result = preparedStatement.executeQuery();
        
        if (result.next())
        {
            if (result.getInt(1) == password.hashCode())
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
     * @param clientLogin first argument must be given not null - primary key
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
    
        if (clientLogin != null)
        {
            preparedStatement.setString(1, clientLogin);
        }
        else
        {
            throw new SQLException("Primary key clientLogin is null");
        }
        
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
     * @param clientLogin first argument must be given not null - primary key
     * @return max client ticket price
     */
    public int getMaxTicketPrice(String clientLogin)
    throws SQLException
    {
        PreparedStatement preparedStatement = this.connection
                .prepareStatement("SELECT MAX(price) "
                                  + "FROM ticket "
                                  + "WHERE Client_login = ?;");
    
        if (clientLogin != null)
        {
            preparedStatement.setString(1, clientLogin);
        }
        else
        {
            throw new SQLException("Primary key clientLogin is null");
        }
        
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
     * @param clientLogin first argument must be given not null - primary key
     * @return true, if client is in the DataBase
     */
    public boolean isClient(String clientLogin)
    throws SQLException
    {
        PreparedStatement preparedStatement = this.connection
                .prepareStatement("SELECT login "
                                  + "FROM client "
                                  + "WHERE login = ? ;");
    
        if (clientLogin != null)
        {
            preparedStatement.setString(1, clientLogin);
        }
        else
        {
            throw new SQLException("Primary key clientLogin is null");
        }
        
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
    
    /**
     * @param ticketId first argument must be given not null - primary key
     * @return performers related with ticket
     */
    public LinkedList<PerformerDB> getPerformers(Integer ticketId)
    throws SQLException
    {
        PreparedStatement preparedStatement = this.connection
                .prepareStatement("SELECT Performer_name "
                                  + "FROM culturalevent_has_performer "
                                  + "WHERE CulturalEvent_idCulturalEvent = "
                                  + "(SELECT CulturalEvent_idCulturalEvent "
                                  + "FROM ticket "
                                  + "WHERE idTicket =  ?);");
    
        if (ticketId != null)
        {
            preparedStatement.setInt(1, ticketId);
        }
        else
        {
            throw new SQLException("Primary key ticketID is null");
        }
        
        ResultSet resultSet = preparedStatement.executeQuery();
        
        LinkedList<PerformerDB> performers = new LinkedList<>();
        while (resultSet.next())
        {
            performers.add(new PerformerDB(resultSet.getString(1)));
        }
        
        return performers;
    }
    
    /**
     * @param clientLogin first argument must be given not null - primary key
     * @return client events
     */
    public LinkedList<CulturalEventDB> getClientEvents(String clientLogin)
    throws SQLException
    {
        PreparedStatement preparedStatement = this.connection.prepareStatement(
                "select\n"
                + "  culturalevent.idCulturalEvent,\n"
                + "  culturalevent.date,\n"
                + "  culturalevent.Place_city,\n"
                + "  culturalevent.Place_street\n"
                + "from culturalevent\n"
                + "  inner join ticket t\n"
                + "    on culturalevent.idCulturalEvent = t"
                + ".CulturalEvent_idCulturalEvent\n"
                + "  where t.Client_login = ?;");
        
        
        if (clientLogin != null)
        {
            preparedStatement.setString(1, clientLogin);
        }
        else
        {
            throw new SQLException("Primary key clientLogin is null");
        }
        
        ResultSet resultSet = preparedStatement.executeQuery();
        
        LinkedList<CulturalEventDB> events = new LinkedList<>();
        while (resultSet.next())
        {
            events.add(new CulturalEventDB(resultSet.getInt(1), null,
                    resultSet.getDate(2), null, resultSet.getString(3),
                    resultSet.getString(4), null));
        }
        
        return events;
    }
    
    public LinkedList<PerformerDB> getAllPerformers()
    throws SQLException
    {
        PreparedStatement preparedStatement = this.connection
                .prepareStatement("SELECT name "
                                  + "FROM performer;");
        
        ResultSet resultSet = preparedStatement.executeQuery();
        
        LinkedList<PerformerDB> performers = null;
        
        while (resultSet.next())
        {
            performers.add(new PerformerDB(resultSet.getString(1)));
        }
        
        return performers;
    }
    
    public LinkedList<CulturalEventHasPerformer> getEventsAndPerformers()
    throws SQLException
    {
        PreparedStatement preparedStatement = this.connection
                .prepareStatement("SELECT * "
                                  + "FROM culturalevent_has_performer;");
        
        ResultSet resultSet = preparedStatement.executeQuery();
        
        LinkedList<CulturalEventHasPerformer> eventsAndPerformers = new
                LinkedList<>();
        
        while (resultSet.next())
        {
            eventsAndPerformers.add(new CulturalEventHasPerformer(resultSet
                    .getInt(1), resultSet.getString(2)));
        }
        
        return eventsAndPerformers;
    }
    
    public void addTicket(CulturalEventDB event, String clientLogin)
    throws SQLException
    {
        PreparedStatement preparedStatement = this.connection
                .prepareStatement("insert into ticket(price, Client_login, "
                                  + "CulturalEvent_idCulturalEvent) "
                                  + "values (?, ?, ?);");
        
        preparedStatement.setInt(1, event.getActualTicketPrice());
        
        if (clientLogin != null)
        {
            preparedStatement.setString(2, clientLogin);
        }
        else
        {
            throw new SQLException("clientLogin is null");
        }
        
        if (event.getIdCulturalEvent() != null)
        {
            preparedStatement.setInt(3, event.getIdCulturalEvent());
        }
        else
        {
            throw new SQLException("event doesn't have id");
        }
        
        preparedStatement.executeUpdate();
    }
}
