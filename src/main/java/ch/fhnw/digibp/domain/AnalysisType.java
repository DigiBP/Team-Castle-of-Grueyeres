package ch.fhnw.digibp.domain;

import java.util.Map;

public enum AnalysisType {
    Sars_Cov_2("copies (RNA) per mL"), Ferritin("ng/mL"), Vitamin_D("ng/mL"), HIV("copies (RNA) per mL"), THC("ng/mL"), Cancer("");

    private final String unit;

    AnalysisType(String unit) {
        this.unit = unit;
    }

    public static AnalysisType valueOf(Map<String, Object> map) {
        if (map.containsKey("analysis") && map.get("analysis") != null) {
            return valueOf((String) map.get("analysis"));
        }
        return null;
    }

    public String getUnit() {
        return unit;
    }
}
