package edu.course.lab02;

import java.util.Arrays;

/**
 * Образец данных (наблюдение) для моделей искусственного интеллекта.
 */
public class DataSample {

    private final SampleId id;
    private final String label;
    private SampleStatus status;
    private final double[] features;

    /**
     * Создает образец данных с валидацией инвариантов и защитным копированием.
     */
    public DataSample(String id, String label, SampleStatus status, double[] features) {
        SampleId sampleId = new SampleId(id);
        if (label == null || label.isBlank()) {
            throw new IllegalArgumentException("Метка не может быть пустой или null");
        }
        if (status == null) {
            throw new IllegalArgumentException("Статус не может быть null");
        }
        if (features == null || features.length == 0) {
            throw new IllegalArgumentException("Массив признаков не может быть пустым или null");
        }
        for (double val : features) {
            if (Double.isNaN(val) || Double.isInfinite(val)) {
                throw new IllegalArgumentException("Признаки должны содержать конечные вещественные числа");
            }
        }

        this.id = sampleId;
        this.label = label;
        this.status = status;
        // Защитное копирование: защищает внутреннее состояние от изменений снаружи
        this.features = Arrays.copyOf(features, features.length);
    }

    /**
     * Изменяет текущий статус образца данных.
     */
    public void changeStatus(SampleStatus newStatus) {
        if (newStatus == null) {
            throw new IllegalArgumentException("Новый статус не может быть null");
        }
        this.status = newStatus;
    }

    /**
     * Проверяет, готов ли образец данных для обучения модели.
     */
    public boolean isReady() {
        return this.status == SampleStatus.READY;
    }

    /**
     * Вычисляет среднее арифметическое значений признаков.
     */
    public double averageFeature() {
        double mean = 0.0;
        int count = 0;
        for (double feature : this.features) {
            count++;
            // Обновляем среднее постепенно, не накапливая огромную сумму.
            double difference = feature - mean;
            if (Double.isInfinite(difference)) {
                // Для больших чисел разных знаков даже разность может переполниться.
                mean = mean * ((count - 1.0) / count) + feature / count;
            } else {
                mean += difference / count;
            }
        }
        return mean;
    }

    /**
     * Возвращает новый массив признаков, нормализованных методом Min-Max к отрезку [0, 1].
     * Если все признаки равны, возвращает массив нулей.
     * Исходный объект и массив признаков не изменяются.
     */
    public double[] normalizedFeatures() {
        double min = this.features[0];
        double max = this.features[0];
        for (double val : this.features) {
            if (val < min) {
                min = val;
            }
            if (val > max) {
                max = val;
            }
        }

        double[] result = new double[this.features.length];
        if (min == max) {
            return result;
        }
        double range = max - min;

        for (int i = 0; i < this.features.length; i++) {
            if (Double.isInfinite(range)) {
                // Уменьшаем масштаб, если разность двух конечных чисел переполнилась.
                result[i] = (this.features[i] / 2.0 - min / 2.0) / (max / 2.0 - min / 2.0);
            } else {
                result[i] = (this.features[i] - min) / range;
            }
        }
        return result;
    }

    public String getId() {
        return this.id.value();
    }

    /**
     * Возвращает неизменяемое значение идентификатора; копирование не требуется.
     */
    public SampleId getSampleId() {
        return this.id;
    }

    public String getLabel() {
        return this.label;
    }

    public SampleStatus getStatus() {
        return this.status;
    }

    /**
     * Возвращает копию массива признаков (защитное копирование при чтении).
     */
    public double[] getFeatures() {
        return Arrays.copyOf(this.features, this.features.length);
    }
}
