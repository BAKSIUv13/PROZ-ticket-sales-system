package PROZ;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

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
    @FXML private Label passwordProblem;
    
    private Model model;
    private String login;
    
    public void updateClientAction(Event event)
    {
        System.out.println(Model.getCounter());
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
        }
        catch (SQLException ex)
        {
            ViewMethods.exceptionHandler(ex);
        }
    }
    
    public void changePassword(Event event)
    {
        this.passwordProblem.setText("");
        
        String currentPassword = this.currentPassword.getText();
        String newPassword = this.newPassword.getText();
        
        if (isCorrectPassword(currentPassword))
        {
            try
            {
                this.model.changePassword(new ClientDB(this.login, null,
                        null, null), newPassword);
            }
            catch (SQLException ex)
            {
                ViewMethods.exceptionHandler(ex);
            }
    
            this.passwordProblem.setTextFill(Color.web("#6bd700"));
            this.passwordProblem.setText("Password changed");
        }
        else
        {
            this.passwordProblem.setTextFill(Color.web("#b90000"));
            this.passwordProblem.setText("Incorrect current password");
        }
    }
    
    public void logOutAction(Event event)
    throws Exception
    {
        // get root
        FXMLLoader fxmlLoader = new FXMLLoader();
        Parent root = fxmlLoader.load(
                getClass().getResource("LogInView.fxml").openStream());
        
        // set model in new controller
        LogInView logInViewController = (LogInView) fxmlLoader
                .getController();
        logInViewController.setModel(this.model);
        logInViewController.setLogin(login);
        
        // get stage
        Stage primaryStage = (Stage) ((Node) event.getSource())
                .getScene().getWindow();
        primaryStage.setMinWidth(0);
        primaryStage.setMinHeight(0);
        primaryStage.hide();
        
        // set new stage
        Scene clientView = new Scene(root);
        primaryStage.setScene(clientView);
        primaryStage.setMaxWidth(309);
        primaryStage.setMaxHeight(500);
        primaryStage.setResizable(false);
        
        primaryStage.show();
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
                this.model.removeClient(
                        new ClientDB(this.login, null, null, null));
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
        // get root
        FXMLLoader fxmlLoader = new FXMLLoader();
        Parent root = fxmlLoader.load(
                getClass().getResource("TicketView.fxml").openStream());
        
        // set model in new controller
        TicketView ticketViewController = (TicketView) fxmlLoader
                .getController();
        ticketViewController.setModel(this.model);
        ticketViewController.setLogin(this.login);
        
        // set new stage parameters
        Stage window = (Stage) ((Node) event.getSource()).getScene()
                                                         .getWindow();
        window.setScene(new Scene(root));
    }
    
}
