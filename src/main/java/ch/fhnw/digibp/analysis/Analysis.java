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
    private String resultDescription = "BLA BLA BLA";
    @Column
    private String methodDescription;
    @Column
    private String remarks;

    public Analysis() {
    }

    public Analysis(Map<String, Object> map) {
        startDate = getLocalDate("analysis.startDate", map);
        endDate = getLocalDate("analysis.endDate", map);
        resultDescription = getString("analysis.resultDescription", map);
        methodDescription = getString("analysis.methodDescription", map);
        remarks = getString("analysis.remarks", map);
        if (map.containsKey("analysis.resultCategory") && map.get("analysis.resultCategory") != null) {
            resultCategory = ResultCategory.valueOf((String) map.get("analysis.resultCategory"));
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

    public String getMethodDescription() {
        return methodDescription;
    }

    public void setMethodDescription(String methodDescription) {
        this.methodDescription = methodDescription;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
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
        map.put(prefix + "methodDescription", getMethodDescription());
        map.put(prefix + "remarks", getRemarks());
        if (getResultCategory() != null) {
            map.put(prefix + "resultCategory", getResultCategory().name());
        }
        return map;
    }

    public enum ResultCategory {
        TEST
    }


}
