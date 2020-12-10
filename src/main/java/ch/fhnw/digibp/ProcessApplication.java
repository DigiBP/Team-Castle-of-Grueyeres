package ch.fhnw.digibp;

import java.util.Random;

import javax.annotation.PostConstruct;

import ch.fhnw.digibp.client.Client;
import ch.fhnw.digibp.client.ClientRepository;
import ch.fhnw.digibp.domain.AnalysisType;
import ch.fhnw.digibp.recommendation.AnalysisEntry;
import ch.fhnw.digibp.recommendation.AnalysisEntryRepository;
import org.camunda.bpm.spring.boot.starter.annotation.EnableProcessApplication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableProcessApplication()
public class ProcessApplication {

    private static final double DEVIATION = 0.10;
    private final Random random = new Random();

    @Autowired
    private AnalysisEntryRepository analysisEntryRepository;
    @Autowired
    private ClientRepository clientRepository;

    public static void main(String[] args) {
        SpringApplication.run(ProcessApplication.class, args);
    }

    @PostConstruct
    public void postConstruct() {
        createEntries(AnalysisType.Sars_Cov_2, 0, 81, "No quarantine required.");
        createEntries(AnalysisType.Sars_Cov_2, 100, 81, "Low virus level, quarantine for 3 days.");
        createEntries(AnalysisType.Sars_Cov_2, 1000, 81, "Low virus level, quarantine for 5 days.");
        createEntries(AnalysisType.Sars_Cov_2, 10000, 81, "Medium virus level, quarantine for 10 days, person(s) with close contact in quarantine for 2 days.");
        createEntries(AnalysisType.Sars_Cov_2, 100000, 81, "High virus level, quarantine for 10 days, person(s) with close contact in quarantine for 5 days.");
        createEntries(AnalysisType.Sars_Cov_2, 1000000, 81, "Very high virus level, quarantine for 10 days, person(s) with close contact in quarantine for 10 days.");
        createEntry(AnalysisType.Ferritin, 10, "Ferritin level too low, diet change highly recommended.");
        createEntry(AnalysisType.Ferritin, 20, "Ferritin level at the lower end. diet change recommended.");
        createEntry(AnalysisType.Ferritin, 50, "Average ferritin level. No action required.");
        createEntry(AnalysisType.Ferritin, 100, "Average ferritin level. No action required.");
        createEntry(AnalysisType.Ferritin, 600, "Ferritin level above average. Try to eat less meat and other ferritin containing food.");
        createEntry(AnalysisType.Ferritin, 1000, "Ferritin level too high. Please contact your physician.");
        createEntries(AnalysisType.Vitamin_D, 10, 11, "Vitamine D level way too low. Get in touch with your physician.");
        createEntries(AnalysisType.Vitamin_D, 20, 11, "Vitamine D level at the lower limit. Consider the intake of food additives.");
        createEntries(AnalysisType.Vitamin_D, 40, 11, "Vitamien D level in a ideal range. No action required.");
        createEntries(AnalysisType.Vitamin_D, 60, 11, "Vitamien D level in a ideal range. No action required.");
        createEntries(AnalysisType.Vitamin_D, 100, 11, "Vitamine D level too high. Reduce food additives if applicable. Otherwise please contact your physician too discuss your diet.");
        createEntries(AnalysisType.THC, 0, 11, "No THC found.");
        createEntries(AnalysisType.THC, 1, 11, "Patient might have consumed THC in the last few days. A hair analysis should be performed for a more accurate result.");
        createEntries(AnalysisType.THC, 5, 11, "Patient did consume THC on a regular basis. Driver's license should be revoked");
        createEntries(AnalysisType.THC, 10, 11, "THC level very high. Patient should do a drugs rehabilitation");
        createEntries(AnalysisType.HIV, 40, 11, "HIV test negative. Viral load below threshold");
        createEntries(AnalysisType.HIV, 50, 11, "HIV test positive. Viral load at the threshold. Get in touch with an expert immediately.");
        createEntries(AnalysisType.HIV, 1000, 11, "HIV test positive. Viral load above threshold. Get in touch with an expert immediately");
        createBooleanEntries(AnalysisType.Cancer, 0, 10, "Test negative. No cancer cells found. No action required.");
        createBooleanEntries(AnalysisType.Cancer, 1, 10, "Test positive. Get in touch with an expert immediately.");
        createClient("KKH Lörrach", "timo.schoepflin@gmail.com");
        createClient("Uni Spital Basel", "javier_pose88@hotmail.com");
        createClient("Kantonsspital Aarau", "philippe.schwank@students.fhnw.ch");
        createClient("Kantonsspital Basel-Land", "kevin.schaer@students.fhnw.ch");
        createClient("Kantonsspital Solothurn", "pascal.zimmerli@students.fhnw.ch");
        createClient("Spital FHNW Muttenz", "andreas.martin@fhnw.ch");
    }

    private void createEntries(AnalysisType analysisType, double baseValue, int count, String recommendation) {
        double lower = baseValue * (1 - DEVIATION);
        double upper = baseValue * (1 + DEVIATION);
        for (int i = 0; i < count; i++) {
            double randomValue = lower + (upper - lower) * random.nextDouble();
            createEntry(analysisType, randomValue, recommendation);
        }
    }

    private void createBooleanEntries(AnalysisType analysisType, double baseValue, int count, String recommendation) {
        for (int i = 0; i < count; i++) {
            createEntry(analysisType, baseValue, recommendation);
        }
    }

    private void createEntry(AnalysisType analysisType, double value, String recommendation) {
        AnalysisEntry entry = new AnalysisEntry();
        entry.setAnalysisType(analysisType);
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