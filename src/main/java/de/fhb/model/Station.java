package de.fhb.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Business Object für den Zugriff auf das StationCSG.
 * Bei der Klasse handelt es sich um einen Singleton.
 * Initialisieren der Klasse über die getInstance() Methode.
 */
public class Station implements IStation {

    /**
     * Statische Liste aller Stationen.
     */
    private static List<StationCSG> stationList = new ArrayList<>();
    /**
     * Statische Liste aller Listener.
     */
    private static List<StationListener> listeners = new ArrayList<>();
    private static Station sInstance;

    /**
     * Gibt die Instanz dieser Klasse zurück. Falls noch keine vorhanden, wird diese erstellt.
     *
     * @param obj Klasse, die den StationListener implementiert.
     * @return Instanz der Klasse.
     */
    public static Station getInstance(Object obj) {
        if (sInstance == null) {
            sInstance = new Station(obj);
        } else {
            sInstance.onAttach(obj);
        }
        return sInstance;
    }

    private Station(Object obj) {
        onAttach(obj);
    }

    // Prüft ob die Klasse den Listener implementiert.
    private void onAttach(Object obj) throws ClassCastException {
        try {
            StationListener listener = (StationListener) obj;
            listeners.add(listener);
        } catch (Exception e) {
            // falls Cast auf den Listener fehlschlägt, wir eine Exception geworfen.
            throw new ClassCastException(String.format("%s must implement %s", obj.getClass().getSimpleName(),
                    StationListener.class.getSimpleName()));
        }
    }

    /**
     * Methode zum Finden aller StationCSG.
     *
     * @return Liste aller Stationen.
     */
    @Override
    public List<StationCSG> SelectStations() {
        return stationList;
    }

    /**
     * Findet eine bestimmte Station aus der Liste aller Stationen.
     *
     * @param id des Objektes.
     * @return Die Station oder <code>null</code> falls Station nicht existiert.
     */
    @Override
    public StationCSG SelectStationById(Long id) {
        StationCSG returnStation = null;
        boolean stationFound = false;
        int i = 0;
        while (!stationFound && stationList.size() > i) {
            StationCSG station = stationList.get(i);
            if (id == station.getId()) {
                returnStation = station;
                stationFound = true;
            }
            i++;
        }
        return returnStation;
    }

    /**
     * Methode zum Updaten des Datums einer Station.
     *
     * @param id   der zu updatenden Station.
     * @param date Das neue Datum.
     */
    @Override
    public void updateDate(Long id, Date date) {
        StationCSG updateStation = SelectStationById(id);
        updateStation.setDate(date);

    }

    /**
     * Methode zum Updaten des aktuellen Wertes.
     *
     * @param id          der zu updatenden Station.
     * @param actualValue Der neue Wert.
     */
    @Override
    public void updateActual(Long id, Integer actualValue) {
        StationCSG updateStation = SelectStationById(id);
        updateStation.setActualValue(actualValue);
        updateStation.setVariance(updateStation.getActualValue() - updateStation.getTargetValue());
        notifyListeners2();
    }

    /**
     * Methode zum Updaten des Staionsnamens.
     *
     * @param id   der zu updatenden Station.
     * @param name Der neue Name.
     */
    @Override
    public void updateName(Long id, String name) {
        StationCSG updateStation = SelectStationById(id);
        updateStation.setName(name);

    }

    /**
     * Methode zum Hinzufügen einer neuen Station.
     *
     * @param name        der neuen Station.
     * @param targetValue der neuen Station.
     */
    @Override
    public void addStation(String name, Integer targetValue) {
        stationList.add(new StationCSG(name, targetValue));
        notifyListeners();
    }

    // Benachrichtigt die Listener über Änderungen im Model.
    private void notifyListeners() {
        for (StationListener listener : listeners) {
            listener.onStationChanged();
        }

    }
    private void notifyListeners2() {
        for (StationListener listener : listeners) {
            listener.onStationChanged2();
        }

    }
}
