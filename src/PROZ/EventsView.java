package PROZ;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.ResourceBundle;

/**
 * @author BAKSIUv13
 */
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
        
        this.searchEventsTable.getSelectionModel().setSelectionMode(
                SelectionMode.MULTIPLE);
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
    
    public void searchEvents(Event event)
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
        
        for (CulturalEventHasPerformer eventAndPerformer : eventHasPerformers)
        {
            if (eventAndPerformer.getPerformerName().contains(performer))
            {
                eventIds.add(eventAndPerformer.getCulturalEventId());
            }
        }
        
        for (Integer id : eventIds)
        {
            try
            {
                CulturalEventDB eventToAdd = this.model.getCulturalEvent(id);
                
                Calendar currentTime = Calendar.getInstance();
                Date currentDate = new Date((currentTime.getTime()).getTime());
                
                if (eventToAdd.getDate().after(currentDate))
                {
                    eventObservable.add(this.model.getCulturalEvent(id));
                }
            }
            catch (SQLException ex)
            {
                ViewMethods.exceptionHandler(ex);
            }
        }
        
        this.searchEventsTable.setItems(eventObservable);
    }
    
    public void buySelectedEventsAction()
    throws Exception
    {
        ObservableList<CulturalEventDB> selectedEvents = this.searchEventsTable
                .getSelectionModel().getSelectedItems();
        
        // get root
        FXMLLoader fxmlLoader = new FXMLLoader();
        Parent root = fxmlLoader.load(
                this.getClass().getResource("OrderView.fxml").openStream());
        
        // set model in new controller
        OrderView orderViewViewController = (OrderView) fxmlLoader
                .getController();
        orderViewViewController.setModel(model);
        orderViewViewController.setLogin(login);
        orderViewViewController.setSelectedEvents(selectedEvents);
        
        Scene orderScene = new Scene(root);
        Stage orderStage = new Stage();
        orderStage.setScene(orderScene);
        
        orderStage.initModality(Modality.APPLICATION_MODAL);
        orderStage.setTitle("Ticket Sales System");
        orderStage.setResizable(false);
        orderStage.getIcons().add(new Image("PROZ/icon.jpg"));
        
        orderStage.showAndWait();
    }
    
    public void searchEnterAction(KeyEvent event)
    {
        if (event.getCode().equals(KeyCode.ENTER))
        {
            searchEvents(event);
        }
    }
}