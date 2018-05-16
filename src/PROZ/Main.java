/**
 * Ticket Sales System
 * Bartlomiej Kulik
 * 16 May 2018
 */
package PROZ;

/**
 * Starting class in Ticket Sales System.
 */
public class Main
{
    public static void main(String[] args)
    {
        Model model = connectToMyDatabase.getModel();
        Controller controller = new Controller(model);
    }
}