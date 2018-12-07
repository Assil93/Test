package de.fhb.model;

import java.util.Date;
import java.util.List;

/**
 * Interface für alle StationBo's. Diese Methoden müssen immer bereitgestellt werden.
 */
public interface IStationBo {

    List<StationVo> findAll();

    StationVo findStationById(Long id);

    void updateStationDate(Long id, Date date);

    void updateStationValue(Long id, Integer actualValue);

    void updateStationName(Long id, String name);

    void addStation(String name, Integer targetValue);

}
