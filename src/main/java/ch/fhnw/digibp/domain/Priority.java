package ch.fhnw.digibp.domain;

import java.util.Map;

public enum Priority {
    HIGH, MEDIUM, LOW;

    public static Priority valueOf(String key, Map<String, Object> map) {
        if (map.containsKey(key) && map.get(key) != null) {
            return valueOf((String) map.get(key));
        }
        return null;
    }
}
