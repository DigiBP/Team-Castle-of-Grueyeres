package ch.fhnw.digibp.recommendation;

import java.util.List;

import ch.fhnw.digibp.analysis.Analysis;
import ch.fhnw.digibp.validation.Validation;
import org.springframework.stereotype.Component;

@Component
public class RecommendationEngine {
    private final AnalysisEntryRepository analysisEntryRepository;

    public RecommendationEngine(AnalysisEntryRepository analysisEntryRepository) {
        this.analysisEntryRepository = analysisEntryRepository;
    }

    public Validation recommend(Analysis analysis) {
        long count = analysisEntryRepository.countEntries(analysis.getResultValue(), analysis.getResultCategory());
        RecommendationEntry highestMatch = findHighestMatch(analysis);
        analysisEntryRepository.save(buildAnalysisEntry(analysis, highestMatch.getRecommendation()));
        double probability = calculateProbability(highestMatch.getCount(), count);
        return buildValidation(highestMatch.getRecommendation(), probability);
    }

    private RecommendationEntry findHighestMatch(Analysis analysis) {
        List<RecommendationEntry> recommendationEntries = analysisEntryRepository.findRecommendation(analysis.getResultValue(), analysis.getResultCategory());
        RecommendationEntry highestMatch = null;
        for (RecommendationEntry entry : recommendationEntries) {
            if (highestMatch == null || (highestMatch.getCount() < entry.getCount())) {
                highestMatch = entry;
            }
        }
        return highestMatch;
    }

    private AnalysisEntry buildAnalysisEntry(Analysis analysis, String recommendation) {
        AnalysisEntry analysisEntry = new AnalysisEntry();
        analysisEntry.setResultValue(analysis.getResultValue());
        analysisEntry.setResultCategory(analysis.getResultCategory());
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
