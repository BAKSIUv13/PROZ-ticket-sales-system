package PROZ;

/**
 * CulturalEventHasPerformerDB is a Class which helps to send a data between
 * DataBase and Model&Controllers.
 *
 * @author BAKSIUv13
 */
public class CulturalEventHasPerformerDB
{
    private Integer culturalEventId;
    private String performerName;
    
    public CulturalEventHasPerformerDB(Integer culturalEventId,
            String performerName)
    {
        this.culturalEventId = culturalEventId;
        this.performerName = performerName;
    }
    
    public Integer getCulturalEventId()
    {
        return this.culturalEventId;
    }
    
    public String getPerformerName()
    {
        return this.performerName;
    }
}
