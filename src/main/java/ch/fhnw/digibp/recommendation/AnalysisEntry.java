package ch.fhnw.digibp.recommendation;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import ch.fhnw.digibp.AbstractEntity;
import ch.fhnw.digibp.domain.AnalysisType;

@Entity
@Table(name = "analysis_entry")
public class AnalysisEntry extends AbstractEntity {
    public static final String PREFIX = "analysisEntry";
    @Id
    @GeneratedValue
    private Long id;
    @Column
    private double resultValue;
    @Column
    private AnalysisType analysisType;
    @Column
    private String recommendation;

    public AnalysisEntry() {
    }

    public AnalysisEntry(Map<String, Object> map) {
        id = getLong(PREFIX + "." + "id", map);
        resultValue = getDouble(PREFIX + "." + "resultValue", map);
        recommendation = getString(PREFIX + "." + "recommendation", map);
        analysisType = AnalysisType.valueOf(map);
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getResultValue() {
        return resultValue;
    }

    public void setResultValue(double resultValue) {
        this.resultValue = resultValue;
    }

    public AnalysisType getAnalysisType() {
        return analysisType;
    }

    public void setAnalysisType(AnalysisType analysisType) {
        this.analysisType = analysisType;
    }

    public String getRecommendation() {
        return recommendation;
    }

    public void setRecommendation(String recommendation) {
        this.recommendation = recommendation;
    }

    @Override
    public Map<String, Object> toMap() {
        Map<String, Object> map = new HashMap<>();
        map.put(PREFIX + "." + "id", id);
        map.put(PREFIX + "." + "resultValue", resultValue);
        if (analysisType != null) {
            map.put(PREFIX + "." + "analysisType", analysisType.name());
        }
        map.put(PREFIX + "." + "recommendation", recommendation);
        return map;
    }
}
