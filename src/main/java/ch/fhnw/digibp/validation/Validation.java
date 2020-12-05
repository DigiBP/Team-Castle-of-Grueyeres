package ch.fhnw.digibp.validation;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import ch.fhnw.digibp.AbstractEntity;
import ch.fhnw.digibp.recommendation.AnalysisEntry;

@Embeddable
public class Validation extends AbstractEntity {
    @Column
    private int similarAnalysisCount;
    @Column
    private String recommendation;
    @Column
    private boolean verificationNeeded;
    @Column
    private boolean approved;
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "fk_analysis_entry")
    private AnalysisEntry analysisEntry;


    public Validation() {

    }

    public Validation(Map<String, Object> map) {
        this.similarAnalysisCount = getInt("validation.similarAnalysisCount", map);
        this.recommendation = getString("validation.recommendation", map);
        this.verificationNeeded = getBoolean("validation.verificationNeeded", map);
        this.approved = getBoolean("validation.approved", map);
        loadAnalysisEntry(map);
    }

    private void loadAnalysisEntry(Map<String, Object> map) {
        if (map.containsKey("analysisEntry")) {
            analysisEntry = new AnalysisEntry((Map<String, Object>) map.get("analysisEntry"));
        } else {
            analysisEntry = new AnalysisEntry(map);
        }
        if (analysisEntry.getId() == null) {
            analysisEntry = null;
        }
    }

    public int getSimilarAnalysisCount() {
        return similarAnalysisCount;
    }

    public void setSimilarAnalysisCount(int similarAnalysisCount) {
        this.similarAnalysisCount = similarAnalysisCount;
    }

    public boolean isVerificationNeeded() {
        return verificationNeeded;
    }

    public void setVerificationNeeded(boolean verificationNeeded) {
        this.verificationNeeded = verificationNeeded;
    }

    public boolean isApproved() {
        return approved;
    }

    public void setApproved(boolean verified) {
        this.approved = verified;
    }

    public String getRecommendation() {
        return recommendation;
    }

    public void setRecommendation(String recommendation) {
        this.recommendation = recommendation;
    }

    public AnalysisEntry getAnalysisEntry() {
        return analysisEntry;
    }

    public void setAnalysisEntry(AnalysisEntry analysisEntry) {
        this.analysisEntry = analysisEntry;
    }

    @Override
    public Map<String, Object> toMap() {
        return toMapWithPrefix("validation.");
    }

    private Map<String, Object> toMapWithPrefix(String prefix) {
        Map<String, Object> map = new HashMap<>();
        map.put(prefix + "similarAnalysisCount", getSimilarAnalysisCount());
        map.put(prefix + "recommendation", getRecommendation());
        map.put(prefix + "verificationNeeded", isVerificationNeeded());
        map.put(prefix + "approved", isApproved());
        if (analysisEntry != null) {
            map.putAll(analysisEntry.toMap());
        }
        return map;
    }

    @Override
    public String toString() {
        return "Validation{" +
                "similarAnalysisCount=" + similarAnalysisCount +
                ", recommendation='" + recommendation + '\'' +
                ", verificationNeeded=" + verificationNeeded +
                ", approved=" + approved +
                '}';
    }
}
