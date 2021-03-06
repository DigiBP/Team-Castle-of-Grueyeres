package ch.fhnw.digibp.order;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import ch.fhnw.digibp.AbstractEntity;
import ch.fhnw.digibp.analysis.Analysis;
import ch.fhnw.digibp.client.Client;
import ch.fhnw.digibp.domain.AnalysisType;
import ch.fhnw.digibp.domain.Priority;
import ch.fhnw.digibp.sample.Sample;
import ch.fhnw.digibp.validation.Validation;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "orders")
public class Order extends AbstractEntity {
    @Id
    private String uuid;
    @Column
    private AnalysisType analysisType;
    @Column
    private String comment;
    @Column
    private String laboratory;
    @Column
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate orderDate;
    @Column
    private Priority priority = Priority.LOW;
    @Column
    @Enumerated(EnumType.STRING)
    private State state = State.NEW;
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "sample_id")
    private Sample sample;
    @Embedded
    private BillingInformation billingInformation = new BillingInformation();
    @Embedded
    private SampleRequirements sampleRequirements = new SampleRequirements();
    @Embedded
    private Analysis analysisResult;
    @Embedded
    private Validation validation;
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "fk_client")
    private Client client;


    public Order() {
    }

    public Order(Map<String, Object> map) {
        this.uuid = getString("uuid", map);
        this.comment = getString("comment", map);
        this.orderDate = getLocalDate("orderDate", map);
        this.priority = Priority.valueOf("priority", map);
        this.laboratory = getString("laboratory", map);
        analysisType = AnalysisType.valueOf(map);
        loadState(map);
        loadSample(map);
        loadSampleRequirements(map);
        loadBillingInformation(map);
        loadValidation(map);
        loadAnalysis(map);
        loadClient(map);
    }

    public AnalysisType getAnalysisType() {
        return analysisType;
    }

    public void setAnalysisType(AnalysisType analysisType) {
        this.analysisType = analysisType;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String analysis) {
        this.comment = analysis;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public Sample getSample() {
        return sample;
    }

    public void setSample(Sample sample) {
        this.sample = sample;
    }

    public BillingInformation getBillingInformation() {
        return billingInformation;
    }

    public void setBillingInformation(BillingInformation billingInformation) {
        this.billingInformation = billingInformation;
    }

    public SampleRequirements getSampleRequirements() {
        return sampleRequirements;
    }

    public void setSampleRequirements(SampleRequirements sampleRequirements) {
        this.sampleRequirements = sampleRequirements;
    }

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public LocalDate getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDate orderDate) {
        this.orderDate = orderDate;
    }

    public Analysis getAnalysisResult() {
        return analysisResult;
    }

    public void setAnalysisResult(Analysis analysisResult) {
        this.analysisResult = analysisResult;
    }

    public String getLaboratory() {
        return laboratory;
    }

    public void setLaboratory(String laboratory) {
        this.laboratory = laboratory;
    }

    public Validation getValidation() {
        return validation;
    }

    public void setValidation(Validation validation) {
        this.validation = validation;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public boolean isSampleTypeMismatch() {
        return sample != null && sampleRequirements.getSampleType() != null && !sampleRequirements.getSampleType().equals(sample.getSampleType());
    }

    @Override
    public Map<String, Object> toMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("uuid", getUuid());
        map.put("comment", getComment());
        map.put("orderDate", toString(getOrderDate()));
        map.put("state", getState().name());
        map.put("sampleTypeMismatch", isSampleTypeMismatch());
        map.put("laboratory", getLaboratory());

        if (getSample() != null) {
            map.putAll(getSample().toMap());
        }
        if (getSampleRequirements() != null) {
            map.putAll(getSampleRequirements().toMap());
        }
        if (getBillingInformation() != null) {
            map.putAll(getBillingInformation().toMap());
        }
        if (getValidation() != null) {
            map.putAll(getValidation().toMap());
        }
        if (getPriority() != null) {
            map.put("priority", getPriority().name());
        }
        if (getAnalysisType() != null) {
            map.put("analysis", getAnalysisType().name());
        }
        if (getAnalysisResult() != null) {
            map.putAll(getAnalysisResult().toMap());
        }
        if (getClient() != null) {
            map.putAll(getClient().toMap());
        }
        return map;
    }

    private void loadState(Map<String, Object> map) {
        if (mapHasKey("state", map)) {
            state = State.valueOf((String) map.get("state"));
        }
    }

    private void loadSample(Map<String, Object> map) {
        if (mapHasKey("sample", map)) {
            sample = new Sample((Map<String, Object>) map.get("sample"));
        } else {
            sample = new Sample(map);
        }
    }

    private void loadSampleRequirements(Map<String, Object> map) {
        if (mapHasKey("sampleRequirements", map)) {
            sampleRequirements = new SampleRequirements((Map<String, Object>) map.get("sampleRequirements"));
        } else {
            sampleRequirements = new SampleRequirements(map);
        }
    }

    private void loadBillingInformation(Map<String, Object> map) {
        if (mapHasKey("billingInformation", map)) {
            billingInformation = new BillingInformation((Map<String, Object>) map.get("billingInformation"));
        } else {
            billingInformation = new BillingInformation(map);
        }
    }

    private void loadValidation(Map<String, Object> map) {
        if (mapHasKey("validation", map)) {
            validation = new Validation((Map<String, Object>) map.get("validation"));
        } else {
            validation = new Validation(map);
        }
    }

    private void loadAnalysis(Map<String, Object> map) {
        if (mapHasKey("analysisResult", map)) {
            analysisResult = new Analysis((Map<String, Object>) map.get("analysisType"));
        } else {
            analysisResult = new Analysis(map);
        }
    }

    private void loadClient(Map<String, Object> map) {
        if (mapHasKey(Client.PREFIX, map)) {
            client = new Client((Map<String, Object>) map.get(Client.PREFIX));
        } else {
            client = new Client(map);
        }
    }

    @Override
    public String toString() {
        return "Order{" +
                "uuid='" + uuid + '\'' +
                ", analysis='" + analysisType + '\'' +
                ", comment='" + comment + '\'' +
                ", orderDate=" + orderDate +
                ", priority=" + priority +
                ", state=" + state +
                ", sample=" + sample +
                ", billingInformation=" + billingInformation +
                ", sampleRequirements=" + sampleRequirements +
                ", validation=" + validation +
                ", client=" + client +
                '}';
    }

    public enum State {
        NEW, CANCELLED, CONFIRMED, SAMPLE_RECEIVED, IN_ANALYSIS, ANALYSIS_DONE, ANALYSIS_REVIEWED, DONE
    }
}
