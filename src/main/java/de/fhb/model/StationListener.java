package de.fhb.model;

/**
 * Interface für alle Klassen, die das StationBo verwenden wollen.
 * Diese muss von allen Klassen, die StationBo benutzen implementiert werden.
 */
public interface StationListener {

    void onStationChanged();
    void onStationChanged2();
}
