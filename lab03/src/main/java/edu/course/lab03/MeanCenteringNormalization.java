package edu.course.lab03;

public class MeanCenteringNormalization implements NormalizationStrategy {
    @Override
    public double[] normalize(double[] values) {
        if (values == null || values.length == 0) {
            throw new IllegalArgumentException("Вектор не может быть null или пустым");
        }
        double mean = 0.0;
        int count = 0;
        for (double value : values) {
            if (!Double.isFinite(value)) {
                throw new IllegalArgumentException("Элементы должны быть конечными");
            }
            count++;
            double difference = value - mean;
            if (Double.isInfinite(difference)) {
                mean = mean * ((count - 1.0) / count) + value / count;
            } else {
                mean += difference / count;
            }
        }

        double[] result = new double[values.length];
        for (int i = 0; i < values.length; i++) {
            result[i] = values[i] - mean;
            if (!Double.isFinite(result[i])) {
                throw new ArithmeticException("Результат центрирования выходит за диапазон double");
            }
        }
        return result;
    }
}
