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
    private Boolean isPersonal;
    private String clientLogin;
    private Integer culturalEventId;
    
    public TicketDB(Integer idTicket)
    {
        this.idTicket = idTicket;
    }
    
    public TicketDB(Integer idTicket, Integer price, Boolean isPersonal,
            String clientLogin, Integer culturalEventId)
    throws SQLException
    {
        this.idTicket = idTicket;
        
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
    
    public TicketDB(TicketDB ticket)
    {
        this.idTicket = ticket.idTicket;
        this.price = ticket.price;
        this.isPersonal = ticket.isPersonal;
        this.clientLogin = ticket.clientLogin;
        this.culturalEventId = ticket.culturalEventId;
    }
    
    public Integer getIdTicket()
    {
        return this.idTicket;
    }
    
    public Integer getPrice()
    {
        return this.price;
    }
    
    public Boolean getIsPersonal()
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
