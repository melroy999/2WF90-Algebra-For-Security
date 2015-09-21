package polynomial;

import polynomial.support.Numeric;
import java.util.TreeMap;

/**
 * Created by Melroy van Nijnatten - 0849740.
 */
public class Polynomial<T extends Number> {
    private final Numeric<T> calc;
    private final TreeMap<Integer, T> terms;

    /**
     *
     * #-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#
     *                                  Constructors
     * #-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#
     *
     */

    public Polynomial(Numeric<T> calc, String rep) {
        this.calc = calc;
        terms = null;
    }

    /**
     *
     * #-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#
     *                                     Getters
     * #-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#
     *
     */

    /**
     *
     * @param degree
     * @return
     */
    public T getCoefficient(int degree) {
        return terms.get(degree);
    }

    /**
     *
     * @param degree
     * @return
     */
    public T get(int degree) {
        return getCoefficient(degree);
    }

    /**
     *
     * @return
     */
    public int getDegree(){
        return terms.lastKey();
    }
}
