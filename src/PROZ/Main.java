/**
 * Ticket Sales System
 * Bartlomiej Kulik
 * 1 May 2018
 */

package PROZ;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application
{
    public static void main(String[] args)
    {
        //launch(args);
        System.out.println("Hello world!");
        Model.connect("jdbc:mysql://localhost/mydb?useUnicode=true" +
                      "&useJDBCCompliantTimezoneShift=true" +
                      "&useLegacyDatetimeCode=false&serverTimezone=UTC&useSSL" +
                      "" + "=false", "root", "root");
        
        Model.disConnect();
    }
    
    @Override
    public void start(Stage primaryStage) throws Exception
    {
        Parent root = FXMLLoader.load(getClass().getResource("view.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();
    }
}