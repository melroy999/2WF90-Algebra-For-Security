package polynomial;

/**
 * Created by Melroy van Nijnatten - 0849740.
 */
public class Monomial<T extends Number> {
    private final T coefficient;
    private final int degree;

    public Monomial(T coefficient, int degree) {
        this.coefficient = coefficient;
        this.degree = degree;
    }

    public T getCoefficient() {
        return coefficient;
    }

    public int getDegree() {
        return degree;
    }

    public Monomial<T> add(Monomial<T> toAdd){
        return null;
    }

    public Monomial<T> subtract(){
        return null;
    }

    public Monomial<T> divide(){
        return null;
    }

    public Monomial<T> multiply(){
        return null;
    }
}
