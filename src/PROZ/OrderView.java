package PROZ;

import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ResourceBundle;

/**
 * OrderView.fxml class controller.
 *
 * @author BAKSIUv13
 */
public class OrderView
        implements Initializable
{
    @FXML private RadioButton regulationsRadioButton;
    @FXML private Button buyButton;
    @FXML private Button cancelButton;
    
    @FXML private TableView<CulturalEventDB> eventsTable;
    
    @FXML private TableColumn<CulturalEventDB, Integer> idColumn;
    @FXML private TableColumn<CulturalEventDB, String> typeColumn;
    @FXML private TableColumn<CulturalEventDB, Date> dateColumn;
    @FXML private TableColumn<CulturalEventDB, Integer> nrOfPeopleColumn;
    @FXML private TableColumn<CulturalEventDB, String> cityColumn;
    @FXML private TableColumn<CulturalEventDB, String> streetColumn;
    @FXML private TableColumn<CulturalEventDB, Integer> ticketPriceColumn;
    
    @FXML private Label boughtLabel;
    
    private Model model;
    private String clientLogin;
    // to show
    private ObservableList<CulturalEventDB> selectedEvents;
    
    /**
     * sets column CellValueFactory
     */
    @Override public void initialize(URL location, ResourceBundle resources)
    {
        this.idColumn.setCellValueFactory(
                new PropertyValueFactory<>("idCulturalEvent"));
        this.typeColumn.setCellValueFactory(
                new PropertyValueFactory<>("type"));
        this.dateColumn.setCellValueFactory(
                new PropertyValueFactory<>("date"));
        this.nrOfPeopleColumn.setCellValueFactory(
                new PropertyValueFactory<>("nrPeople"));
        this.cityColumn.setCellValueFactory(
                new PropertyValueFactory<>("placeCity"));
        this.streetColumn.setCellValueFactory(
                new PropertyValueFactory<>("placeStreet"));
        this.ticketPriceColumn.setCellValueFactory(
                new PropertyValueFactory<>("actualTicketPrice"));
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
     * Shows selected events.
     *
     * @param selectedEvents form earlier stage
     */
    public void setSelectedEvents(
            ObservableList<CulturalEventDB> selectedEvents)
    {
        this.selectedEvents = selectedEvents;
        this.eventsTable.setItems(selectedEvents);
    }
    
    /**
     * regulationsRadioButton action.
     */
    public void agreeAction()
    {
        if (this.regulationsRadioButton.isSelected()
            && this.selectedEvents.size() > 0)
        {
            this.buyButton.setDisable(false);
        }
        else
        {
            this.buyButton.setDisable(true);
        }
    }
    
    /**
     * cancelButton action.
     *
     * @param event is necessary to get primary stage
     */
    public void cancelAction(Event event)
    {
        // get stage
        Stage primaryStage = (Stage) ((Node) event.getSource())
                .getScene().getWindow();
        
        primaryStage.close();
    }
    
    /**
     * buyButton action
     */
    public void buyAction()
    {
        try
        {
            for (CulturalEventDB event : this.selectedEvents)
            {
                this.model.addTicket(event, this.clientLogin);
            }
        }
        catch (SQLException ex)
        {
            ViewMethods.exceptionHandler(ex);
        }
        
        this.buyButton.setDisable(true);
        this.boughtLabel.setVisible(true);
        this.cancelButton.setText("Go back");
    }
}
