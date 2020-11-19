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
    private String recommendation;
    @Column
    private boolean verificationNeeded;
    @Column
    private boolean approved;

    public Validation() {

    }

    public Validation(Map<String, Object> map) {
        this.probability = getDouble("validation.probability", map);
        this.recommendation = getString("validation.recommendation", map);
        this.verificationNeeded = getBoolean("validation.verificationNeeded", map);
        this.approved = getBoolean("validation.approved", map);
    }

    public double getProbability() {
        return probability;
    }

    public void setProbability(double probability) {
        this.probability = probability;
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

    @Override
    public Map<String, Object> toMap() {
        return toMapWithPrefix("validation.");
    }

    private Map<String, Object> toMapWithPrefix(String prefix) {
        Map<String, Object> map = new HashMap<>();
        map.put(prefix + "probability", getProbability());
        map.put(prefix + "recommendation", getRecommendation());
        map.put(prefix + "verificationNeeded", isVerificationNeeded());
        map.put(prefix + "approved", isApproved());
        return map;
    }

    @Override
    public String toString() {
        return "Validation{" +
                "probability=" + probability +
                ", recommendation='" + recommendation + '\'' +
                ", verificationNeeded=" + verificationNeeded +
                ", approved=" + approved +
                '}';
    }
}
