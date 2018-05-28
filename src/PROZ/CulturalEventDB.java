package PROZ;

import java.util.Date;

/**
 * CulturalEventDb is a Class which helps to send a data between DataBase and
 * Model&LogInView.
 *
 * @author BAKSIUv13
 */
public class CulturalEventDB
{
    private Integer idCulturalEvent;
    private String type;
    private Date date;
    private Integer nrPeople;
    private String placeCity;
    private String placeStreet;
    private Integer actualTicketPrice;
    
    public CulturalEventDB(Integer idCulturalEvent)
    {
        this.idCulturalEvent = idCulturalEvent;
    }
    
    public CulturalEventDB(Integer idCulturalEvent, String type,
            Date date, Integer nrPeople, String placeCity,
            String placeStreet, Integer actualTicketPrice)
    {
        this.idCulturalEvent = idCulturalEvent;
        this.type = type;
        this.date = date;
        this.nrPeople = nrPeople;
        this.placeCity = placeCity;
        this.placeStreet = placeStreet;
        this.actualTicketPrice = actualTicketPrice;
    }
    
    public Integer getIdCulturalEvent()
    {
        return this.idCulturalEvent;
    }
    
    public String getType()
    {
        return this.type;
    }
    
    public Date getDate()
    {
        return this.date;
    }
    
    public int getNrPeople()
    {
        return this.nrPeople;
    }
    
    public String getPlaceCity()
    {
        return this.placeCity;
    }
    
    public String getPlaceStreet()
    {
        return this.placeStreet;
    }
    
    public Integer getActualTicketPrice()
    {
        return this.actualTicketPrice;
    }
}