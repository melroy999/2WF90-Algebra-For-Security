package polynomial.support.implementation;

import polynomial.support.Numeric;

/**
 * Created by Melroy van Nijnatten - 0849740.
 */
public final class DoubleNumeric implements Numeric<Double> {
    @Override
    public Double add(Double n1, Double n2) {
        return n1 + n2;
    }

    @Override
    public Double subtract(Double n1, Double n2) {
        return n1 - n2;
    }

    @Override
    public Double quotient(Double n1, Double n2) {
        return n1 / n2;
    }

    @Override
    public Double remainder(Double n1, Double n2) {
        return n1 % n2;
    }

    @Override
    public Double multiply(Double n1, Double n2) {
        return n1 * n2;
    }

    @Override
    public Double negate(Double n1) {
        return subtract(0d, n1);
    }

    @Override
    public boolean equal(Double T1, Double T2) {
        return T1.equals(T2);
    }

    @Override
    public boolean greater(Double T1, Double T2) {
        return T1 > T2;
    }

    @Override
    public boolean isPositive(Double T1) {
        return T1 > 0;
    }

    @Override
    public boolean isNegative(Double T1) {
        return T1 < 0;
    }

    @Override
    public boolean isOne(Double T1) {
        return T1 == 1;
    }

    @Override
    public boolean isZero(Double T1) {
        return T1 == 0;
    }

    @Override
    public boolean isMinusOne(Double T1) {
        return T1 == -1;
    }

    @Override
    public Double parse(String T1) {
        return Double.parseDouble(T1);
    }

    @Override
    public Double format(Double T1) {
        return T1;
    }
}
