package ch.fhnw.digibp.recommendation;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class OutlierRemovalAlgorithmTest {

    private final OutlierRemovalAlgorithm testee = new OutlierRemovalAlgorithm();

    @Test
    public void testOutlierRemovalOfHigherNumber() {
        testOutliers(false, 50.0);
    }

    @Test
    public void testOutlierRemovalOfLowerNumber() {
        testOutliers(false, 2.0);
    }

    @Test
    public void testInclusionOfCorrectValues() {
        testOutliers(true, 11.5);
    }

    private void testOutliers(boolean removed, double value) {
        List<Double> actual = testee.removeOutliers(buildList(value));
        assertEquals(removed, actual.contains(value));
    }

    private List<Double> buildList(double outlier) {
        return Arrays.asList(9.0, 10.0, 10.0, 10.0, 11.0, outlier);
    }

}