package ch.fhnw.digibp;

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

    @Autowired
    private AnalysisEntryRepository analysisEntryRepository;
    @Autowired
    private ClientRepository clientRepository;

    public static void main(String[] args) {
        SpringApplication.run(ProcessApplication.class, args);
    }

    @PostConstruct
    public void postConstruct() {
        createEntry(AnalysisType.Sars_Cov_2, 0, "No quarantine required.");
        createEntry(AnalysisType.Sars_Cov_2, 100, "Low virus level, quarantine for 3 days.");
        createEntry(AnalysisType.Sars_Cov_2, 1000, "Low virus level, quarantine for 5 days.");
        createEntry(AnalysisType.Sars_Cov_2, 10000, "Medium virus level, quarantine for 10 days, person(s) with close contact in quarantine for 2 days.");
        createEntry(AnalysisType.Sars_Cov_2, 100000, "High virus level, quarantine for 10 days, person(s) with close contact in quarantine for 5 days.");
        createEntry(AnalysisType.Sars_Cov_2, 1000000, "Very high virus level, quarantine for 10 days, person(s) with close contact in quarantine for 10 days.");
        createEntry(AnalysisType.Ferritin, 10, "Ferritin level too low, diet change highly recommended.");
        createEntry(AnalysisType.Ferritin, 20, "Ferritin level at the lower end. diet change recommended.");
        createEntry(AnalysisType.Ferritin, 50, "Average ferritin level. No action required.");
        createEntry(AnalysisType.Ferritin, 100, "Average ferritin level. No action required.");
        createEntry(AnalysisType.Ferritin, 600, "Ferritin level above average. Try to eat less meat and other ferritin containing food.");
        createEntry(AnalysisType.Ferritin, 1000, "Ferritin level too high. Please contact your physician.");
        createEntry(AnalysisType.Vitamin_D, 10, "Vitamine D level way too low. Get in touch with your physician.");
        createEntry(AnalysisType.Vitamin_D, 20, "Vitamine D level at the lower limit. Consider the intake of food additives.");
        createEntry(AnalysisType.Vitamin_D, 40, "Vitamien D level in a ideal range. No action required.");
        createEntry(AnalysisType.Vitamin_D, 60, "Vitamien D level in a ideal range. No action required.");
        createEntry(AnalysisType.Vitamin_D, 100, "Vitamine D level too high. Reduce food additives if applicable. Otherwise please contact your physician too discuss your diet.");
        createEntry(AnalysisType.THC, 0, "No THC found.");
        createEntry(AnalysisType.THC, 1, "Patient might have consumed THC in the last few days. A hair analysis should be performed for a more accurate result.");
        createEntry(AnalysisType.THC, 5, "Patient did consume THC on a regular basis. Driver's license should be revoked");
        createEntry(AnalysisType.THC, 10, "THC level very high. Patient should do a drugs rehabilitation");
        createEntry(AnalysisType.HIV, 40, "HIV test negative. Viral load below threshold");
        createEntry(AnalysisType.HIV, 50, "HIV test positive. Viral load at the threshold. Get in touch with an expert immediately.");
        createEntry(AnalysisType.HIV, 1000, "HIV test positive. Viral load above threshold. Get in touch with an expert immediately");
        createEntry(AnalysisType.Cancer, 0, "Test negative. No cancer cells found. No action required.");
        createEntry(AnalysisType.Cancer, 1, "Test positive. Get in touch with an expert immediately.");
        createClient("KKH Lörrach", "timo.schoepflin@gmail.com");
        createClient("Uni Spital Basel", "javier_pose88@hotmail.com");
        createClient("Kantonsspital Aarau", "philippe.schwank@students.fhnw.ch");
        createClient("Kantonsspital Basel-Land", "kevin.schaer@students.fhnw.ch");
        createClient("Kantonsspital Solothurn", "pascal.zimmerli@students.fhnw.ch");
        createClient("Spital FHNW Muttenz", "andreas.martin@fhnw.ch");
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