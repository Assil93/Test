package de.fhb.model;
import javafx.beans.property.SimpleStringProperty;
import java.io.Serializable;
import java.util.Date;


public class StationVo implements Serializable {

    private static Long nextId = 0L;
    private Long id;
    private String name;
    private Integer targetValue;
    private Integer actualValue;
    private Integer variance;
    private Date date;

    private SimpleStringProperty nameSSP, dateSSP, targetSSP, actualSSP, varianceSSP;



    public StationVo(String name, Integer targetValue, Integer actualValue, Integer variance, Date date) {
        this(name, targetValue);
        this.actualValue = actualValue;
        this.variance = variance;
        this.date = date;
        //Die SimpleStringProperties werden für die Zuweisung der Inhalte in der Tabelle benötigt.
        this.nameSSP = new SimpleStringProperty(name);
        this.targetSSP = new SimpleStringProperty(targetValue.toString());
        this.actualSSP = new SimpleStringProperty(actualValue.toString());
        this.varianceSSP = new SimpleStringProperty(variance.toString());
        this.dateSSP = new SimpleStringProperty(date.toString());

    }

    public StationVo(String name, Integer targetValue) {
        this.id = nextId;
        this.name = name;
        this.targetValue = targetValue;
        this.date = new Date();
        nextId++;

        this.nameSSP = new SimpleStringProperty(name);
        this.targetSSP = new SimpleStringProperty(this.targetValue.toString());
        this.dateSSP = new SimpleStringProperty(this.date.toString());
        this.actualSSP = new SimpleStringProperty(null);
        this.varianceSSP = new SimpleStringProperty(null);

    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Integer getTargetValue() {
        return targetValue;
    }

    public Integer getActualValue() {
        return actualValue;
    }

    public Integer getVariance() {
        return variance;
    }

    public Date getDate() {
        return date;
    }

    public void setName(String name) {
        this.name = name;
        this.nameSSP.set(name);
    }

    public void setActualValue(Integer actualValue) {
        this.actualValue = actualValue;
        this.actualSSP.set(actualValue.toString());
    }

    public void setDate(Date date) {
        this.date = date;
        this.dateSSP.set(date.toString());
    }

    protected void setVariance(Integer variance) {
        this.variance = variance;
        this.varianceSSP.set(variance.toString());
    }

    public SimpleStringProperty nameSSPProperty() {
        return nameSSP;
    }

    public SimpleStringProperty dateSSPProperty() {
        return dateSSP;
    }

    public SimpleStringProperty targetSSPProperty() {
        return targetSSP;
    }

    public SimpleStringProperty actualSSPProperty() {
        return actualSSP;
    }

    public SimpleStringProperty varianceSSPProperty() {
        return varianceSSP;
    }

    @Override
    public String toString() {
        return getName();
    }
}
