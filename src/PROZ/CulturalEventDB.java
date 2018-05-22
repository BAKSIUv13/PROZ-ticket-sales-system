package PROZ;

import java.sql.SQLException;
import java.util.Date;

/**
 * CulturalEventDb is a Class which helps to send a data between DataBase and
 * Model&Controller.
 */
public class CulturalEventDB
{
    private Integer idCulturalEvent;
    private String type;
    private Date date;
    private int nrPeople;
    private String placeCity;
    private String placeStreet;
    
    public CulturalEventDB(Integer idCulturalEvent, String type, Date date,
            int nrPeople, String placeCity, String placeStreet)
    throws SQLException
    {
        if (idCulturalEvent != null)
        {
            this.idCulturalEvent = idCulturalEvent;
        }
        else
        {
            throw new SQLException("idCulturaEvent is a primary key and it "
                                   + "cannot be null");
        }
        this.type = type;
        this.date = date;
        this.nrPeople = nrPeople;
        if (placeCity != null && placeStreet != null)
        {
            this.placeCity = placeCity;
            this.placeStreet = placeStreet;
        }
        else
        {
            throw new SQLException("placeCity and placeStreet is necessary - "
                                   + "foreign key");
        }
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
}
