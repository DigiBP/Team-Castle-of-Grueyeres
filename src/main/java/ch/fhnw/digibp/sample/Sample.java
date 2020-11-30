package ch.fhnw.digibp.sample;

import java.time.LocalDate;
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
    private boolean temperatureIndicatorNotOk;
    @Column
    private LocalDate entryDate;
    @Column
    private ZonedDateTime updateDate;

    public Sample() {
    }

    public Sample(Map<String, Object> map) {
        id = getLong("sample.id", map);
        damaged = getBoolean("sample.damaged", map);
        this.sampleType = SampleType.valueOf("sample.sampleType", map);
        entryDate = getLocalDate("sample.entryDate", map);
        updateDate = getZonedDateTime("sample.updateDate", map);
        temperatureIndicatorNotOk = getBoolean("sample.temperatureIndicatorNotOk", map);
    }


    @Override
    public Map<String, Object> toMap() {
        return toMapWithPrefix("sample.");
    }

    private Map<String, Object> toMapWithPrefix(String prefix) {
        Map<String, Object> map = new HashMap<>();
        map.put(prefix + "id", getId());
        map.put(prefix + "damaged", isDamaged());
        map.put(prefix + "sampleType", getSampleTypeValue());
        map.put(prefix + "entryDate", toString(getEntryDate()));
        map.put(prefix + "updateDate", toString(getUpdateDate()));
        map.put(prefix + "temperatureIndicatorNotOk", isTemperatureIndicatorNotOk());
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

    public void loadSampleType(SampleType sampleType) {
        this.sampleType = sampleType;
    }

    public LocalDate getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(LocalDate entryDate) {
        this.entryDate = entryDate;
    }

    public ZonedDateTime getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(ZonedDateTime updateDate) {
        this.updateDate = updateDate;
    }

    public void setSampleType(SampleType sampleType) {
        this.sampleType = sampleType;
    }

    public boolean isTemperatureIndicatorNotOk() {
        return temperatureIndicatorNotOk;
    }

    public void setTemperatureIndicatorNotOk(boolean temperatureIndicatorOk) {
        this.temperatureIndicatorNotOk = temperatureIndicatorOk;
    }

    @Override
    public String toString() {
        return "Sample{" +
                "id=" + id +
                ", damaged=" + damaged +
                ", sampleType=" + sampleType +
                ", temperatureIndicatorNotOk=" + temperatureIndicatorNotOk +
                ", entryDate=" + entryDate +
                ", updateDate=" + updateDate +
                '}';
    }

}
