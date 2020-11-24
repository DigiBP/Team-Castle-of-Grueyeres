package ch.fhnw.digibp;

import javax.annotation.PostConstruct;

import ch.fhnw.digibp.analysis.Analysis;
import ch.fhnw.digibp.recommendation.AnalysisEntry;
import ch.fhnw.digibp.recommendation.AnalysisEntryRepository;
import org.camunda.bpm.spring.boot.starter.annotation.EnableProcessApplication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableProcessApplication()
public class ProcessApplication {

    @Autowired
    private AnalysisEntryRepository analysisEntryRepository;

    public static void main(String[] args) {
        SpringApplication.run(ProcessApplication.class, args);
    }

    @PostConstruct
    public void postConstruct() {
        createEntry(Analysis.ResultCategory.TEST, 20, "Some Recommendation");
        createEntry(Analysis.ResultCategory.TEST, 20, "Some Recommendation");
        createEntry(Analysis.ResultCategory.TEST, 20, "Some Recommendation");
        createEntry(Analysis.ResultCategory.TEST, 20, "Some Recommendation");
        createEntry(Analysis.ResultCategory.TEST, 20, "Some Recommendation");
        createEntry(Analysis.ResultCategory.TEST, 20, "Some Recommendation");
        createEntry(Analysis.ResultCategory.TEST, 20, "Some Recommendation");
        createEntry(Analysis.ResultCategory.TEST, 20, "Some Rare Recommendation");
        createEntry(Analysis.ResultCategory.TEST, 20, "Some Rare Recommendation");
        createEntry(Analysis.ResultCategory.TEST, 50, "Some Other Recommendation");
        createEntry(Analysis.ResultCategory.TEST, 50, "Some Other Recommendation");
        createEntry(Analysis.ResultCategory.TEST, 50, "Some Other Recommendation");
    }

    private void createEntry(Analysis.ResultCategory category, double value, String recommendation) {
        AnalysisEntry entry = new AnalysisEntry();
        entry.setResultCategory(category);
        entry.setResultValue(value);
        entry.setRecommendation(recommendation);
        analysisEntryRepository.save(entry);
    }

}