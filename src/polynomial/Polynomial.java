package polynomial;

import polynomial.support.Numeric;

import java.util.TreeMap;

/**
 * Created by Melroy van Nijnatten - 0849740.
 */
public class Polynomial<T extends Number> {
    private final Numeric<T> num;

    private TreeMap<Integer, Monomial<T>> polynomial;

    public Polynomial(Numeric<T> num) {
        this.num = num;
    }
}
