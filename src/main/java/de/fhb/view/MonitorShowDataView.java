package de.fhb.view;

import de.fhb.model.StationVo;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;

import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Created by Tobi on 30.11.2015.
 */
public class MonitorShowDataView extends AMonitorView implements Initializable {

    @FXML
    private Button changeViewBtn;
    @FXML
    private TableView<StationVo> tableView;
    @FXML
    private TableColumn<StationVo, String> tableColumName;
    @FXML
    private TableColumn<StationVo, String> tableColumDate;
    @FXML
    private TableColumn<StationVo, String> tableColumTarget;
    @FXML
    private TableColumn<StationVo, String> tableColumActual;
    @FXML
    private TableColumn<StationVo, String> tableColumVariance;
    @FXML
    private ChoiceBox<String> choiceBox;

    private ObservableList<StationVo> observableList;


    public MonitorShowDataView(Object obj) {
        super(obj);
    }

    /**
     * Methode zum Updaten der Liste der von Stationen in der View
     *
     * @param list Liste aller Stationen
     */
    @Override
    public void updateStationList(List<StationVo> list) {
        if (list != null) {
            observableList = FXCollections.observableArrayList(list);

        }
        if (choiceBox.getSelectionModel().isSelected(0)) {
            tableView.setItems(observableList);

        }


    }

    /**
     * Die Methode verändert die Tabelle hinsichtlich der anzuzeigenden Elemente.
     * Sie Filtert je nach Auswahl.
     *
     * @param choice Auswahl als Zahlenwert von 0-3
     */
    public void filterTable(int choice) {
        FilteredList<StationVo> fList = new FilteredList<StationVo>(observableList, p -> true);
        fList.setPredicate(station -> {
            switch (choice) {
                case 0:
                    return true;
                case 1:
                    return station.getActualValue() != null && station.getActualValue() * 100 / station.getTargetValue() <= 90;
                case 2:
                    return station.getActualValue() != null && station.getActualValue() * 100 / station.getTargetValue() > 90 && station.getActualValue() * 100 / station.getTargetValue() < 105;
                case 3:
                    return station.getActualValue() != null && station.getActualValue() * 100 / station.getTargetValue() >= 105;
                default:
                    return false;
            }
        });
        tableView.setItems(fList);


    }

    /**
     * Methode erstellt alle nötigen Komponenten der View.
     * So wie Listen zur Darstellung in der Tabelle, zur Auswahl und Listener
     * @param location
     * @param resources
     */
    public void initialize(URL location, ResourceBundle resources) {

        tableColumName.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<StationVo, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<StationVo, String> param) {
                return param.getValue().nameSSPProperty();
            }
        });
        tableColumDate.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<StationVo, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<StationVo, String> param) {
                return param.getValue().dateSSPProperty();
            }
        });
        tableColumTarget.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<StationVo, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<StationVo, String> param) {
                return param.getValue().targetSSPProperty();
            }
        });
        tableColumActual.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<StationVo, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<StationVo, String> param) {
                return param.getValue().actualSSPProperty();
            }
        });
        tableColumVariance.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<StationVo, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<StationVo, String> param) {
                return param.getValue().varianceSSPProperty();
            }
        });
        ObservableList<String> observableChoichList = FXCollections.observableArrayList(Arrays.asList("Alle", "-10% Abweichung", "Zwischen -10% und +5%", "über +5%"));
        this.choiceBox.setItems(observableChoichList);
        this.choiceBox.getSelectionModel().selectFirst();
        this.choiceBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (newValue != null) {
                    switch (newValue) {
                        case "-10% Abweichung":
                            filterTable(1);
                            break;
                        case "Zwischen -10% und +5%":
                            filterTable(2);
                            break;
                        case "über +5%":
                            filterTable(3);
                            break;
                        default:
                            filterTable(0);
                            updateStationList(null);

                    }
                }
            }
        });


        this.changeViewBtn.setOnMouseClicked(new EventHandler<MouseEvent>() {

            public void handle(MouseEvent event) {
                if (event.getButton().equals(MouseButton.PRIMARY)) {
                    listener.onViewChangeClicked();
                    changeViewBtn.setText("Clicked");
                }
            }
        });
    }

}