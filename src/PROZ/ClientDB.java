package PROZ;

import java.sql.SQLException;

/**
 * ClientDB is a Class which helps to send a data between DataBase and
 * Model&Controller.
 */
public class ClientDB
{
    private String login;
    private String name;
    private String surname;
    private String city;
    
    public ClientDB(String login, String name, String surname,
            String city)
    throws SQLException
    {
        if (login != null)
        {
            this.login = login;
        }
        else
        {
            throw new SQLException("login is a primary key and it cannot be "
                                   + "null");
        }
        this.name = name;
        this.surname = surname;
        this.city = city;
    }
    
    public String getLogin()
    {
        return this.login;
    }
    
    public String getName()
    {
        return this.name;
    }
    
    public String getSurname()
    {
        return this.surname;
    }
    
    public String getCity()
    {
        return this.city;
    }
}
