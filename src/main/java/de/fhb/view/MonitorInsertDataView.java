package de.fhb.view;

import de.fhb.model.StationCSG;
import de.fhb.presenter.Presenter;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Point2D;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.ResourceBundle;

/**
 * JavaFX Klasse zum Anzeigen der InsertDataView.
 * <p>
 * Created on 23.11.2015.
 */
public class MonitorInsertDataView extends AMonitorView {

    // JavaFX Elemente
    @FXML
    private ListView<StationCSG> stationListView;
    @FXML
    private TextField stationIDTextField;
    @FXML
    private TextField dateTextField;
    @FXML
    private TextField targetTextField;
    @FXML
    private TextField actualTextField;
    @FXML
    private TextField varianceTextField;
    @FXML
    private Button changeViewBtn;
    @FXML
    private Button saveBtn;
    
    // Logger
    private static final Logger log = LoggerFactory.getLogger(MonitorInsertDataView.class);

    /**
     * Methode zum Initialisieren der View
     *
     * @see Initializable
     */
    public void initialize(URL location, ResourceBundle resources) {
      


        // Listener für ListView
        stationListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<StationCSG>() {
            @Override
            public void changed(ObservableValue<? extends StationCSG> observable, StationCSG oldValue, StationCSG newValue) {
                if (newValue != null) {

                    // falls der Fokus nicht auf dem Feld ist und eine neue
                    // Station geklickt wurde den Namen der Station aktualisieren
                    if (!stationIDTextField.isFocused() || (!newValue.equals(oldValue) && oldValue != null)) {
                        stationIDTextField.setText(newValue.getName());
                    }

                    // Target TextField aktualisieren
                    targetTextField.setText(String.valueOf(newValue.getTargetValue()));

                    // falls der Fokus nicht auf dem Feld ist
                    // und eine neue Station geklickt wurde das Datum aktualisieren
                    if (!dateTextField.isFocused() || (!newValue.equals(oldValue) && oldValue != null)) {
                        if (newValue.getDate() != null) {
                            dateTextField.setText(new SimpleDateFormat("dd.MM.yyyy").format(newValue.getDate()));
                        } else {
                            dateTextField.setText("");
                        }
                    }

                    // falls der Fokus nicht auf dem Feld ist
                    // und eine neue Station geklickt wurde den aktuellen Wert aktualisieren
                    if (!actualTextField.isFocused() || (!newValue.equals(oldValue) && oldValue != null)) {
                        if (newValue.getActualValue() != null) { // nur wenn bereits ein aktueller Wert vorhanden
                            actualTextField.setText(String.valueOf(newValue.getActualValue()));
                        } else {
                            actualTextField.setText("");
                        }
                    }

                    // falls Varianz vorhanden diese anzeigen
                   
                        
                      if (newValue.getVariance() != null){
                        varianceTextField.setText(String.valueOf(newValue.getVariance()));
                       varianceTextField.setStyle(listener.setVarianceColor(newValue.getTargetValue(),newValue.getActualValue())); // 10% unter target
                    }
                else {
                    varianceTextField.setText("");
                }
                

                    // handle OnKeyReleased Event für Datums TextField
                    dateTextField.setOnKeyReleased(new EventHandler<KeyEvent>() {
                        @Override
                        public void handle(KeyEvent event) {
                            if (event.getCode().equals(KeyCode.ENTER)) { // falls Enter gedrückt speichern
                                if (validateDate(dateTextField.getText())) {
                                    try {
                                        newValue.setDate(new SimpleDateFormat("dd.MM.yyyy").parse(dateTextField.getText()));
                                        listener.onDataChanged(newValue);
                                    } catch (ParseException e) {
                                        log.error("Got Exception: " + e.getMessage());
                                    }
                                } else {
                                    // falls Datumsformat falsch, Tooltip anzeigen
                                    showTooltip(dateTextField, "Date format dd.MM.yyyy");
                                    dateTextField.setText(new SimpleDateFormat("dd.MM.yyyy").format(newValue.getDate()));
                                }
                            } else {
                                // Tooltip ausblenden sobald neue Eingabe erfolgt
                                hideTooltip(dateTextField);
                            }
                        }
                    });

                    // handle OnKeyReleased Event für actual TextField
                    actualTextField.setOnKeyReleased(new EventHandler<KeyEvent>() {
                        @Override
                        public void handle(KeyEvent event) {
                            if (event.getCode() == KeyCode.ENTER) { // falls Enter gedrückt speichern
                                // falls hier was schief läuft, wird ein Tooltip angezeigt
                                Integer newActualValue = validateActualValue(actualTextField.getText());
                                if (newActualValue != null) {
                                    newValue.setActualValue(newActualValue);
                                    listener.onDataChanged(newValue);
                                }
                            } else {
                                // Tooltip ausblenden wenn andere Taste gedrückt wurde
                                hideTooltip(actualTextField);
                            }
                        }
                    });

                    // EventHandler für Speicher Button
                    saveBtn.setOnMouseClicked(new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent event) {
                            if (event.getButton().equals(MouseButton.PRIMARY)) { // falls primäre Maustaste
                                if (!stationIDTextField.getText().isEmpty()) {
                                    newValue.setName(stationIDTextField.getText());
                                }
                                if (!actualTextField.getText().isEmpty()) {
                                    // falls hier was schief läuft, wird ein Tooltip angezeigt
                                    Integer newActualValue = validateActualValue(actualTextField.getText());
                                    if (newActualValue != null) { // falls parsen ok
                                        newValue.setActualValue(newActualValue);
                                        listener.onDataChanged(newValue);
                                    }
                                }
                                if (!dateTextField.getText().isEmpty()) {
                                    if (validateDate(dateTextField.getText())) { // prüfen ob Format stimmt
                                        try {
                                            newValue.setDate(new SimpleDateFormat("dd.MM.yyyy").parse(dateTextField.getText()));
                                        } catch (ParseException e) {
                                            log.error("Got Exception: " + e.getMessage());
                                        }
                                    } else {
                                        // Tooltip anzeigen wenn Format falsch
                                        showTooltip(dateTextField, "Date format dd.MM.yyyy");
                                    }
                                }
                                listener.onDataChanged(newValue);
                            }
                        }
                    });
                }
            }
        });

        // Button zum Ändern der View. Wird vom Presenter ausgeführt.
        changeViewBtn.setOnMouseClicked(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {
                if (event.getButton().equals(MouseButton.PRIMARY)) {
                    listener.onViewChangeClicked();
                }
            }
        });
    }

    /**
     * Überprüft ob Datums String richtiges Format hat.
     *
     * @param date als String.
     * @return <code>true</code> falls String korrekt oder
     * <code>false</code> falls String nicht dem Format entspricht.
     */
    private boolean validateDate(String date) {
        return date.matches("^(((0?[1-9]|[12]\\d|3[01])[\\.\\-\\/](0?[13578]|1[02])[\\.\\-\\/]((1[6-9]|[2-9]\\d)?" +
                "\\d{2}))|((0?[1-9]|[12]\\d|30)[\\.\\-\\/](0?[13456789]|1[012])[\\.\\-\\/]((1[6-9]|[2-9]\\d)?\\d{2}" +
                "))|((0?[1-9]|1\\d|2[0-8])[\\.\\-\\/]0?2[\\.\\-\\/]((1[6-9]|[2-9]\\d)?\\d{2}))|(29[\\.\\-\\/]0?2[\\." +
                "\\-\\/]((1[6-9]|[2-9]\\d)?(0[48]|[2468][048]|[13579][26])|((16|[2468][048]|[3579][26])00)|00)))$");
    }

    /**
     * Prüft ob der übergebende String zu einem Integer geparst werden kann und größer als 0 ist.
     * Zeigt Tooltip falls beim Parsen ein Fehler auftritt.
     *
     * @param value String der geparst werden soll.
     * @return geparster Integer wert oder <code>null</code>
     */
    private Integer validateActualValue(String value) {
        Integer returnInt = null;
        try {
            if (Integer.parseInt(value) < 0) {
                showTooltip(actualTextField, "The value must be greater 0.");
            } else {
                returnInt = Integer.parseInt(value);
            }
        } catch (NumberFormatException e) {
            showTooltip(actualTextField, "Please enter a number greater 0.");
        }
        return returnInt;
    }

    // Tooltip ausblenden
    private void hideTooltip(TextField field) {
        if (field.getTooltip() != null && field.getTooltip().isShowing()) {
            field.getTooltip().hide();
        }
    }

    // Zeige bei falscher Eingabe ein Tooltip für die TextFields
    private void showTooltip(TextField field, String message) {
        Point2D p = field.localToScene(0.0, 0.0);
        Tooltip tooltip = new Tooltip(message);
        if (field.getTooltip() != null) {
            field.getTooltip().hide();
        }
        field.setTooltip(tooltip);
        tooltip.show(field,
                p.getX() + field.getScene().getX() + field.getScene().getWindow().getX(),
                p.getY() + field.getScene().getY() + field.getScene().getWindow().getY() + 27);
    }

    /**
     * Aktualisiert die ListView. Wird vom Presenter aufgerufen.
     *
     * @param list Liste von StationCSG Objekten, die in der View angezeigt werden sollen
     * @see Presenter#onStationChanged()
     */

    public void updateStationList(List<StationCSG> list) {
        log.debug("Updating ListView");
        ObservableList<StationCSG> observableList = FXCollections.observableArrayList(list);
        stationListView.setItems(observableList);
    }

    /**
     * Konstruktor für die MonitorInsertDataView Klasse.
     *
     * @param obj Die aufrufende Klasse. Diese muss den ViewListener implementieren.
     * @throws ClassCastException wenn die übergebene Klasse nicht den ViewListener implementiert.
     * @see ViewListener
     */
    public MonitorInsertDataView(Object obj) {
        super(obj);
    }

}
