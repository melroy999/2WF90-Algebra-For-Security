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
}