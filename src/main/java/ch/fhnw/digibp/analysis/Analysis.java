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
    private ResultCategory resultCategory;
    @Column
    private double resultValue;
    @Column
    private String resultDescription;
    @Column
    private AnalysisMethod method;
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
        if (map.containsKey("analysis.resultCategory") && map.get("analysis.resultCategory") != null) {
            resultCategory = ResultCategory.valueOf((String) map.get("analysis.resultCategory"));
        }
        if (map.containsKey("analysis.method") && map.get("analysis.method") != null) {
            method = AnalysisMethod.valueOf((String) map.get("analysis.method"));
        }
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

    public ResultCategory getResultCategory() {
        return resultCategory;
    }

    public void setResultCategory(ResultCategory resultCategory) {
        this.resultCategory = resultCategory;
    }

    public String getResultDescription() {
        return resultDescription;
    }

    public void setResultDescription(String resultDescription) {
        this.resultDescription = resultDescription;
    }

    public AnalysisMethod getMethod() {
        return method;
    }

    public void setMethod(AnalysisMethod method) {
        this.method = method;
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
        if (getResultCategory() != null) {
            map.put(prefix + "resultCategory", getResultCategory().name());
        }
        if (getMethod() != null) {
            map.put(prefix + "method", getMethod().name());
        }
        return map;
    }

    public enum ResultCategory {
        TEST
    }

    public enum AnalysisMethod {
        TEST
    }

}
