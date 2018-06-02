package PROZ;

public class CulturalEventHasPerformer
{
    private Integer culturalEventId;
    private String performerName;
    
    public CulturalEventHasPerformer(Integer culturalEventId,
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
