package ch.fhnw.digibp.recommendation;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Simple outlier detection based on Chauvenet's criterion
 */
public class OutlierRemovalAlgorithm {

    private static final Map<Integer, Double> chauvenetsCriterionTable = new HashMap<Integer, Double>() {{
        put(3, 1.383);
        put(4, 1.534);
        put(5, 1.645);
        put(6, 1.732);
        put(7, 1.803);
        put(8, 1.863);
        put(9, 1.915);
        put(10, 1.960);
        put(11, 2.000);
        put(12, 2.037);
        put(13, 2.070);
        put(14, 2.100);
        put(15, 2.128);
        put(16, 2.154);
        put(17, 2.178);
        put(18, 2.200);
        put(19, 2.222);
        put(20, 2.241);
        put(21, 2.260);
        put(22, 2.278);
        put(23, 2.295);
        put(24, 2.311);
        put(25, 2.326);
        put(26, 2.341);
        put(27, 2.355);
        put(28, 2.369);
        put(29, 2.382);
        put(30, 2.394);
        put(31, 2.406);
        put(32, 2.418);
        put(33, 2.429);
        put(34, 2.440);
        put(35, 2.450);
        put(36, 2.460);
        put(37, 2.470);
        put(38, 2.479);
        put(39, 2.489);
        put(40, 2.498);
        put(50, 2.576);
        put(100, 2.807);
        put(500, 3.291);
        put(1000, 3.481);
    }};

    public List<Double> removeOutliers(List<Double> values) {
        double mean = calculateMean(values);
        double standardDeviation = calculateStandardDeviation(mean, values);
        double probabilityBand = identifyProbabilityBand(values.size());
        return values.stream().filter(v -> !isOutlier(v, mean, standardDeviation, probabilityBand)).collect(Collectors.toList());
    }

    private double calculateMean(List<Double> values) {
        double sum = values.stream().mapToDouble(v -> v).sum();
        return sum / values.size();
    }

    private double calculateStandardDeviation(double mean, List<Double> values) {
        double standardDeviation = 0.0;

        for (double num : values) {
            standardDeviation += Math.pow(num - mean, 2);
        }
        return Math.sqrt(standardDeviation / values.size());
    }

    private double identifyProbabilityBand(int size) {
        if (3 > size) {
            return chauvenetsCriterionTable.get(3);
        } else if (!chauvenetsCriterionTable.containsKey(size) && size < 45) {
            return chauvenetsCriterionTable.get(40);
        } else if (size >= 45 && size < 75) {
            return chauvenetsCriterionTable.get(50);
        } else if (size >= 75 && size < 250) {
            return chauvenetsCriterionTable.get(100);
        } else if (size >= 250 && size < 750) {
            return chauvenetsCriterionTable.get(500);
        } else if (size > 750) {
            return chauvenetsCriterionTable.get(1000);
        } else {
            return chauvenetsCriterionTable.get(size);
        }
    }

    private boolean isOutlier(double value, double mean, double standardDeviation, double probabilityBand) {
        return probabilityBand < Math.abs((value - mean) / standardDeviation);
    }
}
