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
}
