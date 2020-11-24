package ch.fhnw.digibp;

import javax.annotation.PostConstruct;

import ch.fhnw.digibp.analysis.Analysis;
import ch.fhnw.digibp.client.Client;
import ch.fhnw.digibp.client.ClientRepository;
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
    @Autowired
    private ClientRepository clientRepository;

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
        createClient("KKH Lörrach", "timo.schoepflin@gmail.com");
        createClient("Uni Spital Basel", "javier_pose88@hotmail.com");
    }

    private void createEntry(Analysis.ResultCategory category, double value, String recommendation) {
        AnalysisEntry entry = new AnalysisEntry();
        entry.setResultCategory(category);
        entry.setResultValue(value);
        entry.setRecommendation(recommendation);
        analysisEntryRepository.save(entry);
    }

    private void createClient(String name, String email) {
        Client client = new Client();
        client.setName(name);
        client.setEmail(email);
        clientRepository.save(client);
    }

}