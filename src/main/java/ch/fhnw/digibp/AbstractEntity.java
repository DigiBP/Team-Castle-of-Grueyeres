package ch.fhnw.digibp;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

public abstract class AbstractEntity {

    public abstract Map<String, Object> toMap();

    protected String toString(ZonedDateTime zonedDateTime) {
        return zonedDateTime != null ? zonedDateTime.format(DateTimeFormatter.ISO_OFFSET_DATE_TIME) : null;
    }

    protected String getString(String key, Map<String, Object> map) {
        return mapHasKey(key, map) ? (String) map.get(key) : null;
    }

    protected Long getLong(String key, Map<String, Object> map) {
        return mapHasKey(key, map) ? (Long) map.get(key) : null;
    }

    protected boolean getBoolean(String key, Map<String, Object> map) {
        return mapHasKey(key, map) ? (Boolean) map.get(key) : false;
    }

    protected double getDouble(String key, Map<String, Object> map) {
        return mapHasKey(key, map) ? (double) map.get(key) : -0.0;
    }

    protected ZonedDateTime getZonedDateTime(String key, Map<String, Object> map) {
        if (mapHasKey(key, map)) {
            String str = (String) map.get(key);
            return ZonedDateTime.parse(str, DateTimeFormatter.ISO_OFFSET_DATE_TIME);
        }
        return null;
    }

    protected boolean mapHasKey(String key, Map<String, Object> map) {
        return map.containsKey(key) && map.get(key) != null;
    }
}
