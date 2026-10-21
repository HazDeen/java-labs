package edu.course.lab03;

public class MinMaxNormalization implements NormalizationStrategy {
    @Override
    public double[] normalize(double[] values) {
        if (values == null || values.length == 0) {
            throw new IllegalArgumentException("Вектор не может быть null или пустым");
        }
        double min = values[0];
        double max = values[0];
        for (double value : values) {
            if (!Double.isFinite(value)) {
                throw new IllegalArgumentException("Элементы должны быть конечными");
            }
            if (value < min) {
                min = value;
            }
            if (value > max) {
                max = value;
            }
        }

        double[] result = new double[values.length];
        if (min == max) {
            return result;
        }
        double range = max - min;
        for (int i = 0; i < values.length; i++) {
            if (Double.isInfinite(range)) {
                result[i] = (values[i] / 2.0 - min / 2.0) / (max / 2.0 - min / 2.0);
            } else {
                result[i] = (values[i] - min) / range;
            }
        }
        return result;
    }
}
