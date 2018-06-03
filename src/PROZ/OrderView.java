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
 * @author BAKSIUv13
 */
public class OrderView
        implements Initializable
{
    @FXML private RadioButton regulations;
    @FXML private Button buy;
    @FXML private Button cancel;
    
    @FXML private TableView<CulturalEventDB> events;
    
    @FXML private TableColumn<CulturalEventDB, Integer> idColumn;
    @FXML private TableColumn<CulturalEventDB, String> typeColumn;
    @FXML private TableColumn<CulturalEventDB, Date> dateColumn;
    @FXML private TableColumn<CulturalEventDB, Integer> nrOfPeopleColumn;
    @FXML private TableColumn<CulturalEventDB, String> cityColumn;
    @FXML private TableColumn<CulturalEventDB, String> streetColumn;
    @FXML private TableColumn<CulturalEventDB, Integer> ticketPriceColumn;
    
    @FXML private Label boughtLabel;
    
    private Model model;
    private String login;
    private ObservableList<CulturalEventDB> selectedEvents;
    
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
    
    public void setLogin(String login)
    {
        this.login = login;
    }
    
    public void setSelectedEvents(
            ObservableList<CulturalEventDB> selectedEvents)
    {
        this.selectedEvents = selectedEvents;
        this.events.setItems(selectedEvents);
    }
    
    public void agreeAction()
    {
        if (this.regulations.isSelected() && this.selectedEvents.size() > 0)
        {
            this.buy.setDisable(false);
        }
        else
        {
            this.buy.setDisable(true);
        }
    }
    
    public void cancelAction(Event event)
    {
        // get stage
        Stage primaryStage = (Stage) ((Node) event.getSource())
                .getScene().getWindow();
        
        primaryStage.close();
    }
    
    public void buyAction()
    {
        try
        {
            for (CulturalEventDB event : this.selectedEvents)
            {
                this.model.addTicket(event, this.login);
            }
        }
        catch (SQLException ex)
        {
            ViewMethods.exceptionHandler(ex);
        }
        
        this.buy.setDisable(true);
        this.boughtLabel.setVisible(true);
        this.cancel.setText("Go back");
        
    }
}
