package de.fhb.presenter;

import de.fhb.model.IStationBo;
import de.fhb.model.StationBo;
import de.fhb.model.StationListener;
import de.fhb.model.StationVo;
import de.fhb.system.IceCreamRandomizerTask;
import de.fhb.view.AMonitorView;
import de.fhb.view.MonitorInsertDataView;
import de.fhb.view.MonitorShowDataView;
import de.fhb.view.ViewListener;
import eu.hansolo.enzo.notification.Notification;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import org.apache.commons.lang.ObjectUtils.Null;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Presenterklasse. Diese ist für das Ändern der View zuständig.
 * Über das Observer Pattern wird bei Änderungen im Model auch das View aktualisiert.
 */
public class Presenter extends Application implements ViewListener, StationListener {

    private static final Logger log = LoggerFactory.getLogger(Presenter.class);

    private static Presenter sInstance;
    private IStationBo stationBo;
    private static AMonitorView monitorView;
    private static AMonitorView monitorView1;
    private Stage primaryStage;
    private int viewNumber = 1;

    /**
     * Defaultkonstruktor. Dieser wird bei initialisieren der Anwendung durch JavaFX aufgerufen.
     */
    public Presenter() {
        this.stationBo = StationBo.getInstance(this);
    }

    private Presenter(String[] args) {
        super();
        //this.stationBo = StationBo.getInstance(this);
        launch(args);
    }

    public static Presenter getInstance() {
        if (sInstance == null) {
            sInstance = new Presenter(null);
        }
        return sInstance;
    }

    /**
     * Methode die beim Starten der JavaFX Application gestartet wird.
     *
     * @see Application
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        log.info("Starting IceCreamMonitor application");
        // View anzeigen
        changeView();

    }

    /**
     * Methode zum Wechseln der View.
     *
     * @throws Exception wenn beim Laden der JavaFX Komponenten etwas fehlschlägt.
     */
    private void changeView() throws Exception {
        if (viewNumber == 0) {
            Stage secondStage = new Stage();


            // .fxml Datei der View.
            String fxmlFile = "/fxml/monitorShowDataView.fxml";
            log.debug(String.format("Loading FXML for MonitorShowDataView from: %s", fxmlFile));
            FXMLLoader loader = new FXMLLoader();
            monitorView = new MonitorShowDataView(this);
            loader.setController(monitorView);
            Parent rootNode = (Parent) loader.load(getClass().getResourceAsStream(fxmlFile));

            log.debug("Showing MonitorShowDataView");
            Scene scene = new Scene(rootNode, 900, 650);
            scene.getStylesheets().add("/styles/styles.css");
            secondStage.setTitle("ICE APP");
            secondStage.setScene(scene);
            secondStage.show();

            viewNumber = 1;
            // Liste der Stationen aktualisieren.

           monitorView.updateStationList(stationBo.findAll());
           
           
            
        } else if (viewNumber == 1) {
            // .fxml Datei der View.
            String fxmlFile = "/fxml/monitorInsertDataView.fxml";
            log.debug(String.format("Loading FXML for MonitorInsertDataView from: %s", fxmlFile));
            FXMLLoader loader = new FXMLLoader();
            Stage secondStage = new Stage();
            monitorView1 = new MonitorInsertDataView(this);
            loader.setController(monitorView1);
            Parent rootNode = (Parent) loader.load(getClass().getResourceAsStream(fxmlFile));
          
            log.debug("Showing MonitorInsertDataView");
            Scene scene = new Scene(rootNode, 900, 650);
            scene.getStylesheets().add("/styles/styles.css");
            primaryStage.setTitle("ICE APP");
            primaryStage.setScene(scene);
            primaryStage.show();

            viewNumber = 0;
            // Liste der Stationen aktualisieren.
            monitorView1.updateStationList(stationBo.findAll());
            // EventHandler für das Beenden der Anwendung setzen.
            primaryStage.setOnCloseRequest(event -> {
                if (event.getEventType() == WindowEvent.WINDOW_CLOSE_REQUEST) {
                    Platform.exit();
                }
            });
        }
    }

    @Override
    public void stop() throws Exception {
        // Beim Beenden sicherstellen, dass alle Anwendungsteile gestoppt werden.
        super.stop();
        IceCreamRandomizerTask.stop();
        Notification.Notifier.INSTANCE.stop();
    }

    // Methoden für den ViewListener
    @Override
    public void onViewChangeClicked() {
        try {
            changeView();
        } catch (Exception e) {
           log.error("Got Exception: " + e.getMessage());
        }
    }

    @Override
    public void onDataChanged(StationVo station) {
        // Prüfen welche Daten geändert wurden und die Station updaten.
        if (station.getDate() != null) {
            stationBo.updateStationDate(station.getId(), station.getDate());
        }
        if (station.getActualValue() != null) {
            stationBo.updateStationValue(station.getId(), station.getActualValue());
        }
        if (station.getName() != null) {
            stationBo.updateStationName(station.getId(), station.getName());
        }
        monitorView.updateStationList(stationBo.findAll());
    }

    // Methoden für StationListener
    @Override
    public void onStationChanged() {
        log.debug("Daten geändert.");
        Platform.runLater(() -> {
            // View updaten
            monitorView.updateStationList(stationBo.findAll());
            monitorView1.updateStationList(stationBo.findAll());
            // Notification anzeigen
            Notification.Notifier.INSTANCE.notifyInfo("Info", "New Station added");
            // View wieder in der Vordergrund, nachdem die Notification gezeigt wurde.
            primaryStage.toFront();
        });
    }

    public void onStationChanged2() {
        log.debug("Daten geändert.");
        Platform.runLater(() -> {
            // View updaten
            monitorView.updateStationList(stationBo.findAll());
            // Notification anzeigen
            Notification.Notifier.INSTANCE.notifySuccess("Info", "Variance is calculated");
            // View wieder in der Vordergrund, nachdem die Notification gezeigt wurde.
            primaryStage.toFront();
            System.out.println("Hello World!");


        });
    }
}
