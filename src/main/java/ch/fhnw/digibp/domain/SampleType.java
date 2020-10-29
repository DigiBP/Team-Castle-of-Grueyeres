package ch.fhnw.digibp.domain;

import java.util.Map;

public enum SampleType {
    BLOOD, TISSUE, URINE;

    public static SampleType valueOf(Map<String, Object> map) {
        if (map.containsKey("sampleType") && map.get("sampleType") != null) {
            return valueOf((String) map.get("sampleType"));
        }
        return null;
    }
}
