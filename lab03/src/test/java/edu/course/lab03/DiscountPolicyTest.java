package edu.course.lab03;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class DiscountPolicyTest {
    @Test
    void noDiscountKeepsPrice() {
        assertEquals(100.0, new NoDiscount().apply(100));
        assertEquals(0.0, new NoDiscount().apply(0));
    }

    @Test
    void policiesRejectInvalidPrices() {
        DiscountPolicy[] policies = {new NoDiscount(), new PercentDiscount(20)};
        for (DiscountPolicy policy : policies) {
            for (double price : new double[]{-1, Double.NaN, Double.POSITIVE_INFINITY}) {
                assertThrows(IllegalArgumentException.class, () -> policy.apply(price));
            }
        }
    }

    @Test
    void percentDiscountReducesPrice() {
        assertEquals(80.0, new PercentDiscount(20).apply(100), 1e-9);
        assertEquals(150.0, new PercentDiscount(25).apply(200), 1e-9);
    }

    @Test
    void acceptsBoundaryPercentages() {
        assertEquals(100.0, new PercentDiscount(0).apply(100));
        assertEquals(0.0, new PercentDiscount(100).apply(100));
    }

    @Test
    void rejectsInvalidPercentages() {
        for (double percent : new double[]{-1, 101, Double.NaN, Double.POSITIVE_INFINITY}) {
            assertThrows(IllegalArgumentException.class, () -> new PercentDiscount(percent));
        }
    }

    @Test
    void calculatorAcceptsDifferentPolicies() {
        assertEquals(100.0, new PriceCalculator(new NoDiscount()).calculate(100));
        assertEquals(80.0, new PriceCalculator(new PercentDiscount(20)).calculate(100), 1e-9);
    }

    @Test
    void calculatorRejectsNullPolicy() {
        assertThrows(IllegalArgumentException.class, () -> new PriceCalculator(null));
    }
}
