package ch.fhnw.digibp.recommendation;

public class RecommendationEntry {
    private Long count;
    private String recommendation;

    public RecommendationEntry(Long count, String recommendation) {
        this.count = count;
        this.recommendation = recommendation;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public String getRecommendation() {
        return recommendation;
    }

    public void setRecommendation(String recommendation) {
        this.recommendation = recommendation;
    }
}
