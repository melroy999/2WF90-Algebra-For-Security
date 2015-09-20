package polynomial;

import polynomial.support.Numeric;
import polynomial.support.implementation.ModNumeric;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import java.util.TreeMap;

/**
 * Created by Melroy van Nijnatten - 0849740.
 */
public class Polynomial<T extends Number> {
    private final Numeric<T> calc;
    private final TreeMap<Integer, T> polynomial;

    /**
     *
     * #-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#
     *                                  Constructors
     * #-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#
     *
     */

    public Polynomial(Numeric<T> calc, String rep) {
        this.calc = calc;
        polynomial = null;
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
        return polynomial.get(degree);
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
        return polynomial.lastKey();
    }
}
