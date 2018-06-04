package PROZ;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.ResourceBundle;

/**
 * TicketView.fxml controller class.
 *
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
    private String clientLogin;
    
    /**
     * sets column CellValueFactory
     */
    @Override public void initialize(URL location, ResourceBundle resources)
    {
        this.idTicketColumn.setCellValueFactory(
                new PropertyValueFactory<>("idTicket"));
        // must be getIdTicket() !!!
        this.priceTicketColumn.setCellValueFactory(
                new PropertyValueFactory<>("price"));
        this.isPersonalColumn.setCellValueFactory(
                new PropertyValueFactory<>("isPersonal"));
        
        this.performerNameColumn.setCellValueFactory(
                new PropertyValueFactory<>("name"));
    }
    
    public void setModel(Model model)
    {
        this.model = model;
    }
    
    public void setClientLogin(String clientLogin)
    {
        this.clientLogin = clientLogin;
    }
    
    /**
     * logOutButton action
     */
    public void logOutAction(Event event)
    throws Exception
    {
        ViewMethods.logOutAction(event, this, this.clientLogin, this.model);
    }
    
    
    /**
     * refresh data
     */
    public void refreshTicketsAction()
    {
        this.ticketTable.setItems(getTickets());
        
        try
        {
            this.nrOfTicketsLabel.setText("Number of tickets: " + this.model
                    .getNumberOfTickets(this.clientLogin));
            
            this.maxTicketPriceLabel.setText("Maximum price: " + this.model
                    .getMaxTicketPrice(this.clientLogin));
        }
        catch (SQLException ex)
        {
            ViewMethods.exceptionHandler(ex);
        }
    }
    
    /**
     * Shows performers related to ticket.
     */
    public void showPerformersAction()
    {
        this.performerTable.setItems(getPerformer());
    }
    
    /**
     * Change scene to client scene.
     *
     * @param event is necessary to get primary stage
     */
    public void changeSceneClientAction(Event event)
    throws Exception
    {
        ViewMethods.changeSceneClientAction(event, this, this.clientLogin,
                this.model);
    }
    
    /**
     * Ticket was double clicked.
     *
     * @param event to recognise clicked action
     */
    public void doubleClickedTickedAction(MouseEvent event)
    {
        if (event.getButton().equals(MouseButton.PRIMARY))
        {
            if (event.getClickCount() == 2)
            {
                showPerformersAction();
            }
        }
    }
    
    /**
     * Change scene to events scene.
     *
     * @param event is necessary to get primary stage
     */
    public void changeSceneEventsAction(Event event)
    throws Exception
    {
        ViewMethods.changeSceneEventsAction(event, this, this.clientLogin,
                this.model);
    }
    
    private ObservableList<TicketDB> getTickets()
    {
        ObservableList<TicketDB> ticketsObservable = FXCollections
                .observableArrayList();
        LinkedList<TicketDB> tickets = null;
        
        try
        {
            tickets = this.model.getClientTickets(this.clientLogin);
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
                performers = this.model.getPerformers(
                        selectedTicket.getIdTicket());
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
