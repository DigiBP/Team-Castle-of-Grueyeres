package ch.fhnw.digibp.domain;

import java.util.Map;

public enum SampleType {
    BLOOD, TISSUE, URINE, SALIVA;

    public static SampleType valueOf(String key, Map<String, Object> map) {
        if (map.containsKey(key) && map.get(key) != null) {
            return valueOf((String) map.get(key));
        }
        return null;
    }
}
