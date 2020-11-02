package ch.fhnw.digibp.sample;

import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import ch.fhnw.digibp.AbstractEntity;
import ch.fhnw.digibp.domain.PackageType;
import ch.fhnw.digibp.domain.SampleType;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "samples")
public class Sample extends AbstractEntity {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column
    private boolean damaged;
    @Column
    private SampleType sampleType;
    @Column
    private PackageType packageType;
    @Column
    private double amount;
    @Column
    private double temperature;
    @Column
    private boolean temperatureIndicatorOk;
    @Column
    private ZonedDateTime entryDate;
    @Column
    private ZonedDateTime updateDate;

    public Sample() {
    }

    public Sample(Map<String, Object> map) {
        id = getLong("sample.id", map);
        damaged = getBoolean("sample.damaged", map);
        this.packageType = PackageType.valueOf(map);
        this.sampleType = SampleType.valueOf("sample.sampleType", map);
        amount = getDouble("sample.amount", map);
        temperature = getDouble("sample.temperature", map);
        entryDate = getZonedDateTime("sample.entryDate", map);
        updateDate = getZonedDateTime("sample.updateDate", map);
        temperatureIndicatorOk = getBoolean("sample.temperatureIndicatorOk", map);
    }


    @Override
    public Map<String, Object> toMap() {
        return toMapWithPrefix("");
    }

    public Map<String, Object> toMapWithPrefix() {
        return toMapWithPrefix("sample.");
    }

    private Map<String, Object> toMapWithPrefix(String prefix) {
        Map<String, Object> map = new HashMap<>();
        map.put(prefix + "id", getId());
        map.put(prefix + "damaged", isDamaged());
        map.put(prefix + "sampleType", getSampleTypeValue());
        map.put(prefix + "packageType", getPackageTypeValue());
        map.put(prefix + "amount", getAmount());
        map.put(prefix + "temperature", getTemperature());
        map.put(prefix + "entryDate", toString(getEntryDate()));
        map.put(prefix + "updateDate", toString(getUpdateDate()));
        return map;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isDamaged() {
        return damaged;
    }

    public void setDamaged(boolean damaged) {
        this.damaged = damaged;
    }

    public SampleType getSampleType() {
        return sampleType;
    }

    @JsonIgnore
    public String getSampleTypeValue() {
        return sampleType != null ? sampleType.name() : null;
    }

    @JsonIgnore
    public String getPackageTypeValue() {
        return packageType != null ? packageType.name() : null;
    }

    public void loadSampleType(SampleType sampleType) {
        this.sampleType = sampleType;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public ZonedDateTime getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(ZonedDateTime entryDate) {
        this.entryDate = entryDate;
    }

    public ZonedDateTime getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(ZonedDateTime updateDate) {
        this.updateDate = updateDate;
    }

    public PackageType getPackageType() {
        return packageType;
    }


    public void setSampleType(SampleType sampleType) {
        this.sampleType = sampleType;
    }

    public void setPackageType(PackageType packageType) {
        this.packageType = packageType;
    }

    public boolean isTemperatureIndicatorOk() {
        return temperatureIndicatorOk;
    }

    public void setTemperatureIndicatorOk(boolean temperatureIndicatorOk) {
        this.temperatureIndicatorOk = temperatureIndicatorOk;
    }

    @Override
    public String toString() {
        return "Sample{" +
                "id=" + id +
                ", damaged=" + damaged +
                ", sampleType=" + sampleType +
                ", packageType=" + packageType +
                ", amount=" + amount +
                ", temperature=" + temperature +
                ", temperatureIndicatorOk=" + temperatureIndicatorOk +
                ", entryDate=" + entryDate +
                ", updateDate=" + updateDate +
                '}';
    }

}
