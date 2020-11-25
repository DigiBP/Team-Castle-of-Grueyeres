package ch.fhnw.digibp.domain;

import java.util.Map;

public enum AnalysisType {
    Sars_Cov_2, Ferritin, Vitamin_D, HIV, THC, Cancer;

    public static AnalysisType valueOf(Map<String, Object> map) {
        if (map.containsKey("analysis") && map.get("analysis") != null) {
            return valueOf((String) map.get("analysis"));
        }
        return null;
    }
}
