package de.fhb.view;

import de.fhb.model.StationCSG;

/**
 * Interface zum Erkennen von Änderungen über die View, wie Wechsel der View oder ändern der Daten durch den Benutzer über die View.
 */
public interface ViewListener {

    void onViewChangeClicked();
    String  setVarianceColor(int target, int actual);
    void onDataChanged(StationCSG station);
}
