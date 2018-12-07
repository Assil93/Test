package de.fhb;

import de.fhb.presenter.Presenter;
import de.fhb.system.IceCreamRandomizerTask;

/**
 * Main Klasse zum initialisieren der Anwendung.
 */
public class MainApp {

    public static void main(String[] args) throws Exception {
        // Das "System" starten (automatisches Erstellen neuer Stationen)
        new IceCreamRandomizerTask().run();
        // Presenter initialisieren
        Presenter.getInstance();
    }
}
