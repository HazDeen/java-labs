package edu.course.lab03;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class NormalizationStrategyTest {
    @Test
    void minMaxNormalizesVector() {
        assertArrayEquals(new double[]{0, 0.5, 1},
                new MinMaxNormalization().normalize(new double[]{10, 20, 30}), 1e-9);
    }

    @Test
    void minMaxHandlesUnsortedNegativeValues() {
        assertArrayEquals(new double[]{0.5, 1, 0},
                new MinMaxNormalization().normalize(new double[]{0, 10, -10}), 1e-9);
    }

    @Test
    void minMaxHandlesExtremeRange() {
        assertArrayEquals(new double[]{0, 0.5, 1}, new MinMaxNormalization().normalize(
                new double[]{-Double.MAX_VALUE, 0, Double.MAX_VALUE}), 1e-9);
    }

    @Test
    void minMaxHandlesSmallRange() {
        assertArrayEquals(new double[]{0, 0.5, 1},
                new MinMaxNormalization().normalize(new double[]{0, 1e-13, 2e-13}), 1e-9);
    }

    @Test
    void meanCenteringSubtractsMean() {
        assertArrayEquals(new double[]{-10, 0, 10},
                new MeanCenteringNormalization().normalize(new double[]{10, 20, 30}), 1e-9);
    }

    @Test
    void meanCenteringHandlesNegativeValues() {
        assertArrayEquals(new double[]{-2, 0, 2},
                new MeanCenteringNormalization().normalize(new double[]{-6, -4, -2}), 1e-9);
    }

    @Test
    void meanCenteringAvoidsSumOverflow() {
        assertArrayEquals(new double[]{0, 0}, new MeanCenteringNormalization().normalize(
                new double[]{Double.MAX_VALUE, Double.MAX_VALUE}), 1e-9);
        assertArrayEquals(new double[]{Double.MAX_VALUE, -Double.MAX_VALUE},
                new MeanCenteringNormalization().normalize(
                        new double[]{Double.MAX_VALUE, -Double.MAX_VALUE}), 1e-9);
    }

    @Test
    void meanCenteringReportsResultOverflow() {
        assertThrows(ArithmeticException.class, () -> new MeanCenteringNormalization().normalize(
                new double[]{Double.MAX_VALUE, Double.MAX_VALUE, -Double.MAX_VALUE}));
    }

    @Test
    void strategiesReturnZerosForConstantVectors() {
        NormalizationStrategy[] strategies = {new MinMaxNormalization(), new MeanCenteringNormalization()};
        for (NormalizationStrategy strategy : strategies) {
            assertArrayEquals(new double[]{0, 0, 0}, strategy.normalize(new double[]{5, 5, 5}), 1e-9);
            assertArrayEquals(new double[]{0}, strategy.normalize(new double[]{42}), 1e-9);
        }
    }

    @Test
    void strategiesRejectInvalidInputs() {
        NormalizationStrategy[] strategies = {new MinMaxNormalization(), new MeanCenteringNormalization()};
        double[][] invalid = {null, {}, {Double.NaN}, {Double.POSITIVE_INFINITY}, {Double.NEGATIVE_INFINITY}};
        for (NormalizationStrategy strategy : strategies) {
            for (double[] values : invalid) {
                assertThrows(IllegalArgumentException.class, () -> strategy.normalize(values));
            }
        }
    }

    @Test
    void strategiesProtectInputArray() {
        NormalizationStrategy[] strategies = {new MinMaxNormalization(), new MeanCenteringNormalization()};
        for (NormalizationStrategy strategy : strategies) {
            double[] original = {10, 20, 30};
            double[] result = strategy.normalize(original);
            assertNotSame(original, result);
            assertArrayEquals(new double[]{10, 20, 30}, original, 1e-9);
            result[0] = 999;
            assertArrayEquals(new double[]{10, 20, 30}, original, 1e-9);
        }
    }

    @Test
    void processorAcceptsDifferentStrategies() {
        double[] values = {10, 20, 30};
        FeatureProcessor minMax = new FeatureProcessor(new MinMaxNormalization());
        FeatureProcessor centered = new FeatureProcessor(new MeanCenteringNormalization());
        assertArrayEquals(new double[]{0, 0.5, 1}, minMax.process(values), 1e-9);
        assertArrayEquals(new double[]{-10, 0, 10}, centered.process(values), 1e-9);
        assertArrayEquals(new double[]{10, 20, 30}, values, 1e-9);
    }

    @Test
    void processorAcceptsCustomStrategy() {
        NormalizationStrategy copy = new NormalizationStrategy() {
            @Override
            public double[] normalize(double[] values) {
                return values.clone();
            }
        };
        double[] input = {7, 9};
        double[] result = new FeatureProcessor(copy).process(input);
        assertArrayEquals(input, result, 1e-9);
        assertNotSame(input, result);
    }

    @Test
    void processorRejectsNullStrategy() {
        assertThrows(IllegalArgumentException.class, () -> new FeatureProcessor(null));
    }
}
