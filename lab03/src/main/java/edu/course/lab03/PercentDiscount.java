package edu.course.lab03;

public class PercentDiscount implements DiscountPolicy {
    private final double percent;

    public PercentDiscount(double percent) {
        if (!Double.isFinite(percent) || percent < 0 || percent > 100) {
            throw new IllegalArgumentException("Процент должен быть от 0 до 100");
        }
        this.percent = percent;
    }

    @Override
    public double apply(double price) {
        if (!Double.isFinite(price) || price < 0) {
            throw new IllegalArgumentException("Цена должна быть конечной и неотрицательной");
        }
        return price * (1.0 - percent / 100.0);
    }
}
