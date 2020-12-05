package ch.fhnw.digibp.recommendation;

import java.util.ArrayList;
import java.util.List;

import ch.fhnw.digibp.validation.Validation;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RecommendationAlgorithmTest {


    @Test
    public void testRecommendationAlgorithmNumbers() {
        final RecommendationAlgorithm testee = new RecommendationAlgorithm(33, buildNumberAnalysisEntries());
        Validation actualRecommendation = testee.getRecommendation();
        assertEquals("Go to Doctor", actualRecommendation.getRecommendation());
    }

    @Test
    public void testRecommendationAlgorithmBinary() {
        final RecommendationAlgorithm testee = new RecommendationAlgorithm(1, buildBinaryAnalysisEntries());
        Validation actualRecommendation = testee.getRecommendation();
        assertEquals("Go to Doctor", actualRecommendation.getRecommendation());
    }

    private List<AnalysisEntry> buildNumberAnalysisEntries() {
        List<AnalysisEntry> analysisEntries = new ArrayList<>();
        addEntry(analysisEntries, 29, "Go to Doctor");
        addEntry(analysisEntries, 33, "Go to Doctor");
        addEntry(analysisEntries, 30, "Go to Doctor");
        addEntry(analysisEntries, 10, "All good");
        addEntry(analysisEntries, 8, "All good");
        addEntry(analysisEntries, 5, "All good");
        addEntry(analysisEntries, 1, "All good");
        return analysisEntries;
    }

    private List<AnalysisEntry> buildBinaryAnalysisEntries() {
        List<AnalysisEntry> analysisEntries = new ArrayList<>();
        addEntry(analysisEntries, 1, "Go to Doctor");
        addEntry(analysisEntries, 1, "Go to Doctor");
        addEntry(analysisEntries, 1, "Go to Doctor");
        addEntry(analysisEntries, 0, "All good");
        addEntry(analysisEntries, 0, "All good");
        addEntry(analysisEntries, 0, "All good");
        addEntry(analysisEntries, 0, "All good");
        return analysisEntries;
    }

    private void addEntry(List<AnalysisEntry> analysisEntries, double value, String recommendation) {
        AnalysisEntry analysisEntry = new AnalysisEntry();
        analysisEntry.setRecommendation(recommendation);
        analysisEntry.setResultValue(value);
        analysisEntries.add(analysisEntry);
    }
}