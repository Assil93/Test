package de.fhb.system;

import de.fhb.model.IStation;
import de.fhb.model.Station;
import de.fhb.model.StationListener;
import org.apache.commons.lang.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Simuliert das System. Hier werden alle 10-15 Sekunden eine neue Staion hinzugefügt.
 
 */
public class IceCreamRandomizerTask extends TimerTask implements StationListener {

    private static Timer timer = new Timer();
    private static boolean shouldRun = true;
    private static IStation station;
    private static IceCreamRandomizerTask sInstance;

    private static final Logger log = LoggerFactory.getLogger(IceCreamRandomizerTask.class);

    public IceCreamRandomizerTask() {
        sInstance = this;
        station = Station.getInstance(this);
    }

    public static void stop() {
        // Stoppen des Timers
        shouldRun = false;
        timer.cancel();
        sInstance.cancel();
    }

    @Override
    public void run() {
        Random rand = new Random();
        // Zufälligen Namen erstellen.
        String name = RandomStringUtils.randomAlphanumeric(6).toUpperCase();
        // Zufälligen target value erstellen.
        int randomNum = rand.nextInt((75 - 25) + 1) + 25;
        // Speichern der neuen Station.
        station.addStation(name, randomNum);
        log.debug("Created Station: " + name);
        if (shouldRun) {
            // Nach 10-15 Sekunden den Task nochmal ausführen.
            int delay = (10 + new Random().nextInt(5)) * 1000;
            timer.schedule(new IceCreamRandomizerTask(), delay);
        }

    }

    @Override
    public void onStationChanged() {
        // Nichts zu tun hier. Muss allerdings implemntiert werden.
    }

    public void onStationChanged2() {
        // Nichts zu tun hier. Muss allerdings implemntiert werden.
    }
}
