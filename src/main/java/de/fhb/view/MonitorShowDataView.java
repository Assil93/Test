package de.fhb.view;

import de.fhb.model.StationCSG;
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

 */
public class MonitorShowDataView extends AMonitorView implements Initializable {



    @FXML
    private TableView<StationCSG> tableView;
    @FXML
    private TableColumn<StationCSG, String> tableColumName;
    @FXML
    private TableColumn<StationCSG, String> tableColumDate;
    @FXML
    private TableColumn<StationCSG, String> tableColumTarget;
    @FXML
    private TableColumn<StationCSG, String> tableColumActual;
    @FXML
    private TableColumn<StationCSG, String> tableColumVariance;


    private ObservableList<StationCSG> observableList;


    public MonitorShowDataView(Object obj) {
        super(obj);
    }


    @Override
    public void updateStationList(List<StationCSG> list) {
        if (list != null) {
            observableList = FXCollections.observableArrayList(list);
        }
            tableView.setItems(observableList);
    }


    public void initialize(URL location, ResourceBundle resources) {

        tableColumName.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<StationCSG, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<StationCSG, String> param) {
                return param.getValue().nameSSPProperty();
            }
        });
        tableColumDate.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<StationCSG, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<StationCSG, String> param) {
                return param.getValue().dateSSPProperty();
            }
        });
        tableColumTarget.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<StationCSG, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<StationCSG, String> param) {
                return param.getValue().targetSSPProperty();
            }
        });
        tableColumActual.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<StationCSG, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<StationCSG, String> param) {
                return param.getValue().actualSSPProperty();
            }
        });
        tableColumVariance.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<StationCSG, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<StationCSG, String> param) {
                return param.getValue().varianceSSPProperty();

            }


        });
        tableColumVariance.setStyle("-fx-text-fill: red;");
    }

}