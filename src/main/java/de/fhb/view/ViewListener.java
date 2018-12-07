package de.fhb.view;

import de.fhb.model.StationVo;

/**
 * Interface zum Erkennen von Änderungen über die View, wie Wechsel der View oder ändern der Daten durch den Benutzer über die View.
 */
public interface ViewListener {

    void onViewChangeClicked();

    void onDataChanged(StationVo station);
}
