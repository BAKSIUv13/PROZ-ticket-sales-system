package PROZ;

/**
 * ClientDB is a Class which helps to send a data between DataBase and
 * Model&Controllers.
 *
 * @author BAKSIUv13
 */
public class ClientDB
{
    private String login;
    private String name;
    private String surName;
    private String city;
    
    public ClientDB(String login)
    {
        this.login = login;
    }
    
    public ClientDB(String login, String name, String surname, String city)
    {
        this.login = login;
        this.name = name;
        this.surName = surname;
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
    
    public String getSurName()
    {
        return this.surName;
    }
    
    public String getCity()
    {
        return this.city;
    }
}