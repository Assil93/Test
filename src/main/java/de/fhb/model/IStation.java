package de.fhb.model;
import java.util.Date;
import java.util.List;

/**
 * Interface für alle Station's. Diese Methoden müssen immer bereitgestellt werden.
 */
public interface IStation {


    void addStation(String name, Integer targetValue);
    void updateName(Long id, String name);
    void updateDate(Long id, Date date);
    void updateActual(Long id, Integer actualValue);
    StationCSG SelectStationById(Long id);
    List<StationCSG> SelectStations();
    

    

    

   

}
