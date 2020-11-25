package ch.fhnw.digibp.recommendation;

import java.util.List;

import ch.fhnw.digibp.order.Order;
import ch.fhnw.digibp.validation.Validation;
import org.springframework.stereotype.Component;

@Component
public class RecommendationEngine {
    private final AnalysisEntryRepository analysisEntryRepository;

    public RecommendationEngine(AnalysisEntryRepository analysisEntryRepository) {
        this.analysisEntryRepository = analysisEntryRepository;
    }

    public Validation recommend(Order order) {
        long count = analysisEntryRepository.countEntries(order.getAnalysisResult().getResultValue(), order.getAnalysisType());
        RecommendationEntry highestMatch = findHighestMatch(order);
        if (highestMatch != null) {
            analysisEntryRepository.save(buildAnalysisEntry(order, highestMatch.getRecommendation()));
            double probability = calculateProbability(highestMatch.getCount(), count);
            return buildValidation(highestMatch.getRecommendation(), probability);
        }
        return buildValidation("", 0);
    }

    private RecommendationEntry findHighestMatch(Order order) {
        List<RecommendationEntry> recommendationEntries = analysisEntryRepository.findRecommendation(order.getAnalysisResult().getResultValue(), order.getAnalysisType());
        RecommendationEntry highestMatch = null;
        for (RecommendationEntry entry : recommendationEntries) {
            if (highestMatch == null || (highestMatch.getCount() < entry.getCount())) {
                highestMatch = entry;
            }
        }
        return highestMatch;
    }

    private AnalysisEntry buildAnalysisEntry(Order order, String recommendation) {
        AnalysisEntry analysisEntry = new AnalysisEntry();
        analysisEntry.setResultValue(order.getAnalysisResult().getResultValue());
        analysisEntry.setAnalysisType(order.getAnalysisType());
        analysisEntry.setRecommendation(recommendation);
        return analysisEntry;
    }

    private double calculateProbability(long matchCount, long totalCount) {
        double probability = ((double) matchCount / (double) totalCount) * 100;
        return Math.round(probability * 100) / 100;
    }

    private Validation buildValidation(String recommendation, double probability) {
        Validation validation = new Validation();
        validation.setRecommendation(recommendation);
        validation.setProbability(probability);
        return validation;
    }
}
