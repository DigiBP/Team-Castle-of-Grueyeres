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
import javax.persistence.OneToOne;
import javax.persistence.Table;

import ch.fhnw.digibp.AbstractEntity;
import ch.fhnw.digibp.analysis.Analysis;
import ch.fhnw.digibp.domain.Priority;
import ch.fhnw.digibp.sample.Sample;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "orders")
public class Order extends AbstractEntity {
    @Id
    private String uuid;
    @Column
    private String clientId;
    @Column
    private String analysis;
    @Column
    private String comment;
    @Column
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dueDate;
    @Column
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate orderDate;
    @Column
    private Priority priority = Priority.LOW;
    @Column
    @Enumerated(EnumType.STRING)
    private State state;
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "sample_id")
    private Sample sample;
    @Embedded
    private BillingInformation billingInformation = new BillingInformation();
    @Embedded
    private SampleRequirements sampleRequirements = new SampleRequirements();
    @Embedded
    private Analysis analysisResult;

    public Order() {
    }

    public Order(Map<String, Object> map) {
        this.uuid = getString("uuid", map);
        this.clientId = getString("clientId", map);
        this.analysis = getString("analysis", map);
        this.comment = getString("comment", map);
        this.dueDate = getLocalDate("dueDate", map);
        this.priority = Priority.valueOf("priority", map);
        loadState(map);
        loadSample(map);
        loadSampleRequirements(map);
        loadBillingInformation(map);
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getAnalysis() {
        return analysis;
    }

    public void setAnalysis(String analysis) {
        this.analysis = analysis;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String analysis) {
        this.comment = analysis;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
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

    public boolean isSampleTypeMismatch() {
        return sample != null && sampleRequirements.getSampleType() != null && !sampleRequirements.getSampleType().equals(sample.getSampleType());
    }

    public boolean isTemperatureOk() {
        return sample != null && sampleRequirements.getTemperature() == sample.getTemperature();
    }

    @Override
    public Map<String, Object> toMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("uuid", getUuid());
        map.put("clientId", getClientId());
        map.put("analysis", getAnalysis());
        map.put("comment", getComment());
        map.put("dueDate", toString(getDueDate()));
        map.put("state", getState().name());
        map.put("sampleTypeMismatch", isSampleTypeMismatch());
        map.put("temperatureOk", isTemperatureOk());

        if (getSample() != null) {
            map.putAll(getSample().toMapWithPrefix());
        }
        if (getSampleRequirements() != null) {
            map.putAll(getSampleRequirements().toMapWithPrefix());
        }
        if (getBillingInformation() != null) {
            map.putAll(getBillingInformation().toMapWithPrefix());
        }
        if (getPriority() != null) {
            map.put("priority", getPriority().name());
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
            sampleRequirements = new SampleRequirements(map);
        }
    }

    @Override
    public String toString() {
        return "Order{" +
                "uuid='" + uuid + '\'' +
                ", clientId='" + clientId + '\'' +
                ", analysis='" + analysis + '\'' +
                ", comment='" + comment + '\'' +
                ", dueDate=" + dueDate +
                ", priority=" + priority +
                ", state=" + state +
                ", sample=" + sample +
                ", billingInformation=" + billingInformation +
                ", sampleRequirements=" + sampleRequirements +
                '}';
    }

    public enum State {
        NEW, CANCELLED, CONFIRMED, SAMPLE_RECEIVED, IN_ANALYSIS, ANALYSIS_DONE, DONE
    }
}
