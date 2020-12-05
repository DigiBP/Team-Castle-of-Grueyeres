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
        List<AnalysisEntry> analysisEntries = analysisEntryRepository.findPreviousRecommendation(order.getAnalysisType());
        RecommendationAlgorithm recommendationAlgorithm = new RecommendationAlgorithm(order.getAnalysisResult().getResultValue(), analysisEntries);
        Validation validation = recommendationAlgorithm.getRecommendation();
        analysisEntryRepository.save(validation.getAnalysisEntry());
        return validation;
    }

}
