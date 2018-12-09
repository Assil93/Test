package de.fhb.model;

/**
 * Interface für alle Klassen, die das Station verwenden wollen.
 * Diese muss von allen Klassen, die Station benutzen implementiert werden.
 */
public interface StationListener {

    void onStationChanged();
    void onStationChanged2();
}
