package PROZ;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.ResourceBundle;

/**
 * @author BAKSIUv13
 */
public class TicketView
        implements Initializable
{
    @FXML private Label nrOfTicketsLabel;
    @FXML private Label maxTicketPriceLabel;
    
    @FXML private TableView<TicketDB> ticketTable;
    @FXML private TableView<PerformerDB> performerTable;
    
    @FXML private TableColumn<TicketDB, Integer> idTicketColumn;
    @FXML private TableColumn<TicketDB, Integer> priceTicketColumn;
    @FXML private TableColumn<TicketDB, Boolean> isPersonalColumn;
    
    @FXML private TableColumn<PerformerDB, String> performerNameColumn;
    
    private Model model;
    private String login;
    
    @Override public void initialize(URL location, ResourceBundle resources)
    {
        this.idTicketColumn.setCellValueFactory(
                new PropertyValueFactory<>("idTicket"));
        this.priceTicketColumn.setCellValueFactory(
                new PropertyValueFactory<>("price"));
        this.isPersonalColumn.setCellValueFactory(
                new PropertyValueFactory<>("isPersonal"));
        
        this.performerNameColumn.setCellValueFactory(
                new PropertyValueFactory<>("name"));
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
    
    public void setModel(Model model)
    {
        this.model = model;
    }
    
    public String getLogin()
    {
        return this.login;
    }
    
    public void setLogin(String login)
    {
        this.login = login;
    }
    
    /**
     * refresh data
     */
    public void refreshTicketsAction()
    {
        this.ticketTable.setItems(getTickets());
        
        try
        {
            ClientDB client = new ClientDB(this.login);
            
            this.nrOfTicketsLabel.setText("Number of tickets: " + this.model
                    .getNumberOfTickets(client));
            
            this.maxTicketPriceLabel.setText("Maximum price: " + this.model
                    .getMaxTicketPrice(client));
        }
        catch (SQLException ex)
        {
            ViewMethods.exceptionHandler(ex);
        }
    }
    
    public void showPerformersAction()
    {
        this.performerTable.setItems(getPerformer());
    }
    
    public void changeSceneClientAction(Event event)
    throws Exception
    {
        // get root
        FXMLLoader fxmlLoader = new FXMLLoader();
        Parent root = fxmlLoader.load(
                getClass().getResource("ClientView.fxml").openStream());
        
        // set model in new controller
        ClientView clientViewController = (ClientView) fxmlLoader
                .getController();
        clientViewController.setModel(this.model);
        clientViewController.setLogin(this.login);
        
        // set new stage parameters
        Stage window = (Stage) ((Node) event.getSource()).getScene()
                                                         .getWindow();
        window.setScene(new Scene(root));
    }
    
    private ObservableList<TicketDB> getTickets()
    {
        ObservableList<TicketDB> ticketsObservable = FXCollections
                .observableArrayList();
        LinkedList<TicketDB> tickets = null;
        
        try
        {
            tickets = this.model.getClientTickets(new ClientDB(this.login));
        }
        catch (SQLException ex)
        {
            ViewMethods.exceptionHandler(ex);
        }
        
        if (tickets != null)
        {
            ticketsObservable.addAll(tickets);
        }
        
        return ticketsObservable;
    }
    
    private ObservableList<PerformerDB> getPerformer()
    {
        ObservableList<PerformerDB> performerObservable = FXCollections
                .observableArrayList();
        LinkedList<PerformerDB> performers = null;
        
        TicketDB selectedTicket = this.ticketTable.getSelectionModel()
                                                  .getSelectedItem();
        
        if (selectedTicket != null)
        {
            try
            {
                performers = this.model.getPerformers(selectedTicket);
            }
            catch (SQLException ex)
            {
                ViewMethods.exceptionHandler(ex);
            }
            
            if (performers != null)
            {
                performerObservable.addAll(performers);
            }
        }
        
        return performerObservable;
    }
}
