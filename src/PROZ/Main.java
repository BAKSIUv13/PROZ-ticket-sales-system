/**
 * Ticket Sales System
 * Bartlomiej Kulik
 * 5 May 2018
 */

package PROZ;

public class Main
{
    public static void main(String[] args)
    {
        Model model = connectToMyDatabase.getModel();
        Controller controller = new Controller(model);
    }
}