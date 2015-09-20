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
}
