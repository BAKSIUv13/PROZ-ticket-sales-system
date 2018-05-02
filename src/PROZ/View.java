package PROZ;

public class View
{
    public View()
    {
        System.out.println("View");
    }
    
    public void connectionSuccess()
    {
        System.out.println("Connected with database successfully");
    }
    
    public void disconnectionSuccess()
    {
        System.out.println("Disconnected successfully from the database");
    }
    
    public void disconnectionFail()
    {
        System.out.println("Failed to disconnect from the database");
    }
    
    public void doSomething(final String message)
    {
        System.out.println(message);
    }
}