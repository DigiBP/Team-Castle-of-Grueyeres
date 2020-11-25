package ch.fhnw.digibp.recommendation;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import ch.fhnw.digibp.order.Order;

@Entity
@Table(name = "analysis_entry")
public class AnalysisEntry {
    @Id
    @GeneratedValue
    private Long id;
    @Column
    private double resultValue;
    @Column
    private Order.AnalysisType analysisType;
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

    public Order.AnalysisType getAnalysisType() {
        return analysisType;
    }

    public void setAnalysisType(Order.AnalysisType analysisType) {
        this.analysisType = analysisType;
    }

    public String getRecommendation() {
        return recommendation;
    }

    public void setRecommendation(String recommendation) {
        this.recommendation = recommendation;
    }
}
