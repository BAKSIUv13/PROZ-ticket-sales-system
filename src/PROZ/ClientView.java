package PROZ;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.paint.Color;

import java.sql.SQLException;
import java.util.Optional;

/**
 * @author BAKSIUv13
 */
public class ClientView
{
    @FXML private TextField name;
    @FXML private TextField surName;
    @FXML private TextField city;
    
    @FXML private PasswordField currentPassword;
    @FXML private PasswordField newPassword;
    
    @FXML private Label passwordInfo;
    @FXML private Label updateInfo;
    
    private Model model;
    private String login;
    
    /**
     * update client data
     */
    public void updateClientAction(Event event)
    {
        this.updateInfo.setVisible(false);
        
        String name = this.name.getText();
        if (name.equals(""))
        {
            name = null;
        }
        String surName = this.surName.getText();
        if (surName.equals(""))
        {
            surName = null;
        }
        String city = this.city.getText();
        if (city.equals(""))
        {
            city = null;
        }
        
        try
        {
            this.model.updateClient(new ClientDB(this.login, name, surName,
                    city));
            this.updateInfo.setVisible(true);
        }
        catch (SQLException ex)
        {
            ViewMethods.exceptionHandler(ex);
        }
    }
    
    public void changePassword(Event event)
    {
        this.passwordInfo.setText("");
        
        String currentPassword = this.currentPassword.getText();
        String newPassword = this.newPassword.getText();
        
        if (isCorrectPassword(currentPassword))
        {
            try
            {
                this.model.changePassword(this.login, newPassword);
            }
            catch (SQLException ex)
            {
                ViewMethods.exceptionHandler(ex);
            }
    
            this.passwordInfo.setTextFill(Color.web("#6bd700"));
            this.passwordInfo.setText("Password changed");
        }
        else
        {
            this.passwordInfo.setTextFill(Color.web("#b90000"));
            this.passwordInfo.setText("Incorrect current password");
        }
    }
    
    public void logOutAction(Event event)
    throws Exception
    {
        ViewMethods.logOutAction(event, this, this.login, this.model);
    }
    
    public void deleteAccountAction(Event event)
    throws Exception
    {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete Your Account");
        alert.setHeaderText("Do you want to delete your account?");
        
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK)
        {
            try
            {
                this.model.removeClient(this.login);
            }
            catch (SQLException ex)
            {
                ViewMethods.exceptionHandler(ex);
            }
            
            alert.hide();
            logOutAction(event);
        }
        else
        {
            alert.hide();
        }
    }
    
    public String getLogin()
    {
        return this.login;
    }
    
    public void setLogin(String login)
    {
        this.login = login;
    }
    
    public void setModel(Model model)
    {
        this.model = model;
    }
    
    private boolean isCorrectPassword(String password)
    {
        boolean isCorrectPassword = false;
        try
        {
            isCorrectPassword = this.model.isCorrectLogIn(this.login, password);
        }
        catch (SQLException ex)
        {
            ViewMethods.exceptionHandler(ex);
        }
        
        return isCorrectPassword;
    }
    
    public void changeSceneTicketAction(Event event)
    throws Exception
    {
        ViewMethods.changeSceneTicketAction(event, this, this.login,
                this.model);
    }
}
