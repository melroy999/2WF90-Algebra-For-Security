package core;

import polynomial.Polynomial;
import polynomial.support.implementation.DoubleNumeric;
import polynomial.support.implementation.FloatNumeric;
import polynomial.support.implementation.IntegerNumeric;
import polynomial.support.implementation.ModNumeric;

/**
 * Created by Melroy van Nijnatten - 0849740.
 */
public class Core {
    public Core() {
        new Polynomial<Integer>(new IntegerNumeric());
        new Polynomial<Integer>(new ModNumeric(11));
        new Polynomial<Double>(new DoubleNumeric());
        new Polynomial<Float>(new FloatNumeric());
    }

    public static void main(String[] args){
        new Core();
    }
}
