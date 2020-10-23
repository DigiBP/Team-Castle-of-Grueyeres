package ch.fhnw.digibp.order;

import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.Map;

import ch.fhnw.digibp.AbstractEntity;

public class Order extends AbstractEntity {
    public enum State {
        NEW, CANCELLED, CONFIRMED, ANALYSIS, DONE
    }

    private String uuid;
    private String clientId;
    private String analysis;
    private ZonedDateTime dueDate;
    private State state;

    public Order() {
    }

    public Order(Map<String, Object> map) {
        this.uuid = getString("uuid", map);
        this.clientId = getString("clientId", map);
        this.analysis = getString("analysis", map);
        this.dueDate = getZonedDateTime("dueDate", map);
        this.state = getState(map);
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

    public ZonedDateTime getDueDate() {
        return dueDate;
    }

    public void setDueDate(ZonedDateTime dueDate) {
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

    @Override
    public Map<String, Object> toMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("uuid", getUuid());
        map.put("clientId", getClientId());
        map.put("analysis", getAnalysis());
        map.put("dueDate", toString(getDueDate()));
        map.put("state", getState().name());
        return map;
    }

    private State getState(Map<String, Object> map) {
        return mapHasKey("state", map) ? State.valueOf((String) map.get("state")) : null;
    }

    @Override
    public String toString() {
        return "Order{" +
                "uuid='" + uuid + '\'' +
                ", clientId='" + clientId + '\'' +
                ", analysis='" + analysis + '\'' +
                ", dueDate=" + dueDate +
                ", state=" + state +
                '}';
    }
}
