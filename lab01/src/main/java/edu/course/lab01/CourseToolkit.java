package edu.course.lab01;

/**
 * Небольшие методы для первой лабораторной работы.
 */
public final class CourseToolkit {

    private CourseToolkit() {
        // Утилитарный класс не должен иметь экземпляров.
    }

    /**
     * Возвращает true, если число четное.
     */
    public static boolean isEven(int number) {
        return number % 2 == 0;
    }

    /**
     * Возвращает true, если число простое.
     */
    public static boolean isPrime(int number) {
        if (number < 2) {
            return false;
        }
        for (int d = 2; (long) d * d <= number; d++) {
            if (number % d == 0) {
                return false;
            }
        }
        return true;
    }

    /**
     * Возвращает true, если строка читается одинаково слева направо и справа налево.
     */
    public static boolean isPalindrome(String text) {
        if (text == null) {
            throw new IllegalArgumentException("Text cannot be null");
        }

        // Идем двумя указателями навстречу друг другу с краев строки
        int left = 0;
        int right = text.length() - 1;

        while (left < right) {
            if (text.charAt(left) != text.charAt(right)) {
                return false;
            }
            left++; // left += 1
            right--; // right -= 1
        }

        return true;
    }

    /**
     * Возвращает среднее арифметическое элементов массива.
     */
    public static double average(int[] values) {
        if (values == null || values.length == 0) {
            throw new IllegalArgumentException("Массив не может быть пустым");
        }

        long sum = 0;
        for (int val : values) { // for val in values
            sum += val;
        }

        return (double) sum / values.length;
    }

        /**
     * Возвращает минимальный элемент массива целых чисел.
     */
    public static int min(int[] values) {
        if (values == null || values.length == 0) {
            throw new IllegalArgumentException("Массив не может быть пустым");
        }

        // Стартуем с первого элемента массива
        int currentMin = values[0];
        for (int i = 1; i < values.length; i++) {
            if (values[i] < currentMin) {
                currentMin = values[i];
            }
        }

        return currentMin;
    }

    /**
     * Возвращает максимальный элемент массива целых чисел.
     * Не изменяет исходный массив.
     * При передаче null или пустого массива выбрасывает IllegalArgumentException.
     */
    public static int max(int[] values) {
        if (values == null || values.length == 0) {
            throw new IllegalArgumentException("Массив не может быть пустым");
        }

        // Стартуем с первого элемента массива
        int currentMax = values[0];
        for (int i = 1; i < values.length; i++) {
            if (values[i] > currentMax) {
                currentMax = values[i];
            }
        }

        return currentMax;
    }

}
