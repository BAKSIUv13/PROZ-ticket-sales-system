package PROZ;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.ResourceBundle;

public class EventsView
        implements Initializable
{
    @FXML private TableView clientEventsTable;
    @FXML private TableView searchEventsTable;
    
    @FXML private TableColumn<CulturalEventDB, Integer> idColumnClient;
    @FXML private TableColumn<CulturalEventDB, Date> dateColumnClient;
    @FXML private TableColumn<CulturalEventDB, String> cityColumnClient;
    @FXML private TableColumn<CulturalEventDB, String> streetColumnClient;
    @FXML private TableColumn<CulturalEventDB, Integer> idColumnSearch;
    @FXML private TableColumn<CulturalEventDB, Date> dateColumnSearch;
    @FXML private TableColumn<CulturalEventDB, String> cityColumnSearch;
    @FXML private TableColumn<CulturalEventDB, String> streetColumnSearch;
    
    @FXML private TextField performer;
    
    private Model model;
    private String login;
    
    @Override public void initialize(URL location, ResourceBundle resources)
    {
        this.idColumnClient.setCellValueFactory(
                new PropertyValueFactory<>("idCulturalEvent"));
        this.idColumnSearch.setCellValueFactory(
                new PropertyValueFactory<>("idCulturalEvent"));
        this.dateColumnClient.setCellValueFactory(
                new PropertyValueFactory<>("date"));
        this.dateColumnSearch.setCellValueFactory(
                new PropertyValueFactory<>("date"));
        this.cityColumnClient.setCellValueFactory(
                new PropertyValueFactory<>("placeCity"));
        this.cityColumnSearch.setCellValueFactory(
                new PropertyValueFactory<>("placeCity"));
        this.streetColumnClient.setCellValueFactory(
                new PropertyValueFactory<>("placeStreet"));
        this.streetColumnSearch.setCellValueFactory(
                new PropertyValueFactory<>("placeStreet"));
    }
    
    public void logOutAction(Event event)
    throws Exception
    {
        ViewMethods.logOutAction(event, this, this.login, this.model);
    }
    
    public void changeSceneTicketAction(Event event)
    throws Exception
    {
        ViewMethods.changeSceneTicketAction(event, this, this.login,
                this.model);
    }
    
    public void changeSceneClientAction(Event event)
    throws Exception
    {
        ViewMethods.changeSceneClientAction(event, this, this.login,
                this.model);
    }
    
    public void setModel(Model model)
    {
        this.model = model;
    }
    
    public void setLogin(String login)
    {
        this.login = login;
    }
    
    private ObservableList<CulturalEventDB> getClientEvents()
    {
        ObservableList<CulturalEventDB> eventsObservable = FXCollections
                .observableArrayList();
        LinkedList<CulturalEventDB> events = null;
        
        try
        {
            events = this.model.getClientEvents(this.login);
        }
        catch (SQLException ex)
        {
            ViewMethods.exceptionHandler(ex);
        }
        
        if (events != null)
        {
            eventsObservable.addAll(events);
        }
        
        return eventsObservable;
    }
    
    public void refreshClientEvents()
    throws Exception
    {
        this.clientEventsTable.setItems(getClientEvents());
    }
    
    public void searchEvents()
    {
        String performer = this.performer.getText();
        LinkedList<CulturalEventHasPerformer> eventHasPerformers = new
                LinkedList<>();
        LinkedList<Integer> eventIds = new LinkedList<>();
        ObservableList<CulturalEventDB> eventObservable = FXCollections
                .observableArrayList();
        
        try
        {
            eventHasPerformers = this.model.getEventsAndPerformers();
        }
        catch (SQLException ex)
        {
            ViewMethods.exceptionHandler(ex);
        }
        
        for (CulturalEventHasPerformer event : eventHasPerformers)
        {
            if (event.getPerformerName().contains(performer))
            {
                eventIds.add(event.getCulturalEventId());
            }
        }
        
        for (Integer id : eventIds)
        {
            try
            {
                eventObservable.add(this.model.getCulturalEvent(id));
            }
            catch (SQLException ex)
            {
                ViewMethods.exceptionHandler(ex);
            }
        }
        
        this.searchEventsTable.setItems(eventObservable);
    }
}