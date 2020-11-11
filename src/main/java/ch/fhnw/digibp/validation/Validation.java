package ch.fhnw.digibp.validation;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import ch.fhnw.digibp.AbstractEntity;

@Embeddable
public class Validation extends AbstractEntity {
    @Column
    private double probability;
    @Column
    private String mlRecommendation;
    @Column
    private String physicanRecommendation;
    @Column
    private boolean verificationNeeded;
    @Column
    private boolean approved;

    public Validation() {

    }

    public Validation(Map<String, Object> map) {
        this.probability = getDouble("validation.probability", map);
        this.mlRecommendation = getString("validation.mlRecommendation", map);
        this.physicanRecommendation = getString("validation.physicanRecommendation", map);
        this.verificationNeeded = getBoolean("validation.verificationNeeded", map);
        this.approved = getBoolean("validation.approved", map);
    }

    public double getProbability() {
        return probability;
    }

    public void setProbability(double probability) {
        this.probability = probability;
    }

    public String getMlRecommendation() {
        return mlRecommendation;
    }

    public void setMlRecommendation(String mlRecommendation) {
        this.mlRecommendation = mlRecommendation;
    }

    public String getPhysicanRecommendation() {
        return physicanRecommendation;
    }

    public void setPhysicanRecommendation(String physicanRecommendation) {
        this.physicanRecommendation = physicanRecommendation;
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

    @Override
    public Map<String, Object> toMap() {
        return toMapWithPrefix("validation.");
    }

    private Map<String, Object> toMapWithPrefix(String prefix) {
        Map<String, Object> map = new HashMap<>();
        map.put(prefix + "probability", getProbability());
        map.put(prefix + "mlRecommendation", getMlRecommendation());
        map.put(prefix + "physicanRecommendation", getPhysicanRecommendation());
        map.put(prefix + "verificationNeeded", isVerificationNeeded());
        map.put(prefix + "approved", isApproved());
        return map;
    }

    @Override
    public String toString() {
        return "Validation{" +
                "probability=" + probability +
                ", mlRecommendation='" + mlRecommendation + '\'' +
                ", physicanRecommendation='" + physicanRecommendation + '\'' +
                ", verificationNeeded=" + verificationNeeded +
                ", approved=" + approved +
                '}';
    }
}
