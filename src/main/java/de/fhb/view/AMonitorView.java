package de.fhb.view;

import de.fhb.model.StationCSG;
import javafx.fxml.Initializable;

import java.util.List;

/**
 * Abstratkte View Klasse.
 */
public abstract class AMonitorView implements Initializable {

    protected ViewListener listener;

    public AMonitorView(Object obj) {
        onAttach(obj);
    }

    /**
     * Prüft ob die Aufrufende Klasse den ViewListener implementiert.
     *
     * @param obj die aufrufende Klasse.
     * @throws ClassCastException falls die aufrufende Klasse nicht den ViewListener implemntiert.
     */
    protected void onAttach(Object obj) throws ClassCastException {
        try {
            listener = (ViewListener) obj;
        } catch (Exception e) {
            throw new ClassCastException(String.format("%s must implement %s", obj.getClass().getSimpleName(),
                    ViewListener.class.getSimpleName()));
        }
    }

    public abstract void updateStationList(List<StationCSG> list);


}
