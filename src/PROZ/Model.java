package PROZ;

import java.sql.*;
import java.util.LinkedList;

/**
 * This class is a main class responsible for data.
 * It helps connect to the database, disconnect to the database and
 * provides methods to modify the database. It also allows you to extract
 * information from the database.
 *
 * @author BAKSIUv13
 */
public class Model
{
    // passwordField must have more than 4 characters
    static final public int MIN_PASSWORD_LENGTH = 4;
    // MySQL connection
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
     * @throws SQLException when client loginField is null or empty string or
     *                      occur problem with the database
     */
    public void addClient(ClientDB client)
    throws SQLException
    {
        PreparedStatement preparedStatement = this.connection
                .prepareStatement("insert into client "
                                  + "values (?, ?, ?, ?, ?);");
        
        if (client.getLogin() != null && !client.getLogin().equals(""))
        {
            preparedStatement.setString(1, client.getLogin());
        }
        else
        {
            throw new SQLException("Primary key loginField is empty or null");
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
    
        // set passwordField as null - we change it in other query, because
        // some of
        // users can be not register in system
        preparedStatement.setNull(5, Types.INTEGER);
        
        preparedStatement.executeUpdate();
    }
    
    /**
     * Remove client and clients tickets form the DataBase.
     *
     * @param clientLogin first argument must be given not null - primary key
     * @throws SQLException when clientLogin is null or occur problem with
     *                      the database
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
                .prepareStatement("delete from client "
                                  + "where login = ?;");
        
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
     * @throws SQLException when ticketId is null or occur problem with the
     *                      database
     */
    public void removeTicket(Integer ticketId)
    throws SQLException
    {
        PreparedStatement preparedStatement = this.connection
                .prepareStatement("delete from ticket "
                                  + "where idTicket = ?;");
        
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
     * @throws SQLException when client loginField is null or occur problem with
     *                      the database
     */
    public void updateClient(ClientDB client)
    throws SQLException
    {
        PreparedStatement preparedStatement = this.connection
                .prepareStatement("update client "
                                  + "set "
                                  + "name = ?, "
                                  + "surname = ?, "
                                  + "city = ? "
                                  + "where login = ?;");
        
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
            throw new SQLException("Primary key loginField is empty or null");
        }
        
        preparedStatement.executeUpdate();
    }
    
    /**
     * Returns client ticket.
     *
     * @param clientLogin first argument must be given not null - primary key
     * @return LinkedList containing tickets
     * @throws SQLException when clientLogin is null or occur problem with
     *                      the database
     */
    public LinkedList<TicketDB> getClientTickets(String clientLogin)
    throws SQLException
    {
        PreparedStatement preparedStatement = this.connection
                .prepareStatement("select * "
                                  + "from ticket "
                                  + "where Client_login = ?;");
        
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
     * Returns cultural event with given id.
     *
     * @param id is cultural event id.
     * @return cultural event with given id
     * @throws SQLException when id is null or occur problem with the database
     */
    public CulturalEventDB getCulturalEvent(Integer id)
    throws SQLException
    {
        PreparedStatement preparedStatement = this.connection
                .prepareStatement("select * "
                                  + "from culturalevent "
                                  + "where idCulturalEvent = ?;");
        if (id != null)
        {
            preparedStatement.setInt(1, id);
        }
        else
        {
            throw new SQLException("Primary key id is null");
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
     * Changes client passwordField.
     *
     * @param clientLogin first argument must be given not null - primary key
     * @param password    must be given not null
     * @throws SQLException when clientLogin or passwordField are null or occur
     *                      problem with the dataabse
     */
    public void changePassword(String clientLogin, String password)
    throws SQLException
    {
        PreparedStatement preparedStatement = this.connection
                .prepareStatement("update client "
                                  + "set "
                                  + "password = ? "
                                  + "where login = ?;");
        
        if (password != null)
        {
            preparedStatement.setInt(1, password.hashCode());
        }
        else
        {
            throw new SQLException("passwordField is null");
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
     * Checks if is correct loginField.
     *
     * @param clientLogin first argument must be given not null - primary key
     * @return true if LogIn is correct
     * @throws SQLException when clientLogin is null or occur problem with
     *                      the database
     */
    public boolean isCorrectLogIn(String clientLogin, String password)
    throws SQLException
    {
        PreparedStatement preparedStatement = this.connection
                .prepareStatement("select password "
                                  + "from client "
                                  + "where login = ? ;");
        
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
     * Returns number of client tickets.
     *
     * @param clientLogin first argument must be given not null - primary key
     * @return number of tickets belonging to the client
     * @throws SQLException when clientLogin is null or occur problem with
     *                      the database
     */
    public int getNumberOfTickets(String clientLogin)
    throws SQLException
    {
        PreparedStatement preparedStatement = this.connection
                .prepareStatement("select count(idTicket) "
                                  + "from ticket "
                                  + "where Client_login = ? "
                                  + "group by Client_login;");
        
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
     * Returns the maximum price of the client's ticket.
     *
     * @param clientLogin first argument must be given not null - primary key
     * @return the maximum price of the client's ticket
     * @throws SQLException when clientLogin is null or occur problem with
     *                      the database
     */
    public int getMaxTicketPrice(String clientLogin)
    throws SQLException
    {
        PreparedStatement preparedStatement = this.connection
                .prepareStatement("select max(price) "
                                  + "from ticket "
                                  + "where Client_login = ?;");
        
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
     * Checks whether a given client exists in the database.
     *
     * @param clientLogin first argument must be given not null - primary key
     * @return true, if client is in the DataBase
     * @throws SQLException when clientLogin is null or occur problem with
     *                      the database
     */
    public boolean isClient(String clientLogin)
    throws SQLException
    {
        PreparedStatement preparedStatement = this.connection
                .prepareStatement("select login "
                                  + "from client "
                                  + "where login = ? ;");
        
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
     * Returns performers related with ticketId.
     *
     * @param ticketId first argument must be given not null - primary key
     * @return performers related with ticket
     * @throws SQLException when ticketId is null or occur problem with the
     *                      database
     */
    public LinkedList<PerformerDB> getPerformers(Integer ticketId)
    throws SQLException
    {
        PreparedStatement preparedStatement = this.connection
                .prepareStatement("select Performer_name "
                                  + "from culturalevent_has_performer "
                                  + "where CulturalEvent_idCulturalEvent = "
                                  + "(select CulturalEvent_idCulturalEvent "
                                  + " from ticket "
                                  + " where idTicket =  ?);");
        
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
     * Returns client events.
     *
     * @param clientLogin first argument must be given not null - primary key
     * @return client events
     * @throws SQLException when clientLogin is null or occur problem with
     *                      the database
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
    
    /**
     * Returns all events and related performers.
     *
     * @return all events and related performers.
     * @throws SQLException when occur problem with the database
     */
    public LinkedList<CulturalEventHasPerformerDB> getEventsAndPerformers()
    throws SQLException
    {
        PreparedStatement preparedStatement = this.connection
                .prepareStatement("select * "
                                  + "from culturalevent_has_performer;");
        
        ResultSet resultSet = preparedStatement.executeQuery();
    
        LinkedList<CulturalEventHasPerformerDB> eventsAndPerformers = new
                LinkedList<>();
        
        while (resultSet.next())
        {
            eventsAndPerformers.add(new CulturalEventHasPerformerDB(resultSet
                    .getInt(1), resultSet.getString(2)));
        }
        
        return eventsAndPerformers;
    }
    
    /**
     * Adds ticket related with event and client to the database.
     *
     * @param event       first argument in constructor must be given not null -
     *                    primary key
     * @param clientLogin must be given not null - primary key
     * @throws SQLException when event id or clientLogin is null or occur
     *                      problem with the database
     */
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
            throw new SQLException("Primary key clientLogin is null");
        }
        
        if (event.getIdCulturalEvent() != null)
        {
            preparedStatement.setInt(3, event.getIdCulturalEvent());
        }
        else
        {
            throw new SQLException("Primary key event id is null");
        }
        
        preparedStatement.executeUpdate();
    }
}