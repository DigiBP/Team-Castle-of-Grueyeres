package ch.fhnw.digibp.recommendation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ch.fhnw.digibp.validation.Validation;

public class RecommendationAlgorithm {

    private final OutlierRemovalAlgorithm outlierRemovalAlgorithm = new OutlierRemovalAlgorithm();

    private final double value;
    private final Map<String, Recommendation> recommendations = new HashMap<>();

    public RecommendationAlgorithm(double value, Collection<AnalysisEntry> analysisEntries) {
        this.value = value;
        loadMap(analysisEntries);
    }

    public Validation getRecommendation() {
        String bestRecommendation = "";
        double bestDistance = 1000;
        for (Map.Entry<String, Recommendation> entry : recommendations.entrySet()) {
            Recommendation recommendation = entry.getValue();
            recommendation.setValues(outlierRemovalAlgorithm.removeOutliers(recommendation.getValues()));
            for (double currentValue : recommendation.getValues()) {
                double currentDistance = Math.abs(currentValue - value);
                if (currentDistance < bestDistance) {
                    bestDistance = currentDistance;
                    bestRecommendation = recommendation.getRecommendation();
                }
            }
        }
        return buildValidation(bestRecommendation);
    }

    private void loadMap(Collection<AnalysisEntry> analysisEntries) {
        for (AnalysisEntry analysisEntry : analysisEntries) {
            if (!recommendations.containsKey(analysisEntry.getRecommendation())) {
                createEntry(analysisEntry.getRecommendation());
            }
            recommendations.get(analysisEntry.getRecommendation()).addValue(analysisEntry.getResultValue());
        }
    }

    private void createEntry(String recommendation) {
        recommendations.put(recommendation, new Recommendation(recommendation));
    }

    private Validation buildValidation(String recommendation) {
        Validation validation = new Validation();
        validation.setRecommendation(recommendation);
        AnalysisEntry analysisEntry = new AnalysisEntry();
        analysisEntry.setResultValue(value);
        analysisEntry.setRecommendation(recommendation);
        validation.setAnalysisEntry(analysisEntry);
        validation.setSimilarAnalysisCount(recommendations.get(recommendation).getValues().size());
        return validation;
    }


    private class Recommendation {
        private List<Double> values = new ArrayList<>();
        private String recommendation;

        public Recommendation(String recommendation) {
            this.recommendation = recommendation;
        }

        public void addValue(double value) {
            values.add(value);
        }

        public String getRecommendation() {
            return recommendation;
        }

        public void setValues(List<Double> values) {
            this.values = values;
        }

        public List<Double> getValues() {
            return values;
        }
    }
}
