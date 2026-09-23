package edu.course.lab01;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CourseToolkitTest {

    // --- Тесты isEven ---

    @Test
    void returnsTrueForEvenNumber() {
        boolean result = CourseToolkit.isEven(8);

        assertTrue(result);
    }

    @Test
    void returnsFalseForOddNumber() {
        boolean result = CourseToolkit.isEven(7);

        assertFalse(result);
    }

    @Test
    void returnsTrueForZero() {
        boolean result = CourseToolkit.isEven(0);
        assertTrue(result);
    }

    // --- Подготовительная часть ЛР 1 ---

    @Test
    void returnsTrueForNegativeEvenNumber() {
        boolean result = CourseToolkit.isEven(-8);
        assertTrue(result);
    }

    // --- Тесты для isPrime  ---

    @Test
    void returnsFalseForNumbersLessThanTwo() {
        assertFalse(CourseToolkit.isPrime(-5));
        assertFalse(CourseToolkit.isPrime(0));
        assertFalse(CourseToolkit.isPrime(1));  
    }

    @Test
    void returnsTrueForSmallPrimes() {
        assertTrue(CourseToolkit.isPrime(2));
        assertTrue(CourseToolkit.isPrime(3));
        assertTrue(CourseToolkit.isPrime(17));  
    }

    @Test
    void returnsFalseForCompositeNumbers() {
        assertFalse(CourseToolkit.isPrime(4));
        assertFalse(CourseToolkit.isPrime(15));
        assertFalse(CourseToolkit.isPrime(100));
    }

    @Test
    void returnsFalseForSquareOfPrime() {
        assertFalse(CourseToolkit.isPrime(9));
        assertFalse(CourseToolkit.isPrime(25));
        assertFalse(CourseToolkit.isPrime(49));
    }

    // --- Тесты для isPalindrome ---

    @Test
    void returnsTrueForValidPalindromes() {
        assertTrue(CourseToolkit.isPalindrome(""));
        assertTrue(CourseToolkit.isPalindrome("a"));
        assertTrue(CourseToolkit.isPalindrome("radar"));
    }

    @Test
    void returnsFalseWhenCaseOrSpacesMismatch() {
        assertFalse(CourseToolkit.isPalindrome("Radar"));
        assertFalse(CourseToolkit.isPalindrome("race car"));
        assertFalse(CourseToolkit.isPalindrome("hello"));
    }

    @Test
    void throwsIllegalArgumentExceptionWhenPalindromeInputIsNull() {
        assertThrows(IllegalArgumentException.class, () -> CourseToolkit.isPalindrome(null));
    }

    // --- Тесты для average ---

    @Test
    void calculatesAverageForPositiveNumbers() {
        assertEquals(3.0, CourseToolkit.average(new int[]{1, 2, 3, 4, 5}), 1e-9);
    }

    @Test
    void calculatesAverageWithFractionalResult() {
        assertEquals(1.5, CourseToolkit.average(new int[]{1, 2}), 1e-9);
    }

    @Test
    void calculatesAverageForNegativeAndMixedNumbers() {
        int[] numbers = {-10, 10, -5, 5};
        assertEquals(0.0, CourseToolkit.average(numbers), 1e-9);
    }


    @Test
    void doesNotModifyInputArray() {
        int[] numbers = {10, 20, 30};
        CourseToolkit.average(numbers);
        assertArrayEquals(new int[]{10, 20, 30}, numbers);
    }

    @Test
    void throwsIllegalArgumentExceptionWhenAverageInputIsNull() {
        assertThrows(IllegalArgumentException.class, () -> CourseToolkit.average(null));
    }

    @Test
    void throwsIllegalArgumentExceptionWhenAverageInputIsEmpty() {

        assertThrows(IllegalArgumentException.class, () -> CourseToolkit.average(new int[0]));
    }


        // --- Тесты min (доп. часть) ---

    @Test
    void findsMinInPositiveNumbers() {
        int[] numbers = {5, 2, 8, 1, 9};
        assertEquals(1, CourseToolkit.min(numbers));
    }

    @Test
    void findsMinInNegativeNumbers() {
        int[] numbers = {-10, -3, -50, -1};
        assertEquals(-50, CourseToolkit.min(numbers));
    }

    @Test
    void findsMinInSingleElementArray() {
        assertEquals(42, CourseToolkit.min(new int[]{42}));
    }

    @Test
    void minDoesNotModifyInputArray() {
        int[] numbers = {10, 5, 20};
        CourseToolkit.min(numbers);
        assertArrayEquals(new int[]{10, 5, 20}, numbers);
    }

    @Test
    void throwsExceptionWhenMinInputIsNull() {
        assertThrows(IllegalArgumentException.class, () -> CourseToolkit.min(null));
    }

    @Test
    void throwsExceptionWhenMinInputIsEmpty() {
        assertThrows(IllegalArgumentException.class, () -> CourseToolkit.min(new int[0]));
    }

    // --- Тесты max (доп. часть) ---

    @Test
    void findsMaxInPositiveNumbers() {
        int[] numbers = {5, 2, 8, 1, 9};
        assertEquals(9, CourseToolkit.max(numbers));
    }

    @Test
    void findsMaxInNegativeNumbers() {
        int[] numbers = {-10, -3, -50, -1};
        assertEquals(-1, CourseToolkit.max(numbers));
    }

    @Test
    void findsMaxInSingleElementArray() {
        assertEquals(42, CourseToolkit.max(new int[]{42}));
    }

    @Test
    void maxDoesNotModifyInputArray() {
        int[] numbers = {10, 5, 20};
        CourseToolkit.max(numbers);
        assertArrayEquals(new int[]{10, 5, 20}, numbers);
    }

    @Test
    void throwsExceptionWhenMaxInputIsNull() {
        assertThrows(IllegalArgumentException.class, () -> CourseToolkit.max(null));
    }

    @Test
    void throwsExceptionWhenMaxInputIsEmpty() {
        assertThrows(IllegalArgumentException.class, () -> CourseToolkit.max(new int[0]));
    }

}
