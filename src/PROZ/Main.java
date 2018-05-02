/**
 * Ticket Sales System
 * Bartlomiej Kulik
 * 2 May 2018
 */

package PROZ;

public class Main
{
    public static void main(String[] args)
    {
        Model model = connectToMyDatabase.getModel();
        View view = new View();
        Controller controller = new Controller(model, view);
    }
}