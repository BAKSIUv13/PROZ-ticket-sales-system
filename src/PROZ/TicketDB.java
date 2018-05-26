package PROZ;

import java.sql.SQLException;

/**
 * TicketDB is a Class which helps to send a data between DataBase and
 * Model&LogInView.
 *
 * @author BAKSIUv13
 */
public class TicketDB
{
    private Integer idTicket;
    private Integer price;
    private boolean isPersonal;
    private String clientLogin;
    private Integer culturalEventId;
    
    public TicketDB(Integer idTicket, Integer price, boolean isPersonal,
            String clientLogin, Integer culturalEventId)
    throws SQLException
    {
        if (idTicket != null)
        {
            this.idTicket = idTicket;
        }
        else
        {
            throw new SQLException("idTicket is a primary key and it cannot "
                                   + "be null");
        }
        if (price != null)
        {
            this.price = price;
        }
        else
        {
            throw new SQLException("price cannot be null");
        }
        this.isPersonal = isPersonal;
        if (clientLogin != null && culturalEventId != null)
        {
            this.clientLogin = clientLogin;
            this.culturalEventId = culturalEventId;
        }
        else
        {
            throw new SQLException("clientLogin culturalEventId is a not null"
                                   + "foreign and it cannot be null");
        }
    }
    
    public Integer getIdTicket()
    {
        return this.idTicket;
    }
    
    public Integer getPrice()
    {
        return this.price;
    }
    
    public boolean isPersonal()
    {
        return this.isPersonal;
    }
    
    public String getClientLogin()
    {
        return this.clientLogin;
    }
    
    public Integer getCulturalEventId()
    {
        return this.culturalEventId;
    }
}
