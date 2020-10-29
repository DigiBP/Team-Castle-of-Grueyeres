package ch.fhnw.digibp.domain;

import java.util.Map;

public enum PackageType {
    LETTER;

    public static PackageType valueOf(Map<String, Object> map) {
        if (map.containsKey("packageType") && map.get("packageType") != null) {
            return valueOf((String) map.get("packageType"));
        }
        return null;
    }
}
