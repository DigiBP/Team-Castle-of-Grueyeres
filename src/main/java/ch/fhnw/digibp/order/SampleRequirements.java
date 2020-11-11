package ch.fhnw.digibp.order;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import ch.fhnw.digibp.AbstractEntity;
import ch.fhnw.digibp.domain.Priority;
import ch.fhnw.digibp.domain.SampleType;

@Embeddable
public class SampleRequirements extends AbstractEntity {

    public enum TestingMethod {
        TEST
    }

    @Column
    private double temperature;
    @Column
    private Priority severityOfMisClassification;
    @Column
    private SampleType sampleType;
    @Column
    private boolean biohazard;
    @Column
    private TestingMethod testingMethod;

    public SampleRequirements() {
    }

    public SampleRequirements(Map<String, Object> map) {
        this.temperature = getDouble("sampleRequirements.temperature", map);
        this.biohazard = getBoolean("sampleRequirements.biohazard", map);
        this.sampleType = SampleType.valueOf("sampleRequirements.sampleType", map);
        this.severityOfMisClassification = Priority.valueOf("sampleRequirements.severityOfMisClassification", map);
    }

    @Override
    public Map<String, Object> toMap() {
        return toMapWithPrefix("sampleRequirements.");
    }
    
    private Map<String, Object> toMapWithPrefix(String prefix) {
        Map<String, Object> map = new HashMap<>();
        map.put(prefix + "temperature", getTemperature());
        map.put(prefix + "biohazard", isBiohazard());
        if (getSeverityOfMisClassification() != null) {
            map.put(prefix + "severityOfMisClassification", getSeverityOfMisClassification().name());
        } else {
            map.put(prefix + "severityOfMisClassification", null);
        }
        if (getSampleType() != null) {
            map.put(prefix + "sampleType", getSampleType().name());
        } else {
            map.put(prefix + "sampleType", null);
        }
        return map;
    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public SampleType getSampleType() {
        return sampleType;
    }

    public void setSampleType(SampleType sampleType) {
        this.sampleType = sampleType;
    }

    public Priority getSeverityOfMisClassification() {
        return severityOfMisClassification;
    }

    public void setSeverityOfMisClassification(Priority severityOfMisClassification) {
        this.severityOfMisClassification = severityOfMisClassification;
    }

    public boolean isBiohazard() {
        return biohazard;
    }

    public void setBiohazard(boolean biohazard) {
        this.biohazard = biohazard;
    }

    @Override
    public String toString() {
        return "SampleRequirements{" +
                "temperature=" + temperature +
                ", severityOfMisClassification=" + severityOfMisClassification +
                ", sampleType=" + sampleType +
                ", biohazard=" + biohazard +
                '}';
    }
}
