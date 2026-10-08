package edu.course.lab02;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DataSampleTest {

    @Test
    void createsSampleWithValidArguments() {
        double[] features = {1.5, 2.5, 3.5};
        DataSample sample = new DataSample("sample-01", "iris-setosa", SampleStatus.RAW, features);

        assertEquals("sample-01", sample.getId());
        assertEquals("iris-setosa", sample.getLabel());
        assertEquals(SampleStatus.RAW, sample.getStatus());
        assertArrayEquals(new double[]{1.5, 2.5, 3.5}, sample.getFeatures(), 1e-9);
    }

    // --- Проверка инвариантов конструктора ---

    @Test
    void throwsExceptionWhenIdIsNullOrBlank() {
        double[] features = {1.0, 2.0};
        assertThrows(IllegalArgumentException.class, () -> new DataSample(null, "label", SampleStatus.RAW, features));
        assertThrows(IllegalArgumentException.class, () -> new DataSample("", "label", SampleStatus.RAW, features));
        assertThrows(IllegalArgumentException.class, () -> new DataSample("   ", "label", SampleStatus.RAW, features));
    }

    @Test
    void throwsExceptionWhenLabelIsNullOrBlank() {
        double[] features = {1.0, 2.0};
        assertThrows(IllegalArgumentException.class, () -> new DataSample("id", null, SampleStatus.RAW, features));
        assertThrows(IllegalArgumentException.class, () -> new DataSample("id", "", SampleStatus.RAW, features));
        assertThrows(IllegalArgumentException.class, () -> new DataSample("id", "   ", SampleStatus.RAW, features));
    }

    @Test
    void throwsExceptionWhenStatusIsNull() {
        double[] features = {1.0, 2.0};
        assertThrows(IllegalArgumentException.class, () -> new DataSample("id", "label", null, features));
    }

    @Test
    void throwsExceptionWhenFeaturesAreNullOrEmpty() {
        assertThrows(IllegalArgumentException.class, () -> new DataSample("id", "label", SampleStatus.RAW, null));
        assertThrows(IllegalArgumentException.class, () -> new DataSample("id", "label", SampleStatus.RAW, new double[0]));
    }

    @Test
    void throwsExceptionWhenFeaturesContainNaNOrInfinite() {
        assertThrows(IllegalArgumentException.class, () -> 
            new DataSample("id", "label", SampleStatus.RAW, new double[]{1.0, Double.NaN}));
        assertThrows(IllegalArgumentException.class, () -> 
            new DataSample("id", "label", SampleStatus.RAW, new double[]{Double.POSITIVE_INFINITY, 2.0}));
    }

    // --- Проверка методов поведения ---

    @Test
    void changesStatusSuccessfully() {
        DataSample sample = new DataSample("s1", "label", SampleStatus.RAW, new double[]{1.0, 2.0});
        sample.changeStatus(SampleStatus.PROCESSED);
        assertEquals(SampleStatus.PROCESSED, sample.getStatus());

        sample.changeStatus(SampleStatus.READY);
        assertEquals(SampleStatus.READY, sample.getStatus());
    }

    @Test
    void throwsExceptionWhenNewStatusIsNull() {
        DataSample sample = new DataSample("s1", "label", SampleStatus.RAW, new double[]{1.0, 2.0});
        assertThrows(IllegalArgumentException.class, () -> sample.changeStatus(null));
    }

    @Test
    void checksIfSampleIsReady() {
        DataSample rawSample = new DataSample("s1", "label", SampleStatus.RAW, new double[]{1.0});
        assertFalse(rawSample.isReady());

        DataSample processedSample = new DataSample("s2", "label", SampleStatus.PROCESSED, new double[]{1.0});
        assertFalse(processedSample.isReady());

        DataSample readySample = new DataSample("s3", "label", SampleStatus.READY, new double[]{1.0});
        assertTrue(readySample.isReady());
    }

    @Test
    void calculatesAverageFeatureCorrectly() {
        DataSample sample = new DataSample("s1", "label", SampleStatus.RAW, new double[]{2.0, 4.0, 6.0, 8.0});
        assertEquals(5.0, sample.averageFeature(), 1e-9);
    }

    @Test
    void averagesMaximumFeaturesWithoutOverflow() {
        DataSample sample = new DataSample("s1", "label", SampleStatus.RAW,
                new double[]{Double.MAX_VALUE, Double.MAX_VALUE});
        assertEquals(Double.MAX_VALUE, sample.averageFeature());
    }

    @Test
    void averagesMinimumNegativeFeaturesWithoutOverflow() {
        DataSample sample = new DataSample("s1", "label", SampleStatus.RAW,
                new double[]{-Double.MAX_VALUE, -Double.MAX_VALUE});
        assertEquals(-Double.MAX_VALUE, sample.averageFeature());
    }

    @Test
    void averagesOppositeExtremeFeaturesWithoutOverflow() {
        DataSample sample = new DataSample("s1", "label", SampleStatus.RAW,
                new double[]{Double.MAX_VALUE, -Double.MAX_VALUE});
        assertEquals(0.0, sample.averageFeature());
    }

    @Test
    void averagesMixedExtremeFeaturesWithoutOverflow() {
        DataSample sample = new DataSample("s1", "label", SampleStatus.RAW,
                new double[]{Double.MAX_VALUE, Double.MAX_VALUE, -Double.MAX_VALUE});
        double expected = Double.MAX_VALUE / 3.0;
        // Допускаем несколько шагов представления double из-за округления.
        assertEquals(expected, sample.averageFeature(), 4.0 * Math.ulp(expected));
    }

    @Test
    void preservesAverageOfEqualSmallestPositiveFeatures() {
        DataSample sample = new DataSample("s1", "label", SampleStatus.RAW,
                new double[]{Double.MIN_VALUE, Double.MIN_VALUE});
        assertEquals(Double.MIN_VALUE, sample.averageFeature());
    }

    // --- Ключевые тесты защитного копирования (Defensive Copying) ---

    @Test
    void constructorProtectsAgainstExternalArrayModification() {
        double[] externalArray = {10.0, 20.0};
        DataSample sample = new DataSample("s1", "label", SampleStatus.RAW, externalArray);

        // Изменяем исходный внешний массив снаружи
        externalArray[0] = 999.0;

        // Внутреннее состояние объекта не должно измениться
        assertArrayEquals(new double[]{10.0, 20.0}, sample.getFeatures(), 1e-9);
    }

    @Test
    void getterProtectsAgainstInternalArrayModification() {
        DataSample sample = new DataSample("s1", "label", SampleStatus.RAW, new double[]{10.0, 20.0});

        // Получаем массив через геттер и пытаемся его модифицировать
        double[] leakedArray = sample.getFeatures();
        leakedArray[0] = 999.0;

        // Внутреннее состояние объекта должно остаться нетронутым
        assertArrayEquals(new double[]{10.0, 20.0}, sample.getFeatures(), 1e-9);
    }

    // --- Дополнительная часть: нормализация признаков ---

    @Test
    void normalizesFeaturesWithoutModifyingOriginal() {
        double[] original = {10.0, 20.0, 30.0};
        DataSample sample = new DataSample("s1", "label", SampleStatus.RAW, original);

        double[] normalized = sample.normalizedFeatures();

        // 10 -> 0.0, 20 -> 0.5, 30 -> 1.0
        assertArrayEquals(new double[]{0.0, 0.5, 1.0}, normalized, 1e-9);

        // Исходные признаки внутри объекта не изменились
        assertArrayEquals(new double[]{10.0, 20.0, 30.0}, sample.getFeatures(), 1e-9);
    }

    @Test
    void normalizesEqualFeaturesToZeros() {
        DataSample sample = new DataSample("s1", "label", SampleStatus.RAW, new double[]{5.0, 5.0, 5.0});
        assertArrayEquals(new double[]{0.0, 0.0, 0.0}, sample.normalizedFeatures(), 1e-9);
    }

    @Test
    void normalizesSingleFeatureToZero() {
        DataSample sample = new DataSample("s1", "label", SampleStatus.RAW, new double[]{42.0});
        assertArrayEquals(new double[]{0.0}, sample.normalizedFeatures(), 1e-9);
    }

    @Test
    void normalizesNegativeAndUnsortedFeatures() {
        DataSample sample = new DataSample("s1", "label", SampleStatus.RAW, new double[]{0.0, 10.0, -10.0});
        assertArrayEquals(new double[]{0.5, 1.0, 0.0}, sample.normalizedFeatures(), 1e-9);
    }

    @Test
    void normalizesDistinctFeaturesEvenWhenRangeIsSmall() {
        DataSample sample = new DataSample("s1", "label", SampleStatus.RAW, new double[]{0.0, 1e-13, 2e-13});
        assertArrayEquals(new double[]{0.0, 0.5, 1.0}, sample.normalizedFeatures(), 1e-9);
    }

    @Test
    void normalizesExtremeFiniteFeaturesWithoutOverflow() {
        DataSample sample = new DataSample("s1", "label", SampleStatus.RAW,
                new double[]{-Double.MAX_VALUE, 0.0, Double.MAX_VALUE});
        assertArrayEquals(new double[]{0.0, 0.5, 1.0}, sample.normalizedFeatures(), 1e-9);
    }

    @Test
    void changingNormalizedResultDoesNotChangeSampleOrFutureResults() {
        DataSample sample = new DataSample("s1", "T", SampleStatus.PROCESSED, new double[]{10.0, 20.0, 30.0});
        double[] normalized = sample.normalizedFeatures();
        normalized[0] = 999.0;

        assertArrayEquals(new double[]{10.0, 20.0, 30.0}, sample.getFeatures(), 1e-9);
        assertArrayEquals(new double[]{0.0, 0.5, 1.0}, sample.normalizedFeatures(), 1e-9);
        assertEquals("s1", sample.getId());
        assertEquals("T", sample.getLabel());
        assertEquals(SampleStatus.PROCESSED, sample.getStatus());
    }
}
