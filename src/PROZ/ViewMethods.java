package PROZ;

import javafx.scene.control.Alert;

/**
 * @author BAKSIUv13
 */
public class ViewMethods
{
    static public void exceptionHandler(Throwable ex)
    {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Fatal Error");
        alert.setHeaderText("SQLException");
        alert.setContentText(ex.getMessage());
        
        alert.showAndWait();
        System.exit(-1);
    }
}