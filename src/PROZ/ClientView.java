package PROZ;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.paint.Color;

import java.sql.SQLException;
import java.util.Optional;

/**
 * ClientView.fxml controller class.
 *
 * @author BAKSIUv13
 */
public class ClientView
{
    @FXML private TextField nameField;
    @FXML private TextField surNameField;
    @FXML private TextField cityField;
    
    @FXML private PasswordField currentPasswordField;
    @FXML private PasswordField newPasswordField;
    
    @FXML private Label passwordInfoLabel;
    @FXML private Label updateInfoLabel;
    
    private Model model;
    private String clientLogin;
    
    public void setClientLogin(String clientLogin)
    {
        this.clientLogin = clientLogin;
    }
    
    public void setModel(Model model)
    {
        this.model = model;
    }
    
    /**
     * Update client data.
     */
    @FXML private void updateClientAction()
    {
        this.updateInfoLabel.setVisible(false);
    
        String name = this.nameField.getText();
        if (name.equals(""))
        {
            name = null;
        }
    
        String surName = this.surNameField.getText();
        if (surName.equals(""))
        {
            surName = null;
        }
        String city = this.cityField.getText();
        if (city.equals(""))
        {
            city = null;
        }
        
        try
        {
            this.model.updateClient(
                    new ClientDB(this.clientLogin, name, surName,
                            city));
            this.updateInfoLabel.setVisible(true);
        }
        catch (SQLException ex)
        {
            ViewMethods.exceptionHandler(ex);
        }
    }
    
    /**
     * Changes passwordField.
     */
    @FXML private void changePasswordAction()
    {
        this.passwordInfoLabel.setText("");
    
        String currentPassword = this.currentPasswordField.getText();
        String newPassword = this.newPasswordField.getText();
        
        if (isCorrectPassword(currentPassword))
        {
            try
            {
                this.model.changePassword(this.clientLogin, newPassword);
            }
            catch (SQLException ex)
            {
                ViewMethods.exceptionHandler(ex);
            }
    
            this.passwordInfoLabel.setVisible(true);
            this.passwordInfoLabel.setTextFill(Color.web("#6bd700"));
            this.passwordInfoLabel.setText("Password changed");
        }
        else
        {
            this.passwordInfoLabel.setTextFill(Color.web("#b90000"));
            this.passwordInfoLabel.setText("Incorrect current passwordField");
        }
    }
    
    /**
     * Logs out.
     *
     * @param event is necessary to get primary stage
     */
    @FXML private void logOutAction(Event event)
    throws Exception
    {
        ViewMethods.logOutAction(event, this, this.clientLogin, this.model);
    }
    
    /**
     * Deletes client account.
     *
     * @param event is necessary to get primary stage
     */
    @FXML private void deleteAccountAction(Event event)
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
                this.model.removeClient(this.clientLogin);
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
    
    /**
     * Checks if is correct passwordField.
     *
     * @param password to check
     * @return tru, if passwordField is correct.
     */
    private boolean isCorrectPassword(String password)
    {
        boolean isCorrectPassword = false;
        try
        {
            isCorrectPassword = this.model.isCorrectLogIn(this.clientLogin,
                    password);
        }
        catch (SQLException ex)
        {
            ViewMethods.exceptionHandler(ex);
        }
        
        return isCorrectPassword;
    }
    
    /**
     * Change scene to ticket scene.
     *
     * @param event is necessary to get primary stage
     */
    @FXML private void changeSceneTicketAction(Event event)
    throws Exception
    {
        ViewMethods.changeSceneTicketAction(event, this, this.clientLogin,
                this.model);
    }
    
    /**
     * Change scene to event scene.
     *
     * @param event is necessary to get primary stage
     */
    @FXML private void changeSceneEventsAction(Event event)
    throws Exception
    {
        ViewMethods.changeSceneEventsAction(event, this, this.clientLogin,
                this.model);
    }
}