package ch.fhnw.digibp.order;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import ch.fhnw.digibp.AbstractEntity;
import ch.fhnw.digibp.sample.Sample;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "orders")
public class Order extends AbstractEntity {
    public enum State {
        NEW, CANCELLED, CONFIRMED, SAMPLE_RECEIVED, ANALYSIS, DONE
    }

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
    @Enumerated(EnumType.STRING)
    private State state;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "sample_id")
    private Sample sample = new Sample();

    public Order() {
    }

    public Order(Map<String, Object> map) {
        this.uuid = getString("uuid", map);
        this.clientId = getString("clientId", map);
        this.analysis = getString("analysis", map);
        this.comment = getString("comment", map);
        this.dueDate = getLocalDate("dueDate", map);
        loadState(map);
        loadSample(map);
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

    @Override
    public Map<String, Object> toMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("uuid", getUuid());
        map.put("clientId", getClientId());
        map.put("analysis", getAnalysis());
        map.put("comment", getComment());
        map.put("dueDate", toString(getDueDate()));
        map.put("state", getState().name());
        if (getSample() != null) {
            map.put("sample", getSample().toMap());
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
        }
    }

    @Override
    public String toString() {
        return "Order{" +
                "uuid='" + uuid + '\'' +
                ", clientId='" + clientId + '\'' +
                ", comment='" + comment + '\'' +
                ", dueDate=" + dueDate +
                ", state=" + state +
                '}';
    }
}
