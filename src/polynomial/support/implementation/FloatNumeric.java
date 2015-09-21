package polynomial.support.implementation;

import polynomial.support.Numeric;

/**
 * Created by Melroy van Nijnatten - 0849740.
 */
public final class FloatNumeric implements Numeric<Float> {
    @Override
    public Float add(Float n1, Float n2) {
        return n1 + n2;
    }

    @Override
    public Float subtract(Float n1, Float n2) {
        return n1 - n2;
    }

    @Override
    public Float quotient(Float n1, Float n2) {
        return n1 / n2;
    }

    @Override
    public Float remainder(Float n1, Float n2) {
        return n1 % n2;
    }

    @Override
    public Float multiply(Float n1, Float n2) {
        return n1 * n2;
    }

    @Override
    public Float negate(Float n1) {
        return subtract(0f, n1);
    }

    @Override
    public boolean equal(Float T1, Float T2) {
        return T1.equals(T2);
    }

    @Override
    public boolean greater(Float T1, Float T2) {
        return T1 > T2;
    }

    @Override
    public boolean isPositive(Float T1) {
        return T1 > 0;
    }

    @Override
    public boolean isNegative(Float T1) {
        return T1 < 0;
    }

    @Override
    public boolean isOne(Float T1) {
        return T1 == 1;
    }

    @Override
    public boolean isZero(Float T1) {
        return T1 == 0;
    }

    @Override
    public boolean isMinusOne(Float T1) {
        return T1 == -1;
    }

    @Override
    public Float parse(String T1) {
        return Float.parseFloat(T1);
    }

    @Override
    public Float format(Float T1) {
        return T1;
    }
}
