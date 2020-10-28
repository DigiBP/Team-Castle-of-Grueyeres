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
    private double amount;
    @Column
    private AmountUnit amountUnit;
    @Column
    private double temperature;
    @Column
    private TemperatureUnit temperatureUnit;
    @Column
    private ZonedDateTime entryDate;
    @Column
    private ZonedDateTime updateDate;

    public Sample() {
    }

    public Sample(Map<String, Object> map) {
        id = getLong("id", map);
        damaged = getBoolean("damaged", map);
        loadSampleType(map);
        amount = getDouble("amount", map);
        loadAmountUnit(map);
        temperature = getDouble("temperature", map);
        loadTemperatureUnit(map);
        entryDate = getZonedDateTime("entryDate", map);
        updateDate = getZonedDateTime("updateDate", map);
    }


    @Override
    public Map<String, Object> toMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("id", getId());
        map.put("damaged", isDamaged());
        map.put("sampleType", getSampleTypeValue());
        map.put("amount", getAmount());
        map.put("amountUnit", getAmountUnitValue());
        map.put("temperature", getTemperature());
        map.put("temperatureUnit", getTemperatureUnitValue());
        map.put("entryDate", toString(getEntryDate()));
        map.put("updateDate", toString(getUpdateDate()));
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

    private void loadSampleType(Map<String, Object> map) {
        if (mapHasKey("sampleType", map)) {
            sampleType = SampleType.valueOf((String) map.get("sampleType"));
        }
    }

    public String getSampleTypeValue() {
        return sampleType != null ? sampleType.name() : null;
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

    public AmountUnit getAmountUnit() {
        return amountUnit;
    }

    private void loadAmountUnit(Map<String, Object> map) {
        if (mapHasKey("amountUnit", map)) {
            amountUnit = AmountUnit.valueOf((String) map.get("amountUnit"));
        }
    }

    public void loadAmountUnit(AmountUnit amountUnit) {
        this.amountUnit = amountUnit;
    }

    public String getAmountUnitValue() {
        return amountUnit != null ? amountUnit.name() : null;
    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public TemperatureUnit getTemperatureUnit() {
        return temperatureUnit;
    }

    private void loadTemperatureUnit(Map<String, Object> map) {
        if (mapHasKey("temperatureUnit", map)) {
            temperatureUnit = TemperatureUnit.valueOf((String) map.get("temperatureUnit"));
        }
    }

    public void loadTemperatureUnit(TemperatureUnit temperatureUnit) {
        this.temperatureUnit = temperatureUnit;
    }

    public String getTemperatureUnitValue() {
        return temperatureUnit != null ? temperatureUnit.name() : null;
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

    public enum SampleType {
        BLOOD, TISSUE, URINE
    }

    public enum AmountUnit {
        MILLIGRAM, MILLILITER
    }

    public enum TemperatureUnit {
        CELSIUS, FAHRENHEIT
    }
}
