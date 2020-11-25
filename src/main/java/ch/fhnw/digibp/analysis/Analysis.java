package ch.fhnw.digibp.analysis;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import ch.fhnw.digibp.AbstractEntity;
import org.springframework.format.annotation.DateTimeFormat;

@Embeddable
public class Analysis extends AbstractEntity {

    @Column
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;
    @Column
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDate;
    @Column
    private double resultValue;
    @Column
    private String resultDescription;
    @Column
    private String remarks;

    public Analysis() {
    }

    public Analysis(Map<String, Object> map) {
        startDate = getLocalDate("analysis.startDate", map);
        endDate = getLocalDate("analysis.endDate", map);
        resultDescription = getString("analysis.resultDescription", map);
        remarks = getString("analysis.remarks", map);
        resultValue = getDouble("analysis.resultValue", map);
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public String getResultDescription() {
        return resultDescription;
    }

    public void setResultDescription(String resultDescription) {
        this.resultDescription = resultDescription;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public double getResultValue() {
        return resultValue;
    }

    public void setResultValue(double resultValue) {
        this.resultValue = resultValue;
    }

    @Override
    public Map<String, Object> toMap() {
        return toMapWithPrefix("analysis.");
    }

    private Map<String, Object> toMapWithPrefix(String prefix) {
        Map<String, Object> map = new HashMap<>();
        map.put(prefix + "startDate", toString(getStartDate()));
        map.put(prefix + "endDate", toString(getEndDate()));
        map.put(prefix + "resultDescription", getResultDescription());
        map.put(prefix + "remarks", getRemarks());
        map.put(prefix + "resultValue", getResultValue());
        return map;
    }

}
