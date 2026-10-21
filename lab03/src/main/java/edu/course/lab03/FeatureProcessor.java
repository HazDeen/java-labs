package edu.course.lab03;

public class FeatureProcessor {
    private final NormalizationStrategy strategy;

    public FeatureProcessor(NormalizationStrategy strategy) {
        if (strategy == null) {
            throw new IllegalArgumentException("Стратегия не может быть null");
        }
        this.strategy = strategy;
    }

    public double[] process(double[] values) {
        return strategy.normalize(values);
    }
}
