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
}
