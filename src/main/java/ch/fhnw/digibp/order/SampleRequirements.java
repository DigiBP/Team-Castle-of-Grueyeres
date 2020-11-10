package ch.fhnw.digibp.order;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import ch.fhnw.digibp.AbstractEntity;
import ch.fhnw.digibp.domain.PackageType;
import ch.fhnw.digibp.domain.SampleType;

@Embeddable
public class SampleRequirements extends AbstractEntity {

    public enum TestingMethod {
        TEST
    }

    @Column
    private double temperature;
    @Column
    private String hazardCategory;
    @Column
    private PackageType packageType;
    @Column
    private SampleType sampleType;
    @Column
    private TestingMethod testingMethod;

    public SampleRequirements() {
    }

    public SampleRequirements(Map<String, Object> map) {
        this.temperature = getDouble("temperature", map);
        this.hazardCategory = getString("hazardCategory", map);
        this.packageType = PackageType.valueOf(map);
        this.sampleType = SampleType.valueOf(map);
    }

    @Override
    public Map<String, Object> toMap() {
        return toMapWithPrefix("");
    }

    public Map<String, Object> toMapWithPrefix() {
        return toMapWithPrefix("sampleRequirements.");
    }

    private Map<String, Object> toMapWithPrefix(String prefix) {
        Map<String, Object> map = new HashMap<>();
        map.put(prefix + "temperature", getTemperature());
        map.put(prefix + "hazardCategory", getHazardCategory());
        if (getPackageType() != null) {
            map.put(prefix + "packageType", getPackageType().name());
        } else {
            map.put(prefix + "packageType", null);
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

    public String getHazardCategory() {
        return hazardCategory;
    }

    public void setHazardCategory(String hazardCategory) {
        this.hazardCategory = hazardCategory;
    }

    public PackageType getPackageType() {
        return packageType;
    }

    public void setPackageType(PackageType packageType) {
        this.packageType = packageType;
    }

    public SampleType getSampleType() {
        return sampleType;
    }

    public void setSampleType(SampleType sampleType) {
        this.sampleType = sampleType;
    }

    @Override
    public String toString() {
        return "SampleRequirements{" +
                "temperature=" + temperature +
                ", hazardCategory='" + hazardCategory + '\'' +
                ", packageType=" + packageType +
                ", sampleType=" + sampleType +
                '}';
    }
}
