package ch.fhnw.digibp.recommendation;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import ch.fhnw.digibp.analysis.Analysis;

@Entity
@Table(name = "analysis_entry")
public class AnalysisEntry {
    @Id
    @GeneratedValue
    private Long id;
    @Column
    private double resultValue;
    @Column
    private Analysis.ResultCategory resultCategory;
    @Column
    private String recommendation;

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

    public Analysis.ResultCategory getResultCategory() {
        return resultCategory;
    }

    public void setResultCategory(Analysis.ResultCategory resultCategory) {
        this.resultCategory = resultCategory;
    }

    public String getRecommendation() {
        return recommendation;
    }

    public void setRecommendation(String recommendation) {
        this.recommendation = recommendation;
    }
}
