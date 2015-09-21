package polynomial.support;

/**
 * Created by Melroy van Nijnatten - 0849740.
 */
public interface Numeric<T> {
    public T add(T T1, T T2);
    public T subtract(T T1, T T2);
    public T quotient(T T1, T T2);
    public T remainder(T T1, T T2);
    public T multiply(T T1, T T2);
    public T negate(T T1);
    public boolean equal(T T1, T T2);
    public boolean greater(T T1, T T2);
    public boolean isPositive(T T1);
    public boolean isNegative(T T1);
    public boolean isOne(T T1);
    public boolean isZero(T T1);
    public boolean isMinusOne(T T1);
    public T parse(String T1);
    public T format(T T1);
}