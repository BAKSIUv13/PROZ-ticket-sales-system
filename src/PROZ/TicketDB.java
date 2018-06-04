package PROZ;

/**
 * TicketDB is a Class which helps to send a data between DataBase and
 * Model&Controllers.
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
    {
        this.idTicket = idTicket;
        this.price = price;
        this.isPersonal = isPersonal;
        this.clientLogin = clientLogin;
        this.culturalEventId = culturalEventId;
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