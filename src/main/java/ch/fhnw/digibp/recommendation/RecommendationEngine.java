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
            AnalysisEntry analysisEntry = createAnalysisEntry(order, highestMatch.getRecommendation());
            double probability = calculateProbability(highestMatch.getCount(), count);
            return buildValidation(highestMatch.getRecommendation(), probability, analysisEntry);
        }
        return buildValidation("", 0, createAnalysisEntry(order, ""));
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

    private AnalysisEntry createAnalysisEntry(Order order, String recommendation) {
        AnalysisEntry analysisEntry = new AnalysisEntry();
        if (order.getValidation() != null && order.getValidation().getAnalysisEntry() != null) {
            analysisEntry.setId(order.getValidation().getAnalysisEntry().getId());
        }
        analysisEntry.setResultValue(order.getAnalysisResult().getResultValue());
        analysisEntry.setAnalysisType(order.getAnalysisType());
        analysisEntry.setRecommendation(recommendation);
        return analysisEntryRepository.save(analysisEntry);
    }

    private double calculateProbability(long matchCount, long totalCount) {
        double probability = ((double) matchCount / (double) totalCount) * 100;
        return Math.round(probability * 100) / 100;
    }

    private Validation buildValidation(String recommendation, double probability, AnalysisEntry analysisEntry) {
        Validation validation = new Validation();
        validation.setRecommendation(recommendation);
        validation.setProbability(probability);
        validation.setAnalysisEntry(analysisEntry);
        return validation;
    }
}
