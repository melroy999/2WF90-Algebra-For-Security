package polynomial.support.implementation;

import polynomial.support.Numeric;

/**
 * Created by Melroy van Nijnatten - 0849740.
 */
public class IntegerNumeric implements Numeric<Integer> {
    @Override
    public Integer add(Integer n1, Integer n2) {
        return n1 + n2;
    }

    @Override
    public Integer subtract(Integer n1, Integer n2) {
        return n1 - n2;
    }

    @Override
    public Integer quotient(Integer n1, Integer n2) {
        return n1 / n2;
    }

    @Override
    public Integer remainder(Integer n1, Integer n2) {
        return n1 % n2;
    }

    @Override
    public Integer multiply(Integer n1, Integer n2) {
        return n1 * n2;
    }

    @Override
    public Integer negate(Integer n1) {
        return subtract(0, n1);
    }

    @Override
    public boolean equal(Integer T1, Integer T2) {
        return T1.equals(T2);
    }

    @Override
    public boolean greater(Integer T1, Integer T2) {
        return T1 > T2;
    }

    @Override
    public boolean isPositive(Integer T1) {
        return T1 > 0;
    }

    @Override
    public boolean isNegative(Integer T1) {
        return T1 < 0;
    }

    @Override
    public boolean isOne(Integer T1) {
        return T1 == 1;
    }

    @Override
    public boolean isZero(Integer T1) {
        return T1 == 0;
    }

    @Override
    public boolean isMinusOne(Integer T1) {
        return T1 == -1;
    }

    @Override
    public Integer parse(String T1) {
        return Integer.parseInt(T1);
    }

    @Override
    public Integer format(Integer T1) {
        return T1;
    }
}
